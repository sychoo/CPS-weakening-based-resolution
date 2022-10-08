package edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.drone;

import io.mavsdk.System;
import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import edu.cmu.stl.encoder.floats.util.FileOperation;
import io.mavsdk.telemetry.Telemetry;
import io.reactivex.Flowable;
import java.util.ArrayList;
import java.util.Arrays;

import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.Params;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.feature.BoundaryEnforcer;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.mission.FlyWaypointsAroundCenter;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.monitor.Monitor;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.resolver.Resolver;
import edu.cmu.px4.mavsdk.utils.Util;
import java.util.Collections;

public class EgoDrone implements Runnable {
    System egoDrone;
    System followerDrone;

    // for conflict resolver (model construction)
    // by default has 3 items: north vel, east vel, and down vel
    // designed for the solver
    public static ArrayList<Double> prev_ego_command = new ArrayList<Double>(Arrays.asList(0.0, 0.0, 0.0));

    public EgoDrone(System egoDrone, System followerDrone) {
        this.egoDrone = egoDrone;
        this.followerDrone = followerDrone;
    }

    @Override
    public void run() {

        java.lang.System.out.println(Thread.currentThread().getName() + " is executing");

        DroneAction.armAndTakeoff(egoDrone);
        DroneAction.startOffboardMode(egoDrone);

        // can be used for a good demo
        // DroneAction.clockwiseClimb(egoDrone, 20);

        /************************/
        /***** Mission Prep *****/
        /************************/

        // fly towards the offset of center of the bounding box
        ArrayList<Double> offsetLatLonList = DroneTelemetry.offsetLatLon(Params.BOUND_CTR_LAT, Params.BOUND_CTR_LON, Params.EGO_DRONE_START_LOCATION_NORTH, Params.EGO_DRONE_START_LOCATION_EAST);
        Double offsetLat = offsetLatLonList.get(0);
        Double offsetLon = offsetLatLonList.get(1);

        while (true) {
            if (DroneTelemetry.distanceBetweenDroneAndDest(egoDrone, offsetLat, offsetLon) < Params.MISSION_WAYPOINT_TOLERANCE) {
                break;
            }

            // keep flying to the center of the bounding box
            prev_ego_command = DroneAction.goToDestination(egoDrone, Params.REFRESH_RATE,
                    Params.EGO_DRONE_CRUISING_SPEED, offsetLat, offsetLon);
        }

        // start the mission collection
        Monitor.start();

        /************************/
        /***** Main Mission *****/
        /************************/

        while (true) {

            long cycleStartTime = java.lang.System.currentTimeMillis(); // measured in miliseconds

            java.lang.System.out.println("ego drone is executing.");

            // get the distance bewteen ego drone and follower drone
            double distanceToFollower = DroneTelemetry.distanceBetweenDrones(egoDrone, followerDrone);
            double minDistanceToBoundary = BoundaryEnforcer.minDistanceToInnerBoundary(egoDrone);

            // add the signal variable robustness!
            // Monitor.addSignalVariables(distanceToFollower - Params.EGO_AVOID_DISTANCE, minDistanceToBoundary - Params.EGO_BOUND_DISTANCE_LIMIT);
            Monitor.addSignalVariables(distanceToFollower, minDistanceToBoundary);

            Util.println("previous ego drone command: " + prev_ego_command);
            Util.println("distance to follower: " + distanceToFollower);
            Util.println("min distance to boundary: " + minDistanceToBoundary);

            // TODO: calculate the shortest distance to the boundary

            /*******************************/
            /***** Feature Interaction *****/
            /*******************************/

            // TODO: use if condition to check conflicting scenario

            if (distanceToFollower < Params.EGO_AVOID_DISTANCE &&
                    minDistanceToBoundary < Params.EGO_BOUND_DISTANCE_LIMIT) {

                /***********************************************/
                /***** update the enforcer activation list *****/
                /***********************************************/

                // both features are activated 1.0: activated, 0.0: deactivated
                Monitor.boundaryEnforcerActivatedList.add(1.0);
                Monitor.runawayEnforcerActivatedList.add(1.0);
                Monitor.numberOfEnforcerActivatedList.add(2.0);

                /**************************************************/
                /***** Weakening Resolver + Priority Enforcer *****/
                /**************************************************/

                if (Params.RESOLVER.equals("WEAKENING - PRIORITY - RUNAWAY")
                        || Params.RESOLVER.equals("WEAKENING - PRIORITY - BOUNDARY")) {
                    ArrayList<Double> egoDronePos = DroneTelemetry.getPositionList(egoDrone);
                    ArrayList<Double> followerDronePos = DroneTelemetry.getPositionList(followerDrone);

                    long resolutionStartTime = java.lang.System.currentTimeMillis(); // measured in miliseconds

                    ArrayList<Double> actionList = Resolver.run(egoDronePos.get(0), egoDronePos.get(1),
                            egoDronePos.get(2), followerDronePos.get(0), followerDronePos.get(1),
                            followerDronePos.get(2));

                    long resolutionEndTime = java.lang.System.currentTimeMillis(); // measured in miliseconds
                    double resolutionDuration = (resolutionEndTime - resolutionStartTime) / 1000.0;

                    if (actionList != null) {
                        double ego_vel_north = actionList.get(0);
                        double ego_vel_east = actionList.get(1);
                        double ego_vel_down = actionList.get(2);
                        DroneAction.sendVelocityNed(egoDrone, Params.REFRESH_RATE, (float) ego_vel_north,
                                (float) ego_vel_east, (float) ego_vel_down, 0.0f);
                        prev_ego_command = actionList;
                        Util.println("RESOLUTION SUCCEEDED!");
                        Util.println("*** RESOLUTION COMMAND ***: " + actionList);

                    }
                    else {

                        /***************************************/
                        /***** SECONDARY PRIORITY RESOLVER *****/
                        /***************************************/

                        if (Params.RESOLVER.equals("WEAKENING - PRIORITY - RUNAWAY")) {
                            // suppose runaway takes the priority
                            // enables runaway enforcer
                            prev_ego_command = DroneAction.repelDrone(egoDrone, followerDrone, Params.REFRESH_RATE,
                                    Params.EGO_AVOID_SPEED);
                            Monitor.runaway_enforcer_counter += 1;
                            Util.println("RESOLUTION FAILED! USING PRIORITY - RUNAWAY ENFORCER");
                        }

                        if (Params.RESOLVER.equals("WEAKENING - PRIORITY - BOUNDARY")) {
                            prev_ego_command = DroneAction.goToDestination(egoDrone, Params.REFRESH_RATE,
                            Params.EGO_BOUND_ENFORCER_SPEED, Params.BOUND_CTR_LAT, Params.BOUND_CTR_LON);
                            Monitor.boundary_enforcer_counter += 1;
                        }
                    }

                    long cycleEndTime = java.lang.System.currentTimeMillis(); // measured in miliseconds
                    double cycleDuration = (cycleEndTime - cycleStartTime) / 1000.0;
                    Monitor.resolution(resolutionDuration, cycleDuration, actionList != null);

                }

                /*****************************/
                /***** Priority Resolver *****/
                /*****************************/

                else if (Params.RESOLVER.equals("PRIORITY - RUNAWAY")) {
                    // suppose runaway takes the priority
                    prev_ego_command = DroneAction.repelDrone(egoDrone, followerDrone, Params.REFRESH_RATE,
                            Params.EGO_AVOID_SPEED);
                    Monitor.runaway_enforcer_counter += 1;
                }

                else if (Params.RESOLVER.equals("PRIORITY - BOUNDARY")) {
                    prev_ego_command = DroneAction.goToDestination(egoDrone, Params.REFRESH_RATE,
                            Params.EGO_BOUND_ENFORCER_SPEED, Params.BOUND_CTR_LAT, Params.BOUND_CTR_LON);
                    Monitor.boundary_enforcer_counter += 1;
                }

                Util.println("************************************");
                Util.println("*** Feature Interaction Occurs! ***");
                Util.println("************************************");

                Monitor.feature_interaction_counter += 1;
            }

            /****************************/
            /***** Runaway Enforcer *****/
            /****************************/

            // generate avoid action if the distance is too close
            else if (distanceToFollower < Params.EGO_AVOID_DISTANCE) {
                java.lang.System.out.println("ego drone is avoiding follower drone");
                prev_ego_command = DroneAction.repelDrone(egoDrone, followerDrone, Params.REFRESH_RATE,
                        Params.EGO_AVOID_SPEED);
                // Util.println("RUNAWAY ENFORCER SPEED = " + prev_ego_command);
                Monitor.runaway_enforcer_counter += 1;

                /***********************************************/
                /***** update the enforcer activation list *****/
                /***********************************************/

                // both features are activated 1.0: activated, 0.0: deactivated
                Monitor.boundaryEnforcerActivatedList.add(0.0);
                Monitor.runawayEnforcerActivatedList.add(1.0);
                Monitor.numberOfEnforcerActivatedList.add(1.0);
            }

            /*****************************/
            /***** Boundary Enforcer *****/
            /*****************************/

            else if (minDistanceToBoundary < Params.EGO_BOUND_DISTANCE_LIMIT) {
                // 2D boundary enforcer - only consider north and east velocity
                prev_ego_command = DroneAction.goToDestination(egoDrone, Params.REFRESH_RATE,
                        Params.EGO_BOUND_ENFORCER_SPEED, Params.BOUND_CTR_LAT, Params.BOUND_CTR_LON);
                // 3D boundary enforcer - pull towards center
                // prev_ego_command = DroneAction.goToDestination3D(egoDrone,
                // Params.REFRESH_RATE, Params.EGO_BOUND_ENFORCER_SPEED, Params.BOUND_CTR_LAT,
                // Params.BOUND_CTR_LON, (Params.BOUND_SQUARE_SIDE / 2));
                // Util.println("BOUNDARY ENFORCER SPEED = " + prev_ego_command);
                Monitor.boundary_enforcer_counter += 1;

                /***********************************************/
                /***** update the enforcer activation list *****/
                /***********************************************/

                // both features are activated 1.0: activated, 0.0: deactivated
                Monitor.boundaryEnforcerActivatedList.add(1.0);
                Monitor.runawayEnforcerActivatedList.add(0.0);
                Monitor.numberOfEnforcerActivatedList.add(1.0);
            }

            /***********************************/
            /***** Fly Waypoints (Mission) *****/
            /***********************************/

            // case when to go to destination
            else {
                java.lang.System.out.println("Continuing Mission");
                // DroneAction.goToDestination(egoDrone, Params.REFRESH_RATE,
                // Params.EGO_DRONE_MAX_SPEED, Params.DEST_LAT,
                // Params.DEST_LON);

                prev_ego_command = FlyWaypointsAroundCenter.run(egoDrone);
                Util.println("EGO DRONE SPEED = " + prev_ego_command);

                /***********************************************/
                /***** update the enforcer activation list *****/
                /***********************************************/

                // both features are activated 1.0: activated, 0.0: deactivated
                // none of the features are activated. Simple flying waypoints.
                Monitor.boundaryEnforcerActivatedList.add(0.0);
                Monitor.runawayEnforcerActivatedList.add(0.0);
                Monitor.numberOfEnforcerActivatedList.add(0.0);
            }

            // terminate mission if the designated cycles have finished
            if (Monitor.cycle_counter >= Params.MAX_EGO_DRONE_CYCLE) {
                java.lang.System.out.println("max execution cycles reached!");

                // make sure the drone hold their position and land
                DroneAction.holdPosition(egoDrone, Params.REFRESH_RATE);
                DroneAction.holdPosition(followerDrone, Params.REFRESH_RATE);

                // DroneAction.land(egoDrone);
                // DroneAction.land(followerDrone);
                break;
            }

            Util.println("cycle count: " + Monitor.cycle_counter);
            Util.println("feature interaction count: " + Monitor.feature_interaction_counter);
            Util.println("boundary enforcer count: " + Monitor.boundary_enforcer_counter);
            Util.println("runaway enforcer count: " + Monitor.feature_interaction_counter);
            Util.println("successful resolution count: " + Monitor.successful_resolution);
            Util.println("failed resolution count: " + Monitor.failed_resolution);

            Monitor.cycle_counter += 1;
        }

        /*********************************************/
        /* There is where experiment data is handled */
        /*********************************************/
        DroneAction.holdPosition(followerDrone, Params.REFRESH_RATE);
        Monitor.terminate();

        // terminate Java program
        java.lang.System.exit(0);
    }

}
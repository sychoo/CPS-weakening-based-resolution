package edu.cmu.px4.mavsdk.case_study.organ_delivery.main;

import io.mavsdk.System;
import java.util.ArrayList;
import io.mavsdk.action.Action;
import edu.cmu.px4.mavsdk.utils.Plan;
import edu.cmu.px4.mavsdk.utils.Feature;
import edu.cmu.px4.mavsdk.case_study.organ_delivery.main.resolver.Resolver;
import edu.cmu.px4.mavsdk.case_study.organ_delivery.it1.features.DeliveryPlanning;
import edu.cmu.px4.mavsdk.case_study.organ_delivery.it1.features.SafeLanding;
import io.mavsdk.mission.Mission;
import io.mavsdk.offboard.Offboard;
import io.mavsdk.telemetry.Telemetry;
import io.reactivex.Completable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.cmu.px4.mavsdk.case_study.organ_delivery.main.monitor.Monitor;
import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;

import edu.cmu.px4.mavsdk.case_study.organ_delivery.main.Params;
import edu.cmu.px4.mavsdk.utils.Util;

public class DroneController implements Runnable {

    System drone;
    Thread batteryControllerThread;

    public static double missionTimeLeft = Params.MISSION_TIME_OVERALL;

    public DroneController(System drone, Thread batteryControllerThread) {
        this.drone = drone;
        this.batteryControllerThread = batteryControllerThread;
    }

    @Override
    public void run() {
        // initialize the system and features
        System drone = new System("127.0.0.1", 50051);
        DroneAction.armAndTakeoff(drone);
        DroneAction.startOffboardMode(drone);

        // start the battery controller, count down the battery
        // after the drone has taken off
        batteryControllerThread.start();

        // initialize parameters
        Params params = new Params();

        Monitor.start();
        // stop the control loop when the drone is about to land or landed
        // this also causes slow down of the control loop
        // while (!DroneTelemetry.isLanded(drone) && !DroneTelemetry.isLanding(drone)) {
        while (true) {
            // decrement the mission time left
            missionTimeLeft -= Params.REFRESH_RATE_SECOND;

            Monitor.cycle_counter++; // increment the cycle counter
            long cycleStartTime = java.lang.System.currentTimeMillis(); // measured in miliseconds

            double distanceToDestination = DroneTelemetry.distanceBetweenDroneAndDest(drone,
                    Params.DESTINATION_LOCATION_LAT, Params.DESTINATION_LOCATION_LON);
            // warning: pulling battery percentage is slow
            // double batteryPercentage = DroneTelemetry.getRemainingBatteryPercent(drone);
            // // causes slow down for some reason
            double batteryPercentage = BatteryController.getBatteryPercentage();

            Monitor.addSignalVariables(batteryPercentage, 0.0, distanceToDestination, missionTimeLeft, Params.SPEED);

            Util.println("distance to destination : " + distanceToDestination);
            Util.println("battery percentage      : " + batteryPercentage);
            Util.println("mission time left       : " + missionTimeLeft);
            Util.println("speed                   : " + Params.SPEED);
            /*******************************/
            /***** Feature Interaction *****/
            /*******************************/
            if (distanceToDestination > Params.LANDING_POINT_TOLERANCE
                    && batteryPercentage <= Params.SAFE_LANDING_BATTERY_THRESHOLD) {
                Util.println("************************************");
                Util.println("*** Feature Interaction Occurs! ***");
                Util.println("************************************");

                /**********************************************/
                /*********** Update the Activated List ********/
                /**********************************************/
                
                // both features are activated here
                Monitor.deliveryPlannerActivatedList.add(1.0);
                Monitor.safeLandingActivatedList.add(1.0);
                Monitor.numberOfEnforcerActivatedList.add(2.0);

                // this is where the solver kicks in
                // Resolver
                if (Params.RESOLVER.equals("WEAKENING - PRIORITY - SAFELANDING")
                        || Params.RESOLVER.equals("WEAKENING - PRIORITY - DELIVERYPLANNING")) {
                    // Resolver returns a plan
                    long resolutionStartTime = java.lang.System.currentTimeMillis(); // measured in miliseconds
                    Plan p = Resolver.run(drone);
                    long resolutionEndTime = java.lang.System.currentTimeMillis(); // measured in miliseconds
                    double resolutionDuration = (resolutionEndTime - resolutionStartTime) / 1000.0;


                    Util.println("*** Solver returned plan: " + p.name() + " ***");

                    if (p.name().equals("land")) {
                        Util.println("************************************");
                        Util.println("*** Resolver: Land the drone! ***");
                        Util.println("************************************");
                        DroneAction.land(drone);
                        break;
                    }

                    if (p.name().equals("fly")) {
                        Util.println("************************************");
                        Util.println("*** Resolver: Fly to the destination! ***");
                        Util.println("************************************");
                        ArrayList<Double> actionNE = p.parameters();
                        double actionNorth = actionNE.get(1);
                        double actionEast = actionNE.get(0);
                        DroneAction.sendVelocityNed(drone, Params.REFRESH_RATE_MILLISECOND, (float) actionNorth,
                                (float) actionEast, (float) 0.0, (float) 0.0);
                    }

                    // case when the resolver returns a plan
                    // fall back to secondary resolver
                    if (p.name().equals("unsat")) {
                        // Secondary Resolver
                        if (Params.RESOLVER.equals("WEAKENING - PRIORITY - SAFELANDING")) {
                            Util.println("*** Priority - Safe Landing ***");
                            DroneAction.land(drone);
                            break;
                        }

                        if (Params.RESOLVER.equals("WEAKENING - PRIORITY - DELIVERYPLANNING")) {
                            Util.println("*** WEAKENING - PRIORITY - DELIVERYPLANNING ***");
                            DroneAction.goToDestination(drone, Params.REFRESH_RATE_MILLISECOND, Params.SPEED,
                                    Params.DESTINATION_LOCATION_LAT, Params.DESTINATION_LOCATION_LON);
                        }
                    }

                    long cycleEndTime = java.lang.System.currentTimeMillis(); // measured in miliseconds
                    double cycleDuration = (cycleEndTime - cycleStartTime) / 1000.0;
                    Monitor.resolution(resolutionDuration, cycleDuration, !p.name().equals("unsat"));
                }

                /*****************************/
                /***** Priority Resolver *****/
                /*****************************/

                if (Params.RESOLVER.equals("PRIORITY - SAFELANDING")) {
                    Util.println("*** Priority - Safe Landing ***");
                    DroneAction.land(drone);
                    break;
                }

                if (Params.RESOLVER.equals("PRIORITY - DELIVERYPLANNING")) {
                    Util.println("*** PRIORITY - DELIVERYPLANNING ***");
                    DroneAction.goToDestination(drone, Params.REFRESH_RATE_MILLISECOND, Params.SPEED,
                            Params.DESTINATION_LOCATION_LAT, Params.DESTINATION_LOCATION_LON);
                }
            }

            /************************/
            /***** safe landing *****/
            /************************/
            else if (batteryPercentage <= Params.SAFE_LANDING_BATTERY_THRESHOLD) {

                /**********************************************/
                /*********** Update the Activated List ********/
                /**********************************************/
                
                // one of the features are activated here
                Monitor.deliveryPlannerActivatedList.add(0.0);
                Monitor.safeLandingActivatedList.add(1.0);
                Monitor.numberOfEnforcerActivatedList.add(1.0);
                
                Util.println("*** Safe Landing Feature Starts! ***");
                DroneAction.land(drone);
                break;
            }

            /*****************************/
            /***** delivery planning *****/
            /*****************************/

            // keep heading towards the destination if the drone hasn't arrived.
            else if (distanceToDestination > Params.LANDING_POINT_TOLERANCE) {
                /**********************************************/
                /*********** Update the Activated List ********/
                /**********************************************/
                
                // one of the features are activated here
                Monitor.deliveryPlannerActivatedList.add(1.0);
                Monitor.safeLandingActivatedList.add(0.0);
                Monitor.numberOfEnforcerActivatedList.add(1.0);
                
                Util.println("*** Delivery Planning Starts! ***");
                DroneAction.goToDestination(drone, Params.REFRESH_RATE_MILLISECOND, Params.SPEED,
                        Params.DESTINATION_LOCATION_LAT, Params.DESTINATION_LOCATION_LON);
            }

            // part of delivery planning, drone has arrived at the destination
            else {

                /**********************************************/
                /*********** Update the Activated List ********/
                /**********************************************/
                
                // both features are deactivated here
                Monitor.deliveryPlannerActivatedList.add(0.0);
                Monitor.safeLandingActivatedList.add(0.0);
                Monitor.numberOfEnforcerActivatedList.add(0.0);
                

                Util.println("*** Drone has arrived at the destination! ***");
                DroneAction.land(drone);
                break; // landing is complete
            }
        }

        // this is where the drone starts to land
        BatteryController.startLanding = true;
        Util.println("battery level when the drone starts to land : " + BatteryController.getBatteryPercentage());
        Monitor.battery_level_when_started_landing = BatteryController.getBatteryPercentage();

        // mission time left stops updating after the drone lands
        // drone is currently landing, therefore, isLanding = 1.0
        Monitor.addSignalVariables(BatteryController.getBatteryPercentage(), 1.0, DroneTelemetry.distanceBetweenDroneAndDest(drone, Params.DESTINATION_LOCATION_LAT, Params.DESTINATION_LOCATION_LON), missionTimeLeft, 0.0);

        /**********************************************/
        /*********** Update the Activated List ********/
        /**********************************************/
        
        // both features are deactivated
        Monitor.deliveryPlannerActivatedList.add(0.0);
        Monitor.safeLandingActivatedList.add(0.0);
        Monitor.numberOfEnforcerActivatedList.add(0.0);
        
        // drone control cycle has terminated
        while (!DroneTelemetry.isLanded(drone)) {
            Util.println("waiting for the drone to land...");
            Util.println("battery : " + BatteryController.getBatteryPercentage());
            Util.println("distance to destination : " + DroneTelemetry.distanceBetweenDroneAndDest(drone,
                    Params.DESTINATION_LOCATION_LAT, Params.DESTINATION_LOCATION_LON));

            // drone is currently landing, therefore, isLanding = 1.0
            Monitor.addSignalVariables(BatteryController.getBatteryPercentage(), 1.0, DroneTelemetry.distanceBetweenDroneAndDest(drone, Params.DESTINATION_LOCATION_LAT, Params.DESTINATION_LOCATION_LON), missionTimeLeft, 0.0);

            /**********************************************/
            /*********** Update the Activated List ********/
            /**********************************************/
            
            // both features are deactivated
            Monitor.deliveryPlannerActivatedList.add(0.0);
            Monitor.safeLandingActivatedList.add(0.0);
            Monitor.numberOfEnforcerActivatedList.add(0.0);
        }

        // this is the battery level when the drone finally landed.
        Util.println("battery level when the drone has landed : " + BatteryController.getBatteryPercentage());
        Monitor.battery_level_when_landed = BatteryController.getBatteryPercentage();
        Monitor.distance_to_destination_when_landed = DroneTelemetry.distanceBetweenDroneAndDest(drone,
                Params.DESTINATION_LOCATION_LAT, Params.DESTINATION_LOCATION_LON);

        Monitor.terminate();

        java.lang.System.exit(0);
    }

    // maybe measure some kind of robustness here.

    // public static void main(String[] args) {
    // java.lang.System.out.println("Starting Organ Delivery Case Study...");
    // run();
    // }
}

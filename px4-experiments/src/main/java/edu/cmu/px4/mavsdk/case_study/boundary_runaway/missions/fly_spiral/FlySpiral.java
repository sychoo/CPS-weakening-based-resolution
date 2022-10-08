package edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.fly_spiral;
// this is a complicated version of fly spiral that requires a certain radius 
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.fly_spiral.Params;
import io.mavsdk.System;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.multiprocess_ego_follower.EgoDrone;
import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import edu.cmu.px4.mavsdk.utils.Util;

import edu.cmu.stl.encoder.floats.util.FileOperation;
import io.mavsdk.telemetry.Telemetry;
import io.reactivex.Flowable;
import java.util.ArrayList;

public class FlySpiral {

    public static String experimentFileDir = "experiments/results/bound_run/";
    public static String experimentFile = "experiments/results/bound_run/flight_path_fly_spiral.csv";

    public static double EGO_DRONE_SPEED = 15.00; // m/s
    public static double FIX_SPEED = 5.00; // m/s

    public static int REFRESH_RATE = 1; // Hz

    public static double CTR_LAT = 40.44470;
    public static double CTR_LON = -79.948124;
    public static double CTR_RADIUS = 100.00; // m
    public static double CTR_ERR_MARGIN = 20.00; // m

    // make the ANGLE ERR MARGIN loose so that it does not need to fix it all the time.

    public static double ANGLE_ERR_MARGIN = 20.00; // degree
    public static double ANGLE_FIX_INCREMENT = 10.00; // degree

    public static double RUN_CYCLE = 100; // how many cycles you want to run

    public static StringBuilder result = new StringBuilder();
    
    // refer to FlyEight code
    // 3 configurations:
    //
    // 1. max speed
    // 2. refresh rate
    // 3. elevation angle （仰角)
    //
    // reason: find the next way point based on speed (PositionNED)
    // consider the circle
    // the up lift is set

    // if the ego drone can reach the main circle with the next step (max_speed * refresh_rate, then find speed vector
    // - make sure it is not too far out of too far in of the circle
    // if not, pull towards the center of the circle


    private static class Controller implements Runnable {
        System drone;

        public Controller(System drone) {
            this.drone = drone;
        }

        public static void logData(String data) {
            if (FileOperation.isFileEmpty(experimentFile)) {
                FileOperation.writeToFile(experimentFileDir, experimentFile, data);
            }
    
            FileOperation.appendToFile(experimentFile, data);
        }


        @Override
        public void run() {

            java.lang.System.out.println(Thread.currentThread().getName() + " is executing");

            DroneAction.armAndTakeoff(drone);
            DroneAction.startOffboardMode(drone);

            // can be used for a good demo
            // DroneAction.clockwiseClimb(egoDrone, 20);

            // get the original lat and lon
            // double originalLat = DroneTelemetry.getLatitudeDeg(drone);
            // double originalLon = DroneTelemetry.getLongitudeDeg(drone);
            
            int counter = 0;

            while (true) {
                // make sure the drone is within radius (short, no more +/- 3)
                // if not, pull towards the center of the circle, or repel
                // this is done on 2D plane
                double distanceToCenter = DroneTelemetry.distance(DroneTelemetry.getLatitudeDeg(drone), DroneTelemetry.getLongitudeDeg(drone), 
                                                CTR_LAT, CTR_LON, 0, 0);
                double bearing = DroneTelemetry.bearing(DroneTelemetry.getLatitudeDeg(drone), DroneTelemetry.getLongitudeDeg(drone), 
                                                CTR_LAT, CTR_LON);                                

                double intendedHeading = bearing - 90;
                // add 360 degrees if it is not the intended heading
                if (intendedHeading < 0) {
                    intendedHeading += 360;
                }

                Util.println("current heading: " + DroneTelemetry.getHeadingDeg(drone));
                Util.println("intended heading: " + intendedHeading);

                Util.println("distance to center: " + distanceToCenter);
                Util.println("counter: " + counter);
                
                // fix the distance
                if (distanceToCenter > CTR_RADIUS + CTR_ERR_MARGIN) {
                    // pull towards the center of the circle
                    DroneAction.goToDestination(drone, REFRESH_RATE, FIX_SPEED, CTR_LAT, CTR_LON);
                    Util.println("pulling towards center");
                }
                else if (distanceToCenter < CTR_RADIUS - CTR_ERR_MARGIN) {
                    // repel towards the outside of the circle
                    DroneAction.repelDestination(drone, REFRESH_RATE, FIX_SPEED, CTR_LAT, CTR_LON);
                    Util.println("repel against center");
                }

                // fix the heading
                // note that heading counts from the north
                else if (intendedHeading > DroneTelemetry.getHeadingDeg(drone) + ANGLE_ERR_MARGIN || intendedHeading < DroneTelemetry.getHeadingDeg(drone) - ANGLE_ERR_MARGIN) {
                    // turning counterclockwise 
                    Util.println("fixing heading: turning counterclockwise");
                    DroneAction.sendVelocityBodyYawspeed(drone, REFRESH_RATE, 0.0f, 0.0f, 0.0f, (float) -ANGLE_FIX_INCREMENT);
                }

                // else if (intendedHeading > DroneTelemetry.getHeadingDeg(drone) + ANGLE_ERR_MARGIN) {
                //         // turning counterclockwise 
                //         Util.println("fixing heading: turning counterclockwise");
                //         DroneAction.sendVelocityBodyYawspeed(drone, REFRESH_RATE, 0.0f, 0.0f, 0.0f, (float) ANGLE_FIX_INCREMENT);
                //     }

                // else if (intendedHeading < DroneTelemetry.getHeadingDeg(drone) - ANGLE_ERR_MARGIN) {
                //     // turning clockwise
                //     Util.println("fixing heading: turning clockwise");
                //     DroneAction.sendVelocityBodyYawspeed(drone, REFRESH_RATE, 0.0f, 0.0f, 0.0f, (float) -ANGLE_FIX_INCREMENT);
                // }

                else {
                    double yawDeg = DroneTelemetry.radiansToDegree(FIX_SPEED * REFRESH_RATE / CTR_RADIUS);
                    // double yawDeg = 90 - (180 - (360 * FIX_SPEED * REFRESH_RATE) / (2 * Math.PI * CTR_RADIUS)) / 2;
                    // double yawDeg = intendedHeading - DroneTelemetry.getHeadingDeg(drone);

                    Util.println("flying spiral: " + yawDeg + " deg");

                    DroneAction.sendVelocityBodyYawspeed(drone, REFRESH_RATE,  (float) FIX_SPEED, 0.0f, (float) 0.0f, (float) yawDeg);

                }

                if (counter > RUN_CYCLE) {
                    java.lang.System.out.println("ego drone has finished execution cycle");
                    DroneAction.holdPosition(drone, REFRESH_RATE);
                    break;
                }

                counter++;
            }


            // while(true) {
            //         // fly the spiral based on velocity and R
            //         // R = V / omega
            //         // https://math.stackexchange.com/questions/296868/robot-command-translation-calculating-circle-radius-from-forward-velocity-and-y


            //         double distanceToCenter = DroneTelemetry.distance(DroneTelemetry.getLatitudeDeg(drone), DroneTelemetry.getLongitudeDeg(drone), 
            //                                     CTR_LAT, CTR_LON, 0, 0);
            //     double bearing = DroneTelemetry.bearing(DroneTelemetry.getLatitudeDeg(drone), DroneTelemetry.getLongitudeDeg(drone), 
            //                                     CTR_LAT, CTR_LON);                                

            //     double intendedHeading = bearing - 90;
            //     // add 360 degrees if it is not the intended heading
            //     if (intendedHeading < 0) {
            //         intendedHeading += 360;
            //     }

            //     Util.println("current heading: " + DroneTelemetry.getHeadingDeg(drone));
            //     Util.println("intended heading: " + intendedHeading);

            //     Util.println("distance to center: " + distanceToCenter);
            //     Util.println("counter: " + counter);
                


            //     // approximate to a triangle
            //         double yawDeg = DroneTelemetry.radiansToDegree(FIX_SPEED * REFRESH_RATE / CTR_RADIUS);
            //         // double yawDeg = 90 - (180 - (360 * FIX_SPEED * REFRESH_RATE) / (2 * Math.PI * CTR_RADIUS)) / 2;
            //         // double yawDeg = intendedHeading - DroneTelemetry.getHeadingDeg(drone);

            //         Util.println("flying spiral: " + yawDeg + " deg");

            //         DroneAction.sendVelocityBodyYawspeed(drone, REFRESH_RATE,  (float) FIX_SPEED, 0.0f, (float) 0.0f, (float) yawDeg);

            //     // get the distance bewteen ego drone and follower drone
            //     // DroneAction.clockwiseClimb(drone, REFRESH_RATE);

            //     if (counter > RUN_CYCLE) {
            //         java.lang.System.out.println("ego drone has finished execution cycle");
            //         DroneAction.holdPosition(drone, REFRESH_RATE);
            //         break;
            //     }

            //     counter++;
            // }

            // write result to file 
            logData(result.toString());
        }
    }

    private static class TelemetryMonitor implements Runnable {
        System egoDrone;
        System followerDrone;

        public TelemetryMonitor(System egoDrone) {
            this.egoDrone = egoDrone;
            this.followerDrone = followerDrone;
        }

        public static String genLatLonAltOutput(Telemetry.Position position) {
            return position.getLatitudeDeg() + ", " + position.getLongitudeDeg() + ", " + position.getRelativeAltitudeM();
        }

       
        @Override
        public void run() {
            java.lang.System.out.println("Telemetry monitor is running");

            // note that this asynchronous function is used to retrieve the telemetry data from the drone.
            // the self adaptive loop still needs blocking functions

            // the goal here is to plot the flight path of the ego drone

            // this is for getting the current position
            Flowable mySpecialAltitudeFlowable = egoDrone.getTelemetry()
                    .getPosition()
                    // .map(position -> Math.round(position.getRelativeAltitudeM()))
                    // .map(position -> position.getRelativeAltitudeM())
                    .distinctUntilChanged();
            // .filter(altitude -> altitude <= 5);

            mySpecialAltitudeFlowable.subscribe(position -> {
                        // java.lang.System.out.println("Altitude: " + ((Telemetry.Position) position).getRelativeAltitudeM());
                        // java.lang.System.out.println("Latitude: " + ((Telemetry.Position) position).getLatitudeDeg());
                        // java.lang.System.out.println("Longitude: " + ((Telemetry.Position) position).getLongitudeDeg());
                        // altitudeList.add((int) altitude);
                        String tempResult = genLatLonAltOutput((Telemetry.Position) position);
                        result.append(tempResult + "\n");
                        // java.lang.System.out.println(tempResult);
                        // logData(genLatLonAltOutput((Telemetry.Position) position));
                    });
        }
    }

    public static void main(String[] args) {
        // connect to the drones
        System drone = new System("127.0.0.1", 50051);

        // to prevent a single process from hanging indefinitely, we use parameter
        // passing of the Systems
        // instead of spawning multiple instances of the System class using same ports.
        Thread t1 = new Thread(new Controller(drone), "Ego Drone Thread");
        Thread t2 = new Thread(new TelemetryMonitor(drone), "Telemetry Monitor Thread");

        // now, let's start all three threads
        t1.start();
        t2.start();
    }
}

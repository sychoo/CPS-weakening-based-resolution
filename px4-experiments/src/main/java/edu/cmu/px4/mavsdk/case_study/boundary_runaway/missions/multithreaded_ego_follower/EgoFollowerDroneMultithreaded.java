// Note that this must be ran with the runpx4_multiple.sh script
// Multi-threaded version of Ego drone follower drone.

package edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.multithreaded_ego_follower;

import io.mavsdk.System;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.multiprocess_ego_follower.EgoDrone;
import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import edu.cmu.stl.encoder.floats.util.FileOperation;
import io.mavsdk.telemetry.Telemetry;
import io.reactivex.Flowable;
import java.util.ArrayList;

public class EgoFollowerDroneMultithreaded {
    // public static double FOLLOWER_DRONE_SPEED = 6.00; // m/s
    // public static double EGO_DRONE_SPEED = 4.00; // m/s
    // public static double EGO_DRONE_AVOID_SPEED = 8.00; // m/s

    // public static int REFRESH_RATE = 1; // Hz
    // public static int RECOVERY_TIME = 5; // cycles

    // public static double CATCH_DISTANCE = 5; // meters, the distance in which the
    // follower drone successfully catch the
    // // ego drone
    // public static double AVOID_DISTANCE = 10; // meters, the distance that
    // triggers avoidance action from ego drone

    // destination : the cut
    public static double DEST_LAT = 40.444139;
    public static double DEST_LON = -79.942639;

    public static String experimentFileDir = "experiments/results/bound_run/";
    public static String experimentFile = "experiments/results/bound_run/flight_path.csv";

    // public static ArrayList<Integer> altitudeList = new ArrayList<Integer>();
    public static StringBuilder result = new StringBuilder();

    private static class EgoDroneTask implements Runnable {
        System egoDrone;
        System followerDrone;

        public EgoDroneTask(System egoDrone, System followerDrone) {
            this.egoDrone = egoDrone;
            this.followerDrone = followerDrone;
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

            DroneAction.armAndTakeoff(egoDrone);
            DroneAction.startOffboardMode(egoDrone);

            // can be used for a good demo
            // DroneAction.clockwiseClimb(egoDrone, 20);

            while (true) {
                // get the distance bewteen ego drone and follower drone
                double distanceToFollower = DroneTelemetry.distanceBetweenDrones(egoDrone, followerDrone);

                java.lang.System.out.println("ego drone is executing.");

                // generate avoid action if the distance is too close
                if (distanceToFollower < Params.AVOID_DISTANCE) {
                    java.lang.System.out.println("ego drone is avoiding follower drone");
                    DroneAction.repelDrone(egoDrone, followerDrone, Params.REFRESH_RATE, Params.EGO_DRONE_AVOID_SPEED);
                }
                // case when to go to destination
                else {
                    java.lang.System.out.println("Continuing Mission");
                    DroneAction.goToDestination(egoDrone, Params.REFRESH_RATE, Params.EGO_DRONE_SPEED, Params.DEST_LAT,
                            Params.DEST_LON);
                }


                // terminate mission if the drone has reached destination
                if (DroneTelemetry.distanceBetweenDroneAndDest(egoDrone, Params.DEST_LAT, Params.DEST_LON) <= 50) {
                    java.lang.System.out.println("ego drone has reached destination");
                    break;
                }
            }

            // write result to file 
            logData(result.toString());
        }
    }

    private static class FollowerDroneTask implements Runnable {
        System egoDrone;
        System followerDrone;

        public FollowerDroneTask(System egoDrone, System followerDrone) {
            this.egoDrone = egoDrone;
            this.followerDrone = followerDrone;
        }

        @Override
        public void run() {

            java.lang.System.out.println(Thread.currentThread().getName() + " is executing");

            DroneAction.armAndTakeoff(followerDrone);
            DroneAction.startOffboardMode(followerDrone);

            // follower drone first finish its task
            // DroneAction.clockwiseClimb(followerDrone, 10);
            while (true) {
                java.lang.System.out.println("follower drone is running. ");

                // generate a velocity vector to the follower drone
                // when the follower drone catch the ego drone, stop for RECOVERY_TIME seconds
                DroneAction.followDrone(followerDrone, egoDrone, Params.REFRESH_RATE, Params.FOLLOWER_DRONE_SPEED,
                        Params.CATCH_DISTANCE);

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
            }

            // Test function to check the relative position
            // while(true) {
            // Telemetry.PositionNed relative_pos =
            // DroneTelemetry.getRelativeDronePosition(followerDrone, egoDrone);
            // java.lang.System.out.println("N: " + relative_pos.getNorthM() + " E: " +
            // relative_pos.getEastM() + " D: " + relative_pos.getDownM());

            // }

        }
    }

    private static class TelemetryMonitorTask implements Runnable {
        System egoDrone;
        System followerDrone;

        public TelemetryMonitorTask(System egoDrone, System followerDrone) {
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
        System egoDrone = new System("127.0.0.1", 50051);
        System followerDrone = new System("127.0.0.1", 50052); // for telemetry pulling purpose

        // to prevent a single process from hanging indefinitely, we use parameter
        // passing of the Systems
        // instead of spawning multiple instances of the System class using same ports.
        Thread t1 = new Thread(new EgoDroneTask(egoDrone, followerDrone), "Ego Drone Thread");
        Thread t2 = new Thread(new FollowerDroneTask(egoDrone, followerDrone), "Follower Drone Thread");
        Thread t3 = new Thread(new TelemetryMonitorTask(egoDrone, followerDrone), "Telemetry Monitor Thread");

        // now, let's start all three threads
        t1.start();
        t2.start();
        t3.start();
    }
}

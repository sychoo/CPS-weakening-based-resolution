// Note that this must be ran with the runpx4_multiple.sh script
// Multi-threaded version of Ego drone follower drone.

package edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.demo;

import io.mavsdk.System;
import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import io.mavsdk.telemetry.Telemetry;

public class EgoFollowerDroneMultithreadedDemo {
    public static double FOLLOWER_DRONE_SPEED = 6.00; // m/s
    public static double EGO_DRONE_SPEED = 4.00; // m/s

    public static int REFRESH_RATE = 1; // Hz
    public static int RECOVERY_TIME = 5; // cycles

    public static double CATCH_DISTANCE = 2; // meters, the distance in which the follower drone successfully catch the ego drone
    public static double AVOID_DISTANCE = 4; // meters, the distance that triggers avoidance action from ego drone

    // destination : the cut
    public static double DEST_LAT = 40.444139;
    public static double DEST_LON = -79.942639;


    private static class EgoDroneTask implements Runnable {

        @Override
        public void run() {
            // try {
            //     Thread.sleep(2000);
            // } catch (Exception e) {

            // }

            java.lang.System.out.println(Thread.currentThread().getName() + " is executing");

            System egoDrone = new System("127.0.0.1", 50051);
            System followerDrone = new System("127.0.0.1", 50052); // for telemetry pulling purpose

            DroneAction.armAndTakeoff(egoDrone);
            DroneAction.startOffboardMode(egoDrone);
            // DroneAction.sendVelocityNed(egoDrone, 10, 5, 0, 0, 0);
            // DroneAction.startOffboardModeVelocityBodyYawspeed(egoDrone);

            // Make sure the ego drone is turning clockwise and climing
            // referencing: https://github.com/mavlink/MAVSDK-Python/blob/main/examples/offboard_velocity_body.py

            // can be used for a good demo
            DroneAction.clockwiseClimb(egoDrone, 20);

            // Note that this procedure wouldn't work because of Java Thread starvation
            // uncomment this section
            // while (true) {
            //     java.lang.System.out.println("ego drone is running");
            //     // DroneAction.goToDestination(egoDrone, REFRESH_RATE, EGO_DRONE_SPEED, DEST_LAT, DEST_LON);

            //     // get the distance bewteen
            //     double distanceToFollower = DroneTelemetry.distanceBetweenDrones(egoDrone, followerDrone);

            //     // generate avoid action if the distance is too close
            //     if (distanceToFollower < AVOID_DISTANCE) {
            //         java.lang.System.out.println("ego drone is avoiding follower drone");
            //         DroneAction.repelDrone(egoDrone, followerDrone, REFRESH_RATE, EGO_DRONE_SPEED);
            //     }
            //     // case when to go to destination
            //     else {
            //         java.lang.System.out.println("Continuing Mission");
            //         DroneAction.goToDestination(egoDrone, REFRESH_RATE, EGO_DRONE_SPEED, DEST_LAT, DEST_LON);
            //     }
            // }
        }
    }

    private static class FollowerDroneTask implements Runnable {

        @Override
        public void run() {

            // delay start 
            try {
                Thread.sleep(2000);
            } catch (Exception e) {

            }

            java.lang.System.out.println(Thread.currentThread().getName() + " is executing");

            System followerDrone = new System("127.0.0.1", 50052);
            System egoDrone = new System("127.0.0.1", 50051);

            DroneAction.armAndTakeoff(followerDrone);
            DroneAction.startOffboardMode(followerDrone);

            // follower drone first finish its task
            // DroneAction.clockwiseClimb(followerDrone, 10);
            while (true) {
                java.lang.System.out.println("follower drone is running. ");

                // generate a velocity vector to the follower drone
                // when the follower drone catch the ego drone, stop for RECOVERY_TIME seconds
                DroneAction.followDrone(followerDrone, egoDrone, REFRESH_RATE, FOLLOWER_DRONE_SPEED, CATCH_DISTANCE);
            }

            // Test function to check the relative position
            // while(true) {
            //     Telemetry.PositionNed relative_pos = DroneTelemetry.getRelativeDronePosition(followerDrone, egoDrone);
            //     java.lang.System.out.println("N: " + relative_pos.getNorthM() + " E: " + relative_pos.getEastM() + " D: " + relative_pos.getDownM());

            //     try {
            //         Thread.sleep(1000);
            //     } catch (Exception e) {
    
            //     }
            // }

        }
    }

    public static void main(String[] args) {

        // created three threads but none will start until you call
        // start() method
        Thread t1 = new Thread(new EgoDroneTask(), "Ego Drone Thread");
        Thread t2 = new Thread(new FollowerDroneTask(), "Follower Drone Thread");

        // now, let's start all three threads
        t1.start();
        t2.start();
    }
}

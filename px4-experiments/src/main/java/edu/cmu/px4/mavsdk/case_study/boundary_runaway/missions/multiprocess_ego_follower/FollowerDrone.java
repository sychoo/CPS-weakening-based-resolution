package edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.multiprocess_ego_follower;

import io.mavsdk.System;
import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import io.mavsdk.telemetry.Telemetry;

public class FollowerDrone {
   
    public static void main(String[] args) {

        java.lang.System.out.println("Follower drone process is executing");

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
            DroneAction.followDrone(followerDrone, egoDrone, Params.REFRESH_RATE, Params.FOLLOWER_DRONE_SPEED, Params.CATCH_DISTANCE);
        }
    }
}

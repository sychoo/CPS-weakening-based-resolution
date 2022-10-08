package edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.multiprocess_ego_follower;

import io.mavsdk.System;
import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import io.mavsdk.telemetry.Telemetry;

public class EgoDrone {
    public static void main(String[] args) {

        java.lang.System.out.println("Ego drone process is executing");

        System egoDrone = new System("127.0.0.1", 50051);
        System followerDrone = new System("127.0.0.1", 50052); // for telemetry pulling purpose

        DroneAction.armAndTakeoff(egoDrone);
        DroneAction.startOffboardMode(egoDrone);
        // DroneAction.sendVelocityNed(egoDrone, 10, 5, 0, 0, 0);
        // DroneAction.startOffboardModeVelocityBodyYawspeed(egoDrone);

        // Make sure the ego drone is turning clockwise and climing
        // referencing:
        // https://github.com/mavlink/MAVSDK-Python/blob/main/examples/offboard_velocity_body.py

        // can be used for a good demo
        // DroneAction.clockwiseClimb(egoDrone, 20);

        // Note that this procedure wouldn't work because of Java Thread starvation
        // uncomment this section
        while (true) {
            java.lang.System.out.println("ego drone is running");
            // DroneAction.goToDestination(egoDrone, REFRESH_RATE, EGO_DRONE_SPEED,
            // DEST_LAT, DEST_LON);

            // get the distance bewteen
            double distanceToFollower = DroneTelemetry.distanceBetweenDrones(egoDrone, followerDrone);

            // generate avoid action if the distance is too close
            if (distanceToFollower < Params.AVOID_DISTANCE) {
                java.lang.System.out.println("ego drone is avoiding follower drone");
                DroneAction.repelDrone(egoDrone, followerDrone, Params.REFRESH_RATE, Params.EGO_DRONE_SPEED);
            }
            // case when to go to destination
            else {
                java.lang.System.out.println("Continuing Mission");
                DroneAction.goToDestination(egoDrone, Params.REFRESH_RATE, Params.EGO_DRONE_SPEED, Params.DEST_LAT,
                        Params.DEST_LON);
            }
        }
    }
}

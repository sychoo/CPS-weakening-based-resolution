package edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.singlethreaded_ego_follower;

import io.mavsdk.System;

import javax.security.auth.RefreshFailedException;

import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import io.mavsdk.telemetry.Telemetry;

public class EgoFollowerSinglethreaded {
    // indicates how many remaining needs to be waited for the follower drone.
    public static int RECOVERY_CYCLE_VAR = 0; 

    public static void run() {
        // connect both ego drone and follower drone
        System egoDrone = new System("127.0.0.1", 50051);
        System followerDrone = new System("127.0.0.1", 50052); // for telemetry pulling purpose

        // initialize the drones
        DroneAction.armAndTakeoff(egoDrone);
        DroneAction.armAndTakeoff(followerDrone);

        DroneAction.startOffboardMode(egoDrone);
        DroneAction.startOffboardMode(followerDrone);

        while (true) {
            
            /****************************/
            /****** follower drone ******/
            /****************************/
            java.lang.System.out.println("follower drone is executing.");

            // follower drone not in a hold position mode
            if (RECOVERY_CYCLE_VAR == 0) {
                boolean egoDroneIsCaught = DroneAction.followDrone(followerDrone, egoDrone, Params.REFRESH_RATE, Params.FOLLOWER_DRONE_SPEED, Params.CATCH_DISTANCE);
                if (egoDroneIsCaught) {
                    java.lang.System.out.println("follower drone is caught the ego drone.");
                    RECOVERY_CYCLE_VAR = Params.RECOVERY_CYCLE;
                }
            } else {
                --RECOVERY_CYCLE_VAR;
                DroneAction.holdPosition(followerDrone, Params.REFRESH_RATE);
            }

            /***********************/
            /****** ego drone ******/
            /***********************/
            
            java.lang.System.out.println("ego drone is executing.");

            // get the distance bewteen ego drone and follower drone
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
    public static void main(String[] args) {
        java.lang.System.out.println("Ego follower process (single-threaded) is executing");
        run();
    }
}

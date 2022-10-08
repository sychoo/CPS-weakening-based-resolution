package edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.drone;
import io.mavsdk.System;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.multiprocess_ego_follower.EgoDrone;
import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.Util;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import edu.cmu.stl.encoder.floats.util.FileOperation;
import io.mavsdk.telemetry.Telemetry;
import io.reactivex.Flowable;
import java.util.ArrayList;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.Params;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.monitor.Monitor;

public class FollowerDrone implements Runnable {
    System egoDrone;
    System followerDrone;

    public FollowerDrone(System egoDrone, System followerDrone) {
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

            boolean egoDroneIsCaught = DroneAction.followDrone(followerDrone, egoDrone, Params.REFRESH_RATE, Params.FOLLOWER_DRONE_MAX_SPEED,
                    Params.FOLLOWER_CATCH_DISTANCE);

            if (egoDroneIsCaught) {
                Monitor.follower_caught_ego_counter += 1;

                int counter = 0;
                while (counter < Params.FOLLOWER_RECOVERY_CYCLE) {
                    Util.println("follower drone is recovering. ");
                    DroneAction.holdPosition(followerDrone, Params.REFRESH_RATE);
                    counter += 1;
                }
            }
            // try {
            //     Thread.sleep(1000);
            // } catch (Exception e) {

            // }
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
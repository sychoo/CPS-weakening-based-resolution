package edu.cmu.px4.mavsdk.case_study.terrain_map_ground_prox;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import io.mavsdk.System;
import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.stl.encoder.floats.STLEncoder;
import edu.cmu.stl.encoder.floats.util.Result;
import java.util.ArrayList;

public class TmgpMain {
    // return velocity down
    public static float TerrainMapping(System drone) {
        // get the current altitude of the drone
        float altitude = DroneTelemetry.getAltitude(drone);
        if (altitude >= 13.0) {
            return 0.0f;
        } else {
            return altitude-13.0f;
        }
    }

    public static float GroundProximity(System drone) {
        // get the current altitude of the drone
        float altitude = DroneTelemetry.getAltitude(drone);
        if (altitude <= 10.0) {
            return 0.0f;
        } else {
            return altitude-10.0f;
        }
    }

    public static String testDir = "src/main/java/edu/cmu/px4/mavsdk/case_study/terrain_map_ground_prox/";

    public static void main(String[] args) {

        System drone = new System("127.0.0.1", 50051);

        DroneAction.armAndTakeoff(drone);
        DroneAction.startOffboardMode(drone);


        while (!DroneTelemetry.isLanded(drone)) {

            java.lang.System.out.println("Altitude: " + DroneTelemetry.getAltitude(drone));

            float tmAction = TerrainMapping(drone);
            float gpAction = GroundProximity(drone);

            if (tmAction == 0.0f && gpAction == 0.0f) {
                // do nothing
            } else if (tmAction != 0.0f && gpAction == 0.0f) {
                DroneAction.sendVelocityNed(drone, 1, 0.0f, 0.0f, tmAction, 0.0f);
                java.lang.System.out.println("tmAction: " + tmAction);
            } else if (tmAction == 0.0f && gpAction != 0.0f) {
                java.lang.System.out.println("gpAction: " + gpAction);
                DroneAction.sendVelocityNed(drone, 1, 0.0f, 0.0f, gpAction, 0.0f);
            } else {

                // resolve, controller override
                // run MiniZinc model
                String spec = testDir + "tmgp.stl";
                String env = testDir + "tmgp.mzn";

                STLEncoder e = new STLEncoder(spec, env);
                Result r = e.run(String.format("constraint sig_var_d2g[1] = %f;", DroneTelemetry.getAltitude(drone)));

                // extract result 
                ArrayList<Float> actionList = Result.parseFloatArray(r.get("action_vel_down"));
                Float resolvedAction = actionList.get(0);
                DroneAction.sendVelocityNed(drone, 1, 0.0f, 0.0f, resolvedAction, 0.0f);

                java.lang.System.out.println("conflict detected " + resolvedAction);

            }
        }
    }
}

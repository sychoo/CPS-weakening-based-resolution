package edu.cmu.px4.mavsdk.case_study.organ_delivery.main.resolver;

import edu.cmu.px4.mavsdk.utils.Plan;
import edu.cmu.stl.encoder.floats.STLEncoder;
import edu.cmu.stl.encoder.floats.util.Result;
import edu.cmu.px4.mavsdk.case_study.organ_delivery.main.BatteryController;
import edu.cmu.px4.mavsdk.case_study.organ_delivery.main.DroneController;
import edu.cmu.px4.mavsdk.case_study.organ_delivery.main.Params;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import java.util.ArrayList;
import edu.cmu.stl.encoder.floats.util.FileOperation;
// import edu.cmu.px4.mavsdk.case_study.organ_delivery.main.monitor.Monitor;
import io.mavsdk.System;
import edu.cmu.px4.mavsdk.utils.Util;

public class Resolver {

    // public static String testDir = "src/main/java/edu/cmu/px4/mavsdk/case_study/organ_delivery/main/model/";
    public static STLEncoder encoder = null;

    public static String gatherParams(double lat, double lon) {
        StringBuilder params = new StringBuilder();
        /*****************************/
        /***** static parameters *****/
        /*****************************/

        // FOLLOWER_DRONE_MAX_SPEED
        // EGO_AVOID_SPEED
        // REFRESH_RATE
        Params p = new Params();
        params.append(p.declareMiniZincParams());

        /******************************/
        /***** runtime parameters *****/
        /******************************/

        // get the mission time left for the drone
        double missionTimeLeft = DroneController.missionTimeLeft;
        params.append("constraint sig_var_mission_time_left[1] = " + missionTimeLeft + ";\n");

        // get current location of the drone
        params.append("constraint loc_x[1] = 0.0;\n");
        params.append("constraint loc_y[1] = 0.0;\n");

        // get the current battery level of the drone
        params.append("constraint sig_var_battery[1] = " + BatteryController.getBatteryPercentage() + ";\n");

        ArrayList<Double> destinationPos = DroneTelemetry.relativePosition(lat, lon, Params.DESTINATION_LOCATION_LAT, Params.DESTINATION_LOCATION_LON);
        Util.println("destination position: " + destinationPos);
        params.append("float: DEST_X = " + destinationPos.get(0) + ";\n");
        params.append("float: DEST_Y = " + destinationPos.get(1) + ";\n");

        return params.toString();
    }

    public static void initializeSTLEncoder() {
            // String stl = testDir + "runbound.stl";
            String stl = Params.MAIN_STL_FILE_FOR_RESOLVER;
            String env = Params.MAIN_ENVIRONMENTAL_MODEL_FILE_FOR_RESOLVER;
            encoder = new STLEncoder(stl, env);
    }

    // run the resolver
    public static Plan run(double drone_lat, double drone_lon) {
        if (encoder == null) {
            initializeSTLEncoder();
        }

        // calculate the relative coordinates between the ego drone and the follower drone
        String params = gatherParams(drone_lat, drone_lon);

        // TODO: set up the STLEncoder files and run the solver
        Result r = encoder.run(params);

        // case when the solver succeeds
        if (r.isSat()) {
            // 1 - keep flying
            // 0 - land
            double action = Result.parseIntArray(r.get("action")).get(0);

            // extract the east speed
            double vel_x = Result.parseFloatArray(r.get("vel_x")).get(0);

            // extract the north speed
            double vel_y = Result.parseFloatArray(r.get("vel_y")).get(0);

            ArrayList<Double> vel = new ArrayList<Double>() {{
                add(vel_x); // east speed
                add(vel_y); // north speed
            }};

            // append the weakening paramete to the monitor
            // delta_bound and delta_runaway
            // Monitor.boundary_requirement_weakening_param_list.add(Double.valueOf(r.get("delta_bound")));
            // Monitor.runaway_requirement_weakening_param_list.add(Double.valueOf(r.get("delta_runaway")));

            if (action == 1) {
                return new Plan("fly", vel);
            } else if (action == 0) {
                return new Plan("land", null);
            } else {
                Util.println("ERROR: action is not 0 or 1");
                return new Plan("unsat", null);
            }
        } else {
            return new Plan("unsat", null);
        }
    }

    // entry point of the resolver program from the DroneController program 
    // extract the current location of drone
    public static Plan run(System drone) {
        ArrayList<Double> dronePos = DroneTelemetry.getPositionList(drone);
        return run(dronePos.get(0), dronePos.get(1));
    }

    public static void main(String[] args) {
    //     // [[40.445131578821034, -79.94760109847633], [40.445131578821034, -79.94701090152367], [40.44468242117897, -79.94701090152367], [40.44468242117897, -79.94760109847633]]

    //     System.out.println(gatherParams(40.445131578821034, -79.94760109847633, 10, 40.445131578821034, -79.94701090152367, 10));
    //     System.out.println(run(40.445131578821034, -79.94760109847633, 10, 40.445131578821034, -79.94701090152367, 10));
    }
}

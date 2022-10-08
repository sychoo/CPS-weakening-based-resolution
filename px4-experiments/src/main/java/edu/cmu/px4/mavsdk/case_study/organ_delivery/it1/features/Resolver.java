package edu.cmu.px4.mavsdk.case_study.organ_delivery.it1.features;

import io.mavsdk.System;
import io.mavsdk.telemetry.Telemetry;

import java.util.ArrayList;
import edu.cmu.stl.encoder.floats.STLEncoder;
import edu.cmu.stl.encoder.floats.util.Result;
import edu.cmu.px4.mavsdk.case_study.organ_delivery.it1.OrganDeliveryMain;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;

public class Resolver {
    public static String testDir = "src/main/java/edu/cmu/px4/mavsdk/case_study/organ_delivery/it1/env_model/";

    public static void resolve(System drone) {
        // STL requirements
        String stl = testDir + "dpsl.stl";

        // environmental model for delivery planning feature
        String dp_env = testDir + "delivery_planning.mzn";

        // environmental model for safe landing feature
        String sl_env = testDir + "safe_landing.mzn";

        ArrayList<String> env_list = new ArrayList<String>() {
            {
                add(dp_env);
                add(sl_env);
            }
        };

        STLEncoder e = new STLEncoder(stl, env_list);

        // runtime parameters
        // current flying state, drone status (0: stationary, landed 1: flying)
        // current battery level
        // String param = String.format("constraint sig_var_battery[1] = %f", ) 100.0;"
        // + "\n" + // intial battery
        // "constraint sig_var_drone_status[1] = 0;" + "\n" + // initial drone status
        // (landedState 0-> in air 1 -> landing 2 -> on ground)
        // "float: DEST_X = 10.0;" + "\n" + // destination X (relative to starting
        // point)
        // "float: DEST_Y = 10.0;" + "\n"; // destination Y

        String param = generateRuntimeParameter(drone);
        Result r = e.run(param);

        // java.lang.System.out.println("Result:");
        // java.lang.System.out.println(r);

        if (r.isSat()) {
            java.lang.System.out.println(r.get("rho_stl_1"));
            java.lang.System.out.println(r.get("delta_battery"));
            SafeLanding.LANDING_THRESHOLD_WEAKENING = Double.parseDouble(r.get("delta_battery"));
            java.lang.System.out.println("WEAKENING has been set to = " + SafeLanding.LANDING_THRESHOLD_WEAKENING);
            java.lang.System.out.println("sig_var_drone_status = " + r.get("sig_var_drone_status"));
        }
    }

    public static String generateRuntimeParameter(System drone) {
        // String param = "constraint sig_var_battery[1] = 100.0;" + "\n" + // intial
        // battery
        // "constraint sig_var_drone_status[1] = 0;" + "\n" + // initial drone status
        // (landedState 0-> in air 1 -> landing 2 -> on ground)
        // "float: DEST_X = 10.0;" + "\n" + // destination X (relative to starting
        // point)
        // "float: DEST_Y = 10.0;" + "\n"; // destination Y

        StringBuilder sb = new StringBuilder();

        // current drone battery
        sb.append(
                String.format("constraint sig_var_battery[1] = %f;", DroneTelemetry.getRemainingBatteryPercent(drone)));
        sb.append("\n");

        // current drone flight status
        int statusNum = 0;
        Telemetry.LandedState droneStatus = DroneTelemetry.getLandedState(drone);
        if (droneStatus == Telemetry.LandedState.LANDING) {
            statusNum = 1;
        } else if (droneStatus == Telemetry.LandedState.ON_GROUND) {
            statusNum = 2;
        } else {
            // IN_AIR
            statusNum = 0;
        }

        sb.append(String.format("constraint sig_var_drone_status[1] = %d;", statusNum));
        sb.append("\n");

        // current location
        double lat1 = DroneTelemetry.getLatitudeDeg(drone);
        double lon1 = DroneTelemetry.getLongitudeDeg(drone);

        // destination
        double lat2 = OrganDeliveryMain.lat;
        double lon2 = OrganDeliveryMain.lon;

        ArrayList<Double> relativePosition = DroneTelemetry.relativePosition(lat1, lon1, lat2, lon2);

        sb.append(String.format("float: DEST_X = %f;", relativePosition.get(0)));
        sb.append("\n");

        sb.append(String.format("float: DEST_Y = %f;", relativePosition.get(1)));
        sb.append("\n");

        return sb.toString();
    }
}

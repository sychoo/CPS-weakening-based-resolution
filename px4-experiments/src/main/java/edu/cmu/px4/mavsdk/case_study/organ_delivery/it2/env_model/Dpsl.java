// run small scale tests
// test the STLEncoder
package edu.cmu.px4.mavsdk.case_study.organ_delivery.it2.env_model;

import edu.cmu.stl.encoder.floats.STLEncoder;
import edu.cmu.stl.encoder.floats.util.Result;

import java.util.ArrayList;

// initialize STLEncoder with parameter passing
public class Dpsl {

    public static String testDir = "src/main/java/edu/cmu/px4/mavsdk/case_study/organ_delivery/it2/env_model/";

    public static void run() {
        // STL requirements
        String stl = testDir + "dpsl.stl";

        // environmental model for delivery planning feature
        String dp_env = testDir + "delivery_planning.mzn";

        // environmental model for safe landing feature
        String sl_env = testDir + "safe_landing.mzn";

        ArrayList<String> env_list = new ArrayList<String>() {{
            add(dp_env);
            add(sl_env);
        }};

        
        STLEncoder e = new STLEncoder(stl, env_list);

        // runtime parameters
        // current flying state, drone status (0: stationary, landed 1: flying)
        // current battery level
        String param =  "constraint sig_var_battery[1] = 100.0;" + "\n" + // intial battery
                        "constraint sig_var_drone_status[1] = 0;" + "\n" + // initial drone status (landedState 0-> in air 1 -> landing 2 -> on ground)
                        "float: DEST_X = 10.0;" + "\n" + // destination X (relative to starting point)
                        "float: DEST_Y = 10.0;" + "\n"; // destination Y

        Result r = e.run(param);


        System.out.println("Result:");
        System.out.println(r);
        System.out.println(r.get("rho_stl_1"));
        System.out.println(r.get("delta_battery"));
        // assert(r.get("rho_stl_1").equals("-100.0"));
    }

    public static void main(String[] args) {
        run();
    }
}

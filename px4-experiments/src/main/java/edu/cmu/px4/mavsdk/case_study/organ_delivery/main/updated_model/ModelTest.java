// Sun Sep 11 19:09:53 EDT 2022

package edu.cmu.px4.mavsdk.case_study.organ_delivery.main.updated_model;

import edu.cmu.stl.encoder.floats.STLEncoder;
import edu.cmu.stl.encoder.floats.util.Result;
import java.util.ArrayList;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.Params;

public class ModelTest {

// initialize STLEncoder with parameter passing

    public static String testDir = "src/main/java/edu/cmu/px4/mavsdk/case_study/organ_delivery/main/updated_model/";

    public static void runSimpleModel() {
        String stl = testDir + "dpsl.stl";
        // String stl = testDir + "dp_original.stl";
        
        String env = testDir + "simple_model.mzn";

        STLEncoder e = new STLEncoder(stl, env);

        long cycleStartTime = java.lang.System.currentTimeMillis(); // measured in miliseconds
        Result r = e.run("");
        long cycleEndTime = java.lang.System.currentTimeMillis(); // measured in miliseconds

        double cycleDuration = (cycleEndTime - cycleStartTime) / 1000.0; // measured in seconds
        System.out.println("time take to resolve (seconds)" + cycleDuration);
        System.out.println(r);
    }

    public static void run() {
        // STL requirements
        String stl = testDir + "runbound.stl";

        // physics
        String physics = testDir + "physical_dynamics.mzn";

        // environmental model for delivery planning feature
        String bound_env = testDir + "boundary_enforcer.mzn";

        // environmental model for safe landing feature
        String run_env = testDir + "runaway_enforcer.mzn";

        ArrayList<String> env_list = new ArrayList<String>() {{
            add(physics);
            add(bound_env);
            add(run_env);
        }};

        STLEncoder e = new STLEncoder(stl, env_list);

        // runtime parameters
        // current flying state, drone status (0: stationary, landed 1: flying)
        // current battery level

        // String param =  "constraint sig_var_battery[1] = 100.0;" + "\n" + // intial battery
        //                 "constraint sig_var_drone_status[1] = 0;" + "\n" + // initial drone status (landedState 0-> in air 1 -> landing 2 -> on ground)
        //                 "float: DEST_X = 10.0;" + "\n" + // destination X (relative to starting point)
        //                 "float: DEST_Y = 10.0;" + "\n"; // destination Y

        String param = "";
        Params p = new Params();
        param += p.declareMiniZincParams(); // include minizinc parameters


        long cycleStartTime = java.lang.System.currentTimeMillis(); // measured in miliseconds

        Result r = e.run(param);

        long cycleEndTime = java.lang.System.currentTimeMillis(); // measured in miliseconds

        double cycleDuration = (cycleEndTime - cycleStartTime) / 1000.0; // measured in seconds
        System.out.println("time take to resolve (seconds)" + cycleDuration);

        System.out.println("Result:");
        System.out.println(r);

        System.out.println(r.get("rho_stl_1"));
        System.out.println(r.get("delta_battery"));
        assert(r.get("rho_stl_1").equals("-100.0"));
    }

    public static void main(String[] args) {
        runSimpleModel();
        // run();
    }
}

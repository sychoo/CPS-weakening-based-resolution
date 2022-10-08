package edu.cmu.px4.mavsdk.case_study.terrain_map_ground_prox;
import edu.cmu.stl.encoder.floats.STLEncoder;
import edu.cmu.stl.encoder.floats.util.Result;

// The only signal of this case study is d2g (distance to ground)
public class TmgpTest {

// initialize STLEncoder with parameter passing

    public static String testDir = "src/main/java/edu/cmu/px4/mavsdk/case_study/terrain_map_ground_prox/";

    public static void run() {
        String spec = testDir + "tmgp.stl";
        String env = testDir + "tmgp.mzn";

        STLEncoder e = new STLEncoder(spec, env);
        Result r = e.run("constraint sig_var_d2g[1] = 20.0;");

        System.out.println("Result:");
        System.out.println(r);
        // System.out.println(r.get("rho_stl_1"));
        System.out.println(Result.parseFloatArray(r.get("action_vel_down")));
        // System.out.println(Result.parseIntArray("[]"));
        // assert(r.get("rho_stl_1").equals("-100.0"));
    }

    public static void main(String[] args) {
        run();
    }
}

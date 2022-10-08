// run small scale tests
// test the STLEncoder
package edu.cmu.stl.encoder.floats.examples.battery;

import edu.cmu.stl.encoder.floats.STLEncoder;
import edu.cmu.stl.encoder.floats.util.Result;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

// initialize STLEncoder with parameter passing
public class BatteryTest {

    public static String testDir = "src/main/java/edu/cmu/stl/encoder/floats/examples/";

    @Test
    public void run() {
        String battery_stl = testDir + "battery/battery.stl";
        String battery_env = testDir + "battery/battery.mzn";

        STLEncoder e = new STLEncoder(battery_stl, battery_env);
        Result r = e.run("constraint sig_var_battery[1] = 100.0;");

        System.out.println("Result:");
        System.out.println(r);
        System.out.println(r.get("rho_stl_1"));

        // final robustness
        assertTrue(r.get("rho_stl_1").equals("-10.0"));
        assertTrue(r.get("delta_battery").equals("10.000001"));
    }
}

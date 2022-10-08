// Sat Feb 19 14:06:46 EST 2022
// Simon Chu
package edu.cmu.stl.encoder.ints.util;

import edu.cmu.stl.encoder.ints.util.Result;
import edu.cmu.stl.encoder.ints.main.Main;
import edu.cmu.stl.encoder.ints.util.Signal;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

public class MainTest {
    public static String testDir = "src/test/java/edu/cmu/stl/encoder/ints/test/";

    @Test
    public void testMain() {

        System.out.println("Start Main Test Module...");

        Result r1 = Main.run(testDir + "stl/single_ap/simple_comp.stl", testDir + "signal/0.sig");
        assert (r1.get("rho_stl_1").equals("7"));

        Result r2 = Main.run(testDir + "stl/single_ap/simple_comp_normalization_1.stl", testDir + "signal/0.sig");
        assert (r2.get("rho_stl_1").equals("6"));

        Result r3 = Main.run(testDir + "stl/single_ap/simple_comp_normalization_2.stl", testDir + "signal/0.sig");
        assert (r3.get("rho_stl_1").equals("3"));

        Result r4 = Main.run(testDir + "stl/single_ap/complex_arith_1.stl", testDir + "signal/0.sig");
        assert (r4.get("rho_stl_1").equals("8"));

        Result r5 = Main.run(testDir + "stl/single_ap/complex_arith_2.stl", testDir + "signal/0.sig");
        assert (r5.get("rho_stl_1").equals("-8"));
        
        Result r6 = Main.run(testDir + "stl/logic_expr/simple_logic_expr_1.stl", testDir + "signal/1.sig");
        assert (r6.get("rho_stl_1").equals("0"));

        Result r7 = Main.run(testDir + "stl/logic_expr/multiple_logic_expr_1.stl", testDir + "signal/1.sig");
        assert (r7.get("rho_stl_1").equals("0"));

        Result r8 = Main.run(testDir + "stl/logic_expr/multiple_logic_expr_2.stl", testDir + "signal/1.sig");
        assert (r8.get("rho_stl_1").equals("-2"));
        
        Result r9 = Main.run(testDir + "stl/logic_expr/multiple_logic_expr_3.stl", testDir + "signal/1.sig");
        assert (r9.get("rho_stl_1").equals("-3"));

        Result r10 = Main.run(testDir + "stl/globally/g_multiple_logic_expr_1.stl", testDir + "signal/1.sig");
        assert (r10.get("rho_stl_1").equals("-9"));

        Result r11 = Main.run(testDir + "stl/globally/g_simple_logic_expr_1.stl", testDir + "signal/1.sig");
        assert (r11.get("rho_stl_1").equals("6"));

        Result r12 = Main.run(testDir + "stl/globally/g_ap_simple_comp_1.stl", testDir + "signal/1.sig");
        assert (r12.get("rho_stl_1").equals("-9"));

        Result r13 = Main.run(testDir + "stl/globally/g_ap_simple_comp_1.stl", testDir + "signal/1.sig");
        assert (r13.get("rho_stl_1").equals("-9"));

        Result r16 = Main.run(testDir + "stl/eventually/f_ap_simple_comp_1.stl", testDir + "signal/1.sig");
        assert (r16.get("rho_stl_1").equals("-5"));

        Result r17 = Main.run(testDir + "stl/logic_expr/simple_logic_expr_3.stl", testDir + "signal/1.sig");
        assert (r17.get("rho_stl_1").equals("1"));

        Result r18 = Main.run(testDir + "stl/globally/g_multiple_logic_expr_2.stl", testDir + "signal/2.sig");
        assert (r18.get("rho_stl_1").equals("9"));
        
        Result r19 = Main.run(testDir + "stl/eventually/f_multiple_logic_expr_1.stl", testDir + "signal/1.sig");
        assert (r19.get("rho_stl_1").equals("-5"));

        // DOES NOT SUPPORT NESTED STL EXPR AS OF NOW
        Result r14 = Main.run(testDir + "stl/nest_temp/nest_g_simple_comp_1.stl", testDir + "signal/2.sig");
        assert (r14.get("rho_stl_1").equals("-13"));

        Result r15 = Main.run(testDir + "stl/nest_temp/nest_g_simple_comp_2.stl", testDir + "signal/2.sig");
        assert (r15.get("rho_stl_1").equals("-15"));

        // r20
        Result r20 = Main.run(testDir + "stl/nest_temp/nest_g_f_simple_comp_1.stl", testDir + "signal/2.sig");
        assert (r20.get("rho_stl_1").equals("-9"));

        // conflict resolution, 1 requirements
        // r21
        Result r21 = Main.run(testDir + "stl_res/1_req/simple_res.stl", testDir + "signal/0.sig");
        assert (r21.get("rho_stl_1").equals("-3"));
        assert (r21.get("rho_stl_1_w").equals("1"));
        assert (r21.get("delta_x").equals("4"));

        Result r22 = Main.run(testDir + "stl_res/1_req/simple_G_res.stl", testDir + "signal/0.sig");
        assert (r22.get("rho_stl_1").equals("-3"));
        assert (r22.get("rho_stl_1_w").equals("1"));
        assert (r22.get("delta_x").equals("4"));

        // additional globally test
        Result r23 = Main.run(testDir + "stl/globally/g_ap_simple_comp_1_non_0_start.stl", testDir + "signal/1.sig");
        assert (r23.get("rho_stl_1").equals("-7"));

        // not, implication, and iff logical operator
        Result r24 = Main.run(testDir + "stl/single_ap/simple_comp_negation.stl", testDir + "signal/0.sig");
        assert (r24.get("rho_stl_1").equals("-7"));

        Result r25 = Main.run(testDir + "stl/single_ap/simple_comp_implication.stl", testDir + "signal/0.sig");
        assert (r25.get("rho_stl_1").equals("8"));

        Result r26 = Main.run(testDir + "stl/single_ap/simple_comp_iff.stl", testDir + "signal/0.sig");
        assert (r26.get("rho_stl_1").equals("7"));
        
        Result r27 = Main.run(testDir + "stl/globally/not_g.stl", testDir + "signal/1.sig");
        assert (r27.get("rho_stl_1").equals("-5"));
        
        Result r28 = Main.run(testDir + "stl/eventually/not_f.stl", testDir + "signal/1.sig");
        assert (r28.get("rho_stl_1").equals("-9"));

        // weakening of temporal operator G time bound (right side)
        Result r29 = Main.run(testDir + "stl_res/1_req/simple_G_res_timebound_1.stl", testDir + "signal/2.sig");
        assert (r29.get("rho_stl_1").equals("-4"));
        assert (r29.get("delta_time_r").equals("3"));
        assert (r29.get("rho_stl_1_w").equals("2"));
        
        // weakening of temporal operator G time bound (left side)
        Result r30 = Main.run(testDir + "stl_res/1_req/simple_G_res_timebound_2.stl", testDir + "signal/2.sig");
        assert (r30.get("rho_stl_1").equals("-4"));
        assert (r30.get("delta_time_l").equals("3"));
        assert (r30.get("rho_stl_1_w").equals("2"));

        // weakening of temporal operator G time bound (both side)
        // Result r31 = Main.run(testDir + "stl_res/1_req/simple_G_res_timebound_3.stl", testDir + "signal/2.sig");
        // System.out.println(r31.get("rho_stl_1").equals("-2"));
        // assert (r31.get("rho_stl_1").equals("-2"));
        // assert (r31.get("delta_time_l").equals("2"));
        // assert (r31.get("delta_time_r").equals("2"));
        // assert (r31.get("rho_stl_1_w").equals("2"));

        // weakening of temporal operator F time bound (right side)

        Result r32 = Main.run(testDir + "stl_res/1_req/simple_F_res_timebound_1.stl", testDir + "signal/2.sig");
        assert (r32.get("rho_stl_1").equals("-5"));
        assert (r32.get("delta_time_r").equals("3"));
        assert (r32.get("rho_stl_1_w").equals("1"));

        // weakening of temporal operator F time bound (left side)

        Result r33 = Main.run(testDir + "stl_res/1_req/simple_F_res_timebound_2.stl", testDir + "signal/2.sig");
        assert (r33.get("rho_stl_1").equals("-3"));
        assert (r33.get("delta_time_l").equals("2"));
        assert (r33.get("rho_stl_1_w").equals("1"));


        // weakening of temporal operator F time bound (both side)
        // Result r34 = Main.run(testDir + "stl_res/1_req/simple_F_res_timebound_3.stl", testDir + "signal/2.sig");
        // assert (r34.get("rho_stl_1").equals("-2"));
        // assert (r34.get("delta_time_l").equals("1"));
        // assert (r34.get("delta_time_r").equals("1"));
        // assert (r34.get("rho_stl_1_w").equals("0"));

        // test the signal object passed into Main.run
        // @constructor construct the signal 0.sig
        ArrayList<String> fieldNames = new ArrayList<String>() {{
                add("x");
                add("y");
            }};

        Signal s = new Signal(fieldNames);

        ArrayList<Double> state1 = new ArrayList<Double>(Arrays.asList(
                        7.0,
                        8.0));
        ArrayList<Double> state2 = new ArrayList<Double>(Arrays.asList(
                        9.0,
                        10.0));

        // @append @toString
        s.append(state1);
        s.append(state2);
        Result r35 = Main.run(testDir + "stl/single_ap/simple_comp.stl", s);
        assert (r35.get("rho_stl_1").equals("7"));

        // // test equality operator "="
        Result r36 = Main.run(testDir + "stl/single_ap/simple_comp_equal.stl", testDir + "signal/0.sig");
        assert (r36.get("rho_stl_1").equals("-7"));

        Result r37 = Main.run(testDir + "stl/single_ap/battery.stl", testDir + "signal/battery.sig");

        System.out.println();
        System.out.println("End Main Test Module...");
        System.out.println("Test Succeeded!\n");
    }
}
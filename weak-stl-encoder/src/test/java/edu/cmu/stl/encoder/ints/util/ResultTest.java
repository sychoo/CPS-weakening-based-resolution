package edu.cmu.stl.encoder.ints.util;
// Sat Feb 19 10:35:34 EST 2022

// Simon Chu

// command to obtain json-formatted results
// minizinc cache1.mzn --output-mode json  
import org.junit.Test;

public class ResultTest {
    // sample SAT result from MiniZinc solver
    // {
    // "rho" : 7
    // }
    // ----------

    // sample UNSAT result from MiniZinc solver
    // =====UNSATISFIABLE=====

    @Test
    public void test() {
        // setDefaultAssertionStatus(true);

        Result r1 = new Result("=====UNSATISFIABLE=====");
        System.out.println("Raw String: " + r1);
        System.out.println("Getting `rho` key from UNSAT array");
        System.out.println(r1.get("rho"));
        assert (r1.get("rho").equals("UNSAT"));
        assert (!r1.isSat());
        System.out.println();

        Result r2 = new Result("{ \"rho\" : 7 }\n----------");
        System.out.println("Raw String: " + r2);
        System.out.println("Getting `rho` key from normal array");
        System.out.println(r2.get("rho"));
        assert (r2.get("rho").equals("7"));
        assert (r2.isSat());
        System.out.println();

        Result r3 = new Result(
                "{\"rho\" : -11, \"p_sig_var\" : [[0, 1], [1, 0], [1, 0], [1, 0], [0, 1], [1, 0]], \"rho_sub\" : [-1, 1, 2, 3, -1, -11], \"p\" : [0, 0, 0, 0, 0, 1]}\n----------");
        System.out.println("Raw String: " + r3);
        System.out.println("Getting `rho` key from normal array");
        System.out.println(r3.get("rho"));
        System.out.println("Getting `p` key from the array");
        System.out.println(r3.get("p"));
        assert (r3.get("rho").equals("-11"));
        assert (r3.get("p").equals("[0,0,0,0,0,1]"));
        assert (r3.isSat());
        System.out.println();

        // Result r4 - new Result("rho = -3; rho_original = -3;
        // delta_x = 0;
        // ----------
        // ==========")
        // }
    }
}
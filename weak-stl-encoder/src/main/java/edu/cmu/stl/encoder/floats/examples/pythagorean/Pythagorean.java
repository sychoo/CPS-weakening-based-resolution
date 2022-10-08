package edu.cmu.stl.encoder.floats.examples.pythagorean;

import edu.cmu.stl.encoder.floats.STLEncoder;
import edu.cmu.stl.encoder.floats.util.Result;

public class Pythagorean {
    public static void run() {
        STLEncoder e = new STLEncoder();

        String minizincModel =  "float: side_1 = 3.0;" + 
                                "float: side_2 = 4.0;" +
                                "var float: side_3;" +    
                                "constraint side_3 > 0;" +
                                "constraint side_1 * side_1 + side_2 * side_2 = side_3 * side_3;";

        Result r = e.run(minizincModel);
        System.out.println(r);
    }
}

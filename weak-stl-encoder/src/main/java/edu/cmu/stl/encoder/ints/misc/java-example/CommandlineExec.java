
// Sat Feb 19 10:11:46 EST 2022
// Simon Chu
// https://stackoverflow.com/questions/5711084/java-runtime-getruntime-getting-output-from-executing-a-command-line-program

// since package name is specified, this program must be run in the root directory
// javac util/CommandlineExec
// java util.CommandlineExec

package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandlineExec {
    // can use both absolute and relative path here for now
    public static String testFile = "/Users/chus/Code/ANTLR/STL-Int/test/encoding/non_sat.mzn";
    // public static String testFile = "test/encoding/simple_comp.mzn";

    public static void main(String[] args) {
        try {
            // execute the MiniZinc command
            // requires MiniZinc to be installed on commandline

            Runtime rt = Runtime.getRuntime();
            String command = String.format("minizinc %s --output-mode json", testFile);
            Process proc = rt.exec(command);

            BufferedReader stdInput = new BufferedReader(new 
                InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                InputStreamReader(proc.getErrorStream()));

            // Read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            String s = null;

            StringBuilder sb = new StringBuilder();
            while ((s = stdInput.readLine()) != null) {
                // System.out.println(s);
                sb.append(s);
            }
            System.out.println(sb.toString());
            System.out.println(sb.toString().equals("=====UNSATISFIABLE====="));

            // Read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

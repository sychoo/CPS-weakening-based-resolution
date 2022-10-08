package edu.cmu.stl.encoder.floats.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandlineOperation {
    public static String runCommand(String command) {
        String stdout = null;
        try {
          // execute the MiniZinc command
          // requires MiniZinc to be installed on commandline

          Runtime rt = Runtime.getRuntime();
          Process proc = rt.exec(command);

          BufferedReader stdInput = new BufferedReader(new 
              InputStreamReader(proc.getInputStream()));

          BufferedReader stdError = new BufferedReader(new 
              InputStreamReader(proc.getErrorStream()));

          String s = null;
          StringBuilder sb = new StringBuilder();

          while ((s = stdInput.readLine()) != null) {
              sb.append(s);
              sb.append("\n");
          }

        //   System.out.println(sb.toString()); // test

          // Read any errors from the attempted command
          while ((s = stdError.readLine()) != null) {
              System.out.println(s);
          }
          stdout = sb.toString();
      } catch (Exception e) {
          e.printStackTrace();
      }

      return stdout;
    }
}

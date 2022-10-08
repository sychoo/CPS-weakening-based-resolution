// Fri Jul 22 10:10:22 EDT 2022
// Simon Chu

package edu.cmu.stl.encoder.floats;

import edu.cmu.stl.encoder.floats.util.Result;
import edu.cmu.stl.encoder.floats.util.Signal;
import edu.cmu.stl.encoder.floats.util.FileOperation;
import edu.cmu.stl.encoder.floats.util.CommandlineOperation;

import java.io.IOException;
import java.util.ArrayList;

import edu.cmu.stl.encoder.floats.parsing.ast.AST;
import edu.cmu.stl.encoder.floats.parsing.Generator;
import edu.cmu.stl.encoder.floats.parsing.Parser;


public class STLEncoder {
    public static String cacheDir = "target/cache/";
    public static String cacheFile = "target/cache/cache.mzn";

    // can only be set to true at this moment
    public static boolean writeToCacheFile = true; // true -> write to cache file, false -> directly solve it on command line
    
    // command line option doesn't work

    // raw file input
    public String stlFileContent = "";
    public String envFileContent = "";

    // runtime parameter
    public String runtimeParameter = "";
    
    // parsed AST for STL
    public ArrayList<AST> stlASTList;

    // generated signal declaration, and stl constraints 
    public String signalDeclaration = "";
    public String stlRobustnessConstraints = "";

    // take in the file path of STL and environmental model
    // once the path is set, it cannot be changed
    public STLEncoder(String stlFilePath, ArrayList<String> envFilePaths) {
        // read in the content of stl
        this.stlFileContent = FileOperation.readFile(stlFilePath);

        // read in the content of env
        StringBuilder envFileContentBuilder = new StringBuilder();
        for (String envFilePath: envFilePaths) {
            envFileContentBuilder.append(FileOperation.readFile(envFilePath));
        }
        this.envFileContent = envFileContentBuilder.toString();

        // generate AST
        this.stlASTList = this.parseSTLtoASTList(this.stlFileContent);
        // System.out.println("AST Obtained in STLEncoder(Main): " + this.stlASTList);

        // generate declaration and constraints associated with the AST
        this.signalDeclaration = this.generateSignalDeclaration(this.stlASTList);
        // System.out.println("Signal Declaration Obtained in STLEncoder(Main): " + this.signalDeclaration);

        this.stlRobustnessConstraints = this.generateSTLRobustnessConstraints(this.stlASTList);
        // System.out.println("STL Robustness Obtained in STLEncoder(Main): " + this.stlRobustnessConstraints);

    }


    // disables the automatic signal declaration
    // rely on human input for signal length
    // take in the file path of STL and environmental model
    // once the path is set, it cannot be changed
    public STLEncoder(String stlFilePath, Signal signal) {
        // read in the content of stl
        this.stlFileContent = FileOperation.readFile(stlFilePath);

        this.envFileContent ="";

        // generate AST
        this.stlASTList = this.parseSTLtoASTList(this.stlFileContent);
        // System.out.println("AST Obtained in STLEncoder(Main): " + this.stlASTList);

        // generate declaration and constraints associated with the AST
        this.signalDeclaration = signal.emitEncoding().toString(); // this.generateSignalDeclaration(this.stlASTList);

        // test
        // System.out.println("this.signalDeclaration: " + this.signalDeclaration);

        // System.out.println("Signal Declaration Obtained in STLEncoder(Main): " + this.signalDeclaration);

        this.stlRobustnessConstraints = this.generateSTLRobustnessConstraints(this.stlASTList);
        // System.out.println("STL Robustness Obtained in STLEncoder(Main): " + this.stlRobustnessConstraints);

    }

    public STLEncoder(String stlFilePath, String envFilePath) {
        this(stlFilePath, new ArrayList<String>() {
            {
                add(envFilePath);
            }
        });
    }

    public STLEncoder() {
        // empty constructor, no supplied stl or environmental files
    }

    // supply runtime parameters before running
    public Result run(String runtimeParameter) {
        this.runtimeParameter = runtimeParameter;
        return this.solve();
    }

    public Result solve() {
        // 1. generate model
        String minizincModel = this.generateMiniZincModel();

        String stdout = "";

        if (writeToCacheFile) {
            // 2. write model to file
            FileOperation.writeToFile(cacheDir, cacheFile, minizincModel);

            // 3. run command obtain stdout
            String command = String.format("minizinc %s --solver gurobi --output-mode json --time-limit 500", cacheFile);
            stdout = CommandlineOperation.runCommand(command);
        } else {
            // 2-3. put model/constraints directly on the commandline
            // String command = String.format("minizinc --solver gurobi --output-mode json --time-limit 5000 - <<< \"%s\"", ");
            // requires special command
            // String command = String.format("minizinc_stdin \"%s\"", minizincModel);
            String command = String.format("minizinc - ");

            // String command = "minizinc_stdin int : i=1;"; // this works
            // String command = "minizinc_stdin var int: x;"; // this works

            stdout = CommandlineOperation.runCommand(command);

            System.out.println("command: " + command);
            System.out.println("stdout: " + stdout);
        }
        
        // 4. parse result from stdout
        Result result = new Result(stdout);

        return result;
    }

    // combine all components of the minizinc model
    public String generateMiniZincModel() {
        StringBuilder sb = new StringBuilder();

        sb.append("%===== BEGIN OF SIGNAL DECLARATION (AUTO) =====\n");
        sb.append(this.signalDeclaration);
        sb.append("\n");
        sb.append("%===== END OF SIGNAL DECLARATION =====\n");
        sb.append("\n");

        sb.append("%===== BEGIN OF RUNTIME PARAMETER (MANUAL) =====\n");
        sb.append(this.runtimeParameter);
        sb.append("\n");
        sb.append("%===== END OF RUNTIME PARAMETER =====\n");
        sb.append("\n");

        sb.append("%===== BEGIN OF ENVIRONMENTAL MODEL (MANUAL) =====\n");
        sb.append(this.envFileContent);
        sb.append("\n");
        sb.append("%===== END OF ENVIRONMENTAL MODEL =====\n");
        sb.append("\n");

        sb.append("%===== BEGIN OF STL ROBUSTNESS CONSTRAINT (AUTO) =====\n");
        sb.append(this.stlRobustnessConstraints);
        sb.append("\n");
        sb.append("%===== END OF STL ROBUSTNESS CONSTRAINT =====\n");
        sb.append("\n");

        return sb.toString();
    }
    //=================== Helper Functions ===================
    public ArrayList<AST> parseSTLtoASTList(String stlFileContent) {
        // TODO: obtain parsed AST from STL expression using ANTLR
        return Parser.parse(stlFileContent);
    }

    public String generateSignalDeclaration(ArrayList<AST> stlAST) {
        // TODO: generate signal declaration using the stl expression
        return Generator.generateSignalDeclaration(stlAST);
    }

    public String generateSTLRobustnessConstraints(ArrayList<AST> stlAST) {
        return Generator.generateSTLRobustnessConstraints(stlAST);
    }

    public static void main(String[] args) throws IOException {

        if (args.length != 3) {
            System.out.println("Usage: java STLEncoder <stlFilePath> <envFilePath> <runtimeParameter");
            System.exit(0);
        }

        // arg 1: stlFilePath
        String stlFilePath = args[0];

        // arg 2: envFilePath
        String envFilePath = args[1];

        // arg 3: runtimeParameter
        String runtimeParameter = args[2];

        STLEncoder e = new STLEncoder(stlFilePath, envFilePath);
        Result r = e.run(runtimeParameter);

        // print the result
        System.out.println(r);
    }
}
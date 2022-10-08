// Mon Feb 14 11:48:49 EST 2022
// Simon Chu
//
// Usage: java Main <STL formula file> <signal file>
// Return: the encoding to calculate robustness
// Future: pair-wise resolution of 2 STL expressions

// TODO: change the object name for STLInt

// InputStream to String conversion referred to:
// https://stackoverflow.com/questions/309424/how-do-i-read-convert-an-inputstream-into-a-string-in-java

// process file I/O

package edu.cmu.stl.encoder.ints.main;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.util.stream.Collectors;

import edu.cmu.stl.encoder.ints.encoder.AST;
import edu.cmu.stl.encoder.ints.encoder.EmitEncoding;

import edu.cmu.stl.encoder.ints.util.Encoding;
import edu.cmu.stl.encoder.ints.util.Result;
import edu.cmu.stl.encoder.ints.util.Signal;
import edu.cmu.stl.encoder.ints.util.EmitEncodingResult;

import edu.cmu.stl.encoder.ints.parser.CompilationListener;

import edu.cmu.stl.encoder.parser.STLIntLexer;
import edu.cmu.stl.encoder.parser.STLIntParser;

public class Main {
    /****************** DANGER **********************/
    /***** BE CAUTIOUS WHEN MODIFYING THIS FIELD ****/
    /* FILE MAY BE DELETED, TRUNCATED, OR OVERRIDED */
    /************************************************/
    public static String cacheDir = "target/cache/";
    public static String cacheFile = "target/cache/cache.mzn";

    public static void printUsage() {
        System.out.println("usage: java Main <STL formula file> <signal file>");
    }

    // write the encoded string to a file
    // https://www.baeldung.com/java-write-to-file
    // https://stackoverflow.com/questions/27599965/java-better-way-to-delete-file-if-exists
    // https://docs.oracle.com/javase/7/docs/api/java/nio/file/Files.html#deleteIfExists%28java.nio.file.Path%29
    public static void writeToFile(String filename, Encoding e) 
          throws IOException {
        String str = e.toString();

        // delete cache file if it exists

        // creating new directory
        File directory = new File(cacheDir);
        if (!directory.exists()) {
            directory.mkdir();
        }

        File file = new File(filename);
        Files.deleteIfExists(file.toPath());

       // write the String str to a file called filename
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(str);
        writer.close();
    }

    // run minizinc command on the minizinc program file, return result (json encoded decision var list or not satisfied
    public static Result runMiniZinc(String filename) {
        Result result = null;

          try {
            // execute the MiniZinc command
            // requires MiniZinc to be installed on commandline

            Runtime rt = Runtime.getRuntime();
            // set the time limit to 500 miniseconds
            String command = String.format("minizinc %s --output-mode json --time-limit 500", filename);
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

            System.out.println(sb.toString()); // test

            // Read any errors from the attempted command
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            result = new Result(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // https://mkyong.com/java/how-to-get-file-extension-in-java/
    // get the suffix of an arbitrary file
    public static String getFileExtension(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("fileName must not be null!");
        }

        String extension = "";

        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            extension = fileName.substring(index + 1);
        }

        return extension;

    }

    ////////////////////////////
    // How to call Main.run() //
    ////////////////////////////
    //
    // there are 3 ways
    // (1) Main.run(XX.stl, XX.sig)
    //     this is used to evaluate a signal XX.sig with respect to an STL specification XX.stl
    // (2) Main.run(XX.stl, XX.env)
    //     this is used to generate the signal using the environmental model and then evaluated
    //     the generated signal with respect to an STL specification XX.stl
    // (3) Main.run(XX.stl, signalObject)
    //     this is used to evaluate the signalObject with respect to an STL specification XX.stl
    //
    public static Result run(String stlFile, Signal signalObj) {
        return run(stlFile, null, signalObj);
    }

    public static Result run(String stlFile, String signalOrEnvFile) {
        return run(stlFile, signalOrEnvFile, null);
    }

    // function that actually runs the parser and listener-based evaluator
    public static Result run(String stlFile, String signalOrEnvFile, Signal signalObject) {

        String signalFile = null; // arbitrary signal file
        String envFile = null; // environmental model file

        Result result = null;

        CompilationListener extractor = null;

        try {
            /************************************/
            /***** process STL formula file *****/
            /************************************/

            InputStream stl_is = new FileInputStream(stlFile);
            ANTLRInputStream antlr_is = new ANTLRInputStream(stl_is); 
            STLIntLexer lexer = new STLIntLexer(antlr_is); 
            CommonTokenStream tokens = new CommonTokenStream(lexer); 
            STLIntParser parser = new STLIntParser(tokens); 
            ParseTree tree = parser.prog(); // start pasing at prog 
            // System.out.println(tree.toStringTree(parser)); // test: print tree as text LISP style list

            /*******************************/
            /***** process signal file *****/
            /*******************************/

            if (signalOrEnvFile == null) {
                extractor = new CompilationListener(parser, signalObject);
            }
            else if (getFileExtension(signalOrEnvFile).equals("sig")) {
                signalFile = signalOrEnvFile;
                InputStream signal_is = new FileInputStream(signalFile);
                String rawSignalString = new BufferedReader(new InputStreamReader(signal_is))
                                    .lines().parallel().collect(Collectors.joining("\n"));
                // System.out.println(rawSignalString); // test: print the signal string obtained from InputStream
                Signal signalObj = Signal.parse(rawSignalString);
                
                System.out.println();
                System.out.println("===== BEGIN OF SIGNAL =====");
                System.out.println(signalObj); // test: print the signal object data structure
                System.out.println("===== END  OF  SIGNAL =====\n\n");

                // process encoding of STL formula to MILP
            
                extractor = new CompilationListener(parser, signalObj);
            } else if (getFileExtension(signalOrEnvFile).equals("env") || getFileExtension(signalOrEnvFile).equals("mzn")) {
                envFile = signalOrEnvFile;
                InputStream env_is = new FileInputStream(envFile);
                String rawEnvString = new BufferedReader(new InputStreamReader(env_is))
                                    .lines().parallel().collect(Collectors.joining("\n"));
                // System.out.println(rawSignalString); // test: print the signal string obtained from InputStream
                
                System.out.println();
                System.out.println("===== BEGIN OF ENV MODEL =====");
                System.out.println(rawEnvString); // test: print the signal object data structure
                System.out.println("===== END  OF  ENV MODEL =====\n\n");

                // process encoding of STL formula to MILP
                extractor = new CompilationListener(parser, null, rawEnvString);

            } else {
                throw new IllegalArgumentException("Unknown File Extension \"" + getFileExtension(signalOrEnvFile) + "\"" + " for file " + signalOrEnvFile);
            }

            /***************************************/
            /***** process STL spec parse tree *****/
            /***************************************/

            ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker
            walker.walk(extractor, tree); // initiate walk of tree with listener

            // pass the signal object to the EvalVisitor to evaluate the AST
            // EvalVisitor eval = new EvalVisitor(signalObj);
            // eval.visit(tree); // start the visit of AST

            Encoding encoding = extractor.getEncoding();
            Encoding debugEncoding = extractor.getDebugEncoding();

            System.out.println("===== BEGIN OF ENCODING =====");
            System.out.println(encoding);
            System.out.println("===== END  OF  ENCODING =====\n\n");
            
            System.out.println("===== BEGIN OF DEBUG ENCODING =====");
            System.out.println(debugEncoding);
            System.out.println("===== END  OF  DEBUG ENCODING =====\n\n");
            
            /************************************/
            /***** process minizinc program *****/
            /************************************/

            Main.writeToFile(cacheFile, encoding);

            System.out.println("===== BEGIN OF MINIZINC OUTPUT =====");
            result = Main.runMiniZinc(cacheFile);            
            System.out.println("===== END  OF  MINIZINC OUTPUT =====\n\n");

            System.out.println("===== BEGIN OF RESULT =====");
            System.out.println("robustness value: " + result.get("rho_stl_1"));
            System.out.println("===== END  OF  RESULT =====");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws Exception {

        // getting input from command line (files)
        String stlFile, signalFile = null; 

        if ( args.length < 2 ) {
            printUsage();
            System.exit(0);
        }
        stlFile = args[0];
        signalFile = args[1];

        Main.run(stlFile, signalFile);
    }
}

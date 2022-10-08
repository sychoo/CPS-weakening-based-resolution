// all helper functions related to file operation

package edu.cmu.stl.encoder.floats.util;

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
import java.util.ArrayList;
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

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

// for appending to file
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class FileOperation {

    public static boolean isFileEmpty(String filename) {
        // https://www.java67.com/2018/03/a-simple-example-to-check-if-file-is-empty-in-java.html
        File newFile = new File(filename);

        if (newFile.length() == 0)
        { 
            return true;
        } 
        else
        {
            return false;
        }
    }
    
    public static void appendToFile(String filename, ArrayList<String> parameterList) {
        try {
            FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            for (String parameter : parameterList) {
                out.print(parameter);
                out.print(",");
            }
            out.println();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendToFile(String filename, String contentToAppend) {
        try (
                FileWriter fw = new FileWriter(filename, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println(contentToAppend);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // read file into a string
    public static String readFile(String fullFilePath) {
        String rawString = "";
        try {
            rawString = new String(Files.readAllBytes(Paths.get(fullFilePath)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
        return rawString;
    }

    /*
     * @param fileDirPath, the path of the enclosing directory of the file
     * i.e. target/cache/
     * 
     * @param fullFilePath, the full path to the file
     * i.e. target/cache/cache.mzn
     */
    // write the encoded string to a file
    // https://www.baeldung.com/java-write-to-file
    // https://stackoverflow.com/questions/27599965/java-better-way-to-delete-file-if-exists
    // https://docs.oracle.com/javase/7/docs/api/java/nio/file/Files.html#deleteIfExists%28java.nio.file.Path%29
    public static void writeToFile(String fileDirPath, String fullFilePath, String str) {
        try {
            // creating new directory if the directory doesn't already exist
            File directory = new File(fileDirPath);
            if (!directory.exists()) {
                directory.mkdir();
            }

            File file = new File(fullFilePath);
            Files.deleteIfExists(file.toPath());

            // write the String str to a file called filename
            BufferedWriter writer = new BufferedWriter(new FileWriter(fullFilePath));
            writer.write(str);
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

package edu.cmu.stl.encoder.floats.parsing;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.stream.Collectors;

import edu.cmu.stl.encoder.parser.STLLexer;
import edu.cmu.stl.encoder.parser.STLParser;

import edu.cmu.stl.encoder.floats.parsing.ast.AST;
import edu.cmu.stl.encoder.floats.util.Encoding;

public class Parser {
    public static Encoding encoding = null;
    public static Encoding debugEncoding = null;

    public static ArrayList<AST> parse(String stlFileContent) {
        ArrayList<AST> parsedASTList = null;
        try {
            InputStream is = new ByteArrayInputStream(stlFileContent.getBytes());
            ANTLRInputStream antlr_is = new ANTLRInputStream(is);
            STLLexer lexer = new STLLexer(antlr_is);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            STLParser parser = new STLParser(tokens);
            ParseTree tree = parser.prog(); // start pasing at prog
            // System.out.println(tree.toStringTree(parser)); // test: print tree as text
            // LISP style list

            // TODO: fix compilation listener so that it only accept parser object
            CompilationListener extractor = new CompilationListener(parser);

            ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker
            walker.walk(extractor, tree); // initiate walk of tree with listener

            // TODO: add getAST method to Compilation listener
            parsedASTList = extractor.getASTList();

            // TODO: to be deprecated: decouple the parse tree from the constraint generation
            encoding = extractor.getEncoding();
            debugEncoding = extractor.getDebugEncoding();

            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return parsedASTList;
    }
}

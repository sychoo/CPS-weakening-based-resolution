package edu.cmu.stl.encoder.floats.parsing;

import java.util.ArrayList;
import java.util.Collections;

import edu.cmu.stl.encoder.floats.parsing.ast.AST;
import edu.cmu.stl.encoder.floats.parsing.ast.Id;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SignalDeclarer {
    // note that by default, only declare floating points number ranging between 0.0
    // and 1000.0 as a global limit
    public static String generateSignalDeclaration(ArrayList<AST> stlASTList) {
        // System.out.println(getSignalLength(stlAST));
        // System.out.println(getSignalVariables(stlAST));

        StringBuilder sb = new StringBuilder();

        // find the max signal length among all STL ASTs
        ArrayList<Integer> stlASTSignalLengths = new ArrayList<Integer>();
        for (AST stlAST : stlASTList) {
            stlASTSignalLengths.add(getSignalLength(stlAST));
        }

        // get the max length of the signal among all the STL expressions.
        sb.append(EmitEncoding.constDecl("int", "SIGNAL_LENGTH = " + Collections.max(stlASTSignalLengths)));

        // for (AST stlAST : stlASTList) {
        //     for (String signalVar : getSignalVariables(stlAST)) {
        //         sb.append(EmitEncoding.array1d("1", "SIGNAL_LENGTH", "var 0.0..1000.0",
        //                 EmitEncoding.signalVar(signalVar)));
        //     }
        // }
        for (String signalVar : getSignalVariableFromASTList(stlASTList)) {
            sb.append(EmitEncoding.array1d("1", "SIGNAL_LENGTH", "var 0.0..1000.0",
                            EmitEncoding.signalVar(signalVar)));
        }
        
        return sb.toString();
    }

    // get (non-repetitive) signal variables list from lit of stlAST's
    public static ArrayList<String> getSignalVariableFromASTList(ArrayList<AST> stlASTList) {
        ArrayList<String> signalVars = new ArrayList<String>();

        for (AST stlAST : stlASTList) {
            signalVars.addAll(getSignalVariables(stlAST));
        }
        Set<String> signalVarSet = new HashSet<String>(signalVars);
        return new ArrayList<>(signalVarSet);
    }

    // TODO: generate list of variables from the AST
    public static ArrayList<String> getSignalVariables(AST stlAST) {
        ArrayList<String> signalVars = stlAST.getSignalVariables();

        // eliminate duplicated elements in ArrayList
        // https://www.netsurfingzone.com/collection/how-to-avoid-duplicate-elements-in-arraylist/
        Set<String> signalVarSet = new HashSet<String>(signalVars);

        return new ArrayList<>(signalVarSet);
    }

    // TODO: generate the max signal length from the AST, temporal operators
    public static int getSignalLength(AST stlAST) {
        return stlAST.getSignalLength();
    }
}

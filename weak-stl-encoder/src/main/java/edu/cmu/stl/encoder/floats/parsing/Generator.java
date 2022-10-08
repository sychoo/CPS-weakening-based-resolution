// generate signal declaration and constraints from AST of the parsed STL

package edu.cmu.stl.encoder.floats.parsing;
import edu.cmu.stl.encoder.floats.parsing.ast.AST;

import java.util.ArrayList;

import edu.cmu.stl.encoder.floats.parsing.SignalDeclarer;

public class Generator {

    // given the AST of the parsed STL, generate signal declaration (list of variables and legnth of the signal)
    public static String generateSignalDeclaration(ArrayList<AST> stlASTList) {
        return SignalDeclarer.generateSignalDeclaration(stlASTList);
    }

    // TODO: generate the constraint of the STL robustness solely based on the AST of the parsed STL
    // TODO: deprecate the static variable storing encoding in the Parser
    public static String generateSTLRobustnessConstraints(ArrayList<AST> stlASTList) {
        return Parser.encoding.toString();
    }
}

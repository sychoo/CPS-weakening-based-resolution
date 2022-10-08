// Int
package edu.cmu.stl.encoder.floats.parsing.ast;


import edu.cmu.stl.encoder.floats.parsing.ast.AST;
import edu.cmu.stl.encoder.floats.parsing.EmitEncoding;

import edu.cmu.stl.encoder.floats.util.Encoding;
import edu.cmu.stl.encoder.floats.util.EmitEncodingResult;

import java.util.ArrayList;


public class Int extends AST {
    String value = null;
    public Int(String value) {
        super("Int");
        this.value = value;
    }

    public int toInt() {
        return Integer.parseInt(this.value);
    }

    @Override public EmitEncodingResult emitEncoding(int time, String rhoVar, boolean isWeak) {
        Encoding e = new Encoding();
        Encoding db = new Encoding();

        e.append(this.value);
        db.append("");

        EmitEncodingResult er = new EmitEncodingResult(e, db);
        return er;
    }

    @Override public AST negate() {
        return null;
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Int(");
        sb.append(this.value);
        sb.append(")");
        return sb.toString();
    }

    @Override public ArrayList<String> getSignalVariables() {
        // empty arraylist
        return new ArrayList<String>();
    }

    @Override public int getSignalLength() {
        return 0;
    }
}




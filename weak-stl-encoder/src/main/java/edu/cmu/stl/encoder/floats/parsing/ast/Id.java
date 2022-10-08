// Id
package edu.cmu.stl.encoder.floats.parsing.ast;


import edu.cmu.stl.encoder.floats.parsing.ast.AST;
import edu.cmu.stl.encoder.floats.parsing.EmitEncoding;

import edu.cmu.stl.encoder.floats.util.Encoding;
import edu.cmu.stl.encoder.floats.util.EmitEncodingResult;
import java.util.ArrayList;


public class Id extends AST {
    String value = null;
    boolean isSignalVar = true;

    public Id(String value) {
        super("Id");
        this.value = value;
    }

    public Id(String value, boolean isSignalVar) {
        super("Id");
        this.value = value;
        this.isSignalVar = isSignalVar;
    }

    public void setIsSignalVar(boolean isSignalVar) {
        this.isSignalVar = isSignalVar;
    }

    @Override public EmitEncodingResult emitEncoding(int time, String rhoVar, boolean isWeak) {
        Encoding e = new Encoding();
        Encoding db = new Encoding();

        if (this.isSignalVar) {
            e.append(EmitEncoding.signalVar(this.value) + "[" + time + "]");
        } else {
            e.append(this.value);
        }
        
        db.append("Encoding ID");

        EmitEncodingResult er = new EmitEncodingResult(e, db);
        return er;
    }

    @Override public AST negate() {
        return null;
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id(");
        sb.append(this.value);
        sb.append(")");
        return sb.toString();
    }

    @Override public ArrayList<String> getSignalVariables() {
        ArrayList<String> sv = new ArrayList<String>();
        sv.add(this.value);
        return sv;
    }

    @Override public int getSignalLength() {
        return 0;
    }
}


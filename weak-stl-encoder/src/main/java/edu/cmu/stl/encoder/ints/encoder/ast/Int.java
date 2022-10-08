// Int
package edu.cmu.stl.encoder.ints.encoder.ast;

import edu.cmu.stl.encoder.ints.encoder.AST;
import edu.cmu.stl.encoder.ints.encoder.EmitEncoding;

import edu.cmu.stl.encoder.ints.util.Encoding;
import edu.cmu.stl.encoder.ints.util.EmitEncodingResult;

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
}




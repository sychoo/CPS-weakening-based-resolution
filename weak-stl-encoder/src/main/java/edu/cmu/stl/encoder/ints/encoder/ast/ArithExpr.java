package edu.cmu.stl.encoder.ints.encoder.ast;

import edu.cmu.stl.encoder.ints.encoder.AST;
import edu.cmu.stl.encoder.ints.encoder.EmitEncoding;

import edu.cmu.stl.encoder.ints.util.Encoding;
import edu.cmu.stl.encoder.ints.util.EmitEncodingResult;

// stores arithmetic expressions
public class ArithExpr extends AST {
    String op = null;
    AST lhs = null;
    AST rhs = null;

    public ArithExpr(String op, AST lhs, AST rhs) {
        super("ArithExpr");
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override public EmitEncodingResult emitEncoding(int time, String rhoVar, boolean isWeak) {
        Encoding e = new Encoding();
        Encoding db = new Encoding();

        EmitEncodingResult lhsResult = lhs.emitEncoding(time, rhoVar, isWeak);
        EmitEncodingResult rhsResult = rhs.emitEncoding(time, rhoVar, isWeak);

        Encoding lhsEncoding = lhsResult.getEncoding();
        Encoding rhsEncoding = rhsResult.getEncoding();

        Encoding lhsDebug = lhsResult.getDebugEncoding();
        Encoding rhsDebug = rhsResult.getDebugEncoding();

        // a hack - ensure precedence rule by adding parenthese around the lhs and rhs of the 
        // arithmetic expr
        e.append("(");
        e.append(lhsEncoding);
        e.append(")");
        e.append(this.op);
        e.append("(");
        e.append(rhsEncoding);
        e.append(")");

        db.append(lhsDebug);
        db.append(rhsDebug);
        db.append("encoding arithmetic expr");

        EmitEncodingResult er = new EmitEncodingResult(e, db);
        return er;
    }

    @Override public AST negate() {
        return null;
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ArithExpr(\n");
        sb.append("    " + this.op + "\n");
        sb.append("    " + this.lhs + "\n");
        sb.append("    " + this.rhs + "\n)");
        return sb.toString();
    }
}


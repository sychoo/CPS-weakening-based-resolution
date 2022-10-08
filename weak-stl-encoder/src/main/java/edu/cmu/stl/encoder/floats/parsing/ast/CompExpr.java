package edu.cmu.stl.encoder.floats.parsing.ast;

import edu.cmu.stl.encoder.floats.parsing.ast.AST;
import edu.cmu.stl.encoder.floats.parsing.EmitEncoding;

import edu.cmu.stl.encoder.floats.util.Encoding;
import edu.cmu.stl.encoder.floats.util.EmitEncodingResult;

import java.util.ArrayList;

// comparison expression
// stores comparison expression
public class CompExpr extends AST {
    String op = null;
    AST lhs = null;
    AST rhs = null;
    AST param = null; // weakening parameter 
    AST param_ub = null; // upper bound of weakening parameter

    // without weakening
    public CompExpr(String op, AST lhs, AST rhs) {
        super("CompExpr");
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
        this.param = null;
    }

    // with weakening (weakening parameter = param)
    public CompExpr(String op, AST lhs, AST rhs, AST param, AST param_ub) {
        super("CompExpr");
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
        this.param = param;
        this.param_ub = param_ub;
    }

    // TO-DO: compExpr will be broken down to arithmetic expressions and encoded from there
    @Override public EmitEncodingResult emitEncoding(int time, String rhoVar, boolean isWeak) {
        // EmitCoding.varDecl();

        Encoding e = new Encoding();
        Encoding db = new Encoding();

        AST arithAST = null;

        // normalize the compExpr
        switch(this.op) {
            // > >=
            // negate and move all rhs arguments to lhs
            case ">":
            case ">=":
                // return new Encoding(ctx.arithExpr(0).getText() + " - (" + ctx.arithExpr(1).getText() + ")");
                arithAST = new ArithExpr("-", this.lhs, this.rhs);
                if (this.param != null && isWeak) {
                    // weakening of atoms, add weakening parameter to the robustness calculation
                    arithAST = new ArithExpr("+", arithAST, this.param);
                }
                break;

            // < <=
            // negate and move all lhs arguments to rhs
            case "<":
            case "<=":
                // return new Encoding(ctx.arithExpr(1).getText() + " - (" + ctx.arithExpr(0).getText() + ")");
                arithAST = new ArithExpr("-", this.rhs, this.lhs);
                if (this.param != null && isWeak) {
                    // weakening of atoms, add weakening parameter to the robustness calculation
                    arithAST = new ArithExpr("+", arithAST, this.param);
                }
                break;

     

            default:
                // do nothing
                break;
        }

        // special case for equal sign, return the encoding of a new AST
        if (this.op.equals("=")) {
                arithAST = new LogicExpr("and", new CompExpr(">=", this.lhs, this.rhs), new CompExpr("<=", this.lhs, this.rhs));
                return arithAST.emitEncoding(time, rhoVar, isWeak);
        }

        // encoding from the normalized expression
        EmitEncodingResult normalized_er = arithAST.emitEncoding(time, null, isWeak);
        Encoding normalized_e = normalized_er.getEncoding();
        Encoding normalized_db = normalized_er.getDebugEncoding();

        EmitEncodingResult assigned_er = EmitEncoding.assign(rhoVar, normalized_e);
        Encoding assigned_e = assigned_er.getEncoding();
        Encoding assigned_db = assigned_er.getDebugEncoding();

        e.append(EmitEncoding.varDecl("float", rhoVar));

        if (this.param != null && isWeak) {
            // declare the weakening parameter
            String paramVar = ((Id) param).value;
            // e.append(EmitEncoding.varDecl("int", ((Id) param).value));

            // declare the constraints on weakening parameter
            // 0 <= param <= param_ub
            String paramVarUpperBound = ((Float) param_ub).value;
            e.append(EmitEncoding.constraint(paramVar + " >= 0"));
            e.append(EmitEncoding.constraint(paramVar + " <= " + paramVarUpperBound));
            
            // eventually add rho_w > 0 (robustness of weakened requirement)
            // eventually add minimize (rho_w - rho), also known as degree of weakening
        }
        e.append(assigned_e);

        db.append(normalized_db);
        db.append(assigned_db);
        db.append("encode comparison expression\n");

        EmitEncodingResult new_new_er = new EmitEncodingResult(e, db);

        // System.out.println(arithAST);
        return new_new_er;
    }

    @Override
    public AST negate() {
        // (1) not p > q === p <= q
        if (this.op.equals(">")) {
            return new CompExpr("<=", this.lhs, this.rhs, this.param, this.param_ub);
        }

        // (2) not p >= q === p < q
        else if (this.op.equals(">=")) {
            return new CompExpr("<", this.lhs, this.rhs, this.param, this.param_ub);
        }

        // (3) not p < q === p >= q
        else if (this.op.equals("<")) {
            return new CompExpr(">=", this.lhs, this.rhs, this.param, this.param_ub);
        }

        // (4) not p <= q === p > q 
        else if (this.op.equals("<=")) {
            return new CompExpr(">", this.lhs, this.rhs, this.param, this.param_ub);
        }

        return null;
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CompExpr(\n");
        sb.append("    " + this.op + "\n");
        sb.append("    " + this.lhs + "\n");
        sb.append("    " + this.rhs + "\n)");
        return sb.toString();
    }

    @Override public ArrayList<String> getSignalVariables() {
        ArrayList<String> lsv = this.lhs.getSignalVariables();
        ArrayList<String> rsv = this.rhs.getSignalVariables();

        lsv.addAll(rsv);
        return lsv;
    }

    @Override public int getSignalLength() {
        return 1;
    }
}


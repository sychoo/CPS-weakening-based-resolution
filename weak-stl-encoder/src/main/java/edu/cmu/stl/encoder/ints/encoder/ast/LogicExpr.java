// 2022-04-02 13:44:46
// note that LogicExpr includes the following:
//
// logical and  (/\)
// logical or   (\/)
// negation     (not)
// implication  (->)
// iff          (<->)

package edu.cmu.stl.encoder.ints.encoder.ast;

import edu.cmu.stl.encoder.ints.encoder.AST;
import edu.cmu.stl.encoder.ints.encoder.EmitEncoding;

import edu.cmu.stl.encoder.ints.util.Encoding;
import edu.cmu.stl.encoder.ints.util.EmitEncodingResult;

// LogicExpr
// pair-wise logical expression
public class LogicExpr extends AST {
    String op = null;
    AST lhs = null;
    AST rhs = null;
    // int time = -1;

    public LogicExpr(String op, AST lhs, AST rhs) {
        super("LogicExpr");
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    // LogicExpr(String op, AST lhs, AST rhs, int time) {
    //     super("LogicExpr");
    //     this.op = op;
    //     this.lhs = lhs;
    //     this.rhs = rhs;
    //     this.time = time;
    // }

    // always declare rhoVar in emitEncoding procedure
    @Override public EmitEncodingResult emitEncoding(int time, String rhoVar, boolean isWeak) {
        Encoding e = new Encoding();
        Encoding db = new Encoding();

        // encoding has two cases
        // (1) AND/OR encoding
        // (2) negate/implication/iff encoding, by converting to AND/OR

        // (1) AND/OR encoding
        if (this.op.equals("and") || this.op.equals("or")) {
            // generate two new fresh variables, they will be declared in subroutines
            String lhsRhoVar   =   EmitEncoding.genNewVar("rho");
            String rhsRhoVar   =   EmitEncoding.genNewVar("rho");

            EmitEncodingResult lhsResult = null;
            EmitEncodingResult rhsResult = null;

            lhsResult = this.lhs.emitEncoding(time, lhsRhoVar, isWeak);
            rhsResult = this.rhs.emitEncoding(time, rhsRhoVar, isWeak);

            // extract encodings
            Encoding lhsEncoding = lhsResult.getEncoding();
            Encoding rhsEncoding = rhsResult.getEncoding();

            Encoding lhsDebug = lhsResult.getDebugEncoding();
            Encoding rhsDebug = rhsResult.getDebugEncoding();

            // append results
            e.append(lhsEncoding);
            e.append(rhsEncoding);
            e.append(EmitEncoding.varDecl("int", rhoVar)); // declare rhoVar

            db.append(lhsDebug);
            db.append(rhsDebug);
            db.append("encode logic expressions.\n");

            // encode logical and (/\)
            if (this.op.equals("and")) {
                EmitEncodingResult extremaResult = EmitEncoding.extrema(EmitEncoding.Extrema.MIN, rhoVar, lhsRhoVar, rhsRhoVar);

                Encoding extremaEncoding = extremaResult.getEncoding();
                Encoding extremaDebug = extremaResult.getDebugEncoding();

                e.append(extremaEncoding);
                db.append(extremaDebug);
            }

            // encode logical or (\/)
            else if (this.op.equals("or")) {
                EmitEncodingResult extremaResult = EmitEncoding.extrema(EmitEncoding.Extrema.MAX, rhoVar, lhsRhoVar, rhsRhoVar);

                Encoding extremaEncoding = extremaResult.getEncoding();
                Encoding extremaDebug = extremaResult.getDebugEncoding();

                e.append(extremaEncoding);
                db.append(extremaDebug);
            }
        }

        // (2) negate/implication/iff encoding, by converting to AND/OR
        if (this.op.equals("not") || this.op.equals("imp") || this.op.equals("iff")) {
            // negation encoding requires propagation of not (using negate() procedure)
            if (this.op.equals("not")) {
                System.out.println("Test:: " + this.rhs);

                System.out.println("Test:: " + this.rhs.negate());
                EmitEncodingResult negateResult = (this.rhs.negate()).emitEncoding(time, rhoVar, isWeak);

                Encoding negateEncoding = negateResult.getEncoding();
                Encoding negateDebug = negateResult.getDebugEncoding();

                e.append(negateEncoding);
                db.append(negateDebug);
            }

            // p -> q === not p \/ q
            else if (this.op.equals("imp")) {
                AST equivFormula = new LogicExpr("or", new LogicExpr("not", null, this.lhs), this.rhs);

                EmitEncodingResult impResult = equivFormula.emitEncoding(time, rhoVar, isWeak);

                Encoding impEncoding = impResult.getEncoding();
                Encoding impDebug = impResult.getDebugEncoding();

                e.append(impEncoding);
                db.append(impDebug);
            }
            
            // p <-> q  === (p -> q) /\ (q -> p)
            //          === (not p \/ q) /\ (not q \/ p)
            // will unwind to implication level
            else if (this.op.equals("iff")) {
                AST equivFormula = new LogicExpr("and", new LogicExpr("imp", this.lhs, this.rhs), new LogicExpr("imp", this.rhs, this.lhs));

                EmitEncodingResult iffResult = equivFormula.emitEncoding(time, rhoVar, isWeak);

                Encoding iffEncoding = iffResult.getEncoding();
                Encoding iffDebug = iffResult.getDebugEncoding();

                e.append(iffEncoding);
                db.append(iffDebug);
            }
        }

        EmitEncodingResult er = new EmitEncodingResult(e, db);
        return er;

    
    }
    // negate of various logic expr (using DeMorgan's)
    // (1) not (p /\ q) = not p \/ not q
    // (2) not (p \/ q) = not p /\ not q
    // (3) not (not q) = q
    // additional negate() function defined in CompExpr to deal with atoms
    @Override
    public AST negate() {
        
        // (1) not (p /\ q) = not p \/ not q
        if (this.op == "and") {
            return new LogicExpr("or", new LogicExpr("not", null, this.lhs), new LogicExpr("not", null, this.rhs));
        }

        // (2) not (p \/ q) = not p /\ not q
        else if (this.op == "or") {
            return new LogicExpr("and", new LogicExpr("not", null, this.lhs), new LogicExpr("not", null, this.rhs));
        }

        // (3) not (not q) = q
        else if (this.op == "not") {
            return this.rhs;
        }
        
        return null;
    }
    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LogicExpr(\n");
        sb.append("    " + this.op + "\n");
        sb.append("    " + this.lhs + "\n");
        sb.append("    " + this.rhs + "\n)");
        return sb.toString();
    }
}


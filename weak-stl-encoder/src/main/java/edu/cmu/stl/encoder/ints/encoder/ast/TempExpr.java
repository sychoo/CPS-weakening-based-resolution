package edu.cmu.stl.encoder.ints.encoder.ast;

import edu.cmu.stl.encoder.ints.encoder.AST;
import edu.cmu.stl.encoder.ints.encoder.EmitEncoding;

import edu.cmu.stl.encoder.ints.util.Encoding;
import edu.cmu.stl.encoder.ints.util.EmitEncodingResult;

import java.util.ArrayList;

// TempExpr
// stores temporal operator
public class TempExpr extends AST {
    String op = null;
    AST lowerBound = null;
    AST upperBound = null;
    AST body = null;

    // left time bound weakening parameter
    AST param_l = null;
    AST param_l_ub = null;

    // right time bound weakening parameter
    AST param_r = null;
    AST param_r_ub = null;

    public TempExpr(String op, AST lowerBound, AST upperBound, AST body) {
        super("TempExpr");
        this.op = op;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.body = body;

        // weakening parameter variable and upper bound
        this.param_l = null;
        this.param_l_ub = null;
        this.param_r = null;
        this.param_r_ub = null;
    }

    // constructor with weakening
    public TempExpr(String op, AST lowerBound, AST upperBound, AST body, AST param_l, AST param_l_ub, AST param_r, AST param_r_ub) {
        super("TempExpr");
        this.op = op;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.body = body;

        // weakening parameter variable and upper bound
        this.param_l = param_l;
        this.param_l_ub = param_l_ub;
        this.param_r = param_r;
        this.param_r_ub = param_r_ub;

    }

    // convert G -> conjunction (logic Expr) AST
    // convert F -> disjunction (logic Expr) AST
    // encoding
    @Override public EmitEncodingResult emitEncoding(int time, String rhoVar, boolean isWeak) {
        // how normal encoding is done?
        // (1)  encoding the body with the time bound specified
        //      store each value in subRhoVarList
        // (2)  find the min/max of the subRhoVarList for G/F
        //
        // how timebound weakening is done?
        // (1)  encoding the body with the time bound specified
        //      store each value in subRhoVarList
        // (2)  create a decision variable array "sub_rho_var_list", store each variable in
        //      subRhoVarList in the decision variable array "sub_rho_var_list"
        // (3)  create left and right decision index (given by user)
        // (4)  create the min/max encoding w.r.t. the "sub_rho_var_list" and the left/right
        //      decision index

        // result
        Encoding e = new Encoding();
        Encoding db = new Encoding();

        e.append(EmitEncoding.varDecl("int", rhoVar)); // declare rhoVar that was passed in

        //////////////////////////////////
        // case there's no weakening
        //////////////////////////////////

        if ((this.param_l == null && this.param_r == null) || !isWeak) {

            //////////////////////////////////////////////////////////
            // (1)  encoding the body with the time bound specified
            //      store each value in subRhoVarList
            //////////////////////////////////////////////////////////

            ArrayList<String> subRhoVarList = new ArrayList<String>();

            // generate the body of the temporal operator at each time step of the time bound
            for (int i = ((Int) this.lowerBound).toInt(); i <= ((Int) this.upperBound).toInt(); ++i) {
                String bodyRhoVar   =   EmitEncoding.genNewVar("rho");
                subRhoVarList.add(bodyRhoVar);

                EmitEncodingResult bodyResult = this.body.emitEncoding(i + time, bodyRhoVar, isWeak);
                Encoding bodyEncoding = bodyResult.getEncoding();
                Encoding bodyDebug = bodyResult.getDebugEncoding();

                e.append(bodyEncoding);
                db.append(bodyDebug);
            }

            ////////////////////////////////////////////////////////
            // (2)  find the min/max of the subRhoVarList for G/F
            ////////////////////////////////////////////////////////

            // chainedExtrema encode list of conjunction or disjunction
            if (this.op.equals("G")) {
                EmitEncodingResult chainedExtremaResult = EmitEncoding.chainedExtrema(EmitEncoding.Extrema.MIN, rhoVar, subRhoVarList);
                Encoding chainedExtremaEncoding = chainedExtremaResult.getEncoding();
                Encoding chainedExtremaDebug = chainedExtremaResult.getDebugEncoding();

                e.append("% Globally encoding\n");
                e.append(chainedExtremaEncoding);
                e.append("% end of Globally encoding\n");

                db.append(chainedExtremaDebug);
            }

            else if (this.op.equals("F")) {
                EmitEncodingResult chainedExtremaResult = EmitEncoding.chainedExtrema(EmitEncoding.Extrema.MAX, rhoVar, subRhoVarList);
                Encoding chainedExtremaEncoding = chainedExtremaResult.getEncoding();
                Encoding chainedExtremaDebug = chainedExtremaResult.getDebugEncoding();

                e.append("% Eventually encoding\n");
                e.append(chainedExtremaEncoding);
                e.append("% end of Eventually encoding\n");
                db.append(chainedExtremaDebug);
            }
        }

        //////////////////////////////////
        // case there is weakening
        //////////////////////////////////

        else if ((this.param_l != null || this.param_r != null) && isWeak) {

            // globally operator "G"
            // (1) encode the body with original time bound
            // (2) emit encoding of elastic array with left and right index

            // extract String and int from weakening parameter and upperbound that was passed in
            // if bound is not declared, use null and -1 as placeholders
            String ext_param_l = this.param_l != null ? ((Id) this.param_l).value : null;
            int ext_param_l_ub = this.param_l_ub != null ? ((Int) this.param_l_ub).toInt() : -1;
            String ext_param_r = this.param_r != null ? ((Id) this.param_r).value : null;
            int ext_param_r_ub = this.param_r_ub != null ? ((Int) this.param_r_ub).toInt() : -1;

            if (this.op.equals("G")) {

                ////////////////////////////////////////////////////////////////
                // (1)  encoding the body with the ORIGINAL time bound specified
                //      store each value in subRhoVarList
                ////////////////////////////////////////////////////////////////

                ArrayList<String> subRhoVarList = new ArrayList<String>();

                // generate the body of the temporal operator at each time step of the time bound
                for (int i = ((Int) this.lowerBound).toInt(); i <= ((Int) this.upperBound).toInt(); ++i) {
                    String bodyRhoVar   =   EmitEncoding.genNewVar("rho");
                    subRhoVarList.add(bodyRhoVar);

                    EmitEncodingResult bodyResult = this.body.emitEncoding(i + time, bodyRhoVar, isWeak);
                    Encoding bodyEncoding = bodyResult.getEncoding();
                    Encoding bodyDebug = bodyResult.getDebugEncoding();

                    e.append(bodyEncoding);
                    db.append(bodyDebug);
                }

                ///////////////////////////////////////////////////////////////////////////////
                // (2) assign array's each index to the decision variable in the subRhoVarList
                ///////////////////////////////////////////////////////////////////////////////

                String subRhoVarListVar = EmitEncoding.genNewVar("sub_rho_list");
                String leftIdxVar = EmitEncoding.genNewVar("l_idx");
                String rightIdxVar = EmitEncoding.genNewVar("r_idx");

                EmitEncodingResult assignArrayResult = EmitEncoding.assignArray(
                    subRhoVarListVar,
                    subRhoVarList,
                    leftIdxVar,
                    rightIdxVar
                    // ext_param_l,
                    // ext_param_l_ub,
                    // ext_param_r,
                    // ext_param_r_ub
                    );

                // note that arrayAssign also encode constraints about the bound
                Encoding arrayAssignEncoding = assignArrayResult.getEncoding();
                Encoding arrayAssignDebug = assignArrayResult.getDebugEncoding();

                e.append(arrayAssignEncoding);
                db.append(arrayAssignDebug);

                //  encode additional G-specific constraint for weakening
                // G will decrease time bound range (shrinking time bound)

                // for each of the 3 cases:
                // weaken left
                // weaken right
                // weaken both side

                ///////////////////////////////////////////////////////////////////////////////
                // (3) Add constraint on left and right index decision var for bounding and weakening
                ///////////////////////////////////////////////////////////////////////////////

                // weakening left only
                // note that array starts with 1
                if (this.param_r == null) {
                    // left = 1 + delta (left_param)
                    // right = size()
                    // 0 <= left_param <= left_upper_bound 
                    e.append(EmitEncoding.constraint(String.format("%s = 1 + %s", leftIdxVar, ext_param_l)));
                    e.append(EmitEncoding.constraint(String.format("%s = %d", rightIdxVar, subRhoVarList.size())));

                    e.append(EmitEncoding.constraint(String.format("%s >= 0", ext_param_l)));
                    e.append(EmitEncoding.constraint(String.format("%s <= %s", ext_param_l, ext_param_l_ub)));
                }

                // weakening right only
                else if (this.param_l == null) {
                    // left = 1
                    // right = size() - delta (right_param)
                    // 0 <= right_param <= right_upper_bound 
                    e.append(EmitEncoding.constraint(String.format("%s = 1", leftIdxVar)));
                    e.append(EmitEncoding.constraint(String.format("%s = %d - %s", rightIdxVar, subRhoVarList.size(), ext_param_r)));

                    e.append(EmitEncoding.constraint(String.format("%s >= 0", ext_param_r)));
                    e.append(EmitEncoding.constraint(String.format("%s <= %s", ext_param_r, ext_param_r_ub)));
                }
                
                // weakening both sides
                else if (this.param_l != null && this.param_r != null) {
                    // left = 1 + delta (left_param)
                    // right = size() - delta (right_param)
                    // 0 <= left_param <= left_upper_bound 
                    // 0 <= right_param <= right_upper_bound 
                    e.append(EmitEncoding.constraint(String.format("%s = 1 + %s", leftIdxVar, ext_param_l)));
                    e.append(EmitEncoding.constraint(String.format("%s = %d - %s", rightIdxVar, subRhoVarList.size(), ext_param_r)));

                    e.append(EmitEncoding.constraint(String.format("%s >= 0", ext_param_l)));
                    e.append(EmitEncoding.constraint(String.format("%s <= %s", ext_param_l, ext_param_l_ub)));

                    e.append(EmitEncoding.constraint(String.format("%s >= 0", ext_param_r)));
                    e.append(EmitEncoding.constraint(String.format("%s <= %s", ext_param_r, ext_param_r_ub)));
                }
                
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // (4) Find the minimum of the subset of the subRhoVarList (in minizinc) bounded by i and j as the robustness value
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                EmitEncodingResult subRhoListRobustnessResult = EmitEncoding.subRhoListRobustness(rhoVar, subRhoVarListVar, EmitEncoding.Extrema.MIN, leftIdxVar, rightIdxVar, subRhoVarList.size());

                Encoding subRhoListRobustnessEncoding = subRhoListRobustnessResult.getEncoding();
                Encoding subRhoListRobustnessDebug = subRhoListRobustnessResult.getDebugEncoding();

                e.append(subRhoListRobustnessEncoding);
                db.append(subRhoListRobustnessDebug);
            }   

            // enlarge the time bound when possible
            else if (this.op.equals("F")) {

                ////////////////////////////////////////////////////////////////
                // (1)  encoding the body with the ORIGINAL time bound specified
                //      store each value in subRhoVarList
                ////////////////////////////////////////////////////////////////

                ArrayList<String> subRhoVarList = new ArrayList<String>();

                // DIFFERENT FROM GLOBALLY OPERATOR
                // IN THAT ALL SIGNALS HAVE TO BE GENERATED
                // generate the body of the temporal operator at each time step of the time bound
                int bodyGenLowerBound = ((Int) this.lowerBound).toInt();
                int bodyGenUpperBound = ((Int) this.upperBound).toInt();

                if (this.param_l != null) {
                    bodyGenLowerBound = bodyGenLowerBound - ext_param_l_ub;
                }
                else if (this.param_r != null) {
                    bodyGenUpperBound = bodyGenUpperBound + ext_param_r_ub;
                }

                for (int i = bodyGenLowerBound; i <= bodyGenUpperBound; ++i) {
                    String bodyRhoVar   =   EmitEncoding.genNewVar("rho");
                    subRhoVarList.add(bodyRhoVar);

                    EmitEncodingResult bodyResult = this.body.emitEncoding(i + time, bodyRhoVar, isWeak);
                    Encoding bodyEncoding = bodyResult.getEncoding();
                    Encoding bodyDebug = bodyResult.getDebugEncoding();

                    e.append(bodyEncoding);
                    db.append(bodyDebug);
                }

                ///////////////////////////////////////////////////////////////////////////////
                // (2) assign array's each index to the decision variable in the subRhoVarList
                ///////////////////////////////////////////////////////////////////////////////

                String subRhoVarListVar = EmitEncoding.genNewVar("sub_rho_list");
                String leftIdxVar = EmitEncoding.genNewVar("l_idx");
                String rightIdxVar = EmitEncoding.genNewVar("r_idx");

                EmitEncodingResult assignArrayResult = EmitEncoding.assignArray(
                    subRhoVarListVar,
                    subRhoVarList,
                    leftIdxVar,
                    rightIdxVar
                    // ext_param_l,
                    // ext_param_l_ub,
                    // ext_param_r,
                    // ext_param_r_ub
                    );

                // note that arrayAssign also encode constraints about the bound
                Encoding arrayAssignEncoding = assignArrayResult.getEncoding();
                Encoding arrayAssignDebug = assignArrayResult.getDebugEncoding();

                e.append(arrayAssignEncoding);
                db.append(arrayAssignDebug);

                //  encode additional G-specific constraint for weakening
                // F will increase time bound range (shrinking time bound)

                // for each of the 3 cases:
                // weaken left
                // weaken right
                // weaken both side

                ///////////////////////////////////////////////////////////////////////////////
                // (3) Add constraint on left and right index decision var for bounding and weakening
                ///////////////////////////////////////////////////////////////////////////////

                // left and right idx var should be pointing to the subset (without addition generated body rho var)

                // weakening left only
                // note that array starts with 1
                if (this.param_r == null) {
                    // left = 1 + left_ub - delta (left_param)
                    // right = size()
                    // 0 <= left_param <= left_upper_bound 
                    e.append(EmitEncoding.constraint(String.format("%s = 1 + %s - %s", leftIdxVar, ext_param_l_ub, ext_param_l)));
                    e.append(EmitEncoding.constraint(String.format("%s = %d", rightIdxVar, subRhoVarList.size())));

                    e.append(EmitEncoding.constraint(String.format("%s >= 0", ext_param_l)));
                    e.append(EmitEncoding.constraint(String.format("%s <= %s", ext_param_l, ext_param_l_ub)));
                }

                // weakening right only
                else if (this.param_l == null) {
                    // left = 1
                    // right = size() - right_ub + delta (right_param)
                    // 0 <= right_param <= right_upper_bound 
                    e.append(EmitEncoding.constraint(String.format("%s = 1", leftIdxVar)));
                    e.append(EmitEncoding.constraint(String.format("%s = %d - %s + %s", rightIdxVar, subRhoVarList.size(), ext_param_r_ub, ext_param_r)));

                    e.append(EmitEncoding.constraint(String.format("%s >= 0", ext_param_r)));
                    e.append(EmitEncoding.constraint(String.format("%s <= %s", ext_param_r, ext_param_r_ub)));
                }
                
                // weakening both sides
                else if (this.param_l != null && this.param_r != null) {
                    // left = 1 + delta (left_param)
                    // right = size() - delta (right_param)
                    // 0 <= left_param <= left_upper_bound 
                    // 0 <= right_param <= right_upper_bound 

                    e.append(EmitEncoding.constraint(String.format("%s = 1 + %s - %s", leftIdxVar, ext_param_l_ub, ext_param_l)));
                    e.append(EmitEncoding.constraint(String.format("%s = %d", rightIdxVar, subRhoVarList.size())));

                    e.append(EmitEncoding.constraint(String.format("%s = 1", leftIdxVar)));
                    e.append(EmitEncoding.constraint(String.format("%s = %d - %s + %s", rightIdxVar, subRhoVarList.size(), ext_param_r_ub, ext_param_r)));

                    e.append(EmitEncoding.constraint(String.format("%s >= 0", ext_param_r)));
                    e.append(EmitEncoding.constraint(String.format("%s <= %s", ext_param_r, ext_param_r_ub)));
                }
                
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // (4) Find the minimum of the subset of the subRhoVarList (in minizinc) bounded by i and j as the robustness value
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                EmitEncodingResult subRhoListRobustnessResult = EmitEncoding.subRhoListRobustness(rhoVar, subRhoVarListVar, EmitEncoding.Extrema.MAX, leftIdxVar, rightIdxVar, subRhoVarList.size());

                Encoding subRhoListRobustnessEncoding = subRhoListRobustnessResult.getEncoding();
                Encoding subRhoListRobustnessDebug = subRhoListRobustnessResult.getDebugEncoding();

                e.append(subRhoListRobustnessEncoding);
                db.append(subRhoListRobustnessDebug);

            }
        }

        EmitEncodingResult er = new EmitEncodingResult(e, db);
        return er;

    
    }

    // encode two negations
    // (1) not F[](phi) === G[](not phi)
    // (2) not G[](phi) === F[](not phi)
    @Override public AST negate() {
        // (1) not F[](phi) === G[](not phi)
        if (this.op.equals("F")) {
            return new TempExpr("G", this.lowerBound, this.upperBound, new LogicExpr("not", null, this.body));
        }
        // (2) not G[](phi) === F[](not phi)
        if (this.op.equals("G")) {
            return new TempExpr("F", this.lowerBound, this.upperBound, new LogicExpr("not", null, this.body));

        }

        // otherwise return null
        return null;
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TempExpr(\n");
        sb.append("    " + this.op + "\n");
        sb.append("    " + this.lowerBound + "\n");
        sb.append("    " + this.upperBound + "\n");
        sb.append("    " + this.body + "\n)\n");
        return sb.toString();
    }
}

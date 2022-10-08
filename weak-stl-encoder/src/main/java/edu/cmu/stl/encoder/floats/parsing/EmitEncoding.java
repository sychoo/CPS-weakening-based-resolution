// 2022-03-06 11:33:36
// Simon Chu
//
// emit encoding given expressions, return MILP encoding
// purpose: off-load the burden of Compilation Listener
//
// note that EmitEncoding can only consist of static functions

package edu.cmu.stl.encoder.floats.parsing;

import java.util.List;
import java.util.ArrayList;

import edu.cmu.stl.encoder.floats.util.Encoding;
import edu.cmu.stl.encoder.floats.util.EmitEncodingResult;
import edu.cmu.stl.encoder.floats.util.Signal;

public class EmitEncoding {
    
    public static int varCount = 0; // for generating fresh variables
    public static int statCount = 0; // for generating fresh variable for statements
    public static boolean statWeakenFlag = false; // by default, do not consider weakening, generate the original robustness
    public static ArrayList<ArrayList<String>> minimizeList = new ArrayList<ArrayList<String>>(); // list for the final minimization
    public static ArrayList<String> paramList = new ArrayList<String>(); // list of weakening parameters

    public enum STLExpr {
        LOGICEXPR,
        GLOBALLY,
        EVENTUALLY;
    }
    
    public enum Extrema {
        MIN,
        MAX
    }

       // the prelude without a signal encoding, used for STL-float
       public static EmitEncodingResult prelude() {
        Encoding e = new Encoding();
        Encoding db = new Encoding();

        e.append("% BEGIN OF STL ENCODING\n");
        e.append(constDecl("int", "M = 1000")); // big integer M = 1000
        // e.append(varDecl("int", "rho"));        // overall robustness/top-level rho

        db.append("M = 1000\n");
        db.append("declare 'rho'\n");

        EmitEncodingResult er = new EmitEncodingResult(e, db);
        return er;
    }

    // public static EmitEncodingResult prelude(Signal signal) {
    //     Encoding e = new Encoding();
    //     Encoding db = new Encoding();

    //     if (signal != null) {
    //         e.append("% BEGIN OF SIGNAL ENCODING\n");
    //         e.append(signal.emitEncoding());
    //         e.append("% END OF SIGNAL ENCODING\n\n");
    //     }

    //     e.append("% BEGIN OF STL ENCODING\n");
    //     e.append(constDecl("int", "M = 1000")); // big integer M = 1000
    //     // e.append(varDecl("int", "rho"));        // overall robustness/top-level rho

    //     if (signal != null) {
    //         db.append("encoding signal : length = " + signal.length() + ", field length = " + signal.getFieldNames().size() + "\n");
    //     }
        
    //     db.append("M = 1000\n");
    //     db.append("declare 'rho'\n");

    //     EmitEncodingResult er = new EmitEncodingResult(e, db);
    //     return er;
    // }

    public static EmitEncodingResult epilogue() {
        Encoding e = new Encoding();
        Encoding db = new Encoding();
       
        // encode rho_w (weakened robustness >= 0)
        for (int i = 0; i < minimizeList.size(); ++i) {
            // get all the rho_w's
            // append rho_w > 0 constraints

            // make sure weakened parameters achieves positive robustness
            e.append(constraint(minimizeList.get(i).get(0) + " > " + 0));
            // e.append(constraint(minimizeList.get(i).get(0) + " >= " + 0));
        }

        // encode solve minimize (rho_stl_1_w - rho_stl_1) + (rho_stl_2_w - rho_stl_2) + ...
        if (minimizeList.size() > 0) {
            e.append("solve minimize ");
        }

        for (int i = 0; i < minimizeList.size(); ++i) {
            // get all the rho_w's
            // append rho_w > 0 constraints
            e.append("(" + minimizeList.get(i).get(0) + " - " + minimizeList.get(i).get(1) + ")");

            if (i != minimizeList.size() - 1) {
                e.append("+");
            } else {
                e.append(";\n");
            }
        }

        EmitEncodingResult er = new EmitEncodingResult(e, db);
        return er;
    }

    // Assign an encoding to an robustness decision variable
    // for single compExpr
    // rhoVar = arithExpr
    public static EmitEncodingResult assign(String rhoVar, Encoding arithExpr) {
        Encoding e = new Encoding();
        Encoding db = new Encoding();
        
        e.append(constraint(rhoVar + " <= " + arithExpr.toString()));
        e.append(constraint(rhoVar + " >= " + arithExpr.toString()));

        db.append("rho assign: rho = " + arithExpr.toString() + "\n");

        EmitEncodingResult er = new EmitEncodingResult(e, db);
        return er;
    }

    // assign array's each index with a individual decision variable
    // i.e.
    // arrayVar[0] = rho_1
    // arrayVar[1] = rho_2
    public static EmitEncodingResult assignArray(String arrayVar, ArrayList subRhoVarList, String leftIdxVar, String rightIdxVar) {
        Encoding e = new Encoding();
        Encoding db = new Encoding();

        // declare the subRhoVar list array variable
        e.append(array1d("1", String.valueOf(subRhoVarList.size()), "var int", arrayVar));
        e.append(varDecl("int", leftIdxVar));
        e.append(varDecl("int", rightIdxVar));

        // add constraints to correspond each element of the decision array to the corresponding index of the subRhoVarList
        e.append("constraint\n");
        for (int i = 0; i < subRhoVarList.size(); ++i) {
            e.append(String.format("    %s[%d] = %s", arrayVar, i + 1, subRhoVarList.get(i)));
            if (i != subRhoVarList.size() - 1) {
                e.append(" /\\\n");
            }
        }
        e.append(";\n");

        // add constraint:
        //
        // leftIdx <= rightIdx
        // 1 <= leftIdx <= size()
        // 1 <= rightIdx <= size()
        e.append(constraint(String.format("%s <= %s", leftIdxVar, rightIdxVar)));
        e.append(constraint(String.format("%s >= 1", leftIdxVar)));
        e.append(constraint(String.format("%s <= %d", leftIdxVar, subRhoVarList.size())));
        e.append(constraint(String.format("%s >= 1", rightIdxVar)));
        e.append(constraint(String.format("%s <= %d", rightIdxVar, subRhoVarList.size())));

        EmitEncodingResult er = new EmitEncodingResult(e, db);
        return er;
    }

    // for pairwise logic expression
    // p /\ q
    // q \/ r
    public static EmitEncodingResult extrema(Extrema extr, String rhoVar, String lhsRhoVar, String rhsRhoVar) {
        ArrayList<String> subRhoVarList = new ArrayList<String>() {{
            add(lhsRhoVar);
            add(rhsRhoVar);
        }};

        return chainedExtrema(extr, rhoVar, subRhoVarList);
    }


    // for logical expr list
    // p /\ q /\ r /\ z...
    // conjunction encoding
    // conjunction --> min
    // disjunction --> max
    public static EmitEncodingResult chainedExtrema(Extrema extr, String rhoVar, ArrayList<String> subRhoVarList) {
        Encoding e = new Encoding();
        Encoding db = new Encoding();

        // String rhoVar   =   genNewVar("rho");   // robustness var for sub goal
        String pVar     =   genNewVar("p");     // p var for sub goal

        // e.append(varDecl("int", rhoVar));               
        // e.append(array1d("1", "2", "var 0..1", pVar));  
        e.append(array1d("1", String.valueOf(subRhoVarList.size()), "var 0..1", pVar));  

        // condition 1
        // sum(p) = 1
        e.append(constraint(String.format("sum(%s) = 1", pVar)));

        // condition 2
            // robustness must be smaller than individual robustness
        if (extr == Extrema.MIN) {
            for (int i = 0; i < subRhoVarList.size(); ++i) {
                e.append(constraint(String.format("%s <= %s", rhoVar, subRhoVarList.get(i))));
                // e.append(constraint(String.format("%s <= %s", rhoVar, expr2)));
            }
        }

        // robustness must be greater than individual robustness
        else if (extr == Extrema.MAX) {
            for (int i = 0; i < subRhoVarList.size(); ++i) {
                e.append(constraint(String.format("%s >= %s", rhoVar, subRhoVarList.get(i))));
            }
        }

        // condition 3
        // involve big integer constant `M` and decision array `p`
        // find the minimum of the two robustness
        for (int i = 0; i < subRhoVarList.size(); ++i) {
            e.append(constraint(String.format("%s >= %s - (1 - %s[%d]) * M", rhoVar, subRhoVarList.get(i), pVar, i + 1))); // minizinc index start with 1
            e.append(constraint(String.format("%s <= %s + (1 - %s[%d]) * M", rhoVar, subRhoVarList.get(i), pVar, i + 1)));
        }

        // e.append(constraint(String.format("%s >= %s - (1 - %s[1]) * M", rhoVar, expr1, pVar)));
        // e.append(constraint(String.format("%s >= %s - (1 - %s[2]) * M", rhoVar, expr2, pVar)));

        // e.append(constraint(String.format("%s <= %s + (1 - %s[1]) * M", rhoVar, expr1, pVar)));
        // e.append(constraint(String.format("%s <= %s + (1 - %s[2]) * M", rhoVar, expr2, pVar)));

        String extrStr = extr == Extrema.MAX ? "max" : "min";
        db.append("chained extrema: " + extrStr + "(" + subRhoVarList + ")\n");
        EmitEncodingResult er = new EmitEncodingResult(e, db);

        // return er;
        return er;
    }

    /*************************************************************************************/
    /***** Primitive Constructs: topLevel, extrema, extremaList, extremeList2Extrema *****/
    /*************************************************************************************/
    

    /*********************************/
    /*********** TOP LEVEL ***********/
    /*********************************/

    // set top-level rho to last intermediate value/variable in compStack
    // rho = compStack.top()
    public static EmitEncodingResult topLevel(String expr) {
        Encoding e = new Encoding();
        Encoding db = new Encoding();
        
        e.append(constraint("rho <= " + expr));
        e.append(constraint("rho >= " + expr));

        db.append("topLevel: rho = " + expr + "\n");

        EmitEncodingResult er = new EmitEncodingResult(e, db);
        return er;
    }

    /*******************************/
    /*********** EXTREMA ***********/
    /*******************************/

    // encode min/max function
    // result = min(rho_expr1, rho_expr2) or
    // result = max(rho_expr1, rho_expr2)
    // expr1 and expr2 must be normalized comparison expression
    // condition 2 is the only difference between min and max encoding
    public static EmitEncodingResult extrema2(String expr1, String expr2, Extrema extr) {
        Encoding e = new Encoding();
        Encoding db = new Encoding();

        String rhoVar   =   genNewVar("rho");   // robustness var for sub goal
        String pVar     =   genNewVar("p");     // p var for sub goal

        e.append(varDecl("float", rhoVar));               
        e.append(array1d("1", "2", "var 0..1", pVar));  

        // condition 1
        // sum(p) = 1
        e.append(constraint(String.format("sum(%s) = 1", pVar)));

        // condition 2
        if (extr == Extrema.MIN) {
            // robustness must be smaller than individual robustness
            e.append(constraint(String.format("%s <= %s", rhoVar, expr1)));
            e.append(constraint(String.format("%s <= %s", rhoVar, expr2)));
        } else if (extr == Extrema.MAX) {
            // robustness must be greater than individual robustness
            e.append(constraint(String.format("%s >= %s", rhoVar, expr1)));
            e.append(constraint(String.format("%s >= %s", rhoVar, expr2)));
        }

        // condition 3
        // involve big integer constant `M` and decision array `p`
        // find the minimum of the two robustness
        e.append(constraint(String.format("%s >= %s - (1 - %s[1]) * M", rhoVar, expr1, pVar)));
        e.append(constraint(String.format("%s >= %s - (1 - %s[2]) * M", rhoVar, expr2, pVar)));

        e.append(constraint(String.format("%s <= %s + (1 - %s[1]) * M", rhoVar, expr1, pVar)));
        e.append(constraint(String.format("%s <= %s + (1 - %s[2]) * M", rhoVar, expr2, pVar)));

        String extrStr = extr == Extrema.MAX ? "max" : "min";
        db.append("extrema: " + extrStr + "(" + expr1 + ", " + expr2 + ")\n");
        // EmitEncodingResult er = new EmitEncodingResult(e, rhoVar, db);

        // return er;
        return null;
    }

    /**************************************************/
    /*********** EXTREME LIST (SINGLE EXPR) ***********/
    /**************************************************/

    // extrema list with one expr argument
    // for comparison expression nested within temporal operator only
    // G[0, 1](x > 1)
    public static EmitEncodingResult extremaList2(String expr, String lowerTimeBound, String upperTimeBound) {
        // return extremaList(expr, null, null, lowerTimeBound, upperTimeBound);
        Encoding e = new Encoding();
        Encoding db = new Encoding();
     
        // String rhoVar = genNewVar("rho");
        String rhoSubVar = genNewVar("rho_sub");
        String pVar = genNewVar("p");

        // e.append(varDecl("int", rhoVar));

        ///////////////////////////////////////////////////////////
        // calculate comp expr robustness for entire time horizon
        ///////////////////////////////////////////////////////////

        // array declarations
        // rho_sub_n array (1d)
        e.append(array1d("1", upperTimeBound + "-" + lowerTimeBound + " + 1", "var int", rhoSubVar));

        // p array (1d) - different from topLevelGloballyLogicExpr
        e.append(array1d("1", upperTimeBound + "-" + lowerTimeBound + " + 1", "var 0..1", pVar));

        // directly calculate the robustness of AP at each time step
        ArrayList<String> cond2BodyList = new ArrayList<>();
        cond2BodyList.add(String.format("%s[idx] >= %s", rhoSubVar, expr));
        cond2BodyList.add(String.format("%s[idx] <= %s", rhoSubVar, expr));

        e.append(
            constraint(
                forallExprList(
                    "idx",
                    lowerTimeBound + " + 1",
                    upperTimeBound + " + 1",
                    cond2BodyList
                )
            )
        );

        db.append("extremaList (single expr): " + rhoSubVar + " = (expr = " + expr + "), time bound = [" + lowerTimeBound + ", " + upperTimeBound + "]\n");

        // EmitEncodingResult er = new EmitEncodingResult(e, rhoSubVar, db);
        // return er;
        return null;

    }


    /*****************************************************/
    /*********** EXTREME LIST (PAIR-WISE EXPR) ***********/
    /*****************************************************/

    // when expr1 and expr2 are both non-list types
    public static EmitEncodingResult extremaList(String expr1, String expr2, Extrema extr, String lowerTimeBound, String upperTimeBound) {
        return extremaList(expr1, expr2, extr, lowerTimeBound, upperTimeBound, false);
    }

    // if leftIsList flag is set to true, expr1 must of of list type, expr2 must be of value (int) type
    // extrema list with two expr argument
    // for pair-wise logic expressions nested within the globally operator
    // G[0, 1](x > 1 /\ y > 1)
    public static EmitEncodingResult extremaList(String expr1, String expr2, Extrema extr, String lowerTimeBound, String upperTimeBound, Boolean expr1IsList) {
        Encoding e = new Encoding();
        Encoding db = new Encoding();

        String rhoSubVar    =   genNewVar("rho_sub");   // robustness sub var for time horizon
        String pVar         =   genNewVar("p");         // p var for sub goal

        //////////////////////////////////////////////////////////////////////////////
        // calculate pair-wise logical expression robustness for entire time horizon
        //////////////////////////////////////////////////////////////////////////////

        // array declarations

        // rho_sub_n array
        e.append(array1d("1", upperTimeBound + "-" + lowerTimeBound + " + 1", "var int", rhoSubVar));

        // p array (for pair-wise comparison expression calculation)
        e.append(array2d("1", upperTimeBound + "-" + lowerTimeBound + " + 1", "1", "2", "var 0..1", pVar));

        // condition 1
        // sum of each sub array of p is 1
        e.append(
            constraint(
                forallExpr(
                    "idx", 
                    lowerTimeBound + " + 1",
                    upperTimeBound + " + 1",
                    String.format("sum (logic_expr_idx in 1..2) (%s[idx, logic_expr_idx]) = 1", pVar)
                )
            )
        );

        // condition 2
        // robustness must be minimum of all
        ArrayList<String> cond2BodyList = new ArrayList<>();

        if (extr == Extrema.MIN) {
            // case when expr1 is not list
            // compare signal idx to expr1 value
            if (!expr1IsList) {
                cond2BodyList.add(String.format("%s[idx] <= %s", rhoSubVar, expr1));
            }

            // case when expr1 is list
            // compare signal idx to expr1 idx
            else {
                cond2BodyList.add(String.format("%s[idx] <= %s[idx]", rhoSubVar, expr1));
            }
        } else if (extr == Extrema.MAX) {
            // case when expr1 is not list
            // compare signal idx to expr1 value
            if (!expr1IsList) {
                cond2BodyList.add(String.format("%s[idx] >= %s", rhoSubVar, expr1));
            }

            // case when expr1 is list
            // compare signal idx to expr1 idx
            else {
                cond2BodyList.add(String.format("%s[idx] >= %s[idx]", rhoSubVar, expr1));
            }
        }


        if (extr == Extrema.MIN) {
            cond2BodyList.add(String.format("%s[idx] <= %s", rhoSubVar, expr2));
        }
        else if (extr == Extrema.MAX) {
            cond2BodyList.add(String.format("%s[idx] >= %s", rhoSubVar, expr2));
        }
        
        e.append(
            constraint(
                forallExprList(
                    "idx",
                    lowerTimeBound + " + 1",
                    upperTimeBound + " + 1",
                    cond2BodyList
                )
            )
        );

        // condition 3
        // big M and P decision array
        ArrayList<String> cond3BodyList = new ArrayList<>();
        if (!expr1IsList) {
            cond3BodyList.add(String.format("%s[idx] >= %s - (1 - %s[idx, 1]) * M", rhoSubVar, expr1, pVar));
            cond3BodyList.add(String.format("%s[idx] <= %s + (1 - %s[idx, 1]) * M", rhoSubVar, expr1, pVar));
        }
        else {
            cond3BodyList.add(String.format("%s[idx] >= %s[idx] - (1 - %s[idx, 1]) * M", rhoSubVar, expr1, pVar));
            cond3BodyList.add(String.format("%s[idx] <= %s[idx] + (1 - %s[idx, 1]) * M", rhoSubVar, expr1, pVar));
        }
        cond3BodyList.add(String.format("%s[idx] >= %s - (1 - %s[idx, 2]) * M", rhoSubVar, expr2, pVar));
        cond3BodyList.add(String.format("%s[idx] <= %s + (1 - %s[idx, 2]) * M", rhoSubVar, expr2, pVar));

        e.append(
            constraint(
                forallExprList(
                    "idx",
                    lowerTimeBound + " + 1",
                    upperTimeBound + " + 1",
                    cond3BodyList
                )
            )
        );

        String extrStr = extr == Extrema.MAX ? "max" : "min";
        db.append(String.format("extremaList (pair-wise): " + rhoSubVar + " = %s(expr1 = " + expr1 + ", expr2 = " + expr2 + "), expr1 is list?: " + expr1IsList + "\n", extrStr));
        // EmitEncodingResult er = new EmitEncodingResult(e, rhoSubVar, db);

        // return er;
        return null;
    }

    public static EmitEncodingResult extremaList2Extrema(String rhoSubVar, Extrema extr, String lowerTimeBound, String upperTimeBound) {
        ////////////////////////////////////////////////////////////
        // calculate overall robustness and push it onto the stack
        ////////////////////////////////////////////////////////////

        Encoding e = new Encoding();
        Encoding db = new Encoding();

        String rhoVar  =   genNewVar("rho");       // robustness var for sub goal
        String pSubVar = genNewVar("p_sub");
            
        e.append(varDecl("float", rhoVar));


        // e.append(array1d(lowerTimeBound + " + 1", upperTimeBound + " + 1", "var 0..1", pSubVar));
        // rho_sub_n array
        e.append(array1d("1", upperTimeBound + "-" + lowerTimeBound + " + 1", "var 0..1", pSubVar));
        
        // condition 1
        // sum of p is 1
        e.append(constraint(String.format("sum (%s) = 1", pSubVar)));

        String cond2Body = null;

        if (extr == Extrema.MIN)
           cond2Body = String.format("%s <= %s[idx]", rhoVar, rhoSubVar);
        else if (extr == Extrema.MAX)
            cond2Body = String.format("%s >= %s[idx]", rhoVar, rhoSubVar);
        else
            throw new RuntimeException("Not Implemented!");

        // condition 2
        // robustness is smaller than all
        e.append(
            constraint(
                forallExpr(
                    "idx",
                    "1",
                    upperTimeBound + " - " + lowerTimeBound + " + 1",
                    cond2Body
                )
            )
        );

        // condition 3
        // P and big M
        ArrayList<String> cond3OverallBodyList = new ArrayList<>();
        cond3OverallBodyList.add(String.format("%s >= %s[idx] - (1 - %s[idx]) * M", rhoVar, rhoSubVar, pSubVar));
        cond3OverallBodyList.add(String.format("%s <= %s[idx] + (1 - %s[idx]) * M", rhoVar, rhoSubVar, pSubVar));

        e.append(
            constraint(
                forallExprList(
                    "idx",
                    "1",
                    upperTimeBound + " - " + lowerTimeBound + " + 1",
                    cond3OverallBodyList                        
                )
            )
        );
        
        String extrStr = extr == Extrema.MAX ? "max" : "min";

        db.append(String.format("extremeList2Extrema: %s = %s(%s)\n", rhoVar, extrStr, rhoSubVar));
        // EmitEncodingResult er = new EmitEncodingResult(e, rhoVar, db);

        // return er;
        return null;
    }





    // calculate the extrema of the array 
    public static EmitEncodingResult subRhoListRobustness(String rhoVar, String rhoSubVar, Extrema extr, String leftIdxVar, String rightIdxVar, int arraySize) {
        ////////////////////////////////////////////////////////////
        // calculate overall robustness and push it onto the stack
        ////////////////////////////////////////////////////////////

        Encoding e = new Encoding();
        Encoding db = new Encoding();

        // String rhoVar  =   genNewVar("rho");       // robustness var for sub goal
        String pSubVar = genNewVar("p_sub");
        String tempIdxVar1 = genNewVar("idx");
        String tempIdxVar2 = genNewVar("idx");
        String tempIdxVar3 = genNewVar("idx");

        // e.append(varDecl("int", rhoVar));


        // e.append(array1d(lowerTimeBound + " + 1", upperTimeBound + " + 1", "var 0..1", pSubVar));
        // rho_sub_n array
        // e.append(array1d("1", upperTimeBound + "-" + lowerTimeBound + " + 1", "var 0..1", pSubVar));
        e.append(array1d("1", String.valueOf(arraySize), "var 0..1", pSubVar));
        
        // condition 1
        // sum of p is 1
        e.append(constraint(String.format("sum (%s in %s..%s)(%s[%s]) = 1", tempIdxVar1, leftIdxVar, rightIdxVar, pSubVar, tempIdxVar1)));

        String cond2Body = null;

        if (extr == Extrema.MIN)
           cond2Body = String.format("%s <= %s[%s]", rhoVar, rhoSubVar, tempIdxVar2);
        else if (extr == Extrema.MAX)
            cond2Body = String.format("%s >= %s[%s]", rhoVar, rhoSubVar, tempIdxVar2);
        else
            throw new RuntimeException("Not Implemented!");

        // condition 2
        // robustness is smaller than all
        e.append(
            constraint(
                forallExpr(
                    tempIdxVar2,
                    leftIdxVar,
                    rightIdxVar,
                    // upperTimeBound + " - " + lowerTimeBound + " + 1",
                    cond2Body
                )
            )
        );

        // condition 3
        // P and big M
        ArrayList<String> cond3OverallBodyList = new ArrayList<>();
        cond3OverallBodyList.add(String.format("%s >= %s[%s] - (1 - %s[%s]) * M", rhoVar, rhoSubVar, tempIdxVar3, pSubVar, tempIdxVar3));
        cond3OverallBodyList.add(String.format("%s <= %s[%s] + (1 - %s[%s]) * M", rhoVar, rhoSubVar, tempIdxVar3, pSubVar, tempIdxVar3));

        e.append(
            constraint(
                forallExprList(
                    tempIdxVar3,
                    leftIdxVar,
                    // upperTimeBound + " - " + lowerTimeBound + " + 1",
                    rightIdxVar,
                    cond3OverallBodyList                        
                )
            )
        );
        
        String extrStr = extr == Extrema.MAX ? "max" : "min";

        db.append(String.format("extremeList2Extrema: %s = %s(%s)\n", rhoVar, extrStr, rhoSubVar));
        EmitEncodingResult er = new EmitEncodingResult(e, db);

        return er;
        // return null;
    }



    /****************************/
    /***** Helper Functions *****/
    /****************************/

    public static String genNewVar(String var) {
        return var + "_" + (++varCount);

    }

    public static String genNewStatVar(String var) {
        return var + "_" + (++statCount);
    }
    
    // declare decision variables
    public static String varDecl(String type, String var) {
        return "var " + type + ": " + var + ";\n";
    }

    public static String constDecl(String type, String body) {
        return type + ": " + body + ";\n";
    }

    // str: variable from signal
    public static String signalVar(String var) {
        return "sig_var_" + var;
    }

    // signal var reference
    public static String signalVarRef(String var) {
        return signalVar(var) + "[idx]";
    }

    public static String constraint(String body) {
        return new String("constraint " + body + ";\n");
    }

    public static String array1d(String lowerBound, String upperBound, String type, String name) {
            return String.format("array[%s..%s] of %s: %s;\n", lowerBound, upperBound, type, name);
    }


    public static String array1d(String lowerBound, String upperBound, String type, String name, String body) {
            return String.format("array[%s..%s] of %s: %s = %s;\n", lowerBound, upperBound, type, name, body);
    }

    public static String array2d(String lowerBound1, String upperBound1, String lowerBound2, String upperBound2, String type, String name) {
            return String.format("array[%s..%s, %s..%s] of %s: %s;\n", lowerBound1, upperBound1, lowerBound2, upperBound2, type, name);
    }

    public static String forallExpr(String indexVar, String lowerBound, String upperBound, String body) {
            return String.format("forall (%s in %s..%s) (\n\t%s\n)\n", indexVar, lowerBound, upperBound, body);
    }

    public static String forallExprList(String indexVar, String lowerBound, String upperBound, List<String> bodyList) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bodyList.size(); ++i) {
                sb.append(String.format("\t%s", bodyList.get(i)));
                if (i != bodyList.size() - 1)
                    sb.append("/\\\n");
                else
                    sb.append("\n");

            }
            return String.format("forall (%s in %s..%s) (\n%s)\n", indexVar, lowerBound, upperBound, sb.toString());
    }

}

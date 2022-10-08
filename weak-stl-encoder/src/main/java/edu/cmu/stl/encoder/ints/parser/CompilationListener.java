// Tue Feb 15 14:30:22 EST 2022
// Simon Chu
//
// purpose: encode the signal along with the STL formula to MILP constraints in
//          MiniZinc format
//
// Main translation program
//
// note that minizinc array starts with index 1

package edu.cmu.stl.encoder.ints.parser;

import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;

import java.util.Stack;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.stl.encoder.parser.STLIntBaseListener;
import edu.cmu.stl.encoder.parser.STLIntParser;
import edu.cmu.stl.encoder.ints.util.Encoding;
import edu.cmu.stl.encoder.ints.util.Signal;
import edu.cmu.stl.encoder.ints.util.EmitEncodingResult;
import edu.cmu.stl.encoder.ints.encoder.AST;
import edu.cmu.stl.encoder.ints.encoder.ast.*;
import edu.cmu.stl.encoder.ints.encoder.EmitEncoding;

public class CompilationListener extends STLIntBaseListener {
    STLIntParser parser;
    Encoding encoding;
    Encoding debugEncoding;
    Signal signal;

    Stack<AST> arithStack;   // temporary storage for results of arithmetic expression

    // everything in compStack can be directly evaluated in terms of robustness
    Stack<AST> compStack;    // stores the normalized form of AP

    public CompilationListener(STLIntParser parser, Signal signal) {
        arithStack = new Stack<AST>();
        compStack = new Stack<AST>();
        // logicOpStack = new Stack<LogicExpr>();
        // tempOpStack = new Stack<STLExpr>();
        // timeBoundStack = new Stack<ArrayList<String>>();
        encoding = new Encoding();
        debugEncoding = new Encoding();

        this.parser = parser;
        this.signal = signal;
        
        // prelude
        EmitEncodingResult er = EmitEncoding.prelude(signal);
        this.encoding.append(er.getEncoding());
        this.debugEncoding.append(er.getDebugEncoding());
    }

    public CompilationListener(STLIntParser parser, Signal signal, String envString) {
        this(parser, signal);
        this.encoding.append(envString);
    }
    /*********************/
    /***** STL Stack *****/
    /*********************/

    // upon entering a program (list of statements)
    @Override
    public void enterProg(STLIntParser.ProgContext ctx) {
        // reset the weakening flag upon entering the program
        EmitEncoding.statWeakenFlag = false;
    }

    // program, encode the "solve minimize" statement
    @Override
    public void exitProg(STLIntParser.ProgContext ctx) {

        // epilogue (opposite of prelude)
        EmitEncodingResult er = EmitEncoding.epilogue();
        this.encoding.append(er.getEncoding());
        this.debugEncoding.append(er.getDebugEncoding());

        // reset var counter and stat counter
        EmitEncoding.varCount = 0;
        EmitEncoding.statCount = 0;
        EmitEncoding.statWeakenFlag = false;
        EmitEncoding.minimizeList.clear();
        EmitEncoding.paramList.clear();
    }

    // upon entering a statement
    @Override
    public void enterStat(STLIntParser.StatContext ctx) {
        // reset of weaken flag (default, do not generate robustness for weakening)
        EmitEncoding.statWeakenFlag = false;
        EmitEncoding.paramList.clear(); // clear paramList upon the entry of statements
    }

    // exit statements
    @Override
    public void exitStat(STLIntParser.StatContext ctx) {

        if (this.compStack.size() == 1) {
            // evaluate the AST in the compStack
            AST expr = this.compStack.pop();
            // EmitEncodingResult er = EmitEncoding.topLevel(expr);
            System.out.println(expr);

            // encode the original robustness without weakening
            // provide the starting time '1' (array in minizinc start with 1)
            // and the decision variable name to be stored in
            String statVar = EmitEncoding.genNewStatVar("rho_stl");
            EmitEncodingResult er = expr.emitEncoding(1, statVar, false);

            // append the encoding
            this.encoding.append("% BEGIN OF STL ENCODING FOR " + statVar + "\n");
            this.encoding.append(er.getEncoding());
            this.debugEncoding.append(er.getDebugEncoding());
            this.encoding.append("% END OF STL ENCODING FOR " + statVar + "\n");

            // emit weakening encoding if there are weakened expressions (statWeakenFlag is set to true)
            //
            // note that statWeakenFlag only check if there's any weakening expression in the statement (if at all)
            // it does not indicate individual AST's weakening. Those information are passed via AST construct.
            //
            // this is used to generate the epilogue all the way at the end
            // this flag need to be set to true everytime a weakened statement in the parse tree is entered/exited
            if (EmitEncoding.statWeakenFlag) {
                // encode the robustness with weakening if encountered weakening statement in parse tree
                String statVarWeak = statVar + "_w";
                EmitEncodingResult er_w = expr.emitEncoding(1, statVarWeak, true);

                this.encoding.append("% BEGIN OF STL ENCODING FOR " + statVarWeak + "\n");

                // declare all weakening parameters before the weakening encoding
                for (String paramVar: EmitEncoding.paramList) {
                    this.encoding.append(EmitEncoding.varDecl("int", paramVar));
                }

                this.encoding.append(er_w.getEncoding());
                this.debugEncoding.append(er_w.getDebugEncoding());
                this.encoding.append("% END OF STL ENCODING FOR " + statVarWeak + "\n");

                // minimizing the difference between weakened rho - original rho
                ArrayList<String> rhoVarPair = new ArrayList<String>() {{
                    add(statVarWeak);
                    add(statVar);
                }};
                EmitEncoding.minimizeList.add(rhoVarPair);
            }
        }
    }

    //////////////////////////////////
    // logical expression for STL
    //////////////////////////////////

    // negation of STL expression is treated as a special case
    // @Override
    // public void exitStlNotExpr(STLIntParser.StlNotExprContext ctx) {
    //     AST stlExpr = this.compStack.pop();
    //     this.compStack.push(new LogicExpr("not", null, stlExpr));
    // }
    
    @Override
    public void exitStlAndExpr(STLIntParser.StlAndExprContext ctx) {
        AST left  = this.compStack.pop();
        AST right = this.compStack.pop();
        AST logicAST = new LogicExpr("and", left, right);
        this.compStack.push(logicAST);
    }

    @Override
    public void exitStlOrExpr(STLIntParser.StlOrExprContext ctx) {
        AST right = this.compStack.pop();
        AST left  = this.compStack.pop();
        AST logicAST = new LogicExpr("or", left, right);
        this.compStack.push(logicAST);
    }

    @Override
    public void exitStlNotExpr(STLIntParser.StlNotExprContext ctx) {
        AST right = this.compStack.pop();
        AST left = null; // set left to null since only 1 arg is passed to not
        AST logicAST = new LogicExpr("not", left, right);
        this.compStack.push(logicAST);
    }

    @Override
    public void exitStlImpExpr(STLIntParser.StlImpExprContext ctx) {
        AST right = this.compStack.pop();
        AST left  = this.compStack.pop();
        AST logicAST = new LogicExpr("imp", left, right);
        this.compStack.push(logicAST);
    }

    @Override
    public void exitStlIffExpr(STLIntParser.StlIffExprContext ctx) {
        AST right = this.compStack.pop();
        AST left  = this.compStack.pop();
        AST logicAST = new LogicExpr("iff", left, right);
        this.compStack.push(logicAST);
    }

    // @Override
    // public void exitStl(STLIntParser.StlContext ctx) {
        
    //     // globally operator
    //     if (ctx.globally() != null) {
    //         // construct TempExpr AST
    //         AST expr = this.compStack.pop();
    //         AST tempExpr = new TempExpr(ctx.globally().op.getText(),
    //                                     new Int(ctx.globally().timeBound().INT(0).getText()),
    //                                     new Int(ctx.globally().timeBound().INT(1).getText()),
    //                                     expr);
    //         this.compStack.push(tempExpr);
    //     } else if (ctx.eventually() != null) {
    //         // construct TempExpr AST
    //         AST expr = this.compStack.pop();
    //         AST tempExpr = new TempExpr(ctx.eventually().op.getText(),
    //                                     new Int(ctx.eventually().timeBound().INT(0).getText()),
    //                                     new Int(ctx.eventually().timeBound().INT(1).getText()),
    //                                     expr);
    //         this.compStack.push(tempExpr);
    //     }

    //         // System.out.println(compStack);

    // }

    @Override
    public void exitGlobally(STLIntParser.GloballyContext ctx) {
        
            // construct TempExpr AST
            AST expr = this.compStack.pop();
            AST tempExpr = new TempExpr(ctx.op.getText(),
                                        new Int(ctx.timeBound().INT(0).getText()),
                                        new Int(ctx.timeBound().INT(1).getText()),
                                        expr);
            this.compStack.push(tempExpr);
    }

    @Override
    public void exitEventually(STLIntParser.EventuallyContext ctx) {
            // construct TempExpr AST
            AST expr = this.compStack.pop();
            AST tempExpr = new TempExpr(ctx.op.getText(),
                                        new Int(ctx.timeBound().INT(0).getText()),
                                        new Int(ctx.timeBound().INT(1).getText()),
                                        expr);
            this.compStack.push(tempExpr);
    }

    // // helper function for exiting weak temporal operator
    // public AST exitWeakTemp(STLIntParser.WeakGloballyContext globallyCtx, STLIntParser.WeakEventuallyContext eventuallyCtx) {

    // }

    @Override
    public void exitWeakGlobally(STLIntParser.WeakGloballyContext ctx) {

        // Weakening occurred in globally time bound
        EmitEncoding.statWeakenFlag = true;

        // logic expr embedded in STL
        AST expr = this.compStack.pop();
        AST tempExpr = null;

        // left time bound weakening
        // [<< 0 >>( delta,  1 ),  2]
        //  INT(0)  Id(0)  Int(1) INT(2)
        if (ctx.weakLeftTimeBound() != null) {
            tempExpr = new TempExpr(ctx.op.getText(),
                                        new Int(ctx.weakLeftTimeBound().INT(0).getText()),
                                        new Int(ctx.weakLeftTimeBound().INT(2).getText()),
                                        expr,
                                        new Id(ctx.weakLeftTimeBound().ID().getText(), false), // weakening parameter
                                        new Int(ctx.weakLeftTimeBound().INT(1).getText()), // weakening parameter upper bound
                                        null,
                                        null
                                    );
            EmitEncoding.paramList.add(ctx.weakLeftTimeBound().ID().getText()); // add weakening parameter to the global list
        }

        // right time bound weakening
        // [0,    << 2 >>( delta,  1 )]
        //  INT(0)  INT(1) Id(0)  Int(2)
        else if (ctx.weakRightTimeBound() != null) {
            tempExpr = new TempExpr(ctx.op.getText(),
                                        new Int(ctx.weakRightTimeBound().INT(0).getText()),
                                        new Int(ctx.weakRightTimeBound().INT(1).getText()),
                                        expr,
                                        null,
                                        null,
                                        new Id(ctx.weakRightTimeBound().ID().getText(), false), // weakening parameter
                                        new Int(ctx.weakRightTimeBound().INT(2).getText()) // weakening parameter upper bound
                                    );
            EmitEncoding.paramList.add(ctx.weakRightTimeBound().ID().getText()); // add weakening parameter to the global list
        }

        // both time bound weakening
        // [[<< 0 >>( delta,  1 ),    << 2 >>( delta,  1 )]
        //    INT(0)   Id(0)  INT(1)    INT(2)  Id(1)  INT(3)
        else if (ctx.weakBothTimeBound() != null) {
            tempExpr = new TempExpr(ctx.op.getText(),
                                        new Int(ctx.weakBothTimeBound().INT(0).getText()),
                                        new Int(ctx.weakBothTimeBound().INT(2).getText()),
                                        expr,
                                        new Id(ctx.weakBothTimeBound().ID(0).getText(), false), // weakening parameter
                                        new Int(ctx.weakBothTimeBound().INT(1).getText()), // weakening parameter upper bound
                                        new Id(ctx.weakBothTimeBound().ID(1).getText(), false), // weakening parameter
                                        new Int(ctx.weakBothTimeBound().INT(3).getText()) // weakening parameter upper bound
                                    );
            EmitEncoding.paramList.add(ctx.weakBothTimeBound().ID(0).getText()); // add weakening parameter to the global list
            EmitEncoding.paramList.add(ctx.weakBothTimeBound().ID(1).getText()); // add weakening parameter to the global list
        }

        this.compStack.push(tempExpr);
    }

    @Override
    public void exitWeakEventually(STLIntParser.WeakEventuallyContext ctx) {

        // Weakening occurred in globally time bound
        EmitEncoding.statWeakenFlag = true;

        // logic expr embedded in STL
        AST expr = this.compStack.pop();
        AST tempExpr = null;

        // left time bound weakening
        // [<< 0 >>( delta,  1 ),  2]
        //  INT(0)  Id(0)  Int(1) INT(2)
        if (ctx.weakLeftTimeBound() != null) {
            tempExpr = new TempExpr(ctx.op.getText(),
                                        new Int(ctx.weakLeftTimeBound().INT(0).getText()),
                                        new Int(ctx.weakLeftTimeBound().INT(2).getText()),
                                        expr,
                                        new Id(ctx.weakLeftTimeBound().ID().getText(), false), // weakening parameter
                                        new Int(ctx.weakLeftTimeBound().INT(1).getText()), // weakening parameter upper bound
                                        null,
                                        null
                                    );
            EmitEncoding.paramList.add(ctx.weakLeftTimeBound().ID().getText()); // add weakening parameter to the global list
        }

        // right time bound weakening
        // [0,    << 2 >>( delta,  1 )]
        //  INT(0)  INT(1) Id(0)  Int(2)
        else if (ctx.weakRightTimeBound() != null) {
            tempExpr = new TempExpr(ctx.op.getText(),
                                        new Int(ctx.weakRightTimeBound().INT(0).getText()),
                                        new Int(ctx.weakRightTimeBound().INT(1).getText()),
                                        expr,
                                        null,
                                        null,
                                        new Id(ctx.weakRightTimeBound().ID().getText(), false), // weakening parameter
                                        new Int(ctx.weakRightTimeBound().INT(2).getText()) // weakening parameter upper bound
                                    );
            EmitEncoding.paramList.add(ctx.weakRightTimeBound().ID().getText()); // add weakening parameter to the global list
        }

        // both time bound weakening
        // [[<< 0 >>( delta,  1 ),    << 2 >>( delta,  1 )]
        //    INT(0)   Id(0)  INT(1)    INT(2)  Id(1)  INT(3)
        else if (ctx.weakBothTimeBound() != null) {
            tempExpr = new TempExpr(ctx.op.getText(),
                                        new Int(ctx.weakBothTimeBound().INT(0).getText()),
                                        new Int(ctx.weakBothTimeBound().INT(2).getText()),
                                        expr,
                                        new Id(ctx.weakBothTimeBound().ID(0).getText(), false), // weakening parameter
                                        new Int(ctx.weakBothTimeBound().INT(1).getText()), // weakening parameter upper bound
                                        new Id(ctx.weakBothTimeBound().ID(1).getText(), false), // weakening parameter
                                        new Int(ctx.weakBothTimeBound().INT(3).getText()) // weakening parameter upper bound
                                    );
            EmitEncoding.paramList.add(ctx.weakBothTimeBound().ID(0).getText()); // add weakening parameter to the global list
            EmitEncoding.paramList.add(ctx.weakBothTimeBound().ID(1).getText()); // add weakening parameter to the global list
        }

        this.compStack.push(tempExpr);
    }

    /**********************/
    /***** Comp Stack *****/
    /**********************/

    // logical expression
    // encoding conjunction
    @Override
    public void exitAndExpr(STLIntParser.AndExprContext ctx) {
        AST left  = this.compStack.pop();
        AST right = this.compStack.pop();
        AST logicAST = new LogicExpr("and", left, right);
        this.compStack.push(logicAST);
    }

    @Override
    public void exitOrExpr(STLIntParser.OrExprContext ctx) {
        AST right = this.compStack.pop();
        AST left  = this.compStack.pop();
        AST logicAST = new LogicExpr("or", left, right);
        this.compStack.push(logicAST);
    }

    @Override
    public void exitNotExpr(STLIntParser.NotExprContext ctx) {
        AST right = this.compStack.pop();
        AST left = null; // set left to null since only 1 arg is passed to not
        AST logicAST = new LogicExpr("not", left, right);
        this.compStack.push(logicAST);
    }

    @Override
    public void exitImpExpr(STLIntParser.ImpExprContext ctx) {
        AST right = this.compStack.pop();
        AST left  = this.compStack.pop();
        AST logicAST = new LogicExpr("imp", left, right);
        this.compStack.push(logicAST);
    }

    @Override
    public void exitIffExpr(STLIntParser.IffExprContext ctx) {
        AST right = this.compStack.pop();
        AST left  = this.compStack.pop();
        AST logicAST = new LogicExpr("iff", left, right);
        this.compStack.push(logicAST);
    }

    /****************************/
    /***** Arithmetic Stack *****/
    /****************************/

    @Override 
    public void exitId(STLIntParser.IdContext ctx) {
        AST idAST = new Id(ctx.getText());
        this.arithStack.push(idAST);
    }

    @Override 
    public void exitInt(STLIntParser.IntContext ctx) { 
        AST intAST = new Int(ctx.getText());
        this.arithStack.push(intAST);
    }

    @Override 
    public void exitParens(STLIntParser.ParensContext ctx) { 
        // AST parensAST = new Parens(ctx.arithExpr()
    }

    // walk through and copy the arith expr via stack without evaluating them
    @Override 
    public void exitMulDivExpr(STLIntParser.MulDivExprContext ctx) { 
        AST right = this.arithStack.pop();
        AST left  = this.arithStack.pop();

        AST arithAST = new ArithExpr(ctx.op.getText(), left, right);
        this.arithStack.push(arithAST);
    }

    @Override 
    public void exitAddSubExpr(STLIntParser.AddSubExprContext ctx) { 
        AST right = this.arithStack.pop();
        AST left  = this.arithStack.pop();

        AST arithAST = new ArithExpr(ctx.op.getText(), left, right);
        this.arithStack.push(arithAST);
    }

    // push the normalized form of compExpr onto the stack
    @Override
    public void exitNormalCompExpr(STLIntParser.NormalCompExprContext ctx) {
        AST right = this.arithStack.pop();
        AST left  = this.arithStack.pop();

        AST compAST = new CompExpr(ctx.op.getText(), left, right);
        this.compStack.push(compAST);
    }
    
    // TODO: implement weakend compExpr
    // push the normalized form of compExpr onto the stack
    @Override
    public void exitWeakCompExpr(STLIntParser.WeakCompExprContext ctx) {
        // weakening occurred in the STL expression
        EmitEncoding.statWeakenFlag = true;
        AST right = this.arithStack.pop();
        AST left  = this.arithStack.pop();
        AST param  = new Id(ctx.ID().getText(), false); // obtain weakening parameter, convert to non-signal var parameter
        AST param_ub = new Int(ctx.INT().getText());
        AST compAST = new CompExpr(ctx.op.getText(), left, right, param, param_ub);
        EmitEncoding.paramList.add(ctx.ID().getText()); // add weakening parameter to the global list
        // System.out.println("adding " + ctx.ID().getText());
        this.compStack.push(compAST);
    }
    
    /****************************/
    /***** Helper Functions *****/
    /****************************/
        
    public Encoding getEncoding() {
        return this.encoding;
    }

    public Encoding getDebugEncoding() {
        return this.debugEncoding;
    }
}

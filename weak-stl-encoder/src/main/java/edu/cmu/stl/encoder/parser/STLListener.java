// Generated from STL.g4 by ANTLR 4.10.1

	package edu.cmu.stl.encoder.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link STLParser}.
 */
public interface STLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link STLParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(STLParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(STLParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link STLParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(STLParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(STLParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlWeakGlobally}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlWeakGlobally(STLParser.StlWeakGloballyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlWeakGlobally}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlWeakGlobally(STLParser.StlWeakGloballyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlLogicExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlLogicExpr(STLParser.StlLogicExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlLogicExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlLogicExpr(STLParser.StlLogicExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlImpExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlImpExpr(STLParser.StlImpExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlImpExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlImpExpr(STLParser.StlImpExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlGlobally}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlGlobally(STLParser.StlGloballyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlGlobally}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlGlobally(STLParser.StlGloballyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlIffExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlIffExpr(STLParser.StlIffExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlIffExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlIffExpr(STLParser.StlIffExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlWeakEventually}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlWeakEventually(STLParser.StlWeakEventuallyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlWeakEventually}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlWeakEventually(STLParser.StlWeakEventuallyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlNotExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlNotExpr(STLParser.StlNotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlNotExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlNotExpr(STLParser.StlNotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlAndExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlAndExpr(STLParser.StlAndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlAndExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlAndExpr(STLParser.StlAndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlOrExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlOrExpr(STLParser.StlOrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlOrExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlOrExpr(STLParser.StlOrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlParenExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlParenExpr(STLParser.StlParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlParenExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlParenExpr(STLParser.StlParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlEventually}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlEventually(STLParser.StlEventuallyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlEventually}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlEventually(STLParser.StlEventuallyContext ctx);
	/**
	 * Enter a parse tree produced by {@link STLParser#globally}.
	 * @param ctx the parse tree
	 */
	void enterGlobally(STLParser.GloballyContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLParser#globally}.
	 * @param ctx the parse tree
	 */
	void exitGlobally(STLParser.GloballyContext ctx);
	/**
	 * Enter a parse tree produced by {@link STLParser#weakGlobally}.
	 * @param ctx the parse tree
	 */
	void enterWeakGlobally(STLParser.WeakGloballyContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLParser#weakGlobally}.
	 * @param ctx the parse tree
	 */
	void exitWeakGlobally(STLParser.WeakGloballyContext ctx);
	/**
	 * Enter a parse tree produced by {@link STLParser#eventually}.
	 * @param ctx the parse tree
	 */
	void enterEventually(STLParser.EventuallyContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLParser#eventually}.
	 * @param ctx the parse tree
	 */
	void exitEventually(STLParser.EventuallyContext ctx);
	/**
	 * Enter a parse tree produced by {@link STLParser#weakEventually}.
	 * @param ctx the parse tree
	 */
	void enterWeakEventually(STLParser.WeakEventuallyContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLParser#weakEventually}.
	 * @param ctx the parse tree
	 */
	void exitWeakEventually(STLParser.WeakEventuallyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(STLParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(STLParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImpExpr}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterImpExpr(STLParser.ImpExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImpExpr}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitImpExpr(STLParser.ImpExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicParens}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicParens(STLParser.LogicParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicParens}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicParens(STLParser.LogicParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicCompExpr}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicCompExpr(STLParser.LogicCompExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicCompExpr}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicCompExpr(STLParser.LogicCompExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(STLParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(STLParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IffExpr}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterIffExpr(STLParser.IffExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IffExpr}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitIffExpr(STLParser.IffExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrExpr}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(STLParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrExpr}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(STLParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NormalCompExpr}
	 * labeled alternative in {@link STLParser#compExpr}.
	 * @param ctx the parse tree
	 */
	void enterNormalCompExpr(STLParser.NormalCompExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NormalCompExpr}
	 * labeled alternative in {@link STLParser#compExpr}.
	 * @param ctx the parse tree
	 */
	void exitNormalCompExpr(STLParser.NormalCompExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WeakCompExpr}
	 * labeled alternative in {@link STLParser#compExpr}.
	 * @param ctx the parse tree
	 */
	void enterWeakCompExpr(STLParser.WeakCompExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WeakCompExpr}
	 * labeled alternative in {@link STLParser#compExpr}.
	 * @param ctx the parse tree
	 */
	void exitWeakCompExpr(STLParser.WeakCompExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Float}
	 * labeled alternative in {@link STLParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void enterFloat(STLParser.FloatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Float}
	 * labeled alternative in {@link STLParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void exitFloat(STLParser.FloatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulDivExpr}
	 * labeled alternative in {@link STLParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void enterMulDivExpr(STLParser.MulDivExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDivExpr}
	 * labeled alternative in {@link STLParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void exitMulDivExpr(STLParser.MulDivExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link STLParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void enterParens(STLParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link STLParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void exitParens(STLParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Id}
	 * labeled alternative in {@link STLParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void enterId(STLParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Id}
	 * labeled alternative in {@link STLParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void exitId(STLParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSubExpr}
	 * labeled alternative in {@link STLParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void enterAddSubExpr(STLParser.AddSubExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSubExpr}
	 * labeled alternative in {@link STLParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void exitAddSubExpr(STLParser.AddSubExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Int}
	 * labeled alternative in {@link STLParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void enterInt(STLParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Int}
	 * labeled alternative in {@link STLParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void exitInt(STLParser.IntContext ctx);
	/**
	 * Enter a parse tree produced by {@link STLParser#timeBound}.
	 * @param ctx the parse tree
	 */
	void enterTimeBound(STLParser.TimeBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLParser#timeBound}.
	 * @param ctx the parse tree
	 */
	void exitTimeBound(STLParser.TimeBoundContext ctx);
	/**
	 * Enter a parse tree produced by {@link STLParser#weakLeftTimeBound}.
	 * @param ctx the parse tree
	 */
	void enterWeakLeftTimeBound(STLParser.WeakLeftTimeBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLParser#weakLeftTimeBound}.
	 * @param ctx the parse tree
	 */
	void exitWeakLeftTimeBound(STLParser.WeakLeftTimeBoundContext ctx);
	/**
	 * Enter a parse tree produced by {@link STLParser#weakRightTimeBound}.
	 * @param ctx the parse tree
	 */
	void enterWeakRightTimeBound(STLParser.WeakRightTimeBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLParser#weakRightTimeBound}.
	 * @param ctx the parse tree
	 */
	void exitWeakRightTimeBound(STLParser.WeakRightTimeBoundContext ctx);
	/**
	 * Enter a parse tree produced by {@link STLParser#weakBothTimeBound}.
	 * @param ctx the parse tree
	 */
	void enterWeakBothTimeBound(STLParser.WeakBothTimeBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLParser#weakBothTimeBound}.
	 * @param ctx the parse tree
	 */
	void exitWeakBothTimeBound(STLParser.WeakBothTimeBoundContext ctx);
}
// Generated from STLInt.g4 by ANTLR 4.10.1

	package edu.cmu.stl.encoder.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link STLIntParser}.
 */
public interface STLIntListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link STLIntParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(STLIntParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLIntParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(STLIntParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link STLIntParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(STLIntParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLIntParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(STLIntParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlWeakGlobally}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlWeakGlobally(STLIntParser.StlWeakGloballyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlWeakGlobally}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlWeakGlobally(STLIntParser.StlWeakGloballyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlLogicExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlLogicExpr(STLIntParser.StlLogicExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlLogicExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlLogicExpr(STLIntParser.StlLogicExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlImpExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlImpExpr(STLIntParser.StlImpExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlImpExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlImpExpr(STLIntParser.StlImpExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlGlobally}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlGlobally(STLIntParser.StlGloballyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlGlobally}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlGlobally(STLIntParser.StlGloballyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlIffExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlIffExpr(STLIntParser.StlIffExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlIffExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlIffExpr(STLIntParser.StlIffExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlWeakEventually}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlWeakEventually(STLIntParser.StlWeakEventuallyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlWeakEventually}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlWeakEventually(STLIntParser.StlWeakEventuallyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlNotExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlNotExpr(STLIntParser.StlNotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlNotExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlNotExpr(STLIntParser.StlNotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlAndExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlAndExpr(STLIntParser.StlAndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlAndExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlAndExpr(STLIntParser.StlAndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlOrExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlOrExpr(STLIntParser.StlOrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlOrExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlOrExpr(STLIntParser.StlOrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlParenExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlParenExpr(STLIntParser.StlParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlParenExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlParenExpr(STLIntParser.StlParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StlEventually}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void enterStlEventually(STLIntParser.StlEventuallyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StlEventually}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 */
	void exitStlEventually(STLIntParser.StlEventuallyContext ctx);
	/**
	 * Enter a parse tree produced by {@link STLIntParser#globally}.
	 * @param ctx the parse tree
	 */
	void enterGlobally(STLIntParser.GloballyContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLIntParser#globally}.
	 * @param ctx the parse tree
	 */
	void exitGlobally(STLIntParser.GloballyContext ctx);
	/**
	 * Enter a parse tree produced by {@link STLIntParser#weakGlobally}.
	 * @param ctx the parse tree
	 */
	void enterWeakGlobally(STLIntParser.WeakGloballyContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLIntParser#weakGlobally}.
	 * @param ctx the parse tree
	 */
	void exitWeakGlobally(STLIntParser.WeakGloballyContext ctx);
	/**
	 * Enter a parse tree produced by {@link STLIntParser#eventually}.
	 * @param ctx the parse tree
	 */
	void enterEventually(STLIntParser.EventuallyContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLIntParser#eventually}.
	 * @param ctx the parse tree
	 */
	void exitEventually(STLIntParser.EventuallyContext ctx);
	/**
	 * Enter a parse tree produced by {@link STLIntParser#weakEventually}.
	 * @param ctx the parse tree
	 */
	void enterWeakEventually(STLIntParser.WeakEventuallyContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLIntParser#weakEventually}.
	 * @param ctx the parse tree
	 */
	void exitWeakEventually(STLIntParser.WeakEventuallyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(STLIntParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(STLIntParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImpExpr}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterImpExpr(STLIntParser.ImpExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImpExpr}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitImpExpr(STLIntParser.ImpExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicParens}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicParens(STLIntParser.LogicParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicParens}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicParens(STLIntParser.LogicParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicCompExpr}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicCompExpr(STLIntParser.LogicCompExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicCompExpr}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicCompExpr(STLIntParser.LogicCompExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(STLIntParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(STLIntParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IffExpr}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterIffExpr(STLIntParser.IffExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IffExpr}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitIffExpr(STLIntParser.IffExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrExpr}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(STLIntParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrExpr}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(STLIntParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NormalCompExpr}
	 * labeled alternative in {@link STLIntParser#compExpr}.
	 * @param ctx the parse tree
	 */
	void enterNormalCompExpr(STLIntParser.NormalCompExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NormalCompExpr}
	 * labeled alternative in {@link STLIntParser#compExpr}.
	 * @param ctx the parse tree
	 */
	void exitNormalCompExpr(STLIntParser.NormalCompExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WeakCompExpr}
	 * labeled alternative in {@link STLIntParser#compExpr}.
	 * @param ctx the parse tree
	 */
	void enterWeakCompExpr(STLIntParser.WeakCompExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WeakCompExpr}
	 * labeled alternative in {@link STLIntParser#compExpr}.
	 * @param ctx the parse tree
	 */
	void exitWeakCompExpr(STLIntParser.WeakCompExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulDivExpr}
	 * labeled alternative in {@link STLIntParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void enterMulDivExpr(STLIntParser.MulDivExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDivExpr}
	 * labeled alternative in {@link STLIntParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void exitMulDivExpr(STLIntParser.MulDivExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link STLIntParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void enterParens(STLIntParser.ParensContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link STLIntParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void exitParens(STLIntParser.ParensContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Id}
	 * labeled alternative in {@link STLIntParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void enterId(STLIntParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Id}
	 * labeled alternative in {@link STLIntParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void exitId(STLIntParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSubExpr}
	 * labeled alternative in {@link STLIntParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void enterAddSubExpr(STLIntParser.AddSubExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSubExpr}
	 * labeled alternative in {@link STLIntParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void exitAddSubExpr(STLIntParser.AddSubExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Int}
	 * labeled alternative in {@link STLIntParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void enterInt(STLIntParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Int}
	 * labeled alternative in {@link STLIntParser#arithExpr}.
	 * @param ctx the parse tree
	 */
	void exitInt(STLIntParser.IntContext ctx);
	/**
	 * Enter a parse tree produced by {@link STLIntParser#timeBound}.
	 * @param ctx the parse tree
	 */
	void enterTimeBound(STLIntParser.TimeBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLIntParser#timeBound}.
	 * @param ctx the parse tree
	 */
	void exitTimeBound(STLIntParser.TimeBoundContext ctx);
	/**
	 * Enter a parse tree produced by {@link STLIntParser#weakLeftTimeBound}.
	 * @param ctx the parse tree
	 */
	void enterWeakLeftTimeBound(STLIntParser.WeakLeftTimeBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLIntParser#weakLeftTimeBound}.
	 * @param ctx the parse tree
	 */
	void exitWeakLeftTimeBound(STLIntParser.WeakLeftTimeBoundContext ctx);
	/**
	 * Enter a parse tree produced by {@link STLIntParser#weakRightTimeBound}.
	 * @param ctx the parse tree
	 */
	void enterWeakRightTimeBound(STLIntParser.WeakRightTimeBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLIntParser#weakRightTimeBound}.
	 * @param ctx the parse tree
	 */
	void exitWeakRightTimeBound(STLIntParser.WeakRightTimeBoundContext ctx);
	/**
	 * Enter a parse tree produced by {@link STLIntParser#weakBothTimeBound}.
	 * @param ctx the parse tree
	 */
	void enterWeakBothTimeBound(STLIntParser.WeakBothTimeBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link STLIntParser#weakBothTimeBound}.
	 * @param ctx the parse tree
	 */
	void exitWeakBothTimeBound(STLIntParser.WeakBothTimeBoundContext ctx);
}
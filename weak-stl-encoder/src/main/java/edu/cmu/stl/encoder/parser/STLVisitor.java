// Generated from STL.g4 by ANTLR 4.10.1

	package edu.cmu.stl.encoder.parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link STLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface STLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link STLParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(STLParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link STLParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat(STLParser.StatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlWeakGlobally}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlWeakGlobally(STLParser.StlWeakGloballyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlLogicExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlLogicExpr(STLParser.StlLogicExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlImpExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlImpExpr(STLParser.StlImpExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlGlobally}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlGlobally(STLParser.StlGloballyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlIffExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlIffExpr(STLParser.StlIffExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlWeakEventually}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlWeakEventually(STLParser.StlWeakEventuallyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlNotExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlNotExpr(STLParser.StlNotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlAndExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlAndExpr(STLParser.StlAndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlOrExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlOrExpr(STLParser.StlOrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlParenExpr}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlParenExpr(STLParser.StlParenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlEventually}
	 * labeled alternative in {@link STLParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlEventually(STLParser.StlEventuallyContext ctx);
	/**
	 * Visit a parse tree produced by {@link STLParser#globally}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobally(STLParser.GloballyContext ctx);
	/**
	 * Visit a parse tree produced by {@link STLParser#weakGlobally}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeakGlobally(STLParser.WeakGloballyContext ctx);
	/**
	 * Visit a parse tree produced by {@link STLParser#eventually}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEventually(STLParser.EventuallyContext ctx);
	/**
	 * Visit a parse tree produced by {@link STLParser#weakEventually}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeakEventually(STLParser.WeakEventuallyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(STLParser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImpExpr}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImpExpr(STLParser.ImpExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicParens}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicParens(STLParser.LogicParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicCompExpr}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicCompExpr(STLParser.LogicCompExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpr(STLParser.NotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IffExpr}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIffExpr(STLParser.IffExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrExpr}
	 * labeled alternative in {@link STLParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpr(STLParser.OrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NormalCompExpr}
	 * labeled alternative in {@link STLParser#compExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalCompExpr(STLParser.NormalCompExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WeakCompExpr}
	 * labeled alternative in {@link STLParser#compExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeakCompExpr(STLParser.WeakCompExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Float}
	 * labeled alternative in {@link STLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloat(STLParser.FloatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulDivExpr}
	 * labeled alternative in {@link STLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDivExpr(STLParser.MulDivExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link STLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(STLParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Id}
	 * labeled alternative in {@link STLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(STLParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddSubExpr}
	 * labeled alternative in {@link STLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSubExpr(STLParser.AddSubExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Int}
	 * labeled alternative in {@link STLParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt(STLParser.IntContext ctx);
	/**
	 * Visit a parse tree produced by {@link STLParser#timeBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeBound(STLParser.TimeBoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link STLParser#weakLeftTimeBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeakLeftTimeBound(STLParser.WeakLeftTimeBoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link STLParser#weakRightTimeBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeakRightTimeBound(STLParser.WeakRightTimeBoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link STLParser#weakBothTimeBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeakBothTimeBound(STLParser.WeakBothTimeBoundContext ctx);
}
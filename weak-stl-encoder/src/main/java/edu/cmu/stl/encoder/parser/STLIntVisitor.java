// Generated from STLInt.g4 by ANTLR 4.10.1

	package edu.cmu.stl.encoder.parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link STLIntParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface STLIntVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link STLIntParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(STLIntParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link STLIntParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat(STLIntParser.StatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlWeakGlobally}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlWeakGlobally(STLIntParser.StlWeakGloballyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlLogicExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlLogicExpr(STLIntParser.StlLogicExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlImpExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlImpExpr(STLIntParser.StlImpExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlGlobally}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlGlobally(STLIntParser.StlGloballyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlIffExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlIffExpr(STLIntParser.StlIffExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlWeakEventually}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlWeakEventually(STLIntParser.StlWeakEventuallyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlNotExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlNotExpr(STLIntParser.StlNotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlAndExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlAndExpr(STLIntParser.StlAndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlOrExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlOrExpr(STLIntParser.StlOrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlParenExpr}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlParenExpr(STLIntParser.StlParenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StlEventually}
	 * labeled alternative in {@link STLIntParser#stl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStlEventually(STLIntParser.StlEventuallyContext ctx);
	/**
	 * Visit a parse tree produced by {@link STLIntParser#globally}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobally(STLIntParser.GloballyContext ctx);
	/**
	 * Visit a parse tree produced by {@link STLIntParser#weakGlobally}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeakGlobally(STLIntParser.WeakGloballyContext ctx);
	/**
	 * Visit a parse tree produced by {@link STLIntParser#eventually}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEventually(STLIntParser.EventuallyContext ctx);
	/**
	 * Visit a parse tree produced by {@link STLIntParser#weakEventually}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeakEventually(STLIntParser.WeakEventuallyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(STLIntParser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImpExpr}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImpExpr(STLIntParser.ImpExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicParens}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicParens(STLIntParser.LogicParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicCompExpr}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicCompExpr(STLIntParser.LogicCompExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpr(STLIntParser.NotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IffExpr}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIffExpr(STLIntParser.IffExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrExpr}
	 * labeled alternative in {@link STLIntParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpr(STLIntParser.OrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NormalCompExpr}
	 * labeled alternative in {@link STLIntParser#compExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalCompExpr(STLIntParser.NormalCompExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WeakCompExpr}
	 * labeled alternative in {@link STLIntParser#compExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeakCompExpr(STLIntParser.WeakCompExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulDivExpr}
	 * labeled alternative in {@link STLIntParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDivExpr(STLIntParser.MulDivExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Parens}
	 * labeled alternative in {@link STLIntParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(STLIntParser.ParensContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Id}
	 * labeled alternative in {@link STLIntParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(STLIntParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddSubExpr}
	 * labeled alternative in {@link STLIntParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSubExpr(STLIntParser.AddSubExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Int}
	 * labeled alternative in {@link STLIntParser#arithExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt(STLIntParser.IntContext ctx);
	/**
	 * Visit a parse tree produced by {@link STLIntParser#timeBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeBound(STLIntParser.TimeBoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link STLIntParser#weakLeftTimeBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeakLeftTimeBound(STLIntParser.WeakLeftTimeBoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link STLIntParser#weakRightTimeBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeakRightTimeBound(STLIntParser.WeakRightTimeBoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link STLIntParser#weakBothTimeBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWeakBothTimeBound(STLIntParser.WeakBothTimeBoundContext ctx);
}
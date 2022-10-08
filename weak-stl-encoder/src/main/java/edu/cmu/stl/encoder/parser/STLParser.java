// Generated from STL.g4 by ANTLR 4.10.1

	package edu.cmu.stl.encoder.parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class STLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, AND=11, OR=12, NOT=13, IMP=14, IFF=15, GT=16, GE=17, LT=18, LE=19, 
		MUL=20, DIV=21, ADD=22, SUB=23, ID=24, INT=25, FLOAT=26, NEWLINE=27, WS=28, 
		Comment=29, Comment2=30;
	public static final int
		RULE_prog = 0, RULE_stat = 1, RULE_stl = 2, RULE_globally = 3, RULE_weakGlobally = 4, 
		RULE_eventually = 5, RULE_weakEventually = 6, RULE_logicExpr = 7, RULE_compExpr = 8, 
		RULE_arithExpr = 9, RULE_timeBound = 10, RULE_weakLeftTimeBound = 11, 
		RULE_weakRightTimeBound = 12, RULE_weakBothTimeBound = 13;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "stat", "stl", "globally", "weakGlobally", "eventually", "weakEventually", 
			"logicExpr", "compExpr", "arithExpr", "timeBound", "weakLeftTimeBound", 
			"weakRightTimeBound", "weakBothTimeBound"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "'G'", "'F'", "'='", "'<<'", "'>>'", "','", "'['", 
			"']'", "'/\\'", "'\\/'", "'not'", "'->'", "'<->'", "'>'", "'>='", "'<'", 
			"'<='", "'*'", "'/'", "'+'", "'-'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, "AND", 
			"OR", "NOT", "IMP", "IFF", "GT", "GE", "LT", "LE", "MUL", "DIV", "ADD", 
			"SUB", "ID", "INT", "FLOAT", "NEWLINE", "WS", "Comment", "Comment2"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "STL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public STLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgContext extends ParserRuleContext {
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitProg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(29); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(28);
				stat();
				}
				}
				setState(31); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__3) | (1L << T__5) | (1L << NOT) | (1L << ID) | (1L << INT) | (1L << FLOAT) | (1L << NEWLINE))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatContext extends ParserRuleContext {
		public StlContext stl() {
			return getRuleContext(StlContext.class,0);
		}
		public TerminalNode NEWLINE() { return getToken(STLParser.NEWLINE, 0); }
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatContext stat() throws RecognitionException {
		StatContext _localctx = new StatContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_stat);
		try {
			setState(38);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(33);
				stl(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(34);
				stl(0);
				setState(35);
				match(NEWLINE);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(37);
				match(NEWLINE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StlContext extends ParserRuleContext {
		public StlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stl; }
	 
		public StlContext() { }
		public void copyFrom(StlContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class StlWeakGloballyContext extends StlContext {
		public WeakGloballyContext weakGlobally() {
			return getRuleContext(WeakGloballyContext.class,0);
		}
		public StlWeakGloballyContext(StlContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterStlWeakGlobally(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitStlWeakGlobally(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitStlWeakGlobally(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StlLogicExprContext extends StlContext {
		public LogicExprContext logicExpr() {
			return getRuleContext(LogicExprContext.class,0);
		}
		public StlLogicExprContext(StlContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterStlLogicExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitStlLogicExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitStlLogicExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StlImpExprContext extends StlContext {
		public Token op;
		public List<StlContext> stl() {
			return getRuleContexts(StlContext.class);
		}
		public StlContext stl(int i) {
			return getRuleContext(StlContext.class,i);
		}
		public TerminalNode IMP() { return getToken(STLParser.IMP, 0); }
		public StlImpExprContext(StlContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterStlImpExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitStlImpExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitStlImpExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StlGloballyContext extends StlContext {
		public GloballyContext globally() {
			return getRuleContext(GloballyContext.class,0);
		}
		public StlGloballyContext(StlContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterStlGlobally(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitStlGlobally(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitStlGlobally(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StlIffExprContext extends StlContext {
		public Token op;
		public List<StlContext> stl() {
			return getRuleContexts(StlContext.class);
		}
		public StlContext stl(int i) {
			return getRuleContext(StlContext.class,i);
		}
		public TerminalNode IFF() { return getToken(STLParser.IFF, 0); }
		public StlIffExprContext(StlContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterStlIffExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitStlIffExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitStlIffExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StlWeakEventuallyContext extends StlContext {
		public WeakEventuallyContext weakEventually() {
			return getRuleContext(WeakEventuallyContext.class,0);
		}
		public StlWeakEventuallyContext(StlContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterStlWeakEventually(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitStlWeakEventually(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitStlWeakEventually(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StlNotExprContext extends StlContext {
		public Token op;
		public StlContext stl() {
			return getRuleContext(StlContext.class,0);
		}
		public TerminalNode NOT() { return getToken(STLParser.NOT, 0); }
		public StlNotExprContext(StlContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterStlNotExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitStlNotExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitStlNotExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StlAndExprContext extends StlContext {
		public Token op;
		public List<StlContext> stl() {
			return getRuleContexts(StlContext.class);
		}
		public StlContext stl(int i) {
			return getRuleContext(StlContext.class,i);
		}
		public TerminalNode AND() { return getToken(STLParser.AND, 0); }
		public StlAndExprContext(StlContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterStlAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitStlAndExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitStlAndExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StlOrExprContext extends StlContext {
		public Token op;
		public List<StlContext> stl() {
			return getRuleContexts(StlContext.class);
		}
		public StlContext stl(int i) {
			return getRuleContext(StlContext.class,i);
		}
		public TerminalNode OR() { return getToken(STLParser.OR, 0); }
		public StlOrExprContext(StlContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterStlOrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitStlOrExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitStlOrExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StlParenExprContext extends StlContext {
		public StlContext stl() {
			return getRuleContext(StlContext.class,0);
		}
		public StlParenExprContext(StlContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterStlParenExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitStlParenExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitStlParenExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StlEventuallyContext extends StlContext {
		public EventuallyContext eventually() {
			return getRuleContext(EventuallyContext.class,0);
		}
		public StlEventuallyContext(StlContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterStlEventually(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitStlEventually(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitStlEventually(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StlContext stl() throws RecognitionException {
		return stl(0);
	}

	private StlContext stl(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		StlContext _localctx = new StlContext(_ctx, _parentState);
		StlContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_stl, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				_localctx = new StlParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(41);
				match(T__0);
				setState(42);
				stl(0);
				setState(43);
				match(T__1);
				}
				break;
			case 2:
				{
				_localctx = new StlLogicExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(45);
				logicExpr(0);
				}
				break;
			case 3:
				{
				_localctx = new StlGloballyContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(46);
				globally();
				}
				break;
			case 4:
				{
				_localctx = new StlWeakGloballyContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(47);
				weakGlobally();
				}
				break;
			case 5:
				{
				_localctx = new StlEventuallyContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(48);
				eventually();
				}
				break;
			case 6:
				{
				_localctx = new StlWeakEventuallyContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(49);
				weakEventually();
				}
				break;
			case 7:
				{
				_localctx = new StlNotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(50);
				((StlNotExprContext)_localctx).op = match(NOT);
				setState(51);
				stl(5);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(68);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(66);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
					case 1:
						{
						_localctx = new StlAndExprContext(new StlContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_stl);
						setState(54);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(55);
						((StlAndExprContext)_localctx).op = match(AND);
						setState(56);
						stl(5);
						}
						break;
					case 2:
						{
						_localctx = new StlOrExprContext(new StlContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_stl);
						setState(57);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(58);
						((StlOrExprContext)_localctx).op = match(OR);
						setState(59);
						stl(4);
						}
						break;
					case 3:
						{
						_localctx = new StlImpExprContext(new StlContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_stl);
						setState(60);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(61);
						((StlImpExprContext)_localctx).op = match(IMP);
						setState(62);
						stl(3);
						}
						break;
					case 4:
						{
						_localctx = new StlIffExprContext(new StlContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_stl);
						setState(63);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(64);
						((StlIffExprContext)_localctx).op = match(IFF);
						setState(65);
						stl(2);
						}
						break;
					}
					} 
				}
				setState(70);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class GloballyContext extends ParserRuleContext {
		public Token op;
		public TimeBoundContext timeBound() {
			return getRuleContext(TimeBoundContext.class,0);
		}
		public StlContext stl() {
			return getRuleContext(StlContext.class,0);
		}
		public GloballyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_globally; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterGlobally(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitGlobally(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitGlobally(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GloballyContext globally() throws RecognitionException {
		GloballyContext _localctx = new GloballyContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_globally);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			((GloballyContext)_localctx).op = match(T__2);
			setState(72);
			timeBound();
			setState(73);
			match(T__0);
			setState(74);
			stl(0);
			setState(75);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WeakGloballyContext extends ParserRuleContext {
		public Token op;
		public WeakLeftTimeBoundContext weakLeftTimeBound() {
			return getRuleContext(WeakLeftTimeBoundContext.class,0);
		}
		public StlContext stl() {
			return getRuleContext(StlContext.class,0);
		}
		public WeakRightTimeBoundContext weakRightTimeBound() {
			return getRuleContext(WeakRightTimeBoundContext.class,0);
		}
		public WeakBothTimeBoundContext weakBothTimeBound() {
			return getRuleContext(WeakBothTimeBoundContext.class,0);
		}
		public WeakGloballyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_weakGlobally; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterWeakGlobally(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitWeakGlobally(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitWeakGlobally(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WeakGloballyContext weakGlobally() throws RecognitionException {
		WeakGloballyContext _localctx = new WeakGloballyContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_weakGlobally);
		try {
			setState(95);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(77);
				((WeakGloballyContext)_localctx).op = match(T__2);
				setState(78);
				weakLeftTimeBound();
				setState(79);
				match(T__0);
				setState(80);
				stl(0);
				setState(81);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(83);
				((WeakGloballyContext)_localctx).op = match(T__2);
				setState(84);
				weakRightTimeBound();
				setState(85);
				match(T__0);
				setState(86);
				stl(0);
				setState(87);
				match(T__1);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(89);
				((WeakGloballyContext)_localctx).op = match(T__2);
				setState(90);
				weakBothTimeBound();
				setState(91);
				match(T__0);
				setState(92);
				stl(0);
				setState(93);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EventuallyContext extends ParserRuleContext {
		public Token op;
		public TimeBoundContext timeBound() {
			return getRuleContext(TimeBoundContext.class,0);
		}
		public StlContext stl() {
			return getRuleContext(StlContext.class,0);
		}
		public EventuallyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eventually; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterEventually(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitEventually(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitEventually(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EventuallyContext eventually() throws RecognitionException {
		EventuallyContext _localctx = new EventuallyContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_eventually);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			((EventuallyContext)_localctx).op = match(T__3);
			setState(98);
			timeBound();
			setState(99);
			match(T__0);
			setState(100);
			stl(0);
			setState(101);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WeakEventuallyContext extends ParserRuleContext {
		public Token op;
		public WeakLeftTimeBoundContext weakLeftTimeBound() {
			return getRuleContext(WeakLeftTimeBoundContext.class,0);
		}
		public StlContext stl() {
			return getRuleContext(StlContext.class,0);
		}
		public WeakRightTimeBoundContext weakRightTimeBound() {
			return getRuleContext(WeakRightTimeBoundContext.class,0);
		}
		public WeakBothTimeBoundContext weakBothTimeBound() {
			return getRuleContext(WeakBothTimeBoundContext.class,0);
		}
		public WeakEventuallyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_weakEventually; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterWeakEventually(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitWeakEventually(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitWeakEventually(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WeakEventuallyContext weakEventually() throws RecognitionException {
		WeakEventuallyContext _localctx = new WeakEventuallyContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_weakEventually);
		try {
			setState(121);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(103);
				((WeakEventuallyContext)_localctx).op = match(T__3);
				setState(104);
				weakLeftTimeBound();
				setState(105);
				match(T__0);
				setState(106);
				stl(0);
				setState(107);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(109);
				((WeakEventuallyContext)_localctx).op = match(T__3);
				setState(110);
				weakRightTimeBound();
				setState(111);
				match(T__0);
				setState(112);
				stl(0);
				setState(113);
				match(T__1);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(115);
				((WeakEventuallyContext)_localctx).op = match(T__3);
				setState(116);
				weakBothTimeBound();
				setState(117);
				match(T__0);
				setState(118);
				stl(0);
				setState(119);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicExprContext extends ParserRuleContext {
		public LogicExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicExpr; }
	 
		public LogicExprContext() { }
		public void copyFrom(LogicExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AndExprContext extends LogicExprContext {
		public Token op;
		public List<LogicExprContext> logicExpr() {
			return getRuleContexts(LogicExprContext.class);
		}
		public LogicExprContext logicExpr(int i) {
			return getRuleContext(LogicExprContext.class,i);
		}
		public TerminalNode AND() { return getToken(STLParser.AND, 0); }
		public AndExprContext(LogicExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitAndExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitAndExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ImpExprContext extends LogicExprContext {
		public Token op;
		public List<LogicExprContext> logicExpr() {
			return getRuleContexts(LogicExprContext.class);
		}
		public LogicExprContext logicExpr(int i) {
			return getRuleContext(LogicExprContext.class,i);
		}
		public TerminalNode IMP() { return getToken(STLParser.IMP, 0); }
		public ImpExprContext(LogicExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterImpExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitImpExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitImpExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LogicParensContext extends LogicExprContext {
		public LogicExprContext logicExpr() {
			return getRuleContext(LogicExprContext.class,0);
		}
		public LogicParensContext(LogicExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterLogicParens(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitLogicParens(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitLogicParens(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LogicCompExprContext extends LogicExprContext {
		public CompExprContext compExpr() {
			return getRuleContext(CompExprContext.class,0);
		}
		public LogicCompExprContext(LogicExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterLogicCompExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitLogicCompExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitLogicCompExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotExprContext extends LogicExprContext {
		public Token op;
		public LogicExprContext logicExpr() {
			return getRuleContext(LogicExprContext.class,0);
		}
		public TerminalNode NOT() { return getToken(STLParser.NOT, 0); }
		public NotExprContext(LogicExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterNotExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitNotExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitNotExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IffExprContext extends LogicExprContext {
		public Token op;
		public List<LogicExprContext> logicExpr() {
			return getRuleContexts(LogicExprContext.class);
		}
		public LogicExprContext logicExpr(int i) {
			return getRuleContext(LogicExprContext.class,i);
		}
		public TerminalNode IFF() { return getToken(STLParser.IFF, 0); }
		public IffExprContext(LogicExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterIffExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitIffExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitIffExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrExprContext extends LogicExprContext {
		public Token op;
		public List<LogicExprContext> logicExpr() {
			return getRuleContexts(LogicExprContext.class);
		}
		public LogicExprContext logicExpr(int i) {
			return getRuleContext(LogicExprContext.class,i);
		}
		public TerminalNode OR() { return getToken(STLParser.OR, 0); }
		public OrExprContext(LogicExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterOrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitOrExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitOrExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicExprContext logicExpr() throws RecognitionException {
		return logicExpr(0);
	}

	private LogicExprContext logicExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LogicExprContext _localctx = new LogicExprContext(_ctx, _parentState);
		LogicExprContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_logicExpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				_localctx = new LogicParensContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(124);
				match(T__0);
				setState(125);
				logicExpr(0);
				setState(126);
				match(T__1);
				}
				break;
			case 2:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(128);
				((NotExprContext)_localctx).op = match(NOT);
				setState(129);
				logicExpr(6);
				}
				break;
			case 3:
				{
				_localctx = new LogicCompExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(130);
				compExpr();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(147);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(145);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
					case 1:
						{
						_localctx = new AndExprContext(new LogicExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_logicExpr);
						setState(133);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(134);
						((AndExprContext)_localctx).op = match(AND);
						setState(135);
						logicExpr(6);
						}
						break;
					case 2:
						{
						_localctx = new OrExprContext(new LogicExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_logicExpr);
						setState(136);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(137);
						((OrExprContext)_localctx).op = match(OR);
						setState(138);
						logicExpr(5);
						}
						break;
					case 3:
						{
						_localctx = new ImpExprContext(new LogicExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_logicExpr);
						setState(139);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(140);
						((ImpExprContext)_localctx).op = match(IMP);
						setState(141);
						logicExpr(4);
						}
						break;
					case 4:
						{
						_localctx = new IffExprContext(new LogicExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_logicExpr);
						setState(142);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(143);
						((IffExprContext)_localctx).op = match(IFF);
						setState(144);
						logicExpr(3);
						}
						break;
					}
					} 
				}
				setState(149);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class CompExprContext extends ParserRuleContext {
		public CompExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compExpr; }
	 
		public CompExprContext() { }
		public void copyFrom(CompExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NormalCompExprContext extends CompExprContext {
		public Token op;
		public List<ArithExprContext> arithExpr() {
			return getRuleContexts(ArithExprContext.class);
		}
		public ArithExprContext arithExpr(int i) {
			return getRuleContext(ArithExprContext.class,i);
		}
		public TerminalNode GT() { return getToken(STLParser.GT, 0); }
		public TerminalNode LT() { return getToken(STLParser.LT, 0); }
		public TerminalNode GE() { return getToken(STLParser.GE, 0); }
		public TerminalNode LE() { return getToken(STLParser.LE, 0); }
		public NormalCompExprContext(CompExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterNormalCompExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitNormalCompExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitNormalCompExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WeakCompExprContext extends CompExprContext {
		public Token op;
		public List<ArithExprContext> arithExpr() {
			return getRuleContexts(ArithExprContext.class);
		}
		public ArithExprContext arithExpr(int i) {
			return getRuleContext(ArithExprContext.class,i);
		}
		public TerminalNode ID() { return getToken(STLParser.ID, 0); }
		public TerminalNode FLOAT() { return getToken(STLParser.FLOAT, 0); }
		public TerminalNode GT() { return getToken(STLParser.GT, 0); }
		public TerminalNode LT() { return getToken(STLParser.LT, 0); }
		public TerminalNode GE() { return getToken(STLParser.GE, 0); }
		public TerminalNode LE() { return getToken(STLParser.LE, 0); }
		public WeakCompExprContext(CompExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterWeakCompExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitWeakCompExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitWeakCompExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompExprContext compExpr() throws RecognitionException {
		CompExprContext _localctx = new CompExprContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_compExpr);
		int _la;
		try {
			setState(165);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
			case ID:
			case INT:
			case FLOAT:
				_localctx = new NormalCompExprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(150);
				arithExpr(0);
				setState(151);
				((NormalCompExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << GT) | (1L << GE) | (1L << LT) | (1L << LE))) != 0)) ) {
					((NormalCompExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(152);
				arithExpr(0);
				}
				break;
			case T__5:
				_localctx = new WeakCompExprContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(154);
				match(T__5);
				setState(155);
				arithExpr(0);
				setState(156);
				((WeakCompExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GT) | (1L << GE) | (1L << LT) | (1L << LE))) != 0)) ) {
					((WeakCompExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(157);
				arithExpr(0);
				setState(158);
				match(T__6);
				setState(159);
				match(T__0);
				setState(160);
				match(ID);
				setState(161);
				match(T__7);
				setState(162);
				match(FLOAT);
				setState(163);
				match(T__1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArithExprContext extends ParserRuleContext {
		public ArithExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithExpr; }
	 
		public ArithExprContext() { }
		public void copyFrom(ArithExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FloatContext extends ArithExprContext {
		public TerminalNode FLOAT() { return getToken(STLParser.FLOAT, 0); }
		public FloatContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterFloat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitFloat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitFloat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MulDivExprContext extends ArithExprContext {
		public Token op;
		public List<ArithExprContext> arithExpr() {
			return getRuleContexts(ArithExprContext.class);
		}
		public ArithExprContext arithExpr(int i) {
			return getRuleContext(ArithExprContext.class,i);
		}
		public TerminalNode MUL() { return getToken(STLParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(STLParser.DIV, 0); }
		public MulDivExprContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterMulDivExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitMulDivExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitMulDivExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParensContext extends ArithExprContext {
		public ArithExprContext arithExpr() {
			return getRuleContext(ArithExprContext.class,0);
		}
		public ParensContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterParens(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitParens(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitParens(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdContext extends ArithExprContext {
		public TerminalNode ID() { return getToken(STLParser.ID, 0); }
		public IdContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitId(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AddSubExprContext extends ArithExprContext {
		public Token op;
		public List<ArithExprContext> arithExpr() {
			return getRuleContexts(ArithExprContext.class);
		}
		public ArithExprContext arithExpr(int i) {
			return getRuleContext(ArithExprContext.class,i);
		}
		public TerminalNode ADD() { return getToken(STLParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(STLParser.SUB, 0); }
		public AddSubExprContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterAddSubExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitAddSubExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitAddSubExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntContext extends ArithExprContext {
		public TerminalNode INT() { return getToken(STLParser.INT, 0); }
		public IntContext(ArithExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterInt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitInt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitInt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArithExprContext arithExpr() throws RecognitionException {
		return arithExpr(0);
	}

	private ArithExprContext arithExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ArithExprContext _localctx = new ArithExprContext(_ctx, _parentState);
		ArithExprContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_arithExpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				{
				_localctx = new IntContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(168);
				match(INT);
				}
				break;
			case FLOAT:
				{
				_localctx = new FloatContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(169);
				match(FLOAT);
				}
				break;
			case ID:
				{
				_localctx = new IdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(170);
				match(ID);
				}
				break;
			case T__0:
				{
				_localctx = new ParensContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(171);
				match(T__0);
				setState(172);
				arithExpr(0);
				setState(173);
				match(T__1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(185);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(183);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
					case 1:
						{
						_localctx = new MulDivExprContext(new ArithExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_arithExpr);
						setState(177);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(178);
						((MulDivExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==MUL || _la==DIV) ) {
							((MulDivExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(179);
						arithExpr(7);
						}
						break;
					case 2:
						{
						_localctx = new AddSubExprContext(new ArithExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_arithExpr);
						setState(180);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(181);
						((AddSubExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
							((AddSubExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(182);
						arithExpr(6);
						}
						break;
					}
					} 
				}
				setState(187);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class TimeBoundContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(STLParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(STLParser.INT, i);
		}
		public TimeBoundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_timeBound; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterTimeBound(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitTimeBound(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitTimeBound(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TimeBoundContext timeBound() throws RecognitionException {
		TimeBoundContext _localctx = new TimeBoundContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_timeBound);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			match(T__8);
			setState(189);
			match(INT);
			setState(190);
			match(T__7);
			setState(191);
			match(INT);
			setState(192);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WeakLeftTimeBoundContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(STLParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(STLParser.INT, i);
		}
		public TerminalNode ID() { return getToken(STLParser.ID, 0); }
		public WeakLeftTimeBoundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_weakLeftTimeBound; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterWeakLeftTimeBound(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitWeakLeftTimeBound(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitWeakLeftTimeBound(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WeakLeftTimeBoundContext weakLeftTimeBound() throws RecognitionException {
		WeakLeftTimeBoundContext _localctx = new WeakLeftTimeBoundContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_weakLeftTimeBound);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			match(T__8);
			setState(195);
			match(T__5);
			setState(196);
			match(INT);
			setState(197);
			match(T__6);
			setState(198);
			match(T__0);
			setState(199);
			match(ID);
			setState(200);
			match(T__7);
			setState(201);
			match(INT);
			setState(202);
			match(T__1);
			setState(203);
			match(T__7);
			setState(204);
			match(INT);
			setState(205);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WeakRightTimeBoundContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(STLParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(STLParser.INT, i);
		}
		public TerminalNode ID() { return getToken(STLParser.ID, 0); }
		public WeakRightTimeBoundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_weakRightTimeBound; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterWeakRightTimeBound(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitWeakRightTimeBound(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitWeakRightTimeBound(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WeakRightTimeBoundContext weakRightTimeBound() throws RecognitionException {
		WeakRightTimeBoundContext _localctx = new WeakRightTimeBoundContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_weakRightTimeBound);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			match(T__8);
			setState(208);
			match(INT);
			setState(209);
			match(T__7);
			setState(210);
			match(T__5);
			setState(211);
			match(INT);
			setState(212);
			match(T__6);
			setState(213);
			match(T__0);
			setState(214);
			match(ID);
			setState(215);
			match(T__7);
			setState(216);
			match(INT);
			setState(217);
			match(T__1);
			setState(218);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WeakBothTimeBoundContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(STLParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(STLParser.INT, i);
		}
		public List<TerminalNode> ID() { return getTokens(STLParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(STLParser.ID, i);
		}
		public WeakBothTimeBoundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_weakBothTimeBound; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).enterWeakBothTimeBound(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STLListener ) ((STLListener)listener).exitWeakBothTimeBound(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STLVisitor ) return ((STLVisitor<? extends T>)visitor).visitWeakBothTimeBound(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WeakBothTimeBoundContext weakBothTimeBound() throws RecognitionException {
		WeakBothTimeBoundContext _localctx = new WeakBothTimeBoundContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_weakBothTimeBound);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(220);
			match(T__8);
			setState(221);
			match(T__5);
			setState(222);
			match(INT);
			setState(223);
			match(T__6);
			setState(224);
			match(T__0);
			setState(225);
			match(ID);
			setState(226);
			match(T__7);
			setState(227);
			match(INT);
			setState(228);
			match(T__1);
			setState(229);
			match(T__7);
			setState(230);
			match(T__5);
			setState(231);
			match(INT);
			setState(232);
			match(T__6);
			setState(233);
			match(T__0);
			setState(234);
			match(ID);
			setState(235);
			match(T__7);
			setState(236);
			match(INT);
			setState(237);
			match(T__1);
			setState(238);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 2:
			return stl_sempred((StlContext)_localctx, predIndex);
		case 7:
			return logicExpr_sempred((LogicExprContext)_localctx, predIndex);
		case 9:
			return arithExpr_sempred((ArithExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean stl_sempred(StlContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		case 2:
			return precpred(_ctx, 2);
		case 3:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean logicExpr_sempred(LogicExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return precpred(_ctx, 5);
		case 5:
			return precpred(_ctx, 4);
		case 6:
			return precpred(_ctx, 3);
		case 7:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean arithExpr_sempred(ArithExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 8:
			return precpred(_ctx, 6);
		case 9:
			return precpred(_ctx, 5);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u001e\u00f1\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0001\u0000\u0004\u0000\u001e\b\u0000"+
		"\u000b\u0000\f\u0000\u001f\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0003\u0001\'\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u00025\b\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002"+
		"C\b\u0002\n\u0002\f\u0002F\t\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004`\b\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006"+
		"z\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u0084\b\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007"+
		"\u0092\b\u0007\n\u0007\f\u0007\u0095\t\u0007\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0003\b\u00a6\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0003\t\u00b0\b\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0005\t\u00b8\b\t\n\t\f\t\u00bb\t\t\u0001\n\u0001\n"+
		"\u0001\n\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0000\u0003\u0004\u000e\u0012\u000e\u0000\u0002"+
		"\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u0000\u0004"+
		"\u0002\u0000\u0005\u0005\u0010\u0013\u0001\u0000\u0010\u0013\u0001\u0000"+
		"\u0014\u0015\u0001\u0000\u0016\u0017\u00ff\u0000\u001d\u0001\u0000\u0000"+
		"\u0000\u0002&\u0001\u0000\u0000\u0000\u00044\u0001\u0000\u0000\u0000\u0006"+
		"G\u0001\u0000\u0000\u0000\b_\u0001\u0000\u0000\u0000\na\u0001\u0000\u0000"+
		"\u0000\fy\u0001\u0000\u0000\u0000\u000e\u0083\u0001\u0000\u0000\u0000"+
		"\u0010\u00a5\u0001\u0000\u0000\u0000\u0012\u00af\u0001\u0000\u0000\u0000"+
		"\u0014\u00bc\u0001\u0000\u0000\u0000\u0016\u00c2\u0001\u0000\u0000\u0000"+
		"\u0018\u00cf\u0001\u0000\u0000\u0000\u001a\u00dc\u0001\u0000\u0000\u0000"+
		"\u001c\u001e\u0003\u0002\u0001\u0000\u001d\u001c\u0001\u0000\u0000\u0000"+
		"\u001e\u001f\u0001\u0000\u0000\u0000\u001f\u001d\u0001\u0000\u0000\u0000"+
		"\u001f \u0001\u0000\u0000\u0000 \u0001\u0001\u0000\u0000\u0000!\'\u0003"+
		"\u0004\u0002\u0000\"#\u0003\u0004\u0002\u0000#$\u0005\u001b\u0000\u0000"+
		"$\'\u0001\u0000\u0000\u0000%\'\u0005\u001b\u0000\u0000&!\u0001\u0000\u0000"+
		"\u0000&\"\u0001\u0000\u0000\u0000&%\u0001\u0000\u0000\u0000\'\u0003\u0001"+
		"\u0000\u0000\u0000()\u0006\u0002\uffff\uffff\u0000)*\u0005\u0001\u0000"+
		"\u0000*+\u0003\u0004\u0002\u0000+,\u0005\u0002\u0000\u0000,5\u0001\u0000"+
		"\u0000\u0000-5\u0003\u000e\u0007\u0000.5\u0003\u0006\u0003\u0000/5\u0003"+
		"\b\u0004\u000005\u0003\n\u0005\u000015\u0003\f\u0006\u000023\u0005\r\u0000"+
		"\u000035\u0003\u0004\u0002\u00054(\u0001\u0000\u0000\u00004-\u0001\u0000"+
		"\u0000\u00004.\u0001\u0000\u0000\u00004/\u0001\u0000\u0000\u000040\u0001"+
		"\u0000\u0000\u000041\u0001\u0000\u0000\u000042\u0001\u0000\u0000\u0000"+
		"5D\u0001\u0000\u0000\u000067\n\u0004\u0000\u000078\u0005\u000b\u0000\u0000"+
		"8C\u0003\u0004\u0002\u00059:\n\u0003\u0000\u0000:;\u0005\f\u0000\u0000"+
		";C\u0003\u0004\u0002\u0004<=\n\u0002\u0000\u0000=>\u0005\u000e\u0000\u0000"+
		">C\u0003\u0004\u0002\u0003?@\n\u0001\u0000\u0000@A\u0005\u000f\u0000\u0000"+
		"AC\u0003\u0004\u0002\u0002B6\u0001\u0000\u0000\u0000B9\u0001\u0000\u0000"+
		"\u0000B<\u0001\u0000\u0000\u0000B?\u0001\u0000\u0000\u0000CF\u0001\u0000"+
		"\u0000\u0000DB\u0001\u0000\u0000\u0000DE\u0001\u0000\u0000\u0000E\u0005"+
		"\u0001\u0000\u0000\u0000FD\u0001\u0000\u0000\u0000GH\u0005\u0003\u0000"+
		"\u0000HI\u0003\u0014\n\u0000IJ\u0005\u0001\u0000\u0000JK\u0003\u0004\u0002"+
		"\u0000KL\u0005\u0002\u0000\u0000L\u0007\u0001\u0000\u0000\u0000MN\u0005"+
		"\u0003\u0000\u0000NO\u0003\u0016\u000b\u0000OP\u0005\u0001\u0000\u0000"+
		"PQ\u0003\u0004\u0002\u0000QR\u0005\u0002\u0000\u0000R`\u0001\u0000\u0000"+
		"\u0000ST\u0005\u0003\u0000\u0000TU\u0003\u0018\f\u0000UV\u0005\u0001\u0000"+
		"\u0000VW\u0003\u0004\u0002\u0000WX\u0005\u0002\u0000\u0000X`\u0001\u0000"+
		"\u0000\u0000YZ\u0005\u0003\u0000\u0000Z[\u0003\u001a\r\u0000[\\\u0005"+
		"\u0001\u0000\u0000\\]\u0003\u0004\u0002\u0000]^\u0005\u0002\u0000\u0000"+
		"^`\u0001\u0000\u0000\u0000_M\u0001\u0000\u0000\u0000_S\u0001\u0000\u0000"+
		"\u0000_Y\u0001\u0000\u0000\u0000`\t\u0001\u0000\u0000\u0000ab\u0005\u0004"+
		"\u0000\u0000bc\u0003\u0014\n\u0000cd\u0005\u0001\u0000\u0000de\u0003\u0004"+
		"\u0002\u0000ef\u0005\u0002\u0000\u0000f\u000b\u0001\u0000\u0000\u0000"+
		"gh\u0005\u0004\u0000\u0000hi\u0003\u0016\u000b\u0000ij\u0005\u0001\u0000"+
		"\u0000jk\u0003\u0004\u0002\u0000kl\u0005\u0002\u0000\u0000lz\u0001\u0000"+
		"\u0000\u0000mn\u0005\u0004\u0000\u0000no\u0003\u0018\f\u0000op\u0005\u0001"+
		"\u0000\u0000pq\u0003\u0004\u0002\u0000qr\u0005\u0002\u0000\u0000rz\u0001"+
		"\u0000\u0000\u0000st\u0005\u0004\u0000\u0000tu\u0003\u001a\r\u0000uv\u0005"+
		"\u0001\u0000\u0000vw\u0003\u0004\u0002\u0000wx\u0005\u0002\u0000\u0000"+
		"xz\u0001\u0000\u0000\u0000yg\u0001\u0000\u0000\u0000ym\u0001\u0000\u0000"+
		"\u0000ys\u0001\u0000\u0000\u0000z\r\u0001\u0000\u0000\u0000{|\u0006\u0007"+
		"\uffff\uffff\u0000|}\u0005\u0001\u0000\u0000}~\u0003\u000e\u0007\u0000"+
		"~\u007f\u0005\u0002\u0000\u0000\u007f\u0084\u0001\u0000\u0000\u0000\u0080"+
		"\u0081\u0005\r\u0000\u0000\u0081\u0084\u0003\u000e\u0007\u0006\u0082\u0084"+
		"\u0003\u0010\b\u0000\u0083{\u0001\u0000\u0000\u0000\u0083\u0080\u0001"+
		"\u0000\u0000\u0000\u0083\u0082\u0001\u0000\u0000\u0000\u0084\u0093\u0001"+
		"\u0000\u0000\u0000\u0085\u0086\n\u0005\u0000\u0000\u0086\u0087\u0005\u000b"+
		"\u0000\u0000\u0087\u0092\u0003\u000e\u0007\u0006\u0088\u0089\n\u0004\u0000"+
		"\u0000\u0089\u008a\u0005\f\u0000\u0000\u008a\u0092\u0003\u000e\u0007\u0005"+
		"\u008b\u008c\n\u0003\u0000\u0000\u008c\u008d\u0005\u000e\u0000\u0000\u008d"+
		"\u0092\u0003\u000e\u0007\u0004\u008e\u008f\n\u0002\u0000\u0000\u008f\u0090"+
		"\u0005\u000f\u0000\u0000\u0090\u0092\u0003\u000e\u0007\u0003\u0091\u0085"+
		"\u0001\u0000\u0000\u0000\u0091\u0088\u0001\u0000\u0000\u0000\u0091\u008b"+
		"\u0001\u0000\u0000\u0000\u0091\u008e\u0001\u0000\u0000\u0000\u0092\u0095"+
		"\u0001\u0000\u0000\u0000\u0093\u0091\u0001\u0000\u0000\u0000\u0093\u0094"+
		"\u0001\u0000\u0000\u0000\u0094\u000f\u0001\u0000\u0000\u0000\u0095\u0093"+
		"\u0001\u0000\u0000\u0000\u0096\u0097\u0003\u0012\t\u0000\u0097\u0098\u0007"+
		"\u0000\u0000\u0000\u0098\u0099\u0003\u0012\t\u0000\u0099\u00a6\u0001\u0000"+
		"\u0000\u0000\u009a\u009b\u0005\u0006\u0000\u0000\u009b\u009c\u0003\u0012"+
		"\t\u0000\u009c\u009d\u0007\u0001\u0000\u0000\u009d\u009e\u0003\u0012\t"+
		"\u0000\u009e\u009f\u0005\u0007\u0000\u0000\u009f\u00a0\u0005\u0001\u0000"+
		"\u0000\u00a0\u00a1\u0005\u0018\u0000\u0000\u00a1\u00a2\u0005\b\u0000\u0000"+
		"\u00a2\u00a3\u0005\u001a\u0000\u0000\u00a3\u00a4\u0005\u0002\u0000\u0000"+
		"\u00a4\u00a6\u0001\u0000\u0000\u0000\u00a5\u0096\u0001\u0000\u0000\u0000"+
		"\u00a5\u009a\u0001\u0000\u0000\u0000\u00a6\u0011\u0001\u0000\u0000\u0000"+
		"\u00a7\u00a8\u0006\t\uffff\uffff\u0000\u00a8\u00b0\u0005\u0019\u0000\u0000"+
		"\u00a9\u00b0\u0005\u001a\u0000\u0000\u00aa\u00b0\u0005\u0018\u0000\u0000"+
		"\u00ab\u00ac\u0005\u0001\u0000\u0000\u00ac\u00ad\u0003\u0012\t\u0000\u00ad"+
		"\u00ae\u0005\u0002\u0000\u0000\u00ae\u00b0\u0001\u0000\u0000\u0000\u00af"+
		"\u00a7\u0001\u0000\u0000\u0000\u00af\u00a9\u0001\u0000\u0000\u0000\u00af"+
		"\u00aa\u0001\u0000\u0000\u0000\u00af\u00ab\u0001\u0000\u0000\u0000\u00b0"+
		"\u00b9\u0001\u0000\u0000\u0000\u00b1\u00b2\n\u0006\u0000\u0000\u00b2\u00b3"+
		"\u0007\u0002\u0000\u0000\u00b3\u00b8\u0003\u0012\t\u0007\u00b4\u00b5\n"+
		"\u0005\u0000\u0000\u00b5\u00b6\u0007\u0003\u0000\u0000\u00b6\u00b8\u0003"+
		"\u0012\t\u0006\u00b7\u00b1\u0001\u0000\u0000\u0000\u00b7\u00b4\u0001\u0000"+
		"\u0000\u0000\u00b8\u00bb\u0001\u0000\u0000\u0000\u00b9\u00b7\u0001\u0000"+
		"\u0000\u0000\u00b9\u00ba\u0001\u0000\u0000\u0000\u00ba\u0013\u0001\u0000"+
		"\u0000\u0000\u00bb\u00b9\u0001\u0000\u0000\u0000\u00bc\u00bd\u0005\t\u0000"+
		"\u0000\u00bd\u00be\u0005\u0019\u0000\u0000\u00be\u00bf\u0005\b\u0000\u0000"+
		"\u00bf\u00c0\u0005\u0019\u0000\u0000\u00c0\u00c1\u0005\n\u0000\u0000\u00c1"+
		"\u0015\u0001\u0000\u0000\u0000\u00c2\u00c3\u0005\t\u0000\u0000\u00c3\u00c4"+
		"\u0005\u0006\u0000\u0000\u00c4\u00c5\u0005\u0019\u0000\u0000\u00c5\u00c6"+
		"\u0005\u0007\u0000\u0000\u00c6\u00c7\u0005\u0001\u0000\u0000\u00c7\u00c8"+
		"\u0005\u0018\u0000\u0000\u00c8\u00c9\u0005\b\u0000\u0000\u00c9\u00ca\u0005"+
		"\u0019\u0000\u0000\u00ca\u00cb\u0005\u0002\u0000\u0000\u00cb\u00cc\u0005"+
		"\b\u0000\u0000\u00cc\u00cd\u0005\u0019\u0000\u0000\u00cd\u00ce\u0005\n"+
		"\u0000\u0000\u00ce\u0017\u0001\u0000\u0000\u0000\u00cf\u00d0\u0005\t\u0000"+
		"\u0000\u00d0\u00d1\u0005\u0019\u0000\u0000\u00d1\u00d2\u0005\b\u0000\u0000"+
		"\u00d2\u00d3\u0005\u0006\u0000\u0000\u00d3\u00d4\u0005\u0019\u0000\u0000"+
		"\u00d4\u00d5\u0005\u0007\u0000\u0000\u00d5\u00d6\u0005\u0001\u0000\u0000"+
		"\u00d6\u00d7\u0005\u0018\u0000\u0000\u00d7\u00d8\u0005\b\u0000\u0000\u00d8"+
		"\u00d9\u0005\u0019\u0000\u0000\u00d9\u00da\u0005\u0002\u0000\u0000\u00da"+
		"\u00db\u0005\n\u0000\u0000\u00db\u0019\u0001\u0000\u0000\u0000\u00dc\u00dd"+
		"\u0005\t\u0000\u0000\u00dd\u00de\u0005\u0006\u0000\u0000\u00de\u00df\u0005"+
		"\u0019\u0000\u0000\u00df\u00e0\u0005\u0007\u0000\u0000\u00e0\u00e1\u0005"+
		"\u0001\u0000\u0000\u00e1\u00e2\u0005\u0018\u0000\u0000\u00e2\u00e3\u0005"+
		"\b\u0000\u0000\u00e3\u00e4\u0005\u0019\u0000\u0000\u00e4\u00e5\u0005\u0002"+
		"\u0000\u0000\u00e5\u00e6\u0005\b\u0000\u0000\u00e6\u00e7\u0005\u0006\u0000"+
		"\u0000\u00e7\u00e8\u0005\u0019\u0000\u0000\u00e8\u00e9\u0005\u0007\u0000"+
		"\u0000\u00e9\u00ea\u0005\u0001\u0000\u0000\u00ea\u00eb\u0005\u0018\u0000"+
		"\u0000\u00eb\u00ec\u0005\b\u0000\u0000\u00ec\u00ed\u0005\u0019\u0000\u0000"+
		"\u00ed\u00ee\u0005\u0002\u0000\u0000\u00ee\u00ef\u0005\n\u0000\u0000\u00ef"+
		"\u001b\u0001\u0000\u0000\u0000\u000e\u001f&4BD_y\u0083\u0091\u0093\u00a5"+
		"\u00af\u00b7\u00b9";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
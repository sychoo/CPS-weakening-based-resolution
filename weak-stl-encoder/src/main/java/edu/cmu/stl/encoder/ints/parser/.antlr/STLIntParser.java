// Generated from /Users/rockita/Code/req-weak/stl/src/main/java/edu/cmu/stl/encoder/int/parser/STLInt.g4 by ANTLR 4.9.2

	package antlr.parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class STLIntParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, AND=11, OR=12, NOT=13, IMP=14, IFF=15, GT=16, GE=17, LT=18, LE=19, 
		MUL=20, DIV=21, ADD=22, SUB=23, ID=24, INT=25, NEWLINE=26, WS=27, Comment=28, 
		Comment2=29;
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
			"SUB", "ID", "INT", "NEWLINE", "WS", "Comment", "Comment2"
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
	public String getGrammarFileName() { return "STLInt.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public STLIntParser(TokenStream input) {
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
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__3) | (1L << T__5) | (1L << NOT) | (1L << ID) | (1L << INT) | (1L << NEWLINE))) != 0) );
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
		public TerminalNode NEWLINE() { return getToken(STLIntParser.NEWLINE, 0); }
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
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
	}
	public static class StlLogicExprContext extends StlContext {
		public LogicExprContext logicExpr() {
			return getRuleContext(LogicExprContext.class,0);
		}
		public StlLogicExprContext(StlContext ctx) { copyFrom(ctx); }
	}
	public static class StlImpExprContext extends StlContext {
		public Token op;
		public List<StlContext> stl() {
			return getRuleContexts(StlContext.class);
		}
		public StlContext stl(int i) {
			return getRuleContext(StlContext.class,i);
		}
		public TerminalNode IMP() { return getToken(STLIntParser.IMP, 0); }
		public StlImpExprContext(StlContext ctx) { copyFrom(ctx); }
	}
	public static class StlGloballyContext extends StlContext {
		public GloballyContext globally() {
			return getRuleContext(GloballyContext.class,0);
		}
		public StlGloballyContext(StlContext ctx) { copyFrom(ctx); }
	}
	public static class StlIffExprContext extends StlContext {
		public Token op;
		public List<StlContext> stl() {
			return getRuleContexts(StlContext.class);
		}
		public StlContext stl(int i) {
			return getRuleContext(StlContext.class,i);
		}
		public TerminalNode IFF() { return getToken(STLIntParser.IFF, 0); }
		public StlIffExprContext(StlContext ctx) { copyFrom(ctx); }
	}
	public static class StlWeakEventuallyContext extends StlContext {
		public WeakEventuallyContext weakEventually() {
			return getRuleContext(WeakEventuallyContext.class,0);
		}
		public StlWeakEventuallyContext(StlContext ctx) { copyFrom(ctx); }
	}
	public static class StlNotExprContext extends StlContext {
		public Token op;
		public StlContext stl() {
			return getRuleContext(StlContext.class,0);
		}
		public TerminalNode NOT() { return getToken(STLIntParser.NOT, 0); }
		public StlNotExprContext(StlContext ctx) { copyFrom(ctx); }
	}
	public static class StlAndExprContext extends StlContext {
		public Token op;
		public List<StlContext> stl() {
			return getRuleContexts(StlContext.class);
		}
		public StlContext stl(int i) {
			return getRuleContext(StlContext.class,i);
		}
		public TerminalNode AND() { return getToken(STLIntParser.AND, 0); }
		public StlAndExprContext(StlContext ctx) { copyFrom(ctx); }
	}
	public static class StlOrExprContext extends StlContext {
		public Token op;
		public List<StlContext> stl() {
			return getRuleContexts(StlContext.class);
		}
		public StlContext stl(int i) {
			return getRuleContext(StlContext.class,i);
		}
		public TerminalNode OR() { return getToken(STLIntParser.OR, 0); }
		public StlOrExprContext(StlContext ctx) { copyFrom(ctx); }
	}
	public static class StlParenExprContext extends StlContext {
		public StlContext stl() {
			return getRuleContext(StlContext.class,0);
		}
		public StlParenExprContext(StlContext ctx) { copyFrom(ctx); }
	}
	public static class StlEventuallyContext extends StlContext {
		public EventuallyContext eventually() {
			return getRuleContext(EventuallyContext.class,0);
		}
		public StlEventuallyContext(StlContext ctx) { copyFrom(ctx); }
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
		public TerminalNode AND() { return getToken(STLIntParser.AND, 0); }
		public AndExprContext(LogicExprContext ctx) { copyFrom(ctx); }
	}
	public static class ImpExprContext extends LogicExprContext {
		public Token op;
		public List<LogicExprContext> logicExpr() {
			return getRuleContexts(LogicExprContext.class);
		}
		public LogicExprContext logicExpr(int i) {
			return getRuleContext(LogicExprContext.class,i);
		}
		public TerminalNode IMP() { return getToken(STLIntParser.IMP, 0); }
		public ImpExprContext(LogicExprContext ctx) { copyFrom(ctx); }
	}
	public static class LogicParensContext extends LogicExprContext {
		public LogicExprContext logicExpr() {
			return getRuleContext(LogicExprContext.class,0);
		}
		public LogicParensContext(LogicExprContext ctx) { copyFrom(ctx); }
	}
	public static class LogicCompExprContext extends LogicExprContext {
		public CompExprContext compExpr() {
			return getRuleContext(CompExprContext.class,0);
		}
		public LogicCompExprContext(LogicExprContext ctx) { copyFrom(ctx); }
	}
	public static class NotExprContext extends LogicExprContext {
		public Token op;
		public LogicExprContext logicExpr() {
			return getRuleContext(LogicExprContext.class,0);
		}
		public TerminalNode NOT() { return getToken(STLIntParser.NOT, 0); }
		public NotExprContext(LogicExprContext ctx) { copyFrom(ctx); }
	}
	public static class IffExprContext extends LogicExprContext {
		public Token op;
		public List<LogicExprContext> logicExpr() {
			return getRuleContexts(LogicExprContext.class);
		}
		public LogicExprContext logicExpr(int i) {
			return getRuleContext(LogicExprContext.class,i);
		}
		public TerminalNode IFF() { return getToken(STLIntParser.IFF, 0); }
		public IffExprContext(LogicExprContext ctx) { copyFrom(ctx); }
	}
	public static class OrExprContext extends LogicExprContext {
		public Token op;
		public List<LogicExprContext> logicExpr() {
			return getRuleContexts(LogicExprContext.class);
		}
		public LogicExprContext logicExpr(int i) {
			return getRuleContext(LogicExprContext.class,i);
		}
		public TerminalNode OR() { return getToken(STLIntParser.OR, 0); }
		public OrExprContext(LogicExprContext ctx) { copyFrom(ctx); }
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
		public TerminalNode GT() { return getToken(STLIntParser.GT, 0); }
		public TerminalNode LT() { return getToken(STLIntParser.LT, 0); }
		public TerminalNode GE() { return getToken(STLIntParser.GE, 0); }
		public TerminalNode LE() { return getToken(STLIntParser.LE, 0); }
		public NormalCompExprContext(CompExprContext ctx) { copyFrom(ctx); }
	}
	public static class WeakCompExprContext extends CompExprContext {
		public Token op;
		public List<ArithExprContext> arithExpr() {
			return getRuleContexts(ArithExprContext.class);
		}
		public ArithExprContext arithExpr(int i) {
			return getRuleContext(ArithExprContext.class,i);
		}
		public TerminalNode ID() { return getToken(STLIntParser.ID, 0); }
		public TerminalNode INT() { return getToken(STLIntParser.INT, 0); }
		public TerminalNode GT() { return getToken(STLIntParser.GT, 0); }
		public TerminalNode LT() { return getToken(STLIntParser.LT, 0); }
		public TerminalNode GE() { return getToken(STLIntParser.GE, 0); }
		public TerminalNode LE() { return getToken(STLIntParser.LE, 0); }
		public WeakCompExprContext(CompExprContext ctx) { copyFrom(ctx); }
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
				match(INT);
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
	public static class MulDivExprContext extends ArithExprContext {
		public Token op;
		public List<ArithExprContext> arithExpr() {
			return getRuleContexts(ArithExprContext.class);
		}
		public ArithExprContext arithExpr(int i) {
			return getRuleContext(ArithExprContext.class,i);
		}
		public TerminalNode MUL() { return getToken(STLIntParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(STLIntParser.DIV, 0); }
		public MulDivExprContext(ArithExprContext ctx) { copyFrom(ctx); }
	}
	public static class ParensContext extends ArithExprContext {
		public ArithExprContext arithExpr() {
			return getRuleContext(ArithExprContext.class,0);
		}
		public ParensContext(ArithExprContext ctx) { copyFrom(ctx); }
	}
	public static class IdContext extends ArithExprContext {
		public TerminalNode ID() { return getToken(STLIntParser.ID, 0); }
		public IdContext(ArithExprContext ctx) { copyFrom(ctx); }
	}
	public static class AddSubExprContext extends ArithExprContext {
		public Token op;
		public List<ArithExprContext> arithExpr() {
			return getRuleContexts(ArithExprContext.class);
		}
		public ArithExprContext arithExpr(int i) {
			return getRuleContext(ArithExprContext.class,i);
		}
		public TerminalNode ADD() { return getToken(STLIntParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(STLIntParser.SUB, 0); }
		public AddSubExprContext(ArithExprContext ctx) { copyFrom(ctx); }
	}
	public static class IntContext extends ArithExprContext {
		public TerminalNode INT() { return getToken(STLIntParser.INT, 0); }
		public IntContext(ArithExprContext ctx) { copyFrom(ctx); }
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
			setState(174);
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
			case ID:
				{
				_localctx = new IdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(169);
				match(ID);
				}
				break;
			case T__0:
				{
				_localctx = new ParensContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(170);
				match(T__0);
				setState(171);
				arithExpr(0);
				setState(172);
				match(T__1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(184);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(182);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
					case 1:
						{
						_localctx = new MulDivExprContext(new ArithExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_arithExpr);
						setState(176);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(177);
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
						setState(178);
						arithExpr(6);
						}
						break;
					case 2:
						{
						_localctx = new AddSubExprContext(new ArithExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_arithExpr);
						setState(179);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(180);
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
						setState(181);
						arithExpr(5);
						}
						break;
					}
					} 
				}
				setState(186);
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
		public List<TerminalNode> INT() { return getTokens(STLIntParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(STLIntParser.INT, i);
		}
		public TimeBoundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_timeBound; }
	}

	public final TimeBoundContext timeBound() throws RecognitionException {
		TimeBoundContext _localctx = new TimeBoundContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_timeBound);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			match(T__8);
			setState(188);
			match(INT);
			setState(189);
			match(T__7);
			setState(190);
			match(INT);
			setState(191);
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
		public List<TerminalNode> INT() { return getTokens(STLIntParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(STLIntParser.INT, i);
		}
		public TerminalNode ID() { return getToken(STLIntParser.ID, 0); }
		public WeakLeftTimeBoundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_weakLeftTimeBound; }
	}

	public final WeakLeftTimeBoundContext weakLeftTimeBound() throws RecognitionException {
		WeakLeftTimeBoundContext _localctx = new WeakLeftTimeBoundContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_weakLeftTimeBound);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			match(T__8);
			setState(194);
			match(T__5);
			setState(195);
			match(INT);
			setState(196);
			match(T__6);
			setState(197);
			match(T__0);
			setState(198);
			match(ID);
			setState(199);
			match(T__7);
			setState(200);
			match(INT);
			setState(201);
			match(T__1);
			setState(202);
			match(T__7);
			setState(203);
			match(INT);
			setState(204);
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
		public List<TerminalNode> INT() { return getTokens(STLIntParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(STLIntParser.INT, i);
		}
		public TerminalNode ID() { return getToken(STLIntParser.ID, 0); }
		public WeakRightTimeBoundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_weakRightTimeBound; }
	}

	public final WeakRightTimeBoundContext weakRightTimeBound() throws RecognitionException {
		WeakRightTimeBoundContext _localctx = new WeakRightTimeBoundContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_weakRightTimeBound);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			match(T__8);
			setState(207);
			match(INT);
			setState(208);
			match(T__7);
			setState(209);
			match(T__5);
			setState(210);
			match(INT);
			setState(211);
			match(T__6);
			setState(212);
			match(T__0);
			setState(213);
			match(ID);
			setState(214);
			match(T__7);
			setState(215);
			match(INT);
			setState(216);
			match(T__1);
			setState(217);
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
		public List<TerminalNode> INT() { return getTokens(STLIntParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(STLIntParser.INT, i);
		}
		public List<TerminalNode> ID() { return getTokens(STLIntParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(STLIntParser.ID, i);
		}
		public WeakBothTimeBoundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_weakBothTimeBound; }
	}

	public final WeakBothTimeBoundContext weakBothTimeBound() throws RecognitionException {
		WeakBothTimeBoundContext _localctx = new WeakBothTimeBoundContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_weakBothTimeBound);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(219);
			match(T__8);
			setState(220);
			match(T__5);
			setState(221);
			match(INT);
			setState(222);
			match(T__6);
			setState(223);
			match(T__0);
			setState(224);
			match(ID);
			setState(225);
			match(T__7);
			setState(226);
			match(INT);
			setState(227);
			match(T__1);
			setState(228);
			match(T__7);
			setState(229);
			match(T__5);
			setState(230);
			match(INT);
			setState(231);
			match(T__6);
			setState(232);
			match(T__0);
			setState(233);
			match(ID);
			setState(234);
			match(T__7);
			setState(235);
			match(INT);
			setState(236);
			match(T__1);
			setState(237);
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
			return precpred(_ctx, 5);
		case 9:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\37\u00f2\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\6\2 \n\2\r\2\16\2!\3\3\3"+
		"\3\3\3\3\3\3\3\5\3)\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\5\4\67\n\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4E\n\4"+
		"\f\4\16\4H\13\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6b\n\6\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\5\b|\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u0086\n\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u0094\n\t\f\t\16\t\u0097"+
		"\13\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n"+
		"\u00a8\n\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u00b1\n\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\7\13\u00b9\n\13\f\13\16\13\u00bc\13\13\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\2\5\6\20\24\20\2\4\6\b\n\f\16\20\22\24\26\30"+
		"\32\34\2\6\4\2\7\7\22\25\3\2\22\25\3\2\26\27\3\2\30\31\2\u00ff\2\37\3"+
		"\2\2\2\4(\3\2\2\2\6\66\3\2\2\2\bI\3\2\2\2\na\3\2\2\2\fc\3\2\2\2\16{\3"+
		"\2\2\2\20\u0085\3\2\2\2\22\u00a7\3\2\2\2\24\u00b0\3\2\2\2\26\u00bd\3\2"+
		"\2\2\30\u00c3\3\2\2\2\32\u00d0\3\2\2\2\34\u00dd\3\2\2\2\36 \5\4\3\2\37"+
		"\36\3\2\2\2 !\3\2\2\2!\37\3\2\2\2!\"\3\2\2\2\"\3\3\2\2\2#)\5\6\4\2$%\5"+
		"\6\4\2%&\7\34\2\2&)\3\2\2\2\')\7\34\2\2(#\3\2\2\2($\3\2\2\2(\'\3\2\2\2"+
		")\5\3\2\2\2*+\b\4\1\2+,\7\3\2\2,-\5\6\4\2-.\7\4\2\2.\67\3\2\2\2/\67\5"+
		"\20\t\2\60\67\5\b\5\2\61\67\5\n\6\2\62\67\5\f\7\2\63\67\5\16\b\2\64\65"+
		"\7\17\2\2\65\67\5\6\4\7\66*\3\2\2\2\66/\3\2\2\2\66\60\3\2\2\2\66\61\3"+
		"\2\2\2\66\62\3\2\2\2\66\63\3\2\2\2\66\64\3\2\2\2\67F\3\2\2\289\f\6\2\2"+
		"9:\7\r\2\2:E\5\6\4\7;<\f\5\2\2<=\7\16\2\2=E\5\6\4\6>?\f\4\2\2?@\7\20\2"+
		"\2@E\5\6\4\5AB\f\3\2\2BC\7\21\2\2CE\5\6\4\4D8\3\2\2\2D;\3\2\2\2D>\3\2"+
		"\2\2DA\3\2\2\2EH\3\2\2\2FD\3\2\2\2FG\3\2\2\2G\7\3\2\2\2HF\3\2\2\2IJ\7"+
		"\5\2\2JK\5\26\f\2KL\7\3\2\2LM\5\6\4\2MN\7\4\2\2N\t\3\2\2\2OP\7\5\2\2P"+
		"Q\5\30\r\2QR\7\3\2\2RS\5\6\4\2ST\7\4\2\2Tb\3\2\2\2UV\7\5\2\2VW\5\32\16"+
		"\2WX\7\3\2\2XY\5\6\4\2YZ\7\4\2\2Zb\3\2\2\2[\\\7\5\2\2\\]\5\34\17\2]^\7"+
		"\3\2\2^_\5\6\4\2_`\7\4\2\2`b\3\2\2\2aO\3\2\2\2aU\3\2\2\2a[\3\2\2\2b\13"+
		"\3\2\2\2cd\7\6\2\2de\5\26\f\2ef\7\3\2\2fg\5\6\4\2gh\7\4\2\2h\r\3\2\2\2"+
		"ij\7\6\2\2jk\5\30\r\2kl\7\3\2\2lm\5\6\4\2mn\7\4\2\2n|\3\2\2\2op\7\6\2"+
		"\2pq\5\32\16\2qr\7\3\2\2rs\5\6\4\2st\7\4\2\2t|\3\2\2\2uv\7\6\2\2vw\5\34"+
		"\17\2wx\7\3\2\2xy\5\6\4\2yz\7\4\2\2z|\3\2\2\2{i\3\2\2\2{o\3\2\2\2{u\3"+
		"\2\2\2|\17\3\2\2\2}~\b\t\1\2~\177\7\3\2\2\177\u0080\5\20\t\2\u0080\u0081"+
		"\7\4\2\2\u0081\u0086\3\2\2\2\u0082\u0083\7\17\2\2\u0083\u0086\5\20\t\b"+
		"\u0084\u0086\5\22\n\2\u0085}\3\2\2\2\u0085\u0082\3\2\2\2\u0085\u0084\3"+
		"\2\2\2\u0086\u0095\3\2\2\2\u0087\u0088\f\7\2\2\u0088\u0089\7\r\2\2\u0089"+
		"\u0094\5\20\t\b\u008a\u008b\f\6\2\2\u008b\u008c\7\16\2\2\u008c\u0094\5"+
		"\20\t\7\u008d\u008e\f\5\2\2\u008e\u008f\7\20\2\2\u008f\u0094\5\20\t\6"+
		"\u0090\u0091\f\4\2\2\u0091\u0092\7\21\2\2\u0092\u0094\5\20\t\5\u0093\u0087"+
		"\3\2\2\2\u0093\u008a\3\2\2\2\u0093\u008d\3\2\2\2\u0093\u0090\3\2\2\2\u0094"+
		"\u0097\3\2\2\2\u0095\u0093\3\2\2\2\u0095\u0096\3\2\2\2\u0096\21\3\2\2"+
		"\2\u0097\u0095\3\2\2\2\u0098\u0099\5\24\13\2\u0099\u009a\t\2\2\2\u009a"+
		"\u009b\5\24\13\2\u009b\u00a8\3\2\2\2\u009c\u009d\7\b\2\2\u009d\u009e\5"+
		"\24\13\2\u009e\u009f\t\3\2\2\u009f\u00a0\5\24\13\2\u00a0\u00a1\7\t\2\2"+
		"\u00a1\u00a2\7\3\2\2\u00a2\u00a3\7\32\2\2\u00a3\u00a4\7\n\2\2\u00a4\u00a5"+
		"\7\33\2\2\u00a5\u00a6\7\4\2\2\u00a6\u00a8\3\2\2\2\u00a7\u0098\3\2\2\2"+
		"\u00a7\u009c\3\2\2\2\u00a8\23\3\2\2\2\u00a9\u00aa\b\13\1\2\u00aa\u00b1"+
		"\7\33\2\2\u00ab\u00b1\7\32\2\2\u00ac\u00ad\7\3\2\2\u00ad\u00ae\5\24\13"+
		"\2\u00ae\u00af\7\4\2\2\u00af\u00b1\3\2\2\2\u00b0\u00a9\3\2\2\2\u00b0\u00ab"+
		"\3\2\2\2\u00b0\u00ac\3\2\2\2\u00b1\u00ba\3\2\2\2\u00b2\u00b3\f\7\2\2\u00b3"+
		"\u00b4\t\4\2\2\u00b4\u00b9\5\24\13\b\u00b5\u00b6\f\6\2\2\u00b6\u00b7\t"+
		"\5\2\2\u00b7\u00b9\5\24\13\7\u00b8\u00b2\3\2\2\2\u00b8\u00b5\3\2\2\2\u00b9"+
		"\u00bc\3\2\2\2\u00ba\u00b8\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\25\3\2\2"+
		"\2\u00bc\u00ba\3\2\2\2\u00bd\u00be\7\13\2\2\u00be\u00bf\7\33\2\2\u00bf"+
		"\u00c0\7\n\2\2\u00c0\u00c1\7\33\2\2\u00c1\u00c2\7\f\2\2\u00c2\27\3\2\2"+
		"\2\u00c3\u00c4\7\13\2\2\u00c4\u00c5\7\b\2\2\u00c5\u00c6\7\33\2\2\u00c6"+
		"\u00c7\7\t\2\2\u00c7\u00c8\7\3\2\2\u00c8\u00c9\7\32\2\2\u00c9\u00ca\7"+
		"\n\2\2\u00ca\u00cb\7\33\2\2\u00cb\u00cc\7\4\2\2\u00cc\u00cd\7\n\2\2\u00cd"+
		"\u00ce\7\33\2\2\u00ce\u00cf\7\f\2\2\u00cf\31\3\2\2\2\u00d0\u00d1\7\13"+
		"\2\2\u00d1\u00d2\7\33\2\2\u00d2\u00d3\7\n\2\2\u00d3\u00d4\7\b\2\2\u00d4"+
		"\u00d5\7\33\2\2\u00d5\u00d6\7\t\2\2\u00d6\u00d7\7\3\2\2\u00d7\u00d8\7"+
		"\32\2\2\u00d8\u00d9\7\n\2\2\u00d9\u00da\7\33\2\2\u00da\u00db\7\4\2\2\u00db"+
		"\u00dc\7\f\2\2\u00dc\33\3\2\2\2\u00dd\u00de\7\13\2\2\u00de\u00df\7\b\2"+
		"\2\u00df\u00e0\7\33\2\2\u00e0\u00e1\7\t\2\2\u00e1\u00e2\7\3\2\2\u00e2"+
		"\u00e3\7\32\2\2\u00e3\u00e4\7\n\2\2\u00e4\u00e5\7\33\2\2\u00e5\u00e6\7"+
		"\4\2\2\u00e6\u00e7\7\n\2\2\u00e7\u00e8\7\b\2\2\u00e8\u00e9\7\33\2\2\u00e9"+
		"\u00ea\7\t\2\2\u00ea\u00eb\7\3\2\2\u00eb\u00ec\7\32\2\2\u00ec\u00ed\7"+
		"\n\2\2\u00ed\u00ee\7\33\2\2\u00ee\u00ef\7\4\2\2\u00ef\u00f0\7\f\2\2\u00f0"+
		"\35\3\2\2\2\20!(\66DFa{\u0085\u0093\u0095\u00a7\u00b0\u00b8\u00ba";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
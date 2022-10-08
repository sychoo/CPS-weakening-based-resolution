// Generated from /Users/rockita/Code/req-weak/stl/src/main/antlr4/STL.g4 by ANTLR 4.9.2

	package edu.cmu.stl.encoder.parser;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class STLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, AND=11, OR=12, NOT=13, IMP=14, IFF=15, GT=16, GE=17, LT=18, LE=19, 
		MUL=20, DIV=21, ADD=22, SUB=23, ID=24, INT=25, FLOAT=26, NEWLINE=27, WS=28, 
		Comment=29, Comment2=30;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "AND", "OR", "NOT", "IMP", "IFF", "GT", "GE", "LT", "LE", "MUL", 
			"DIV", "ADD", "SUB", "ID", "INT", "FLOAT", "DIGIT", "NEWLINE", "WS", 
			"Comment", "Comment2"
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


	public STLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "STL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2 \u00c4\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \3\2"+
		"\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3"+
		"\n\3\n\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\17\3\17"+
		"\3\17\3\20\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\24\3\24"+
		"\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\7\31}\n\31\f\31"+
		"\16\31\u0080\13\31\3\32\5\32\u0083\n\32\3\32\6\32\u0086\n\32\r\32\16\32"+
		"\u0087\3\33\6\33\u008b\n\33\r\33\16\33\u008c\3\33\3\33\7\33\u0091\n\33"+
		"\f\33\16\33\u0094\13\33\3\33\3\33\6\33\u0098\n\33\r\33\16\33\u0099\5\33"+
		"\u009c\n\33\3\34\3\34\3\35\5\35\u00a1\n\35\3\35\3\35\3\36\6\36\u00a6\n"+
		"\36\r\36\16\36\u00a7\3\36\3\36\3\37\3\37\3\37\3\37\7\37\u00b0\n\37\f\37"+
		"\16\37\u00b3\13\37\3\37\3\37\3 \3 \3 \3 \7 \u00bb\n \f \16 \u00be\13 "+
		"\3 \3 \3 \3 \3 \3\u00bc\2!\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32"+
		"\63\33\65\34\67\29\35;\36=\37? \3\2\7\3\2c|\6\2\62;C\\aac|\3\2\62;\4\2"+
		"\13\13\"\"\4\2\f\f\17\17\2\u00cd\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2"+
		"\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2"+
		"\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2"+
		"\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2"+
		"\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2"+
		"\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\3A\3\2\2\2\5C\3\2\2\2\7"+
		"E\3\2\2\2\tG\3\2\2\2\13I\3\2\2\2\rK\3\2\2\2\17N\3\2\2\2\21Q\3\2\2\2\23"+
		"S\3\2\2\2\25U\3\2\2\2\27W\3\2\2\2\31Z\3\2\2\2\33]\3\2\2\2\35a\3\2\2\2"+
		"\37d\3\2\2\2!h\3\2\2\2#j\3\2\2\2%m\3\2\2\2\'o\3\2\2\2)r\3\2\2\2+t\3\2"+
		"\2\2-v\3\2\2\2/x\3\2\2\2\61z\3\2\2\2\63\u0082\3\2\2\2\65\u009b\3\2\2\2"+
		"\67\u009d\3\2\2\29\u00a0\3\2\2\2;\u00a5\3\2\2\2=\u00ab\3\2\2\2?\u00b6"+
		"\3\2\2\2AB\7*\2\2B\4\3\2\2\2CD\7+\2\2D\6\3\2\2\2EF\7I\2\2F\b\3\2\2\2G"+
		"H\7H\2\2H\n\3\2\2\2IJ\7?\2\2J\f\3\2\2\2KL\7>\2\2LM\7>\2\2M\16\3\2\2\2"+
		"NO\7@\2\2OP\7@\2\2P\20\3\2\2\2QR\7.\2\2R\22\3\2\2\2ST\7]\2\2T\24\3\2\2"+
		"\2UV\7_\2\2V\26\3\2\2\2WX\7\61\2\2XY\7^\2\2Y\30\3\2\2\2Z[\7^\2\2[\\\7"+
		"\61\2\2\\\32\3\2\2\2]^\7p\2\2^_\7q\2\2_`\7v\2\2`\34\3\2\2\2ab\7/\2\2b"+
		"c\7@\2\2c\36\3\2\2\2de\7>\2\2ef\7/\2\2fg\7@\2\2g \3\2\2\2hi\7@\2\2i\""+
		"\3\2\2\2jk\7@\2\2kl\7?\2\2l$\3\2\2\2mn\7>\2\2n&\3\2\2\2op\7>\2\2pq\7?"+
		"\2\2q(\3\2\2\2rs\7,\2\2s*\3\2\2\2tu\7\61\2\2u,\3\2\2\2vw\7-\2\2w.\3\2"+
		"\2\2xy\7/\2\2y\60\3\2\2\2z~\t\2\2\2{}\t\3\2\2|{\3\2\2\2}\u0080\3\2\2\2"+
		"~|\3\2\2\2~\177\3\2\2\2\177\62\3\2\2\2\u0080~\3\2\2\2\u0081\u0083\7/\2"+
		"\2\u0082\u0081\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0085\3\2\2\2\u0084\u0086"+
		"\5\67\34\2\u0085\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0085\3\2\2\2"+
		"\u0087\u0088\3\2\2\2\u0088\64\3\2\2\2\u0089\u008b\5\67\34\2\u008a\u0089"+
		"\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d"+
		"\u008e\3\2\2\2\u008e\u0092\7\60\2\2\u008f\u0091\5\67\34\2\u0090\u008f"+
		"\3\2\2\2\u0091\u0094\3\2\2\2\u0092\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u0093"+
		"\u009c\3\2\2\2\u0094\u0092\3\2\2\2\u0095\u0097\7\60\2\2\u0096\u0098\5"+
		"\67\34\2\u0097\u0096\3\2\2\2\u0098\u0099\3\2\2\2\u0099\u0097\3\2\2\2\u0099"+
		"\u009a\3\2\2\2\u009a\u009c\3\2\2\2\u009b\u008a\3\2\2\2\u009b\u0095\3\2"+
		"\2\2\u009c\66\3\2\2\2\u009d\u009e\t\4\2\2\u009e8\3\2\2\2\u009f\u00a1\7"+
		"\17\2\2\u00a0\u009f\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2"+
		"\u00a3\7\f\2\2\u00a3:\3\2\2\2\u00a4\u00a6\t\5\2\2\u00a5\u00a4\3\2\2\2"+
		"\u00a6\u00a7\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00a9"+
		"\3\2\2\2\u00a9\u00aa\b\36\2\2\u00aa<\3\2\2\2\u00ab\u00ac\7\61\2\2\u00ac"+
		"\u00ad\7\61\2\2\u00ad\u00b1\3\2\2\2\u00ae\u00b0\n\6\2\2\u00af\u00ae\3"+
		"\2\2\2\u00b0\u00b3\3\2\2\2\u00b1\u00af\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2"+
		"\u00b4\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b4\u00b5\b\37\2\2\u00b5>\3\2\2\2"+
		"\u00b6\u00b7\7\61\2\2\u00b7\u00b8\7,\2\2\u00b8\u00bc\3\2\2\2\u00b9\u00bb"+
		"\13\2\2\2\u00ba\u00b9\3\2\2\2\u00bb\u00be\3\2\2\2\u00bc\u00bd\3\2\2\2"+
		"\u00bc\u00ba\3\2\2\2\u00bd\u00bf\3\2\2\2\u00be\u00bc\3\2\2\2\u00bf\u00c0"+
		"\7,\2\2\u00c0\u00c1\7\61\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c3\b \2\2\u00c3"+
		"@\3\2\2\2\16\2~\u0082\u0087\u008c\u0092\u0099\u009b\u00a0\u00a7\u00b1"+
		"\u00bc\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
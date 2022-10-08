// Generated from /Users/rockita/Code/req-weak/stl/src/main/java/edu/cmu/stl/encoder/int/parser/STLInt.g4 by ANTLR 4.9.2

	package antlr.parser;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class STLIntLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, AND=11, OR=12, NOT=13, IMP=14, IFF=15, GT=16, GE=17, LT=18, LE=19, 
		MUL=20, DIV=21, ADD=22, SUB=23, ID=24, INT=25, NEWLINE=26, WS=27, Comment=28, 
		Comment2=29;
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
			"DIV", "ADD", "SUB", "ID", "INT", "NEWLINE", "WS", "Comment", "Comment2"
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


	public STLIntLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "STLInt.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\37\u00aa\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\3\2\3\2\3\3\3"+
		"\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13"+
		"\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\20"+
		"\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\24\3\24\3\24\3\25"+
		"\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\7\31y\n\31\f\31\16\31|\13"+
		"\31\3\32\5\32\177\n\32\3\32\6\32\u0082\n\32\r\32\16\32\u0083\3\33\5\33"+
		"\u0087\n\33\3\33\3\33\3\34\6\34\u008c\n\34\r\34\16\34\u008d\3\34\3\34"+
		"\3\35\3\35\3\35\3\35\7\35\u0096\n\35\f\35\16\35\u0099\13\35\3\35\3\35"+
		"\3\36\3\36\3\36\3\36\7\36\u00a1\n\36\f\36\16\36\u00a4\13\36\3\36\3\36"+
		"\3\36\3\36\3\36\3\u00a2\2\37\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13"+
		"\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61"+
		"\32\63\33\65\34\67\359\36;\37\3\2\7\3\2c|\6\2\62;C\\aac|\3\2\62;\4\2\13"+
		"\13\"\"\4\2\f\f\17\17\2\u00b0\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t"+
		"\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2"+
		"\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2"+
		"\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2"+
		"+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2"+
		"\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\3=\3\2\2\2\5?\3\2\2\2\7A\3\2\2\2\t"+
		"C\3\2\2\2\13E\3\2\2\2\rG\3\2\2\2\17J\3\2\2\2\21M\3\2\2\2\23O\3\2\2\2\25"+
		"Q\3\2\2\2\27S\3\2\2\2\31V\3\2\2\2\33Y\3\2\2\2\35]\3\2\2\2\37`\3\2\2\2"+
		"!d\3\2\2\2#f\3\2\2\2%i\3\2\2\2\'k\3\2\2\2)n\3\2\2\2+p\3\2\2\2-r\3\2\2"+
		"\2/t\3\2\2\2\61v\3\2\2\2\63~\3\2\2\2\65\u0086\3\2\2\2\67\u008b\3\2\2\2"+
		"9\u0091\3\2\2\2;\u009c\3\2\2\2=>\7*\2\2>\4\3\2\2\2?@\7+\2\2@\6\3\2\2\2"+
		"AB\7I\2\2B\b\3\2\2\2CD\7H\2\2D\n\3\2\2\2EF\7?\2\2F\f\3\2\2\2GH\7>\2\2"+
		"HI\7>\2\2I\16\3\2\2\2JK\7@\2\2KL\7@\2\2L\20\3\2\2\2MN\7.\2\2N\22\3\2\2"+
		"\2OP\7]\2\2P\24\3\2\2\2QR\7_\2\2R\26\3\2\2\2ST\7\61\2\2TU\7^\2\2U\30\3"+
		"\2\2\2VW\7^\2\2WX\7\61\2\2X\32\3\2\2\2YZ\7p\2\2Z[\7q\2\2[\\\7v\2\2\\\34"+
		"\3\2\2\2]^\7/\2\2^_\7@\2\2_\36\3\2\2\2`a\7>\2\2ab\7/\2\2bc\7@\2\2c \3"+
		"\2\2\2de\7@\2\2e\"\3\2\2\2fg\7@\2\2gh\7?\2\2h$\3\2\2\2ij\7>\2\2j&\3\2"+
		"\2\2kl\7>\2\2lm\7?\2\2m(\3\2\2\2no\7,\2\2o*\3\2\2\2pq\7\61\2\2q,\3\2\2"+
		"\2rs\7-\2\2s.\3\2\2\2tu\7/\2\2u\60\3\2\2\2vz\t\2\2\2wy\t\3\2\2xw\3\2\2"+
		"\2y|\3\2\2\2zx\3\2\2\2z{\3\2\2\2{\62\3\2\2\2|z\3\2\2\2}\177\7/\2\2~}\3"+
		"\2\2\2~\177\3\2\2\2\177\u0081\3\2\2\2\u0080\u0082\t\4\2\2\u0081\u0080"+
		"\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084"+
		"\64\3\2\2\2\u0085\u0087\7\17\2\2\u0086\u0085\3\2\2\2\u0086\u0087\3\2\2"+
		"\2\u0087\u0088\3\2\2\2\u0088\u0089\7\f\2\2\u0089\66\3\2\2\2\u008a\u008c"+
		"\t\5\2\2\u008b\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008b\3\2\2\2\u008d"+
		"\u008e\3\2\2\2\u008e\u008f\3\2\2\2\u008f\u0090\b\34\2\2\u00908\3\2\2\2"+
		"\u0091\u0092\7\61\2\2\u0092\u0093\7\61\2\2\u0093\u0097\3\2\2\2\u0094\u0096"+
		"\n\6\2\2\u0095\u0094\3\2\2\2\u0096\u0099\3\2\2\2\u0097\u0095\3\2\2\2\u0097"+
		"\u0098\3\2\2\2\u0098\u009a\3\2\2\2\u0099\u0097\3\2\2\2\u009a\u009b\b\35"+
		"\2\2\u009b:\3\2\2\2\u009c\u009d\7\61\2\2\u009d\u009e\7,\2\2\u009e\u00a2"+
		"\3\2\2\2\u009f\u00a1\13\2\2\2\u00a0\u009f\3\2\2\2\u00a1\u00a4\3\2\2\2"+
		"\u00a2\u00a3\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a3\u00a5\3\2\2\2\u00a4\u00a2"+
		"\3\2\2\2\u00a5\u00a6\7,\2\2\u00a6\u00a7\7\61\2\2\u00a7\u00a8\3\2\2\2\u00a8"+
		"\u00a9\b\36\2\2\u00a9<\3\2\2\2\n\2z~\u0083\u0086\u008d\u0097\u00a2\3\b"+
		"\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
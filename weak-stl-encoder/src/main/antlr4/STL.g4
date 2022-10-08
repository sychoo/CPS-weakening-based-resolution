// grammar name need to be aligned with filename the word followed by the pond sign determines the
// AST object name in visitor

// refer to following for logical expression
// https://ivanyu.me/blog/2014/09/13/creating-a-simple-parser-with-antlr/

// use camelCase for grammar definition

grammar STL;

@header {
	package edu.cmu.stl.encoder.parser;
}

/** The start rule; begin parsing here. */
prog: stat+;

stat:
	stl // StlExpr 
	| stl NEWLINE // StlExpr
	| NEWLINE ; // blank

/***********************/
/***** STL Formula *****/
/***********************/

// stl formula: consists of atomic proposition or temporal operators
// 
// note that atomic proposition (AP) represent a single logical expr (x > 1) that is evaluated to
// true or false. thus it has now been changed logicExpr

stl:
	'(' stl ')'				# StlParenExpr
	| logicExpr				# StlLogicExpr
	| globally				# StlGlobally
	| weakGlobally			# StlWeakGlobally
	| eventually			# StlEventually
	| weakEventually		# StlWeakEventually
	| op = 'not' stl		# StlNotExpr
	| stl op = '/\\' stl	# StlAndExpr
	| stl op = '\\/' stl	# StlOrExpr
	| stl op = '->' stl		# StlImpExpr
	| stl op = '<->' stl	# StlIffExpr;
// | notStl;

// notStl: 'not' stl; // restrict other logical operators for now

globally: op = 'G' timeBound '(' stl ')';

weakGlobally:
	op = 'G' weakLeftTimeBound '(' stl ')'
	| op = 'G' weakRightTimeBound '(' stl ')'
	| op = 'G' weakBothTimeBound '(' stl ')';

eventually: op = 'F' timeBound '(' stl ')';

weakEventually:
	op = 'F' weakLeftTimeBound '(' stl ')'
	| op = 'F' weakRightTimeBound '(' stl ')'
	| op = 'F' weakBothTimeBound '(' stl ')';

/*******************************************************/
/**** Logical, Comparison and Arithmetic Operators *****/
/*******************************************************/

// simple test for precedence p /\ not q -> r (p /\ (not q)) -> r

// atomic proposition: consists of all things that evaluates to True or False
logicExpr:
	'(' logicExpr ')'					# logicParens
	| op = 'not' logicExpr				# NotExpr
	| logicExpr op = '/\\' logicExpr	# AndExpr
	| logicExpr op = '\\/' logicExpr	# OrExpr
	| logicExpr op = '->' logicExpr		# ImpExpr
	| logicExpr op = '<->' logicExpr	# IffExpr
	// | '<<' compExpr '>>' '(' ID ',' INT ')'	# logicCompExprWeaken
	| compExpr # logicCompExpr;

// comparison expression must happen between two arithmetic expression arguments
compExpr:
	arithExpr op = ('=' | '>' | '<' | '>=' | '<=') arithExpr							# NormalCompExpr
	| '<<' arithExpr op = ('>' | '<' | '>=' | '<=') arithExpr '>>' '(' ID ',' FLOAT ')'	# WeakCompExpr;
	// the minimal requirement can be either integer or floating point numbers

// arithmetic expression
arithExpr:
	arithExpr op = ('*' | '/') arithExpr	# MulDivExpr // multiplication and division
	| arithExpr op = ('+' | '-') arithExpr	# AddSubExpr // addition and subtraction
	| INT									# Int // integer, exitInt 
	| FLOAT									# Float // floating point number
	| ID									# Id // identifier, exitId
	| '(' arithExpr ')'						# Parens ; // parenthese block

timeBound:
	'[' INT ',' INT ']'; // normal time bound														
weakLeftTimeBound:
	'[' '<<' INT '>>' '(' ID ',' INT ')' ',' INT ']'; // weaken left side time bound							
weakRightTimeBound:
	'[' INT ',' '<<' INT '>>' '(' ID ',' INT ')' ']'; // weaken right side time bound
weakBothTimeBound:
	'[' '<<' INT '>>' '(' ID ',' INT ')' ',' '<<' INT '>>' '(' ID ',' INT ')' ']';
	// weaken both sides of the time bound

/****************************/
/**** Logical Operators *****/
/****************************/

AND: '/\\';
OR: '\\/';
NOT: 'not';
IMP: '->';
IFF: '<->';

/****************************/
/*** Comparison Operators ***/
/****************************/

GT: '>';
GE: '>=';
LT: '<';
LE: '<=';

/****************************/
/*** Arithmetic Operators ***/
/****************************/

MUL: '*'; // assigns token name to '*' used above in grammar
DIV: '/';
ADD: '+';
SUB: '-';

// language constructs
ID:
	[a-z][a-zA-Z0-9_]*; // match identifiers lowerCase (UpperCase + Number)*
INT: '-'? DIGIT+; // match integers
FLOAT: DIGIT+ '.' DIGIT* | '.' DIGIT+; // match floats

fragment DIGIT: [0-9];
NEWLINE:
	'\r'? '\n'; // return newlines to parser (is end-statement signal)
WS: [ \t]+ -> skip; // toss out whitespace
// COMMENT: '/*' .* '*/' -> szkip;
Comment: '//' ~( '\r' | '\n')* -> skip;

Comment2: '/*' (.)*? '*/' -> skip;

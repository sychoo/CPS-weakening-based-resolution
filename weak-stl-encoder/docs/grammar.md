# define grammar
grammar is defined using ANTLR. go to `src/main/antlr4/STL.g4` to modify grammar.

# generate lexer & parser
lexer and parser are generated via `antlr4 maven plugin`. the plugin can be started via `mvn antlr4:antlr4`. the output directory is `src/main/java/edu/cmu/stl/parser/`

# test grammar
grammar can be visualized via `make grammar-gui`. input an STL expression such as `G[0,1](x > 1)`. grammar will be visualized as a tree structure via antlr test rig. 

local installation of antlr is required for visualization.
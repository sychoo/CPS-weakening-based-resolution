# must do make before make simulate
# note that -ea flag on java command (note not in javac command) is used to enable assertion statement for testing purposes
#
# define variables here
#
#stl_input_file="test/stl/nest_temp/nest_g_f_simple_comp_1.stl"
#stl_input_file="test/stl_res/1_req/simple_res.stl"
#stl_input_file="test/stl_res/1_req/simple_G_res.stl"
stl_input_file="test/stl_res/1_req/simple_F_res_timebound_1.stl"
#stl_input_file="test/stl/globally/g_ap_simple_comp_1.stl"
#stl_input_file="test/stl/single_ap/simple_comp_equal.stl"
#stl_input_file="sim/controller/resolver/requirements/DPSL.stl"
#signal_input_file="sim/controller/resolver/test/trace.sig"
#stl_input_file="test/experiments/battery/battery.stl"
#signal_input_file="test/experiments/battery/battery.sig"
#stl_input_file="test/experiments/dpsl/dpsl.stl"
#signal_input_file="test/experiments/dpsl/dpsl.sig"
#stl_input_file="test/stl/globally/g_ap_simple_comp_1_non_0_start.stl"
#stl_input_file="test/stl/globally/g_simple_logic_expr_1.stl"
#stl_input_file="test/stl_res/1_req/simple_g_res_timebound_2.stl"
#stl_input_file="test/stl_res/1_req/simple_g_res_timebound_1.stl"
#stl_input_file="test/stl_res/1_req/simple_g_res_timebound_3.stl"
#stl_input_file="test/stl_res/1_req/simple_F_res_timebound_1.stl"
## stl_input_file="test/stl_res/2_req/simple_res.stl"
#stl_input_file="test/env/battery/battery_spec.stl"

#signal_input_file="test/env/battery/battery.mzn"
signal_input_file="test/signal/2.sig"
#signal_input_file="test/signal/1.sig"
#signal_input_file="test/signal/0.sig"

all:

# generate ANTLR parser/lexer program
# eq to `antlr4 -no-listener -visitor STLInt.g4` in command line
	java -Xmx500M -cp "/usr/local/lib/antlr-4.9-complete.jar:$(CLASSPATH)" org.antlr.v4.Tool parser/STLInt.g4 -visitor -o antlr/

# compile ANTLR parser/lexer program generated
	javac -cp "/usr/local/lib/antlr-4.9-complete.jar:$(CLASSPATH)" antlr/parser/STLInt*.java

# compile signal processing module and its test kits
	javac -cp .:jar/json-java.jar:$(CLASSPATH): util/Signal.java

# compile Encoding module
	javac util/Encoding.java

# compile Result module
	javac -cp .:jar/json-java.jar:$(CLASSPATH): util/Result.java

# compile static function collection EmitEncoding
	javac util/EmitEncodingResult.java
	javac encoder/EmitEncoding.java

# compile file that walk through and evaluate the AST
# javac EvalVisitor.java
	javac encoder/AST.java
	javac -cp "/usr/local/lib/antlr-4.9-complete.jar:$(CLASSPATH)" parser/CompilationListener.java

# compile Main java program that interact with user
	javac -cp .:jar/json-java.jar:/usr/local/lib/antlr-4.9-complete.jar:$(CLASSPATH) main/Main.java
	java  -cp .:jar/json-java.jar:/usr/local/lib/antlr-4.9-complete.jar:$(CLASSPATH) main.Main $(stl_input_file) $(signal_input_file)

	@echo
	@echo
	@echo "stl input       : " $(stl_input_file)
	@echo "signal input    : " $(signal_input_file)
	@echo "encoding output :  cache/cache.mzn"
	@echo

testMain:
###### SAME TO all SECTION #####
# generate ANTLR parser/lexer program
# eq to `antlr4 -no-listener -visitor STLInt.g4` in command line
	java -Xmx500M -cp "/usr/local/lib/antlr-4.9-complete.jar:$(CLASSPATH)" org.antlr.v4.Tool parser/STLInt.g4 -visitor -o antlr/

# compile ANTLR parser/lexer program generated
	javac -cp "/usr/local/lib/antlr-4.9-complete.jar:$(CLASSPATH)" antlr/parser/STLInt*.java

# compile signal processing module and its test kits
	javac -cp .:jar/json-java.jar:$(CLASSPATH): util/Signal.java

# compile Encoding module
	javac util/Encoding.java

# compile Result module
	javac -cp .:jar/json-java.jar:$(CLASSPATH): util/Result.java

# compile static function collection EmitEncoding
	javac util/EmitEncodingResult.java
	javac encoder/EmitEncoding.java

# compile file that walk through and evaluate the AST
# javac EvalVisitor.java
	javac encoder/AST.java
	javac -cp "/usr/local/lib/antlr-4.9-complete.jar:$(CLASSPATH)" parser/CompilationListener.java

# compile Main java program that interact with user
	javac -cp .:jar/json-java.jar:/usr/local/lib/antlr-4.9-complete.jar:$(CLASSPATH) main/Main.java
#java  -cp .:jar/json-java.jar:$(CLASSPATH) main.Main $(stl_input_file) $(signal_input_file)

##### /SAME TO all SECTION
	javac -cp .:jar/json-java.jar:/usr/local/lib/antlr-4.9-complete.jar:$(CLASSPATH): utiltest/MainTest.java
	java -ea -cp .:jar/json-java.jar:/usr/local/lib/antlr-4.9-complete.jar:$(CLASSPATH): utiltest.MainTest

testObjects:
# test Signal object
	javac -cp .:jar/json-java.jar:$(CLASSPATH) util/Signal.java
	javac -cp .:jar/json-java.jar:$(CLASSPATH) utiltest/SignalTest.java
	java  -ea -cp .:jar/json-java.jar:$(CLASSPATH) utiltest.SignalTest

# test Encoding object
	javac util/Encoding.java
	javac utiltest/EncodingTest.java
	java  -ea utiltest.EncodingTest

# test Result object
	javac -cp .:jar/json-java.jar:$(CLASSPATH): util/Result.java
	javac -cp .:jar/json-java.jar:$(CLASSPATH): utiltest/ResultTest.java
	java  -ea -cp .:jar/json-java.jar:$(CLASSPATH): utiltest.ResultTest

testUtil:
	javac util/CommandlineExec.java
	java util.CommandlineExec

testGrammarGUI:
# generate ANTLR parser/lexer program
# eq to `antlr4 -no-listener -visitor STLInt.g4` in command line
	java -Xmx500M -cp "/usr/local/lib/antlr-4.9-complete.jar:$(CLASSPATH)" org.antlr.v4.Tool -no-listener -visitor parser/STLInt.g4 -o antlr/

# compile program generated
	javac antlr/parser/STLInt*.java

# eq to `grun STLInt prog -tree -gui` in command line
# added current path `.` to classpath to allow program to find STLInt grammar module
	java -Xmx500M -cp ".:/usr/local/lib/antlr-4.9-complete.jar:$(CLASSPATH)" org.antlr.v4.gui.TestRig antlr.parser.STLInt prog -tree -gui

grammarGen:
# generate ANTLR parser/lexer program
# eq to `antlr4 -no-listener -visitor STLInt.g4` in command line
	java -Xmx500M -cp "/usr/local/lib/antlr-4.9-complete.jar:$(CLASSPATH)" org.antlr.v4.Tool -no-listener -visitor parser/STLInt.g4 -o antlr/

simulate:
# note that encoding program must be compiled before simulation
	find ./sim/ -type f -name "*.class" -delete
	javac -cp .:jar/json-java.jar:$(CLASSPATH): sim/controller/MainController.java
	java -cp .:jar/json-java.jar:$(CLASSPATH): sim.controller.MainController sim/experiment_config/drone.xml sim/experiment_result/drone_output.result
	java -cp .:jar/json-java.jar:$(CLASSPATH): sim.controller.MainController sim/experiment_config/drone.xml sim/experiment_result/drone_output.result

testSim:
#javac sim/controller/features/test/FeatureMainTest.java
#java sim.controller.features.test.FeatureMainTest
#java -ea -cp .:jar/json-java.jar:$(CLASSPATH): utiltest.MainTest
	javac sim/test/CoordsTest.java
	java -ea sim.test.CoordsTest

	javac sim/test/ObstacleAvoidanceTest.java
	java -ea sim.test.ObstacleAvoidanceTest

	@echo "End Of Test"

result:
	open ./sim/experiment_result/main_result/result.csv

cleanresult:
	mv ./sim/experiment_result/main_result/result.csv ~/.Trash/result.csv_$(date)

cleanSim:
	find ./sim/ -type f -name "*.class" -delete

testrebe:
	find ./sim/experiments/rebe/ -type f -name "*.class" -delete
	javac -cp .:jar/json-java.jar:$(CLASSPATH): sim/experiments/rebe/features/test/BoundaryEnforcerFeatureTest.java
	javac -cp .:jar/json-java.jar:$(CLASSPATH): sim/experiments/rebe/features/test/ChaserDroneFeatureTest.java
	java -ea -cp .:jar/json-java.jar:$(CLASSPATH): sim.experiments.rebe.features.test.BoundaryEnforcerFeatureTest
	java -ea -cp .:jar/json-java.jar:$(CLASSPATH): sim.experiments.rebe.features.test.ChaserDroneFeatureTest

clean:
# delete all files generated by ANTLR grammar file STLInt.g4 along with *.class file
#	rm  STLInt.tokens          STLIntLexer.java   STLIntVisitor.java\
#		STLIntBaseVisitor.java STLIntLexer.tokens STLInt.interp\
#		STLIntLexer.interp     STLIntParser.java  STLIntBaseListener.java\
#	  	STLIntListener.java\
#		*.class\
#		cache/*

	# delete all java class files
	find ./ -type f -name "*.class" -delete

	# delete auto-generated antlr parser programs
	rm -rf antlr

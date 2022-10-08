# Pitfalls
- when running the experiments, make sure to configure the absolute path
`sim/experiments/dpsl2/environment/cache-params.mzn` is included in the `sim/experiments/dpsl2/environment/env.mzn` file

# MainController for Dragonfly Simulator modified version
- java MainController <environment XML file> <experiment result file>
- make sure to turn on the ExperimentMain.enableExperimentAutomation
  flag to true

# Make a jar file
# https://www.baeldung.com/java-create-jar

# https://github.com/arothuis/antlr4-calculator/blob/4077cea5508b6ac40ac8b863126f01078f9b8fb3/src/main/java/nl/arothuis/antlr4calculator/core/parser/CalculationVisitor.java
# make
- compile and run grammar along with main application code

# make testGrammarGUI
- test the grammar independently from the application code

# make testObject
- test the objects defined, including Signal and Encoding object

# make clean
- clean all Java class files and auto-generated lexer/parser files

# Architecture
- CompilationListener uses stack data structure to temporarily store the evaluation results

# References
## Stack-based Calculator
- https://github.com/arothuis/antlr4-calculator/blob/master/src/main/java/nl/arothuis/antlr4calculator/core/parser/CalculationListener.java

# STL expressions that is currently supported
## atomic proposition
- x > 1

# Challenge 1: how to deal with p variable (decision array for choosing the max/min)

# Compilation Level
- a single atomic proposition
    - x > 1
- logical expression only (but multiple, in the form of conjunction and concatenation)
    - x > 1 /\ y > 1
    - x > 1 \/ y > 1
- temporal operator
    - G[0, 1](x > 1 /\ y > 1)
    - G[0, 1](x > 1 \/ y > 1)
    - F[0, 1](x > 1 /\ y > 1)
    - F[0, 1](x > 1 \/ y > 1)

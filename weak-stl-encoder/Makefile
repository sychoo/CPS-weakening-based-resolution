stl-int: antlr
	mvn exec:java -Dexec.mainClass="edu.cmu.stl.encoder.ints.main.Main" 
	-Dexec.args="src/test/java/edu/cmu/stl/encoder/floats/input"

stl-float: antlr
	mvn exec:java -Dexec.mainClass="edu.cmu.stl.encoder.floats.STLEncoder" -Dexec.args="$*"

# test: antlr
package: antlr
# mvn clean compile package
# this command is to make a standalone jar file with dependencies
	mvn clean compile assembly:single

reinstall-dependencies:
	mvn dependency:purge-local-repository

update-dependencies:
	mvn dependency:resolve
	mvn install

grammar-gui: antlr
# for stl-float
# requires antlr 4.10.1 to be installed
	cd src/main/java && javac edu/cmu/stl/encoder/parser/STL*.java && java -Xmx500M -cp ".:/usr/local/lib/antlr-4.10.1-complete.jar:$(CLASSPATH)" org.antlr.v4.gui.TestRig edu.cmu.stl.encoder.parser.STL prog -tree -gui

antlr:
# generate parser using .g4
	mvn antlr4:antlr4

# simple test program that can be messed around
main:
	mvn exec:java -Dexec.mainClass="edu.cmu.stl.Main"

simulate:
	mvn exec:java -Dexec.mainClass="edu.cmu.stl.mavsdk.Simulator"

organ:
	mvn exec:java -Dexec.mainClass="edu.cmu.stl.mavsdk.case_study.organ_delivery.OrganDeliveryMain"

boundary:
	#mvn exec:java -Dexec.mainClass="edu.cmu.stl.mavsdk.case_study.boundary_runaway.BoundaryRunawayMain"
	mvn exec:java -Dexec.mainClass="edu.cmu.stl.mavsdk.case_study.boundary_runaway.missions.FlyEight"

update-local-library:
# this will update all the local code using the STLEncoder maven project to the latest code
	mvn clean install 
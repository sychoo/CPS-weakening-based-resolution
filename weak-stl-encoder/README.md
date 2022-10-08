# Maven Encapsulation
- [Import Maven Project in another Maven Project](https://alegrucoding.com/java-add-maven-project-as-a-dependency-to-another-project/)
- TL;DR:
    - Step 1: Download the maven repository
    - Step 2: Execute `make clean install` to install the maven project in the `.m2` directory.
    - Step 3: In the `pom.xml` file of the Maven project where you would like to import the stl constraint encoder, put the following dependency in the `<dependencies>` section.
```
<dependency>
    <groupId>edu.cmu.stl</groupId>
    <artifactId>stl</artifactId>
    <version>1.0</version>
</dependency>
```

# Installation
## Grammar Visualization (ANTLR)
- Install `antlr-4.10.1-complete.jar` under `/usr/local/lib/`
- [ANTLR](https://www.antlr.org/)

## MiniZinc and Gurobi Solver (MiniZinc backend)
- Install MiniZinc App and command line tools to run 
- [MiniZinc](https://www.minizinc.org/doc-2.5.5/en/installation.html)
- [Gurobi](https://www.gurobi.com/)

## Source Code Download
- **Option 1**: Download the latest source code on GitHub by running the following command.

    ```git clone git@github.com:sychoo/STL-Float.git```

- **Option 2**: Download binary releases from [release page](https://github.com/sychoo/STL-Float/releases/tag/1.0).

## Import
- import to maven project
    - if source code is downloaded via `github clone`, in the directory of the repo, execute the following command:
    
        ```mvn clean install```
    - if you have installed the `.jar` file and `.pom` file, follow the [tutorial](https://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html) by Apache Maven Project.

- use for single file Java program (.jar)
    - Currently does not support single file program without Maven package manager

# File Structure
- `encoder/` contains program for parser and encoding weakSTL expression into a MILP formulation
- `mavsdk/` contains program for running the PX4 jMAVSim simulator

# Local Commands
- `runpx4` runs the jMAVSim simulator
- `runpx4_multiple` runs multiple jMAVSim instances 
- search `QGroundControl` to open the ground control application

# Maven Plugins
[Packaging with dependencies](https://www.sohamkamani.com/java/cli-app-with-maven/)
[ANTLR Plugin 4.3](https://www.antlr.org/api/maven-plugin/latest/usage.html)
- `mvn antlr4:antlr4`
- .g4 file located in `src/main/antlr4`
- [Documentation](https://www.antlr.org/api/maven-plugin/latest/antlr4-mojo.html)
- [Tutorial](https://codevomit.wordpress.com/2015/03/15/antlr4-project-with-maven-tutorial-episode-1/)
- [Execute Maven Plugin](https://stackoverflow.com/questions/3166538/how-to-execute-maven-plugin-execution-directly-from-command-line)
- [POM configuration (works)](https://stackoverflow.com/questions/68714724/upgrading-past-antlr-4-5-no-longer-processes-grammar-in-maven)
<!-- - [Tutorial](https://www.alexecollins.com/antlr4-and-maven-tutorial/) -->
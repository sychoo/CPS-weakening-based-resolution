# CPS-weakening-based-resolution
## Introduction
This reposistory consists of two main components:
- __*weakSTL* constraint encoder__ (`weak-stl-encoder/` directory), which converts specs in *STL* and *weakSTL* formula to MILP constraints specified in *MiniZinc* to (1) calculates the robustness value of one or more *STL* formulas with respect to a constant signal or a deterministic environmental model. (2) calculate the predictive actions based on *weakSTL* formula and environmental model, minimizing the degree of weakening.
- __code for experiments__ (`px4-experiments/` directory), which has various MAVSDK-based drone missions (surveillance, fly-circle), and the implementation of various onboard enforcers for the drones (boundary enforcer, boundary enforcer, safe landing enforcer, etc.). These code interacts with jMAVSim flight simulator. There are also various scripts for running the experiments in batches and plotting the results. Together, they support the execution of the organ delivery drone case study and the boundary/runaway drone case study. (Note that each case study requires different simulator startup scripts, due to different number of drones required for each case study).

## Installation
### 3rd-Party Tools/Libraries
1. Maven (Java package manager) is required to build the project. Please follow the instructions [here](https://maven.apache.org/install.html) to install Maven.
2. MiniZinc (MILP solver) is required to solve the MILP constraints. Please follow the instructions [here](https://www.minizinc.org/software.html) to install MiniZinc.
    - In addition, we require the Gurobi backend for the MiniZinc solver to support floating-point constraints. The current MiniZinc release requires `Gurobi 9.1.1`. However, it may change in future release. Please follow the instructions [here](https://www.gurobi.com/documentation/9.1/quickstart_mac/cs_installation_guid.html) to install Gurobi. If you are using Gurobi for educational purposes, you can use it for free by applying for academic license [here](https://www.gurobi.com/academia/academic-program-and-licenses/).
3. PX4 drone simulation toolkit: Follow the instruction [here](https://docs.px4.io/main/en/dev_setup/dev_env_mac.html) for macOS setup. For other platforms, please follow the instructions [here](https://docs.px4.io/main/en/dev_setup/dev_env.html).
   - To use MAVSDK-Java, we also need to install MAVSDK Server by following [here](https://auterion.com/getting-started-with-mavsdk-java/).

### This Repository
1. weakSTL Encoder: 
    - Clone this repository to your local machine.
    - Go to `weak-stl-encoder/` directory.
    - Run `mvn clean install` to install and test the project.
    - Once the weakSTL Encoder is installed globally to all Maven project, in the `pom.xml` file of the Maven project where you would like to import the stl constraint encoder, put the following dependency in the `<dependencies>` section.
    ```
    <dependency>
        <groupId>edu.cmu.stl</groupId>
        <artifactId>stl</artifactId>
        <version>1.0</version>
    </dependency>
    ```
References
- [Import Maven Project in another Maven Project](https://alegrucoding.com/java-add-maven-project-as-a-dependency-to-another-project/)

## Get Started

<!-- ## px4-experiments
- simulation of feature interaction using autonomous drone using PX4 using weakening-based and priority-based resolution

## weak-stl-encoder
- encode weakSTL expression using MILP constraint using MiniZinc -->

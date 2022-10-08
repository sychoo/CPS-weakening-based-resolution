#!/bin/bash
# this script allows to run two experiment
# Use 200 seconds for RUNPX4_TIME Parameter
# RUNPX4_TIME=250 - priority based feature
# RUNPX4_TIME=220 - weakening?
count=200

for i in $(seq 196 $count)
do
    echo "======================"
    echo "Running experiment $i"
    echo "======================"

  # command to start the PX4 simulator and mavsdk-server
  # 50 seconds
    test_runpx4_multiple

    # give time to establish connection with mavsdk_server and QGroundControl
    sleep 20

    pushd ../../

    # stop the program at 200 seconds mark
    # must be greater than the execution cycle time 200 cycle * 0.5 seconds/cycle
    # 300 seconds for the simulator to finish --> additional 100 seconds for data analysis 
    # if the command is not finished on the solver, that means that robustness calculation is taking way too long
    echo "CONFIG: /Users/rockita/Code/req-weak/px4-experiments/experiments/config/bound_run/randomized_config/config_$i.ini"
    gtimeout 400 mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.BoundaryRunawayMain" -Dexec.args="experiments/config/bound_run/randomized_config/config_$i.ini"; sleep 40

    # above this point, the simulator `test_runpx4_multiple` should execute longer than the main program (BoundaryRunawayMain)
    # below this point, the `sleep` command should leave enough time 

    # leave some time here to kill the processes

    popd

    # pkill -15 -P $$
    # pkill -15 px4
    # pkill -15 mavsdk-server
    echo "======================"
    echo "Experiment $i finished"
    echo "======================"

done





# execute the first experiements again from 1 - 100

for i in 5 6 8 9 10 11 12 13 14 15 16 17
do
    echo "======================"
    echo "Running experiment $i"
    echo "======================"

  # command to start the PX4 simulator and mavsdk-server
  # 50 seconds
    test_runpx4_multiple

    # give time to establish connection with mavsdk_server and QGroundControl
    sleep 20

    pushd ../../

    # stop the program at 200 seconds mark
    # must be greater than the execution cycle time 200 cycle * 0.5 seconds/cycle
    # 300 seconds for the simulator to finish --> additional 100 seconds for data analysis 
    # if the command is not finished on the solver, that means that robustness calculation is taking way too long
    echo "CONFIG: /Users/rockita/Code/req-weak/px4-experiments/experiments/config/bound_run/randomized_config/config_$i.ini"
    gtimeout 400 mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.BoundaryRunawayMain" -Dexec.args="experiments/config/bound_run/randomized_config/config_$i.ini"; sleep 40

    # above this point, the simulator `test_runpx4_multiple` should execute longer than the main program (BoundaryRunawayMain)
    # below this point, the `sleep` command should leave enough time 

    # leave some time here to kill the processes

    popd

    # pkill -15 -P $$
    # pkill -15 px4
    # pkill -15 mavsdk-server
    echo "======================"
    echo "Experiment $i finished"
    echo "======================"

done



# execute the new batch (romulo dataset) randomized starting position only
for i in $(seq 1 200)
do
    echo "======================"
    echo "Running experiment $i"
    echo "======================"

  # command to start the PX4 simulator and mavsdk-server
  # 50 seconds
    test_runpx4_multiple

    # give time to establish connection with mavsdk_server and QGroundControl
    sleep 20

    pushd ../../

    # stop the program at 200 seconds mark
    # must be greater than the execution cycle time 200 cycle * 0.5 seconds/cycle
    # 300 seconds for the simulator to finish --> additional 100 seconds for data analysis 
    # if the command is not finished on the solver, that means that robustness calculation is taking way too long
    echo "CONFIG: /Users/rockita/Code/req-weak/px4-experiments/experiments/config/bound_run/randomized_config/config_$i.ini"
    gtimeout 400 mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.BoundaryRunawayMain" -Dexec.args="experiments/config/bound_run/randomized_config_starting_position_only/config_$i.ini"; sleep 40

    # above this point, the simulator `test_runpx4_multiple` should execute longer than the main program (BoundaryRunawayMain)
    # below this point, the `sleep` command should leave enough time 

    # leave some time here to kill the processes

    popd

    # pkill -15 -P $$
    # pkill -15 px4
    # pkill -15 mavsdk-server
    echo "======================"
    echo "Experiment $i finished"
    echo "======================"

done
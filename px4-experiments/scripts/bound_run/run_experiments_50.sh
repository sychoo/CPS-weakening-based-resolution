#!/bin/bash
# this script allows to run two experiment
# RUNPX4_TIME=100
count=2
# RUNPX4_TIME=100

for i in $(seq $count)
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
    gtimeout 80 mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.BoundaryRunawayMain"; sleep 30

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


#!/bin/bash
# this script allows to run two experiment

# the timing of the experiment can be slightly tricky (it is machine dependent)
# you have to make sure that
# (1) the simulator is running longer than the main program (RUNPX4_TIME environment variable)
# (2) at the end, the simulator must terminate together with the main program (make sure to wait for some time )
# # this is so that the simulator (jMAVSim and mavsdk-server does not continue running in the next round of simulation)
# visual representation of the timing
# -----|-----------------------------------|----------------------|---------------------------------|
# simulator start    <sleep 20>       main program start     main program end  <sleep>     simulator end
#
# *  the first sleep is to give time for the simulator to start and the main program to connect to the simulator
# * the second sleep is to give time for the main program to finish and the simulator to terminate

# pitfall
# if the main program is taking longer than expected, simply increase the time allocated for the main program
# `gtimeout <seconds> mvn ...`
count=2

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


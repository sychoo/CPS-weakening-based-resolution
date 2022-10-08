#!/bin/bash

# make the terminal NOT restore previous sessions
# https://apple.stackexchange.com/questions/187765/clear-terminal-and-prevent-restoration

#for i in $(seq 196 $count)
DIR=/Users/rockita/Code/req-weak/px4-experiments/experiments/results/organ_delivery/result/robustness

# randomized dataset
for i in  $(seq 1 100)
do

    if [[ $(ls $DIR/*config_"$i"_*.csv) ]]; then
        echo "there are files config_"$i"_.csv"
    else
        echo "no files found for config_"$i"_.csv"

        echo "======================"
        echo "Running experiment $i"
        echo "======================"
        
        # this is to set the max limit to 400 seconds. If upon exceeding this limit
        # the program is still running, then shut down all the deem the process as failed 
        gtimeout 150 organ "$i"

        pkill -9 -f jmavsim_run.jar
        pkill -9 -f mavsdk_server
        pkill -9 -f px4
        pkill -9 -f airsim
        pkill -9 -f mavsdk_server_macos

        killall Terminal
        echo "======================"
        echo "Finished experiment $i"
        echo "======================"
        sleep 10
    fi
done




# randomized dataset
for i in  $(seq 1 100)
do

    if [[ $(ls $DIR/*config_"$i"_*.csv) ]]; then
        echo "there are files config_"$i"_.csv"
    else
        echo "no files found for config_"$i"_.csv"

        echo "======================"
        echo "Running experiment $i"
        echo "======================"
        
        # this is to set the max limit to 400 seconds. If upon exceeding this limit
        # the program is still running, then shut down all the deem the process as failed 
        gtimeout 150 organ "$i"

        pkill -9 -f jmavsim_run.jar
        pkill -9 -f mavsdk_server
        pkill -9 -f px4
        pkill -9 -f airsim
        pkill -9 -f mavsdk_server_macos

        killall Terminal
        echo "======================"
        echo "Finished experiment $i"
        echo "======================"
        sleep 10
    fi
done





# randomized dataset
for i in  $(seq 1 100)
do

    if [[ $(ls $DIR/*config_"$i"_*.csv) ]]; then
        echo "there are files config_"$i"_.csv"
    else
        echo "no files found for config_"$i"_.csv"

        echo "======================"
        echo "Running experiment $i"
        echo "======================"
        
        # this is to set the max limit to 400 seconds. If upon exceeding this limit
        # the program is still running, then shut down all the deem the process as failed 
        gtimeout 150 organ "$i"

        pkill -9 -f jmavsim_run.jar
        pkill -9 -f mavsdk_server
        pkill -9 -f px4
        pkill -9 -f airsim
        pkill -9 -f mavsdk_server_macos

        killall Terminal
        echo "======================"
        echo "Finished experiment $i"
        echo "======================"
        sleep 10
    fi
done


# randomized dataset
for i in  $(seq 1 100)
do

    if [[ $(ls $DIR/*config_"$i"_*.csv) ]]; then
        echo "there are files config_"$i"_.csv"
    else
        echo "no files found for config_"$i"_.csv"

        echo "======================"
        echo "Running experiment $i"
        echo "======================"
        
        # this is to set the max limit to 400 seconds. If upon exceeding this limit
        # the program is still running, then shut down all the deem the process as failed 
        gtimeout 150 organ "$i"

        pkill -9 -f jmavsim_run.jar
        pkill -9 -f mavsdk_server
        pkill -9 -f px4
        pkill -9 -f airsim
        pkill -9 -f mavsdk_server_macos

        killall Terminal
        echo "======================"
        echo "Finished experiment $i"
        echo "======================"
        sleep 10
    fi
done


# randomized dataset
for i in  $(seq 1 100)
do

    if [[ $(ls $DIR/*config_"$i"_*.csv) ]]; then
        echo "there are files config_"$i"_.csv"
    else
        echo "no files found for config_"$i"_.csv"

        echo "======================"
        echo "Running experiment $i"
        echo "======================"
        
        # this is to set the max limit to 400 seconds. If upon exceeding this limit
        # the program is still running, then shut down all the deem the process as failed 
        gtimeout 150 organ "$i"

        pkill -9 -f jmavsim_run.jar
        pkill -9 -f mavsdk_server
        pkill -9 -f px4
        pkill -9 -f airsim
        pkill -9 -f mavsdk_server_macos

        killall Terminal
        echo "======================"
        echo "Finished experiment $i"
        echo "======================"
        sleep 10
    fi
done
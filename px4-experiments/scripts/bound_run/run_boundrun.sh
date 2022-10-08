#!/bin/bash

# make the terminal NOT restore previous sessions
# https://apple.stackexchange.com/questions/187765/clear-terminal-and-prevent-restoration

# randomized test
# for i in 91 98 45 139 140 142 144 145 146 147 148 150 152 153 154 155 156 157 158 159 161 162 166 167 175 177 178 180 181 182 183 184 199 200

#for i in $(seq 196 $count)
# for i in $(seq 99 100)
# do
#     echo "======================"
#     echo "Running experiment $i"
#     echo "======================"
	
#     # this is to set the max limit to 400 seconds. If upon exceeding this limit
#     # the program is still running, then shut down all the deem the process as failed 
#     gtimeout 350 boundrun "randomized_config/config_$i.ini"

#     pkill -9 -f jmavsim_run.jar
#     pkill -9 -f mavsdk_server
#     pkill -9 -f px4
#     pkill -9 -f airsim
#     pkill -9 -f mavsdk_server_macos

# #     killall Terminal
# #     echo "======================"
# #     echo "Finished experiment $i"
# #     echo "======================"
# # done


# for i in 95

# do
#     # pkill -9 -f jmavsim_run.jar
#     # pkill -9 -f mavsdk_server
#     # pkill -9 -f px4
#     # pkill -9 -f airsim
#     # pkill -9 -f mavsdk_server_macos

#     echo "======================"
#     echo "Running experiment $i"
#     echo "======================"
	
#     # this is to set the max limit to 400 seconds. If upon exceeding this limit
#     # the program is still running, then shut down all the deem the process as failed 
#     gtimeout 350 boundrun "randomized_config/config_$i.ini"

#     pkill -9 -f jmavsim_run.jar
#     pkill -9 -f mavsdk_server
#     pkill -9 -f px4
#     pkill -9 -f airsim
#     pkill -9 -f mavsdk_server_macos

#     killall Terminal
#     echo "======================"
#     echo "Finished experiment $i"
#     echo "======================"
#     sleep 10
# done
DIR=/Users/rockita/Code/req-weak/px4-experiments/experiments/results/bound_run/result/robustness

for i in $(seq 101 200)

do
    if [[ $(ls $DIR/*config_"$i"_*.csv) ]]; then
        echo "there are files config_"$i"_.csv"
    else
        echo "no files found for config_"$i"_.csv"
    
        # pkill -9 -f jmavsim_run.jar
        # pkill -9 -f mavsdk_server
        # pkill -9 -f px4
        # pkill -9 -f airsim
        # pkill -9 -f mavsdk_server_macos

        echo "======================"
        echo "Running experiment $i"
        echo "======================"
        
        # this is to set the max limit to 400 seconds. If upon exceeding this limit
        # the program is still running, then shut down all the deem the process as failed 
        gtimeout 350 boundrun "randomized_config/config_$i.ini"

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

# use the file to check if the experiments has been done

DIR=/Users/rockita/Code/req-weak/px4-experiments/experiments/results/bound_run/result/robustness

for i in $(seq 101 200)

do
    if [[ $(ls $DIR/*config_"$i"_*.csv) ]]; then
        echo "there are files config_"$i"_.csv"
    else
        echo "no files found for config_"$i"_.csv"
    
        # pkill -9 -f jmavsim_run.jar
        # pkill -9 -f mavsdk_server
        # pkill -9 -f px4
        # pkill -9 -f airsim
        # pkill -9 -f mavsdk_server_macos

        echo "======================"
        echo "Running experiment $i"
        echo "======================"
        
        # this is to set the max limit to 400 seconds. If upon exceeding this limit
        # the program is still running, then shut down all the deem the process as failed 
        gtimeout 350 boundrun "randomized_config/config_$i.ini"

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


DIR=/Users/rockita/Code/req-weak/px4-experiments/experiments/results/bound_run/result/robustness

for i in $(seq 101 200)

do
    if [[ $(ls $DIR/*config_"$i"_*.csv) ]]; then
        echo "there are files config_"$i"_.csv"
    else
        echo "no files found for config_"$i"_.csv"
    
        # pkill -9 -f jmavsim_run.jar
        # pkill -9 -f mavsdk_server
        # pkill -9 -f px4
        # pkill -9 -f airsim
        # pkill -9 -f mavsdk_server_macos

        echo "======================"
        echo "Running experiment $i"
        echo "======================"
        
        # this is to set the max limit to 400 seconds. If upon exceeding this limit
        # the program is still running, then shut down all the deem the process as failed 
        gtimeout 300 boundrun "randomized_config/config_$i.ini"

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


DIR=/Users/rockita/Code/req-weak/px4-experiments/experiments/results/bound_run/result/robustness

for i in $(seq 101 200)

do
    if [[ $(ls $DIR/*config_"$i"_*.csv) ]]; then
        echo "there are files config_"$i"_.csv"
    else
        echo "no files found for config_"$i"_.csv"
    
        # pkill -9 -f jmavsim_run.jar
        # pkill -9 -f mavsdk_server
        # pkill -9 -f px4
        # pkill -9 -f airsim
        # pkill -9 -f mavsdk_server_macos

        echo "======================"
        echo "Running experiment $i"
        echo "======================"
        
        # this is to set the max limit to 400 seconds. If upon exceeding this limit
        # the program is still running, then shut down all the deem the process as failed 
        gtimeout 300 boundrun "randomized_config/config_$i.ini"

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


DIR=/Users/rockita/Code/req-weak/px4-experiments/experiments/results/bound_run/result/robustness

for i in $(seq 101 200)

do
    if [[ $(ls $DIR/*config_"$i"_*.csv) ]]; then
        echo "there are files config_"$i"_.csv"
    else
        echo "no files found for config_"$i"_.csv"
    
        # pkill -9 -f jmavsim_run.jar
        # pkill -9 -f mavsdk_server
        # pkill -9 -f px4
        # pkill -9 -f airsim
        # pkill -9 -f mavsdk_server_macos

        echo "======================"
        echo "Running experiment $i"
        echo "======================"
        
        # this is to set the max limit to 400 seconds. If upon exceeding this limit
        # the program is still running, then shut down all the deem the process as failed 
        gtimeout 300 boundrun "randomized_config/config_$i.ini"

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


DIR=/Users/rockita/Code/req-weak/px4-experiments/experiments/results/bound_run/result/robustness

for i in $(seq 101 200)

do
    if [[ $(ls $DIR/*config_"$i"_*.csv) ]]; then
        echo "there are files config_"$i"_.csv"
    else
        echo "no files found for config_"$i"_.csv"
    
        # pkill -9 -f jmavsim_run.jar
        # pkill -9 -f mavsdk_server
        # pkill -9 -f px4
        # pkill -9 -f airsim
        # pkill -9 -f mavsdk_server_macos

        echo "======================"
        echo "Running experiment $i"
        echo "======================"
        
        # this is to set the max limit to 400 seconds. If upon exceeding this limit
        # the program is still running, then shut down all the deem the process as failed 
        gtimeout 300 boundrun "randomized_config/config_$i.ini"

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


DIR=/Users/rockita/Code/req-weak/px4-experiments/experiments/results/bound_run/result/robustness

for i in $(seq 101 200)

do
    if [[ $(ls $DIR/*config_"$i"_*.csv) ]]; then
        echo "there are files config_"$i"_.csv"
    else
        echo "no files found for config_"$i"_.csv"
    
        # pkill -9 -f jmavsim_run.jar
        # pkill -9 -f mavsdk_server
        # pkill -9 -f px4
        # pkill -9 -f airsim
        # pkill -9 -f mavsdk_server_macos

        echo "======================"
        echo "Running experiment $i"
        echo "======================"
        
        # this is to set the max limit to 400 seconds. If upon exceeding this limit
        # the program is still running, then shut down all the deem the process as failed 
        gtimeout 300 boundrun "randomized_config/config_$i.ini"

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


DIR=/Users/rockita/Code/req-weak/px4-experiments/experiments/results/bound_run/result/robustness

for i in $(seq 101 200)

do
    if [[ $(ls $DIR/*config_"$i"_*.csv) ]]; then
        echo "there are files config_"$i"_.csv"
    else
        echo "no files found for config_"$i"_.csv"
    
        # pkill -9 -f jmavsim_run.jar
        # pkill -9 -f mavsdk_server
        # pkill -9 -f px4
        # pkill -9 -f airsim
        # pkill -9 -f mavsdk_server_macos

        echo "======================"
        echo "Running experiment $i"
        echo "======================"
        
        # this is to set the max limit to 400 seconds. If upon exceeding this limit
        # the program is still running, then shut down all the deem the process as failed 
        gtimeout 300 boundrun "randomized_config/config_$i.ini"

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


DIR=/Users/rockita/Code/req-weak/px4-experiments/experiments/results/bound_run/result/robustness

for i in $(seq 101 200)

do
    if [[ $(ls $DIR/*config_"$i"_*.csv) ]]; then
        echo "there are files config_"$i"_.csv"
    else
        echo "no files found for config_"$i"_.csv"
    
        # pkill -9 -f jmavsim_run.jar
        # pkill -9 -f mavsdk_server
        # pkill -9 -f px4
        # pkill -9 -f airsim
        # pkill -9 -f mavsdk_server_macos

        echo "======================"
        echo "Running experiment $i"
        echo "======================"
        
        # this is to set the max limit to 400 seconds. If upon exceeding this limit
        # the program is still running, then shut down all the deem the process as failed 
        gtimeout 300 boundrun "randomized_config/config_$i.ini"

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


DIR=/Users/rockita/Code/req-weak/px4-experiments/experiments/results/bound_run/result/robustness

for i in $(seq 101 200)

do
    if [[ $(ls $DIR/*config_"$i"_*.csv) ]]; then
        echo "there are files config_"$i"_.csv"
    else
        echo "no files found for config_"$i"_.csv"
    
        # pkill -9 -f jmavsim_run.jar
        # pkill -9 -f mavsdk_server
        # pkill -9 -f px4
        # pkill -9 -f airsim
        # pkill -9 -f mavsdk_server_macos

        echo "======================"
        echo "Running experiment $i"
        echo "======================"
        
        # this is to set the max limit to 400 seconds. If upon exceeding this limit
        # the program is still running, then shut down all the deem the process as failed 
        gtimeout 300 boundrun "randomized_config/config_$i.ini"

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

# # romulo's data set
# # for i in 10 11 12 13 14 15 17 18 19 20 21 22 23 24 25 26 27 28 29 32 33 35 37 38 40 42 43 44 45 46 
# for i in $(seq 1 200)
# do
#     echo "======================"
#     echo "Running experiment $i"
#     echo "======================"
	
#     # this is to set the max limit to 400 seconds. If upon exceeding this limit
#     # the program is still running, then shut down all the deem the process as failed 
#     gtimeout 350 boundrun "randomized_config_starting_position_only/config_$i.ini"

#     pkill -9 -f jmavsim_run.jar
#     pkill -9 -f mavsdk_server
#     pkill -9 -f px4
#     pkill -9 -f airsim
#     pkill -9 -f mavsdk_server_macos

#     killall Terminal
#     sleep 5
#     echo "======================"
#     echo "Finished experiment $i"
#     echo "======================"
# done



# # # romulo's data set
# # for i in $(seq 47 200)
# # do
# #     echo "======================"
# #     echo "Running experiment $i"
# #     echo "======================"
	
# #     # this is to set the max limit to 400 seconds. If upon exceeding this limit
# #     # the program is still running, then shut down all the deem the process as failed 
# #     gtimeout 350 boundrun "randomized_config_starting_position_only/config_$i.ini"

# #     pkill -9 -f jmavsim_run.jar
# #     pkill -9 -f mavsdk_server
# #     pkill -9 -f px4
# #     pkill -9 -f airsim
# #     pkill -9 -f mavsdk_server_macos

# #     killall Terminal
# #     sleep 5
# #     echo "======================"
# #     echo "Finished experiment $i"
# #     echo "======================"
# # done




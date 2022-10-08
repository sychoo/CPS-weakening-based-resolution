#!/bin/bash
# https://superuser.com/questions/174576/opening-a-new-terminal-from-the-command-line-and-running-a-command-on-mac-os-x

# start 2 drones
# make sure to set the environment variable $PX4_DIR to the root directory of PX4_DIR

# pkill -15 -P $$
# pkill -15 px4

# pkill -15 -P $$
# pkill -15 px4

# kill -9 $$
# pkill -9 -f jmavsim_run.jar
# pkill -9 -f mavsdk_server
# pkill -9 -f px4
# pkill -9 -f airsim
# pkill -9 -f mavsdk_server_macos

# pkill -15 -P $$
# pkill -15 px4

# # pkill -15 -P $$
# pkill -15 px4

# # kill -9 $$

##### start mavsdk-server #####
# ./mavsdk_server_macos -p 50051 udp://:14540
# ./mavsdk_server_macos -p 50052 udp://:14541

# Mavlink Router - refer to Ben's script
# used to add multiple MAVSDK endpoints to easier control of multiple drones
# osascript -e 'tell app "Terminal"
#     do script "
#         pushd $PX4_DIR/mavlink-router/
#         ./mavlink-routerd -e 127.0.0.1:14690 -e 127.0.0.1:14691  -t 0 0.0.0.0:14540
#         popd"
# end tell'

# osascript -e 'tell app "Terminal"
#     do script "
#         pushd $PX4_DIR/mavlink-router/
#         ./mavlink-routerd -e 127.0.0.1:14692 -e 127.0.0.1:14693 -t 0 0.0.0.0:14541
#         popd"
# end tell'


osascript -e 'tell app "Terminal"
    do script "
        pushd $PX4_DIR/mavsdk-server/
        ./mavsdk_server_macos -p 50051 udp://:14540
        #./mavsdk_server_macos -p 50051 udp://:14690
        popd"
end tell'

osascript -e 'tell app "Terminal"
    do script "
        pushd $PX4_DIR/mavsdk-server/ 
        ./mavsdk_server_macos -p 50052 udp://:14541
        #./mavsdk_server_macos -p 50052 udp://:14692
        popd"
end tell'

# osascript -e 'tell app "Terminal"
#     do script "
#         pushd $PX4_DIR/mavsdk-server/ 
#         ./mavsdk_server_macos -p 50053 udp://:14542
#         popd"
# end tell'

##### start jmavsim simulator #####
# ./Tools/sitl_multiple_run.sh 2
# ./Tools/jmavsim_run.sh -p 4560 -l
# ./Tools/jmavsim_run.sh -p 4561 -l

# start px4 shell 
# this not only allow me to set corresponding parameter, it also enables me for a faster testing cycle
# https://dev.px4.io/v1.10_noredirect/en/simulation/jmavsim.html

# below is one way to start px4 shell, another way is better using Microsoft AirSim
# problem with this is that it can only connect to one drone.
# we need to connect both instances with px4 shells.
# See Microsoft AirSim website for details

# osascript -e 'tell app "Terminal"
#     do script "\
#     pushd $PX4_DIR/PX4-Autopilot
#     make px4_sitl none
#     param set MIS_TAKEOFF_ALT 10.0 # takeoff altitude
#     popd"
# end tell'
# popd

# osascript -e 'tell app "Terminal"
#     do script "\
#     pushd $PX4_DIR/PX4-Autopilot/PX4Scripts
#     ./run_airsim_sitl.sh 0 # connect px4 shell to instance 0
#     param set MIS_TAKEOFF_ALT 10.0 # takeoff altitude
#     popd"
# end tell'
# popd

# osascript -e 'tell app "Terminal"
#     do script "\
#     pushd $PX4_DIR/PX4-Autopilot/PX4Scripts
#     ./run_airsim_sitl.sh 1 # connect px4 shell to instance 1
#     param set MIS_TAKEOFF_ALT 10.0 # takeoff altitude
#     popd"
# end tell'
# popd

# export PX4_HOME_LAT 40.4446815421205
# export PX4_HOME_LON=-79.94543858197448
# start simulator 1
osascript -e 'tell app "Terminal"
    do script "\
        export PX4_HOME_LAT=40.444907
        export PX4_HOME_LON=-79.947306

        pushd $PX4_DIR/PX4-Autopilot
        make px4_sitl_default
        ./Tools/sitl_multiple_run.sh 2
        ./Tools/jmavsim_run.sh -p 4560 -l
        popd"
end tell'
popd

# export PX4_HOME_LAT=40.4446815421205
# export PX4_HOME_LON=-79.94643858197448
# start simulator 2
osascript -e 'tell app "Terminal"
    do script "
        export PX4_HOME_LAT=40.444547673886355
        export PX4_HOME_LON=-79.94683384243788
        
        pushd $PX4_DIR/PX4-Autopilot
        ./Tools/jmavsim_run.sh -p 4561 -l"
        popd
end tell'
popd

# start simulator 3
#osascript -e 'tell app "Terminal"
#    do script "
#        export PX4_HOME_LAT=40.4446815421205
#        export PX4_HOME_LON=-79.94743858197448
#        
#        pushd $PX4_DIR/PX4-Autopilot
#        ./Tools/jmavsim_run.sh -p 4562 -l"
#        popd
#end tell'
#popd

sleep 5

# start px4 shell for corresponding instances
# connect to instance 0
osascript -e 'tell app "Terminal"
    do script "\
    pushd $PX4_DIR/PX4-Autopilot
    make px4_sitl none

    param set MIS_TAKEOFF_ALT 10.0 # takeoff altitude
    param set COM_DL_LOSS_T 300 # avoid data link timeouts
        param set COM_ARM_EKF_AB 0.01 # accelerometer bias
        param set EKF2_ABL_ACCLIM 200 # accelerometer bias
        param set EKF2_ABL_LIM 0.8 # accelerometer bias
        param set MIS_TAKEOFF_ALT 10.0 # takeoff altitude
        param set COM_RCL_EXCEPT 4 # disable joystick (prevent failsafe warning)
        param set SIM_BAT_MIN_PCT 30 # minimal percentage of battery (10)
        param set BAT_CRIT_THR 0.07 # critical threshold of battery (trigger return to landing)
        param set SIM_BAT_DRAIN 100 # battery drain interval
        param set NAV_RCL_ACT 0 # stop fail safe behavior due to having no RC
        # param set NAV_ACC_RAD 200 # useless
        # param set SYS_COMPANION 921600 # set the link baud rate: https://github.com/PX4/PX4-Autopilot/issues/6982
        param set COM_OBL_ACT 1 # control is lost, RC is lost, switch to hold mode

    popd"
end tell'
popd


# connect to instance 1
osascript -e 'tell app "Terminal"
    do script "\
    pushd $PX4_DIR/PX4-Autopilot/PX4Scripts
    ./run_airsim_sitl.sh 1 # connect px4 shell to instance 1
    param set MIS_TAKEOFF_ALT 10.0 # takeoff altitude
    param set COM_DL_LOSS_T 300 # avoid data link timeouts
        param set COM_ARM_EKF_AB 0.01 # accelerometer bias
        param set EKF2_ABL_ACCLIM 200 # accelerometer bias
        param set EKF2_ABL_LIM 0.8 # accelerometer bias
        param set MIS_TAKEOFF_ALT 10.0 # takeoff altitude
        param set COM_RCL_EXCEPT 4 # disable joystick (prevent failsafe warning)
        param set SIM_BAT_MIN_PCT 30 # minimal percentage of battery (10)
        param set BAT_CRIT_THR 0.07 # critical threshold of battery (trigger return to landing)
        param set SIM_BAT_DRAIN 100 # battery drain interval
        param set NAV_RCL_ACT 0 # stop fail safe behavior due to having no RC
        # param set NAV_ACC_RAD 200 # useless
        # param set SYS_COMPANION 921600 # set the link baud rate: https://github.com/PX4/PX4-Autopilot/issues/6982
        param set COM_OBL_ACT 1 # control is lost, RC is lost, switch to hold mode

    popd"
end tell'
popd

# Note that sometimes px4 shell does not start automatically. You may have to start it manually by using airsim script and 
# make px4_sitl none
pkill -15 -P $$
pkill -15 px4

pkill -15 -P $$
pkill -15 px4

pkill -15 -P $$
pkill -15 px4

# kill -9 $$

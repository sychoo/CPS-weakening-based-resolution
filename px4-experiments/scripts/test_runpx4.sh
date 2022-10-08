#!/bin/bash
# point $PX4_DIR environment variable to the PX4 installation directory

if [ -z $1 ]; then
        echo "Using Default Time"
		
else
        RUNPX4_TIME=$1
        echo "RUNPX4_TIME = $1"

fi

##### start mavsdk-server #####
# ./mavsdk_server_macos -p 50051 udp://:14540
osascript -e 'tell app "Terminal"
    do script "
        pushd $PX4_DIR/mavsdk-server/
        gtimeout $RUNPX4_TIME ./mavsdk_server_macos -p 50051 udp://:14540
        popd
		logout"
end tell'


##### start jmavsim simulator #####
# ./Tools/jmavsim_run.sh -p 4560 -l
osascript -e 'tell app "Terminal"
    do script "
        export PX4_HOME_LAT=40.4446815421205
        export PX4_HOME_LON=-79.94543858197448
	export PX4_SIM_SPEED_FACTOR=1 # https://docs.px4.io/main/en/simulation/

        pushd $PX4_DIR/PX4-Autopilot
        gtimeout $RUNPX4_TIME make px4_sitl jmavsim; logout

	param set COM_DL_LOSS_T 300 # avoid data link timeouts
 	param set MIS_TAKEOFF_ALT 10.0 # takeoff altitude
	param set COM_RCL_EXCEPT 4 # disable joystick (prevent failsafe warning)
	param set SIM_BAT_MIN_PCT 30 # minimal percentage of battery (10)
	param set BAT_CRIT_THR 0.07 # critical threshold of battery (trigger return to landing)
	param set SIM_BAT_DRAIN 100 # battery drain interval
	param set NAV_RCL_ACT 0 # stop fail safe behavior due to having no RC
	# param set NAV_ACC_RAD 200 # useless
	# param set SYS_COMPANION 921600 # set the link baud rate: https://github.com/PX4/PX4-Autopilot/issues/6982
	param set COM_OBL_ACT 1 # control is lost, RC is lost, switch to hold mode
        popd

		logout
		logout"
end tell'

# pushd PX4-Autopilot/
# make px4_sitl jmavsim
# popd

#pkill -15 -P $$
pkill -15 px4

#pkill -15 -P $$
pkill -15 px4

#kill -9 $$
#logout
#logout
exit

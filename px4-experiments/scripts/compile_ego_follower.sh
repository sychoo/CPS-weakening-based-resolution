#!/bin/bash

# compile the ego drone
osascript -e 'tell app "Terminal"
    do script "
		pushd $PX4_EXPERIMENT_DIR/
		mvn exec:java -Dexec.mainClass=\"edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.ego_follower.EgoDrone\"
        popd"
end tell'

# compile the follower drone scripts
osascript -e 'tell app "Terminal"
	do script "
	pushd $PX4_EXPERIMENT_DIR/
	mvn exec:java -Dexec.mainClass=\"edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.ego_follower.FollowerDrone\"
	popd"
end tell'

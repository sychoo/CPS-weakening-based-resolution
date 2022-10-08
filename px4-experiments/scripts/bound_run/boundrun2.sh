#!/bin/bash

echo "==============================="
echo "boundrun: Running experiment $1"
echo "==============================="
runpx4_multiple
sleep 25

pushd ../../
mvn exec:java -Dexec.mainClass="edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.BoundaryRunawayMain" -Dexec.args="experiments/config/bound_run/randomized_config/config_$1.ini"
popd

pkill -9 -f jmavsim_run.jar
pkill -9 -f mavsdk_server
pkill -9 -f px4
pkill -9 -f airsim
pkill -9 -f mavsdk_server_macos
pkill -9 -f px4_sitl

# pkill -15 -P $$

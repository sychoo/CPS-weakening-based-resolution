#!/bin/bash
# kill all the processes that contains the word "experiment"
pkill -9 -f experiment
pkill -9 -f boundrun
pkill -9 -f run_boundrun


pkill -9 -f jmavsim_run.jar
pkill -9 -f mavsdk_server
pkill -9 -f px4
pkill -9 -f airsim
pkill -9 -f mavsdk_server_macos

pkill -15 px4
pkill -15 px4

pkill -15 -P $$
pkill -15 -P $$
kill -9 $$

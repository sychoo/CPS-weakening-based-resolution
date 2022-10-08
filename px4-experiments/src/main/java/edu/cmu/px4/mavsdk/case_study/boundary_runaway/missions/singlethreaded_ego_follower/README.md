# Ego-Follower Drone Case Study

## Purpose
Ego drone is on a mission (climb-up clockwise). In the case when the ego drone is in close proximity of the follower drone, avoid it instead of following the original mission

The only goal of the follower drone is to follow the ego drone, and stop for a couple cycles when the ego drone is caught

## Reason
This directory is created out of the frustration that multi-threaded and multi-processed version has control loop starvation issues

## Downside
- unrealistic in that process will hang during the resolution process
- Maybe: pass the system argument into the thread for execution?
- Maybe: try a mix of async and blocking for experiment recording purpose
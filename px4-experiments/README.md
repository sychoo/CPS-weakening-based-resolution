# PX4 Experiments

## Boundary Runaway Case Study
### Execution
`mavsdk/case_study/boundary_runaway/main/BoundaryRunawayMain.java`

### Parameters to collect
**configuration**

**in progress**

**termination**

TODO: introduce the mission (reconnaissance, spiraling up, draw a diagram)

TODO: implementation, introduce the 3-thread setup (Ego drone, Follower drone, and experiment monitor)

### Parameter Settings
- To increase the times runaway enforcer is activated
  - increase 
    - `FOLLOWER_DRONE_MAX_SPEED`
    - `EGO_AVOID_DISTANCE`
  - decrease
    - `FOLLOWER_RECOVERY_CYCLE`
    - `FOLLOWER_CATCH_DISTANCE`

- To increase the times boundary enforcer is activated
  - increase
    - `MISSION_SQUARE_SIDE`
    - `MISSION_WAYPOINT_TOLRANCE`
  - decrease
    - `BOUND_SQUARE_SIDE`
  

## Organ Delivery Drone Case Study
### Parameters to collect
**configuration**
- start and destination location (double) (calculate cartesian coordinates of destination w.r.t to start location)
- refresh rate (int)
- drone speed

**in progress**
- resolver time (s)
- resolver time (% of cycle time)

**termination**
- battery level (float)
- landed state upon termination (Telemetry.LandedState)

## Special Mission
### Fly Spiral 

## Data Analysis Tools
### plot flight path (located in `experiments/plotFlightPath.py`)
  - `python3 plotFlightPath.py`
  - please supply with the `.csv` file following the following format
  `<latitude>, <longitutde>, <altitude>`

## Troubleshooting
### Communication Lost
- possibly ca
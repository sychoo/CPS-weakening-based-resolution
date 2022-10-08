package edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.mission;

import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import edu.cmu.px4.mavsdk.utils.Util;
import io.mavsdk.System;

import java.util.ArrayList;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.Params;
import io.mavsdk.telemetry.Telemetry;
import edu.cmu.px4.mavsdk.utils.Util;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.monitor.Monitor;

// this accepts two parameters
// 1. MISSION SQUARE SIDE
// 2. MISSION WAYPOINT TOLERANCE

// step 1. calculate all the coordinates around the center based on center coordinate
//         using offset function
// step 2. calculate drone distance to all these points
public class FlyWaypointsAroundCenter {
    public static ArrayList<ArrayList<Double>> waypoints = null;
    public static int currentWaypointIndex = 0;
    public static double currentAltitude = 0;
    public static double altitudeIncrement = 0;


    public static void initializeWaypoints(double center_lat, double center_lon, double mission_square_side) {
            // only called in the case case when the waypoint has not been initialized
            double distance = mission_square_side / 2;

            // top left/north west
            ArrayList<Double> waypoint1_lat_lon = DroneTelemetry.offsetLatLon(center_lat, center_lon, distance, -distance);

            // top right/north east
            ArrayList<Double> waypoint2_lat_lon = DroneTelemetry.offsetLatLon(center_lat, center_lon, distance, distance);

            // bottom right/south east
            ArrayList<Double> waypoint3_lat_lon = DroneTelemetry.offsetLatLon(center_lat, center_lon, -distance, distance);

            // bottom left/south west
            ArrayList<Double> waypoint4_lat_lon = DroneTelemetry.offsetLatLon(center_lat, center_lon, -distance, -distance);

            waypoints = new ArrayList<ArrayList<Double>>() {{
                add(waypoint1_lat_lon);
                add(waypoint2_lat_lon);
                add(waypoint3_lat_lon);
                add(waypoint4_lat_lon);
            }};

            Util.println("waypoints: " + waypoints);
    }

    public static ArrayList<Double> run(System drone) {

        // check whether if the waypoints has been initialized
        if (waypoints == null) {
            initializeWaypoints(Params.MISSION_CTR_LAT, Params.MISSION_CTR_LON, Params.MISSION_SQUARE_SIDE);
            altitudeIncrement = Params.MISSION_SQUARE_SIDE / (Params.MISSION_CYCLE * waypoints.size());
            currentAltitude = altitudeIncrement;
        }

        // hold position when the drone fly tops the bounding box
        // if (currentAltitude > Params.MISSION_SQUARE_SIDE) {
        //     DroneAction.holdPosition(drone, Params.REFRESH_RATE);
        // }

        // fly towards the designated destination
        ArrayList<Double> actionList = DroneAction.goToDestination3D(drone, Params.REFRESH_RATE, Params.EGO_DRONE_MAX_SPEED, waypoints.get(currentWaypointIndex % waypoints.size()).get(0), waypoints.get(currentWaypointIndex % waypoints.size()).get(1), currentAltitude);
        
        Util.println("ego drone -> current waypoint: [" + currentWaypointIndex % waypoints.size() + "]");

        // switch waypoints and current altitude if destination is deemed to be reached
        if (DroneTelemetry.distanceBetweenDroneAndDest(drone, waypoints.get(currentWaypointIndex % waypoints.size()).get(0), waypoints.get(currentWaypointIndex % waypoints.size()).get(1)) < Params.MISSION_WAYPOINT_TOLERANCE) {
            // increase the waypoint counter
            Monitor.mission_waypoint_counter++;

            Util.println("WAYPOINT [" + currentWaypointIndex % waypoints.size() + "] REACHED!");
            currentWaypointIndex += 1;
            currentAltitude += altitudeIncrement;
        }

        return actionList;
    }
}
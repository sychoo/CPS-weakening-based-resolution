package edu.cmu.px4.mavsdk.case_study.boundary_runaway.main;

import java.util.ArrayList;

import edu.cmu.px4.mavsdk.utils.ConfigParser;
import org.ini4j.Wini;

import edu.cmu.stl.encoder.floats.util.FileOperation;

import java.io.File;
import java.io.IOException;

public class Params {
    public static double EGO_DRONE_START_LOCATION_NORTH = 0.0;
    public static double EGO_DRONE_START_LOCATION_EAST = 0.0;

    public static String FLIGHT_PATH_FILE_DIR = "experiments/results/bound_run/flight_path";
    public static String FLIGHT_PATH_FILE = "experiments/results/bound_run/flight_path/flight_path";

    public static String RESULT_FILE_DIR = "experiments/results/bound_run/result";
    public static String RESULT_FILE = "experiments/results/bound_run/result/result.csv";

    public static String ROBUSTNESS_FILE_DIR = "experiments/results/bound_run/result/robustness/";

    /***************************************/
    /***** G[1,1](distance2bound > 20) *****/
    /***************************************/

    // this is for the robustness analysis at the end of the experiment cycle
    // public static String STL_BOUNDARY_ORIGINAL_REQUIREMENT_FILE_PATH =
    // "src/main/java/edu/cmu/px4/mavsdk/case_study/boundary_runaway/main/model/bound_original.stl";
    // public static String STL_RUNAWAY_ORIGINAL_REQUIREMENT_FILE_PATH =
    // "src/main/java/edu/cmu/px4/mavsdk/case_study/boundary_runaway/main/model/run_original.stl";
    // public static String MAIN_STL_FILE_FOR_RESOLVER =
    // "src/main/java/edu/cmu/px4/mavsdk/case_study/boundary_runaway/main/model/runbound.stl";
    // public static int STL_BOUNDARY_REQUIREMENT_SIGNAL_LENGTH = 2;
    // public static int STL_RUNAWAY_REQUIREMENT_SIGNAL_LENGTH = 2;

    /*********************************************************************************************************/
    /*****
     * G[0,1]((distance2follower <= 10) -> (F[0,1](<<distance2follower >
     * 10>>(delta_runaway, 8.0))))
     *****/
    /*********************************************************************************************************/

    public static String STL_BOUNDARY_ORIGINAL_REQUIREMENT_FILE_PATH = "src/main/java/edu/cmu/px4/mavsdk/case_study/boundary_runaway/main/model/bound_eventually_original.stl";
    public static String STL_RUNAWAY_ORIGINAL_REQUIREMENT_FILE_PATH = "src/main/java/edu/cmu/px4/mavsdk/case_study/boundary_runaway/main/model/run_eventually_original.stl";
    public static String MAIN_STL_FILE_FOR_RESOLVER = "src/main/java/edu/cmu/px4/mavsdk/case_study/boundary_runaway/main/model/runbound_eventually.stl";
    public static int STL_BOUNDARY_REQUIREMENT_SIGNAL_LENGTH = 3; // manually observe the signal length based on the STL
    public static int STL_RUNAWAY_REQUIREMENT_SIGNAL_LENGTH = 3; // manually observe the signal length based on the STL

    // using the requirement weakening resolver to resolve the conflict
    // with a secondary priority-based resolver using runaway enforcer as the
    // prioritized feature
    public static String RESOLVER = "PRIORITY - RUNAWAY";
    // public static String RESOLVER = "PRIORITY - BOUNDARY";
    // public static String RESOLVER = "WEAKENING - PRIORITY - RUNAWAY";
    // public static String RESOLVER = "WEAKENING - PRIORITY - BOUNDARY";

    public static int REFRESH_RATE = 500; // milliseconds
    // public static int REFRESH_RATE = 1000; // milliseconds
    public static double REFRESH_RATE_SECOND = REFRESH_RATE / 1000.0;

    public static int MAX_EGO_DRONE_CYCLE = 200;

    // note that ego drone speed must be < follower drone speed for follower drone
    // to catch the ego drone
    // initial speed (to reach mission center)
    public static double EGO_DRONE_CRUISING_SPEED = 15.00; // m/s

    public static double EGO_DRONE_MAX_SPEED = 3.0; // m/s
    public static double FOLLOWER_DRONE_MAX_SPEED = 5.0; // m/s

    /****************************************************************/
    /*****
     * Runaway Enforcer/Main Mission for Follower Drone: following Ego Drone
     *****/
    /****************************************************************/

    // the distance within which the ego drone is deemed to be caught by the
    // follower drone
    // changed from originally 5 --> 3.0
    public static double FOLLOWER_CATCH_DISTANCE = 3.0; // m

    // determines how many cycles that the follower drone will need to take to
    // continue catching the ego drone
    public static double FOLLOWER_RECOVERY_CYCLE = 5; // times

    // the distance within which the ego drone will proactively avoid the follower
    // drone
    public static double EGO_AVOID_DISTANCE = 10.0; // m

    // the speed which ego drone manuver to avoid the follower drone
    public static double EGO_AVOID_SPEED = 9.0; // m/s

    /**************************************/
    /***** Main Mission for Ego Drone *****/
    /**************************************/

    // parameters for the fly waypoint mission
    // the side length the of the squared formed by the waypoints
    public static double MISSION_SQUARE_SIDE = 50.0; // m

    // the distance within which the waypoint is deemed to be reached
    public static double MISSION_WAYPOINT_TOLERANCE = 10.0; // m

    // how many time you want to drone to fly around each waypoint will not stop
    // afterwards
    public static int MISSION_CYCLE = 2;

    // Center of the boundary and survillance area
    public static double MISSION_CTR_LAT = 40.444907; // boundary center latitude
    public static double MISSION_CTR_LON = -79.947306; // boundary center longitude

    /*****************************/
    /***** Boundary Enforcer *****/
    /*****************************/
    public static double BOUND_CTR_LAT = MISSION_CTR_LAT;
    public static double BOUND_CTR_LON = MISSION_CTR_LON;

    // the side of the boundary, in meters
    public static double BOUND_SQUARE_SIDE = 80.0; // m, should be aligned relatively close to the MISSION_SQUARE_SIDE

    // the speed in which the ego drone pull towards the center
    public static double EGO_BOUND_ENFORCER_SPEED = 9.0; // m/s

    // the distance in which the boundary enforcer is triggered
    // align with STL requirement
    public static double EGO_BOUND_DISTANCE_LIMIT = 20.0; // m/s

    // destination: saint paul cathedral
    public static double DEST_LAT = 40.44757;
    public static double DEST_LON = -79.95044;

    // obtain the parameter setting from the configuration file, and assign them to
    // the static variables in Params.java class
    public static void assignParamFromFile(String paramFile) throws IOException {
        // have to manually parse the param file
        // ConfigParser.
        Wini ini = null;
        try {
            ini = new Wini(new File(paramFile));
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("[config] Reading parameters from file: " + paramFile);

        // iterate through the keyset of runbound section
        for (String key : ini.get("runbound").keySet()) {
            System.out.println(key + " = " + ini.get("runbound", key));

            switch (key) {
                case "RESOLVER":
                case "resolver":
                    RESOLVER = ini.get("runbound", key);
                    break;
                case "REFRESH_RATE":
                case "refresh_rate":
                    REFRESH_RATE = Integer.parseInt(ini.get("runbound", key));
                    REFRESH_RATE_SECOND = REFRESH_RATE / 1000.0;
                    break;
                case "MAX_EGO_DRONE_CYCLE":
                case "max_ego_drone_cycle":
                    MAX_EGO_DRONE_CYCLE = Integer.parseInt(ini.get("runbound", key));
                    break;
                case "EGO_DRONE_CRUISING_SPEED":
                case "ego_drone_cruising_speed":
                    EGO_DRONE_CRUISING_SPEED = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "EGO_DRONE_MAX_SPEED":
                case "ego_drone_max_speed":
                    EGO_DRONE_MAX_SPEED = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "FOLLOWER_DRONE_MAX_SPEED":
                case "follower_drone_max_speed":
                    FOLLOWER_DRONE_MAX_SPEED = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "FOLLOWER_CATCH_DISTANCE":
                case "follower_catch_distance":
                    FOLLOWER_CATCH_DISTANCE = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "FOLLOWER_RECOVERY_CYCLE":
                case "follower_recovery_cycle":
                    FOLLOWER_RECOVERY_CYCLE = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "EGO_AVOID_DISTANCE":
                case "ego_avoid_distance":
                    EGO_AVOID_DISTANCE = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "EGO_AVOID_SPEED":
                case "ego_avoid_speed":
                    EGO_AVOID_SPEED = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "MISSION_SQUARE_SIDE":
                case "mission_square_side":
                    MISSION_SQUARE_SIDE = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "MISSION_WAYPOINT_TOLERANCE":
                case "mission_waypoint_tolerance":
                    MISSION_WAYPOINT_TOLERANCE = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "MISSION_CYCLE":
                case "mission_cycle":
                    MISSION_CYCLE = Integer.parseInt(ini.get("runbound", key));
                    break;
                case "MISSION_CTR_LAT":
                case "mission_ctr_lat":
                    MISSION_CTR_LAT = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "MISSION_CTR_LON":
                case "mission_ctr_lon":
                    MISSION_CTR_LON = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "BOUND_CTR_LAT":
                case "bound_ctr_lat":
                    BOUND_CTR_LAT = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "BOUND_CTR_LON":
                case "bound_ctr_lon":
                    BOUND_CTR_LON = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "BOUND_SQUARE_SIDE":
                case "bound_square_side":
                    BOUND_SQUARE_SIDE = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "EGO_BOUND_ENFORCER_SPEED":
                case "ego_bound_enforcer_speed":
                    EGO_BOUND_ENFORCER_SPEED = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "EGO_BOUND_DISTANCE_LIMIT":
                case "ego_bound_distance_limit":
                    EGO_BOUND_DISTANCE_LIMIT = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "DEST_LAT":
                case "dest_lat":
                    DEST_LAT = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "DEST_LON":
                case "dest_lon":
                    DEST_LON = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "EGO_DRONE_START_LOCATION_NORTH":
                case "ego_drone_start_location_north":
                    EGO_DRONE_START_LOCATION_NORTH = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "EGO_DRONE_START_LOCATION_EAST":
                case "ego_drone_start_location_east":
                    EGO_DRONE_START_LOCATION_EAST = Double.parseDouble(ini.get("runbound", key));
                    break;
                case "STL_BOUNDARY_ORIGINAL_REQUIREMENT_FILE_PATH":
                case "stl_boundary_original_requirement_file_path":
                    STL_BOUNDARY_ORIGINAL_REQUIREMENT_FILE_PATH = ini.get("runbound", key);
                    break;
                case "STL_RUNAWAY_ORIGINAL_REQUIREMENT_FILE_PATH":
                case "stl_runaway_original_requirement_file_path":
                    STL_RUNAWAY_ORIGINAL_REQUIREMENT_FILE_PATH = ini.get("runbound", key);
                    break;
                case "MAIN_STL_FILE_FOR_RESOLVER":
                case "main_stl_file_for_resolver":
                    MAIN_STL_FILE_FOR_RESOLVER = ini.get("runbound", key);
                    break;
                case "STL_BOUNDARY_REQUIREMENT_SIGNAL_LENGTH":
                case "stl_boundary_requirement_signal_length":
                    STL_BOUNDARY_REQUIREMENT_SIGNAL_LENGTH = Integer.parseInt(ini.get("runbound", key));
                    break;
                case "STL_RUNAWAY_REQUIREMENT_SIGNAL_LENGTH":
                case "stl_runaway_requirement_signal_length":
                    STL_RUNAWAY_REQUIREMENT_SIGNAL_LENGTH = Integer.parseInt(ini.get("runbound", key));
                    break;
                default:    // default case
                    System.out.println("Unknown key: " + key);
                    break;
                // https://stackoverflow.com/questions/1602270/ini4j-how-to-get-all-the-key-names-in-a-setting
            }
        }
    }

    // https://stackoverflow.com/questions/4466743/getting-all-static-variables-in-a-class-into-array-list
    public String declareMiniZincParamsExclude() {
        // determining what parameters to exclude in the MiniZinc declaration
        ArrayList<String> excludedParams = new ArrayList<String>();
        excludedParams.add("EXPERIMENT_FILE_DIR");
        excludedParams.add("EXPERIMENT_FILE");

        StringBuilder params = new StringBuilder();
        for (java.lang.reflect.Field field : this.getClass().getDeclaredFields()) {
            // skip the excluded parameters
            if (!excludedParams.contains(field.getName())) {
                try {
                    params.append("float: " + field.getName() + " = " + field.get(this) + ";\n");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return params.toString();
    }

    public String declareMiniZincParamsInclude() {
        // determining what parameters to exclude in the MiniZinc declaration
        ArrayList<String> includedParams = new ArrayList<String>();
        includedParams.add("EGO_AVOID_SPEED");
        includedParams.add("FOLLOWER_DRONE_MAX_SPEED");
        includedParams.add("REFRESH_RATE_SECOND");

        StringBuilder params = new StringBuilder();
        for (java.lang.reflect.Field field : this.getClass().getDeclaredFields()) {
            // skip the excluded parameters
            if (includedParams.contains(field.getName())) {
                try {
                    params.append("float: " + field.getName() + " = " + field.get(this) + ";\n");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return params.toString();
    }

    public String declareMiniZincParams() {
        return declareMiniZincParamsInclude();
    }
}

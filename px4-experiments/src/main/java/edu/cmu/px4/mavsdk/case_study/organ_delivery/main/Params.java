  
package edu.cmu.px4.mavsdk.case_study.organ_delivery.main;

import edu.cmu.px4.mavsdk.utils.DroneTelemetry;

import java.util.ArrayList;

import edu.cmu.px4.mavsdk.utils.ConfigParser;
import org.ini4j.Wini;

import edu.cmu.stl.encoder.floats.util.FileOperation;

import java.io.File;
import java.io.IOException;

// Note that every time Params is used, constructor must be called. 
public class Params {

    /**************************************/
    /***** CAN BE MODIFIED EXTERNALLY *****/
    /**************************************/



    // measure the distance from start to the destination
    // cartesian coordinates of the destination
    // to be calculated using the offset function --> (lat, lon pairs)
    // public static double DESTINATION_FROM_ORIGIN_NORTH = 170.0; // in meters
    public static double DESTINATION_FROM_ORIGIN_NORTH = 150.0; // in meters

    public static double DESTINATION_FROM_ORIGIN_EAST = 0.0; // in meters

    public static double SAFE_LANDING_BATTERY_THRESHOLD = 40.0; // in percentage
    public static double CRASH_LANDING_BATTERY_THRESHOLD = 0; // in percentage, CRASH

    public static double MISSION_TIME_OVERALL = 40.0; // in seconds 

    // the speed of the drone (static speed) is calculated based on the MISSION_TIME_OVERALL
    public static double SPEED = calculateDistance(DESTINATION_FROM_ORIGIN_NORTH, DESTINATION_FROM_ORIGIN_EAST)/ MISSION_TIME_OVERALL; // m/s

    // public static String RESOLVER = "WEAKENING - PRIORITY - SAFELANDING";
    // public static String RESOLVER = "WEAKENING - PRIORITY - DELIVERYPLANNING";
    // public static String RESOLVER = "PRIORITY - SAFELANDING";
    public static String RESOLVER = "PRIORITY - DELIVERYPLANNING";

    public static String MAIN_STL_FILE_FOR_RESOLVER = "src/main/java/edu/cmu/px4/mavsdk/case_study/organ_delivery/main/updated_model/dpsl.stl";
    public static String DELIVERYPLANNING_STL_ORIGINAL_REQUIREMENT_FILE_PATH = "src/main/java/edu/cmu/px4/mavsdk/case_study/organ_delivery/main/updated_model/dp_original.stl";
    public static String SAFELANDING_STL_ORIGINAL_REQUIREMENT_FILE_PATH = "src/main/java/edu/cmu/px4/mavsdk/case_study/organ_delivery/main/updated_model/sl_original.stl";
    public static int DELIVERYPLANNING_STL_ORIGINAL_REQUIREMENT_SIGNAL_LENGTH = 2;
    public static int SAFELANDING_STL_ORIGINAL_REQUIREMENT_SIGNAL_LENGTH = 3;


    public static String MAIN_ENVIRONMENTAL_MODEL_FILE_FOR_RESOLVER = "src/main/java/edu/cmu/px4/mavsdk/case_study/organ_delivery/main/updated_model/main_model.mzn";
    public static double BATTERY_DRAIN_RATE = 2.0; //PX4_SIM_BAT_FULL_CAPACITY / PX4_SIM_BAT_DRAIN_INTERVAL; // percentage per second

    public static String RESULT_FILE_DIR = "experiments/results/organ_delivery/result";
    public static String RESULT_FILE = "experiments/results/organ_delivery/result/organ_result.csv";

    public static String ROBUSTNESS_FILE_DIR = "experiments/results/organ_delivery/result/robustness/";



    /*************************/
    /***** DO NOT MODIFY *****/
    /*************************/

    public static int REFRESH_RATE_MILLISECOND = 500; // milliseconds
    public static double REFRESH_RATE_SECOND = REFRESH_RATE_MILLISECOND / 1000.0; // seconds 

    public static double LANDING_POINT_TOLERANCE = 10; // in meters

    // this should align with the simulator
    // this specific starting location is tepper school of business
    public static double START_LOCATION_LAT = 40.4446815421205;
    public static double START_LOCATION_LON = -79.94543858197448;

    public static double DESTINATION_LOCATION_LAT = 0.0;
    public static double DESTINATION_LOCATION_LON = 0.0;
 
    // PX4 shell parameters
    public static double PX4_SIM_BAT_FULL_CAPACITY = 100;
    public static double PX4_SIM_BAT_DRAIN_INTERVAL = 50; // seconds until the battery is drained completely
    public static double PX4_SIM_BAT_MIN_PCT = 5.0; //  minimal percentage of battery (10), below which simulator stops decrementing 
    
    public static double LANDING_TIME_REQUIRED = 10.0; // time required for landing

    // constructor
    public Params() {
        ArrayList<Double> destinationLoc = calculateDestinationLocation(START_LOCATION_LAT, START_LOCATION_LON, DESTINATION_FROM_ORIGIN_NORTH, DESTINATION_FROM_ORIGIN_EAST);
        DESTINATION_LOCATION_LAT = destinationLoc.get(0);
        DESTINATION_LOCATION_LON = destinationLoc.get(1);
    }

    public ArrayList<Double> calculateDestinationLocation(double startLat, double startLon, double offsetNorth, double offsetEast) {
        return DroneTelemetry.offsetLatLon(startLat, startLon, offsetNorth, offsetEast);
    }

    // simple geometry to calculate the distance between two points
    public static double calculateDistance(double destination_from_origin_north, double destination_from_origin_east) {
        // calculate the distance between the destination and the start location
        double distance = Math.sqrt(destination_from_origin_north * destination_from_origin_north + destination_from_origin_east * destination_from_origin_east);
        return distance;
    }


    public String declareMiniZincParamsInclude() {
        // determining what parameters to exclude in the MiniZinc declaration
        ArrayList<String> includedParams = new ArrayList<String>();
        includedParams.add("BATTERY_DRAIN_RATE");
        includedParams.add("SPEED");
        includedParams.add("REFRESH_RATE_SECOND");
        includedParams.add("LANDING_TIME_REQUIRED");

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
            return;
        }

        System.out.println("[config] Reading parameters from file: " + paramFile);

        // iterate through the keyset of runbound section
        for (String key : ini.get("organ").keySet()) {
            System.out.println(key + " = " + ini.get("organ", key));

            switch (key) {
                case "SPEED":
                case "speed":
                    SPEED = Double.parseDouble(ini.get("organ", key));
                    break;

                case "DESTINATION_FROM_ORIGIN_NORTH":
                case "destination_from_origin_north":
                    DESTINATION_FROM_ORIGIN_NORTH = Double.parseDouble(ini.get("organ", key));
                    break;
                
                case "DESTINATION_FROM_ORIGIN_EAST":
                case "destination_from_origin_east":
                    DESTINATION_FROM_ORIGIN_EAST = Double.parseDouble(ini.get("organ", key));
                    break;

                case "SAFE_LANDING_BATTERY_THRESHOLD":
                case "safe_landing_battery_threshold":
                    SAFE_LANDING_BATTERY_THRESHOLD = Double.parseDouble(ini.get("organ", key));
                    break;
                
                case "CRASH_LANDING_BATTERY_THRESHOLD":
                case "crash_landing_battery_threshold":
                    CRASH_LANDING_BATTERY_THRESHOLD = Double.parseDouble(ini.get("organ", key));
                    break;

                case "RESOLVER":
                case "resolver":
                    RESOLVER = ini.get("organ", key);
                    break;

                case "BATTERY_DRAIN_RATE":
                case "battery_drain_rate":
                    BATTERY_DRAIN_RATE = Double.parseDouble(ini.get("organ", key));
                    break;

                case "MISSION_TIME_OVERALL":
                case "mission_time_overall":
                    MISSION_TIME_OVERALL = Double.parseDouble(ini.get("organ", key));
                    break;

                default:    // default case
                    System.out.println("Unknown key: " + key);
                    break;
                // https://stackoverflow.com/questions/1602270/ini4j-how-to-get-all-the-key-names-in-a-setting
            }
        }
    }
}


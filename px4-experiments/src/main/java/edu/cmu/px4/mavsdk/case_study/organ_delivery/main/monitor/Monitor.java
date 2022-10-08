package edu.cmu.px4.mavsdk.case_study.organ_delivery.main.monitor;

import io.mavsdk.telemetry.Telemetry;
import io.reactivex.Flowable;
import io.mavsdk.System;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import edu.cmu.px4.mavsdk.case_study.organ_delivery.main.Params;
import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.Util;
import java.util.ArrayList;
import java.util.Collections;
import edu.cmu.stl.encoder.floats.util.FileOperation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import edu.cmu.px4.mavsdk.case_study.organ_delivery.main.monitor.RobustnessCalculator;
import java.io.IOException;

public class Monitor {
    public static String parameter_configuration_file_name = "";
    public static double battery_level_when_started_landing = 0.0;
    public static double battery_level_when_landed = 0.0;
    public static double distance_to_destination_when_landed = 0.0;
    public static boolean drone_has_crashed = false;

    // globally parameter collection
    public static HashMap<String, String> parameterMap = new HashMap<String, String>();

    // stores the how many cycles were executed in the drone controller
    public static int cycle_counter = 0;

    public static int successful_resolution = 0;
    public static int failed_resolution = 0;
    public static int total_resolution = 0;
    public static ArrayList<Double> cycleTimeList = new ArrayList<Double>();
    public static ArrayList<Double> resolutionTimeList = new ArrayList<Double>();

    // store the signal varaibles 
    public static ArrayList<Double> batteryLevelList = new ArrayList<Double>();
    public static ArrayList<Double> isLandingList = new ArrayList<Double>();
    public static ArrayList<Double> distanceToDestinationList = new ArrayList<Double>();
    public static ArrayList<Double> missionTimeLeftList = new ArrayList<Double>();
    public static ArrayList<Double> speedList = new ArrayList<Double>();


    // store the activated list
    // delivery planner should always be activated 
    public static ArrayList<Double> deliveryPlannerActivatedList = new ArrayList<Double>();
    public static ArrayList<Double> safeLandingActivatedList = new ArrayList<Double>();

    // if it is 2, both enforcers are activated,
    // if it is 1, one of the enforcers is activated
    // if it is 0, none of the enforcers is activated / all are deactivated
    public static ArrayList<Double> numberOfEnforcerActivatedList = new ArrayList<Double>();


    public static void addSignalVariables(double batteryLevel, double isLanding, double distanceToDestination, double missionTimeLeft, double speed) {
        batteryLevelList.add(batteryLevel);
        isLandingList.add(isLanding);
        distanceToDestinationList.add(distanceToDestination);
        missionTimeLeftList.add(missionTimeLeft);
        speedList.add(speed);
    }

    /********************************/
    /***** Statistical Analysis *****/
    /********************************/

    public static double findMin(ArrayList<Double> myList) {
        if (myList.size() == 0) {
            return 99999; // invalid value
        }

        double minimum = myList.get(0);
        for (int i = 1; i < myList.size(); i++) {
            if (minimum > myList.get(i))
                minimum = myList.get(i);
        }
        return minimum;
    }

    public static double findMax(ArrayList<Double> myList) {
        if (myList.size() == 0) {
            return -99999; // invalid value
        }

        double maximum = myList.get(0);
        for (int i = 1; i < myList.size(); i++) {
            if (maximum < myList.get(i))
                maximum = myList.get(i);
        }
        return maximum;
    }

    public static double findSum(ArrayList<Double> myList) {
        double sum = 0;
        for (double d : myList) {
            sum += d;
        }
        return sum;
    }

    public static double findAverage(ArrayList<Double> myList) {
        double sum = findSum(myList);
        return sum / myList.size();
    }
    

    // assumption: both arrays are of the same length
    public static ArrayList<ArrayList<Double>> findFeatureInteractionArray(ArrayList<Double> distanceToBoundaryRobustnessList, ArrayList<Double> distanceToFollowerRobustnessList) {
        ArrayList<ArrayList<Double>> featureArray = new ArrayList<ArrayList<Double>>();

        int length1 = distanceToBoundaryRobustnessList.size();
        int length2 = distanceToFollowerRobustnessList.size();
        int looplength = length1;
        if (length1 != length2) {
            // Util.println("Error: two arrays are not of the same length");
            // return null;

            // pick the smaller length as the loop length
            // essentially truncate the ending of the longer array
            looplength = Math.min(length1, length2);
        }

        ArrayList<Double> distanceToBoundaryFIArray = new ArrayList<Double>();
        ArrayList<Double> distanceToFollowerFIArray = new ArrayList<Double>();

        for (int i = 0; i < looplength; i++) {
            // when feature interaction occurs, append to list
            if (distanceToBoundaryRobustnessList.get(i) < 0 && distanceToFollowerRobustnessList.get(i) < 0) {
                distanceToBoundaryFIArray.add(distanceToBoundaryRobustnessList.get(i));
                distanceToFollowerFIArray.add(distanceToFollowerRobustnessList.get(i));
            } 
        }

        featureArray.add(distanceToBoundaryFIArray);
        featureArray.add(distanceToFollowerFIArray);
        return featureArray;
    }


     // at start state
     public static void start() {
        // start time
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy_HH-mm-ss");
        String formattedDate = myDateObj.format(myFormatObj);
        parameterMap.put("start_time", formattedDate);
    }

    // executed during the resolution step
    public static void resolution(double resolutionDuration, double cycleDuration, boolean result) {
        resolutionTimeList.add(resolutionDuration);
        cycleTimeList.add(cycleDuration);
        total_resolution++;

        if (result) {
            successful_resolution++;
        } else {
            failed_resolution++;
        }
    }


    public static void logResultData() {
        if (FileOperation.isFileEmpty(Params.RESULT_FILE)) {
            FileOperation.writeToFile(Params.RESULT_FILE_DIR, Params.RESULT_FILE, genCSVHeader());
        }

        FileOperation.appendToFile(Params.RESULT_FILE, genExperimentOutput());
    }

    public static void calculateResultData() {
        parameterMap.put("avg_resolution_time", String.valueOf(findAverage(resolutionTimeList)));
        parameterMap.put("avg_cycle_time", String.valueOf(findAverage(cycleTimeList)));
        parameterMap.put("resolution_time_percentage", String.valueOf((findAverage(resolutionTimeList) / findAverage(cycleTimeList)) * 100.0));

        parameterMap.put("total_resolution_count", String.valueOf(total_resolution));
        parameterMap.put("resolution_success_count", String.valueOf(successful_resolution));
        parameterMap.put("resolution_failure_count", String.valueOf(failed_resolution));
        parameterMap.put("resolution_success_percentage", String.valueOf(total_resolution == 0 ? 0 : (successful_resolution / (double) total_resolution) * 100.0));

        parameterMap.put("experiment_item", String.valueOf(Params.RESOLVER));

        String input = String.valueOf(parameter_configuration_file_name);

        String[] split = input.split("_");
        String[] suffix = split[split.length - 1].split("\\.");

        String parameter_configuration_number = suffix[0];

        // get the number of the parameter configuration file
        parameterMap.put("parameter_configuration_number", parameter_configuration_number);


        parameterMap.put("parameter_configuration_file_name", String.valueOf(parameter_configuration_file_name));

        parameterMap.put("battery_level_when_started_landing", String.valueOf(battery_level_when_started_landing));
        parameterMap.put("battery_level_when_landed", String.valueOf(battery_level_when_landed)); // min battery level
        parameterMap.put("distance_to_destination_when_landed", String.valueOf(distance_to_destination_when_landed));
        parameterMap.put("drone_has_crashed", String.valueOf(drone_has_crashed));
        parameterMap.put("drone_has_reached_destination", String.valueOf(distance_to_destination_when_landed <= Params.LANDING_POINT_TOLERANCE));
        parameterMap.put("safe_landing_feature_satisfied", String.valueOf(battery_level_when_started_landing >= Params.SAFE_LANDING_BATTERY_THRESHOLD));

        /**********************************/
        /***** Robustness Calculation *****/
        /**********************************/

        // overall robustness
        ArrayList<Double> deliveryPlanningRobustnessList = RobustnessCalculator.deliveryPlanningRobustnessList();//batteryLevelList, isLandingList, distanceToDestinationList, speedList, missionTimeLeftList);
        double minDeliveryPlanningRobustness = findMin(deliveryPlanningRobustnessList);
        double maxDeliveryPlanningRobustness = findMax(deliveryPlanningRobustnessList);
        double averageDeliveryPlanningRobustness = findAverage(deliveryPlanningRobustnessList);
        double culmulativeDeliveryPlanningRobustness = findSum(deliveryPlanningRobustnessList);

        ArrayList<Double> safeLandingRobustnessList = RobustnessCalculator.safeLandingRobustnessList();//batteryLevelList, isLandingList, distanceToDestinationList);
        double minSafeLandingRobustness = findMin(safeLandingRobustnessList);
        double maxSafeLandingRobustness = findMax(safeLandingRobustnessList);
        double averageSafeLandingRobustness = findAverage(safeLandingRobustnessList);
        double culmulativeSafeLandingRobustness = findSum(safeLandingRobustnessList);

        // overall robustness
        parameterMap.put("delivery_planning_min_robustness_overall", String.valueOf(minDeliveryPlanningRobustness));
        parameterMap.put("delivery_planning_max_robustness_overall", String.valueOf(maxDeliveryPlanningRobustness));
        parameterMap.put("delivery_planning_average_robustness_overall", String.valueOf(averageDeliveryPlanningRobustness));
        parameterMap.put("delivery_planning_cumulative_robustness_overall", String.valueOf(culmulativeDeliveryPlanningRobustness));

        parameterMap.put("safe_landing_min_robustness_overall", String.valueOf(minSafeLandingRobustness));
        parameterMap.put("safe_landing_max_robustness_overall", String.valueOf(maxSafeLandingRobustness));
        parameterMap.put("safe_landing_average_robustness_overall", String.valueOf(averageSafeLandingRobustness));
        parameterMap.put("safe_landing_cumulative_robustness_overall", String.valueOf(culmulativeSafeLandingRobustness));

        // feature interaction robustness - when both requirements are negative (not satisfied)
        // find when both of the robustness are negative
        // given two array of the entire time horizon, find two new array of negative robustness values
        ArrayList<ArrayList<Double>> featureInteractionRobustnessList = findFeatureInteractionArray(deliveryPlanningRobustnessList, safeLandingRobustnessList);
        ArrayList<Double> deliveryPlanningRobustnessFeatureInteractionList = featureInteractionRobustnessList.get(0);
        ArrayList<Double> safeLandingRobustnessFeatureInteractionList = featureInteractionRobustnessList.get(1);

        double minDeliveryPlanningRobustnessFI = findMin(deliveryPlanningRobustnessFeatureInteractionList);
        double maxDeliveryPlanningRobustnessFI = findMax(deliveryPlanningRobustnessFeatureInteractionList);
        double averageDeliveryPlanningRobustnessFI = findAverage(deliveryPlanningRobustnessFeatureInteractionList);
        double culmulativeDeliveryPlanningRobustnessFI = findSum(deliveryPlanningRobustnessFeatureInteractionList);
        
        double minSafeLandingRobustnessFI = findMin(safeLandingRobustnessFeatureInteractionList);
        double maxSafeLandingRobustnessFI = findMax(safeLandingRobustnessFeatureInteractionList);
        double averageSafeLandingRobustnessFI = findAverage(safeLandingRobustnessFeatureInteractionList);
        double culmulativeSafeLandingRobustnessFI = findSum(safeLandingRobustnessFeatureInteractionList);
        

        // feature interaction robustness - when both requirements are negative (not satisfied)
        // feature interaction robustness
        parameterMap.put("delivery_planning_min_robustness_feature_interaction", String.valueOf(minDeliveryPlanningRobustnessFI));
        parameterMap.put("delivery_planning_max_robustness_feature_interaction", String.valueOf(maxDeliveryPlanningRobustnessFI));
        parameterMap.put("delivery_planning_average_robustness_feature_interaction", String.valueOf(averageDeliveryPlanningRobustnessFI));
        parameterMap.put("delivery_planning_cumulative_robustness_feature_interaction", String.valueOf(culmulativeDeliveryPlanningRobustnessFI));

        parameterMap.put("safe_landing_min_robustness_feature_interaction", String.valueOf(minSafeLandingRobustnessFI));
        parameterMap.put("safe_landing_max_robustness_feature_interaction", String.valueOf(maxSafeLandingRobustnessFI));
        parameterMap.put("safe_landing_average_robustness_feature_interaction", String.valueOf(averageSafeLandingRobustnessFI));
        parameterMap.put("safe_landing_cumulative_robustness_feature_interaction", String.valueOf(culmulativeSafeLandingRobustnessFI));

        // parameters
        parameterMap.put("SPEED", String.valueOf(Params.SPEED));
        parameterMap.put("REFRESH_RATE_SECOND", String.valueOf(Params.REFRESH_RATE_SECOND));
        parameterMap.put("DESTINATION_FROM_ORIGIN_NORTH", String.valueOf(Params.DESTINATION_FROM_ORIGIN_NORTH));
        parameterMap.put("DESTINATION_FROM_ORIGIN_EAST", String.valueOf(Params.DESTINATION_FROM_ORIGIN_EAST));
        parameterMap.put("SAFE_LANDING_BATTERY_THRESHOLD", String.valueOf(Params.SAFE_LANDING_BATTERY_THRESHOLD));
        parameterMap.put("CRASH_LANDING_BATTERY_THRESHOLD", String.valueOf(Params.CRASH_LANDING_BATTERY_THRESHOLD));
        parameterMap.put("RESOLVER", String.valueOf(Params.RESOLVER));
        parameterMap.put("MAIN_STL_FILE_FOR_RESOLVER", String.valueOf(Params.MAIN_STL_FILE_FOR_RESOLVER));
        parameterMap.put("MAIN_ENVIRONMENTAL_MODEL_FILE_FOR_RESOLVER", String.valueOf(Params.MAIN_ENVIRONMENTAL_MODEL_FILE_FOR_RESOLVER));
        parameterMap.put("BATTERY_DRAIN_RATE", String.valueOf(Params.BATTERY_DRAIN_RATE));
        parameterMap.put("LANDING_POINT_TOLERANCE", String.valueOf(Params.LANDING_POINT_TOLERANCE));
        parameterMap.put("START_LOCATION_LAT", String.valueOf(Params.START_LOCATION_LAT));
        parameterMap.put("START_LOCATION_LON", String.valueOf(Params.START_LOCATION_LON));


        // find when both of the robustness are negative
        // given two array of the entire time horizon, find two new array of negative robustness values
        ArrayList<Double> deliveryPlanningActivationRobustnessList = RobustnessCalculator.extractFeatureActivationRobustnessList(deliveryPlanningRobustnessList, Monitor.numberOfEnforcerActivatedList);
        ArrayList<Double> safeLandingActivationRobustnessList = RobustnessCalculator.extractFeatureActivationRobustnessList(safeLandingRobustnessList, Monitor.numberOfEnforcerActivatedList);

        double minDeliveryPlanningActivationRobustness = findMin(deliveryPlanningActivationRobustnessList);
        double maxDeliveryPlanningActivationRobustness = findMax(deliveryPlanningActivationRobustnessList);
        double averageDeliveryPlanningActivationRobustness = findAverage(deliveryPlanningActivationRobustnessList);
        double culmulativeDeliveryPlanningActivationRobustness = findSum(deliveryPlanningActivationRobustnessList);

        double minSafeLandingActivationRobustness = findMin(safeLandingActivationRobustnessList);
        double maxSafeLandingActivationRobustness = findMax(safeLandingActivationRobustnessList);
        double averageSafeLandingActivationRobustness = findAverage(safeLandingActivationRobustnessList);
        double culmulativeSafeLandingActivationRobustness = findSum(safeLandingActivationRobustnessList);

        parameterMap.put("delivery_planning_activation_robustness_average", String.valueOf(averageDeliveryPlanningActivationRobustness));
        parameterMap.put("delivery_planning_activation_robustness_cumulative", String.valueOf(culmulativeDeliveryPlanningActivationRobustness));
        parameterMap.put("delivery_planning_activation_robustness_min", String.valueOf(minDeliveryPlanningActivationRobustness));
        parameterMap.put("delivery_planning_activation_robustness_max", String.valueOf(maxDeliveryPlanningActivationRobustness));

        parameterMap.put("safe_landing_activation_robustness_average", String.valueOf(averageSafeLandingActivationRobustness));
        parameterMap.put("safe_landing_activation_robustness_cumulative", String.valueOf(culmulativeSafeLandingActivationRobustness));
        parameterMap.put("safe_landing_activation_robustness_min", String.valueOf(minSafeLandingActivationRobustness));
        parameterMap.put("safe_landing_activation_robustness_max", String.valueOf(maxSafeLandingActivationRobustness));

        boolean featureInteractionOccurred = numberOfEnforcerActivatedList.contains(2.0);
        parameterMap.put("feature_interaction_occurred", String.valueOf(featureInteractionOccurred));
        
        /**********************************/
        /***** Log Robustness To File *****/
        /**********************************/
        try {
            logRobustness(deliveryPlanningRobustnessList, safeLandingRobustnessList, parameter_configuration_number);
        } catch (Exception e) {
            java.lang.System.out.println(e);
        }
    }

    public static void logRobustness(ArrayList<Double> distanceToBoundaryList, ArrayList<Double> distanceToFollowerList, String configFileNumber) throws IOException {
        // how to write robustness arraylist to csv file
        // https://stackoverflow.com/questions/35749250/java-write-the-list-string-into-csv-file
        // FileWriter writer = new FileWriter();

        String filename = Params.ROBUSTNESS_FILE_DIR + "config_" + configFileNumber + "_" + parameterMap.get("start_time") + ".csv";
        String fileDir = Params.ROBUSTNESS_FILE_DIR;

        // first line is distance to boundary
        String distanceToBoundaryCSVString = genCSVString(distanceToBoundaryList);

        // second line is distance to follower
        String distanceToFollowerCSVString = genCSVString(distanceToFollowerList);
    
        String deliveryPlannerActivatedCSVString = genCSVString(deliveryPlannerActivatedList);
        String safeLandingActivatedCSVString = genCSVString(safeLandingActivatedList);
        String numberOfEnforcerActivatedCSVString = genCSVString(numberOfEnforcerActivatedList);

        StringBuilder sb = new StringBuilder();
        sb.append("delivery_planning_robustness\n");
        sb.append(distanceToBoundaryCSVString + "\n");
        sb.append("safe_landing_robustness\n");
        sb.append(distanceToFollowerCSVString + "\n");


        sb.append("delivery_planning_feature_activated\n");
        sb.append(deliveryPlannerActivatedCSVString + "\n");

        sb.append("safe_landing_feature_activated\n");
        sb.append(safeLandingActivatedCSVString + "\n");

        sb.append("number_of_enforcers_activated\n");
        sb.append(numberOfEnforcerActivatedCSVString + "\n");

        sb.append("parameters\n");

        sb.append("distance_to_destination\n");
        sb.append(genCSVString(Monitor.distanceToDestinationList) + "\n");

        sb.append("battery_percentage\n");
        sb.append(genCSVString(Monitor.batteryLevelList) + "\n");

        sb.append("is_landing_list\n");
        sb.append(genCSVString(Monitor.isLandingList) + "\n");

        sb.append("mission_time_left_list\n");
        sb.append(genCSVString(Monitor.missionTimeLeftList) + "\n");

        sb.append("speed_list\n");
        sb.append(genCSVString(Monitor.speedList) + "\n");

        FileOperation.writeToFile(fileDir, filename, sb.toString());
    }


    public static String genCSVString(ArrayList<Double> myList) {
        StringBuilder str = new StringBuilder("");
        // Traversing the ArrayList
        for (Double eachDouble : myList) {
 
            // Each element in ArrayList is appended
            // followed by comma
            str.append(eachDouble).append(",");
        }
 
        // StringBuffer to String conversion
        String commaseparatedlist = str.toString();
 
        // Condition check to remove the last comma
        if (commaseparatedlist.length() > 0)
           
            commaseparatedlist
                = commaseparatedlist.substring(
                    0, commaseparatedlist.length() - 1);
        return commaseparatedlist;
    }

    public static void terminate() {
        // finish time
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy_HH-mm-ss");
        String formattedDate = myDateObj.format(myFormatObj);
        parameterMap.put("finish_time", formattedDate); // finish time

        calculateResultData();
        logResultData();
    }

    public static ArrayList<String> sequence() {
        return new ArrayList<String>() {
            {
                add("start_time");
                add("finish_time");

                add("experiment_item");
                add("parameter_configuration_number");
                add("parameter_configuration_file_name");

                add("drone_has_reached_destination");
                add("safe_landing_feature_satisfied");

                add("total_resolution_count");
                add("resolution_success_count");
                add("resolution_failure_count");
                add("resolution_success_percentage");

                add("battery_level_when_started_landing");
                add("battery_level_when_landed");
                add("distance_to_destination_when_landed");
                add("drone_has_crashed");

                // overall robustness
                add("delivery_planning_min_robustness_overall");
                add("delivery_planning_max_robustness_overall");
                add("delivery_planning_average_robustness_overall");
                add("delivery_planning_cumulative_robustness_overall");

                add("safe_landing_min_robustness_overall");
                add("safe_landing_max_robustness_overall");
                add("safe_landing_average_robustness_overall");
                add("safe_landing_cumulative_robustness_overall");

                // feature interaction robustness
                add("delivery_planning_min_robustness_feature_interaction");
                add("delivery_planning_max_robustness_feature_interaction");
                add("delivery_planning_average_robustness_feature_interaction");
                add("delivery_planning_cumulative_robustness_feature_interaction");

                add("safe_landing_min_robustness_feature_interaction");
                add("safe_landing_max_robustness_feature_interaction");
                add("safe_landing_average_robustness_feature_interaction");
                add("safe_landing_cumulative_robustness_feature_interaction");

                add("avg_resolution_time");
                add("avg_cycle_time");
                add("resolution_time_percentage");

                // list out all the parameters set from the configuration files
                add("SPEED");
                add("REFRESH_RATE_SECOND");
                add("DESTINATION_FROM_ORIGIN_NORTH");
                add("DESTINATION_FROM_ORIGIN_EAST");
                add("SAFE_LANDING_BATTERY_THRESHOLD");
                add("CRASH_LANDING_BATTERY_THRESHOLD");
                add("RESOLVER");
                add("MAIN_STL_FILE_FOR_RESOLVER");
                add("MAIN_ENVIRONMENTAL_MODEL_FILE_FOR_RESOLVER");
                add("BATTERY_DRAIN_RATE");
                add("LANDING_POINT_TOLERANCE");
                add("START_LOCATION_LAT");
                add("START_LOCATION_LON");


                add("delivery_planning_activation_robustness_average");
                add("delivery_planning_activation_robustness_cumulative");
                add("delivery_planning_activation_robustness_min");
                add("delivery_planning_activation_robustness_max");
        
                // runaway activation robustness
                add("safe_landing_activation_robustness_average");
                add("safe_landing_activation_robustness_cumulative");
                add("safe_landing_activation_robustness_min");
                add("safe_landing_activation_robustness_max");
        
                // check whether the feature interaction occurred in the experiment
                add("feature_interaction_occurred");
            }
        };
    }

    // helper functions

    public static String genExperimentOutput() {
        StringBuilder sb = new StringBuilder();
        for (String parameter : sequence()) {
            sb.append(parameterMap.get(parameter) + ",");
        }
        // sb.append("\n");
        return sb.toString();
    }

    public static String genCSVHeader() {
        // TODO: convert sequence() to String + \n
        StringBuilder sb = new StringBuilder();
        for (String parameter : sequence()) {
            sb.append(parameter + ",");
        }
        sb.append("\n");
        return sb.toString();
    }

}

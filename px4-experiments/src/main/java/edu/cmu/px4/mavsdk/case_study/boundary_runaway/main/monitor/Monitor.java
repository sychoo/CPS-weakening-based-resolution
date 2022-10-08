package edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.monitor;

import io.mavsdk.telemetry.Telemetry;
import io.reactivex.Flowable;
import io.mavsdk.System;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.feature.BoundaryEnforcer;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.Params;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.drone.EgoDrone;
import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.Util;
import java.util.ArrayList;
import java.util.Collections;
import edu.cmu.stl.encoder.floats.util.FileOperation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.io.FileWriter;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.io.IOException;

public class Monitor implements Runnable {

    public static System egoDrone;
    public static System followerDrone;

    // globally parameter collection
    public static HashMap<String, String> parameterMap = new HashMap<String, String>();

    public static StringBuilder resultStringBuilder = new StringBuilder();
    public static ArrayList<Double> distanceToFollowerList = new ArrayList<Double>();
    public static ArrayList<Double> distanceToBoundaryList = new ArrayList<Double>();

    // for the ego drone
    public static int cycle_counter = 0;
    public static int feature_interaction_counter = 0;
    public static int boundary_enforcer_counter = 0;
    public static int runaway_enforcer_counter = 0;

    // for the ego drone --> resolver
    public static int successful_resolution = 0;
    public static int failed_resolution = 0;
    public static int total_resolution = 0;
    public static ArrayList<Double> cycleTimeList = new ArrayList<Double>();
    public static ArrayList<Double> resolutionTimeList = new ArrayList<Double>();
    public static String stlRequirement = "";
    public static ArrayList<Double> boundary_requirement_weakening_param_list = new ArrayList<Double>();
    public static ArrayList<Double> runaway_requirement_weakening_param_list = new ArrayList<Double>();

    public static ArrayList<Double> boundaryEnforcerActivatedList = new ArrayList<Double>();
    public static ArrayList<Double> runawayEnforcerActivatedList = new ArrayList<Double>();

    // if it is 2, both enforcers are activated,
    // if it is 1, one of the enforcers is activated
    // if it is 0, none of the enforcers is activated / all are deactivated
    public static ArrayList<Double> numberOfEnforcerActivatedList = new ArrayList<Double>();

    // for the follower drone --> ego drone
    public static int follower_caught_ego_counter = 0;

    // for the mission
    public static int mission_waypoint_counter = 0;

    public static String parameter_configuration_file_name = "";
    // public static boolean start = false;

    public Monitor(System egoDrone, System followerDrone) {
        this.egoDrone = egoDrone;
        this.followerDrone = followerDrone;
    }

    public static String genLatLonAltOutput(Telemetry.Position position) {
        return position.getLatitudeDeg() + ", " + position.getLongitudeDeg() + ", " + position.getRelativeAltitudeM();
    }

    // this is used to collect the robustness data throughout the experiment
    // note that this cannot be static because it uses the ego drone and enemy drone
    // public void blockingRun() {
    // Util.println("waiting for monitor (blocking) to start");
    // while (true) {
    // if (start == true) {
    // break;
    // }
    // DroneAction.sleep(Params.REFRESH_RATE);
    // }

    // // continuously monitors the relevant telemetry data
    // while (true) {
    // double distanceToFollower = DroneTelemetry.distanceBetweenDrones(egoDrone,
    // followerDrone);
    // double minDistanceToBoundary =
    // BoundaryEnforcer.minDistanceToInnerBoundary(egoDrone);

    // distanceToFollowerList.add(distanceToFollower);
    // distanceToBoundaryList.add(minDistanceToBoundary);

    // Util.println("Monitor: " + distanceToFollowerList);
    // Util.println("Monitor: " + distanceToBoundaryList);

    // DroneAction.sleep(Params.REFRESH_RATE);
    // }
    // }

    // this will be run as a separate thread
    @Override
    public void run() {
        java.lang.System.out.println("Telemetry monitor is running");

        // note that this asynchronous function is used to retrieve the telemetry data
        // from the drone.
        // the self adaptive loop still needs blocking functions

        // the goal here is to plot the flight path of the ego drone

        // this is for getting the current position
        // Flowable mySpecialAltitudeFlowable = egoDrone.getTelemetry()
        //         .getPosition()
        //         // .map(position -> Math.round(position.getRelativeAltitudeM()))
        //         // .map(position -> position.getRelativeAltitudeM())
        //         .distinctUntilChanged();
        // // .filter(altitude -> altitude <= 5);

        // mySpecialAltitudeFlowable.subscribe(position -> {
        //     // java.lang.System.out.println("Altitude: " + ((Telemetry.Position)
        //     // position).getRelativeAltitudeM());
        //     // java.lang.System.out.println("Latitude: " + ((Telemetry.Position)
        //     // position).getLatitudeDeg());
        //     // java.lang.System.out.println("Longitude: " + ((Telemetry.Position)
        //     // position).getLongitudeDeg());
        //     // altitudeList.add((int) altitude);
        //     String tempResult = genLatLonAltOutput((Telemetry.Position) position);
        //     resultStringBuilder.append(tempResult + "\n");
        //     // java.lang.System.out.println(tempResult);
        //     // logData(genLatLonAltOutput((Telemetry.Position) position));
        // });

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

    // public static void printResult() {
    // double minDistanceToBoundary = findMin(Monitor.distanceToBoundaryList);
    // double averageDistanceToBoundary =
    // findAverage(Monitor.distanceToBoundaryList);
    // double culmulativeDistanceToBoundary =
    // findSum(Monitor.distanceToBoundaryList);

    // Util.println("minimum distance to boundary: " + minDistanceToBoundary);
    // Util.println("average distance to boundary: " + averageDistanceToBoundary);
    // Util.println("culmulative distance to boundary: " +
    // culmulativeDistanceToBoundary);

    // double minDistanceToFollower = findMin(Monitor.distanceToFollowerList);
    // double averageDistanceToFollower =
    // findAverage(Monitor.distanceToFollowerList);
    // double culmulativeDistanceToFollower =
    // findSum(Monitor.distanceToFollowerList);

    // Util.println("minimum distance to follower: " + minDistanceToFollower);
    // Util.println("average distance to follower: " + averageDistanceToFollower);
    // Util.println("culmulative distance to follower: " +
    // culmulativeDistanceToFollower);
    // }

    // at start state
    public static void start() {
        // start time
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy_HH-mm-ss");
        String formattedDate = myDateObj.format(myFormatObj);
        parameterMap.put("start_time", formattedDate);
    }

    public static void addSignalVariables(double distanceToFollowerRobustness, double distanceToBoundaryRobustness) {
        // these are actually the robustness values
        distanceToFollowerList.add(distanceToFollowerRobustness);
        distanceToBoundaryList.add(distanceToBoundaryRobustness);
        // Util.println("Added Signal Variables");
        // Util.println(distanceToFollowerList);
        // Util.println(distanceToBoundaryList);
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

    // helper function to write the designated data to file
    // write the flight trajectory to a designated CSV file
    public static void logFlightData() {
        // String data = resultStringBuilder.toString();
        // String fileNameWithTimeStamp = Params.FLIGHT_PATH_FILE + parameterMap.get("start_time") + ".csv";

        // // always write files
        // // delete existing file and write new data
        // FileOperation.writeToFile(Params.FLIGHT_PATH_FILE_DIR, fileNameWithTimeStamp, data);
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
    
        String boundaryEnforcerActivatedCSVString = genCSVString(boundaryEnforcerActivatedList);
        String runawayEnforcerActivatedCSVString = genCSVString(runawayEnforcerActivatedList);
        String numberOfEnforcerActivatedCSVString = genCSVString(numberOfEnforcerActivatedList);

        // put all the CSV Strings in a String Builder and output it to the CSV file
        StringBuilder sb = new StringBuilder();
        sb.append("boundary_robustness\n");
        sb.append(distanceToBoundaryCSVString + "\n");

        sb.append("runaway_robustness\n");
        sb.append(distanceToFollowerCSVString + "\n");

        sb.append("boundar_enforcer_activated\n");
        sb.append(boundaryEnforcerActivatedCSVString + "\n");

        sb.append("runaway_enforcer_activated\n");
        sb.append(runawayEnforcerActivatedCSVString + "\n");

        sb.append("number_of_enforcers_activated\n");
        sb.append(numberOfEnforcerActivatedCSVString + "\n");

        FileOperation.writeToFile(fileDir, filename, sb.toString());
    }

    // assumption: both arrays are of the same length
    public static ArrayList<ArrayList<Double>> findFeatureInteractionArray(ArrayList<Double> distanceToBoundaryRobustnessList, ArrayList<Double> distanceToFollowerRobustnessList) {
        ArrayList<ArrayList<Double>> featureArray = new ArrayList<ArrayList<Double>>();

        int length1 = distanceToBoundaryRobustnessList.size();
        int length2 = distanceToFollowerRobustnessList.size();

        // when two arrays are of different length, truncate the longer one
        if (length1 != length2) {
            // Util.println("Error: two arrays are not of the same length");
            // return null;
            length1 = Math.min(length1, length2);
        }

        ArrayList<Double> distanceToBoundaryFIArray = new ArrayList<Double>();
        ArrayList<Double> distanceToFollowerFIArray = new ArrayList<Double>();

        for (int i = 0; i < length1; i++) {
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
    public static void calculateResultData() {
        parameterMap.put("avg_resolution_time", String.valueOf(findAverage(resolutionTimeList)));
        parameterMap.put("avg_cycle_time", String.valueOf(findAverage(cycleTimeList)));
        parameterMap.put("resolution_time_percentage", String.valueOf((findAverage(resolutionTimeList) / findAverage(cycleTimeList)) * 100.0));

        parameterMap.put("total_resolution_count", String.valueOf(total_resolution));
        parameterMap.put("resolution_success_count", String.valueOf(successful_resolution));
        parameterMap.put("resolution_failure_count", String.valueOf(failed_resolution));
        parameterMap.put("resolution_success_percentage", String.valueOf(total_resolution == 0 ? 0 : (successful_resolution / (double) total_resolution) * 100.0));

        ArrayList<Double> distanceToBoundaryRobustnssList = RobustnessCalculator.robustnessList("distance2bound", Params.STL_BOUNDARY_REQUIREMENT_SIGNAL_LENGTH, Params.STL_BOUNDARY_ORIGINAL_REQUIREMENT_FILE_PATH, Monitor.distanceToBoundaryList);
        double minDistanceToBoundary = findMin(distanceToBoundaryRobustnssList);
        double maxDistanceToBoundary = findMax(distanceToBoundaryRobustnssList);
        double averageDistanceToBoundary = findAverage(distanceToBoundaryRobustnssList);
        double culmulativeDistanceToBoundary = findSum(distanceToBoundaryRobustnssList);

        ArrayList<Double> distanceToFollowerRobustnssList = RobustnessCalculator.robustnessList("distance2follower", Params.STL_RUNAWAY_REQUIREMENT_SIGNAL_LENGTH, Params.STL_RUNAWAY_ORIGINAL_REQUIREMENT_FILE_PATH, Monitor.distanceToFollowerList);
        double minDistanceToFollower = findMin(distanceToFollowerRobustnssList);
        double maxDistanceToFollower = findMax(distanceToFollowerRobustnssList);
        double averageDistanceToFollower = findAverage(distanceToFollowerRobustnssList);
        double culmulativeDistanceToFollower = findSum(distanceToFollowerRobustnssList);

        
        parameterMap.put("boundary_min_robustness_overall", String.valueOf(minDistanceToBoundary));
        parameterMap.put("boundary_max_robustness_overall", String.valueOf(maxDistanceToBoundary));
        parameterMap.put("boundary_average_robustness_overall", String.valueOf(averageDistanceToBoundary));
        parameterMap.put("boundary_cumulative_robustness_overall", String.valueOf(culmulativeDistanceToBoundary));

        parameterMap.put("runaway_min_robustness_overall", String.valueOf(minDistanceToFollower));
        parameterMap.put("runaway_max_robustness_overall", String.valueOf(maxDistanceToFollower));
        parameterMap.put("runaway_average_robustness_overall", String.valueOf(averageDistanceToFollower));
        parameterMap.put("runaway_cumulative_robustness_overall", String.valueOf(culmulativeDistanceToFollower));


        // find when both of the robustness are negative
        // given two array of the entire time horizon, find two new array of negative robustness values
        ArrayList<ArrayList<Double>> featureInteractionRobustnessList = findFeatureInteractionArray(distanceToBoundaryRobustnssList, distanceToFollowerRobustnssList);
        ArrayList<Double> distanceToBoundaryFeatureInteractionList = featureInteractionRobustnessList.get(0);
        ArrayList<Double> distanceToFollowerFeatureInteractionList = featureInteractionRobustnessList.get(1);

        double minDistanceToBoundaryFI = findMin(distanceToBoundaryFeatureInteractionList);
        double maxDistanceToBoundaryFI = findMax(distanceToBoundaryFeatureInteractionList);
        double averageDistanceToBoundaryFI = findAverage(distanceToBoundaryFeatureInteractionList);
        double culmulativeDistanceToBoundaryFI = findSum(distanceToBoundaryFeatureInteractionList);

        double minDistanceToFollowerFI = findMin(distanceToFollowerFeatureInteractionList);
        double maxDistanceToFollowerFI = findMax(distanceToFollowerFeatureInteractionList);
        double averageDistanceToFollowerFI = findAverage(distanceToFollowerFeatureInteractionList);
        double culmulativeDistanceToFollowerFI = findSum(distanceToFollowerFeatureInteractionList);

        parameterMap.put("runaway_min_robustness_feature_interaction", String.valueOf(minDistanceToFollowerFI));
        parameterMap.put("runaway_max_robustness_feature_interaction", String.valueOf(maxDistanceToFollowerFI));
        parameterMap.put("runaway_average_robustness_feature_interaction", String.valueOf(averageDistanceToFollowerFI));
        parameterMap.put("runaway_cumulative_robustness_feature_interaction", String.valueOf(culmulativeDistanceToFollowerFI));

        parameterMap.put("boundary_min_robustness_feature_interaction", String.valueOf(minDistanceToBoundaryFI));
        parameterMap.put("boundary_max_robustness_feature_interaction", String.valueOf(maxDistanceToBoundaryFI));
        parameterMap.put("boundary_average_robustness_feature_interaction", String.valueOf(averageDistanceToBoundaryFI));
        parameterMap.put("boundary_cumulative_robustness_feature_interaction", String.valueOf(culmulativeDistanceToBoundaryFI));

        double minRunawayWeakeningParam = findMin(runaway_requirement_weakening_param_list);
        double maxRunawayWeakeningParam = findMax(runaway_requirement_weakening_param_list);
        double averageRunawayWeakeningParam = findAverage(runaway_requirement_weakening_param_list);
        double cumulativeRunawayWeakeningParam = findSum(runaway_requirement_weakening_param_list);

        double minBoundaryWeakeningParam = findMin(boundary_requirement_weakening_param_list);
        double maxBoundaryWeakeningParam = findMax(boundary_requirement_weakening_param_list);
        double averageBoundaryWeakeningParam = findAverage(boundary_requirement_weakening_param_list);
        double cumulativeBoundaryWeakeningParam = findSum(boundary_requirement_weakening_param_list);

        parameterMap.put("min_runaway_requirement_weakening_parameter", String.valueOf(minRunawayWeakeningParam));
        parameterMap.put("max_runaway_requirement_weakening_parameter", String.valueOf(maxRunawayWeakeningParam));
        parameterMap.put("average_runaway_requirement_weakening_parameter", String.valueOf(averageRunawayWeakeningParam));
        parameterMap.put("cumulative_runaway_requirement_weakening_parameter", String.valueOf(cumulativeRunawayWeakeningParam));

        parameterMap.put("min_boundary_requirement_weakening_parameter", String.valueOf(minBoundaryWeakeningParam));
        parameterMap.put("max_boundary_requirement_weakening_parameter", String.valueOf(maxBoundaryWeakeningParam));
        parameterMap.put("average_boundary_requirement_weakening_parameter", String.valueOf(averageBoundaryWeakeningParam));
        parameterMap.put("cumulative_boundary_requirement_weakening_parameter", String.valueOf(cumulativeBoundaryWeakeningParam));

        parameterMap.put("runaway_activated_count", String.valueOf(runaway_enforcer_counter));
        parameterMap.put("boundary_activated_count", String.valueOf(boundary_enforcer_counter));
        parameterMap.put("follower_caught_ego_count", String.valueOf(follower_caught_ego_counter));

        parameterMap.put("mission_waypoints_completed_count", String.valueOf(mission_waypoint_counter));
        parameterMap.put("experiment_item", String.valueOf(Params.RESOLVER));

        // make sure to place the double quotes around the string for it to display properly in CSV file
        // https://stackoverflow.com/questions/4617935/is-there-a-way-to-include-commas-in-csv-columns-without-breaking-the-formatting
        // parameterMap.put("stl_requirement", "\"" + String.valueOf(stlRequirement) + "\"");
        parameterMap.put("stl_requirement", "\"\""); // temporarly placeholder

        // Configuration Parameters
        parameterMap.put("EGO_AVOID_SPEED", String.valueOf(Params.EGO_AVOID_SPEED));
        parameterMap.put("EGO_BOUND_ENFORCER_SPEED", String.valueOf(Params.EGO_BOUND_ENFORCER_SPEED));
        parameterMap.put("EGO_DRONE_MAX_SPEED", String.valueOf(Params.EGO_DRONE_MAX_SPEED));
        parameterMap.put("FOLLOWER_DRONE_MAX_SPEED", String.valueOf(Params.FOLLOWER_DRONE_MAX_SPEED));
        parameterMap.put("EGO_DRONE_START_LOCATION_NORTH", String.valueOf(Params.EGO_DRONE_START_LOCATION_NORTH));
        parameterMap.put("EGO_DRONE_START_LOCATION_EAST", String.valueOf(Params.EGO_DRONE_START_LOCATION_EAST));
        parameterMap.put("RESOLVER", String.valueOf(Params.RESOLVER));
        parameterMap.put("MAX_EGO_DRONE_CYCLE", String.valueOf(Params.MAX_EGO_DRONE_CYCLE));
        parameterMap.put("parameter_configuration_file_name", String.valueOf(parameter_configuration_file_name));

        // https://howtodoinjava.com/java/string/get-last-4-characters/
        String input = String.valueOf(parameter_configuration_file_name);
        // String parameter_configuration_number = input;

        String[] split = input.split("_");
        String[] suffix = split[split.length - 1].split("\\.");

        String parameter_configuration_number = suffix[0];

        // get the number of the parameter configuration file
        parameterMap.put("parameter_configuration_number", parameter_configuration_number);

        // determine which requirement to use
        parameterMap.put("STL_BOUNDARY_ORIGINAL_REQUIREMENT_FILE_PATH", String.valueOf(Params.STL_BOUNDARY_ORIGINAL_REQUIREMENT_FILE_PATH));
        parameterMap.put("STL_RUNAWAY_ORIGINAL_REQUIREMENT_FILE_PATH", String.valueOf(Params.STL_RUNAWAY_ORIGINAL_REQUIREMENT_FILE_PATH));
        parameterMap.put("MAIN_STL_FILE_FOR_RESOLVER", String.valueOf(Params.MAIN_STL_FILE_FOR_RESOLVER));
        parameterMap.put("STL_BOUNDARY_REQUIREMENT_SIGNAL_LENGTH", String.valueOf(Params.STL_BOUNDARY_REQUIREMENT_SIGNAL_LENGTH));
        parameterMap.put("STL_RUNAWAY_REQUIREMENT_SIGNAL_LENGTH", String.valueOf(Params.STL_RUNAWAY_REQUIREMENT_SIGNAL_LENGTH));

         // find when both of the robustness are negative
        // given two array of the entire time horizon, find two new array of negative robustness values
        ArrayList<Double> boundaryActivationRobustnessList = RobustnessCalculator.extractFeatureActivationRobustnessList(distanceToBoundaryRobustnssList, Monitor.numberOfEnforcerActivatedList);
        ArrayList<Double> runawayActivationRobustnessList = RobustnessCalculator.extractFeatureActivationRobustnessList(distanceToFollowerRobustnssList, Monitor.numberOfEnforcerActivatedList);

        double minBoundaryActivation = findMin(boundaryActivationRobustnessList);
        double maxBoundaryActivation = findMax(boundaryActivationRobustnessList);
        double averageBoundaryActivation = findAverage(boundaryActivationRobustnessList);
        double cumulativeBoundaryActivation = findSum(boundaryActivationRobustnessList);

        double minRunawayActivation = findMin(runawayActivationRobustnessList);
        double maxRunawayActivation = findMax(runawayActivationRobustnessList);
        double averageRunawayActivation = findAverage(runawayActivationRobustnessList);
        double cumulativeRunawayActivation = findSum(runawayActivationRobustnessList);

        // boundary activation robustness
        parameterMap.put("boundary_activation_robustness_average", String.valueOf(averageBoundaryActivation));
        parameterMap.put("boundary_activation_robustness_cumulative", String.valueOf(cumulativeBoundaryActivation));
        parameterMap.put("boundary_activation_robustness_min", String.valueOf(minBoundaryActivation));
        parameterMap.put("boundary_activation_robustness_max", String.valueOf(maxBoundaryActivation));

        // runaway activation robustness
        parameterMap.put("runaway_activation_robustness_average", String.valueOf(averageRunawayActivation));
        parameterMap.put("runaway_activation_robustness_cumulative", String.valueOf(cumulativeRunawayActivation));
        parameterMap.put("runaway_activation_robustness_min", String.valueOf(minRunawayActivation));
        parameterMap.put("runaway_activation_robustness_max", String.valueOf(maxRunawayActivation));

        /**********************************/
        /***** Log Robustness To File *****/
        /**********************************/
        try {
            logRobustness(distanceToBoundaryRobustnssList, distanceToFollowerRobustnssList, parameter_configuration_number);
        } catch (Exception e) {
        }

    }

    // write the results in a summary CSV file
    // pull all the collected static variables
    public static void logResultData() {
        if (FileOperation.isFileEmpty(Params.RESULT_FILE)) {
            FileOperation.writeToFile(Params.RESULT_FILE_DIR, Params.RESULT_FILE, genCSVHeader());
        }

        FileOperation.appendToFile(Params.RESULT_FILE, genExperimentOutput());
    }

    public static void terminate() {

        // finish time
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy_HH-mm-ss");
        String formattedDate = myDateObj.format(myFormatObj);
        parameterMap.put("finish_time", formattedDate); // finish time

        calculateResultData();

        logFlightData();
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
                add("stl_requirement");

                add("runaway_min_robustness_overall");
                add("runaway_max_robustness_overall");
                add("runaway_average_robustness_overall");
                add("runaway_cumulative_robustness_overall");

                add("runaway_min_robustness_feature_interaction");
                add("runaway_max_robustness_feature_interaction");
                add("runaway_average_robustness_feature_interaction");
                add("runaway_cumulative_robustness_feature_interaction");

                add("boundary_min_robustness_overall");
                add("boundary_max_robustness_overall");
                add("boundary_average_robustness_overall");
                add("boundary_cumulative_robustness_overall");

                add("boundary_min_robustness_feature_interaction");
                add("boundary_max_robustness_feature_interaction");
                add("boundary_average_robustness_feature_interaction");
                add("boundary_cumulative_robustness_feature_interaction");

                add("avg_resolution_time");
                add("avg_cycle_time");
                add("resolution_time_percentage");

                add("max_runaway_requirement_weakening_parameter");
                add("min_runaway_requirement_weakening_parameter");
                add("average_runaway_requirement_weakening_parameter");
                add("cumulative_runaway_requirement_weakening_parameter");

                add("max_boundary_requirement_weakening_parameter");
                add("min_boundary_requirement_weakening_parameter");
                add("average_boundary_requirement_weakening_parameter");
                add("cumulative_boundary_requirement_weakening_parameter");

                add("total_resolution_count");
                add("resolution_success_count");
                add("resolution_failure_count");
                add("resolution_success_percentage");

                add("runaway_activated_count");
                add("boundary_activated_count");
                add("follower_caught_ego_count");

                add("mission_waypoints_completed_count");

                // list out all the parameters set from the configuration files
                add("EGO_AVOID_SPEED");
                add("EGO_BOUND_ENFORCER_SPEED");
                add("EGO_DRONE_MAX_SPEED");
                add("FOLLOWER_DRONE_MAX_SPEED");
                add("EGO_DRONE_START_LOCATION_NORTH");
                add("EGO_DRONE_START_LOCATION_EAST");
                add("RESOLVER");
                add("MAX_EGO_DRONE_CYCLE");

                // determine which requirement is being used
                add("STL_BOUNDARY_ORIGINAL_REQUIREMENT_FILE_PATH");
                add("STL_RUNAWAY_ORIGINAL_REQUIREMENT_FILE_PATH");
                add("MAIN_STL_FILE_FOR_RESOLVER");
                add("STL_BOUNDARY_REQUIREMENT_SIGNAL_LENGTH");
                add("STL_RUNAWAY_REQUIREMENT_SIGNAL_LENGTH");

                // add the parameters regarding the activation robustness
                // boundary
                add("boundary_activation_robustness_average");
                add("boundary_activation_robustness_cumulative");
                add("boundary_activation_robustness_min");
                add("boundary_activation_robustness_max");

                // runaway
                add("runaway_activation_robustness_average");
                add("runaway_activation_robustness_cumulative");
                add("runaway_activation_robustness_min");
                add("runaway_activation_robustness_max");
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
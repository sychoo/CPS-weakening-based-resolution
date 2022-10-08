// Tue Aug  9 16:17:16 EDT 2022
// Experiment Mode is divided in 4 phases:
// 1. start:    nvoked at the beginning of the experiment
// 2. monitor:  invoked at every iteration of the main loop
// 3. end:      invoked at the end of the experiment
// 4. resolve:  resolution completion phase

package edu.cmu.px4.mavsdk.case_study.organ_delivery.it1.experiment;

import java.util.ArrayList;

import edu.cmu.px4.mavsdk.case_study.organ_delivery.it1.features.DeliveryPlanning;
import edu.cmu.px4.mavsdk.case_study.organ_delivery.it1.OrganDeliveryMain;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import io.mavsdk.System;

import edu.cmu.stl.encoder.floats.util.FileOperation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;

public class Experiment {
    // specify the output files for the experiment
    public static String experimentFileDir = "experiments/results/organ_delivery/";
    public static String experimentFile = "experiments/results/organ_delivery/results.csv";

    // globally parameter collection
    public static HashMap<String, String> parameterMap = new HashMap<String, String>();

    public static ArrayList<Float> batteryLevelList = new ArrayList<Float>();
    public static ArrayList<Double> cycleTimeList = new ArrayList<Double>();
    public static ArrayList<Double> resolutionTimeList = new ArrayList<Double>();

    public static int resolutionCount = 0;
    public static int resolutionSuccessCount = 0;
    public static int resolutionFailureCount = 0;

    public static ArrayList<String> sequence() {
        return new ArrayList<String>() {{
            add("start_time");
            add("finish_time");
            add("is_landed");
            add("landing_coords_lon");
            add("landing_coords_lat");
            add("dest_lon");
            add("dest_lat");
            add("distance_to_dest");
            add("min_battery_level");
            add("avg_resolution_time");
            add("avg_cycle_time");
            add("resolution_time_percentage");
            add("resolution_count");
            add("resolution_success_count");
            add("resolution_failure_count");
            add("resolution_success_percentage");
        }};
    }

    // at start state
    public static void start(System drone) {
        // start time
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy_HH-mm-ss");
        String formattedDate = myDateObj.format(myFormatObj);
        parameterMap.put("start_time", formattedDate);
    }

    // executed during the control loop cycle
    public static void monitor(System drone) {
        batteryLevelList.add(DroneTelemetry.getRemainingBatteryPercent(drone));

        // check if there's fail safe mode
        // java.lang.System.out.println(DroneTelemetry.getFlightMode(drone));
    }

    // executed during the resolution step
    public static void resolution(System drone, double resolutionDuration, double cycleDuration, boolean result) {
        resolutionTimeList.add(resolutionDuration);
        cycleTimeList.add(cycleDuration);

        resolutionCount++;

        if (result) {
            resolutionSuccessCount++;
        } else {
            resolutionFailureCount++;
        }
    }

    // at terminate state
    public static void terminate(System drone) {
        // finish time
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy_HH-mm-ss");
        String formattedDate = myDateObj.format(myFormatObj);
        parameterMap.put("finish_time", formattedDate); // finish time

        // log the status of the drone
        parameterMap.put("is_landed", String.valueOf(DroneTelemetry.isLanded(drone)));

        // log the current location
        Double curr_lon = DroneTelemetry.getLongitudeDeg(drone);
        Double curr_lat = DroneTelemetry.getLatitudeDeg(drone);
        Double dest_lon = OrganDeliveryMain.lon;
        Double dest_lat = OrganDeliveryMain.lat;

        parameterMap.put("landing_coords_lon", String.valueOf(curr_lon));
        parameterMap.put("landing_coords_lat", String.valueOf(curr_lat));
        parameterMap.put("dest_lon", String.valueOf(dest_lon));
        parameterMap.put("dest_lat", String.valueOf(dest_lat));
        parameterMap.put("distance_to_dest", String.valueOf(DroneTelemetry.distance(curr_lat, curr_lon, dest_lat, dest_lon, 0, 0)));

        // log the battery level
        // TODO: find the lowest battery level instead
        Float minBattery = Collections.min(batteryLevelList);
        parameterMap.put("min_battery_level", String.valueOf(minBattery));

        parameterMap.put("avg_resolution_time", String.valueOf(doubleArrayListAverage(resolutionTimeList)));
        parameterMap.put("avg_cycle_time", String.valueOf(doubleArrayListAverage(cycleTimeList)));
        parameterMap.put("resolution_time_percentage", String.valueOf((doubleArrayListAverage(resolutionTimeList) / doubleArrayListAverage(cycleTimeList)) * 100.0));

        parameterMap.put("resolution_count", String.valueOf(resolutionCount));
        parameterMap.put("resolution_success_count", String.valueOf(resolutionSuccessCount));
        parameterMap.put("resolution_failure_count", String.valueOf(resolutionFailureCount));
        parameterMap.put("resolution_success_percentage", String.valueOf((resolutionSuccessCount / (double) resolutionCount) * 100.0));

        // should be invoked at the very end of the experiment to log data
        logData();
    }

    public static void logData() {
        if (FileOperation.isFileEmpty(experimentFile)) {
            FileOperation.writeToFile(experimentFileDir, experimentFile, genCSVHeader());
        }

        FileOperation.appendToFile(experimentFile, genExperimentOutput());
    }

    public static String genExperimentOutput() {
        StringBuilder sb = new StringBuilder();
        for (String parameter : sequence()) {
            sb.append(parameterMap.get(parameter) + ",");
        }
        sb.append("\n");
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

    // helper function
    public static double doubleArrayListAverage(ArrayList<Double> list) {
        double sum = 0;
        for (double d : list) {
            sum += d;
        }
        return sum / list.size();
    }
}

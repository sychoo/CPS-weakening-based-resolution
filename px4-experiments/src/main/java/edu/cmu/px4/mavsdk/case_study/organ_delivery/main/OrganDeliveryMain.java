package edu.cmu.px4.mavsdk.case_study.organ_delivery.main;

import io.mavsdk.System;
import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import edu.cmu.stl.encoder.floats.util.FileOperation;
import io.mavsdk.telemetry.Telemetry;
import io.reactivex.Flowable;

import java.io.IOException;
import java.util.ArrayList;
import edu.cmu.px4.mavsdk.case_study.organ_delivery.main.Params;

import edu.cmu.px4.mavsdk.case_study.organ_delivery.main.BatteryController;
import edu.cmu.px4.mavsdk.case_study.organ_delivery.main.DroneController;
import edu.cmu.px4.mavsdk.case_study.organ_delivery.main.monitor.Monitor;

public class OrganDeliveryMain {

    public static void main(String[] args) throws IOException {

         /****************************************/
        /***** Handle Experiment Parameters *****/
        /****************************************/

        // usage java BoundaryRunawayMain <params_file>
        if (args.length == 1) {
            String paramFileName = args[0];
            // parse the file in Params class, assign the parsed values to the static class variables
            Params.assignParamFromFile(paramFileName);
            Monitor.parameter_configuration_file_name = paramFileName;
        }


        System drone = new System("127.0.0.1", 50051);
        Thread t2 = new Thread(new BatteryController(drone), "Battery Thread");
        Thread t1 = new Thread(new DroneController(drone, t2), "Drone Controller Thread");


        // now, let's start all two threads
        t1.start();
    }
}
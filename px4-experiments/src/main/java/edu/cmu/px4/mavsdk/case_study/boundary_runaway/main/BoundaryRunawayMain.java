package edu.cmu.px4.mavsdk.case_study.boundary_runaway.main;

import io.mavsdk.System;
import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import edu.cmu.stl.encoder.floats.util.FileOperation;
import io.mavsdk.telemetry.Telemetry;
import io.reactivex.Flowable;
import java.util.ArrayList;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.Params;

// ego drone and follower drone controller, running concurrently
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.drone.EgoDrone;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.drone.FollowerDrone;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.monitor.Monitor;
import java.io.IOException;

public class BoundaryRunawayMain {
    public static void main(String[] args) throws IOException{
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

        /*********************************************/
        /***** Drone and Experiment Thread Setup *****/
        /*********************************************/

        // establish connections to the drones
        System egoDrone = new System("127.0.0.1", 50051);
        System followerDrone = new System("127.0.0.1", 50052);

        // to prevent a single process from hanging indefinitely
        // note: do not create the instances within the threads
        Thread t1 = new Thread(new EgoDrone(egoDrone, followerDrone), "Ego Drone Thread");
        Thread t2 = new Thread(new FollowerDrone(egoDrone, followerDrone), "Follower Drone Thread");
        Thread t3 = new Thread(new Monitor(egoDrone, followerDrone), "Monitor Thread");

        // now, let's start all three threads
        try {
        t1.start();
        t2.start();
        t3.start();
        } catch (Exception e) {
            java.lang.System.out.println(e);
        }
    }
}

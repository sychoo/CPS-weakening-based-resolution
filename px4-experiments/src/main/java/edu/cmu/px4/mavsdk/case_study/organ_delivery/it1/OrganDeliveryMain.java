package edu.cmu.px4.mavsdk.case_study.organ_delivery.it1;

import io.mavsdk.System;
import io.mavsdk.action.Action;
import edu.cmu.px4.mavsdk.utils.Plan;
import edu.cmu.px4.mavsdk.utils.Feature;
import edu.cmu.px4.mavsdk.case_study.organ_delivery.it1.features.DeliveryPlanning;
import edu.cmu.px4.mavsdk.case_study.organ_delivery.it1.features.SafeLanding;
import io.mavsdk.mission.Mission;
import io.mavsdk.offboard.Offboard;

import io.reactivex.Completable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;

import edu.cmu.px4.mavsdk.case_study.organ_delivery.it1.features.Resolver;
import edu.cmu.px4.mavsdk.case_study.organ_delivery.it1.experiment.Experiment;

public class OrganDeliveryMain {
    public static double SPEED = 5; // m/s
    public static int REFRESH_RATE = 2; // in seconds, the interval in which the telemetry is collected, and plans are
                                        // executed
                                        // give enough time for weakening
    // low refresh rate contribute to RC loss

    // destination : the cut
    public static double lat = 40.444139;
    public static double lon = -79.942639;

    // tepper school of business
    // public static double lat = 40.444936;
    // public static double lon = -79.9511249;

    public static void main(String[] args) {
        java.lang.System.out.println("Starting Organ Delivery Case Study...");
        run();
    }

    public static void run() {
        /*
         * Action Sequence
         * 1. Take off
         * 2. Self-adaptation:
         * a. get telemetry, package it into signal
         * b. feature 1 (safe landing): land if battery is low, otherwise keep going
         * where it was going
         * c. feature 2 (delivery planning): land if arrive at the destination, or keep
         * calculating the heading and go to the corresponding direction
         * d. conflict detection and resolution
         */

        // initialize the system and features
        System drone = new System("127.0.0.1", 50051);
        DroneAction.armAndTakeoff(drone);
        DroneAction.startOffboardMode(drone);

        // cathedral of learning mission
        // double lat = 40.4409193;
        // double lon = -79.9822816;

        // craig st
        // double lat = 40.4449039;
        // double lon = -79.948352;

        // tepper school of business
        // double lat = 40.444936;
        // double lon = -79.9511249;

        // the cut

        // webster hall
        // double lat = 40.4469951;
        // double lon = -79.9533387;

        Feature deliveryPlanning = new DeliveryPlanning(lat, lon);
        Feature safeLanding = new SafeLanding();

        // SafeLanding.LANDING_THRESHOLD_WEAKENING = 20.0;

        Experiment.start(drone);

        // start the main loop
        while (!DroneTelemetry.isLanded(drone)) {
            Experiment.monitor(drone);

            long cycleStartTime = java.lang.System.currentTimeMillis(); // measured in miliseconds

            if (!DroneTelemetry.isLanding(drone)) {
                Plan dpNext = deliveryPlanning.nextPlan(drone);
                Plan slNext = safeLanding.nextPlan(drone);

                // safe landing returns null, no plan, only acutuate delivery planning
                // both are attempting to land, consistent
                if (slNext.name().equals("nop") || (slNext.name().equals("land") && dpNext.name().equals("land"))) {
                    deliveryPlanning.actuate(drone, dpNext);
                }

                // conflict: sl = land, dp = next velocity vector
                else {
                    // resolve conflict by reconfigure
                    java.lang.System.out.println("[main] conflict detected! resolver invoked!");

                    // record the resolution time duration
                    long resolutionStartTime = java.lang.System.currentTimeMillis(); // measured in miliseconds
                    Resolver.resolve(drone);
                    long resolutionEndTime = java.lang.System.currentTimeMillis(); // measured in miliseconds

                    double resolutionDuration = (resolutionEndTime - resolutionStartTime) / 1000.0;
                    java.lang.System.out.println("[main] Resolution Took: " + resolutionDuration + " seconds");

                    // check consistency again
                    Plan dpNextResolved = deliveryPlanning.nextPlan(drone);
                    Plan slNextResolved = safeLanding.nextPlan(drone);

                    if (dpNextResolved.name().equals("fly") && slNextResolved.name().equals("land")) {
                        java.lang.System.out.println("[main] resolution failed!");
                        safeLanding.actuate(drone, slNextResolved);

                        long cycleEndTime = java.lang.System.currentTimeMillis(); // measured in miliseconds
                        double cycleDuration = (cycleEndTime - cycleStartTime) / 1000.0;
                        Experiment.resolution(drone, resolutionDuration, cycleDuration, false);
                    } else {
                        java.lang.System.out.println("[main] resolution success!");
                        deliveryPlanning.actuate(drone, dpNext);

                        long cycleEndTime = java.lang.System.currentTimeMillis(); // measured in miliseconds
                        double cycleDuration = (cycleEndTime - cycleStartTime) / 1000.0;
                        Experiment.resolution(drone, resolutionDuration, cycleDuration, true);
                    }
                }
            }
            java.lang.System.out.println("[main] state: " + DroneTelemetry.getLandedState(drone));

        }

        // the point when the drone main control loop has been terminated
        Experiment.terminate(drone);

        if (DroneTelemetry.isLanded(drone)) {
            java.lang.System.out.println("[main] Drone has landed");
        } else {
            java.lang.System.out.println("[main] Drone has not landed");
        }

        java.lang.System.out.println("[main] Exiting the program.");
        java.lang.System.exit(0);
    }
}
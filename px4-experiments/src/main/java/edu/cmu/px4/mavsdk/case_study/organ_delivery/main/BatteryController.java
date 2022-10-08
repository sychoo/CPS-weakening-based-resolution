package edu.cmu.px4.mavsdk.case_study.organ_delivery.main;

import io.mavsdk.System;
import io.mavsdk.action.Action;
import edu.cmu.px4.mavsdk.utils.Plan;
import edu.cmu.px4.mavsdk.utils.Feature;
import io.mavsdk.mission.Mission;
import io.mavsdk.offboard.Offboard;
import io.mavsdk.telemetry.Telemetry;
import io.reactivex.Completable;
import io.reactivex.Flowable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;

import edu.cmu.px4.mavsdk.case_study.organ_delivery.main.Params;
import edu.cmu.px4.mavsdk.case_study.organ_delivery.main.monitor.Monitor;
import edu.cmu.px4.mavsdk.utils.Util;

// two purpose: 
// (1) prevent the drone from wasting time during the execution cycle by pulling the battery information
// (2) make sure the battery information is accurate (decreasing, not increasing)

// result
// doesn't really work in terms of speed but okay
public class BatteryController implements Runnable {
    public System drone;
    public static double batteryPercentage = 100.0; // initialize to full battery

    public static boolean startLanding = false;

    public BatteryController(System drone) {
        this.drone = drone;
    }

    public static double getBatteryPercentage() {
        return batteryPercentage;
    }

    // manually calculate the battery percentage
    // simulate the battery draining if the telemetry pulling doesn't work
    @Override
    public void run() {
        int landing_cycle_counter = -1;
        while (true && landing_cycle_counter != 0) {
            batteryPercentage = batteryPercentage - Params.BATTERY_DRAIN_RATE * Params.REFRESH_RATE_SECOND;
            try {
                // update the battery level every refresh seconds
                TimeUnit.MILLISECONDS.sleep(Params.REFRESH_RATE_MILLISECOND);
            } catch (InterruptedException e) {
            }

            if (batteryPercentage <= Params.CRASH_LANDING_BATTERY_THRESHOLD) {
                // crash land if the battery is below 0
                Util.println("*** DRONE HAS CRASHED ***");
                Monitor.drone_has_crashed = true;
                DroneAction.land(drone);
                break;
            }

            if (startLanding) {
                landing_cycle_counter = (int) (Params.LANDING_TIME_REQUIRED / Params.REFRESH_RATE_SECOND);
                startLanding = false; // reset startLanding flag
            }
            landing_cycle_counter--;
        }
        // batteryPercentage = 0;
    }

    // @Override
    public void run2() {
        // this is for getting the current position
        Flowable mySpecialAltitudeFlowable = drone.getTelemetry()
                .getBattery()
                // .map(battery -> Math.round(battery.getRemainingPercent()))
                .map(battery -> battery.getRemainingPercent())

                // .map(position -> position.getRelativeAltitudeM())
                .distinctUntilChanged();
        // .filter(altitude -> altitude <= 5);

        mySpecialAltitudeFlowable.subscribe(battery -> {
            // this is to counteract a pitfall in which the battery value fluctuates
            // battery can only decrease, not increase
            // make sure to terminate the simulator everytime the simulation cycle is over
            double tempBatteryPercentage = (float) battery * 100;
            if (tempBatteryPercentage < batteryPercentage) {
                batteryPercentage = tempBatteryPercentage;
            }

            // if you would like to echo the battery everytime it changes (for testing
            // purpose)
            // uncomment below
            // java.lang.System.out.println("realtime battery: " + battery);
            // java.lang.System.out.println("battery controller battery: " +
            // batteryPercentage);

        });
    }
}

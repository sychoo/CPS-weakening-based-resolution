// https://auterion.com/getting-started-with-mavsdk-java/
package edu.cmu.px4.mavsdk.examples;

import io.mavsdk.System;
import io.mavsdk.mission.Mission;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Completable;

public class GettingStarted {
    public static void run() {
        java.lang.System.out.println("Starting GettingStarted...");
      
        System drone = new System("127.0.0.1", 50051);

        // note that these code are all executed simultaneously

        // arm and take off the drone
        Completable armAndTakeoff = drone.getAction().arm().andThen(drone.getAction().takeoff());
        armAndTakeoff.subscribe();
        
        // return altitude of the drone
        Flowable getAltitude = drone.getTelemetry().getPosition().map(position -> position.getRelativeAltitudeM());
        getAltitude.subscribe(altitude -> java.lang.System.out.println(altitude));

        // return the current mission of the drone
        // Question: how to build a self-adaptive loop with the Completable/Flowable construct?

        // Problem: make to to leave enough time for the drone to reach the minimal altitude before proceeding to offboard mode.
    }
}

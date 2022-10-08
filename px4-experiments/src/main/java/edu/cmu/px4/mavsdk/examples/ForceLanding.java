package edu.cmu.px4.mavsdk.examples;

import io.mavsdk.System;
import io.mavsdk.action.Action;
import edu.cmu.px4.mavsdk.utils.Plan;
import edu.cmu.px4.mavsdk.utils.Feature;
import io.mavsdk.mission.Mission;
import io.mavsdk.offboard.Offboard;

import io.reactivex.Completable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;

public class ForceLanding {
 
    public static void run() {
        System drone = new System("127.0.0.1", 50051);
        DroneAction.land(drone);
    }
}

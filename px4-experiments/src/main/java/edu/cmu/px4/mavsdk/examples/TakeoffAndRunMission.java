
package edu.cmu.px4.mavsdk.examples;

import io.mavsdk.System;
import io.mavsdk.action.Action;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TakeoffAndRunMission {
  private static final Logger logger = LoggerFactory.getLogger(TakeoffAndLand.class);

  public static void run() {
    logger.debug("Starting example: takeoff and land...");

    System drone = new System("127.0.0.1", 50051);

    CountDownLatch latch = new CountDownLatch(1);

    drone.getAction().arm()
          .doOnComplete(() -> logger.debug("Arming..."))
          .doOnError(throwable -> logger.error("Failed to arm: "
                  + ((Action.ActionException) throwable).getCode()))
          .andThen(drone.getAction().takeoff()
            .doOnComplete(() -> logger.debug("Taking off..."))
            .doOnError(throwable -> logger.error("Failed to take off: "
                    + ((Action.ActionException) throwable).getCode())))
          .delay(5, TimeUnit.SECONDS)
          .andThen(drone.getAction().land()
            .doOnComplete(() -> logger.debug("Landing..."))
            .doOnError(throwable -> logger.error("Failed to land: "
                    + ((Action.ActionException) throwable).getCode())))
          .subscribe(latch::countDown, throwable -> latch.countDown());

    try {
      latch.await();
    } catch (InterruptedException ignored) {
        // This is expected
    }

    // try
    // {
    // TimeUnit.SECONDS.sleep(5);
    // } 
    // catch(InterruptedException ex)
    // {
    //     ex.printStackTrace();
    // }
  }

}

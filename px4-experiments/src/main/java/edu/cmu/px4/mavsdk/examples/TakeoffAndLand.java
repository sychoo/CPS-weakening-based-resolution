// https://github.com/mavlink/MAVSDK-Java/tree/main/examples/java-client/src/main/java/io/mavsdk/example

// purpose: for two drones to take off simulataneously
// setup (remember to read the log for mavsdk_server for port details)
//
// (1) MAVSDK Server
// ./mavsdk_server_macos -p 50052 udp://:14541
// ./mavsdk_server_macos -p 50051 udp://:14540
//
// (2) start multipel jMAVSim instances
// /Tools/sitl_multiple_run.sh 2
// ./Tools/jmavsim_run.sh -p 4560 -l
// ./Tools/jmavsim_run.sh -p 4561 -l
// 
// (3) create System objects for each drone (using port number)
// System drone = new System("localhost", 50051);
// System drone = new System("localhost", 50052);


package edu.cmu.px4.mavsdk.examples;

import io.mavsdk.System;
import io.mavsdk.action.Action;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TakeoffAndLand {
  private static final Logger logger = LoggerFactory.getLogger(TakeoffAndLand.class);

  public static void run() {
    logger.debug("Starting example: takeoff and land...");

    // System drone = new System("0.0.0.0", 50040);
    System drone = new System("127.0.0.1", 50051);
    
    System drone2 = new System("127.0.0.1", 50052);
    System drone3 = new System("127.0.0.1", 50053);

    // System drone2 = new System();

    // System drone2 = new System("localhost", 14580);

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

    // try {
    //   latch.await();
    // } catch (InterruptedException ignored) {
    //     // This is expected
    // }

    try
    {
    TimeUnit.SECONDS.sleep(5);
    } 
    catch(InterruptedException ex)
    {
        ex.printStackTrace();
    }
    
    drone2.getAction().arm()
          .doOnComplete(() -> logger.debug("Arming..."))
          .doOnError(throwable -> logger.error("Failed to arm: "
                  + ((Action.ActionException) throwable).getCode()))
          .andThen(drone2.getAction().takeoff()
            .doOnComplete(() -> logger.debug("Taking off..."))
            .doOnError(throwable -> logger.error("Failed to take off: "
                    + ((Action.ActionException) throwable).getCode())))
          .delay(5, TimeUnit.SECONDS)
          .andThen(drone2.getAction().land()
            .doOnComplete(() -> logger.debug("Landing..."))
            .doOnError(throwable -> logger.error("Failed to land: "
                    + ((Action.ActionException) throwable).getCode())))
          .subscribe(latch::countDown, throwable -> latch.countDown());

          drone3.getAction().arm()
          .doOnComplete(() -> logger.debug("Arming..."))
          .doOnError(throwable -> logger.error("Failed to arm: "
                  + ((Action.ActionException) throwable).getCode()))
          .andThen(drone3.getAction().takeoff()
            .doOnComplete(() -> logger.debug("Taking off..."))
            .doOnError(throwable -> logger.error("Failed to take off: "
                    + ((Action.ActionException) throwable).getCode())))
          .delay(5, TimeUnit.SECONDS)
          .andThen(drone3.getAction().land()
            .doOnComplete(() -> logger.debug("Landing..."))
            .doOnError(throwable -> logger.error("Failed to land: "
                    + ((Action.ActionException) throwable).getCode())))
          .subscribe(latch::countDown, throwable -> latch.countDown());


    try {
      latch.await();
    } catch (InterruptedException ignored) {
        // This is expected
    }
  
  }
}


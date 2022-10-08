// https://github.com/mavlink/MAVSDK-Java/tree/main/examples/java-client/src/main/java/io/mavsdk/example

package edu.cmu.px4.mavsdk.examples;

import io.mavsdk.System;
import io.mavsdk.action.Action;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mavsdk.telemetry.Telemetry;

import io.reactivex.Flowable;

public class GetTelemetry {
  private static final Logger logger = LoggerFactory.getLogger(TakeoffAndLand.class);

  public static void run() {
    logger.debug("Starting example: takeoff and land...");

    // System drone = new System();
    System drone = new System("localhost", 50051);
    CountDownLatch latch = new CountDownLatch(1);

    java.lang.System.out.println("this is a main message");

    // https://auterion.com/getting-started-with-mavsdk-java/

    // this is for getting the current position
    Flowable mySpecialAltitudeFlowable = drone.getTelemetry()
                                                   .getPosition()
                                                   .map(position -> Math.round(position.getRelativeAltitudeM()))
                                                  // .map(position -> position.getRelativeAltitudeM())
                                                   .distinctUntilChanged();
                                                  //  .filter(altitude -> altitude <= 5);

    mySpecialAltitudeFlowable.take(3).subscribe(altitude -> java.lang.System.out.println("Altitude: " + altitude));
     
    
    // this is for getting the current telemetry
    Flowable mySpecialAltitudeFlowable2 = drone.getTelemetry()
    .getBattery()
    // .map(position -> Math.round(position.getRelativeAltitudeM()))
   .map(battery -> battery.getRemainingPercent())
    .distinctUntilChanged();
   //  .filter(altitude -> altitude <= 5);

mySpecialAltitudeFlowable2.take(3).subscribe(battery -> java.lang.System.out.println("Battery: " + battery));

    drone.getAction().arm()
          .doOnComplete(() -> logger.debug("Arming..."))
          .doOnError(throwable -> logger.error("Failed to arm: "
                  + ((Action.ActionException) throwable).getCode()))
          .andThen(drone.getAction().takeoff()

          // https://auterion.com/getting-started-with-mavsdk-java/
            // .doOnComplete(() -> logger.debug("Taking off... battery = " + drone.getTelemetry().getBattery()))
            .doOnComplete(() -> logger.debug("Taking off..."))
            // .doOnComplete(() -> drone.getTelemetry().getPosition().map(position -> position.getRelativeAltitudeM()).take(3).subscribe(altitude -> java.lang.System.out.println(altitude)))

            .doOnError(throwable -> logger.error("Failed to take off: "
                    + ((Action.ActionException) throwable).getCode()))
          .delay(5, TimeUnit.SECONDS)
          .andThen(drone.getAction().land()
            .doOnComplete(() -> logger.debug("Landing..."))
            .doOnError(throwable -> logger.error("Failed to land: "
                    + ((Action.ActionException) throwable).getCode()))))
          .subscribe(latch::countDown, throwable -> latch.countDown());

    try {
      latch.await();
    } catch (InterruptedException ignored) {
        // This is expected
    }
  }
}


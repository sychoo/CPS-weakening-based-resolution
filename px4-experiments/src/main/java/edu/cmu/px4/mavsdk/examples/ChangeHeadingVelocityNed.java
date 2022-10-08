// https://mavsdk.mavlink.io/main/en/cpp/guide/offboard.html
// works 

// have three heading,each fly 10 seconds

package edu.cmu.px4.mavsdk.examples;

import io.mavsdk.System;
import io.mavsdk.action.Action;
import io.mavsdk.mission.Mission;
import io.mavsdk.offboard.Offboard;

import io.reactivex.Completable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeHeadingVelocityNed {
    private static final Logger logger = LoggerFactory.getLogger(TakeoffAndLand.class);

    public static void run() {
        logger.debug("Starting example: takeoff and land...");

        System drone = new System();
        CountDownLatch latch = new CountDownLatch(1);

        // // Takeoff the drone
        // drone.getAction().arm()
        // .doOnComplete(() -> logger.debug("Arming..."))
        // .doOnError(throwable -> logger.error("Failed to arm: "
        // + ((Action.ActionException) throwable).getCode()))
        // .andThen(drone.getAction().takeoff()
        // .doOnComplete(() -> logger.debug("Taking off..."))
        // .doOnError(throwable -> logger.error("Failed to take off: "
        // + ((Mission.MissionException) throwable).getCode())))
        // .delay(5, TimeUnit.SECONDS)
        // .subscribe(latch::countDown, throwable -> latch.countDown());

        // .andThen(drone.getOffboard().setPositionNed(new
        // Offboard.PositionNedYaw(0f,0f,0f,0f)))
        // java.lang.System.out.println("I'm here 1");

        // before starting, make sure to initialize data structure for velocityNED and
        // velocity body yaw
        Completable armAndTakeoff = drone.getAction().arm().andThen(drone.getAction().takeoff());
        Completable offboard = drone.getOffboard().setVelocityNed(new Offboard.VelocityNedYaw(0f, 0f, 0f, 0f))
                // .andThen(drone.getOffboard().setPositionNed(new
                // Offboard.PositionNedYaw(0f,0f,0f,0f)))
                // .andThen(drone.getOffboard().setPositionGlobal(new
                // Offboard.PositionGlobalYaw(40.4418366,-79.9634698,20f,0f,Offboard.PositionGlobalYaw.AltitudeType.AGL)))
                // .andThen(drone.getOffboard().setVelocityBody(new
                // Offboard.VelocityBodyYawspeed(0f, 0f, 0f, 0f)))

                .andThen(drone.getOffboard().start());
        // .delay(10, TimeUnit.SECONDS);
        // .subscribe(latch::countDown, throwable -> latch.countDown());

        // java.lang.System.out.println("I'm here 2");
        Completable fly1, fly2, fly3;

        fly1 = drone.getOffboard().setVelocityNed(new Offboard.VelocityNedYaw(5.0f, -2.0f, 0f, 0f));
        fly2 = drone.getOffboard().setVelocityNed(new Offboard.VelocityNedYaw(5.0f, 2.0f, 0f, 0f));
        fly3 = drone.getOffboard().setVelocityNed(new Offboard.VelocityNedYaw(-5.0f, 2.0f, 0f, 0f));

        // .andThen(drone.getOffboard().setVelocityNed(new
        // Offboard.VelocityNedYaw(5.0f,-2.0f,0f,30f)))
        // .andThen(drone.getOffboard().setPositionNed(new
        // Offboard.PositionNedYaw(0f,100f, 0f,0f)))
        // .andThen(drone.getOffboard().setVelocityBody(new
        // Offboard.VelocityBodyYawspeed(5f, 0f, 0f, 30f)))
        // .andThen(drone.getOffboard().setPositionGlobal(new
        // Offboard.PositionGlobalYaw(40.4418366,-79.9634698,20f,0f,Offboard.PositionGlobalYaw.AltitudeType.AGL)))

        // drone.getOffboard().setVelocityNed(new
        // Offboard.VelocityNedYaw(-5.0f,2.0f,0f,0f)).delay(5,
        // TimeUnit.SECONDS).delay(5, TimeUnit.SECONDS).andThen(
        Completable land = drone.getAction().land();
        // .doOnComplete(() -> logger.debug("Landing..."))
        // .doOnError(throwable -> logger.error("Failed to land: "
        // + ((Mission.MissionException) throwable).getCode())))

        // armAndTakeoff.andThen(fly).delay(30,
        // TimeUnit.SECONDS).andThen(land).subscribe(latch::countDown, throwable ->
        // latch.countDown());

        ////////////// it works!
        // armAndTakeoff
        // .delay(10, TimeUnit.SECONDS)
        // .andThen(offboard)
        // .andThen(fly1)
        // .delay(10, TimeUnit.SECONDS)
        // .andThen(fly2)
        // .delay(10, TimeUnit.SECONDS)
        // .andThen(fly3)
        // .delay(10, TimeUnit.SECONDS)
        // .andThen(land)
        // .subscribe(latch::countDown, throwable -> latch.countDown());

        ////////////// it works!
        armAndTakeoff.blockingAwait();
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
        }

        offboard.blockingAwait();
        fly1.blockingAwait();
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
        }
        fly2.blockingAwait();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
        }

        fly3.blockingAwait();
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
        }
        land.blockingAwait();

        // armAndTakeoff.blockingSubscribe();
        // .delay(10, TimeUnit.SECONDS);

        // Completable.complete();
        // offboard
        // .andThen(fly1)
        // .delay(10, TimeUnit.SECONDS)
        // .andThen(fly2)
        // .delay(10, TimeUnit.SECONDS)
        // .andThen(fly3)
        // .delay(10, TimeUnit.SECONDS)
        // .andThen(land)
        // .subscribe(latch::countDown, throwable -> latch.countDown());
        try {
            latch.await();
        } catch (InterruptedException ignored) {
            // This is expected
        }
    }
}
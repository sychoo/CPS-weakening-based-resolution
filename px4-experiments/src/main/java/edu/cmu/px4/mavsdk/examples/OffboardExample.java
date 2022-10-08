// https://mavsdk.mavlink.io/main/en/cpp/guide/offboard.html

package edu.cmu.px4.mavsdk.examples;


    import io.mavsdk.System;
    import io.mavsdk.action.Action;
    import io.mavsdk.mission.Mission;
    import io.mavsdk.offboard.Offboard;
    import java.util.concurrent.CountDownLatch;
    import java.util.concurrent.TimeUnit;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    
    public class OffboardExample {
      private static final Logger logger = LoggerFactory.getLogger(TakeoffAndLand.class);
    
      public static void run() {
        logger.debug("Starting example: takeoff and land...");
    
        System drone = new System();
        CountDownLatch latch = new CountDownLatch(1);
    
        drone.getAction().arm()
              .doOnComplete(() -> logger.debug("Arming..."))
              .doOnError(throwable -> logger.error("Failed to arm: "
                      + ((Action.ActionException) throwable).getCode()))
              .andThen(drone.getAction().takeoff()
                .doOnComplete(() -> logger.debug("Taking off..."))
                .doOnError(throwable -> logger.error("Failed to take off: "
                        + ((Mission.MissionException) throwable).getCode())))
              .delay(5, TimeUnit.SECONDS)
            //   .andThen(drone.getOffboard().setPositionNed(new Offboard.PositionNedYaw(0f,0f,0f,0f)))

                // before starting, make sure to initialize data structure for velocityNED and velocity body yaw
              .andThen(drone.getOffboard().setVelocityNed(new Offboard.VelocityNedYaw(0f,0f,0f,0f)))
            //   .andThen(drone.getOffboard().setPositionNed(new Offboard.PositionNedYaw(0f,0f,0f,0f)))
            //   .andThen(drone.getOffboard().setPositionGlobal(new Offboard.PositionGlobalYaw(40.4418366,-79.9634698,20f,0f,Offboard.PositionGlobalYaw.AltitudeType.AGL)))
            //   .andThen(drone.getOffboard().setVelocityBody(new Offboard.VelocityBodyYawspeed(0f, 0f, 0f, 0f)))
            
            

              .andThen(drone.getOffboard().start())
            //   .andThen(drone.getOffboard().setVelocityNed(new Offboard.VelocityNedYaw(5.0f,-2.0f,0f,30f)))
              .andThen(drone.getOffboard().setVelocityNed(new Offboard.VelocityNedYaw(5.0f,-2.0f,0f,0f)))
            // .andThen(drone.getOffboard().setPositionNed(new Offboard.PositionNedYaw(0f,100f, 0f,0f)))
            //   .andThen(drone.getOffboard().setVelocityBody(new Offboard.VelocityBodyYawspeed(5f, 0f, 0f, 30f)))
            // .andThen(drone.getOffboard().setPositionGlobal(new Offboard.PositionGlobalYaw(40.4418366,-79.9634698,20f,0f,Offboard.PositionGlobalYaw.AltitudeType.AGL)))



              .delay(3000, TimeUnit.SECONDS)
              .andThen(drone.getAction().land()
                .doOnComplete(() -> logger.debug("Landing..."))
                .doOnError(throwable -> logger.error("Failed to land: "
                        + ((Mission.MissionException) throwable).getCode())))
              .subscribe(latch::countDown, throwable -> latch.countDown());
    
        try {
          latch.await();
        } catch (InterruptedException ignored) {
            // This is expected
        }
      }
    }
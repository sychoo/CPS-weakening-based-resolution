package edu.cmu.px4.mavsdk.examples;

import io.mavsdk.System;
import io.mavsdk.mission.Mission;
import io.mavsdk.offboard.Offboard;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MissionOffboardExample {
  private static final Logger logger = LoggerFactory.getLogger(RunMission.class);

  public static void run() {
    logger.debug("Starting example: mission...");

    List<Mission.MissionItem> missionItems = new ArrayList<>();
    missionItems.add(generateMissionItem(47.398039859999997, 8.5455725400000002));
    missionItems.add(generateMissionItem(47.398036222362471, 8.5450146439425509));
    missionItems.add(generateMissionItem(47.397825620791885, 8.5450092830163271));
    missionItems.add(generateMissionItem(47.397832880000003, 8.5455939999999995));

    Mission.MissionPlan missionPlan = new Mission.MissionPlan(missionItems);


    System drone = new System("127.0.0.1", 50051);

    // Upload, arm and start the mission
    drone.getMission()
        .setReturnToLaunchAfterMission(true)
        .andThen(drone.getMission().uploadMission(missionPlan)
            .doOnComplete(() -> logger.debug("Upload succeeded")))
        .andThen(drone.getAction().arm())
        .andThen(drone.getMission().startMission()
            .doOnComplete(() -> logger.debug("Mission started")))
        .subscribe();

    // Pause after the first mission item is reached, run an offboard routine and resume mission
    drone.getMission()
        .getMissionProgress()
        // .filter(progress -> progress.getCurrentItemIndex() == 1)
        .doOnNext(progress -> logger.debug("Reached first mission item, starting offboard routine..."))
        .take(1)
        .ignoreElements()
        .andThen(drone.getMission().pauseMission())
        .delay(2, TimeUnit.SECONDS)
        .andThen(drone.getOffboard().setPositionNed(new Offboard.PositionNedYaw(0f,-5.0f,0.0f,0f)))
        .andThen(drone.getOffboard().start())
        .delay(1, TimeUnit.SECONDS)
        .andThen(drone.getOffboard().stop()
            .doOnComplete(() -> logger.debug("Resuming mission in 5 seconds...")))
        .delay(5, TimeUnit.SECONDS)
        .doOnComplete(() -> logger.debug("Resuming..."))
        .andThen(drone.getMission().startMission())
        .subscribe();

    // Monitor the mission progress and "release" the latch when it is finished
    CountDownLatch latch = new CountDownLatch(1);
    drone.getMission()
        .getMissionProgress()
        // .filter(progress -> progress.getCurrentItemIndex() == progress.getMissionCount())
        .take(1)
        .subscribe(ignored -> latch.countDown());

    try {
      latch.await();
    } catch (InterruptedException ignored) {
      // This is expected
    }
  }

  public static Mission.MissionItem generateMissionItem(double latitudeDeg, double longitudeDeg) {
    return new Mission.MissionItem(
      latitudeDeg,
      longitudeDeg,
      10f,
      10f,
      true,
      Float.NaN,
      Float.NaN,
      Mission.MissionItem.CameraAction.NONE,
      Float.NaN,
      Double.NaN,
      Float.NaN,
      Float.NaN,
      Float.NaN);
}
}
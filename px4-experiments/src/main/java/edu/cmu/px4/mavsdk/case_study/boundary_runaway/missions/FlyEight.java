// note that this currently does not work properly.
package edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions;

import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import edu.cmu.px4.mavsdk.utils.DroneAction;

import io.mavsdk.telemetry.Telemetry;
import io.mavsdk.System;

public class FlyEight {
  public final static double MAX_DRONE_SPEED = 2.0;
  public final static double angularStep = -0.1;
  public final static double figureLength = 20;
  
  // TODO: get the fly 8 mission to work
  // be translating the C++ code to Java
  public static void run() {

    System drone = new System("127.0.0.1", 50051);
    DroneAction.armAndTakeoff(drone);
    DroneAction.startOffboardMode(drone);

    double theta = 2.5 * Math.PI;
    double waypointX = 0;
    double waypointY = 0;

    boolean firstWpt = true; // first way point

    while (theta > 0.5 * Math.PI) {
      double prevWaypointX = waypointX;
      double prevWaypointY = waypointY;
      double scale = 2 / (3 - Math.cos(2 * theta));
      waypointX = figureLength * scale * Math.cos(theta);
      waypointY = figureLength * scale * Math.sin(2 * theta) / 2;

      theta += angularStep;

      if (firstWpt) {
        firstWpt = false;
        continue;
      }

      // std::cout << "next waypoint: " << waypointX << ',' << waypointY << std::endl;

      double distance = Math.sqrt(Math.pow(waypointX - prevWaypointX, 2) + Math.pow(waypointY - prevWaypointY, 2));
      double estimatedTimeToWpt = distance / MAX_DRONE_SPEED;

      //// auto startTime = hrclock::now();
      // Main fly-8 loop
      while (true) {
        // get the current velocity
        Telemetry.PositionNed posvel = DroneTelemetry.getPositionVelocityNed(drone).getPosition();
        float posX = posvel.getNorthM();
        float posY = posvel.getEastM();

        float yaw = DroneTelemetry.getEulerAngle(drone).getYawDeg();

        double deltaX = waypointX - posX;
        double deltaY = waypointY - posY;
        // // std::cout << "pos: " << posX << ',' << posY
        // // << " deltaPos: " << deltaX << ',' << deltaY
        // // << std::endl;

        double nextYaw= DroneTelemetry.getAngle(deltaX, deltaY);
        double deltaYaw = DroneTelemetry.fmod((nextYaw > yaw) ? nextYaw - yaw : yaw - nextYaw,
        360.0);
        if (deltaYaw > 180) {
        deltaYaw = 360 - deltaYaw;
        }

        if (deltaYaw > 90) { // the next waypoint is behind
          java.lang.System.out.println("flew past waypoint");
        // break;
        }

        // // compute velocity vector components
        double diag = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        double velX = MAX_DRONE_SPEED * deltaX / diag;
        double velY = MAX_DRONE_SPEED * deltaY / diag;

        double figureYaw = DroneTelemetry.getAngle(waypointX - prevWaypointX, waypointY -
        prevWaypointY);

        // auto start_time = chrono::high_resolution_clock::now();
        DroneAction.sendVelocityNed(drone, 2, (float) velX, (float) velY, 0.0f, (float)
        figureYaw);
        // auto end_time = chrono::high_resolution_clock::now();

        // auto time_taken =
        // chrono::duration_cast<chrono::milliseconds>(end_time-start_time).count();
        // cout << "Coordinator took "
        // << time_taken << " milliseconds.\n" << endl;

        // // Sleep for TICK_DURATION minus however long the last computation took
        // // (or 0 if the computation took longer than the tick duration)
        // // i.e., total duration between ticks is close to the actual TICK_DURATION
        // auto sleep_time =
        // max((int)(std::lround(droneutil::TICK_DURATION*1000)-time_taken), 0);
        // if(sleep_time == 0) {
        // cout << "WARNING: Computation time took longer than tick duration.
        // Computation time: " << time_taken << " ms." << endl;
        // }

        // this_thread::sleep_for(chrono::milliseconds(sleep_time));

        // double deltaSec =
        // std::chrono::duration<double>(hrclock::now() - startTime).count();
        // /*
        // double deltaLoc =
        // sqrt(pow(posX-waypointY, 2), pow(posY-waypointY, 2));

        // if(deltaLoc <=) {

        // }
        // */

        // if (deltaSec > estimatedTimeToWpt) {
        //   break;
        // }
      }
    }
    // return true;
  }

  public static void main(String[] args) {
    run();
  }
}

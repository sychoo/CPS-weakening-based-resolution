package edu.cmu.px4.mavsdk.utils;

import io.mavsdk.System;
import io.mavsdk.action.Action;
import edu.cmu.px4.mavsdk.utils.Plan;
import edu.cmu.px4.mavsdk.utils.Feature;
// import edu.cmu.px4.mavsdk.case_study.organ_delivery.features.DeliveryPlanning;
// import edu.cmu.px4.mavsdk.case_study.organ_delivery.features.SafeLanding;
import io.mavsdk.mission.Mission;
import io.mavsdk.offboard.Offboard;

import io.reactivex.Completable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.mavsdk.telemetry.Telemetry;

public class DroneAction {
    public static int SIMULATION_SPEED = 1; // n times the real time speed

    public static void armAndTakeoff(System drone) {
        Completable armAndTakeoff = drone.getAction().arm().andThen(drone.getAction().takeoff());
        java.lang.System.out.println("arming and taking off");
        armAndTakeoff.blockingAwait();
        // ensure the drone reaches minimum altitude (20 m)
        sleep(20 * 1000 / SIMULATION_SPEED); // sleep 20 seconds
    }

    public static void land(System drone) {
        Completable land = drone.getAction().land();
        java.lang.System.out.println("landing");
        land.blockingAwait();
    }

    public static void startOffboardMode(System drone) {
        // initialize VelocityNedYaw struct
        Completable offboard = drone.getOffboard().setVelocityNed(new Offboard.VelocityNedYaw(0f, 0f, 0f, 0f))
                .andThen(drone.getOffboard().start());
        java.lang.System.out.println("starting offboard mode");
        offboard.blockingAwait();
    }

    /*
     * @param seconds: number of seconds that drone needs to execute at the current
     * posture
     */
    public static void sendVelocityNed(System drone, int seconds, float northSpeed, float eastSpeed, float downSpeed,
            float yaw) {
        Completable command = drone.getOffboard()
                .setVelocityNed(new Offboard.VelocityNedYaw(northSpeed, eastSpeed, downSpeed, yaw));
        // North, East, Down, Yaw
        java.lang.System.out
                .println("starting commands: N:" + northSpeed + " E:" + eastSpeed + " D:" + downSpeed + " Yaw:" + yaw);
        command.blockingAwait();
        sleep(seconds / SIMULATION_SPEED);
    }

    // https://github.com/mavlink/MAVSDK-Python/blob/main/examples/offboard_velocity_body.py
    // uses Offboard.VelocityBodyYawspeed
    public static void sendVelocityBodyYawspeed(System drone, int seconds, float forwardSpeed, float rightSpeed,
            float downSpeed, float yawDeg) {
        Completable command = drone.getOffboard()
                .setVelocityBody(new Offboard.VelocityBodyYawspeed(forwardSpeed, rightSpeed, downSpeed, yawDeg));
        // North, East, Down, Yaw
        java.lang.System.out.println("starting commands: Forward:" + forwardSpeed + " Right:" + rightSpeed + " Down:"
                + downSpeed + " YawDeg:" + yawDeg);
        command.blockingAwait();
        sleep(seconds / SIMULATION_SPEED);
    }

    // climb or descend to a given altitude
    public static void goToDestinationAlt(System drone, int seconds, double max_speed, double dest_alt) {
        double drone_alt = DroneTelemetry.getAltitude(drone);

        double speed = Math.abs(max_speed);

        if (seconds * max_speed > Math.abs(dest_alt - drone_alt)) {
            seconds = (int) (Math.abs(dest_alt - drone_alt) / max_speed);
            speed = Math.abs(dest_alt - drone_alt);
        }

        // speed is always positive

        // going up, velocity is negative
        if (dest_alt > drone_alt) {
            speed = -speed;
        }

        sendVelocityNed(drone, seconds, 0.0f, 0.0f, new Float(speed), 0.0f);
    }

    public static void clockwiseClimb(System drone, int seconds) {
        sendVelocityBodyYawspeed(drone, seconds, (float) 5.0, (float) 0.0, (float) -1.0, (float) 5.0);
    }

    // generate an action for drone1 to repel drone2 with a limiting speed
    // this generate the opposite vector as the followDrone method
    public static ArrayList<Double> repelDrone(System drone1, System drone2, int seconds, double max_speed) {
        Telemetry.PositionNed relative_pos = DroneTelemetry.getRelativeDronePosition(drone1, drone2);

        float north_pos = relative_pos.getNorthM();
        float east_pos = relative_pos.getEastM();
        float down_pos = relative_pos.getDownM();

        // calculate the diagonal distance between the drones
        double diagonal_distance = Math.sqrt(Math.pow(north_pos, 2) + Math.pow(east_pos, 2) + Math.pow(down_pos, 2));

        // test print
        // java.lang.System.out.println("Distance between two drones: " + diagonal_distance);

        // prevent division by zero
        if (diagonal_distance > 0.0) {
            // note that diagonal_distance / max_speed is equal to north_pos / north_speed =
            // east_pos / east_speed = down_pos / down_speed
            // we then can calculate the component speed for each axis
            float north_speed = -1 * (float) (north_pos * max_speed / diagonal_distance);
            float east_speed = -1 * (float) (east_pos * max_speed / diagonal_distance);
            float down_speed = -1 * (float) (down_pos * max_speed / diagonal_distance);
            sendVelocityNed(drone1, seconds, north_speed, east_speed, down_speed, 0.0f);

            return new ArrayList<Double>(Arrays.asList((double) north_speed, (double) east_speed, (double) down_speed));
        }
        // case when drone 2 is caught by drone 1
        else {
            // do nothing if the drones are at the same position
            // hold current position
            java.lang.System.out.println("drone2 has collided with drone1");
            sendVelocityNed(drone1, seconds, 0, 0, 0, 0.0f);

            return new ArrayList<Double>(Arrays.asList(0.0, 0.0, 0.0));
        }

    }

    // generate an action for drone1 to follow drone2 with a limiting speed
    public static boolean followDrone(System drone1, System drone2, int seconds, double max_speed,
            double catch_distance_meters) {
        Telemetry.PositionNed relative_pos = DroneTelemetry.getRelativeDronePosition(drone1, drone2);

        float north_pos = relative_pos.getNorthM();
        float east_pos = relative_pos.getEastM();
        float down_pos = relative_pos.getDownM();

        // calculate the diagonal distance between the drones
        double diagonal_distance = Math.sqrt(Math.pow(north_pos, 2) + Math.pow(east_pos, 2) + Math.pow(down_pos, 2));

        // test print
        java.lang.System.out.println("Distance between two drones: " + diagonal_distance);

        // prevent division by zero
        if (diagonal_distance > catch_distance_meters) {
            // note that diagonal_distance / max_speed is equal to north_pos / north_speed =
            // east_pos / east_speed = down_pos / down_speed
            // we then can calculate the component speed for each axis
            float north_speed = (float) (north_pos * max_speed / diagonal_distance);
            float east_speed = (float) (east_pos * max_speed / diagonal_distance);
            float down_speed = (float) (down_pos * max_speed / diagonal_distance);
            sendVelocityNed(drone1, seconds, north_speed, east_speed, down_speed, 0.0f);
            return false;
        }
        // case when drone 2 is caught by drone 1
        else {
            // do nothing if the drones are at the same position
            // hold current position
            java.lang.System.out.println("drone2 has been caught!");
            sendVelocityNed(drone1, seconds, 0, 0, 0, 0.0f);
            // sleep(recovery_time_seconds);
            return true;
        }

    }

    // generate an action for drone1 to follow drone2 with a limiting speed
    // this is for the boundary enforcer - in the case of drone violating the
    // boundary, it generate a velocity
    // vector that fly to the center of the boundary
    public static ArrayList<Double> goToDestination3D(System drone, int miliseconds, double max_speed, double dest_lat,
            double dest_lon, double dest_alt) {

        double seconds = miliseconds / 1000.0;
        Telemetry.PositionNed relative_pos = DroneTelemetry.getRelativeDroneDestPosition(drone, dest_lat, dest_lon,
                dest_alt);

        float north_pos = relative_pos.getNorthM();
        float east_pos = relative_pos.getEastM();
        float down_pos = relative_pos.getDownM();

        // calculate the diagonal distance between the drones
        double diagonal_distance = Math.sqrt(Math.pow(north_pos, 2) + Math.pow(east_pos, 2) + Math.pow(down_pos, 2));

        // test print
        java.lang.System.out.println("Distance between drone and destination: " + diagonal_distance);

        // case when drone is close to the destination, go at reduced speed
        if (seconds * max_speed > diagonal_distance) {
            max_speed = diagonal_distance;
        }

        // fly at normal speed (max_speed)
        // note that diagonal_distance / max_speed is equal to north_pos / north_speed =
        // east_pos / east_speed = down_pos / down_speed
        // we then can calculate the component speed for each axis
        float north_speed = (float) (north_pos * max_speed / diagonal_distance);
        float east_speed = (float) (east_pos * max_speed / diagonal_distance);
        float down_speed = (float) (down_pos * max_speed / diagonal_distance);
        sendVelocityNed(drone, miliseconds, north_speed, east_speed, down_speed, 0.0f);

        return new ArrayList<Double>(Arrays.asList((double) north_speed, (double) east_speed, (double) down_speed));
    }

     // generate an action for drone1 to follow drone2 with a limiting speed
    // this is for the boundary enforcer - in the case of drone violating the
    // boundary, it generate a velocity
    // vector that fly to the center of the boundary
    public static ArrayList<Double> repelDestination3D(System drone, int miliseconds, double max_speed, double dest_lat,
            double dest_lon, double dest_alt) {

        double seconds = miliseconds / 1000.0;
        Telemetry.PositionNed relative_pos = DroneTelemetry.getRelativeDroneDestPosition(drone, dest_lat, dest_lon,
                dest_alt);

        float north_pos = relative_pos.getNorthM();
        float east_pos = relative_pos.getEastM();
        float down_pos = relative_pos.getDownM();

        // calculate the diagonal distance between the drones
        double diagonal_distance = Math.sqrt(Math.pow(north_pos, 2) + Math.pow(east_pos, 2) + Math.pow(down_pos, 2));

        // test print
        // java.lang.System.out.println("Distance between drone and destination: " + diagonal_distance);

        // case when drone is close to the destination, go at reduced speed
        if (seconds * max_speed > diagonal_distance) {
            max_speed = diagonal_distance;
        }

        // fly at normal speed (max_speed)
        // note that diagonal_distance / max_speed is equal to north_pos / north_speed =
        // east_pos / east_speed = down_pos / down_speed
        // we then can calculate the component speed for each axis
        float north_speed = (float) (north_pos * max_speed / diagonal_distance);
        float east_speed = (float) (east_pos * max_speed / diagonal_distance);
        float down_speed = (float) (down_pos * max_speed / diagonal_distance);
        sendVelocityNed(drone, miliseconds, -north_speed, -east_speed, -down_speed, 0.0f);

        return new ArrayList<Double>(Arrays.asList((double) -north_speed, (double) -east_speed, (double) -down_speed));
    }

    // give the destination position and the current drone, generate velocity vector
    // to reach the destination
    // returns the command it generated ArrayList<North, East, Down>
    public static ArrayList<Double> goToDestination(System drone, int miliseconds, double max_speed, double dest_lat, double dest_lon) {
        double seconds = miliseconds / 1000.0;
        // get the current position of the drone
        Telemetry.Position pos = DroneTelemetry.getPosition(drone);
        double curr_lat = pos.getLatitudeDeg();
        double curr_lon = pos.getLongitudeDeg();

        double distance_between_2_points = DroneTelemetry.distance(curr_lat, curr_lon, dest_lat, dest_lon, 0, 0);

        if (seconds * max_speed > distance_between_2_points) {
            max_speed = distance_between_2_points;
        }

        // calculate the bearing between the current location and the destination
        double bearing = DroneTelemetry.bearing(curr_lat, curr_lon, dest_lat, dest_lon);
        double northSpeed = max_speed * Math.cos(Math.toRadians(bearing));
        double eastSpeed = max_speed * Math.sin(Math.toRadians(bearing));

        sendVelocityNed(drone, miliseconds, (float) northSpeed, (float) eastSpeed, 0.0f, 0.0f);

        return new ArrayList<Double>(Arrays.asList(northSpeed, eastSpeed, 0.0));
    }

    // give the destination position and the current drone, generate velocity vector
    // to reach the destination
    public static ArrayList<Double> repelDestination(System drone, int miliseconds, double max_speed, double dest_lat, double dest_lon) {
        double seconds = miliseconds / 1000.0;

        // get the current position of the drone
        Telemetry.Position pos = DroneTelemetry.getPosition(drone);
        double curr_lat = pos.getLatitudeDeg();
        double curr_lon = pos.getLongitudeDeg();

        // calculate the bearing between the current location and the destination
        double bearing = DroneTelemetry.bearing(curr_lat, curr_lon, dest_lat, dest_lon);
        double northSpeed = max_speed * Math.cos(Math.toRadians(bearing));
        double eastSpeed = max_speed * Math.sin(Math.toRadians(bearing));

        sendVelocityNed(drone, miliseconds, (float) -northSpeed, (float) -eastSpeed, 0.0f, 0.0f);

        return new ArrayList<Double>(Arrays.asList(-northSpeed, -eastSpeed, 0.0));
    }


    // hold the drone at the original position
    public static ArrayList<Double> holdPosition(System drone, int miliseconds) {
        java.lang.System.out.println("holding current position");
        sendVelocityNed(drone, miliseconds, 0, 0, 0, 0.0f);

        return new ArrayList<Double>(Arrays.asList(0.0, 0.0, 0.0));
    }

    public static void sleep(long miliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(miliseconds);
        } catch (InterruptedException e) {
        }
    }
}

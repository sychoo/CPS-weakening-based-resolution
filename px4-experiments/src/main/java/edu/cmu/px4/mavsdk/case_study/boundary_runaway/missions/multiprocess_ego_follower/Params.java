package edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.multiprocess_ego_follower;

public class Params {
    public static double FOLLOWER_DRONE_SPEED = 6.00; // m/s
    public static double EGO_DRONE_SPEED = 4.00; // m/s

    public static int REFRESH_RATE = 1; // Hz
    public static int RECOVERY_TIME = 5; // cycles

    public static double CATCH_DISTANCE = 2; // meters, the distance in which the follower drone successfully catch the ego drone
    public static double AVOID_DISTANCE = 4; // meters, the distance that triggers avoidance action from ego drone

    // destination : the cut
    public static double DEST_LAT = 40.444139;
    public static double DEST_LON = -79.942639;
}

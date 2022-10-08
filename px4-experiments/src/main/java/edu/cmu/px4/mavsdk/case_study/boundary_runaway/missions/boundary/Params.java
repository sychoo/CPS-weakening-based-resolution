package edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.boundary;

public class Params {
    public static double EGO_DRONE_SPEED = 15.00; // m/s

    public static int REFRESH_RATE = 1; // seconds

    // destination : the cut
    public static double DEST_LAT = 40.444139;
    public static double DEST_LON = -79.942639;
    public static double DEST_ALT = 50;

    // box center : TCS Hall
    public static double BOUND_CTR_LAT = 40.444907; // boundary center latitude
    public static double BOUND_CTR_LON = -79.947306; // boundary center longitude
    public static double BOUND_CTR_ALT = 20; // boundary center: in m/s
    public static double REACH_RADIUS = 5; // the point where it is considered reached.

    public static double BOUND_SIZE = 10; // boundary width in m
    // public static double BOUND_X_LOWER = -10; // boundary x lower bound
    // public static double BOUND_X_UPPER = 10; // boundary x lower bound
    // public static double BOUND_Y_LOWER = -10; // boundary y lower bound
    // public static double BOUND_Y_UPPER = 10; // boundary y lower bound
    // public static double BOUND_Z_LOWER = -10; // boundary z lower bound
    // public static double BOUND_Z_UPPER = 10; // boundary z lower bound

}

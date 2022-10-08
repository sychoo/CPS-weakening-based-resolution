package edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.feature;

import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.Params;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import java.util.Collections;
import io.mavsdk.System;
import java.util.ArrayList;

public class BoundaryEnforcer {
    // step 1. calculate the distance between the current drone locations and the boundary center
    // step 2. use BOUND_SQUARE_SIDE / 2 - max(horizontal distance, vertical distance)
    // calculate the distance to the boundaries (lat, lon only, don't care about altitude)
    // note that this demonstrates a square boundary.

    // note that a negative value indicate that the drone has exceeded the boundary
    public static double minDistanceToInnerBoundary(System drone) {
        ArrayList<Double> horizontal_vertical_distance = DroneTelemetry.horizontalVerticalDistanceBetweenDroneDest(drone, Params.BOUND_CTR_LAT, Params.BOUND_CTR_LON);
        double max_horizontal_vertical_distance = Collections.max(horizontal_vertical_distance);
        return Params.BOUND_SQUARE_SIDE / 2 - max_horizontal_vertical_distance;
    }
}

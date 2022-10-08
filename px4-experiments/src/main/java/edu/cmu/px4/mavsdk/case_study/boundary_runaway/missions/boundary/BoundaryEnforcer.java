package edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.boundary;

import io.mavsdk.System;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.missions.multiprocess_ego_follower.EgoDrone;
import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import io.mavsdk.telemetry.Telemetry;

public class BoundaryEnforcer {
    public static void main(String[] args) {
        // first go to the boundary center
        // connect to the drones
        System egoDrone = new System("127.0.0.1", 50051);

        DroneAction.armAndTakeoff(egoDrone);
        DroneAction.startOffboardMode(egoDrone);

        /**************************/
        /*** Position the drone ***/
        /**************************/

        // go to the boundary center (lat, lon)
        while (true) {
            double horizontal_distance = DroneTelemetry.distanceBetweenDroneAndDest(egoDrone, Params.BOUND_CTR_LAT, Params.BOUND_CTR_LON);
            java.lang.System.out.println("horizontal distance: " + horizontal_distance);

            if (horizontal_distance < Params.REACH_RADIUS) {
                break;
            }
            DroneAction.goToDestination(egoDrone, 1, Params.EGO_DRONE_SPEED, Params.BOUND_CTR_LAT, Params.BOUND_CTR_LON);
        }

        java.lang.System.out.println("finished horizontal manuever");

        // lat/lon has been reached
        // go to boundary center (alt)
        while (true) {
            double vertical_distance = DroneTelemetry.altDiffBetweenDroneAndDest(egoDrone, Params.BOUND_CTR_ALT);
            java.lang.System.out.println("vertical distance: " + vertical_distance);

            if (vertical_distance < Params.REACH_RADIUS) {
                break;
            }

            DroneAction.goToDestinationAlt(egoDrone, 1, Params.EGO_DRONE_SPEED, Params.BOUND_CTR_ALT);
        }
        
        java.lang.System.out.println("finished vertical manuever");


        /**************************************************/
        /*** Violate Boundary, Enable Boundary Enforcer ***/
        /**************************************************/

        // execute a mission that violates the boundary
        while (true) {

            double horizontal_distance = DroneTelemetry.distanceBetweenDroneAndDest(egoDrone, Params.BOUND_CTR_LAT, Params.BOUND_CTR_LON);
            double vertical_distance = DroneTelemetry.altDiffBetweenDroneAndDest(egoDrone, Params.BOUND_CTR_ALT);

            // note that this is a bounding circle instead of a bounding box!!
            // TODO: change it to a bounding box
            // TODO: use the distance and bearing function to calculate the true horizontal and vertical distance
            if (horizontal_distance > Params.BOUND_SIZE || vertical_distance > Params.BOUND_SIZE) {
                java.lang.System.out.println("violated boundary");
                DroneAction.goToDestination3D(egoDrone, Params.REFRESH_RATE, Params.EGO_DRONE_SPEED, Params.BOUND_CTR_LAT, Params.BOUND_CTR_LON, Params.BOUND_CTR_ALT);
            } 
            else {
                java.lang.System.out.println("executing mission");
                DroneAction.goToDestination3D(egoDrone, Params.REFRESH_RATE, Params.EGO_DRONE_SPEED, Params.DEST_LAT, Params.DEST_LON, Params.DEST_ALT);
            }
        }
    }

}

// snippets of MAVSDK code that support case studies with 3 goals
// (1) connectivity to the simulator
// (2) sending commands
// (3) receiving telemetry (battery, location, etc)

package edu.cmu.px4.mavsdk.case_study;

import io.mavsdk.System;
import java.util.ArrayList;

public class Snippets {
    // connect single drone
    public static System connectDefault() {
        System drone = new System("127.0.0.1", 50051);
        return drone;
    }

    // connect multiple drones
    public static ArrayList<System> connectAll(int numberOfDrones) {
        ArrayList<System> drones = new ArrayList<System>();
        for (int i = 0; i < numberOfDrones; i++) {
            System drone = new System("127.0.0.1", 50051 + i);
            drones.add(drone);
        }
        return drones;
    }


}

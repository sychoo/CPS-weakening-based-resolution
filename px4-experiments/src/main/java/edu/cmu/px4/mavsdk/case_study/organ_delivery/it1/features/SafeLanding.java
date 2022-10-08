package edu.cmu.px4.mavsdk.case_study.organ_delivery.it1.features;

import edu.cmu.px4.mavsdk.utils.Plan;
import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import edu.cmu.px4.mavsdk.utils.Feature;
import io.mavsdk.System;

public class SafeLanding extends Feature {

    public static double LANDING_THRESHOLD_WEAKENING = 0.0;
    public static double LANDING_THRESHOLD = 90.0;

    public SafeLanding() {
        super("Safe Landing");
    }

    @Override
    public Plan nextPlan(System drone) {
        float battery = DroneTelemetry.getRemainingBatteryPercent(drone);
        java.lang.System.out.println("[safe landing] remaining battery = " + battery);

        if (battery >= LANDING_THRESHOLD - LANDING_THRESHOLD_WEAKENING) {
            return new Plan("nop", null);
        }
        else {
            java.lang.System.out.println("[safe landing] Landing threshold (" + (LANDING_THRESHOLD - LANDING_THRESHOLD_WEAKENING) + ") reached. Force landing now!");
            return new Plan("land", null);
        }
    }

    @Override
    public void actuate(System drone, Plan plan) {
        if (plan.name().equals("land")) {
            DroneAction.land(drone);
        }
    }
}

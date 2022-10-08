package edu.cmu.px4.mavsdk.case_study.organ_delivery.it1.features;

import edu.cmu.px4.mavsdk.case_study.organ_delivery.it1.OrganDeliveryMain;
import edu.cmu.px4.mavsdk.utils.DroneAction;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import edu.cmu.px4.mavsdk.utils.Feature;
import edu.cmu.px4.mavsdk.utils.Plan;
import io.mavsdk.System;
import io.mavsdk.telemetry.Telemetry;

import java.util.ArrayList;;

public class DeliveryPlanning extends Feature {
    public double dest_lat; // latitude
    public double dest_lon; // longitude

    // pass in the destination latitude and longitude
    public DeliveryPlanning(double dest_lat, double dest_lon) {
        super("Delivery Planning");
        this.dest_lat = dest_lat;
        this.dest_lon = dest_lon;
    }

    @Override
    public Plan nextPlan(System drone) {
        // 1. get the current location of the drone
        // 2. get the location of the target

        Telemetry.Position pos = DroneTelemetry.getPosition(drone);
        double curr_lat = pos.getLatitudeDeg();
        double curr_lon = pos.getLongitudeDeg();

        // java.lang.System.out.println(curr_lat);
        // java.lang.System.out.println(curr_lon);

        java.lang.System.out
                .println("[delivery planning] distance to destination: "
                        + DroneTelemetry.distance(curr_lat, curr_lon, dest_lat, dest_lon, 0, 0));

        java.lang.System.out.println("[delivery planning] curr lat: " + curr_lat + " curr lon" + curr_lon);

        // land the drone if it is close to the destination (50 m)
        if (DroneTelemetry.distance(curr_lat, curr_lon, dest_lat, dest_lon, 0, 0) <= 50) {
            java.lang.System.out.println("[delivery planning] destination has reached. Landing drone now!");
            // DroneAction.land(drone);
            return new Plan("land", null);
        } else {
            return genVelocityNed(OrganDeliveryMain.SPEED, Double.valueOf(OrganDeliveryMain.REFRESH_RATE), curr_lat,
                    curr_lon, dest_lat, dest_lon);
        }
    }

    @Override
    public void actuate(System drone, Plan plan) {
        // double curr_lat = DroneTelemetry.getLatitudeDeg(drone);
        // double curr_lon = DroneTelemetry.getLongitudeDeg(drone);

        ArrayList<Double> argList = plan.parameters();
        // plan<int seconds, float vx, float vy, float vz, float yaw>
        if (plan.name().equals("fly")) {
            DroneAction.sendVelocityNed(drone, argList.get(0).intValue(), argList.get(1).floatValue(),
                    argList.get(2).floatValue(), argList.get(3).floatValue(), argList.get(4).floatValue());
        }
        else if (plan.name().equals("land")) {
            DroneAction.land(drone);
        }

        // double curr_lat = argList.get(5);
        // double curr_lon = argList.get(6);

    }

    public static Plan genVelocityNed(double speed, double refresh_rate, double curr_lat, double curr_lon,
            double dest_lat, double dest_lon) {
        // given the speed, return speed vector in Plan
        ArrayList<Double> argList = new ArrayList<Double>();
        argList.add(refresh_rate);

        // calculate the bearing between the current location and the destination
        double bearing = DroneTelemetry.bearing(curr_lat, curr_lon, dest_lat, dest_lon);
        double northSpeed = speed * Math.cos(Math.toRadians(bearing));
        double eastSpeed = speed * Math.sin(Math.toRadians(bearing));

        argList.add(northSpeed);
        argList.add(eastSpeed);
        argList.add(0.0);
        argList.add(0.0);
        argList.add(curr_lat);
        argList.add(curr_lon);

        Plan plan = new Plan("fly", argList);
        return plan;
    }
}
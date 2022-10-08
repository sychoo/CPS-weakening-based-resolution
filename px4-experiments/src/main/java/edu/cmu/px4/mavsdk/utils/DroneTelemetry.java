// bizzare behavior, swing back and forth instead of flying 8

package edu.cmu.px4.mavsdk.utils;

import io.mavsdk.System;
import io.mavsdk.action.Action;
import io.mavsdk.action_server.ActionServer;
import edu.cmu.px4.mavsdk.utils.Plan;
import edu.cmu.px4.mavsdk.utils.Feature;
// import edu.cmu.px4.mavsdk.case_study.organ_delivery.it1.features.DeliveryPlanning;
// import edu.cmu.px4.mavsdk.case_study.organ_delivery.it1.features.SafeLanding;
import io.mavsdk.mission.Mission;
import io.mavsdk.offboard.Offboard;
import io.mavsdk.telemetry.Telemetry;
import io.reactivex.Completable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class DroneTelemetry {
    public static double degreeToRadians(double degree) {
        return degree * Math.PI / 180;
    }

    public static double radiansToDegree(double radians) {
        return radians * 180 / Math.PI;
    }

    public static Telemetry.LandedState getLandedState(System drone) {
        return drone.getTelemetry().getLandedState().blockingFirst();
    }

    // get the current heading
    public static double getHeadingDeg(System drone) {
        return drone.getTelemetry().getHeading().blockingFirst().getHeadingDeg();
    }

    // depends on getLandedState
    public static boolean isLanded(System drone) {
        return getLandedState(drone) == Telemetry.LandedState.ON_GROUND;
    }

    public static boolean isLanding(System drone) {
        return getLandedState(drone) == Telemetry.LandedState.LANDING;
    }

    public static Telemetry.Position getPosition(System drone) {
        return drone.getTelemetry().getPosition().blockingFirst();
    }

    // returns [Lat, Lon, Alt]
    public static ArrayList<Double> getPositionList(System drone) {
        Telemetry.Position pos = getPosition(drone);
        ArrayList<Double> positionList = new ArrayList<Double>();
        positionList.add(pos.getLatitudeDeg());
        positionList.add(pos.getLongitudeDeg());
        positionList.add(Double.valueOf(pos.getRelativeAltitudeM()));
        return positionList;
    }

    // depends on getPosition
    public static Double getLatitudeDeg(System drone) {
        return getPosition(drone).getLatitudeDeg();
    }

    // depends on getPosition
    public static Double getLongitudeDeg(System drone) {
        return getPosition(drone).getLongitudeDeg();
    }

    public static Float getAltitude(System drone) {
        return getPosition(drone).getRelativeAltitudeM();
    }

    public static Telemetry.PositionVelocityNed getPositionVelocityNed(System drone) {
        return drone.getTelemetry().getPositionVelocityNed().blockingFirst();
    }

    public static Telemetry.EulerAngle getEulerAngle(System drone) {
        return drone.getTelemetry().getAttitudeEuler().blockingFirst();
    }

    public static Float getRemainingBatteryPercent(System drone) {
        return drone.getTelemetry().getBattery().blockingFirst().getRemainingPercent() * 100;
    }

    // get the relative position of the drone 2 with respect to drone 1
    public static Telemetry.PositionNed getRelativeDronePosition(System drone1, System drone2) {
        // pull the (lat, lon, alt) for drone 1 and drone 2
        Telemetry.Position drone1_pos = drone1.getTelemetry().getPosition().blockingFirst();
        Telemetry.Position drone2_pos = drone2.getTelemetry().getPosition().blockingFirst();

        Double drone1_lat = drone1_pos.getLatitudeDeg();
        Double drone1_lon = drone1_pos.getLongitudeDeg();
        Float drone1_alt = drone1_pos.getRelativeAltitudeM();

        Double drone2_lat = drone2_pos.getLatitudeDeg();
        Double drone2_lon = drone2_pos.getLongitudeDeg();
        Float drone2_alt = drone2_pos.getRelativeAltitudeM();

        // get relative North and East position (NORTH, EAST)
        ArrayList<Double> relative_pos = relativePosition(drone1_lat, drone1_lon, drone2_lat, drone2_lon);
        Double east_pos = relative_pos.get(0);
        Double north_pos = relative_pos.get(1);

        // get relative altitude difference (DOWN)
        Float relative_alt = drone1_alt - drone2_alt;

        return new Telemetry.PositionNed(new Float(north_pos), new Float(east_pos), relative_alt);
    }

    // get the relative position of the drone 2 with respect to drone 1
    public static Telemetry.PositionNed getRelativeDroneDestPosition(System drone, double dest_lat, double dest_lon, double dest_alt) {
        // pull the (lat, lon, alt) for drone 1 and drone 2
        Telemetry.Position drone_pos = drone.getTelemetry().getPosition().blockingFirst();

        Double drone_lat = drone_pos.getLatitudeDeg();
        Double drone_lon = drone_pos.getLongitudeDeg();
        Float drone_alt = drone_pos.getRelativeAltitudeM();

        // get relative North and East position (NORTH, EAST)
        ArrayList<Double> relative_pos = relativePosition3D(drone_lat, drone_lon, dest_lat, dest_lon, drone_alt, dest_alt);
        Double east_pos = relative_pos.get(0);
        Double north_pos = relative_pos.get(1);
        Double down_pos = relative_pos.get(2);

        return new Telemetry.PositionNed(new Float(north_pos), new Float(east_pos), new Float(down_pos));
    }
    // public static boolean isFailSafeMode(System drone) {
    //     return getFlightMode(drone) == ActionServer.FlightMode.
    // }

    public static ActionServer.FlightMode getFlightMode(System drone) {
        return drone.getActionServer().getFlightModeChange().blockingFirst();
    }

    // https://stackoverflow.com/questions/3932502/calculate-angle-between-two-latitude-longitude-points
    // angle returned is in degrees (not radians)
    // absolute bearing: clockwise degree from north
    // i.e. 90 degrees bearing = due east
    // public static double angleFromCoordinate(double lat1, double long1, double
    // lat2,
    // double long2) {

    // double dLon = (long2 - long1);

    // double y = Math.sin(dLon) * Math.cos(lat2);
    // double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
    // * Math.cos(lat2) * Math.cos(dLon);

    // double brng = Math.atan2(y, x);

    // brng = Math.toDegrees(brng);
    // brng = (brng + 360) % 360;
    // brng = 360 - brng; // count degrees counter-clockwise - remove to make
    // clockwise

    // return brng;
    // }
    // finding distance between two coordinates
    // https://dzone.com/articles/distance-calculation-using-3

    // calculate new coordinates with a given distance and bearing
    // https://stackoverflow.com/a/53329672

    // TODO: implement distanceBetween2coord and given bearing and distance, return
    // the new coordinates

    // https://stackoverflow.com/questions/9457988/bearing-from-one-coordinate-to-another
    // receive latitude/longitude in degrees, output in degrees, intermediate
    // representation in radians
    // bearing counts from North, clockwise
    public static double bearing(double lat1, double lon1, double lat2, double lon2) {
        double longitude1 = lon1;
        double longitude2 = lon2;
        double latitude1 = Math.toRadians(lat1);
        double latitude2 = Math.toRadians(lat2);
        double longDiff = Math.toRadians(longitude2 - longitude1);
        double y = Math.sin(longDiff) * Math.cos(latitude2);
        double x = Math.cos(latitude1) * Math.sin(latitude2)
                - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff);

        return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
    }

    // calculate the distance between a drone and a destination
    public static double distanceBetweenDroneAndDest(System drone, double dest_lat, double dest_lon) {
        Telemetry.Position drone_pos = DroneTelemetry.getPosition(drone);
        double drone_lat = drone_pos.getLatitudeDeg();
        double drone_lon = drone_pos.getLongitudeDeg();

        return distance(drone_lat, drone_lon, dest_lat, dest_lon, 0, 0);
    }


    // calculate the distance bewteen a drone and a destination (3D)
    public static double distanceBetweenDroneAndDest(System drone, double dest_lat, double dest_lon, double dest_alt) {
        Telemetry.Position drone_pos = DroneTelemetry.getPosition(drone);
        double drone_lat = drone_pos.getLatitudeDeg();
        double drone_lon = drone_pos.getLongitudeDeg();
        double drone_alt = drone_pos.getRelativeAltitudeM();

        return distance(drone_lat, drone_lon, dest_lat, dest_lon, drone_alt, dest_alt);
    }

   public static double altDiffBetweenDroneAndDest(System drone, double dest_alt) {
        double drone_alt = getAltitude(drone);
        return Math.abs(drone_alt - dest_alt);
   }

    // calculate the distance between two drones
    // need to be pull twice to get the latest position
    public static double distanceBetweenDrones(System drone1, System drone2) {
        // pull the current location of both drones
        Telemetry.Position drone1_pos = DroneTelemetry.getPosition(drone1);
        Telemetry.Position drone2_pos = DroneTelemetry.getPosition(drone2);

        // extract the laitude and longitude of both drones
        double drone1_lat = drone1_pos.getLatitudeDeg();
        double drone1_lon = drone1_pos.getLongitudeDeg();
        double drone1_alt = drone1_pos.getRelativeAltitudeM();

        double drone2_lat = drone2_pos.getLatitudeDeg();
        double drone2_lon = drone2_pos.getLongitudeDeg();
        double drone2_alt = drone2_pos.getRelativeAltitudeM();

        return distance(drone1_lat, drone1_lon, drone2_lat, drone2_lon, drone1_alt, drone2_alt);
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * 
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * 
     * @returns Distance in Meters
     */
    // https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude
    public static double distance(double lat1, double lon1, double lat2,
            double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        double height = el1 - el2;
        distance = Math.pow(distance, 2) + Math.pow(height, 2);
        return Math.sqrt(distance);
    }

    // given coord 1 and coord 2, find the relative position of coord 2 with respect to coord 1
    // in a cartesian plane
    // coord 1 --> coord 2
    public static ArrayList<Double> relativePosition(double lat1, double lon1, double lat2,
            double lon2) {
        double distanceValue = distance(lat1, lon1, lat2, lon2, 0, 0);
        double bearingValue = bearing(lat1, lon1, lat2, lon2);

        double northPos = distanceValue * Math.cos(Math.toRadians(bearingValue));
        double eastPos = distanceValue * Math.sin(Math.toRadians(bearingValue));

        ArrayList<Double> result = new ArrayList<Double>() {{
            add(eastPos);
            add(northPos);
        }};

        return result;
    }


    public static ArrayList<Double> relativePosition3D(double lat1, double lon1, double lat2,
            double lon2, double alt1, double alt2) {
        double distanceValue = distance(lat1, lon1, lat2, lon2, 0, 0);
        double bearingValue = bearing(lat1, lon1, lat2, lon2);

        double northPos = distanceValue * Math.cos(Math.toRadians(bearingValue));
        double eastPos = distanceValue * Math.sin(Math.toRadians(bearingValue));
        double downPos = alt1 - alt2;

        ArrayList<Double> result = new ArrayList<Double>() {{
            add(eastPos);
            add(northPos);
            add(downPos);
        }};

        return result;
    }


    // get the degree-based angle based on (x, y)
    public static double getAngle(double deltaX, double deltaY){
        double deg = Math.atan(deltaY / deltaX) * 180.0 / Math.PI;
        
        // for clock-wise 0-north angles
        if (deltaX < 0) {
          deg += 180;
        } else if (deltaY < 0) {
          deg += 360;
        }
        
        return deg;
      }

     public static double fmod(double a, double b) {
        int result = (int) Math.floor(a / b);
        return a - result * b;
    }

    // given the original latitude and longitude, and the offset in terms of north and east (in meters)
    // return the new pair of latitude and longitude
    // https://gis.stackexchange.com/questions/2951/algorithm-for-offsetting-a-latitude-longitude-by-some-amount-of-meters
    public static ArrayList<Double> offsetLatLon(double lat, double lon, double displace_north, double displace_east) {

        // Earth’s radius, sphere
        int R = 6378137;

        double displace_lat = displace_north/R;
        double displace_lon = displace_east/(R * Math.cos(Math.PI * lat / 180));
        
        double new_lat = lat + displace_lat * 180 / Math.PI;
        double new_lon = lon + displace_lon * 180 / Math.PI;

        return new ArrayList<Double>() {{
            add(new_lat);
            add(new_lon);
        }};
    }

    // combining both functions
    public static ArrayList<Double> horizontalVerticalDistanceBetweenDroneDest(System drone, double dest_lat, double dest_lon) {
        Telemetry.Position pos = DroneTelemetry.getPosition(drone);
        double curr_lat = pos.getLatitudeDeg();
        double curr_lon = pos.getLongitudeDeg();

        return new ArrayList<Double>() {{
            add(DroneTelemetry.horizontalDistance(curr_lat, curr_lon, dest_lat, dest_lon));
            add(DroneTelemetry.verticalDistance(curr_lat, curr_lon, dest_lat, dest_lon));
        }};
    }

    public static double horizontalDistanceBetweenDroneDest(System drone, double dest_lat, double dest_lon) {
        Telemetry.Position pos = DroneTelemetry.getPosition(drone);
        double curr_lat = pos.getLatitudeDeg();
        double curr_lon = pos.getLongitudeDeg();

        return DroneTelemetry.horizontalDistance(curr_lat, curr_lon, dest_lat, dest_lon);
    }

    public static double verticalDistanceBetweenDroneDest(System drone, double dest_lat, double dest_lon) {
        Telemetry.Position pos = DroneTelemetry.getPosition(drone);
        double curr_lat = pos.getLatitudeDeg();
        double curr_lon = pos.getLongitudeDeg();

        return DroneTelemetry.verticalDistance(curr_lat, curr_lon, dest_lat, dest_lon);
    }

    public static double verticalDistance(double lat1, double lon1, double lat2, double lon2) {
        double distance = DroneTelemetry.distance(lat1, lon1, lat2, lon2, 0, 0);

        // calculate the bearing between the current location and the destination
        double bearing = DroneTelemetry.bearing(lat1, lon1, lat2, lon2);
        double northDistance = distance * Math.cos(Math.toRadians(bearing));
        
        return Math.abs(northDistance);
    }

    // horizontal - east/west
    public static double horizontalDistance(double lat1, double lon1, double lat2, double lon2) {
        double distance = DroneTelemetry.distance(lat1, lon1, lat2, lon2, 0, 0);

        // calculate the bearing between the current location and the destination
        double bearing = DroneTelemetry.bearing(lat1, lon1, lat2, lon2);
        double eastDistance = distance * Math.sin(Math.toRadians(bearing));
        
        return Math.abs(eastDistance);
    }
}

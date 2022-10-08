package edu.cmu.px4.mavsdk;


import edu.cmu.px4.mavsdk.examples.GetTelemetry;
import edu.cmu.px4.mavsdk.examples.GettingStarted;
import edu.cmu.px4.mavsdk.examples.RunMission;
import edu.cmu.px4.mavsdk.examples.TakeoffAndLand;
import io.mavsdk.offboard.Offboard;
import edu.cmu.px4.mavsdk.examples.MissionOffboardExample;
import edu.cmu.px4.mavsdk.examples.OffboardExample;
import edu.cmu.px4.mavsdk.examples.ChangeHeadingVelocityNed;
import edu.cmu.px4.mavsdk.examples.ForceLanding;
import edu.cmu.px4.mavsdk.examples.SetRtlAltitude;

public class Simulator {
    public static void main(String[] args) {
        System.out.println("Starting Simulator...");
        // TakeoffAndLand.run();
        // RunMission.run();
        // GetTelemetry.run();
        // MissionOffboardExample.run();
        OffboardExample.run();
        // ChangeHeadingVelocityNed.run();
        // ForceLanding.run();
        // SetRtlAltitude.run();
        // GettingStarted.run();
    }
}

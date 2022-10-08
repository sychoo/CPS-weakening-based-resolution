package edu.cmu.px4.utils;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import java.util.ArrayList;

/**
 * Unit test for simple App.
 */
public class DroneTelemetryTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void relativePositionTest()
    {
         // tcs
         double lat1 = 40.4446815421205;
         double lon1 = -79.94543858197448;
 
         // the cut
         double lat2 = 40.444139;
         double lon2 = -79.942639;
 
         ArrayList<Double> relativePosition = DroneTelemetry.relativePosition(lat1, lon1, lat2, lon2);
         assertTrue(relativePosition.get(0) == 236.91085157724038);
         assertTrue(relativePosition.get(1) == -60.324176576299244);
    }
}
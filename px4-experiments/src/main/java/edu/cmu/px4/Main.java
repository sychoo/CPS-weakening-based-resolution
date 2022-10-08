package edu.cmu.px4;

import edu.cmu.stl.encoder.floats.STLEncoder;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.Params;

import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        // destination : the cut
        double lat = 40.444139;
        double lon = -79.942639;
        System.out.println(DroneTelemetry.offsetLatLon(lat, lon, 100, -100));

        Params p = new Params();
        System.out.println(p.declareMiniZincParams());
    }
}

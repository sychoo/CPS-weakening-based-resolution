package edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.resolver;

import edu.cmu.stl.encoder.floats.STLEncoder;
import edu.cmu.stl.encoder.floats.util.Result;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.Params;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.drone.EgoDrone;
import edu.cmu.px4.mavsdk.utils.DroneTelemetry;
import java.util.ArrayList;
import edu.cmu.stl.encoder.floats.util.FileOperation;
import edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.monitor.Monitor;


public class Resolver {

    public static String testDir = "src/main/java/edu/cmu/px4/mavsdk/case_study/boundary_runaway/main/model/";
    public static STLEncoder encoder = null;

    public static String gatherParams(double ego_lat, double ego_lon, double ego_alt, double follower_lat, double follower_lon, double follower_alt) {
        StringBuilder params = new StringBuilder();
        /*****************************/
        /***** static parameters *****/
        /*****************************/

        // FOLLOWER_DRONE_MAX_SPEED
        // EGO_AVOID_SPEED
        // REFRESH_RATE
        Params p = new Params();
        params.append(p.declareMiniZincParams());

        /******************************/
        /***** runtime parameters *****/
        /******************************/

        // -ego_alt since z is pointing downwards

        // get current location
        params.append(genInitLoc("ego_loc", 0.0, 0.0, -ego_alt));
        params.append(genInitLoc("follower_perceive_ego_loc", 0.0, 0.0, -ego_alt));

        ArrayList<Double> followerDronePos = DroneTelemetry.relativePosition(ego_lat, ego_lon, follower_lat, follower_lon);
        params.append(genInitLoc("follower_loc", followerDronePos.get(0), followerDronePos.get(1), -follower_alt));

        // get previous ego drone velocity vector
        ArrayList<Double> prevEgoCommand = EgoDrone.prev_ego_command;
        params.append(genPrevEgoCommand(prevEgoCommand));

        // generate the boundary limits
        params.append(genBoundaryLimits(ego_lat, ego_lon));
        return params.toString();
    }

    // float: BOUND_UPPER_X = 8.0;
    // float: BOUND_LOWER_X = -35.0;
    // float: BOUND_UPPER_Y = 8.0;
    // float: BOUND_LOWER_Y = -35.0;
    public static String genBoundaryLimits(double ego_lat, double ego_lon) {
        // boundary center -> ego drone
        ArrayList<Double> relative_pos = DroneTelemetry.relativePosition(Params.BOUND_CTR_LAT, Params.BOUND_CTR_LON, ego_lat, ego_lon);
        double half_side = 0.5 * Params.BOUND_SQUARE_SIDE;
        double bound_upper_x = half_side - relative_pos.get(0); // x-pos
        double bound_lower_x = -(half_side + relative_pos.get(0)); // x-neg
        double bound_upper_y = half_side - relative_pos.get(1); // y-pos
        double bound_lower_y = -(half_side + relative_pos.get(1)); // y-neg

        return "float: BOUND_UPPER_X = " + bound_upper_x + ";\n" +
               "float: BOUND_LOWER_X = " + bound_lower_x + ";\n" +
               "float: BOUND_UPPER_Y = " + bound_upper_y + ";\n" +
               "float: BOUND_LOWER_Y = " + bound_lower_y + ";\n";
    }

    // float: PREV_EGO_VEL_X = -6.0;
    // float: PREV_EGO_VEL_Y = 0.0;
    // float: PREV_EGO_VEL_Z = 0.0;
    public static String genPrevEgoCommand(ArrayList<Double> prevEgoCommand) {
        return "float: PREV_EGO_VEL_X = " + prevEgoCommand.get(0) + ";\n" +
               "float: PREV_EGO_VEL_Y = " + prevEgoCommand.get(1) + ";\n" +
               "float: PREV_EGO_VEL_Z = " + prevEgoCommand.get(2) + ";\n";
    }

    // generate the initial location of minizinc
    public static String genInitLoc(String var, double x, double y, double z) {
        return "constraint " + var + "_x[1] = " + x + ";\n" +
               "constraint " + var + "_y[1] = " + y + ";\n" +
               "constraint " + var + "_z[1] = " + z + ";\n";
    }

    public static void initializeSTLEncoder() {
            // String stl = testDir + "runbound.stl";
            String stl = Params.MAIN_STL_FILE_FOR_RESOLVER;
            String env = testDir + "updated_main_model.mzn";
            encoder = new STLEncoder(stl, env);

            // initialize the monitor as well
            Monitor.stlRequirement = FileOperation.readFile(stl);
    }

    // run the resolver
    public static ArrayList<Double> run(double ego_lat, double ego_lon, double ego_alt, double follower_lat, double follower_lon, double follower_alt) {
        if (encoder == null) {
            initializeSTLEncoder();
        }

        // calculate the relative coordinates between the ego drone and the follower drone
        String params = gatherParams(ego_lat, ego_lon, ego_alt, follower_lat, follower_lon, follower_alt);

        // TODO: set up the STLEncoder files and run the solver
        Result r = encoder.run(params);

        if (r.isSat()) {
            double ego_vel_x = Result.parseFloatArray(r.get("ego_vel_x")).get(0);
            double ego_vel_y = Result.parseFloatArray(r.get("ego_vel_y")).get(0);
            double ego_vel_z = Result.parseFloatArray(r.get("ego_vel_z")).get(0);

            // append the weakening paramete to the monitor
            // delta_bound and delta_runaway
            Monitor.boundary_requirement_weakening_param_list.add(Double.valueOf(r.get("delta_bound")));
            Monitor.runaway_requirement_weakening_param_list.add(Double.valueOf(r.get("delta_runaway")));

        
            return new ArrayList<Double>() {{
                add(ego_vel_x);
                add(ego_vel_y);
                add(ego_vel_z);
            }};
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        // [[40.445131578821034, -79.94760109847633], [40.445131578821034, -79.94701090152367], [40.44468242117897, -79.94701090152367], [40.44468242117897, -79.94760109847633]]

        System.out.println(gatherParams(40.445131578821034, -79.94760109847633, 10, 40.445131578821034, -79.94701090152367, 10));
        System.out.println(run(40.445131578821034, -79.94760109847633, 10, 40.445131578821034, -79.94701090152367, 10));
    }
}

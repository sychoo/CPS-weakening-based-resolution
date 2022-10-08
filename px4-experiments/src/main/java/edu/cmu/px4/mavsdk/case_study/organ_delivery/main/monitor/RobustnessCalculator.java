package edu.cmu.px4.mavsdk.case_study.organ_delivery.main.monitor;

import edu.cmu.stl.encoder.floats.STLEncoder;
// Thu Sep 22 21:32:29 EDT 2022
// calculate the robustness of the system
import edu.cmu.stl.encoder.floats.util.Signal;
import java.util.ArrayList;
import edu.cmu.stl.encoder.floats.util.Result;
import edu.cmu.px4.mavsdk.case_study.organ_delivery.main.Params;

public class RobustnessCalculator {

        // given the number of enforcers activated and the overall robustness array,
    // extract the robustness array of the system
    // between when both features are activated and when both features are
    // deactivated
    public static ArrayList<Double> extractFeatureActivationRobustnessList(ArrayList<Double> robustnessList,
            ArrayList<Double> numEnforcersActivated) {
        ArrayList<Double> newRobustnessList = new ArrayList<Double>();

        // choose the minimal size of two list to make the cut from the overall
        // robustness list
        int minListSize = Math.min(robustnessList.size(), numEnforcersActivated.size());

        boolean start = false; // signal to start counting

        // loop through both arrays
        for (int i = 0; i < minListSize; i++) {
            if (numEnforcersActivated.get(i) == 2) {
                start = true;
            }

            // if both features are de-activated, stop counting
            if (numEnforcersActivated.get(i) == 0) {
                start = false;
            }

            // if both features are activated, start counting
            if (start) {
                newRobustnessList.add(robustnessList.get(i));
            }
        }
        return newRobustnessList;
    }

    // calculate the delivery planning robustness
    // G[0,1](remaining_range_upon_delivery >= 0)
    // requires
    // - distance_to_destination
    // - current battery level
    // // - BATTERY DRAIN RATE
    // // - speed
    // // depending on if the drone is currently landing, the estimated range upon delivery changes (can be negative when it is deemed impossible. -the distance to the destination)
    // public static ArrayList<Double> deliveryPlanningRobustnessListOld(ArrayList<Double> batteryLevelList, ArrayList<Double> isLandingList, ArrayList<Double> distanceToDestinationList, ArrayList<Double> speedList, ArrayList<Double> timeLeftList) {
    //     ArrayList<Double> remaining_range_upon_delivery_list = new ArrayList<Double>();

    //     int stlSignalLength = Params.DELIVERYPLANNING_STL_ORIGINAL_REQUIREMENT_SIGNAL_LENGTH;
    //     String signalVariableName = "remaining_range_upon_delivery";
    //     String stlFilePath = Params.DELIVERYPLANNING_STL_ORIGINAL_REQUIREMENT_FILE_PATH;

    //     // calculate the remaining range upon delivery (signal variable)
    //     for (int i = 0; i < batteryLevelList.size(); i++) {
    //         double remaining_range_upon_delivery = 0.0;
    //         // has yet to be landed
    //         if (isLandingList.get(i) == 0.0) {
    //             remaining_range_upon_delivery = (batteryLevelList.get(i) / Params.BATTERY_DRAIN_RATE) * Params.SPEED - distanceToDestinationList.get(i);
    //         } 

    //         // when the drone is landing, negate the distance to destination
    //         else {
    //             remaining_range_upon_delivery = - distanceToDestinationList.get(i);
    //         }
    //         remaining_range_upon_delivery_list.add(remaining_range_upon_delivery);

    //     }
    //     return robustnessList(signalVariableName, stlSignalLength, stlFilePath, remaining_range_upon_delivery_list);
    // }




       // depending on if the drone is currently landing, the estimated range upon delivery changes (can be negative when it is deemed impossible. -the distance to the destination)
       public static ArrayList<Double> deliveryPlanningRobustnessList() {//ArrayList<Double> batteryLevelList, ArrayList<Double> isLandingList, ArrayList<Double> distanceToDestinationList, ArrayList<Double> speedList, ArrayList<Double> missionTimeLeftList) {
        // ArrayList<Double> remaining_range_upon_delivery_list = new ArrayList<Double>();

        int stlSignalLength = Params.DELIVERYPLANNING_STL_ORIGINAL_REQUIREMENT_SIGNAL_LENGTH;
        String signalVariableName1 = "speed";
        String signalVariableName2 = "mission_time_left";
        String signalVariableName3 = "distance_to_hospital";

        String stlFilePath = Params.DELIVERYPLANNING_STL_ORIGINAL_REQUIREMENT_FILE_PATH;

        // // calculate the remaining range upon delivery (signal variable)
        // for (int i = 0; i < batteryLevelList.size(); i++) {
        //     double remaining_range_upon_delivery = 0.0;
        //     // has yet to be landed
        //     if (isLandingList.get(i) == 0.0) {
        //         remaining_range_upon_delivery = (batteryLevelList.get(i) / Params.BATTERY_DRAIN_RATE) * Params.SPEED - distanceToDestinationList.get(i);
        //     } 

        //     // when the drone is landing, negate the distance to destination
        //     else {
        //         remaining_range_upon_delivery = - distanceToDestinationList.get(i);
        //     }
        //     remaining_range_upon_delivery_list.add(remaining_range_upon_delivery);

        // }
        return robustnessList_three_signal_var(signalVariableName1, signalVariableName2, signalVariableName3, stlSignalLength, stlFilePath, Monitor.speedList, Monitor.missionTimeLeftList, Monitor.distanceToDestinationList);
    }

    // calculate the safe landing robustness
    // G[0,1]((<<battery <= 40>>(delta_battery, 20.0)) -> (F[0,1](is_landing = 1)))
    // requires
    // - current battery level
    // - whether the drone is landing [false, false, false , , true]
    public static ArrayList<Double> safeLandingRobustnessList() {//ArrayList<Double> batteryLevelList, ArrayList<Double> isLandingList, ArrayList<Double> distanceToDestinationList) {

        int stlSignalLength = Params.SAFELANDING_STL_ORIGINAL_REQUIREMENT_SIGNAL_LENGTH;
        String signalVariableName1 = "battery";
        String signalVariableName2 = "is_landing";
        String stlFilePath = Params.SAFELANDING_STL_ORIGINAL_REQUIREMENT_FILE_PATH;

        return robustnessList_two_signal_var(signalVariableName1, signalVariableName2, stlSignalLength, stlFilePath, Monitor.batteryLevelList, Monitor.isLandingList);
    }

    // calculate the robustness of the system for a single requirement
    // preferably, minimum robustness, maximum robustness, average robustness and
    // cumulative robustness
    // signalVariableName: the name of the signal variable
    // signalValues: the values of the signal variable
    // return: the robustness of the system
    public static ArrayList<Double> robustnessList(String signalVariableName, int stlSignalLength, String stlFilePath,
            ArrayList<Double> signalValues) {
        ArrayList<Double> result = new ArrayList<Double>();

        // conver the arraylist of signal values (collected from Monitor) to a signal
        // sample calculation of robustness
        ArrayList<String> fieldName = new ArrayList<String>() {
            {
                add(signalVariableName);
            }
        };


        // shift the window to the right by 1 every time
        // to calculate the robustness at each time step
        for (int j = 0; j < signalValues.size() - stlSignalLength; j++) {
            Signal s = new Signal(fieldName); // re-initialize the signal every time
            for (int i = 0; i < stlSignalLength; i++) {
                ArrayList<Double> value = new ArrayList<Double>();
                value.add(signalValues.get(j + i));
                s.append(value);
            }

            STLEncoder e = new STLEncoder(stlFilePath, s);
            Result r = e.run("");
            Double d = 0.0;
            try {
                d = Double.valueOf(r.get("rho_stl_1"));
                result.add(d);
            } catch (Exception e1) {
                System.out.println("Error in robustness calculation");
                System.out.println("*********** ERROR ***********");
                System.out.println(e1);
                System.out.println("*********** /ERROR ***********");
                // not the most ideal solution but can add 0 if there is an error?
                result.add(0.0);
            }
        }

        System.out.println("robustness calculation complete for " + signalVariableName);
        return result;
    }





    // calculate the robustness of the system for a single requirement
    // preferably, minimum robustness, maximum robustness, average robustness and
    // cumulative robustness
    // signalVariableName: the name of the signal variable
    // signalValues: the values of the signal variable
    // return: the robustness of the system

    // calculate robustness with two signal variables 
    public static ArrayList<Double> robustnessList_two_signal_var(String signalVariableName1, String signalVariableName2, int stlSignalLength, String stlFilePath,
            ArrayList<Double> signalValues1, ArrayList<Double> signalValues2) {
        ArrayList<Double> result = new ArrayList<Double>();

        // conver the arraylist of signal values (collected from Monitor) to a signal
        // sample calculation of robustness
        ArrayList<String> fieldName = new ArrayList<String>() {
            {
                add(signalVariableName1);
                add(signalVariableName2);
            }
        };

        // shift the window to the right by 1 every time
        // to calculate the robustness at each time step
        for (int j = 0; j < signalValues1.size() - stlSignalLength; j++) {
            Signal s = new Signal(fieldName); // re-initialize the signal every time
            for (int i = 0; i < stlSignalLength; i++) {
                ArrayList<Double> value = new ArrayList<Double>();
                value.add(signalValues1.get(j + i));
                value.add(signalValues2.get(j + i));
                s.append(value);
            }

            STLEncoder e = new STLEncoder(stlFilePath, s);
            Result r = e.run("");
            Double d = 0.0;
            try {
                d = Double.valueOf(r.get("rho_stl_1"));
                result.add(d);
            } catch (Exception e1) {
                System.out.println("Error in robustness calculation");
                System.out.println("*********** ERROR ***********");
                System.out.println(e1);
                System.out.println("*********** /ERROR ***********");

            }
        }

        System.out.println("robustness calculation complete for " + signalVariableName1 + " & " + signalVariableName2);
        return result;
    }




     // calculate the robustness of the system for a single requirement
    // preferably, minimum robustness, maximum robustness, average robustness and
    // cumulative robustness
    // signalVariableName: the name of the signal variable
    // signalValues: the values of the signal variable
    // return: the robustness of the system

    // calculate robustness with two signal variables 
    public static ArrayList<Double> robustnessList_three_signal_var(String signalVariableName1, String signalVariableName2, String signalVariableName3, int stlSignalLength, String stlFilePath,
            ArrayList<Double> signalValues1, ArrayList<Double> signalValues2, ArrayList<Double> signalValues3) {
                System.out.println("speed: " + signalValues1);
                System.out.println("mission_time_left: " + signalValues2);
                System.out.println("distance_to_hospital: " + signalValues3);

        ArrayList<Double> result = new ArrayList<Double>();

        // conver the arraylist of signal values (collected from Monitor) to a signal
        // sample calculation of robustness
        ArrayList<String> fieldName = new ArrayList<String>() {
            {
                add(signalVariableName1);
                add(signalVariableName2);
                add(signalVariableName3);
            }
        };

        // shift the window to the right by 1 every time
        // to calculate the robustness at each time step
        for (int j = 0; j < signalValues1.size() - stlSignalLength; j++) {
            Signal s = new Signal(fieldName); // re-initialize the signal every time
            for (int i = 0; i < stlSignalLength; i++) {
                ArrayList<Double> value = new ArrayList<Double>();
                value.add(signalValues1.get(j + i));
                value.add(signalValues2.get(j + i));
                value.add(signalValues3.get(j + i));
                s.append(value);
            }

            STLEncoder e = new STLEncoder(stlFilePath, s);

            // System.out.println(s); // print the signal
            
            Result r = e.run("");
            Double d = 0.0;
            try {
                d = Double.valueOf(r.get("rho_stl_1"));
                result.add(d);
            } catch (Exception e1) {
                System.out.println("Error in robustness calculation");
                System.out.println("*********** ERROR ***********");
                System.out.println(e1);
                System.out.println("*********** /ERROR ***********");

            }
        }

        System.out.println("robustness calculation complete for " + signalVariableName1 + " & " + signalVariableName2 + " & " + signalVariableName3);
        return result;
    }

    public static void exampleCalculation() {
        // sample calculation of robustness (without the use of API)
        ArrayList<String> fieldName = new ArrayList<String>();
        fieldName.add("distance2bound");
        Signal s = new Signal(fieldName);

        // signal is stored in a weird way, to calculate robustness, it has to create a
        // single array list for each data point.
        ArrayList<Double> fieldValue = new ArrayList<Double>();
        fieldValue.add(1.0);

        ArrayList<Double> fieldValue2 = new ArrayList<Double>() {
            {
                add(2.0);
            }
        };

        s.append(fieldValue);
        s.append(fieldValue2);
        String testDir = "src/main/java/edu/cmu/px4/mavsdk/case_study/boundary_runaway/main/model/";

        // STL requirements
        String stl = testDir + "bound_original.stl";

        STLEncoder e = new STLEncoder(stl, s);
        Result r = e.run("");

        System.out.println(r);
        System.out.println(r.get("rho_stl_1"));
    }

    public static void exampleAPICall() {
        String signalVariableName = "distance2bound";
        int stlSignalLength = 2;
        String stlFilePath = "src/main/java/edu/cmu/px4/mavsdk/case_study/boundary_runaway/main/model/bound_original.stl";
        ArrayList<Double> signalValues = new ArrayList<Double>() {{
            add(10.0);
            add(9.0);
            add(8.0);
            add(7.0);
            add(6.0);
            add(5.0);
            add(4.0);
            add(3.0);
            add(2.0);
            add(1.0);
            add(0.0);
        }};
        ArrayList<Double> result = robustnessList(signalVariableName, stlSignalLength, stlFilePath, signalValues);
        System.out.println(result);
    }

    public static void main(String[] args) {
        // exampleAPICall();

    }
}

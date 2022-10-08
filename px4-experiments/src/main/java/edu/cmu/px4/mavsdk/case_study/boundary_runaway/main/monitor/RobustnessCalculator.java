package edu.cmu.px4.mavsdk.case_study.boundary_runaway.main.monitor;

import edu.cmu.stl.encoder.floats.STLEncoder;
// Thu Sep 22 21:32:29 EDT 2022
// calculate the robustness of the system
import edu.cmu.stl.encoder.floats.util.Signal;
import java.util.ArrayList;
import edu.cmu.stl.encoder.floats.util.Result;

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

            }
        }

        System.out.println("robustness calculation complete for " + signalVariableName);
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
        ArrayList<Double> signalValues = new ArrayList<Double>() {
            {
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
            }
        };
        ArrayList<Double> result = robustnessList(signalVariableName, stlSignalLength, stlFilePath, signalValues);
        System.out.println(result);
    }

    public static void main(String[] args) {
        exampleAPICall();
    }
}

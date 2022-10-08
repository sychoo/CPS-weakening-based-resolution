// Sat Feb 19 10:34:32 EST 2022
// Parses and stores the result from the MiniZinc commandline solver
// Simon Chu
//
// Note that result will not convert the value of the JSON formatted result
// to their corresponding data types
package edu.cmu.stl.encoder.floats.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

import org.json.JSONObject;

public class Result {
    private String rawResultString;
    private Boolean isSat;
    private Map<String, String> resultMap;

    public Result(String str) {
        // copy obtained str to rawResultString
        this.rawResultString = str;

        // initialize result map
        this.resultMap = new HashMap<String, String>();

        // by default isSat = true
        this.isSat = true;

        // set isSat flag
        if (str.contains("=====UNSATISFIABLE=====")) {
            this.isSat = false;
        } else if (str.contains("=====UNSATorUNBOUNDED=====")) {
            this.isSat = false;
        } else if (str.length() >= 10) {
            // get rid of last 10 characters ("----------")
            String modifiedRawString = str.substring(0, str.length() - 10);
            // System.out.println("Modified: " + modifiedRawString);

            try {
                this.resultMap = this.parse(modifiedRawString);
            } catch (Exception e) {
                // regard any exception as unsat
                // org.json.JSONException as unsat
                this.isSat = false;

                // create a empty map in case that it needs to be accessed
                this.resultMap = new HashMap<String, String>();
            }
            // System.out.println(this.resultMap);
        }

    }

    // parse the json object obtained from the result
    // and put it in result map
    public static Map<String, String> parse(String str) {
        JSONObject jsonObj = new JSONObject(str);
        Map<String, String> resultMap = new HashMap<String, String>();

        // get list of keys
        Set<String> keys = ((JSONObject) jsonObj).keySet();

        // build the resultMap data structure
        for (String key : keys) {
            resultMap.put(key, String.valueOf(jsonObj.get(key)));
        }

        return resultMap;
    }

    // get specific keys from the json result string
    public String get(String key) {
        if (!this.isSat) {
            return "UNSAT";
        } else {
            return this.resultMap.get(key);
        }
    }

    // see if the result is satisifed
    public Boolean isSat() {
        return this.isSat;
    }

    public String toString() {
        return this.rawResultString;
    }

    // parse the String result (in particular, action array) to an array list
    // [0]
    // [1, 2, 3]
    public static ArrayList<Integer> parseIntArray(String intArrayStr) {
        String commaSeparatedListStr = intArrayStr.substring(1, intArrayStr.length() - 1);
        String[] arrOfStr = commaSeparatedListStr.split(",");

        ArrayList<Integer> intArray = new ArrayList<Integer>();
        for (String a : arrOfStr) {
            if (!a.equals("")) {
                intArray.add(Integer.parseInt(a));
            }
        }

        return intArray;
    }

    public static ArrayList<Float> parseFloatArray(String floatArrayStr) {
        String commaSeparatedListStr = floatArrayStr.substring(1, floatArrayStr.length() - 1);
        String[] arrOfStr = commaSeparatedListStr.split(",");

        ArrayList<Float> floatArray = new ArrayList<Float>();
        for (String a : arrOfStr) {
            if (!a.equals("")) {
                floatArray.add(Float.parseFloat(a));
            }
        }

        return floatArray;
    }
}
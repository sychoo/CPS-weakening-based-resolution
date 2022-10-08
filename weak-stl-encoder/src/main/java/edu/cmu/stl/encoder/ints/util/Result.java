// Sat Feb 19 10:34:32 EST 2022
// Parses and stores the result from the MiniZinc commandline solver
// Simon Chu
//
// Note that result will not convert the value of the JSON formatted result
// to their corresponding data types
package edu.cmu.stl.encoder.ints.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        } else if (str.length() >= 10) {
            // get rid of last 10 characters ("----------")
            String modifiedRawString = str.substring(0, str.length() - 10);
            // System.out.println("Modified: "  + modifiedRawString);

            this.resultMap = this.parse(modifiedRawString);
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
    public String get (String key) {
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
}
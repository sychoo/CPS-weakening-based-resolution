// 2022-02-02 21:44:32
// Simon Chu

// program that supports signal operations

// implementation refered to 
// https://github.com/cps-sei/cps-synth-resolultion/blob/master/Signal.h
// https://github.com/cps-sei/cps-synth-resolultion/blob/master/Signal.cpp

// JSON parsing referred to
// https://stackoverflow.com/questions/9151619/how-to-iterate-over-a-jsonobject
// https://coderolls.com/parse-json-in-java/

// InputStream to String conversion referred to 
// https://www.baeldung.com/convert-input-stream-to-string
package edu.cmu.stl.encoder.ints.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.math.BigDecimal;

import org.json.JSONObject; // for parsing raw JSON formatted signals

import edu.cmu.stl.encoder.ints.util.Encoding;

// [[a = 0, b = 1, c = 2], [a = 3, b = 4, c = 5]]
// at time 0, the signal is [0, 1, 2]
// at time 1, the signal is [3, 4, 5]
// field2Index mapping: ["a" |-> 0, "b" |-> 1, "c" |-> 2]

public class Signal {
    // index |-> field name
    private ArrayList<String> fieldNames;

    // map from signal field names to indices
    // field name |-> index
    // assume that hashmap is a bijective mapping relation
    // meaning that no two distinct field names maps to identical indices
    private Map<String, Integer> field2Index;

    
    // stores the signal, sequence of system states
    private ArrayList<ArrayList<Double>> signal;

    // constructor
    public Signal(ArrayList<String> fieldNames) {
        this.fieldNames = fieldNames; 
        field2Index = new HashMap<String, Integer>();
        signal = new ArrayList<ArrayList<Double>>();

        // assign indices for field names and put them to field2Index hashmap
        for (int i = 0; i < fieldNames.size(); ++i) {
            field2Index.put(fieldNames.get(i), i);
        }
    }

    // parse JSON formatted signal string to a Signal object

    public static Signal parse(String rawSignalString) {
        // read in the raw signal string as JSON Object
        JSONObject jsonObj = new JSONObject(rawSignalString);

        // empty string is passed in
        // - return empty signal
        if (rawSignalString == "")
            return new Signal(new ArrayList<String>());
        else
            return Signal.parse(jsonObj);
    }

    public static Signal parse(JSONObject jsonObj) {
        int signalLength = jsonObj.length(); // length/duration of signal

        // intialize fieldNames arraylist for constructing Signal object
        ArrayList<String> fieldNames = new ArrayList<String>();

        // get signal fields at times index 0
        // - put them in an arrayList to construct Signal object
        for (String field : ((JSONObject) jsonObj.get("0")).keySet()) {
            fieldNames.add(field);
        }

        int fieldNameLength = fieldNames.size(); // length of field names

        // intialize signal 
        Signal signal = new Signal(fieldNames);

        // loop through time index
        for (int i = 0; i < signalLength; ++i) {

            // contain state info at time index i
            ArrayList<Double> state = new ArrayList<Double>();

            // json object that contain state info at time index i
            JSONObject stateJsonObj = (JSONObject) jsonObj.get(Integer.toString(i));

            for (int j = 0; j < fieldNameLength; ++j) {
                String currFieldName = fieldNames.get(j);

                Object currFieldValue = stateJsonObj.get(currFieldName);
                if (currFieldValue.getClass().getName() == "java.math.BigDecimal") {
                    state.add(((BigDecimal) currFieldValue).doubleValue());
                } else if (currFieldValue.getClass().getName() == "java.lang.Integer") {
                    state.add(Double.valueOf((Integer) currFieldValue));
                } else {
                    throw new RuntimeException("signal state should only contain integers or floats.");
                }
            }
            signal.append(state);
        }

        return signal;
    }

    // get the duration of the signal
    public int length() {
        return this.signal.size();
    }

    // check if a time index is available within the current signal
    public Boolean available(int t){
        return (t >= 0 && t < this.length());
    }

    // look up the index of a particular field name
    public int indexLookup(String fieldName) {
        return this.field2Index.get(fieldName);
    }

    // assume that hashmap is a bijective mapping relation
    public String fieldLookup(int index) {
        return this.fieldNames.get(index);
    }

    public ArrayList<String> getFieldNames() {
        return this.fieldNames;
    }

    // append new time 
    // -    when appending new state to signal, user must follow the
    //      field2Index specs
    // -    length of `next` should be identical to # of elements in
    //      field2Index
    public void append(ArrayList<Double> next) {
        assert (next.size() == this.field2Index.size());
        this.signal.add(next);
    }

    // remove the last (current) state of the signal
    public void pop() {
        this.signal.remove(this.length() - 1);
    }

    // get the entire sequence (throughout the time horizon) of a certain field
    // -    field must be one of the keys in field2Index
    public ArrayList<Double> getFieldList(String field) {
        assert (field2Index.containsKey(field));

        // look up the index from the field2Index hashmap
        int fieldIndex = field2Index.get(field);

        ArrayList<Double> result = new ArrayList<Double>();
        for (ArrayList<Double> state : this.signal) {
            result.add(state.get(fieldIndex));
        }
        return result;
    }

    // get the signal value of a specific field @ time index `time`
    // - ensure the time index given is available within the signal
    public double getField(String field, int time) {
        assert (this.available(time));
        return this.signal.get(time).get(this.indexLookup(field));
    }

    // by default, if with no time arguments, return current time
    public double getField(String field) {
        return this.getField(field, this.length() - 1);
    }

    // convert the Signal object to a string
    // - Usage: System.out.println(<Signal Object>);
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int t = 0; t < this.length(); ++t) {
            ArrayList<Double> state = this.signal.get(t);
            sb.append("{\n");
            sb.append("  time = " + t  + "\n");
            for (int i = 0; i < state.size(); ++i) {
                sb.append("  ");
                sb.append(this.fieldLookup(i));
                sb.append(" = ");
                sb.append(state.get(i));
                sb.append("\n");
            }
            sb.append("}\n");
        }
        
        return sb.toString();
    }

    public Encoding emitListEncoding(String fieldName, ArrayList<Double> list) {
        // note that double will be cast to int for STL-Int encoding
        Encoding e = new Encoding();
        
        // store the body of the array elements
        StringBuilder sb = new StringBuilder();

        //e.append(String.format("array[1..signal_length] of int: %s = ", CompilationListener.signalVar(fieldName)));
        e.append(String.format("array[1..signal_length] of int: %s = ", "sig_var_" + fieldName));

        sb.append("([");
        
        for (int i = 0; i < list.size(); ++i) {
            sb.append(String.valueOf(list.get(i).intValue()));

            if (i != list.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("])");
        sb.append(";\n");

        e.append(sb.toString());
        // e.append(CompilationListener.array1d("1", "signal_length", "int", CompilationListener.signalVar(fieldName), sb.toString()));

        return e;
    }

    public Encoding emitEncoding() {
        Encoding e = new Encoding();
        
        // obtain signal length
        e.append(String.format("int: signal_length = %d;\n", this.length()));

        // create flattened signal arrays
        for (String fieldName: this.getFieldNames()) {
            e.append(this.emitListEncoding(fieldName, this.getFieldList(fieldName)));
        }

        return e;
    }
}
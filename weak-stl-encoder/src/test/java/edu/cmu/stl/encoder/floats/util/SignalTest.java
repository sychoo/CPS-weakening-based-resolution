// 2022-02-02 21:54:36
// Simon Chu

// command to run SignalTest
/*
javac -cp .:jar/json-java.jar SignalTest.java
java -cp .:jar/json-java.jar SignalTest 
*/

package edu.cmu.stl.encoder.floats.util;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

import edu.cmu.stl.encoder.floats.util.Encoding;
import edu.cmu.stl.encoder.floats.util.Signal;

public class SignalTest {

    @Test
    public void SignalTestFunc() {
        // [[a = 0, b = 1, c = 2], [a = 3, b = 4, c = 5]]
        // at time 0, the signal is [0, 1, 2]
        // at time 1, the signal is [3, 4, 5]
        // field2Index mapping: ["a" |-> 0, "b" |-> 1, "c" |-> 2]

        // @constructor
        ArrayList<String> fieldNames = new ArrayList<String>() {{
                add("a");
                add("b");
                add("c");
            }};

        Signal s = new Signal(fieldNames);

        ArrayList<Double> state1 = new ArrayList<Double>(Arrays.asList(
                        0.0,
                        1.0,
                        2.0));
        ArrayList<Double> state2 = new ArrayList<Double>(Arrays.asList(
                        3.0,
                        4.0,
                        5.0));

        // @append @toString
        s.append(state1);
        s.append(state2);

        System.out.println(s); // [[0, 1, 2], [3, 4 ,5]]

        // @length
        System.out.println("signal length/duration:");
        System.out.println(s.length()); // 2
        assert (s.length() == 2);

        // @getFieldList
        System.out.println("getting field list for field 'a':");
        ArrayList<Double> fieldList = s.getFieldList("a");
        for (double field : fieldList) {
            System.out.println(field);
        }

        // @getField at index 0
        System.out.println("getting field 'a' at time index 0:");
        System.out.println(s.getField("a", 0)); // 0.0
        assert (s.getField("a", 0) == 0.0);

        // @getField at current time index
        System.out.println("getting field 'a' at current time index:");
        System.out.println(s.getField("a")); // 3.0
        assert (s.getField("a") == 3.0);

        // @pop
        System.out.println("pop:");
        s.pop();
        System.out.println(s); // [[0, 1, 2]]

        // @available
        System.out.println("check if time index is available at time 0:");
        System.out.println(s.available(0)); // true
        assert (s.available(0) == true);

        System.out.println("check if time index is available at time 1:");
        System.out.println(s.available(1)); // false
        assert (s.available(1) == false);

        // @fieldLookup
        System.out.println("looking up the field of index '1'");
        System.out.println(s.fieldLookup(1)); // b
        assert (s.fieldLookup(1) == "b");

        // @indexLookup
        System.out.println("looking up index of the field 'a'");
        System.out.println(s.indexLookup("a")); // 0
        assert (s.indexLookup("a") == 0);

        // @Signal.parse
        Signal newSignal = Signal.parse(" {\"0\" : { \"x\" : 1.25, \"y\" : 1 }, \"1\" : { \"x\" : 3, \"y\" : 4  } }");
        System.out.println(newSignal);

        // @Signal.parse (Int Signal)
        Signal newSignal2 = Signal.parse(" {\"0\" : { \"x\" : 1, \"y\" : 1 }, \"1\" : { \"x\" : 3, \"y\" : 4  } }");
        System.out.println(newSignal2);

        // @emitEncoding
        Encoding e = newSignal2.emitEncoding();
        System.out.println(e);
    }
}

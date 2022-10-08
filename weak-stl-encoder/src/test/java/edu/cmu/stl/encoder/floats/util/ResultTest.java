package edu.cmu.stl.encoder.floats.util;

import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import edu.cmu.stl.encoder.floats.util.Result;

public class ResultTest {
    @Test
    public void parIntArrayTest() {
        String str1 = "[]";
        ArrayList<Integer> parsed_str1 = Result.parseIntArray(str1);
        assertTrue (parsed_str1.size() == 0);

        String str2 = "[0,1,2]";
        ArrayList<Integer> parsed_str2 = Result.parseIntArray(str2);
        assertTrue (parsed_str2.size() == 3);

        assertTrue (parsed_str2.get(0) == 0);
        assertTrue (parsed_str2.get(1) == 1);
        assertTrue (parsed_str2.get(2) == 2);
    }
}

package edu.cmu.px4.mavsdk.utils;
import org.ini4j.InvalidFileFormatException;
// http://ini4j.sourceforge.net/tutorial/OneMinuteTutorial.java.html
import org.ini4j.Wini;

import edu.cmu.stl.encoder.floats.util.FileOperation;

import java.io.File;
import java.io.IOException;

// content of the ini file
// [happy]
// age = 99
// height = 77.66
// homeDir = /home/happy 

public class ConfigParser {
    // can use Python to generate ini file with random parameters
    // configParser
    public static void readExample() throws InvalidFileFormatException, IOException {
        String filename = "tmp/happy.ini";
        Wini ini = new Wini(new File(filename));
        int age = ini.get("happy", "age", int.class);
        int age2 = ini.get("happy", "age2", int.class);

        double height = ini.get("happy", "height", double.class);
        String dir = ini.get("happy", "homeDir");

        System.out.println("age = " + age);
        System.out.println("age2 = " + age2);
        
        System.out.println("height = " + height);
        System.out.println("dir = " + dir);
    }

    public static void writeExample() throws IOException {
        String filename = "tmp/unhappy.ini";
        Wini ini = new Wini(new File(filename));
        ini.put("sleepy", "age", 55);
        ini.put("sleepy", "weight", 45.6);
        ini.store();
    }
    public static void main(String[] args) throws InvalidFileFormatException, IOException {
        readExample();
        writeExample();
    }
}

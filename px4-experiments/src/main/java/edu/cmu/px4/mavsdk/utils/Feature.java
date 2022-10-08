package edu.cmu.px4.mavsdk.utils;

import io.mavsdk.System;

public abstract class Feature {
    public String name;

    public Feature(String name) {
        this.name = name;
    }

    abstract public Plan nextPlan(System drone);
    abstract public void actuate(System drone, Plan plan);
}

package com.leetcode2024spring.ecommercedemo1.collection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {
    private double longitude;
    private double latitude;

    public Location(){

    }

    public Location(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }
}

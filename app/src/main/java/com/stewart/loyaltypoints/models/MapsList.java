package com.stewart.loyaltypoints.models;


import java.util.Timer;

/**
 * Created by stewart on 17/01/2017.
 */

public class MapsList {

    public String Name;
    public String Location;
    public String Hours;

    public MapsList() {
    }

    public MapsList(String name, String location, String hours) {
        Name = name;
        Location = location;
        Hours = hours;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getHours() {
        return Hours;
    }

    public void setHours(String hours) {
        Hours = hours;
    }
}

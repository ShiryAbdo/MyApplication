package com.example.shady.myapplication;

/**
 * Created by Shiry Abdo on 3/31/2017.
 */

public class Spacecraft {


    public Long getTimelong() {
        return timelong;
    }

    public void setTimelong(Long timelong) {

        this.timelong = timelong;
    }

    long timelong;
    String time, name, propellant, description;

    public Spacecraft() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPropellant() {
        return propellant;
    }

    public void setPropellant(String propellant) {
        this.propellant = propellant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

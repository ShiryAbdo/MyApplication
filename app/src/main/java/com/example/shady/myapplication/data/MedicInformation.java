package com.example.shady.myapplication.data;


import java.util.ArrayList;

/**
 * Created by EL.GAMAL on 3/29/2017.
 */

public class MedicInformation {

    public String medicName;
    public Integer numberDoses;
    public String medicTime;



    public Boolean saturday;
    public Boolean sunday;
    public Boolean monday;
    public Boolean tuesday;
    public Boolean wednesday;
    public Boolean thursday;
    public Boolean friday;
    public String medicStartDate;
    public String medicEndDate;

//    public long timelong;
    public ArrayList<Long>timelong;





    public MedicInformation(){

    }

    public MedicInformation(String medicName, Integer numberDoses, String medicTime, ArrayList<Long> timelong,  String medicStartDate, String medicEndDate ,Boolean saturday, Boolean sunday, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday) {
        this.medicName = medicName;
        this.numberDoses = numberDoses;
        this.medicTime = medicTime;
        this.timelong = timelong;
        this.saturday = saturday;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.medicStartDate = medicStartDate;
        this.medicEndDate = medicEndDate;
    }

    public Boolean getTuesday() {
        return tuesday;
    }

    public void setTuesday(Boolean tuesday) {
        this.tuesday = tuesday;
    }


    public ArrayList<Long> getTimelong() {
        return timelong;
    }

    public void setTimelong(ArrayList<Long> timelong) {

        this.timelong = timelong;
    }





    public Integer getNumberDoses() {
        return numberDoses;
    }

    public void setNumberDoses(Integer numberDoses) {
        this.numberDoses = numberDoses;
    }

    public String getMedicName() {
        return medicName;
    }

    public void setMedicName(String medicName) {
        this.medicName = medicName;
    }

    public String getMedicTime() {
        return medicTime;
    }

    public void setMedicTime(String medicTime) {
        this.medicTime = medicTime;
    }

    public Boolean getSunday() {
        return sunday;
    }

    public void setSunday(Boolean sunday) {
        this.sunday = sunday;
    }

    public Boolean getSaturday() {
        return saturday;
    }

    public void setSaturday(Boolean saturday) {
        this.saturday = saturday;
    }

    public Boolean getMonday() {
        return monday;
    }

    public void setMonday(Boolean monday) {
        this.monday = monday;
    }


    public Boolean getWednesday() {
        return wednesday;
    }

    public void setWednesday(Boolean wednesday) {
        this.wednesday = wednesday;
    }

    public Boolean getThursday() {
        return thursday;
    }

    public void setThursday(Boolean thursday) {
        this.thursday = thursday;
    }

    public Boolean getFriday() {
        return friday;
    }

    public void setFriday(Boolean friday) {
        this.friday = friday;
    }

    public String getMedicStartDate() {
        return medicStartDate;
    }

    public void setMedicStartDate(String medicStartDate) {
        this.medicStartDate = medicStartDate;
    }

    public String getMedicEndDate() {
        return medicEndDate;
    }

    public void setMedicEndDate(String medicEndDate) {
        this.medicEndDate = medicEndDate;
    }

}
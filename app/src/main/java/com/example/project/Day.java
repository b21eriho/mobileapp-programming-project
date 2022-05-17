package com.example.project;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Day {
    @SerializedName("size")
    private int date;
    private String name;
    @SerializedName("location")
    private String sunUp;
    @SerializedName("company")
    private String sunDown;
    @SerializedName("auxdata")
    private List<String> events;

    public int getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getSunUp() {
        return sunUp;
    }

    public String getSunDown() {
        return sunDown;
    }

    public List<String> getEvents() {
        return events;
    }

    public String getSunTimesShort(){
        String[] sunUpShort = sunUp.split(":");
        String[] sunDownShort = sunDown.split(":");

        String returner = sunUpShort[0] + "-" + sunDownShort[0];

        return returner;
    }

    public String getFullDate() {
        String returner = "";

        returner += date + "";

        switch (date){
            case 1:
                returner += "st ";
                break;
            case 2:
                returner += "nd ";
                break;
            case 3:
                returner += "rd ";
                break;
            default:
                returner += "th ";
                break;
        }

        returner += "of Lightrise";

        return returner;
    }
}

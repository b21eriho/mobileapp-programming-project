package com.example.project;

public class DayNameHelper {

    public static String getNameFromDate(int date){
        String tmp = "";

        switch (date % 5){
            case 0:
                tmp += "Sunday the ";
                break;
            case 1:
                tmp += "Monday the ";
                break;
            case 2:
                tmp += "Tuesday the ";
                break;
            case 3:
                tmp += "Wednesday  the ";
                break;
            case 4:
                tmp += "Friday the ";
                break;
        }

        tmp += date + "";

        tmp += getNumberEnding(date);

        return tmp;
    }

    private static String getNumberEnding(int nr){
        switch (nr){
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    public static  String getFullDateFromDate(int date){
        String tmp = "";

        tmp += date + "";

        tmp += getNumberEnding(date);

        tmp += " of Lightrise";

        return tmp;
    }

}

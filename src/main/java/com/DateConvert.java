package com;

import java.util.Date;

public class DateConvert {

    public static String convertNumericDateToMinimumAlphabeticDate(Date date) {

        // convert to string array
        String [] dateStrings = date.toString().split("-");

        // now get month
        int day = Integer.parseInt(dateStrings[2]);
        int month = Integer.parseInt(dateStrings[1]);

        switch (month) {
            case 1: return day + " Jan " + dateStrings[0];
            case 2: return day + " Feb " + dateStrings[0];
            case 3: return day + " Mar " + dateStrings[0];
            case 4: return day + " Apr " + dateStrings[0];
            case 5: return day + " May " + dateStrings[0];
            case 6: return day + " Jun " + dateStrings[0];
            case 7: return day + " Jul " + dateStrings[0];
            case 8: return day + " Aug " + dateStrings[0];
            case 9: return day + " Sep " + dateStrings[0];
            case 10: return day + " Oct " + dateStrings[0];
            case 11: return day + " Nov " + dateStrings[0];
            case 12: return day + " Dec " + dateStrings[0];
            default: return date.toString();
        }
    }

    public static String convertNumericDateToAlphabeticDate(Date date) {

        // convert to string array
        String [] dateStrings = date.toString().split("-");

        // now get month
        int day = Integer.parseInt(dateStrings[2]);
        int month = Integer.parseInt(dateStrings[1]);

        switch (month) {
            case 1: return day + " January " + dateStrings[0];
            case 2: return day + " February " + dateStrings[0];
            case 3: return day + " March " + dateStrings[0];
            case 4: return day + " April " + dateStrings[0];
            case 5: return day + " May " + dateStrings[0];
            case 6: return day + " June " + dateStrings[0];
            case 7: return day + " July " + dateStrings[0];
            case 8: return day + " August " + dateStrings[0];
            case 9: return day + " September " + dateStrings[0];
            case 10: return day + " October " + dateStrings[0];
            case 11: return day + " November " + dateStrings[0];
            case 12: return day + " December " + dateStrings[0];
            default: return date.toString();
        }
    }
}

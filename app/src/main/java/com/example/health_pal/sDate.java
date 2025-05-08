package com.example.health_pal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class sDate {
    String returnDate, day, month, year;
    Calendar cal = Calendar.getInstance();;
    public sDate(Date inputDate){
        cal.setTime(inputDate);
        month = Integer.toString(cal.get(Calendar.MONTH) + 1);
        if(month.length() == 1){ month = "0" + month; }
        day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
        if(day.length() == 1){ day = "0" + day; }
        year = Integer.toString(cal.get(Calendar.YEAR));
        returnDate = month + "-" + day + "-" + year;
    }
    public sDate(int inputMonth, int inputDay, int inputYear){
        month = Integer.toString(inputMonth);
        if(month.length() == 1){ month = "0" + month; }
        day = Integer.toString(inputDay);
        if(day.length() == 1){ day = "0" + day; }
        year = Integer.toString(inputYear);
        returnDate = month + "-" + day + "-" + year;
    }
    public String getDate(){
        return returnDate;
    }
}

package com.example.health_pal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class sDate {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String returnDate;
    public sDate(Date inputDate){
        returnDate = sdf.format(inputDate);
    }
    public String getDate(){
        return returnDate;
    }
}

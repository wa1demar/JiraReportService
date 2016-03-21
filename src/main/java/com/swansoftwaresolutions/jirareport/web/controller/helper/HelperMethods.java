package com.swansoftwaresolutions.jirareport.web.controller.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Vitaliy Holovko
 */


public class HelperMethods {


    public boolean isWeekend(Date date1) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);

        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    public  boolean isSameDate(Date date, Date anotherDate) {
        if(date==null && anotherDate==null){
            return true;
        }
        else if(date==null || anotherDate==null){
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        Calendar anotherCalendar = Calendar.getInstance();
        anotherCalendar.setTime(anotherDate);
        anotherCalendar.set(Calendar.HOUR_OF_DAY, 0);
        anotherCalendar.set(Calendar.MINUTE, 0);
        anotherCalendar.set(Calendar.SECOND, 0);
        anotherCalendar.set(Calendar.MILLISECOND, 0);
        anotherCalendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        return calendar.compareTo(anotherCalendar) == 0;
    }

    public Long isNull(Long hours) {
        if (hours == null){
            return 0L;
        }
        return hours;
    }

    public int isNullDoubleToInt(Double targetHours) {
        if (targetHours!=null){
            return targetHours.intValue();
        }
        return 0;
    }
}

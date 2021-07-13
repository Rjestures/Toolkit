package com.rjesture.startupkit.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.rjesture.startupkit.utils.AppTools.handleCatch;
import static com.rjesture.startupkit.utils.AppTools.setLog;

public class DateFactory {
    static String patternFormat = "";

    public DateFactory(String pattern) {
        patternFormat = pattern.isEmpty() ? "dd-MMM-yyyy hh:mm aa" : pattern;
    }

    public String convertEpochDate(String timestamp) {
        return Long.toString(Long.parseLong(timestamp) * 1000);
    }

    public String readTimeStampDate(String timeStampDate) {
        try {
            Date date = new Date(Long.parseLong(timeStampDate));
            SimpleDateFormat format = new SimpleDateFormat(patternFormat);
            String myDate = format.format(date);
            return myDate;
        } catch (Exception e) {
            handleCatch(e);
        }
        return "";
    }

    public int getDays(String startDate) {
        try {
            long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;
            SimpleDateFormat dateFormat = new SimpleDateFormat(patternFormat);
            long begin = dateFormat.parse(startDate).getTime();
            long end = new Date().getTime(); // 2nd date want to compare
            long diff = (end - begin) / (MILLIS_PER_DAY);
            setLog("Days_Ago", "days " + diff);
            return (int) diff;
        } catch (Exception e) {
            handleCatch(e);
        }
        return 0;
    }

    public String getCurrentDate() {
        try {
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);
            SimpleDateFormat df = new SimpleDateFormat(patternFormat);
            String formattedDate = df.format(c);
            return formattedDate;
        } catch (Exception e) {
            handleCatch(e);
        }
        return "";
    }

    public String addDays(String oldDate, int days) {
        DateFormat formatter = new SimpleDateFormat(patternFormat, Locale.US);
        try {
            Date date = formatter.parse(oldDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, days);
            System.out.println("Cool" + formatter.format(calendar.getTime()));
            return formatter.format(calendar.getTime());
        } catch (Exception e) {
            handleCatch(e);
            System.out.println("Wrong date");
        }
        return "";
    }

}

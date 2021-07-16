package com.upnvj.skripsian.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    private static SimpleDateFormat DAY_DATE_FORMATTER = new SimpleDateFormat("EEEE, d MMMM yyyy", getLocale());
    private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("d MMMM yyyy", getLocale());

    public static boolean isWeekdays(long timestamp) {
        Calendar c = getCalendar(timestamp);
        return c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY ||
                c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY ||
                c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY ||
                c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY ||
                c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
    }

    public static boolean isFromSameDay(long timestamp, int constantIndex) {
        Calendar c = getCalendar(timestamp);
        return c.get(Calendar.DAY_OF_WEEK) == convertConstantIndexToDayOfWeek(constantIndex);
    }

    public static boolean isFromSameDate(long timestamp1, long timestamp2) {
        String formated1 = formatDate(timestamp1);
        String formated2 = formatDate(timestamp2);
        return formated1.equals(formated2);
    }

    public static boolean isFromSameDate(long timestamp, String datestring) {
        String formated = formatDate(timestamp);
        return formated.equals(datestring);
    }

    private static int convertConstantIndexToDayOfWeek(int index) {
        switch (index) {
            case 0:
                return Calendar.MONDAY;
            case 1:
                return Calendar.TUESDAY;
            case 2:
                return Calendar.WEDNESDAY;
            case 3:
                return Calendar.THURSDAY;
            case 4:
                return Calendar.FRIDAY;
            default:
                return Calendar.SUNDAY;
        }
    }

    public static String formatDayDate(long timestamp) {
        Date date = new Date(timestamp);
        return DAY_DATE_FORMATTER.format(date);
    }

    public static String formatDate(long timestamp) {
        Date date = new Date(timestamp);
        return DATE_FORMATTER.format(date);
    }

    public static long parseDate(String datestring) {
        Date date = null;
        try {
            date = DATE_FORMATTER.parse(datestring);
            if (date != null) return date.getTime();
            else return -1;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static Calendar getCalendar(long timestamp) {
        Calendar c = Calendar.getInstance(getLocale());
        c.setTimeInMillis(timestamp);
        return c;
    }

    private static Locale getLocale() {
        return new Locale("in", "ID");
    }

}

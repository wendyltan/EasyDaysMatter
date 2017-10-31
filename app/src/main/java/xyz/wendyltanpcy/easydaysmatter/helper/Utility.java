package xyz.wendyltanpcy.easydaysmatter.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by user on 17-8-29.
 */

public class Utility {

    /**
     * Formatting date object to string of the form 'yyyy-MM-dd'
     * @param date the date object to format
     * @return a string represent date
     */
    public static String dateToString(Date date) {
        return new SimpleDateFormat("yyy-MM-dd").format(date);
    }

    /**
     * Calculating the date interval between current date and target date
     * @param date target date
     * @return the interval between current date and target date
     */
    public static long getDateInterval(Date date) {

        Date now = new Date();

        GregorianCalendar inCalendar = new GregorianCalendar();

        GregorianCalendar nowCanlendar = new GregorianCalendar();

        inCalendar.setTime(date);

        now.setHours(0);
        now.setMinutes(0);
        now.setSeconds(0);

        nowCanlendar.setTime(now);

        long dayCount = (inCalendar.getTimeInMillis()-nowCanlendar.getTimeInMillis()+86400000-1)/86400000;

        return dayCount;
    }
}

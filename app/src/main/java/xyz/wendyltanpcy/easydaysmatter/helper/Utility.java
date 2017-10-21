package xyz.wendyltanpcy.easydaysmatter.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        long duration = date.getTime() - currentDate.getTime();
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        return days;
    }
}

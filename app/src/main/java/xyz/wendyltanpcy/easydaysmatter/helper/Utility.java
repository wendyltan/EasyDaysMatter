package xyz.wendyltanpcy.easydaysmatter.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);
        long days = dateCal.get(Calendar.DAY_OF_YEAR)-calendar.get(Calendar.DAY_OF_YEAR);
        return days;
    }
}

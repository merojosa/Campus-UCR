package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Utilities;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Utility class for handling Date formats.
 * */
public class UtilDates {

    /**
     * Converts a Date object to its String representation.
     * @param date Date to be converted.
     * @return String containing the converted date.
     * */
    public static String DateToString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(date);
    }

    /**
     * Converts a String date into a Date object.
     * @param date Date to be converted.
     * @return Date containing the converted date.
     * */
    public static Date StringToDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date convertedDate;
        try{
            convertedDate = format.parse(date);
        }catch(ParseException e){
            convertedDate = null;
            Log.d("exception", e.toString());
        }
        return convertedDate;
    }
}

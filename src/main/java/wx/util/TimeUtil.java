package wx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    private static SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    public static int calculateDayDifference(String fromDate, String toDate) throws ParseException {
        Date _fromDate  = simpleFormat.parse(fromDate);
        Date _toDate  = simpleFormat.parse(toDate);
        long from1 = _fromDate.getTime();
        long to1 = _toDate.getTime();
        int days = (int) ((to1 - from1) / (1000 * 60 * 60 * 24));
        return days;
    }



}

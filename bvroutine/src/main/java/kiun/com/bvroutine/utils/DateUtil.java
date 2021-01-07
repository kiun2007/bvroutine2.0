package kiun.com.bvroutine.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DateUtil {

    public static String weekNow(){
        String[] weeks = new String[]{"","7","1","2","3","4","5","6"};
        return weeks[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)];
    }

    public static Date getMonthStart(Date date){
        Calendar calendar = Calendar.getInstance();

        if (date != null){
            calendar.setTime(date);
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date getMonthEnd(Date date){
        Calendar calendar = Calendar.getInstance();
        if (date != null){
            calendar.setTime(date);
        }
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);

        if (Calendar.getInstance().before(calendar)){
            calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        }
        return calendar.getTime();
    }

    public static float getHours(Date start, Date end){

        return (end.getTime() - start.getTime()) / (3600*1000);
    }

    public static List<String> getDays(Date start, Date end, String format){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);

        List<String> allTimes = new LinkedList<>();
        allTimes.add(MCString.formatDate(format, start));
        while (calendar.getTime().before(end)){
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            allTimes.add(MCString.formatDate(format, calendar.getTime()));
        }
        return allTimes;
    }

    public static boolean weekIn(String weeks){
        if (weeks == null){
            return false;
        }
        String[] weekArray = weeks.split(",");

        if (weekArray.length < 0){
            return false;
        }

        String now = weekNow();
        for (int i = 0; i < weekArray.length; i++) {
            if (weekArray[i].equals(now)){
                return true;
            }
        }
        return false;
    }

    /**
     * 增加N天.
     * @param date
     * @param days
     * @return
     */
    public static Date addDay(Date date, int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    /**
     * 当天的时分秒.
     * @param time
     * @return
     */
    public static Date todayHours(String time){
        return MCString.dateByFormat(String.format("%s %s", MCString.formatDate("yyyy-MM-dd", new Date()),time), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 一天的开始.
     * @param date
     * @return
     */
    public static Date getDayStart(Date date){
        long newTime = date.getTime() % (24*3600*1000);
        return new Date(date.getTime()-newTime);
    }

    /**
     * 一周的开始.
     * @param date
     * @return
     */
    public static Date getWeekStart(Date date){

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        return getDayStart(c.getTime());
    }

    /**
     * 一周的最后一秒.
     * @param date
     * @return
     */
    public static Date getWeekEnd(Date date){

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        return getDayEnd(c.getTime());
    }

    public static Date now(){
        return new Date();
    }

    public static Date nowDay(){
        return getDayEnd(new Date());
    }

    /**
     * 一天中的最后一秒.
     * @param date
     * @return
     */
    public static Date getDayEnd(Date date){
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        return date;
    }
}

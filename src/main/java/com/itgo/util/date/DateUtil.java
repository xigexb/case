package com.itgo.util.date;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/5/31 10:32
 * @description desc:
 */
public class DateUtil {

    /**
     * 1秒对应的毫秒数
     */
    public static final long MILLIS_IN_A_SECOND = 1000;

    /**
     * 1分对应的秒数
     */
    public static final long SECONDS_IN_A_MINUTE = 60;

    /**
     * 1小时对应的分钟数
     */
    public static final long MINUTES_IN_AN_HOUR = 60;

    /**
     * 1天对应的小时数
     */
    public static final long HOURS_IN_A_DAY = 24;

    /**
     * 1星期对应的天数
     */
    public static final int DAYS_IN_A_WEEK = 7;

    /**
     * 1年对应的月数
     */
    public static final int MONTHS_IN_A_YEAR = 12;

    /**
     * 1天对应的毫秒数
     */
    public static final long millSecondsInOneDay = HOURS_IN_A_DAY * MINUTES_IN_AN_HOUR * SECONDS_IN_A_MINUTE * MILLIS_IN_A_SECOND;

    /**
     * 1分钟对应的毫秒数
     */
    public static final long millSecondsInOneMinute = SECONDS_IN_A_MINUTE * MILLIS_IN_A_SECOND;

    /**
     * 时间格式化
     */
    public static final String DATE_FORMAT = "yyyyMMddHHmmssSSS";

    /**
     * 获取简单的日期
     *
     * @return
     */
    public static String getSimpleDate() {
        SimpleDateFormat time = new SimpleDateFormat(DATE_FORMAT);
        String now = time.format(new Date());

        return now;
    }

    /**
     * 日期格式化
     *
     * @param date    日期
     * @param formate 格式，以本类定义的格式为准，可自行添加。
     * @return
     */
    public static String dateToString(Date date, String formate) {
        if (date == null) {
            return "";
        }

        return new SimpleDateFormat(formate).format(date);
    }

    /**
     * 字符串转日期
     *
     * @param dateString
     * @param format     日期格式
     * @return
     */
    public static Date strToDate(String dateString, String format) {
        if (dateString == null) {
            throw new InvalidParameterException("dateString cannot be null!");
        }
        try {
            return new SimpleDateFormat(format).parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("parse error! [dateString:" + dateString + ";format:" + format + "]");
        }
    }


    /**
     * 统计两个日期之间包含的天数,忽略时间
     *
     * @param startDate
     * @param endDate
     * @return
     * @description
     */
    public static int getDaysBetween(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new InvalidParameterException("startDate and endDate cannot be null!");
        }
        Date _startDate = org.apache.commons.lang3.time.DateUtils.truncate(startDate, Calendar.DATE);
        Date _endDate = org.apache.commons.lang3.time.DateUtils.truncate(endDate, Calendar.DATE);
        if (_startDate.after(_endDate)) {
            throw new InvalidParameterException("startDate cannot be after endDate!");
        }
        return (int) ((_endDate.getTime() - _startDate.getTime()) / millSecondsInOneDay);
    }


    /**
     * 日期增加
     *
     * @param date         日期
     * @param calendarType 增加类型，如：Calendar.YEAR，Calendar.DAY_OF_YEAR，Calendar.MONTH
     * @param value
     * @return
     */
    public static Date addDate(Date date, int calendarType, int calendarValue) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendarType, calendarValue);
        return calendar.getTime();
    }

    /**
     * 日期增加n年
     *
     * @param date 日期
     * @param year 年数
     * @return
     */
    public static Date addYear(Date date, int year) {
        return addDate(date, Calendar.YEAR, year);
    }

    /**
     * 日期增加n月
     *
     * @param date  日期
     * @param month 月数
     * @return
     * @author huangyunsong
     * @createDate 2015年12月21日
     */
    public static Date addMonth(Date date, int month) {
        return addDate(date, Calendar.MONTH, month);
    }

    /**
     * 日期增加n天
     *
     * @param date 日期
     * @param day  天数
     * @return
     */
    public static Date addDay(Date date, int day) {
        return addDate(date, Calendar.DAY_OF_YEAR, day);
    }

    /**
     * 日期减少n年
     *
     * @param date 日期
     * @param year 年数
     * @return
     */
    public static Date minusYear(Date date, int year) {
        return addDate(date, Calendar.YEAR, -year);
    }

    /**
     * 日期减少n月
     *
     * @param date  日期
     * @param month 月数
     * @return
     */
    public static Date minusMonth(Date date, int month) {
        return addDate(date, Calendar.MONTH, -month);
    }

    /**
     * 日期减少n天
     *
     * @param date 日期
     * @param day  天数
     * @return
     */
    public static Date minusDay(Date date, int day) {
        return addDate(date, Calendar.DAY_OF_YEAR, -day);
    }

    /**
     * 获取年
     *
     * @param time
     * @return
     */
    public static int getYear(Date time) {
        if (time == null) {
            throw new InvalidParameterException("time cannot be null!");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前时间年
     *
     * @return
     */
    public static int getCurrentYear() {
        return getYear(new Date());
    }

    /**
     * 获取月
     *
     * @param time
     * @return
     */
    public static int getMonth(Date time) {
        if (time == null) {
            throw new InvalidParameterException("time cannot be null!");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前时间月
     *
     * @return
     */
    public static int getCurrentMonth() {
        return getMonth(new Date());
    }

    /**
     * 获取日
     *
     * @param time
     * @return
     */
    public static int getDay(Date time) {
        if (time == null) {
            throw new InvalidParameterException("time cannot be null!");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前时间日
     *
     * @return
     */
    public static int getCurrentDay() {
        return getDay(new Date());
    }

    /**
     * 判断date1是否在date2之后，忽略时间部分
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isDateAfter(Date date1, Date date2) {
        Date theDate1 = org.apache.commons.lang3.time.DateUtils.truncate(date1, Calendar.DATE);
        Date theDate2 = org.apache.commons.lang3.time.DateUtils.truncate(date2, Calendar.DATE);
        return theDate1.after(theDate2);
    }

    /**
     * 判断date1是否在date2之前，忽略时间部分
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isDateBefore(Date date1, Date date2) {
        return isDateAfter(date2, date1);
    }

    /**
     * 判断date1是否在date2之后       如:“2015-12-06 12：05：15”.after("2015-12-06 12:17:16") = true
     *
     * @param val
     * @param scale
     * @return
     * @author liudong
     * @createDate 2015年12月24日
     */
    public static boolean isDatetimeAfter(Date date1, Date date2) {
        return date1.after(date2);
    }

    /**
     * 判断time1是否在time2之后，忽略日期部分
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isTimeAfter(Date time1, Date time2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(time1);
        calendar1.set(1900, 1, 1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(time2);
        calendar2.set(1900, 1, 1);
        return calendar1.after(calendar2);
    }

    /**
     * 判断time1是否在time2之前，忽略日期部分
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isTimeBefore(Date time1, Date time2) {
        return isTimeAfter(time2, time1);
    }

    /**
     * 判断两个日期是否同一天（忽略时间部分）
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2) {
        return org.apache.commons.lang3.time.DateUtils.isSameDay(date1, date2);
    }


    /**
     * 获取当前月份的第一天
     *
     * @param val
     * @param scale
     * @return
     * @author liudong
     * @createDate 2015年12月24日
     */
    public static String getMonthFirstDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        String s = sdf.format(calendar.getTime());
        StringBuffer str = new StringBuffer().append(s).append(" 00:00:00");

        return str.toString();
    }

    /**
     * 获取当前月份的最后一天
     *
     * @param val
     * @param scale
     * @return
     * @author liudong
     * @createDate 2015年12月24日
     */
    public static String getMonthLastDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String s = sdf.format(calendar.getTime());
        StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");

        return str.toString();
    }

    /**
     * 获取今天次日的时间
     * 比如现在时间是：2018-07-03 14:53:35
     * 次日是：2018-07-04 00:00:00
     * DateUtil.getTodayNextDay(DateUtil.addDay(new Date(), 1))
     *
     * @return
     * @author liuhefei
     * @createDate 2018年7月3日
     */
    public static String getTodayNextDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        String s = sdf.format(date);
        StringBuffer str = new StringBuffer().append(s).append(" 00:00:00");

        return str.toString();
    }

    /**
     * 得到当前时间距2013-11-01 00:00:00的小时数
     *
     * @return
     */
    public static String getHours() {
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simple.parse("2013-11-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millisecond = System.currentTimeMillis() - date.getTime();
        long temp = 1000 * 60 * 60;
        long hours = millisecond / temp;

        return Long.toString(hours);
    }

    /**
     * 获得当前周- 周一的日期
     *
     * @return
     */
    public static String getCurrentMonday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String preMonday = sdf.format(monday);
        return preMonday;
    }

    public static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期一是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    /**
     * 获得当前周- 周日的日期
     *
     * @return
     */
    public static String getPreviousSunday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DATE, mondayPlus + 6);
        Date monday = currentDate.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String preMonday = sdf.format(monday);
        return preMonday;
    }

    /**
     * 返回2个日期相差的天数
     *
     * @param fDate
     * @param oDate
     * @return
     */
    public static int getIntervalDays(Date fDate, Date oDate) {

        if (null == fDate || null == oDate) {

            return -1;
        }

        long intervalMilli = oDate.getTime() - fDate.getTime();

        return (int) (intervalMilli / (24 * 60 * 60 * 1000));

    }

    /**
     * 判断date1是否与date2相等，忽略时间部分
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isDateEqual(Date date1, Date date2) {
        Date theDate1 = org.apache.commons.lang3.time.DateUtils.truncate(date1, Calendar.DATE);
        Date theDate2 = org.apache.commons.lang3.time.DateUtils.truncate(date2, Calendar.DATE);

        return theDate1.equals(theDate2);
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param format
     * @return
     */
    public static Date dateToDate(Date date, String format) {
        if (date == null) {
            throw new InvalidParameterException("date cannot be null!");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        Date newDate = null;
        String dateStr = DateUtil.dateToString(date, format);
        try {
            newDate = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("parse error! [dateString:" + dateStr + ";format:" + format + "]");
        }

        return newDate;
    }

}

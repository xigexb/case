package com.itgo.util.date;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Create by xigexb
 *
 * @version 1.0.0
 * @Author xigexb
 * @date 2019/9/6 9:08
 * @Description desc:
 */
public class DateTimeUtil {

    private static final String DEF_ZONE_ID = "Asia/Shanghai";

    private static final ZoneId DEF_ZONE = ZoneId.systemDefault();

    private static final String DEF_DATE_PAT = "yyyy-MM-dd hh:mm:ss";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEF_DATE_PAT);

    /**
     * 获取当前时区的当前时间
     *
     * @return
     */
    public static LocalDateTime now() {
        return now(DEF_ZONE_ID);
    }

    /**
     * 获取当前时区的当前时间
     *
     * @return
     */
    public static String nowStr(String pattern) {
        LocalDateTime localDateTime = now(DEF_ZONE_ID);
        return toLocalDateTimeStr(localDateTime,pattern);
    }

    /**
     * 获取当前时区的当前时间
     *
     * @return
     */
    public static String nowStr() {
        LocalDateTime localDateTime = now(DEF_ZONE_ID);
        return toLocalDateTimeStr(localDateTime);
    }

    /**
     * 获取指定时区的当前时间
     *
     * @return
     */
    public static LocalDateTime now(String zoneId) {
        return LocalDateTime.now(Clock.system(ZoneId.of(zoneId)));
    }


    /**
     * Date转LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return toLocalDateTime(date, DEF_ZONE_ID);
    }

    /**
     * Date转LocalDateTime 转换指定时区时间
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date, String zoneId) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.of(zoneId);
        return LocalDateTime.ofInstant(instant, zone);
    }


    /**
     * Date转LocalTime
     *
     * @param date
     * @return
     */
    public static LocalTime toLocalTime(Date date) {
        return toLocalTime(date, DEF_ZONE_ID);
    }

    /**
     * Date转LocalTime 转换指定时区时间
     *
     * @param date
     * @return
     */
    public static LocalTime toLocalTime(Date date, String zoneId) {
        LocalDateTime localDateTime = toLocalDateTime(date, zoneId);
        return localDateTime.toLocalTime();
    }


    /**
     * Date转LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        return toLocalDate(date, DEF_ZONE_ID);
    }

    /**
     * Date转LocalDate 转换指定时区时间
     *
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date, String zoneId) {
        LocalDateTime localDateTime = toLocalDateTime(date, zoneId);
        return localDateTime.toLocalDate();
    }


    /**
     * LocalDateTime转Date
     *
     * @param localDateTime
     * @return
     */
    public static Date fromLocalDateTime(LocalDateTime localDateTime) {
        return fromLocalDateTime(localDateTime, DEF_ZONE_ID);
    }

    /**
     * LocalDateTime转Date到指定时区
     *
     * @param localDateTime
     * @return
     */
    public static Date fromLocalDateTime(LocalDateTime localDateTime, String zoneId) {
        ZoneId zone = ZoneId.of(zoneId);
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }


    /**
     * LocalDate 转Date
     *
     * @param localDate
     * @return
     */
    public static Date fromLocalDate(LocalDate localDate) {
        return fromLocalDate(localDate, DEF_ZONE_ID);
    }

    /**
     * LocalDate 转Date 指定时区id
     *
     * @param localDate
     * @return
     */
    public static Date fromLocalDate(LocalDate localDate, String zoneId) {
        ZoneId zone = ZoneId.of(zoneId);
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }


    /**
     * LocalTime 转Date
     *
     * @param localTime
     * @return
     */
    public static Date fromLocalTime(LocalTime localTime) {
        return fromLocalTime(localTime, DEF_ZONE_ID);
    }


    /**
     * LocalTime 转Date指定时区
     *
     * @param localTime
     * @return
     */
    public static Date fromLocalTime(LocalTime localTime, String zoneId) {
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneId zone = ZoneId.of(zoneId);
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * long转date 13位时间戳 毫秒
     *
     * @param time
     * @return
     */
    public static Date fromLong13(Long time) {
        Instant instant = Instant.ofEpochMilli(time);
        LocalDateTime localDateTime = instant.atZone(DEF_ZONE).toLocalDateTime();
        return fromLocalDateTime(localDateTime);
    }

    /**
     * long转date 10位时间戳 秒
     *
     * @param time
     * @return
     */
    public static Date fromLong10(Long time) {
        Instant instant = Instant.ofEpochSecond(time);
        LocalDateTime localDateTime = instant.atZone(DEF_ZONE).toLocalDateTime();
        return fromLocalDateTime(localDateTime);
    }


    /**
     * long 转 LocalDateTime
     *
     * @param time
     * @return
     */
    public static LocalDateTime longToLocalDateTime(Long time) {
        Date date = fromLong13(time);
        return toLocalDateTime(date);
    }


    /**
     * long 转 LocalTime
     *
     * @param time
     * @return
     */
    public static LocalTime longToLocalTime(Long time) {
        Date date = fromLong13(time);
        return toLocalTime(date);
    }


    /**
     * long 转 LocalDate
     *
     * @param time
     * @return
     */
    public static LocalDate longToLocalDate(Long time) {
        Date date = fromLong13(time);
        return toLocalDate(date);
    }


    /**
     * 获取年
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        LocalDate localDate = toLocalDate(date);
        return localDate.getYear();
    }


    /**
     * 获取月
     *
     * @param date
     * @return
     */
    public static Month getMonth(Date date) {
        LocalDate localDate = toLocalDate(date);
        return localDate.getMonth();
    }


    /**
     * 获取月
     *
     * @param date
     * @return
     */
    public static int getMonthValue(Date date) {
        Month month = getMonth(date);
        return month.getValue();
    }


    /**
     * 获取月份中的一天
     *
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        LocalDate localDate = toLocalDate(date);
        return localDate.getDayOfMonth();
    }


    /**
     * 获取年份中的一天
     *
     * @param date
     * @return
     */
    public static int getDayOfYear(Date date) {
        LocalDate localDate = toLocalDate(date);
        return localDate.getDayOfYear();
    }

    /**
     * 获取当前日期的星期
     *
     * @param date
     * @return
     */
    public static DayOfWeek getWeek(Date date) {
        LocalDate localDate = toLocalDate(date);
        return localDate.getDayOfWeek();
    }

    /**
     * 获取当前日期的星期
     *
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {
        DayOfWeek week = getWeek(date);
        return week.getValue();
    }


    /**
     * 获取小时
     *
     * @param date
     * @return
     */
    public static int getHour(Date date) {
        LocalTime localTime = toLocalTime(date);
        return localTime.getHour();
    }


    /**
     * 获取分钟
     *
     * @param date
     * @return
     */
    public static int getMinute(Date date) {
        LocalTime localTime = toLocalTime(date);
        return localTime.getMinute();
    }


    /**
     * 获取秒
     *
     * @param date
     * @return
     */
    public static int getSecond(Date date) {
        LocalTime localTime = toLocalTime(date);
        return localTime.getSecond();
    }


    /**
     * 两个日期年差
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long differenceOfYears(Date date1, Date date2) {
        LocalDate localDate1 = toLocalDate(date1);
        LocalDate localDate2 = toLocalDate(date2);
        return localDate2.until(localDate1, ChronoUnit.YEARS);
    }


    /**
     * 当前时间与指定日期年差
     *
     * @param date
     * @return
     */
    public static long differenceOfYears(Date date) {
        LocalDate localDate1 = toLocalDate(date);
        LocalDate localDate2 = toLocalDate(new Date());
        return localDate2.until(localDate1, ChronoUnit.YEARS);
    }


    /**
     * 两个日期月差
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long differenceOfMonths(Date date1, Date date2) {
        LocalDate localDate1 = toLocalDate(date1);
        LocalDate localDate2 = toLocalDate(date2);
        return localDate2.until(localDate1, ChronoUnit.MONTHS);
    }


    /**
     * 当前时间与指定日期月差
     *
     * @param date
     * @return
     */
    public static long differenceOfMonths(Date date) {
        LocalDate localDate1 = toLocalDate(date);
        LocalDate localDate2 = toLocalDate(new Date());
        return localDate2.until(localDate1, ChronoUnit.MONTHS);
    }


    /**
     * 两个日期日差
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long differenceOfDays(Date date1, Date date2) {
        LocalDate localDate1 = toLocalDate(date1);
        LocalDate localDate2 = toLocalDate(date2);
        return localDate2.until(localDate1, ChronoUnit.DAYS);
    }


    /**
     * 当前时间与指定日期日差
     *
     * @param date
     * @return
     */
    public static long differenceOfDays(Date date) {
        LocalDate localDate1 = toLocalDate(date);
        LocalDate localDate2 = toLocalDate(new Date());
        return localDate2.until(localDate1, ChronoUnit.DAYS);
    }


    /**
     * 两个日期时差
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long differenceOfHours(Date date1, Date date2) {
        LocalDateTime localDateTime1 = toLocalDateTime(date1);
        LocalDateTime localDateTime2 = toLocalDateTime(date2);
        return localDateTime2.until(localDateTime1, ChronoUnit.HOURS);
    }


    /**
     * 当前时间与指定日期日差时差
     *
     * @param date
     * @return
     */
    public static long differenceOfHours(Date date) {
        LocalDateTime localDateTime1 = toLocalDateTime(date);
        LocalDateTime localDateTime2 = toLocalDateTime(new Date());
        return localDateTime2.until(localDateTime1, ChronoUnit.HOURS);
    }


    /**
     * 两个日期分差
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long differenceOfMinutes(Date date1, Date date2) {
        LocalDateTime localDateTime1 = toLocalDateTime(date1);
        LocalDateTime localDateTime2 = toLocalDateTime(date2);
        return localDateTime2.until(localDateTime1, ChronoUnit.MINUTES);
    }

    /**
     * 当前时间与指定日期分差
     *
     * @param date
     * @return
     */
    public static long differenceOfMinutes(Date date) {
        LocalDateTime localDateTime1 = toLocalDateTime(date);
        LocalDateTime localDateTime2 = toLocalDateTime(new Date());
        return localDateTime2.until(localDateTime1, ChronoUnit.MINUTES);
    }


    /**
     * 两个日期秒差
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long differenceOfSeconds(Date date1, Date date2) {
        LocalDateTime localDateTime1 = toLocalDateTime(date1);
        LocalDateTime localDateTime2 = toLocalDateTime(date2);
        return localDateTime2.until(localDateTime1, ChronoUnit.SECONDS);
    }

    /**
     * 当前时间与指定日期秒差
     *
     * @param date
     * @return
     */
    public static long differenceOfSeconds(Date date) {
        LocalDateTime localDateTime1 = toLocalDateTime(date);
        LocalDateTime localDateTime2 = toLocalDateTime(new Date());
        return localDateTime2.until(localDateTime1, ChronoUnit.SECONDS);
    }


    /**
     * 两个日期毫秒差
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long differenceOfMillis(Date date1, Date date2) {
        LocalDateTime localDateTime1 = toLocalDateTime(date1);
        LocalDateTime localDateTime2 = toLocalDateTime(date2);
        return localDateTime2.until(localDateTime1, ChronoUnit.MILLIS);
    }

    /**
     * 当前时间与指定日期毫秒差
     *
     * @param date
     * @return
     */
    public static long differenceOfMillis(Date date) {
        LocalDateTime localDateTime1 = toLocalDateTime(date);
        LocalDateTime localDateTime2 = toLocalDateTime(new Date());
        return localDateTime2.until(localDateTime1, ChronoUnit.MILLIS);
    }


    /**
     * 获取日期所在月份第一天
     *
     * @param date
     * @return
     */
    public static LocalDate firstDayOfMonth(Date date) {
        LocalDate localDate = toLocalDate(date);
        return localDate.with(TemporalAdjusters.firstDayOfMonth());
    }


    /**
     * 获取日期所在月份第一天
     *
     * @param date
     * @return
     */
    public static LocalDate lastDayOfMonth(Date date) {
        LocalDate localDate = toLocalDate(date);
        return localDate.with(TemporalAdjusters.lastDayOfMonth());
    }


    /**
     * 获取日期所在年份份第一天
     *
     * @param date
     * @return
     */
    public static LocalDate firstDayOfYear(Date date) {
        LocalDate localDate = toLocalDate(date);
        return localDate.with(TemporalAdjusters.firstDayOfYear());
    }


    /**
     * 获取日期所在年份第一天
     *
     * @param date
     * @return
     */
    public static LocalDate lastDayOfYear(Date date) {
        LocalDate localDate = toLocalDate(date);
        return localDate.with(TemporalAdjusters.lastDayOfYear());
    }


    /**
     * 获取当前日期所在月份一共多少天
     *
     * @param date
     * @return
     */
    public static int daysOfMonth(Date date) {
        LocalDate localDate = toLocalDate(date);
        return localDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
    }


    /**
     * 获取当前日期所在年份份一共多少天
     *
     * @param date
     * @return
     */
    public static int daysOfYear(Date date) {
        LocalDate localDate = toLocalDate(date);
        return localDate.with(TemporalAdjusters.lastDayOfYear()).getDayOfYear();
    }


    /**
     * 比较两个时间大小
     * 1 大于 -1 小于 0 等于
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareTo(Date date1, Date date2) {
        LocalDateTime localDateTime1 = toLocalDateTime(date1);
        LocalDateTime localDateTime2 = toLocalDateTime(date2);
        return localDateTime1.compareTo(localDateTime2);
    }


    /**
     * 获取下个星期的今天
     *
     * @param date
     * @return
     */
    public static Date nextWeek(Date date) {
        return nextWeek(date, -1);
    }

    /**
     * 获取上个星期的今天
     *
     * @param date
     * @return
     */
    public static Date previousWeek(Date date) {
        return nextWeek(date, -1);
    }

    /**
     * 获取时间指定多少个星期的这天
     *
     * @param date
     * @return
     */
    public static Date nextWeek(Date date, int weeks) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        LocalDateTime dateTime = localDateTime.minusWeeks(weeks);
        return fromLocalDateTime(dateTime);
    }


    /**
     * 获取下个月的今天
     *
     * @param date
     * @return
     */
    public static Date nextMonth(Date date) {
        return nextMonth(date, -1);
    }

    /**
     * 获取上个月的今天
     *
     * @param date
     * @return
     */
    public static Date previousMonth(Date date) {
        return nextMonth(date, 1);
    }


    /**
     * 获取时间指定多少个月的这天
     *
     * @param date
     * @return
     */
    public static Date nextMonth(Date date, int month) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        LocalDateTime dateTime = localDateTime.minusMonths(month);
        return fromLocalDateTime(dateTime);
    }


    /**
     * 获取明年的今天
     *
     * @param date
     * @return
     */
    public static Date nextYear(Date date) {
        return nextYear(date, -1);
    }

    /**
     * 获取去年的今天
     *
     * @param date
     * @return
     */
    public static Date previousYear(Date date) {
        return nextYear(date, 1);
    }


    /**
     * 获取时间指定多少个年的这天
     *
     * @param date
     * @return
     */
    public static Date nextYear(Date date, int year) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        LocalDateTime dateTime = localDateTime.minusYears(year);
        return fromLocalDateTime(dateTime);
    }


    /**
     * 计算日差
     *
     * @param date1
     * @param date2
     * @return
     */
    private static int periodOfDay(Date date1, Date date2) {
        return periodOfLocalDate(date1, date2, 3);
    }


    /**
     * 计算月差
     *
     * @param date1
     * @param date2
     * @return
     */
    private static int periodOfMonth(Date date1, Date date2) {
        return periodOfLocalDate(date1, date2, 2);
    }


    /**
     * 计算年差
     *
     * @param date1
     * @param date2
     * @return
     */
    private static int periodOfYear(Date date1, Date date2) {
        return periodOfLocalDate(date1, date2, 1);
    }


    /**
     * 计算日期差
     *
     * @param date1
     * @param date2
     * @param type
     * @return
     */
    private static int periodOfLocalDate(Date date1, Date date2, int type) {
        LocalDate localDate1 = toLocalDate(date1);
        LocalDate localDate2 = toLocalDate(date2);
        Period period = Period.between(localDate1, localDate2);
        switch (type) {
            case 1:
                return period.getYears();
            case 2:
                return period.getMonths();
            case 3:
                return period.getDays();
        }
        return -1;
    }


    /**
     * 从机器时间转时间
     *
     * @param instant
     * @return
     */
    public static Date fromInstant(Instant instant) {
        return new Date(instant.toEpochMilli());
    }


    /**
     * 时间转机器时间
     *
     * @param date
     * @return
     */
    public static Instant toInstant(Date date) {
        return Instant.ofEpochMilli(date.getTime());
    }

    /**
     * 解析字符串日期
     *
     * @param dateStr
     * @return
     */
    public static LocalDate parseToLocalDate(String dateStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = format.parse(dateStr);
            return toLocalDate(parse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解析字符串日期
     *
     * @param dateStr
     * @return
     */
    public static LocalDateTime parseToLocalDateTime(String dateStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parse = format.parse(dateStr);
            return toLocalDateTime(parse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析字符串日期
     *
     * @param dateStr
     * @return
     */
    public static LocalTime parseToLocalTime(String dateStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
            Date parse = format.parse(dateStr);
            return toLocalTime(parse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解析字符串日期
     *
     * @param dateStr
     * @return
     */
    public static Date parse(String dateStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return format.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 转换指定时间
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static LocalDate ofLocalDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }


    /**
     * 转换指定时间
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static LocalDateTime ofLocalDateTime(int year, int month, int day, int hour, int minute, int second) {
        return LocalDateTime.of(year, month, day, hour, minute, second);
    }


    /**
     * 转换指定时间
     *
     * @return
     */
    public static LocalTime ofLocalTime(int hour, int minute, int second) {
        return LocalTime.of(hour, minute, second);
    }


    /**
     * 增加指定小时
     *
     * @param hours
     * @return
     */
    public static LocalTime plusHours(int hours) {
        LocalTime now = LocalTime.now();
        return now.plusHours(hours);
    }


    /**
     * 增加指定分钟
     *
     * @param minutes
     * @return
     */
    public static LocalTime plusMinutes(int minutes) {
        LocalTime now = LocalTime.now();
        return now.plusMinutes(minutes);
    }


    /**
     * 增加指定秒数
     *
     * @param seconds
     * @return
     */
    public static LocalTime plusSeconds(int seconds) {
        LocalTime now = LocalTime.now();
        return now.plusSeconds(seconds);
    }


    /**
     * 增加指定小时
     *
     * @param hours
     * @return
     */
    public static LocalTime plusHours(Date date, int hours) {
        LocalTime localTime = toLocalTime(date);
        return localTime.plusHours(hours);
    }


    /**
     * 增加指定分钟
     *
     * @param minutes
     * @return
     */
    public static LocalTime plusMinutes(Date date, int minutes) {
        LocalTime localTime = toLocalTime(date);
        return localTime.plusMinutes(minutes);
    }


    /**
     * 增加指定秒数
     *
     * @param seconds
     * @return
     */
    public static LocalTime plusSeconds(Date date, int seconds) {
        LocalTime localTime = toLocalTime(date);
        return localTime.plusSeconds(seconds);
    }


    /**
     * 增加指定小时
     *
     * @param hours
     * @return
     */
    public static Date plusHoursToDate(Date date, int hours) {
        LocalTime localTime = toLocalTime(date);
        LocalTime localTime1 = localTime.plusHours(hours);
        return fromLocalTime(localTime1);
    }


    /**
     * 增加指定分钟
     *
     * @param minutes
     * @return
     */
    public static Date plusMinutesTo(Date date, int minutes) {
        LocalTime localTime = toLocalTime(date);
        LocalTime localTime1 = localTime.plusMinutes(minutes);
        return fromLocalTime(localTime1);
    }


    /**
     * 增加指定秒数
     *
     * @param seconds
     * @return
     */
    public static Date plusSecondsToDate(Date date, int seconds) {
        LocalTime localTime = toLocalTime(date);
        LocalTime localTime1 = localTime.plusMinutes(seconds);
        return fromLocalTime(localTime1);
    }

    /**
     * 减去指定小时
     *
     * @param hours
     * @return
     */
    public static Date minusHoursToDate(Date date, int hours) {
        LocalTime localTime = toLocalTime(date);
        LocalTime localTime1 = localTime.minusHours(hours);
        return fromLocalTime(localTime1);
    }

    /**
     * 减去指定分钟
     *
     * @param minutes
     * @return
     */
    public static Date minusMinutesToDate(Date date, int minutes) {
        LocalTime localTime = toLocalTime(date);
        LocalTime localTime1 = localTime.minusMinutes(minutes);
        return fromLocalTime(localTime1);
    }

    /**
     * 减去指定分钟
     *
     * @param seconds
     * @return
     */
    public static Date minusSecondsToDate(Date date, int seconds) {
        LocalTime localTime = toLocalTime(date);
        LocalTime localTime1 = localTime.minusSeconds(seconds);
        return fromLocalTime(localTime1);
    }


    /**
     * 减去指定天
     *
     * @param days
     * @return
     */
    public static Date minusDaysToDate(Date date, int days) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        LocalDateTime dateTime = localDateTime.minusDays(days);
        return fromLocalDateTime(dateTime);
    }

    /**
     * 减去指定月
     *
     * @param months
     * @return
     */
    public static Date minusMonthsToDate(Date date, int months) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        LocalDateTime dateTime = localDateTime.minusMonths(months);
        return fromLocalDateTime(dateTime);
    }


    /**
     * 减去指定月
     *
     * @param years
     * @return
     */
    public static Date minusYearsToDate(Date date, int years) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        LocalDateTime dateTime = localDateTime.minusYears(years);
        return fromLocalDateTime(dateTime);
    }


    /**
     * 减去指定年
     *
     * @param years
     * @return
     */
    public static Date minusYearsToDate(LocalDate date, int years) {
        LocalDate newDate = date.minusYears(years);
        return fromLocalDate(newDate);
    }



    /**
     * 减去指定年
     *
     * @param years
     * @return
     */
    public static Date minusYearsToDate(int years) {
        LocalDate newDate = LocalDate.now().minusYears(years);
        return fromLocalDate(newDate);
    }


    /**
     * 减去指定月
     *
     * @param months
     * @return
     */
    public static Date minusMonthsToDate(LocalDate date, int months) {
        LocalDate newDate = date.minusMonths(months);
        return fromLocalDate(newDate);
    }

    /**
     * 减去指定月
     *
     * @param months
     * @return
     */
    public static Date minusMonthsToDate(int months) {
        LocalDate newDate = LocalDate.now().minusMonths(months);
        return fromLocalDate(newDate);
    }

    /**
     * 减去指定天
     *
     * @param days
     * @return
     */
    public static Date minusDaysToDate(LocalDate date, int days) {
        LocalDate newDate = date.minusDays(days);
        return fromLocalDate(newDate);
    }


    /**
     * 减去指定天
     *
     * @param days
     * @return
     */
    public static Date minusDaysToDate(int days) {
        LocalDate newDate = LocalDate.now().minusDays(days);
        return fromLocalDate(newDate);
    }


    /**
     * 减去指定星期
     *
     * @param weeks
     * @return
     */
    public static Date minusWeeksToDate(LocalDate date, int weeks) {
        LocalDate newDate = date.minusWeeks(weeks);
        return fromLocalDate(newDate);
    }

    /**
     * 减去指定星期
     *
     * @param weeks
     * @return
     */
    public static Date minusWeeksToDate(int weeks) {
        LocalDate newDate = LocalDate.now().minusWeeks(weeks);
        return fromLocalDate(newDate);
    }


    /**
     * 是否在指定时间之前
     *
     * @param date
     * @return
     */
    public static boolean isBefore(Date date) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        LocalDateTime now = LocalDateTime.now();
        return now.isBefore(localDateTime);
    }


    /**
     * 是否再定日期之前
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isBefore(Date date1, Date date2) {
        LocalDateTime localDateTime1 = toLocalDateTime(date1);
        LocalDateTime localDateTime2 = toLocalDateTime(date2);
        return localDateTime1.isBefore(localDateTime2);
    }


    /**
     * 是否在指定时间之后
     *
     * @param date
     * @return
     */
    public static boolean isAfter(Date date) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(localDateTime);
    }


    /**
     * 是否再定日期之后
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isAfter(Date date1, Date date2) {
        LocalDateTime localDateTime1 = toLocalDateTime(date1);
        LocalDateTime localDateTime2 = toLocalDateTime(date2);
        return localDateTime1.isBefore(localDateTime2);
    }


    /**
     * 判断指定时间是否是闰年
     *
     * @param date
     * @return
     */
    public static boolean isLeapYear(Date date) {
        LocalDate localDate = toLocalDate(date);
        return localDate.isLeapYear();
    }

    /**
     * 判断当前时间是否是闰年
     *
     * @return
     */
    public static boolean isLeapYear() {
        LocalDate localDate = LocalDate.now();
        return localDate.isLeapYear();
    }


    /**
     * 获取时间戳
     *
     * @return
     */
    public static Instant timeStamp() {
        Instant instant = Instant.now();
        return instant;
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public static Instant timeStamp(Integer hour) {
        Instant instant = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(hour));
        return instant;
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public static Long timeStampToLong10() {
        Instant instant = Instant.now();
        return instant.getEpochSecond();
    }


    /**
     * 转换 LocalDateTime
     *
     * @param date
     * @return
     */
    public static String toLocalDateTimeStr(Date date) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        String format = localDateTime.format(formatter);
        return format;
    }

    /**
     * 转换 LocalDateTimeStr
     *
     * @param localDateTime
     * @return
     */
    public static String toLocalDateTimeStr(LocalDateTime localDateTime) {
        String format = localDateTime.format(formatter);
        return format;
    }


    /**
     * 转换 LocalTime
     *
     * @param date
     * @return
     */
    public static String toLocalTimeStr(Date date) {
        LocalTime localTime = toLocalTime(date);
        String format = localTime.format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        return format;
    }

    /**
     * 转换 LocalTime
     *
     * @param date
     * @return
     */
    public static String toLocalDateStr(Date date) {
        LocalDate localDate = toLocalDate(date);
        String format = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return format;
    }


    /**
     * 转换 LocalDateTime
     *
     * @param date
     * @return
     */
    public static String toLocalDateTimeStr(Date date, String pattern) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        String format = localDateTime.format(DateTimeFormatter.ofPattern(pattern));
        return format;
    }

    /**
     * 转换 LocalDateTime
     *
     * @param localDateTime
     * @return
     */
    public static String toLocalDateTimeStr(LocalDateTime localDateTime, String pattern) {
        String format = localDateTime.format(DateTimeFormatter.ofPattern(pattern));
        return format;
    }

    /**
     * 转换字符串
     *
     * @param date
     * @return
     */
    public static String toStr(Date date, String pattern) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        String format = localDateTime.format(DateTimeFormatter.ofPattern(pattern));
        return format;
    }

    /**
     * 转换字符串
     *
     * @param date
     * @return
     */
    public static String toStr(Date date) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        String format = localDateTime.format(DateTimeFormatter.ofPattern(DEF_DATE_PAT));
        return format;
    }
    /**
     * 转换字符串
     *
     * @param pattern
     * @return
     */
    public static String toStr(String pattern) {
        LocalDateTime localDateTime = toLocalDateTime(new Date());
        String format = localDateTime.format(DateTimeFormatter.ofPattern(pattern));
        return format;
    }


    /**
     * 转换 LocalTime
     *
     * @param date
     * @return
     */
    public static String toLocalTimeStr(Date date, String pattern) {
        LocalTime localTime = toLocalTime(date);
        String format = localTime.format(DateTimeFormatter.ofPattern(pattern));
        return format;
    }

    /**
     * 转换 LocalTime
     *
     * @param date
     * @return
     */
    public static String toLocalDateStr(Date date, String pattern) {
        LocalDate localDate = toLocalDate(date);
        String format = localDate.format(DateTimeFormatter.ofPattern(pattern));
        return format;
    }

}

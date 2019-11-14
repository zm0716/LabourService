package com.agilefinger.labourservice.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by 86251 on 2019/4/19.
 */

public class DateFormatUtils {

    public static String formatDeadline3(int hour) {
        long eTime = System.currentTimeMillis() + hour * 60 * 60 * 1000;
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(new Date(eTime)) + " 23:59";
    }

    public static String formatDeadline4(int hour) {
        long eTime = System.currentTimeMillis() + hour * 60 * 60 * 1000;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(eTime)) + " 23:59:59";
    }

    public static String formatDeadline(int hour) {
        long eTime = System.currentTimeMillis() + hour * 60 * 60 * 1000;
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return format.format(new Date(eTime));
    }

    public static String formatDeadline2(int hour) {
        long eTime = System.currentTimeMillis() + hour * 60 * 60 * 1000;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(eTime));
    }

    public static String formatDate(Calendar calendar) {
        Calendar cal = calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static String formatDate2(Calendar calendar) {
        Calendar cal = calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        return format.format(date)+" 23:59:59";
    }


    public static String formatDateEnd(Calendar calendar) {
        Calendar cal = calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date) + " 23:59:59";
    }

    public static String formatDate_YMD(Calendar calendar) {
        Calendar cal = calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年-MM月-dd日");
        return format.format(date);
    }

    public static String formatDeadline(Calendar date) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int dayOfMonth = date.get(Calendar.DAY_OF_MONTH);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);
        return String.format(Locale.getDefault(), "%d年%02d月%02d日 %02d:%02d", year, month + 1, dayOfMonth, hour, minute);
    }

    public static Calendar postponeYears(int year) {
        Calendar calendar = new GregorianCalendar();
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(calendar.YEAR, year);//把日期往后增加一年.整数往后推,负数往前移动
        return calendar;
    }

    public static String nearMonth(int month) {
        month = month * 30 + 1;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -month);
        return format.format(calendar.getTime()) + " 00:00:00";
    }

    public static String nearMonth2(int month) {
        month = month * 30;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, month);
        return format.format(calendar.getTime()) + " 00:00:00";
    }

    public static String nearWeek(int week) {
        week = week * 7 ;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, week);
        return format.format(calendar.getTime()) + " 00:00:00";
    }

}

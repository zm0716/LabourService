package com.agilefinger.labourservice.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

 /**
      * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
      */
 public static String convertTimeToFormat(String timeStamp) {
long curTime =System.currentTimeMillis()/1000 ;
long time = curTime - Long.parseLong(timeStamp);
if (time < 60 && time >= 0) {
 return "刚刚";
} else if (time >= 60 && time < 3600) {
return time / 60 + "分钟前";
 } else if (time >= 3600 && time < 3600 * 24) {
return time / 3600 + "小时前";
} else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
 return time / 3600 / 24 + "天前";
 } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
return time / 3600 / 24 / 30 + "个月前";
} else if (time >= 3600 * 24 * 30 * 12) {
 return time / 3600 / 24 / 30 / 12 + "年前";
}else {
 return curTime+"-"+Long.parseLong(timeStamp)*1000+"-"+time+"";
 }


 }


 /**
      * 时间戳转化为日期格式字符串
      */
 public static String timeStampToDate(String seconds, String format) {
 if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
 return "";
 }
 if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
 SimpleDateFormat sdf = new SimpleDateFormat(format);
return sdf.format(new Date(Long.valueOf(seconds+"000")));
}
 /**
      * 日期格式字符串转换成时间戳
      */
  public static String dateToTimeStamp(String date_str, String format){
try {
 SimpleDateFormat sdf = new SimpleDateFormat(format);
return String.valueOf(sdf.parse(date_str).getTime()/1000);
} catch (Exception e) {
 e.printStackTrace();
}
 return "";
 }


 /**
      * 取得当前时间戳（精确到秒）
      * @return
      */
 public static String timeStamp(){
 long time = System.currentTimeMillis();
 String t = String.valueOf(time/1000);
 return t;
}


 /**
      * 取得指定日期时间戳（精确到秒）
      * @return
      */
    public static String timeStamp1(String s)  {
        long time = 0;
        try {
            time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String t = String.valueOf(time/1000);
        return t;
 }
    /**
     * 根据日期计算周几
     *
     * @param strParams
     * @return
     */
    public static String caculateWeeks(String strParams) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(strParams));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }
        return Week;
    }

}

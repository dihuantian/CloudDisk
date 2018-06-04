package com.cloud.disk.unit;

import com.cloud.disk.domain.RecycleBin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/6/1
 * Time: 23:01
 */
public class CronDateShift {

    /**
     * cron转换成具体时间
     *
     * @param cron cron表达式
     * @return 返回具体时间
     */
    public static Date cronSwitchDate(String cron) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = simpleDateFormat.parse(cron);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    /**
     * 时间转换成cron
     *
     * @param date
     * @return
     */
    public static String dateSwitchCron(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(date);
    }

    public static int differentDaysByMillisecond(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int days = (int) ((date.getTime() - calendar.getTime().getTime()) / (1000 * 3600 * 24));
        return days;
    }

    public static boolean dateEquality(Calendar calendar1, Calendar calendar2) {
        if (calendar1.getWeekYear() != calendar2.getWeekYear())
            return false;
        if (calendar1.get(Calendar.MONTH) != calendar2.get(Calendar.MONTH))
            return false;
        if (calendar1.get(Calendar.DATE) != calendar2.get(Calendar.DATE))
            return false;
        return true;
    }
}

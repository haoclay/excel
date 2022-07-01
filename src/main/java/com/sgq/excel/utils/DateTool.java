package com.sgq.excel.utils;

import cn.hutool.core.date.DateTime;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author : SGQ
 * @date : 2022-05-01 00:57
 **/
public class DateTool {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date stringToDate(String time){
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String dateToString(Date time){
        return sdf.format(time);
    }

    public static String longToDate(long lo){

        Date date = new Date(lo);

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sd.format(date);

    }
    public static String getLongToDate(long lo){

        Date date = new Date(lo);

        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");

        return sd.format(date);

    }

    public static void main(String[] args) {
        System.out.println(longToDate(1656259200));
    }

}
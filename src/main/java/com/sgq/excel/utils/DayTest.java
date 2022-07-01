package com.sgq.excel.utils;

/**
 * @author : SGQ
 * @date : 2022-05-12 15:38
 **/
public class DayTest {

    public static void main(String[] args) {
        String s = "day02";
       String [] arr = s.split("day");
        String path = "day"+arr[1].substring(0,2);
        System.out.println(arr.length);
        System.out.println(path);
        System.out.println(arr[0]);
        System.out.println(arr[1]);
    }
}
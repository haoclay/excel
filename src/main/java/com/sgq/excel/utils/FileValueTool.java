package com.sgq.excel.utils;

import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : SGQ
 * @date : 2022-04-30 20:12
 **/
public class FileValueTool {
    //所有作业的day
    private static List<String> dayList =  new ArrayList<>();
    //所有作业excel的路径
    private static List<String> fileNameList =  new ArrayList<>();


//    private static Map map = new HashMap();
    //通过班级作业所在文件夹获取day
    public static Map<String,List<String>> getDayByFileName(String clazzPath,Map map){

        File file = new File(clazzPath);
        if(!file.exists()){
            System.out.println("当前系统不存在该路径...");
        }else {
            File[] files = file.listFiles();
            for (File temp : files) {
                if(!temp.isDirectory()){
                    if(temp.getName().endsWith(".xls")||temp.getName().endsWith(".xlsx")) {
                        fileNameList.add(temp.getAbsolutePath());
                        String[] str = temp.getName().split("day");
                        String path = "day"+str[1].substring(0,2);
                        dayList.add(path);
                    }

                }else {
                    getDayByFileName(temp.getAbsolutePath(),map);

                }
            }

        }
        map.put("dayList",dayList);
        map.put("fileNameList",fileNameList);
       
        return map;
    }

    public static void main(String[] args) {

       Map<String,List<String>> map = new HashMap<>();
       Map<String,List<String>>  map1 = getDayByFileName("D:\\xxx\\homework\\vip9",map);
        map1.get("fileNameList").forEach(s -> System.out.println(s));
        int size = map1.size();
        System.out.println("size = " + size);
        map1.get("dayList").forEach(s -> System.out.println(s));
        int size2 = map1.get("dayList").size();
        System.out.println(size2+"size2================");
    }
}
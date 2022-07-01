package com.sgq.excel.utils;

import com.sgq.excel.pojo.HomeWorkContent;
import com.sgq.excel.pojo.IndexList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : SGQ
 * @date : 2022-06-22 21:04
 **/
public class IndexListToHomeWorkContent {

    public static List<HomeWorkContent> transform(List<IndexList> list,List<Integer> dayIndexList,String clazz){
       List<HomeWorkContent> result = new ArrayList<>();
        int index = 0 ;
        for (int i = list.size() - 1; i >= 0; i--) {
            HomeWorkContent temp = new HomeWorkContent();
            IndexList indexList = list.get(i);
            temp.setClazz(clazz);
            temp.setTitle(indexList.getTitle());
            temp.setDayIndex(dayIndexList.get(index));
            temp.setCreateTime(Long.valueOf(indexList.getCreateTime()));
            temp.setEndTime(Long.parseLong(indexList.getEndTime()));
            result.add(temp);
            index++;
        }

        return result;
    }
}
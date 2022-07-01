package com.sgq.excel.service;

import com.sgq.excel.bean.StuHomeWorkBean;
import com.sgq.excel.pojo.StuHomeWork;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : SGQ
 * @date : 2022-05-01 00:25
 **/


public interface IStuHomeWorkService {
    //下载excel到指定目录解析后插入数据库
    Integer addBatch(String clazz);

    List<StuHomeWorkBean> findByMap(Map map);

    //直接爬虫插入数据库 String clazz,Integer startDay,Integer endDay
    Integer addBatchBySpider(Map map);

    //按照条件删除班级的作业(班级必备,起始信息可不设置)
    Integer removeByMap(Map map);

    //事务
   boolean removeAndAdd(Map map);

    Map<String,List> findTitleAndDayIndex(String clazz);

    Map<String,List> findTitleAndDayIndexDB(Map map);

    String findMaxUpdateTime(String clazz);

    List<StuHomeWork> findDetailed(Map map);
}
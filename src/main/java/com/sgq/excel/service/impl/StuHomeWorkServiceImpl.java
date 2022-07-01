package com.sgq.excel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.sgq.excel.bean.StuHomeWorkBean;
import com.sgq.excel.bean.TitleDayBean;
import com.sgq.excel.listener.StuHomeWorkListener;
import com.sgq.excel.mapper.StuHomeWorkMapper;
import com.sgq.excel.pojo.StuHomeWork;
import com.sgq.excel.service.IStuHomeWorkService;
import com.sgq.excel.utils.DateTool;
import com.sgq.excel.utils.FileValueTool;
import com.sgq.excel.utils.StuHomeWorkCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author : SGQ
 * @date : 2022-05-01 00:26
 **/

@Service
public class StuHomeWorkServiceImpl implements IStuHomeWorkService {
    @Autowired
    private StuHomeWorkMapper mapper;

    @Autowired
    private StuHomeWorkCreator stuHomeWorkCreator;

    private static final String PRE_PATH = "D:\\xxx\\homework\\";

    private List<StuHomeWork> list = new ArrayList<StuHomeWork>();

    private static final Integer COUNT = 50;

    @Override
    public Integer addBatch(String clazz) {

        Map map = new HashMap<>();
        Map<String, List<String>> resultMap = FileValueTool.getDayByFileName(PRE_PATH+clazz, map);
        List<String> dayList = resultMap.get("dayList");
        List<String> fileNameList = resultMap.get("fileNameList");
        for (String day : dayList) {
            for (String fileName : fileNameList) {
                if(fileName.contains(day)){
                    System.out.println("===================开始解析"+day+"=========================");
                    EasyExcel.read(fileName, StuHomeWork.class, new StuHomeWorkListener(mapper,clazz, day)).sheet().doRead();

                }
            }
        }



        return null;
    }

    @Override
    public List<StuHomeWorkBean> findByMap(Map map) {
        return mapper.selectSubmitCountByMap(map);
    }
    /*
    * 第一次不满足50条直接插入,
    * 每50条插入一次,
    * 最后一次不满足50条直接插入
    * */
    @Override
    public Integer addBatchBySpider(Map map) {

        List<StuHomeWork> stuHomeWorkList =  stuHomeWorkCreator.creatData(map);
        System.out.println("长度---->"+stuHomeWorkList.size());
        int count = 0 ;
        int dev = stuHomeWorkList.size() / COUNT ;
        int left = stuHomeWorkList.size() % COUNT  ;
        int clearCount = 0;
        if (dev == 0){
            mapper.insertBatch(stuHomeWorkList);
        }else {
            for (int i = 0; i < stuHomeWorkList.size(); i++) {
                list.add(stuHomeWorkList.get(i));
                count++;
                if(clearCount == dev){
                    if(count == left) {
                        mapper.insertBatch(list);
                        list.clear();
                    }
                }else {
                    if(count == COUNT ){
                        mapper.insertBatch(list);
                        list.clear();
                        clearCount++;
                        count = 0 ;
                    }
                }

            }
        }


        return stuHomeWorkList.size();
    }

    @Override
    public Integer removeByMap(Map map) {
        return mapper.deleteByBatchMap(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class,transactionManager = "txManager1")
    public boolean removeAndAdd(Map map) {
        removeByMap(map);
        int sum1 =  addBatchBySpider(map);
        int sum2 = (((Integer) map.get("endDay")) - ((Integer) map.get("startDay")) + 1)*stuHomeWorkCreator.getTotal();
        System.out.println("sum1---->"+sum1);
        System.out.println("sum2---->"+sum2);
        if(sum1==sum2)
            return true;
        return false;
    }

    @Override
    public Map<String, List> findTitleAndDayIndex(String clazz) {
        stuHomeWorkCreator.getIndex(clazz);
        return  stuHomeWorkCreator.getTileAndDayIndex();

    }

    @Override
    public Map<String, List> findTitleAndDayIndexDB(Map map) {
        List<TitleDayBean> titleDayBeanList = mapper.findTitleAndDayIndexDB(map);
        List<String> titleList = new ArrayList<>();
        List<Integer> dayIndexList = new ArrayList<>();
        System.out.println("TitleDayBean--------->"+titleDayBeanList);
        for (TitleDayBean titleDayBean : titleDayBeanList) {
            titleList.add(titleDayBean.getTitle());
            dayIndexList.add(titleDayBean.getDay());
        }

        Map result =  new HashMap(){{
            put("titleList",titleList);
            put("dayIndexList",dayIndexList);
        }};
        return result;
    }

    @Override
    public String findMaxUpdateTime(String clazz) {
        Date date = mapper.selectMaxUpdateTime(clazz);
        if(date != null){
            return DateTool.dateToString(date);
        }
        return null;

    }

    @Override
    public List<StuHomeWork> findDetailed(Map map) {
        return mapper.selectTitleAndState(map);
    }
}
package com.sgq.excel.utils;

import cn.hutool.core.lang.Console;
import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sgq.excel.bean.SubmitPointsBean;
import com.sgq.excel.pojo.*;
import com.sgq.excel.service.IHomeWorkContentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author : SGQ
 * @date : 2022-06-22 20:29
 **/
/*@Component
@SpringBootTest
@RunWith(SpringRunner.class)
@MapperScan("com.sgq.excel.mapper")*/
public class HomeWorkSpider {

    @Autowired
    private IHomeWorkContentService service;

    //老师登录后获得的token 必填
    private static String token = PropertiesTool.getCourseId("token");

    //获得作业目录数据,获得作业id列表
    private List<IndexList> indexLists ;

    //作业名称
    private ArrayList<String> titleList = new ArrayList();

    //作业索引
    private ArrayList<Integer> dayIndexList = new ArrayList();

    //创建作业id集合
    private List<String> classIdList = new LinkedList<>();

    //每天需插入的作业条数
    private Integer total = 0;

    public Integer getTotal() {
        return total;
    }

    //1 -> 获取每天作业目录列表，并为填充作业id列表,作业名称列表,索引列表
    public synchronized IndexJson getIndex(String clazz) {

        clearList();

        String json = JSONObject.toJSONString(getIndexSelect(clazz));
        //发起请求
        HttpResponse res = HttpRequest.post("https://openapiv5.ketangpai.com//FutureV2/CourseMeans/getCourseContent")
                .header("token", token)
                .body(json)
                .execute();
        String returnJson = UnicodeUtil.toString(res.body());
        //获得数据 封装为data
        IndexJson data = JSON.parseObject(returnJson, IndexJson.class);
        //作业目录 =>{'作业id' ,'作业名称title'}
        indexLists = data.getData().getList();
        int indexCount = 0 ;
        for (int i = indexLists.size() - 1; i >= 0; i--) {
            //倒序添加
            classIdList.add(indexLists.get(i).getId());
            titleList.add(indexLists.get(i).getTitle());
            dayIndexList.add(indexCount);
            indexCount++;
        }
        Console.log(data);
        System.out.println(classIdList);
        System.out.println(classIdList.size());
        System.out.println(titleList);
        System.out.println(dayIndexList);
        return data;
    }

    //2 -> 每个请求进来必先设置CourseId,通过clazz获取请求体数据
    private IndexSelect getIndexSelect(String clazz){
        return  new IndexSelect().setCourseid(PropertiesTool.getCourseId(clazz))
                .setContenttype(4)
                .setDirid(0)
                .setPage(1)
                .setLimit(100)
                .setDesc(3)
                .setCourserole(1);
    }

    //每一个进来的请求需设置班级

    //访问一次返回作业列表JSON
    public ReturnJson attemptRequestOnce(String clazz){

        IndexJson indexJson = getIndex(clazz);
        HomeworkSelect homeworkSelect = new HomeworkSelect();
        homeworkSelect.setHomeworkid(indexJson.getData().getList().get(0).getId())
                .setPageSize(200)
                .setCurrentPage(1);
        return getData(homeworkSelect, token);

    }


    //获取最大页数
    public  int getMaxPage(String clazz){
        total = attemptRequestOnce(clazz).getData().getTotal();
        System.out.println("这是在获取最大作业数:"+total);
        return total %200 == 0?(total/200):(total/200 +1);
    }

    //获取标题列表&日期索引列表
    public Map<String,List> getTileAndDayIndex(){
        //通过班级获得token =>

        Map map =  new HashMap(){{
            put("titleList",titleList);
            put("dayIndexList",dayIndexList);
        }};
        return map;
    }


    //在前端接收的起始时间都 -1后再赋值
    public List<StuHomeWork> creatData(Map map) {
        String clazz = map.get("clazz").toString();
        Integer startDay= (Integer) map.get("startDay");
        Integer endDay = (Integer) map.get("endDay");
        //把CourseIdList赋值
        getIndex(clazz);

        //创建请求集合 方便循环访问每一次作业
        List<HomeworkSelect> homeworkSelectList = new LinkedList<>();
        //创建每一次作业对应学生分数的集合
        List<List<User>> homeworkList = new ArrayList<>();

        //获取该班级最大页数
        int maxPage = getMaxPage(clazz) ;
        System.out.println("maxPage = " + maxPage);
        System.out.println("startDay"+startDay);
        System.out.println("endDay"+endDay);
        System.out.println("classIdList-->"+classIdList);
        //把每一个请求设置对应的CourseId
        for (int i = startDay; i <= endDay; i++) {
            for (int currentPage = 1; currentPage <= maxPage; currentPage++) {

                HomeworkSelect homeworkSelect = new HomeworkSelect();
                homeworkSelect.setHomeworkid(classIdList.get(i))
                        .setPageSize(200)
                        .setCurrentPage(currentPage);
                homeworkSelectList.add(homeworkSelect);
            }
        }

        System.out.println("homeworkSelectList--->"+homeworkSelectList);

        //制作好后循环访问，获得作业数据添加到分数集合
        int requestCount = 0 ;
        for (HomeworkSelect select : homeworkSelectList) {
            //获取学生数据
            ReturnJson data = getData(select, token);
            List<User> userList = data.getData().getList();
            //针对每一个user集合的第一个元素设置值,到时候属于这一个集合的元素
            //一律赋值一样
            userList.get(0).setClazz(clazz);
            userList.get(0).setCreateDate(titleList.get(startDay));
            userList.get(0).setDayIndex(startDay);
            System.out.println("startDay"+startDay);
            System.out.println("----->"+userList.size());
            homeworkList.add(userList);
            requestCount++;
            //请求最大次数的页面才是属于同一天的作业，每次只能请求200条
            if(requestCount == maxPage){
                startDay++;
                requestCount = 0;
            }

        }
        System.out.println("最终结果:"+homeworkList);
        return UserToStuHomeWork.getStuHomeWorkList(homeworkList);
    }

    public List<HomeWorkContent> creatContent(String clazz){
        IndexJson indexJson = getIndex(clazz);
        List<IndexList> list = indexJson.getData().getList();
        return IndexListToHomeWorkContent.transform(list,dayIndexList,clazz);
    }

    //获取作业提交详情列表
    public ReturnJson getData(HomeworkSelect homeworkSelect, String token) {
        String json = null;
        json = JSONObject.toJSONString(homeworkSelect);
        //发起请求
        HttpResponse res = HttpRequest.post("https://openapiv51.ketangpai.com/ReviewApi/getlistByhomework")
                .header("token", token)
                .body(json)
                .execute();
        String returnJson = UnicodeUtil.toString(res.body());
        //获得数据 封装为data
        ReturnJson data = JSON.parseObject(returnJson, ReturnJson.class);
        Console.log(data);
        return data;
    }



    public void  clearList(){
        if(classIdList.size() != 0){
            classIdList.clear();
        }

        if(titleList.size() != 0){
            titleList.clear();
        }
        if(dayIndexList.size() != 0){
            dayIndexList.clear();
        }
    }


//    @Test
    public void test(){
       /* List<HomeWorkContent> list = creatContent("vip2202");
        System.out.println(service.addBatch(list) );*/
        List<SubmitPointsBean> vip2202 = service.findSubmitPoints("vip2202");
        System.out.println(vip2202);

    }
}
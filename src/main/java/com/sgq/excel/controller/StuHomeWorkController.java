package com.sgq.excel.controller;

import com.sgq.excel.bean.StuHomeWorkBean;
import com.sgq.excel.pojo.StuHomeWork;
import com.sgq.excel.pojo.StuUpdate;
import com.sgq.excel.service.IHomeWorkContentService;
import com.sgq.excel.service.IStuHomeWorkService;
import com.sgq.excel.service.IStuUpdateService;
import com.sgq.excel.utils.DateTool;
import com.sgq.excel.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : SGQ
 * @date : 2022-05-01 01:05
 **/
@Controller
@RequestMapping("/stuHomeWork")
public class StuHomeWorkController {
    @Autowired
    private IStuHomeWorkService service;

    @Autowired
    private IStuUpdateService stuUpdateService;
    @Autowired
    private IHomeWorkContentService homeWorkContentService;

  /*  @RequestMapping("/queryPage")
    public String queryPage(Model model){
        String updateTime = service.findMaxUpdateTime();
        model.addAttribute("updateTime",updateTime);
        return "page/query";
    }*/

    @RequestMapping("/queryPage")
    public String queryPage(Model model){
        StuUpdate stuUpdate = stuUpdateService.findMaxUpdate("vip2202");
        stuUpdate.setUpdateTimePlus(DateTool.dateToString(stuUpdate.getUpdateTime()));
        model.addAttribute("stuUpdate",stuUpdate);
        return "page/query";
    }
    @RequestMapping("/updatePage")
    public String updatePage(Model model){
        StuUpdate stuUpdate = stuUpdateService.findMaxUpdate("vip2202");
        stuUpdate.setUpdateTimePlus(DateTool.dateToString(stuUpdate.getUpdateTime()));
        model.addAttribute("stuUpdate",stuUpdate);
        return "page/update";
    }

    @RequestMapping("/findUpdateTime")
    @ResponseBody
    public StuUpdate findUpdateTime(String clazz){
        return  stuUpdateService.findMaxUpdate(clazz);
    }
    @RequestMapping("/detailedPage")
    public String detailedPage(Model model,HttpServletRequest request,
                               @RequestParam("clazz") String clazz,
                               @RequestParam("name") String name){
        System.out.println(name);
        System.out.println(clazz);

        Map infoMap = (Map) request.getSession().getAttribute("map");
        Map map = new HashMap(){{
            put("clazz",clazz);
            put("name",name);
            put("start",infoMap.get("start"));
            put("end",infoMap.get("end"));
        }};
        List<StuHomeWork> detailedList = service.findDetailed(map);
        model.addAttribute("detailedList",detailedList);
        System.out.println(detailedList);
        model.addAttribute("map",map);
        return "page/detailed";
    }


    //通过excel插入的接口
    @RequestMapping("/add")
    public void add(){
        service.addBatch("vip9");
    }
    /*
    * 更新哪一个班，哪一天到哪一天的作业
    *
    * */
    @ResponseBody
    @RequestMapping("/addBySpider")
    public Messages addBySpider(String clazz, Integer startDay, Integer endDay){
        Map map = new HashMap(){{
            put("clazz",clazz);
            put("startDay",startDay);
            put("endDay",endDay);
        }};
        Integer sum = service.addBatchBySpider(map);
        Messages message = new Messages();
        message.setState(1);
        message.setInfo("添加"+sum+"条信息成功");
        return message;
    }
    @ResponseBody
    @RequestMapping("/removeAndAdd")
    public Messages removeAndAdd(String clazz,
                                 Integer startDay,
                                 Integer endDay,
                                 String startTitle,
                                 String endTitle){
        Map map = new HashMap(){{
            put("clazz",clazz);
            put("startDay",startDay);
            put("endDay",endDay);
            put("startTitle",startTitle);
            put("endTitle",endTitle);
        }};
        boolean b1 = service.removeAndAdd(map);
        boolean b2 =  homeWorkContentService.addBatchAndRemove(map.get("clazz").toString());
        Messages message = new Messages();
        if(b1 && b2){
            message.setState(1);
            message.setInfo("添加信息成功");
        }else {
            message.setState(0);
            message.setInfo("添加信息失败");
        }
        return message;
    }

    @RequestMapping("/findTitleAndDayIndexFromDB")
    @ResponseBody
    public Map findTitleAndDayIndexFromDB(@RequestParam String clazz){
        Map map = new HashMap(){{
            put("clazz",clazz);
        }};
        return  service.findTitleAndDayIndexDB(map);
    }

    @RequestMapping("/findTitleAndDayIndexFromKTP")
    @ResponseBody
    public Map findTitleAndDayIndexFromKTP(@RequestParam String clazz){
        System.out.println(clazz);
        return  service.findTitleAndDayIndex(clazz);
    }



    @ResponseBody
    @RequestMapping("/findBy")
    public Messages findBy(
                         @RequestParam("clazz") String clazz,
                         @RequestParam("subCount") String subCount,
                         Integer start,
                         Integer end,
                         @RequestParam("startTitle") String startTitle,
                         @RequestParam("endTitle") String endTitle,
                         HttpServletRequest request){
        Map map = new HashMap(){{
            put("clazz",clazz);
            put("subCount",subCount);
            put("start",start);
            put("end",end);
            put("startTitle",startTitle);
            put("endTitle",endTitle);
            put("sumDays",end - start + 1);
        }};

        List<StuHomeWorkBean> homeWorkBeans = service.findByMap(map);
        request.getSession().setAttribute("homeWorkBeans",homeWorkBeans);
        request.getSession().setAttribute("subCount",subCount);
        request.getSession().setAttribute("map",map);
        Messages messages = new Messages();
        if(homeWorkBeans.size()!= 0 && homeWorkBeans!=null){
            messages.setState(1);
            messages.setInfo("查询有效");
        }else {
            messages.setState(0);
            messages.setInfo("查询无效");
        }
        System.out.println("homeWorkBeans = " + homeWorkBeans);

        return messages;
    }

    @RequestMapping("/showHomeWork")
    public String showHomeWork(HttpServletRequest request,Model model){
        model.addAttribute("homeWorkBeans",request.getSession().getAttribute("homeWorkBeans"));
        model.addAttribute("subCount",request.getSession().getAttribute("subCount"));
        model.addAttribute("map",request.getSession().getAttribute("map"));
        return "page/show";
    }

    @RequestMapping("/showByCondition")
    public String showByCondition(@RequestBody Map<String,String> condition, Model model){
        List<StuHomeWorkBean> homeWorkBeans = service.findByMap(condition);
        model.addAttribute("homeWorkBeans",homeWorkBeans);
        System.out.println("homeWorkBeans = " + homeWorkBeans);
        return "/page/stuhomework_list";
    }
}
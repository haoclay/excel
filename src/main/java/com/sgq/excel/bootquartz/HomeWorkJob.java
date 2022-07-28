package com.sgq.excel.bootquartz;

import com.sgq.excel.pojo.HomeWorkContent;
import com.sgq.excel.service.IHomeWorkContentService;
import com.sgq.excel.service.IStuHomeWorkService;
import com.sgq.excel.utils.DateTool;
import com.sgq.excel.utils.MailTool;
import lombok.SneakyThrows;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@DisallowConcurrentExecution
@PersistJobDataAfterExecution
@Component
public class HomeWorkJob extends QuartzJobBean {
    private static final  Integer BEFORE_NUM = 9;



    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        SchedulerContext schedulerContext = context.getScheduler().getContext();

        IStuHomeWorkService stuHomeWorkService = (IStuHomeWorkService)schedulerContext.get("homeWorkService");
        IHomeWorkContentService homeWorkContentService = (IHomeWorkContentService)schedulerContext.get("contentService");

        //定义数据更新范围
        List<HomeWorkContent> contentList = homeWorkContentService.getHomeWorkContentList("vip2202");

        int endIndex = contentList.get(contentList.size()-1).getDayIndex();

        int startIndex = endIndex - BEFORE_NUM ;

        if(startIndex < 0){

            startIndex = 0 ;
        }

        int finalStartIndex = startIndex;
        String startTitle = contentList.get(finalStartIndex).getTitle();
        String endTitle = contentList.get(endIndex).getTitle();
        Map map = new HashMap(){{
            put("clazz","vip2202");
            put("startDay", finalStartIndex);
            put("endDay",endIndex);
            put("startTitle",startTitle);
            put("endTitle",endTitle);

        }};

        boolean b1 = stuHomeWorkService.removeAndAdd(map);

        boolean b2 = homeWorkContentService.addBatchAndRemove("vip2202");

        String updateTime = DateTool.dateToString(new Date());

        String message = "于"+updateTime+ "更新"+startTitle+"~"+endTitle+"作业数据失败!";

        if(b1 && b2 ){
            message =  "于"+updateTime+  "更新"+startTitle+"~"+endTitle+"作业数据成功!";
        }

        Map<String,String> envelope = new HashMap();
        envelope.put("mail_target","837783587@qq.com");
        envelope.put("mail_title","作业更新提醒");
        envelope.put("mail_content",message);
        MailTool.send(envelope);
    }
}
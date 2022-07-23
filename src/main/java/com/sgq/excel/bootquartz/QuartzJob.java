package com.sgq.excel.bootquartz;

import com.sgq.excel.utils.DateTool;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class QuartzJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            Thread.sleep(2000);
            System.out.println(context.getScheduler().getSchedulerInstanceId());
            System.out.println("任务名字:"+context.getJobDetail().getKey().getName());
            System.out.println("执行任务时间:"+ DateTool.dateToString(new Date()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
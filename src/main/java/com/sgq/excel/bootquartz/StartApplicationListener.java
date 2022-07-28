package com.sgq.excel.bootquartz;

import com.sgq.excel.service.IHomeWorkContentService;
import com.sgq.excel.service.IStuHomeWorkService;
import lombok.SneakyThrows;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private Scheduler scheduler;



    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        TriggerKey triggerKey = TriggerKey.triggerKey("trigger_homework","group_homework");

        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);

            if (trigger == null ){
                trigger =  TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .withSchedule(CronScheduleBuilder.cronSchedule("0 20 14 * * ?"))
                        .build();
            }

            JobKey jobKey = JobKey.jobKey("job_homework", "group_homework");
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if(jobDetail == null){
                jobDetail = JobBuilder.newJob(HomeWorkJob.class)
                        .withIdentity(jobKey)
                        .build();
            }
            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        scheduler.start();

    }
}
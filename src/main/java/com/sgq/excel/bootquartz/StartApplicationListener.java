package com.sgq.excel.bootquartz;

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
        TriggerKey triggerKey = TriggerKey.triggerKey("trigger_mail","group_mail");

        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);

            if (trigger == null ){
                trigger =  TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .withSchedule(CronScheduleBuilder.cronSchedule("0 5-10 15 * * ?"))
                        .build();
            }

            JobKey jobKey = JobKey.jobKey("job_mail", "group_mail");
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if(jobDetail == null){
                jobDetail = JobBuilder.newJob(EmailJob.class)
                        .withIdentity("job_mail","group_mail")
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
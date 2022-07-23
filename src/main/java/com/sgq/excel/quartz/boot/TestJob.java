package com.sgq.excel.quartz.boot;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class TestJob {



    public static void main(String[] args) {
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                .withIdentity("job","job")
                .usingJobData("打工","lxyk")
                .usingJobData("address","job111")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger","trigger")
                .startNow()
                .usingJobData("打工","baidu")
                .usingJobData("address","trigger111")
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                .build();

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
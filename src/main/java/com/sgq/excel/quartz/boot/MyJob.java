package com.sgq.excel.quartz.boot;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob implements Job {
    private String address;

    public void setAddress(String address) {
        this.address = address;
    }
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        JobDataMap triggerMap = context.getTrigger().getJobDataMap();
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        System.out.println(jobDataMap.getString("打工"));
        System.out.println(triggerMap.getString("打工"));
        System.out.println("address:"+address);
    }
}
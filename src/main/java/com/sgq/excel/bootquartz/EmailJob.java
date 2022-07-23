package com.sgq.excel.bootquartz;

import com.sgq.excel.utils.MailTool;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;


@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class EmailJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Map<String,String> map = new HashMap(){{
            put("mail_target","1853737225@qq.com");
            put("mail_title","欢迎大家");
            put("mail_content","来到乐学优课");
        }};
        try {
            MailTool.send(map);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
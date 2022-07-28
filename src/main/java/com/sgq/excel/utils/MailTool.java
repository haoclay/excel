package com.sgq.excel.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MailTool {
      private static  Properties properties = new Properties();

      static {
        properties.setProperty("foxmail.host","smtp.163.com");
        properties.setProperty("foxmail.host.mail","shugq1107@163.com");
        properties.setProperty("foxmail.smtp.pwd","WRYCFJZCTFVIQNLV");
        properties.setProperty("foxmail.transport.protocol","smtp");
        properties.setProperty("foxmail.smtp.auth","true");

     }

    public static void main(String[] args) throws MessagingException {
              Map<String,String> map = new HashMap(){{
                  put("mail_target","837783587@qq.com");
                  put("mail_title","欢迎大家");
                  put("mail_content","来到乐学优课");
              }};
              send(map);

    }


    public static void send(Map<String,String> mailMap) throws MessagingException {
          //创建会话
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("foxmail.host.mail"),properties.getProperty("foxmail.smtp.pwd"));
            }
        });

        //通过会话拿到传输对象
        Transport transport =session.getTransport();
        //通过传输对象拿到连接
        transport.connect(properties.getProperty("foxmail.host"),properties.getProperty("foxmail.host.mail"),properties.getProperty("foxmail.smtp.pwd"));
        //创建邮件对象塞进会话
        Message message = new MimeMessage(session);
        //配置邮件对象参数
        message.setFrom(new InternetAddress(properties.getProperty("foxmail.host.mail")));
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(mailMap.get("mail_target")));
        message.setSubject(mailMap.get("mail_title"));
        message.setContent(mailMap.get("mail_content"),"text/html;charset=utf-8");
        //通过传输对象发送
        transport.sendMessage(message,message.getAllRecipients());
        System.out.println("successful");


    }


}

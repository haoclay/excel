package com.sgq.excel.controller;

import com.alibaba.fastjson.JSONObject;
import com.sgq.excel.utils.MailTool;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class EmailController {

    @RequestMapping("/sendEmail")
    public String sendEmail(@RequestParam String target,
                            @RequestParam String title,
                            @RequestParam String content) throws MessagingException {
        ConcurrentHashMap map = new ConcurrentHashMap(){{
            put("mail_target",target);
            put("mail_title",title);
            put("mail_content",content);
        }};
        MailTool.send(map);
        JSONObject json  = new JSONObject();
        json.put("state","1");
        return json.toJSONString();

    }

    @RequestMapping("/emailPage")
    public ModelAndView emailPage(){
        ModelAndView mv = new ModelAndView("/email/page/email");
        return mv;
    }
}

package com.sgq.excel.controller;


import com.sgq.excel.utils.MessageTool;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MessageController {

    @RequestMapping("/messagePage")
    public String messagePage(){
       return "message/login";
    }

    @RequestMapping("/message/send")
    @ResponseBody
    public String send(@RequestParam("phone") String phone) throws Exception {
        return MessageTool.send(phone);
    }
}

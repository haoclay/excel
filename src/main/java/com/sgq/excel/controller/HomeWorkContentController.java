package com.sgq.excel.controller;

import com.sgq.excel.bean.SubmitPointsBean;
import com.sgq.excel.service.IHomeWorkContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author : SGQ
 * @date : 2022-06-24 14:16
 **/

@Controller
@RequestMapping("/stuHomeWork")
public class HomeWorkContentController {

    @Autowired
    private IHomeWorkContentService service;

    @RequestMapping("/showSubmitPoints")
    public String showSubmitPoints(@RequestParam("clazz") String clazz,
                                      Model model){
        List<SubmitPointsBean> submitPoints = service.findSubmitPoints(clazz);
        model.addAttribute("submitPoints",submitPoints);
        model.addAttribute("clazz",clazz);
        System.out.println("submitPoints = " + submitPoints);
        return "page/submit";
    }
}
package com.sgq.excel.bean;

import lombok.Data;

/**
 * @author : SGQ
 * @date : 2022-06-24 13:53
 **/

@Data
public class SubmitPointsBean {

    private String clazz;

    private String name;
    //总积分
    private Integer points;
   //占每次作业总分百分比
    private Double percentage;

}
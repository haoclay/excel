package com.sgq.excel.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 作者：Rem
 * 时间：2021/12/21 17:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String username;
    private String grade;
    //作业创建名称
    private String createDate;
    private String clazz;
    private String remake;
    private Integer dayIndex;
    private Long updateTime;



}

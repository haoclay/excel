package com.sgq.excel.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sgq.excel.utils.LocalDateTimeConverter;
import lombok.Data;

import java.util.Date;

/**
 * @author : SGQ
 * @date : 2022-04-30 19:31
 **/
@Data
@TableName(value = "stu_homework")
public class StuHomeWork {

    @TableId(type = IdType.AUTO)
    private Integer id;


    @TableField("name")
    @ExcelProperty(index = 2)
    private String name;

    //从班级文件名称夹获取
    @TableField("clazz")
    private String clazz;

    //从作业excel名称获取
    @TableField("create_date")
    private String createDate;

    //已提交或未提交
    @TableField("state")
    @ExcelProperty("成绩")
    private String state;

    //提交时间
    @TableField("submit_date")
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    @ExcelProperty(index = 6 )
    private Date submitDate;

    @TableField("remark")
    private Date remark;

    @TableField("day_index")
    private Integer dayIndex;

    @TableField("uuid")
    private String uuid;

    @TableField("update_time")
    private Long updateTime;
}
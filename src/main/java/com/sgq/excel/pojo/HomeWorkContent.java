package com.sgq.excel.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;



/**
 * @author : SGQ
 * @date : 2022-06-22 21:00
 **/

@Data
@TableName("homework_content")
public class HomeWorkContent {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("clazz")
    private String clazz;

    @TableField("day_index")
    private Integer dayIndex;

    @TableField("title")
    private String title;

    @TableField("create_time")
    private Long createTime;

    @TableField("end_time")
    private Long endTime;
}
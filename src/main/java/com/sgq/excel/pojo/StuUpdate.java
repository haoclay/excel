package com.sgq.excel.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sgq.excel.utils.DateTool;
import lombok.Data;

import java.util.Date;

/**
 * @author : SGQ
 * @date : 2022-06-03 22:35
 **/
@Data
@TableName("stu_update")
public class StuUpdate {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("clazz")
    private String clazz;

    @TableField("start_title")
    private String startTime;

    @TableField("end_title")
    private String endTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("remark")
    private String remark;

    private String updateTimePlus;

    @Override
    public String toString() {
        return "StuUpdate{" +
                "id=" + id +
                ", clazz='" + clazz + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", updateTime=" + DateTool.dateToString(updateTime) +
                ", remark='" + remark + '\'' +
                '}';
    }
}
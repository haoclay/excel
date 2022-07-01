package com.sgq.excel.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author : SGQ
 * @date : 2022-06-22 16:56
 **/

@Data
public class PointsUpdate {

    private Integer id;

    private String uuid;

    private Integer dayIndex;

    private Integer influenceValue;

    private Date updateTime;
}
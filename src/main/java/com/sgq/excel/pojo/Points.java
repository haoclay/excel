package com.sgq.excel.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author : SGQ
 * @date : 2022-06-22 16:53
 **/

@Data
public class Points {

    private Integer id;

    private String uuid;

    private Integer pointsValue;

    private Date updateTime ;
}
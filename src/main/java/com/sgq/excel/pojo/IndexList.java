package com.sgq.excel.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 作者：Rem
 * 时间：2021/12/22 3:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexList {
    private String title;
    private String id;
    private Integer dayIndex;
    private String createTime;
    private String endTime;
}
package com.sgq.excel.service;

import com.sgq.excel.bean.SubmitPointsBean;
import com.sgq.excel.pojo.HomeWorkContent;

import java.util.List;

public interface IHomeWorkContentService {

    boolean addBatchAndRemove(String clazz) ;

    List<SubmitPointsBean> findSubmitPoints(String clazz);
}

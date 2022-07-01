package com.sgq.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sgq.excel.bean.SubmitPointsBean;
import com.sgq.excel.pojo.HomeWorkContent;

import java.util.List;

/**
 * @author : SGQ
 * @date : 2022-06-22 21:30
 **/
public interface HomeWorkContentMapper extends BaseMapper<HomeWorkContent> {

    Integer insertBatch(List<HomeWorkContent> list);

   List<SubmitPointsBean> selectSubmitPoints(String clazz);

   Integer deleteByClazz(String clazz);
}
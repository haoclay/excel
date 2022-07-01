package com.sgq.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sgq.excel.bean.StuHomeWorkBean;
import com.sgq.excel.bean.TitleDayBean;
import com.sgq.excel.pojo.StuHomeWork;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author : SGQ
 * @date : 2022-04-30 19:42
 **/

public interface StuHomeWorkMapper extends BaseMapper<StuHomeWork> {

    @Override
    List<StuHomeWork> selectByMap(Map<String, Object> columnMap);

    Integer insertBatch(List<StuHomeWork> list);

    List<StuHomeWorkBean> selectSubmitCountByMap(Map map);

    Integer deleteByBatchMap(Map map);

    //从数据库获取数据
    List<TitleDayBean> findTitleAndDayIndexDB(Map map);

    //数据最大更新时间
    Date selectMaxUpdateTime(String clazz);

    //详细
    List<StuHomeWork> selectTitleAndState(Map map);

}
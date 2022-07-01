package com.sgq.excel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sgq.excel.pojo.StuUpdate;

/**
 * @author : SGQ
 * @date : 2022-06-03 22:44
 **/

public interface StuUpdateMapper extends BaseMapper<StuUpdate> {

    StuUpdate  selectMaxUpdate(String clazz);

}
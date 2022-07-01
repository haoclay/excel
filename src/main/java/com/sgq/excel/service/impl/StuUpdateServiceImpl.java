package com.sgq.excel.service.impl;

import com.sgq.excel.mapper.StuUpdateMapper;
import com.sgq.excel.pojo.StuUpdate;
import com.sgq.excel.service.IStuUpdateService;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author : SGQ
 * @date : 2022-06-03 23:08
 **/
@Service
public class StuUpdateServiceImpl implements IStuUpdateService {

    @Autowired
    private StuUpdateMapper mapper;

    @Override
    public int insert(StuUpdate stuUpdate) {
        return mapper.insert(stuUpdate);
    }

    @Override
    public StuUpdate findMaxUpdate(String clazz) {
        return mapper.selectMaxUpdate(clazz);
    }
}
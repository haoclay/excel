package com.sgq.excel.service.impl;

import com.sgq.excel.bean.SubmitPointsBean;
import com.sgq.excel.mapper.HomeWorkContentMapper;
import com.sgq.excel.pojo.HomeWorkContent;
import com.sgq.excel.service.IHomeWorkContentService;
import com.sgq.excel.utils.StuHomeWorkCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : SGQ
 * @date : 2022-06-22 21:39
 **/

@Service
public class HomeWorkContentServiceImpl implements IHomeWorkContentService {
    @Autowired
    private HomeWorkContentMapper mapper;
    @Autowired
    private StuHomeWorkCreator creator;

    @Override
    public boolean addBatchAndRemove(String clazz) {
         List<HomeWorkContent> list = getHomeWorkContentList(clazz);
         mapper.deleteByClazz(clazz);
         Integer count = mapper.insertBatch(list);
         return count == list.size();
    }

    @Override
    public List<SubmitPointsBean> findSubmitPoints(String clazz) {
        return mapper.selectSubmitPoints(clazz);
    }

    @Override
    public List<HomeWorkContent> getHomeWorkContentList(String clazz) {
        return creator.creatContent(clazz);
    }
}
package com.sgq.excel;


import com.sgq.excel.service.IStuHomeWorkService;
import com.sgq.excel.utils.StuHomeWorkCreator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ClassroomApplicationTests {
    @Resource
    private IStuHomeWorkService service;

    @Test
    void contextLoads() {
        Map map = new HashMap(){{
            put("clazz", "vip9");
            put("startDay",0);
            put("endDay",1);
        }};
        service.addBatchBySpider(map);
    }



}

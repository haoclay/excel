package com.sgq.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.sgq.excel.mapper.StuHomeWorkMapper;
import com.sgq.excel.pojo.StuHomeWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author ppliang
 */ // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class StuHomeWorkListener extends AnalysisEventListener<StuHomeWork> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StuHomeWorkListener.class);

    private static final int BATCH_COUNT = 20;

    private StuHomeWorkMapper mapper;

    private String clazz;

    private String createDate;

    public StuHomeWorkListener(){

    }
    public StuHomeWorkListener(StuHomeWorkMapper mapper, String clazz, String createDate) {
        this.mapper = mapper;
        this.clazz = clazz;
        this.createDate = createDate;
    }

    List<StuHomeWork> list = new ArrayList<StuHomeWork>();


    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        if(list.size() > 0){
            mapper.insertBatch(list);
            LOGGER.info("存储数据库成功！");
        }else {
            System.out.println("暂无数据需要存储...");
        }

    }

    // 一行一行读取 excel 内容
    @Override
    public void invoke(StuHomeWork data, AnalysisContext context) {
        data.setClazz(clazz);
        data.setCreateDate(createDate);
        if (data.getState().equals("未交")) {
            data.setState("0");
        } else {
            data.setState("1");
        }
        System.out.println("===================="+data+"====================");
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {

            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }
    // 读取表头内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头：" + headMap);
    }

    // 读取完成之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info(createDate+"表所有数据解析完成！");
    }

}

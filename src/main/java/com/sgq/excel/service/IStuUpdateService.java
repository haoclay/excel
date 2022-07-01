package com.sgq.excel.service;

import com.sgq.excel.pojo.StuUpdate;

public interface IStuUpdateService {

     int insert(StuUpdate stuUpdate);

     StuUpdate findMaxUpdate(String clazz);
}

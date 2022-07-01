package com.sgq.excel.common;

import java.util.List;

/**
 * @author : SGQ
 * @date : 2022-04-28 15:52
 **/
public interface ExcelAdapter {

     Integer write(List list);

     List read(String path);

     boolean supports(Object o);
}
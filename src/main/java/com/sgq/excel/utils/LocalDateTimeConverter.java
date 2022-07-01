package com.sgq.excel.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : SGQ
 * @date : 2022-05-01 01:43
 **/
public class LocalDateTimeConverter implements Converter<LocalDateTime> {
    @Override
    public Class supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDateTime convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return LocalDateTime.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }

    @Override
    public CellData convertToExcelData(LocalDateTime value, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData<>(value.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
    }
}
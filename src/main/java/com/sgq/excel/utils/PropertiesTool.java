package com.sgq.excel.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

/**
 * @author : SGQ
 * @date : 2022-05-31 16:22
 **/
@Component
@Configuration
@PropertySource("classpath:course-id.properties")
public class PropertiesTool {
    private static Properties properties = new Properties();
    private static final String prefix = "lxyk.";
    static {
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = PropertiesTool.class.getClassLoader().getResourceAsStream("course-id.properties");
        // 使用properties对象加载输入流
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCourseId(String key){

        return properties.get(prefix+key).toString();
    }

}
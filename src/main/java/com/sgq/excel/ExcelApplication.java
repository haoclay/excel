package com.sgq.excel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@SpringBootApplication
@MapperScan("com.sgq.excel.mapper")
@EnableTransactionManagement
public class ExcelApplication  {



    // 创建事务管理器1
    @Bean(name = "txManager1")
    public PlatformTransactionManager txManager1(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    // 创建事务管理器2
    @Bean(name = "txManager2")
    public PlatformTransactionManager txManager2(EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

    @Bean
    public Object testBean(@Qualifier("txManager2") PlatformTransactionManager platformTransactionManager) {
        System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
        return new Object();
    }

    public static void main(String[] args) {
        SpringApplication.run(ExcelApplication.class, args);
    }



}

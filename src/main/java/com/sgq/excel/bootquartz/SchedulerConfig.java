package com.sgq.excel.bootquartz;


import com.sgq.excel.service.IHomeWorkContentService;
import com.sgq.excel.service.IStuHomeWorkService;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

@Configuration
public class SchedulerConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private IStuHomeWorkService stuHomeWorkService;

    @Autowired
    private IHomeWorkContentService homeWorkContentService;

    @Bean
    @Primary
   public Scheduler getScheduler(){

      return   getSchedulerFactoryBean().getScheduler();
   }

   @Bean
    public SchedulerFactoryBean getSchedulerFactoryBean(){
       Map serviceMap = new HashMap();
       serviceMap.put("homeWorkService",stuHomeWorkService);
       serviceMap.put("contentService",homeWorkContentService);


       SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
       factoryBean.setSchedulerName("cluster_scheduler");
       factoryBean.setDataSource(dataSource);
       factoryBean.setApplicationContextSchedulerContextKey("application");
       factoryBean.setQuartzProperties(getProperties());
       factoryBean.setTaskExecutor(schedulerThreadPool());
       factoryBean.setSchedulerContextAsMap(serviceMap);
       factoryBean.setStartupDelay(0);
       return factoryBean;
   }

   @Bean
    public Properties getProperties(){
       PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
       propertiesFactoryBean.setLocation(new ClassPathResource("/spring-quartz.properties"));
       try {
           propertiesFactoryBean.afterPropertiesSet();
           return propertiesFactoryBean.getObject();
       } catch (IOException e) {
           e.printStackTrace();
       }

       return null;

   }

    @Bean
    public Executor schedulerThreadPool(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
        executor.setQueueCapacity(Runtime.getRuntime().availableProcessors());
        return  executor;
    }
}
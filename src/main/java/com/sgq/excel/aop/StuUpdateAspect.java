package com.sgq.excel.aop;

import com.sgq.excel.pojo.StuUpdate;
import com.sgq.excel.service.IStuUpdateService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author : SGQ
 * @date : 2022-06-03 23:26
 **/
@Aspect
@Component
public class StuUpdateAspect {
    @Autowired
    private IStuUpdateService stuUpdateService;

    @Around("execution(*  *..IStuHomeWorkService.removeAndAdd(..))")
    public Object addUpdateTime(ProceedingJoinPoint pjp){
        System.out.println("开始织入每次数据更新的时间....");
        try {
            Map map = (Map) pjp.getArgs()[0];
            StuUpdate stuUpdate = new StuUpdate();
            stuUpdate.setClazz(map.get("clazz").toString());
            stuUpdate.setStartTime(map.get("startTitle").toString());
            stuUpdate.setEndTime(map.get("endTitle").toString());
            stuUpdate.setUpdateTime(new Date());
            Boolean result = (Boolean) pjp.proceed();
            int count = stuUpdateService.insert(stuUpdate);
            if(count == 1 && result == true)
                return true;
            else
                return false;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return false;

    }
}
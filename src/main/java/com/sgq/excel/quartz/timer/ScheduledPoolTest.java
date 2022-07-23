package com.sgq.excel.quartz.timer;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledPoolTest {

    public static void main(String[] args) {

        ScheduledExecutorService service = Executors.newScheduledThreadPool(5);

        service.scheduleAtFixedRate(
                () -> System.out.println("111"),
                10,
                2,
                TimeUnit.SECONDS);
    }
}
package com.sgq.excel.quartz.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

    public static void main(String[] args) {
        Timer timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022,6,16,15,4,0);

        timer.schedule(new MyTask("task1"),calendar.getTime(),2);
    }
}

class MyTask extends TimerTask{
   private String name ;

    public MyTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name+"正在运行...");
    }
}

class TaskTest extends TimerTask{

    @Override
    public void run() {

    }
}
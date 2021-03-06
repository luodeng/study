package com.roden.study.java.util.concurrent;

import org.junit.Test;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/3/9.
 */
public class ScheduleTest {
    //每天定时执行
    @Test
    public void startSchedulePlan() {
       Calendar calendar = Calendar.getInstance();
       long currentTimeMillis=calendar.getTimeInMillis();
       calendar.set(Calendar.HOUR_OF_DAY, 10);
       calendar.set(Calendar.MINUTE, 0);
       calendar.set(Calendar.SECOND, 0);
       calendar.set(Calendar.MILLISECOND,calendar.getActualMinimum(Calendar.MILLISECOND));
       long initialDelay=calendar.getTimeInMillis()-currentTimeMillis;
       long onedayTimeMillis=1000*60*60*24;
       initialDelay=initialDelay>0?initialDelay:onedayTimeMillis+initialDelay;

        System.out.println(initialDelay/1000/60/60);
        ScheduledExecutorService scheduled= Executors.newSingleThreadScheduledExecutor();
        scheduled.scheduleAtFixedRate(new Runnable(){
                                          @Override
                                          public void run() {
                                              System.out.println(new Date());
                                          }
                                      },
               initialDelay, onedayTimeMillis, TimeUnit.MILLISECONDS);
   }

    /**
     * 每天0点修改昨天待还款订单修改为逾期
     */
    public void startScheduleMakeLoanOverdue() {
        ScheduledExecutorService scheduledExecutorService= Executors.newSingleThreadScheduledExecutor();
        LocalTime localTime = LocalTime.of(0, 5);
        long time = ChronoUnit.MINUTES.between(LocalTime.now(), localTime);
        long initialDelay = time > 0 ? time : 24 * 60 + time;
        System.out.println("startScheduleMakeLoanOverdue start after minutes " + initialDelay);
        scheduledExecutorService.scheduleAtFixedRate(() -> {}, initialDelay, 24 * 60, TimeUnit.MINUTES);

    }
}

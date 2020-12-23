package com.roden.study.java.time;



import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class LocalDateTest {
    @Test
    public void between(){
        LocalDate localDate=LocalDate.now();
        LocalDate repayDate=LocalDate.of(2018,5,23);

        System.out.println(repayDate.until(localDate,ChronoUnit.DAYS));
        System.out.println(localDate.toEpochDay()-repayDate.toEpochDay());
        System.out.println(ChronoUnit.DAYS.between(repayDate,localDate));
        System.out.println(Duration.between(LocalDateTime.of(repayDate, LocalTime.now()),LocalDateTime.now()).toDays());
    }

    @Test
    //  java.util.Date --> java.time.LocalDate
    public void DateToLocalDate() {
        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        ZonedDateTime zonedDateTime= instant.atZone(zone);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        //LocalDate localDate = localDateTime.toLocalDate();
        LocalDate localDate=zonedDateTime.toLocalDate();
        System.out.println("LocalDate = " + localDate);
        System.out.println("Date = " + date);

        System.out.println(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        System.out.println(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    @Test
    //  java.time.LocalDate --> java.util.Date
    public void LocalDateToDate() {
        LocalDate localDate = LocalDate.now();
        ZoneId zone = ZoneId.systemDefault();
        //Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Instant instant = localDate.atStartOfDay(zone).toInstant();
        //Instant instant = LocalDateTime.now().atZone(zone).toInstant();
        Date date = Date.from(instant);
        System.out.println("LocalDate = " + localDate);
        System.out.println("Date = " + date);

        System.out.println(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        System.out.println(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

    }
    @Test
    public void cst() throws ParseException {
        System.out.println(new Date().toString());
        String date = "Thu Aug 27 18:05:49 CST 2019";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date d = sdf.parse(date);
        String formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);

        System.out.println(formatDate);
    }

    @Test
    public void gmt() {
        Calendar cd = Calendar.getInstance();
        System.out.println(cd);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8")); // 设置时区为GMT  +8为北京时间东八区
        String str = sdf.format(cd.getTime());
        System.out.println(str);
    }

    //给人读的( LocalDateTime & LocalDate & LocalTime)
    //给计算机读的(Instant)
    @Test
    public void local() {
        //获取UTC时间(格林尼治时间)
        Instant instant = Instant.now();
        System.out.println(instant);
        //获取北京时间(东8区)
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);
        //获取毫秒数(1970-01-01T00:00:00Z开始计算)
        long epochMilli = instant.toEpochMilli();
        System.out.println(epochMilli);
        //定义时间戳
        Instant instant1 = Instant.ofEpochSecond(59);
        System.out.println(instant1);
        Instant instant2 = instant1.plusSeconds(99);
        System.out.println(instant2);

    }

    //时间间隔(Duration)
    @Test
    public void duration() {
        //计算日期间隔
        Instant instant1 = Instant.now();
        Instant instant2 = instant1.plusSeconds(60*60*24*3+2);

        Duration duration1 = Duration.between(instant1, instant2);
        Duration duration2 = Duration.between(instant2, instant1);
        System.out.println(duration1);
        System.out.println(duration2);

        long duration1Seconds = duration1.getSeconds();
        long duration2Seconds = duration2.getSeconds();
        System.out.println(duration1Seconds);
        System.out.println(duration2Seconds);

       //操作时间间隔
        Duration duration3 = duration1.plusDays(1);
        System.out.println(duration3);
    }

    //日期间隔(Period)
    @Test
    public void period() {
        LocalDate localDate1 = LocalDate.now();
        LocalDate localDate2 = localDate1.plusDays(500);
        Period period = Period.between(localDate1, localDate2);
        System.out.println(period.toString());
        System.out.println(period.getDays());


    }
    //日期/时间校正器(TemporalAdjuster)
    @Test
    public void temporalAdjuster() {
        LocalDateTime localDateTime1 = LocalDateTime.now();
        LocalDateTime localDateTime2 = localDateTime1.withDayOfMonth(20);

        LocalDateTime localDateTime3 = localDateTime2.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

        // 获取下一个工作日
        LocalDateTime localDateTime4 = localDateTime3.with(tempDateTime -> {
            LocalDateTime localDateTime5 = (LocalDateTime) tempDateTime;
            DayOfWeek dayOfWeek = localDateTime5.getDayOfWeek();
            if (dayOfWeek.equals(DayOfWeek.FRIDAY)) {
                return localDateTime5.plusDays(3);
            } else if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
                return localDateTime5.plusDays(2);
            } else {
                return localDateTime5.plusDays(1);
            }
        });
        System.out.println(localDateTime1);
        System.out.println(localDateTime2);
        System.out.println(localDateTime3);
        System.out.println(localDateTime4);
    }

    @Test
    public void transform() {
        //时间戳转LocalDate，LocalDate，LocalDateTime
        long timestamp = Instant.now().toEpochMilli();
        LocalDateTime localDateTime = Instant.ofEpochMilli(timestamp).atOffset(ZoneOffset.ofHours(8)).toLocalDateTime();
        System.out.println(localDateTime);
        //LocalDate，LocalDate，LocalDateTime转时间戳
        LocalDateTime localDateTime2 = LocalDateTime.now();
        long timestamp2 = localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        System.out.println(timestamp2);
        //兼容Date
        LocalDateTime localDateTime3 = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
        LocalDateTime localDateTime4  = localDateTime3.atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        System.out.println(date);
        System.out.println(localDateTime4);
    }

}

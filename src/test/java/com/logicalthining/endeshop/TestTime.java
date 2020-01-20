package com.logicalthining.endeshop;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.StopWatch;

import java.time.Duration;
import java.util.UUID;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/21 0021 下午 6:02
 **/
public class TestTime {

    @Test
    public void test1() {
        int dayOfWeek = DateTime.now().getDayOfWeek();
        System.out.println(dayOfWeek);

        UUID uuid = new AlternativeJdkIdGenerator().generateId();
        System.out.println(uuid.toString());
    }


    @Test
    public void test2(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Duration duration = Duration.parse("PT20S");
        System.out.println(duration.toString());
        stopWatch.stop();
        long lastTaskTimeMillis = stopWatch.getLastTaskTimeMillis();
        System.out.println(lastTaskTimeMillis);
    }
}

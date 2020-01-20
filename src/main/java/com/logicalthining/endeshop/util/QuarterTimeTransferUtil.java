package com.logicalthining.endeshop.util;

import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * 季度-时间互相转换器
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/11 0011 下午 6:04
 **/
public class QuarterTimeTransferUtil {

    /**
     * 将时间转化为季度
     * 季度格式 2019-1
     *
     * @param time 1
     * @return java.lang.String
     * @since 下午 4:09 2019/11/9 0009
     **/
    public static String transferDateToQuarter(Date time) {

        StringBuilder sb = new StringBuilder();

        DateTime dateTime = new DateTime(time.getTime());
        //取年
        int year = dateTime.getYear();
        sb.append(year);
        sb.append("-");
        //1季度为 一月一日零点 到三月三十一日十一点五十九分
        //2季度为 四月一日零点 到六月三十日十一点五十九分
        //3季度为 七月一日零点 到九月三十日十一点五十九分
        //4季度为 十月一日零点 到十二月三十一日十一点五十九分

        //所以可以得出以下区间
        DateTime dateTime1 = dateTime.withMonthOfYear(1).withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
        DateTime dateTime2 = dateTime.withMonthOfYear(4).withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
        DateTime dateTime3 = dateTime.withMonthOfYear(7).withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
        DateTime dateTime4 = dateTime.withMonthOfYear(10).withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);

        long timeMillis = time.getTime();

        if (timeMillis >= dateTime1.getMillis()) {
            if (timeMillis < dateTime2.getMillis()) {
                sb.append(1);
            } else if (timeMillis < dateTime3.getMillis()) {
                sb.append(2);
            } else if (timeMillis < dateTime4.getMillis()) {
                sb.append(3);
            } else {
                sb.append(4);
            }
        }
        return sb.toString();
    }


    /**
     * 将季度转化为时间区间
     *
     * @param quarter 1
     * @return com.logicalthining.endeshop.util.QuarterTimeTransferUtil.QuarterTimeInterval
     * @since 下午 6:08 2019/11/11 0011
     **/
    public static QuarterTimeInterval transferQuarterToQuarterTimeInterval(String quarter) {
        if (StringUtils.isNotEmpty(quarter) && Pattern.matches("^\\d{4}-\\d$", quarter)) {
            //将季度字符串解析为 年 和 季度
            String[] split = quarter.split("-");
            Integer year = Integer.valueOf(split[0]);
            Integer quarterIndex = Integer.valueOf(split[1]);

            if (Lists.asList(1, 2, 3, 4).contains(quarterIndex)) {
                DateTime dateTime = new DateTime().withYear(year);

                Date startTime = null;
                Date endTime = null;
                QuarterTimeInterval quarterTimeInterval = new QuarterTimeInterval();
                quarterTimeInterval.setQuarter(quarter);
                switch (quarterIndex) {
                    case 1:
                        startTime = dateTime.withMonthOfYear(1).withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDate();
                        endTime = dateTime.withMonthOfYear(3).withDayOfMonth(31).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate();
                        break;
                    case 2:
                        startTime = dateTime.withMonthOfYear(4).withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDate();
                        endTime = dateTime.withMonthOfYear(6).withDayOfMonth(30).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate();
                        break;
                    case 3:
                        startTime = dateTime.withMonthOfYear(7).withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDate();
                        endTime = dateTime.withMonthOfYear(9).withDayOfMonth(30).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate();
                        break;
                    case 4:
                        startTime = dateTime.withMonthOfYear(10).withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDate();
                        endTime = dateTime.withMonthOfYear(12).withDayOfMonth(31).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate();
                        break;
                }

                quarterTimeInterval.setStartTime(startTime);
                quarterTimeInterval.setEndTime(endTime);
                return quarterTimeInterval;
            }


        }
        return null;
    }


    /**
     * 季度时间区间
     *
     * @since 下午 6:07 2019/11/11 0011
     **/
    @Setter
    @Getter
    public static class QuarterTimeInterval {

        /**
         * 季度
         *
         * @since 下午 6:07 2019/11/11 0011
         **/
        private String quarter;

        /**
         * 开始时间
         *
         * @since 下午 6:07 2019/11/11 0011
         **/
        private Date startTime;

        /**
         * 结束时间
         *
         * @since 下午 6:07 2019/11/11 0011
         **/
        private Date endTime;

    }


}

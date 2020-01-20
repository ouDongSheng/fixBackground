package com.logicalthining.endeshop.common.pojo;

import com.github.chenlijia1111.utils.core.IDUtil;
import org.joda.time.DateTime;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link com.github.chenlijia1111.utils.core.IDUtil} 工厂
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/1 0001 下午 2:44
 **/
public class IDGenerateFactory {

    /**
     * 产品id生成器
     *
     * @since 下午 2:45 2019/11/1 0001
     **/
    public static final IDUtil PRODUCT_ID_UTIL = new IDUtil(1, 1);

    /**
     * 商品id生成器
     *
     * @since 下午 2:45 2019/11/1 0001
     **/
    public static final IDUtil GOOD_ID_UTIL = new IDUtil(2, 2);

    /**
     * 订单id生成器
     *
     * @since 下午 2:46 2019/11/1 0001
     **/
    public static final IDUtil ORDER_ID_UTIL = new IDUtil(3, 3);

    //毫秒级别记录,同一秒内不允许重复
    private static Set<String> millisIdRecode = new HashSet<>();
    //记录的秒
    private static long recodeSecond = 0L;

    /**
     * 生成产品编号
     *
     * @return java.lang.String
     * @since 下午 3:54 2019/12/3 0003
     **/
    public static String createProductId() {
        StringBuilder sb = new StringBuilder();
        DateTime now = DateTime.now();
        String s = now.toString("yyyyMMddHHmmss");
        sb.append(s);
        sb.append(shortId());
        return sb.toString();
    }

    /**
     * 生成商品编号
     *
     * @return java.lang.String
     * @since 下午 3:54 2019/12/3 0003
     **/
    public static String createGoodId() {
        StringBuilder sb = new StringBuilder();
        DateTime now = DateTime.now();
        String s = now.toString("yyyyMMddHHmmss");
        sb.append(s);
        sb.append(shortId());
        return sb.toString();
    }


    /**
     * 生成订单编号
     *
     * @return java.lang.String
     * @since 下午 3:54 2019/12/3 0003
     **/
    public static String createOrderId() {
        StringBuilder sb = new StringBuilder();
        DateTime now = DateTime.now();
        String s = now.toString("yyyyMMddHHmmss");
        sb.append(s);
        sb.append(shortId());
        return sb.toString();
    }

    /**
     * 获取短随机数
     *
     * @return java.lang.String
     * @since 下午 3:48 2019/12/4 0004
     **/
    public static String shortId() {
        long l = ORDER_ID_UTIL.nextId();
        String s = String.valueOf(l);
        s = s.substring(15);
        long recodeSecond = System.currentTimeMillis() / 1000;
        if (recodeSecond == IDGenerateFactory.recodeSecond) {
            //是同一秒内
            //判断是否重复
            if (millisIdRecode.contains(s)) {
                //重新取值
                return shortId();
            }
            millisIdRecode.add(s);
            return s;
        } else {
            //记录的不是同一秒了
            IDGenerateFactory.recodeSecond = recodeSecond;
            millisIdRecode.clear();
            millisIdRecode.add(s);
            return s;
        }
    }

//    public static void main(String[] args) {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        String orderId = createOrderId();
//        System.out.println(orderId);
//        stopWatch.stop();
//        long totalTimeMillis = stopWatch.getTotalTimeMillis();
//        System.out.println(totalTimeMillis);
//    }

}

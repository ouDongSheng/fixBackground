package com.logicalthining.endeshop;

import com.github.chenlijia1111.utils.core.IDUtil;
import org.junit.jupiter.api.Test;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/1 0001 下午 2:37
 **/
public class TestIDUtil {


    @Test
    public void test1(){
        IDUtil idUtil = new IDUtil(2, 2);
        long l = idUtil.nextId();
        System.out.println(l);//387654602344112128
                              //387654686272135168
    }
}

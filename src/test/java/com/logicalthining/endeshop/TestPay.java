package com.logicalthining.endeshop;

import com.github.chenlijia1111.utils.core.IOUtil;
import com.github.chenlijia1111.utils.core.RandomUtil;
import com.github.chenlijia1111.utils.pay.wx.PayType;
import com.github.chenlijia1111.utils.pay.wx.WXPayUtil;
import com.logicalthining.endeshop.common.pojo.Constants;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Map;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/13 0013 下午 2:32
 **/
public class TestPay {

    @Test
    public void test1() {
        Map body = WXPayUtil.createPreOrder(Constants.WX_APP_ID, Constants.WX_MCHID, "body", Constants.WX_PAY_SIGN_KEY,
                100, "http://www.shixian.com", PayType.JSAPI, "o9gLgjrvSzcMND3ELUhKg0E0hwjg", RandomUtil.createUUID(), null);
        System.out.println(body);
    }

    @Test
    public void test2(){
        InputStream inputStream = IOUtil.inputStreamToBaseProject("apiclient_cert.p12");
        Map refund = WXPayUtil.refund(Constants.WX_APP_ID, Constants.WX_MCHID, Constants.WX_PAY_SIGN_KEY, inputStream,
                Constants.WX_MCHID, null, "da87b3b14a9a4533b7a244c9567fcb76",
                100, 100);
        System.out.println(refund);
    }

}

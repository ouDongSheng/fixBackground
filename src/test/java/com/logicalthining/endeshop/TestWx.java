package com.logicalthining.endeshop;

import com.github.chenlijia1111.utils.oauth.wx.WXCommonLoginUtil;
import com.logicalthining.endeshop.common.pojo.Constants;
import com.logicalthining.endeshop.common.requestVo.wx.WxJsApiSignParams;
import com.logicalthining.endeshop.common.responseVo.wx.WxJsApiSignVo;
import com.logicalthining.endeshop.service.WxShareServiceI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/19 0019 下午 4:27
 **/
@SpringBootTest
public class TestWx {

    @Test
    public void test1(){
        WXCommonLoginUtil loginUtil = new WXCommonLoginUtil();
        Map map = loginUtil.accessToken(Constants.WX_APP_ID, Constants.WX_APP_SECRET, "021qaaGj1KdbGt0gDEJj1y8tGj1qaaGE");
        System.out.println(map);
    }

    @Autowired
    private WxShareServiceI wxShareService;

    @Test
    public void test2(){
        WxJsApiSignParams params = new WxJsApiSignParams();
        params.setUrl("http://www.baicu.com");
        WxJsApiSignVo wxJsApiSignVo = wxShareService.createWxJsApiSignVo(params);
        System.out.println(wxJsApiSignVo.getSign());
    }


}

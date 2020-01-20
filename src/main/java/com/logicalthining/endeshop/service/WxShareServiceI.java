package com.logicalthining.endeshop.service;

import com.logicalthining.endeshop.common.requestVo.wx.WxJsApiSignParams;
import com.logicalthining.endeshop.common.responseVo.wx.WxJsApiSignVo;

/**
 * 微信分享
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/20 0020 下午 3:20
 **/
public interface WxShareServiceI {

    /**
     * 获取公众号请求的accessToken
     * @since 下午 3:21 2019/11/20 0020
     * @return java.lang.String
     **/
    String accessToken();

    /**
     * 获取微信公众号调用接口凭证
     * @since 下午 3:35 2019/11/20 0020
     * @return java.lang.String
     **/
    String jsApiTicket();

    /**
     * 生成微信公众号接口调用凭证签名
     * @since 下午 3:53 2019/11/20 0020
     * @param params 1
     * @return com.logicalthining.endeshop.common.responseVo.wx.WxJsApiSignVo
     **/
    WxJsApiSignVo createWxJsApiSignVo(WxJsApiSignParams params);

}

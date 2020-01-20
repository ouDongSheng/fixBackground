package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.core.RandomUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.encrypt.SHA1EncryptUtil;
import com.github.chenlijia1111.utils.http.HttpClientUtils;
import com.logicalthining.endeshop.common.pojo.Constants;
import com.logicalthining.endeshop.common.requestVo.wx.WxJsApiSignParams;
import com.logicalthining.endeshop.common.responseVo.wx.WxJsApiSignVo;
import com.logicalthining.endeshop.service.WxShareServiceI;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 微信分享
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/20 0020 下午 3:21
 **/
@Service
public class WxShareServiceImpl implements WxShareServiceI {

    private String accessToken;

    /**
     * 微信accessToken过期时间
     *
     * @since 下午 3:22 2019/11/20 0020
     **/
    private Date accessTokenLimitTime;

    private String jsApiTicket;

    /**
     * 微信公众号接口调用凭证过期时间
     *
     * @since 下午 3:36 2019/11/20 0020
     **/
    private Date jsApiTicketLimitTime;


    /**
     * 获取公众号请求的accessToken
     * 提前60秒重新获取,方式失效
     *
     * @return java.lang.String
     * @since 下午 3:21 2019/11/20 0020
     **/
    @Override
    public String accessToken() {

        if (!(StringUtils.isNotEmpty(accessToken) && Objects.nonNull(accessTokenLimitTime) &&
                accessTokenLimitTime.getTime() - System.currentTimeMillis() > 60 * 1000L)) {
            Map map = HttpClientUtils.getInstance().putParams("grant_type", "client_credential").
                    putParams("appid", Constants.WX_APP_ID).putParams("secret", Constants.WX_APP_SECRET).
                    doGet("https://api.weixin.qq.com/cgi-bin/token").toMap();
            Object errcode = map.get("errcode");
            if (Objects.isNull(errcode)) {
                //请求成功
                String access_token = (String) map.get("access_token");
                Integer expires_in = (Integer) map.get("expires_in");
                this.accessToken = access_token;
                this.accessTokenLimitTime = DateTime.now().plusSeconds(expires_in).toDate();
            }
        }

        return accessToken;
    }

    /**
     * 获取微信公众号调用接口凭证
     *
     * @return java.lang.String
     * @since 下午 3:35 2019/11/20 0020
     **/
    @Override
    public String jsApiTicket() {

        if (!(StringUtils.isNotEmpty(jsApiTicket) && Objects.nonNull(jsApiTicketLimitTime) &&
                jsApiTicketLimitTime.getTime() - System.currentTimeMillis() > 60 * 1000)) {

            String accessToken = accessToken();
            if(StringUtils.isNotEmpty(accessToken)){
                Map map = HttpClientUtils.getInstance().putParams("access_token", accessToken).
                        putParams("type", "jsapi").
                        doGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket").toMap();
                Object errcode = map.get("errcode");
                if (Objects.equals(errcode, 0)) {
                    //请求成功
                    String ticket = (String) map.get("ticket");
                    Integer expires_in = (Integer) map.get("expires_in");
                    this.jsApiTicket = ticket;
                    this.jsApiTicketLimitTime = DateTime.now().plusSeconds(expires_in).toDate();
                }
            }
        }

        return jsApiTicket;
    }

    /**
     * 生成微信公众号接口调用凭证签名
     *
     * @param params 1
     * @return com.logicalthining.endeshop.common.responseVo.wx.WxJsApiSignVo
     * @since 下午 3:53 2019/11/20 0020
     **/
    @Override
    public WxJsApiSignVo createWxJsApiSignVo(WxJsApiSignParams params) {

        //接口调用凭证
        String jsApiTicket = jsApiTicket();
        if (StringUtils.isNotEmpty(jsApiTicket)) {
            //如果获取不到接口调用凭证,可能是需要设置ip白名单
            String url = params.getUrl();
            //随机字符串
            String nonceStr = RandomUtil.createUUID();

            //时间戳
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

            String tempStr = HttpClientUtils.getInstance().
                    putParams("noncestr", nonceStr).
                    putParams("jsapi_ticket", jsApiTicket).
                    putParams("timestamp", timestamp).putParams("url", url).
                    paramsToString(true);
            String sign = SHA1EncryptUtil.SHA1StringToHexString(tempStr).toLowerCase();

            WxJsApiSignVo wxJsApiSignVo = new WxJsApiSignVo().
                    setUrl(url).
                    setNonceStr(nonceStr).
                    setTimeStamp(timestamp).
                    setJsApiTicket(jsApiTicket).
                    setSign(sign);
            return wxJsApiSignVo;
        }

        return null;
    }

}

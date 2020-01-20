package com.logicalthining.endeshop.common.responseVo.wx;

import com.logicalthining.endeshop.common.pojo.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 微信公众号调用微信接口需要进行签名
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/20 0020 下午 3:44
 **/
@ApiModel
@Setter
@Getter
@Accessors(chain = true)
public class WxJsApiSignVo {

    /**
     * appId
     *
     * @since 下午 6:08 2019/11/20 0020
     **/
    @ApiModelProperty(value = "appId")
    private String appId = Constants.WX_APP_ID;

    /**
     * 随机字符串
     *
     * @since 下午 3:47 2019/11/20 0020
     **/
    @ApiModelProperty(value = "随机字符串")
    private String nonceStr;

    /**
     * 微信接口调用凭证
     *
     * @since 下午 3:47 2019/11/20 0020
     **/
    @ApiModelProperty(value = "微信接口调用凭证")
    private String jsApiTicket;

    /**
     * 时间戳
     *
     * @since 下午 3:47 2019/11/20 0020
     **/
    @ApiModelProperty(value = "时间戳")
    private String timeStamp;

    /**
     * 当前网页的URL，不包含#及其后面部分
     *
     * @since 下午 3:47 2019/11/20 0020
     **/
    @ApiModelProperty(value = "当前网页的URL，不包含#及其后面部分")
    private String url;

    /**
     * 签名
     *
     * @since 下午 3:47 2019/11/20 0020
     **/
    @ApiModelProperty(value = "签名")
    private String sign;

}

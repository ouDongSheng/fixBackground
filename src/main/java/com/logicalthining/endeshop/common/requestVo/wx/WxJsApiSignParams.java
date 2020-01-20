package com.logicalthining.endeshop.common.requestVo.wx;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 微信公众号接口凭证签名参数
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/20 0020 下午 3:49
 **/
@Setter
@Getter
@ApiModel
public class WxJsApiSignParams {

    /**
     * 当前网页的URL，不包含#及其后面部分
     * @since 下午 3:47 2019/11/20 0020
     **/
    @ApiModelProperty(value = "当前网页的URL，不包含#及其后面部分")
    @PropertyCheck(name = "当前网页的URL")
    private String url;

}

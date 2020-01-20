package com.logicalthining.endeshop.common.requestVo.user;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.github.chenlijia1111.utils.core.enums.PropertyCheckType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 微信登录参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/12 0012 下午 5:49
 **/
@ApiModel
@Setter
@Getter
public class BindTelephoneParams {

    /**
     * openId
     *
     * @since 下午 5:51 2019/11/12 0012
     **/
    @ApiModelProperty(value = "openId")
    @PropertyCheck(name = "openId")
    private String openId;

    /**
     * 手机号
     *
     * @since 下午 5:52 2019/11/12 0012
     **/
    @ApiModelProperty(value = "手机号")
    @PropertyCheck(name = "手机号", checkType = PropertyCheckType.MOBILE_PHONE)
    private String telephone;

    /**
     * 短信验证码
     *
     * @since 下午 5:52 2019/11/12 0012
     **/
    @ApiModelProperty(value = "短信验证码")
    @PropertyCheck(name = "短信验证码")
    private String msgCode;

}

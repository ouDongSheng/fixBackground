package com.logicalthining.endeshop.common.requestVo.user;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.github.chenlijia1111.utils.core.enums.PropertyCheckType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户登录参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 上午 11:05
 **/
@ApiModel
@Setter
@Getter
public class LoginParams {

    /**
     * 手机号
     *
     * @since 上午 11:06 2019/11/5 0005
     **/
    @ApiModelProperty(value = "手机号")
    @PropertyCheck(name = "手机号", checkType = PropertyCheckType.MOBILE_PHONE)
    private String telephone;

    /**
     * 验证码
     *
     * @since 上午 11:06 2019/11/5 0005
     **/
    @ApiModelProperty(value = "验证码")
    @PropertyCheck(name = "验证码")
    private String msgCode;

}

package com.logicalthining.endeshop.common.requestVo.admin;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 登录参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/30 0030 上午 10:30
 **/
@ApiModel
@Setter
@Getter
public class LoginParams {

    /**
     * 账号名称
     */
    @ApiModelProperty("账号名称")
    @PropertyCheck(name = "账号名称")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    @PropertyCheck(name = "密码")
    private String password;

    /**
     * 验证码key
     * 通过发送验证码接口获取到key
     *
     * @since 上午 10:33 2019/10/30 0030
     **/
    @ApiModelProperty(value = "请求发送验证码时的key,用于校验验证码是否正确")
    private String verifyCodeKey;

    /**
     * 验证码内容
     *
     * @since 上午 10:34 2019/10/30 0030
     **/
    @ApiModelProperty(value = "验证码内容")
    private String verifyCodeValue;
}

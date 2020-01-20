package com.logicalthining.endeshop.common.requestVo.user;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 设置提现密码参数
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/14 0014 下午 12:03
 **/
@ApiModel
@Setter
@Getter
public class EditWithdrawPasswordParams {

    /**
     * 手机验证码
     * @since 下午 12:03 2019/11/14 0014
     **/
    @ApiModelProperty(value = "手机验证码")
    @PropertyCheck(name = "手机验证码")
    private String msgCode;

    /**
     * 提现密码
     * @since 下午 12:03 2019/11/14 0014
     **/
    @ApiModelProperty(value = "提现密码")
    @PropertyCheck(name = "提现密码")
    private String password;

}

package com.logicalthining.endeshop.common.requestVo.user;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.logicalthining.endeshop.common.checkFunction.PriceCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 客户端用户提现参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/14 0014 上午 9:59
 **/
@ApiModel
@Setter
@Getter
public class AppWithdrawParams {

    /**
     * 提现账号
     *
     * @since 上午 10:00 2019/11/14 0014
     **/
    @ApiModelProperty(value = "提现账号")
    @PropertyCheck(name = "提现账号")
    private String withdrawAccount;

    /**
     * 提现金额
     *
     * @since 上午 10:00 2019/11/14 0014
     **/
    @ApiModelProperty(value = "提现金额")
    @PropertyCheck(name = "提现金额", checkFunction = PriceCheck.class)
    private Double money;

    /**
     * 提现密码
     *
     * @since 上午 10:00 2019/11/14 0014
     **/
    @ApiModelProperty(value = "提现密码")
    @PropertyCheck(name = "提现密码")
    private String password;

}

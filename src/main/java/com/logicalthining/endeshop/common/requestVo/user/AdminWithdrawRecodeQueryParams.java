package com.logicalthining.endeshop.common.requestVo.user;

import com.logicalthining.endeshop.common.requestVo.PageTimeLimitQueryVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 后台用户提现记录查询参数
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/14 0014 上午 10:30
 **/
@ApiModel
@Setter
@Getter
public class AdminWithdrawRecodeQueryParams extends PageTimeLimitQueryVo {

    /**
     * 账号
     * 用户账户和提现账号都可查询
     * @since 上午 10:32 2019/11/14 0014
     **/
    @ApiModelProperty(value = "账号")
    private String account;

    /**
     * 状态 0 初始状态 1提现成功 2提现失败
     * @since 上午 10:31 2019/11/14 0014
     **/
    @ApiModelProperty(value = "状态 0 初始状态 1提现成功 2提现失败")
    private Integer status;

}

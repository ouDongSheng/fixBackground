package com.logicalthining.endeshop.common.responseVo.withdraw;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 后台提现记录列表对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/14 0014 上午 10:22
 **/
@ApiModel
@Setter
@Getter
public class AdminWithdrawListVo {

    /**
     * 提现记录id
     **/
    @ApiModelProperty(value = "提现记录id")
    private Integer id;

    /**
     * 申请时间
     */
    @ApiModelProperty("申请时间")
    private Date createTime;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String userAccount;

    /**
     * 提现金额
     */
    @ApiModelProperty(value = "提现金额")
    private Double withdrawMoney;

    /**
     * 提现账号
     */
    @ApiModelProperty(value = "提现账号")
    private String withdrawAccount;

    /**
     * 状态 0 初始状态 1提现成功 2提现失败
     */
    @ApiModelProperty(value = "状态 0 初始状态 1提现成功 2提现失败")
    private Integer status;

    /**
     * 操作人名称
     */
    @ApiModelProperty(value = "操作人名称")
    private String processAdminName;

    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    private Date processTime;


}

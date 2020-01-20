package com.logicalthining.endeshop.common.responseVo.shareManage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 分享记录返回视图
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/7 0007 上午 10:46
 **/
@ApiModel
@Setter
@Getter
public class ShareManageVo {

    /**
     * 订单编号
     * @since 上午 10:53 2019/11/7 0007
     **/
    @ApiModelProperty(value = "订单编号")
    private String groupId;

    /**
     * 下单时间
     * @since 上午 10:53 2019/11/7 0007
     **/
    @ApiModelProperty(value = "下单时间")
    private Date createOrderTime;

    /**
     * 上级用户
     * @since 上午 10:53 2019/11/7 0007
     **/
    @ApiModelProperty(value = "上级用户")
    private String parentUserAccount;

    /**
     * 购买用户
     * @since 上午 10:53 2019/11/7 0007
     **/
    @ApiModelProperty(value = "购买用户")
    private String buyerUserAccount;

    /**
     * 订单金额
     * @since 上午 10:53 2019/11/7 0007
     **/
    @ApiModelProperty(value = "订单金额")
    private Double orderMoney;

    /**
     * 收入 表示上级用户在这个订单中的收入
     * @since 上午 10:53 2019/11/7 0007
     **/
    @ApiModelProperty(value = "收入")
    private Double income;

}

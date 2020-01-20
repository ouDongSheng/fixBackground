package com.logicalthining.endeshop.common.responseVo.userScore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 后台查询用户积分列表返回对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/8 0008 上午 10:36
 **/
@Setter
@Getter
@ApiModel
public class AdminUserScoreListVo {

    /**
     * 订单编号
     *
     * @since 上午 10:53 2019/11/7 0007
     **/
    @ApiModelProperty(value = "订单编号")
    private String groupId;

    /**
     * 下单时间
     *
     * @since 上午 10:53 2019/11/7 0007
     **/
    @ApiModelProperty(value = "下单时间")
    private Date createOrderTime;

    /**
     * 合伙人
     *
     * @since 上午 10:53 2019/11/7 0007
     **/
    @ApiModelProperty(value = "合伙人")
    private String parentUserAccount;

    /**
     * 购买用户
     *
     * @since 上午 10:53 2019/11/7 0007
     **/
    @ApiModelProperty(value = "购买用户")
    private String buyerUserAccount;

    /**
     * 订单金额
     *
     * @since 上午 10:53 2019/11/7 0007
     **/
    @ApiModelProperty(value = "订单金额")
    private Double orderMoney;

    /**
     * 积分 表示合伙人在这个订单中的获得到的积分
     *
     * @since 上午 10:53 2019/11/7 0007
     **/
    @ApiModelProperty(value = "获得积分")
    private Double score;

}

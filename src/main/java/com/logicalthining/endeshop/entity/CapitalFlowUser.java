package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户资金流水
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-07 09:37:20
 **/
@ApiModel("用户资金流水")
@Table(name = "s_capital_flow_user")
@Setter
@Getter
@Accessors(chain = true)
public class CapitalFlowUser {
    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    @PropertyCheck(name = "主键id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @PropertyCheck(name = "用户id")
    @Column(name = "user_id")
    private Integer userId;


    /**
     * 流水类别 1分享奖收益 2提现
     */
    @ApiModelProperty("流水类别 1分享奖收益 2提现")
    @PropertyCheck(name = "流水类别 1分享奖收益 2提现")
    @Column(name = "type")
    private Integer type;

    /**
     * 关联的id 可能是订单Id也可能是其他的
     * 如果是分享奖,那么关联的是订单编号(即groupId)
     * 如果是提现,那么关联的是提现记录的Id
     */
    @ApiModelProperty("关联的id 可能是订单Id也可能是其他的")
    @PropertyCheck(name = "关联的id 可能是订单Id也可能是其他的")
    @Column(name = "relation_item")
    private String relationItem;

    /**
     * 本次流水金额
     */
    @ApiModelProperty("本次流水金额")
    @PropertyCheck(name = "本次流水金额")
    @Column(name = "transaction_money")
    private Double transactionMoney;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @PropertyCheck(name = "备注")
    @Column(name = "remarks")
    private String remarks;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 结算之后用户账户余额
     */
    @ApiModelProperty("结算之后用户账户余额")
    @PropertyCheck(name = "结算之后用户账户余额")
    @Column(name = "user_balance")
    private Double userBalance;

    /**
     * 是否到账 0否1是
     */
    @ApiModelProperty("是否到账 0否1是")
    @PropertyCheck(name = "是否到账")
    @Column(name = "arrive_account_status")
    private Integer arriveAccountStatus;

}

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
 * 用户积分记录
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-07 13:41:29
 **/
@ApiModel("用户积分记录")
@Table(name = "s_user_score_recode")
@Setter
@Getter
@Accessors(chain = true)
public class UserScoreRecode {
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
     * 积分来源类型 1间接奖获取积分 2复购奖获取积分 3使用积分 4积分池积分结算
     */
    @ApiModelProperty("积分来源类型 1间接奖获取积分 2复购奖获取积分 3使用积分 4积分池积分结算")
    @PropertyCheck(name = "积分来源类型 1间接奖获取积分 2复购奖获取积分 3使用积分 4积分池积分结算")
    @Column(name = "type")
    private Integer type;

    /**
     * 关联id 可能是订单id也可能是其他的
     */
    @ApiModelProperty("关联id 可能是订单id也可能是其他的")
    @PropertyCheck(name = "关联id 可能是订单id也可能是其他的")
    @Column(name = "relation_item")
    private String relationItem;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @PropertyCheck(name = "备注")
    @Column(name = "remarks")
    private String remarks;

    /**
     * 此次交易的积分比例
     */
    @ApiModelProperty("此次交易的积分比例")
    @PropertyCheck(name = "此次交易的积分比例")
    @Column(name = "ratio")
    private Double ratio;

    /**
     * 此次交易的积分数量
     */
    @ApiModelProperty("此次交易的积分数量")
    @PropertyCheck(name = "此次交易的积分数量")
    @Column(name = "transaction_score")
    private Double transactionScore;

    /**
     * 当前用户积分
     */
    @ApiModelProperty("当前用户积分")
    @PropertyCheck(name = "当前用户积分")
    @Column(name = "total_score")
    private Double totalScore;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 是否到账 0否1是
     */
    @ApiModelProperty("是否到账 0否1是")
    @PropertyCheck(name = "是否到账")
    @Column(name = "arrive_account_status")
    private Integer arriveAccountStatus;

}

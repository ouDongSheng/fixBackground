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
 * 用户业绩记录
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-12-12 14:40:24
 **/
@ApiModel("用户业绩记录")
@Table(name = "s_user_performance_history")
@Setter
@Getter
@Accessors(chain = true)
public class UserPerformanceHistory {
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
     * 关联id groupId
     */
    @ApiModelProperty("关联id groupId")
    @PropertyCheck(name = "关联id groupId")
    @Column(name = "relation_items")
    private String relationItems;

    /**
     * 业绩金额
     */
    @ApiModelProperty("业绩金额")
    @PropertyCheck(name = "业绩金额")
    @Column(name = "transaction_money")
    private Double transactionMoney;

    /**
     * 当前季度
     */
    @ApiModelProperty("当前季度")
    @PropertyCheck(name = "当前季度")
    @Column(name = "current_quarter")
    private String currentQuarter;

    /**
     * 当前季度业绩
     */
    @ApiModelProperty("当前季度业绩")
    @PropertyCheck(name = "当前季度业绩")
    @Column(name = "current_quarter_money")
    private Double currentQuarterMoney;

    /**
     * 当前业绩
     */
    @ApiModelProperty("当前业绩")
    @PropertyCheck(name = "当前业绩")
    @Column(name = "current_money")
    private Double currentMoney;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;


}

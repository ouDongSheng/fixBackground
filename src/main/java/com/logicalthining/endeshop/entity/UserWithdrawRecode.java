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
 * 用户提现记录
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-13 20:12:10
 **/
@ApiModel("用户提现记录")
@Table(name = "s_user_withdraw_recode")
@Setter
@Getter
@Accessors(chain = true)
public class UserWithdrawRecode {
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
     * 提现金额
     */
    @ApiModelProperty("提现金额")
    @PropertyCheck(name = "提现金额")
    @Column(name = "withdraw_money")
    private Double withdrawMoney;

    /**
     * 提现账号
     */
    @ApiModelProperty("提现账号")
    @PropertyCheck(name = "提现账号")
    @Column(name = "withdraw_account")
    private String withdrawAccount;

    /**
     * 0 初始状态 1提现成功 2提现失败
     */
    @ApiModelProperty("0 初始状态 1提现成功 2提现失败")
    @PropertyCheck(name = "0 初始状态 1提现成功 2提现失败")
    @Column(name = "status")
    private Integer status;

    /**
     * 申请时间
     */
    @ApiModelProperty("申请时间")
    @PropertyCheck(name = "申请时间")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 处理结果
     */
    @ApiModelProperty("处理结果")
    @PropertyCheck(name = "处理结果")
    @Column(name = "process_result")
    private String processResult;

    /**
     * 操作人
     */
    @ApiModelProperty("操作人")
    @PropertyCheck(name = "操作人")
    @Column(name = "process_admin")
    private Integer processAdmin;

    /**
     * 操作时间
     */
    @ApiModelProperty("操作时间")
    @PropertyCheck(name = "操作时间")
    @Column(name = "process_time")
    private Date processTime;


}

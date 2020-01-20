package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户提现密码
 * @author chenLiJia
 * @since 2019-11-13 20:12:10
 * @version 1.0
 **/
@ApiModel("用户提现密码")
@Table(name = "s_user_withdraw_password")
@Setter
@Getter
public class UserWithdrawPassword {
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @PropertyCheck(name = "用户id")
    @Id
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 提现密码
     */
    @ApiModelProperty("提现密码")
    @PropertyCheck(name = "提现密码")
    @Column(name = "password")
    private String password;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @PropertyCheck(name = "修改时间")
    @Column(name = "update_time")
    private Date updateTime;

}

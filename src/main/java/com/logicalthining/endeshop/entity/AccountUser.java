package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.NumberUtil;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

/**
 * 用户账户
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-07 09:37:20
 **/
@ApiModel("用户账户")
@Table(name = "s_account_user")
@Setter
@Getter
@Accessors(chain = true)
public class AccountUser {
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @PropertyCheck(name = "用户id")
    @Id
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 用户余额
     */
    @ApiModelProperty("用户余额")
    @PropertyCheck(name = "用户余额")
    @Column(name = "user_balance")
    private Double userBalance;

    /**
     * 累计收益
     */
    @ApiModelProperty("累计收益")
    @PropertyCheck(name = "累计收益")
    @Column(name = "grand_total_balance")
    private Double grandTotalBalance;

    /**
     * 变更时间
     */
    @ApiModelProperty("变更时间")
    @PropertyCheck(name = "变更时间")
    @Column(name = "update_time")
    private Date updateTime;

    public AccountUser setUserBalance(Double userBalance) {
        if (Objects.isNull(userBalance) || userBalance < 0.0) {
            userBalance = 0.0;
        }
        userBalance = NumberUtil.doubleToFixLengthDouble(userBalance, 2);
        this.userBalance = userBalance;

        return this;
    }

    public AccountUser setGrandTotalBalance(Double grandTotalBalance) {
        if (Objects.isNull(grandTotalBalance) || grandTotalBalance < 0.0) {
            grandTotalBalance = 0.0;
        }
        grandTotalBalance = NumberUtil.doubleToFixLengthDouble(grandTotalBalance, 2);
        this.grandTotalBalance = grandTotalBalance;
        return this;
    }
}

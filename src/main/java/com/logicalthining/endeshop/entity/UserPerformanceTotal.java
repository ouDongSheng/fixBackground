package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.NumberUtil;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

/**
 * 用户累计业绩
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-12 10:19:53
 **/
@ApiModel("用户累计业绩")
@Table(name = "s_user_performance_total")
public class UserPerformanceTotal {
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @PropertyCheck(name = "用户id")
    @Id
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 累计业绩
     */
    @ApiModelProperty("累计业绩")
    @PropertyCheck(name = "累计业绩")
    @Column(name = "grand_total_performance")
    private Double grandTotalPerformance;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @PropertyCheck(name = "更新时间")
    @Column(name = "update_time")
    private Date updateTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getGrandTotalPerformance() {
        return grandTotalPerformance;
    }

    public void setGrandTotalPerformance(Double grandTotalPerformance) {
        if (Objects.isNull(grandTotalPerformance) || grandTotalPerformance < 0.0) {
            grandTotalPerformance = 0.0;
        }
        grandTotalPerformance = NumberUtil.doubleToFixLengthDouble(grandTotalPerformance, 2);
        this.grandTotalPerformance = grandTotalPerformance;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

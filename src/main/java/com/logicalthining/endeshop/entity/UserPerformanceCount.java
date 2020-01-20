package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.NumberUtil;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * 用户业绩季度统计信息
 * 一个季度为一条记录，当用户或者用户的夏季订单完成时,在这个记录修改数据
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-09 15:23:12
 **/
@ApiModel("用户业绩季度统计信息")
@Table(name = "s_user_performance_count")
@Setter
@Getter
public class UserPerformanceCount {
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
     * 季度
     * 格式 2019-1 表示2019第一季度
     */
    @ApiModelProperty("季度")
    @PropertyCheck(name = "季度")
    @Column(name = "quarter")
    private String quarter;

    /**
     * 当前季度业绩
     */
    @ApiModelProperty("当前季度业绩")
    @PropertyCheck(name = "当前季度业绩")
    @Column(name = "current_quarter_money")
    private Double currentQuarterMoney;


    /**
     * 历史业绩 即在当前时间之前的业绩之和
     */
    @ApiModelProperty("历史业绩")
    @PropertyCheck(name = "历史业绩")
    @Column(name = "history_money")
    private Double historyMoney;

    /**
     * 结算状态 0未结算 1已结算
     *
     * @since 上午 10:44 2019/11/12 0012
     **/
    @ApiModelProperty(value = "结算状态 0未结算 1已结算")
    @PropertyCheck(name = "结算状态")
    @Column(name = "archive_status")
    private Integer archiveStatus;

    /**
     * 结算积分比例
     *
     * @since 上午 10:45 2019/11/12 0012
     **/
    @ApiModelProperty(value = "结算积分比例")
    @PropertyCheck(name = "结算积分比例")
    @Column(name = "score_ratio")
    private Double scoreRatio;

    /**
     * 结算获得的积分
     *
     * @since 上午 10:45 2019/11/12 0012
     **/
    @ApiModelProperty(value = "结算获得的积分")
    @PropertyCheck(name = "结算获得的积分")
    @Column(name = "score")
    private Double score;


    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @PropertyCheck(name = "更新时间")
    @Column(name = "update_time")
    private Date updateTime;


    public UserPerformanceCount setCurrentQuarterMoney(Double currentQuarterMoney) {
        if (Objects.isNull(currentQuarterMoney) || currentQuarterMoney < 0) {
            currentQuarterMoney = 0.0;
        }
        currentQuarterMoney = NumberUtil.doubleToFixLengthDouble(currentQuarterMoney, 2);
        this.currentQuarterMoney = currentQuarterMoney;
        return this;
    }
}

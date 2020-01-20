package com.logicalthining.endeshop.common.responseVo.count;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 盈利统计数据
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/12 0012 下午 2:32
 **/
@ApiModel
@Setter
@Getter
public class ProfitCountVo {

    /**
     * 今日销售
     * @since 下午 2:34 2019/11/12 0012
     **/
    @ApiModelProperty(value = "今日销售")
    private Double nowadaysSales;

    /**
     * 平台总销售
     * @since 下午 2:35 2019/11/12 0012
     **/
    @ApiModelProperty(value = "平台总销售")
    private Double totalSales;

    /**
     * 分享奖
     * @since 下午 2:35 2019/11/12 0012
     **/
    @ApiModelProperty(value = "分享奖")
    private Double shareAward;

    /**
     * 间接奖
     * @since 下午 2:35 2019/11/12 0012
     **/
    @ApiModelProperty(value = "间接奖")
    private Double indirectAward;

    /**
     * 复购奖
     * @since 下午 2:35 2019/11/12 0012
     **/
    @ApiModelProperty(value = "复购奖")
    private Double repeatAward;

    /**
     * 奖金池
     * @since 下午 2:35 2019/11/12 0012
     **/
    @ApiModelProperty(value = "奖金池")
    private Double awardPool;

    /**
     * 盈利
     * @since 下午 2:35 2019/11/12 0012
     **/
    @ApiModelProperty(value = "盈利")
    private Double profit;



}

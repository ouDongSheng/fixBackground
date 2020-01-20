package com.logicalthining.endeshop.common.responseVo.userScore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 积分池-详情-团第积分详情-历史收益记录对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/12 0012 上午 11:22
 **/
@ApiModel
@Setter
@Getter
public class UserScoreRecodeVo {

    /**
     * 创建时间
     */
    @ApiModelProperty("获得积分时间")
    private Date createTime;

    /**
     * 积分来源类型 1间接奖获取积分 2复购奖获取积分 3使用积分 4积分池积分结算
     */
    @ApiModelProperty("积分类型 1间接奖获取积分 2复购奖获取积分 3使用积分 4积分池积分结算")
    private Integer type;

    /**
     * 此次交易的积分比例
     */
    @ApiModelProperty("获得积分比例")
    private Double ratio;

    /**
     * 此次交易的积分数量
     */
    @ApiModelProperty("积分")
    private Double transactionScore;


    /**
     * 当前用户积分
     */
    @ApiModelProperty("历史总积分")
    private Double totalScore;

}

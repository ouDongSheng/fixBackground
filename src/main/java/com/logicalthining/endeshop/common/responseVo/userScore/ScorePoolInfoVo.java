package com.logicalthining.endeshop.common.responseVo.userScore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 积分池列表-详情-团队积分详情列表返回对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/12 0012 上午 9:15
 **/
@ApiModel
@Setter
@Getter
public class ScorePoolInfoVo {

    /**
     * 合伙人用户id
     *
     * @since 下午 4:46 2019/11/9 0009
     **/
    @ApiModelProperty(value = "合伙人用户id")
    private Integer partnerUserId;

    /**
     * 合伙人账号
     *
     * @since 下午 4:46 2019/11/9 0009
     **/
    @ApiModelProperty(value = "合伙人账号")
    private String partnerAccount;

    /**
     * 季度
     * @since 上午 10:12 2019/11/12 0012
     **/
    @ApiModelProperty(value = "季度")
    private String quarter;

    /**
     * 当前季度业绩
     */
    @ApiModelProperty("当前季度业绩")
    private Double currentQuarterMoney;


    /**
     * 历史业绩 即在当前时间之前的业绩之和
     */
    @ApiModelProperty("历史业绩")
    private Double historyMoney;

    /**
     * 销售额,表示这个用户的总销售额
     *
     * @since 上午 9:23 2019/11/12 0012
     **/
    @ApiModelProperty(value = "销售额")
    private Double sales;

    /**
     * 是否结算 0未计算 1结算
     * @since 上午 10:50 2019/11/12 0012
     **/
    @ApiModelProperty(value = "是否结算 0未计算 1结算")
    private Integer archiveStatus;

    /**
     * 可获得积分比例
     * 表示当前季度可获得积分比例
     *
     * @since 下午 4:47 2019/11/9 0009
     **/
    @ApiModelProperty(value = "可获得积分比例")
    private Double ratio;

    /**
     * 当前季度可获得积分
     *
     * @since 上午 9:24 2019/11/12 0012
     **/
    @ApiModelProperty(value = "当前季度可获得积分")
    private Double currentScore;

    /**
     * 合伙人姓名
     */
    @ApiModelProperty("合伙人姓名")
    private String partnerName;

    /**
     * 银行卡号
     */
    @ApiModelProperty("银行卡号")
    private String partnerBankCard;


    /**
     * 历史总积分
     * @since 下午 4:49 2019/11/9 0009
     **/
    @ApiModelProperty(value = "历史总积分")
    private Double totalScore;

}

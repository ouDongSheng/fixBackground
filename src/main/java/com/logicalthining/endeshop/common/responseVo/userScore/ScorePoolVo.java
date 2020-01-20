package com.logicalthining.endeshop.common.responseVo.userScore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 积分池返回对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/9 0009 下午 4:43
 **/
@ApiModel
@Setter
@Getter
public class ScorePoolVo {

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
     *
     * @since 下午 4:46 2019/11/9 0009
     **/
    @ApiModelProperty(value = "季度")
    private String quarter;

    /**
     * 可获得积分比例
     *
     * @since 下午 4:47 2019/11/9 0009
     **/
    @ApiModelProperty(value = "可获得积分比例")
    private Double ratio;

    /**
     * 团队总人数
     * 注意 在{@link com.logicalthining.endeshop.entity.UserChildCount} 中保存的是下级数量
     * 所以这里的团队总人数需要在下级数量的基础上加上自己 即+1
     *
     * @since 下午 4:47 2019/11/9 0009
     **/
    @ApiModelProperty(value = "团队总人数")
    private Integer teamPersonCount;

    /**
     * 当前季度业绩
     * @since 下午 4:48 2019/11/9 0009
     **/
    @ApiModelProperty(value = "当前季度业绩")
    private Double currentQuarterMoney;

    /**
     * 历史业绩总计
     * @since 下午 4:48 2019/11/9 0009
     **/
    @ApiModelProperty(value = "历史业绩总计")
    private Double totalMoney;

    /**
     * 可获得积分
     * @since 下午 4:49 2019/11/9 0009
     **/
    @ApiModelProperty(value = "可获得积分")
    private Double score;

    /**
     * 季度结算是否结束 0否1是
     * @since 下午 4:49 2019/11/9 0009
     **/
    @ApiModelProperty(value = "季度结算是否结束 0否1是")
    private Integer cleanStatus;

}

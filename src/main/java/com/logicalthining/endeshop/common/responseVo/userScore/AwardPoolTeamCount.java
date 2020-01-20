package com.logicalthining.endeshop.common.responseVo.userScore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 各个奖金池的团队数量
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/18 0018 下午 7:41
 **/
@Setter
@Getter
@ApiModel
public class AwardPoolTeamCount {

    /**
     * 奖金池层级的起始积分
     *
     * @since 下午 7:43 2019/11/18 0018
     **/
    @ApiModelProperty(value = "奖金池层级的起始积分")
    private Double rangeScoreStart;

    /**
     * 达到这个层级的数量
     *
     * @since 下午 7:43 2019/11/18 0018
     **/
    @ApiModelProperty(value = "达到这个层级的数量")
    private Integer teamCount;

}

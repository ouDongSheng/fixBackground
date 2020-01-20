package com.logicalthining.endeshop.common.responseVo.userScore;

import com.logicalthining.endeshop.common.pojo.systemConfig.AwardPoolConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 积分比例设置返回对象
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/8 0008 下午 1:31
 **/
@ApiModel
@Setter
@Getter
public class ScoreRatioConfigVo {

    /**
     * 奖金池设置
     * @since 下午 1:32 2019/11/8 0008
     **/
    @ApiModelProperty(value = "奖金池设置")
    private List<AwardPoolConfig> awardPoolConfigList;

    /**
     * 间接奖设置
     * @since 下午 1:32 2019/11/8 0008
     **/
    @ApiModelProperty(value = "间接奖设置")
    private Double indirectAwardConfig;

    /**
     * 复购奖设置
     * @since 下午 1:33 2019/11/8 0008
     **/
    @ApiModelProperty(value = "复购奖设置")
    private Double repeatAwardConfig;

}

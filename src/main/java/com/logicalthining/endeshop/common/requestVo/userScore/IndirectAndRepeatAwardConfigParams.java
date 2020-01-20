package com.logicalthining.endeshop.common.requestVo.userScore;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.logicalthining.endeshop.common.checkFunction.PriceCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 设置间接奖和复购奖比例
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/15 0015 下午 6:29
 **/
@ApiModel
@Setter
@Getter
public class IndirectAndRepeatAwardConfigParams {

    /**
     * 间接奖比例
     * @since 下午 6:30 2019/11/15 0015
     **/
    @ApiModelProperty(value = "间接奖比例")
    @PropertyCheck(name = "间接奖比例",checkFunction = PriceCheck.class)
    private Double indirectRatio;

    /**
     * 复购奖比例
     * @since 下午 6:30 2019/11/15 0015
     **/
    @ApiModelProperty(value = "复购奖比例")
    @PropertyCheck(name = "复购奖比例",checkFunction = PriceCheck.class)
    private Double repeatRatio;

}

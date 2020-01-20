package com.logicalthining.endeshop.common.responseVo.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 产品奖金发放累计情况
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/25 0025 下午 4:35
 **/
@ApiModel
@Setter
@Getter
public class ProductAwardCountInfo {

    /**
     * 产品id
     *
     * @since 下午 4:37 2019/11/25 0025
     **/
    @ApiModelProperty(value = "产品id")
    private String productId;

    /**
     * 分享奖累计发放值
     *
     * @since 下午 4:37 2019/11/25 0025
     **/
    @ApiModelProperty(value = "分享奖累计发放值")
    private Double shareAward;

    /**
     * 间接奖累计发放值
     *
     * @since 下午 4:37 2019/11/25 0025
     **/
    @ApiModelProperty(value = "间接奖累计发放值")
    private Double indirectAward;

}

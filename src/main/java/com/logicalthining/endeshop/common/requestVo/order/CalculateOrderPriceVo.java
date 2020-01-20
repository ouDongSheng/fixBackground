package com.logicalthining.endeshop.common.requestVo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 结算订单金额返回对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 下午 7:47
 **/
@ApiModel
@Setter
@Getter
@Accessors(chain = true)
public class CalculateOrderPriceVo {

    /**
     * 订单总金额
     *
     * @since 下午 7:48 2019/11/5 0005
     **/
    @ApiModelProperty(value = "订单总金额")
    private Double orderAmountTotal;

    /**
     * 优惠金额
     *
     * @since 下午 7:49 2019/11/5 0005
     **/
    @ApiModelProperty(value = "优惠金额")
    private Double discountPrice;

    /**
     * 应付金额
     *
     * @since 下午 7:49 2019/11/5 0005
     **/
    @ApiModelProperty(value = "应付金额")
    private Double payAble;

}

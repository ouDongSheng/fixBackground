package com.logicalthining.endeshop.common.requestVo.product;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.logicalthining.endeshop.common.checkFunction.PriceCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 商品添加参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/1 0001 下午 2:03
 **/
@ApiModel
@Setter
@Getter
public class GoodAddParams {

    /**
     * 商品的规格
     *
     * @since 下午 3:10 2019/11/1 0001
     **/
    @ApiModelProperty(value = "商品的规格")
    @PropertyCheck(name = "商品的规格")
    private List<GoodSpecParams> goodSpecParamsList;

    /**
     * 售价
     */
    @ApiModelProperty("售价")
    @PropertyCheck(name = "售价", checkFunction = PriceCheck.class)
    private Double price;

    /**
     * 会员价
     */
    @ApiModelProperty("会员价")
    @PropertyCheck(name = "会员价", checkFunction = PriceCheck.class)
    private Double vipPrice;

    /**
     * 市场价
     */
    @ApiModelProperty("市场价")
    @PropertyCheck(name = "市场价", checkFunction = PriceCheck.class)
    private Double marketPrice;

    /**
     * 商品编号
     */
    @ApiModelProperty("商品编号")
    @PropertyCheck(name = "商品编号")
    private String goodNo;

}

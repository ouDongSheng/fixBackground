package com.logicalthining.endeshop.common.responseVo.count;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 统计信息-销售-返回对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/12 0012 下午 3:49
 **/
@ApiModel
@Setter
@Getter
public class SaleCountProvinceVo {

    /**
     * 省份
     *
     * @since 下午 3:50 2019/11/12 0012
     **/
    @ApiModelProperty(value = "省份")
    private String province;

    /**
     * 销售值
     *
     * @since 下午 3:50 2019/11/12 0012
     **/
    @ApiModelProperty(value = "销售值")
    private Double sales;

}

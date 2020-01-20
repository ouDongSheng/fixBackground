package com.logicalthining.endeshop.common.requestVo.count;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 统计销售查询条件
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/12 0012 下午 3:46
 **/
@ApiModel
@Setter
@Getter
public class CountSaleQueryParams {

    /**
     * 年
     * @since 下午 3:46 2019/11/12 0012
     **/
    @ApiModelProperty(value = "年")
    private Integer year;

    /**
     * 月
     * @since 下午 3:47 2019/11/12 0012
     **/
    @ApiModelProperty(value = "月")
    private Integer month;

}

package com.logicalthining.endeshop.common.responseVo.count;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
public class SaleCountVo {



    /**
     * 总销售值
     *
     * @since 下午 3:50 2019/11/12 0012
     **/
    @ApiModelProperty(value = "总销售值")
    private Double totalSales;

    /**
     * 每个省的销售值
     * @since 下午 3:53 2019/11/12 0012
     **/
    @ApiModelProperty(value = "每个省的销售值")
    private List<SaleCountProvinceVo> list;

}

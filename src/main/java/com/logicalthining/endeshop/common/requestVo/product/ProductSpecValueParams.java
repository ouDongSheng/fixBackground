package com.logicalthining.endeshop.common.requestVo.product;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 产品规格值参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/1 0001 下午 3:01
 **/
@ApiModel
@Setter
@Getter
public class ProductSpecValueParams {

    /**
     * 图片规格值
     *
     * @since 下午 3:02 2019/11/1 0001
     **/
    @ApiModelProperty(value = "产品图片规格值")
    @PropertyCheck(name = "产品图片规格值")
    private String imageValue;

    /**
     * 规格值
     *
     * @since 下午 3:02 2019/11/1 0001
     **/
    @ApiModelProperty(value = "产品规格值")
    @PropertyCheck(name = "产品规格值")
    private String value;

}

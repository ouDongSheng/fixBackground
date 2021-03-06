package com.logicalthining.endeshop.common.requestVo.product;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.logicalthining.endeshop.common.checkFunction.StateCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 产品修改参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/1 0001 下午 2:13
 **/
@ApiModel
@Setter
@Getter
public class ProductUpdateParams extends ProductAddParams {

    /**
     * 产品id
     *
     * @since 下午 2:15 2019/11/1 0001
     **/
    @ApiModelProperty(value = "产品id")
    @PropertyCheck(name = "产品id")
    private String id;


}

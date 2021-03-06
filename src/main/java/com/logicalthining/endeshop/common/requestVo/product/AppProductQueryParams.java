package com.logicalthining.endeshop.common.requestVo.product;

import com.logicalthining.endeshop.common.requestVo.PageAbleVo;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 客户端产品查询参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 上午 11:26
 **/
@ApiModel
@Setter
@Getter
public class AppProductQueryParams extends PageAbleVo {
    private int masterCategory;

    private int productType;
}

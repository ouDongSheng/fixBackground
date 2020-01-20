package com.logicalthining.endeshop.common.responseVo.count;

import lombok.Getter;
import lombok.Setter;

/**
 * 产品销量对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/18 0018 下午 1:41
 **/
@Setter
@Getter
public class ProductSaleVo {

    /**
     * 产品id
     *
     * @since 下午 1:42 2019/11/18 0018
     **/
    private String productId;

    /**
     * 产品销量
     *
     * @since 下午 1:42 2019/11/18 0018
     **/
    private Integer salesVolume;

}

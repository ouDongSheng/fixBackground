package com.logicalthining.endeshop.common.pojo.order;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 订单备注
 * 因为在本系统中订单备注有用户备注和商家备注,为了不增加字段,所以备注用json存取
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/7 0007 下午 5:00
 **/
@Setter
@Getter
@Accessors(chain = true)
public class OrderRemarks {

    /**
     * 用户备注
     * @since 下午 5:01 2019/11/7 0007
     **/
    private String customRemarks;

    /**
     * 商家备注
     * @since 下午 5:01 2019/11/7 0007
     **/
    private String shopRemarks;

}

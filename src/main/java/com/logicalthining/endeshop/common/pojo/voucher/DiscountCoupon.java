package com.logicalthining.endeshop.common.pojo.voucher;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 折扣券
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 下午 6:06
 **/
@Setter
@Getter
@Accessors(chain = true)
public class DiscountCoupon {

    /**
     * 优惠券代号
     *
     * @since 下午 6:08 2019/11/5 0005
     **/
    private String voucherKey = DiscountCoupon.class.getSimpleName();

    /**
     * 条件金额
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    private Double conditionMoney;

    /**
     * 条件数量
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    private Integer conditionCount;

    /**
     * 折扣率
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    private Double discount;

    /**
     * 是否生效 0 未生效 1生效
     * @since 下午 6:09 2019/11/5 0005
     **/
    private Integer effective;

}

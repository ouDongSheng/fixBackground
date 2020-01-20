package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import javax.persistence.*;

/**
 * 订单
 * @author chenLiJia
 * @since 2019-11-05 16:39:11
 * @version 1.0
 **/
@ApiModel("订单")
@Table(name = "s_shopping_order")
@Setter
@Getter
@Accessors(chain = true)
public class ShoppingOrder {
    /**
     * 订单编号
     */
    @ApiModelProperty("订单编号")
    @PropertyCheck(name = "订单编号")
    @Id
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 客户标识
     */
    @ApiModelProperty("客户标识")
    @PropertyCheck(name = "客户标识")
    @Column(name = "custom")
    private String custom;

    /**
     * 商家标识
     */
    @ApiModelProperty("商家标识")
    @PropertyCheck(name = "商家标识")
    @Column(name = "shops")
    private String shops;

    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    @PropertyCheck(name = "商品id")
    @Column(name = "goods_id")
    private String goodsId;

    /**
     * 商品数量
     */
    @ApiModelProperty("商品数量")
    @PropertyCheck(name = "商品数量")
    @Column(name = "count")
    private Integer count;

    /**
     * 状态 0初始状态 983042成功 983041 失败或者取消
     */
    @ApiModelProperty("状态 0初始状态 983042成功 983041 失败或者取消")
    @PropertyCheck(name = "状态 0初始状态 983042成功 983041 失败或者取消")
    @Column(name = "state")
    private Integer state;

    /**
     * 商品金额
     */
    @ApiModelProperty("商品支付时单价")
    @PropertyCheck(name = "商品支付时单价")
    @Column(name = "good_price")
    private Double goodPrice;

    /**
     * 商品金额
     */
    @ApiModelProperty("商品金额")
    @PropertyCheck(name = "商品金额")
    @Column(name = "product_amount_total")
    private Double productAmountTotal;

    /**
     * 支付渠道
     */
    @ApiModelProperty("支付渠道")
    @PropertyCheck(name = "支付渠道")
    @Column(name = "pay_channel")
    private String payChannel;

    /**
     * 商户支付订单号
     */
    @ApiModelProperty("商户支付订单号")
    @PropertyCheck(name = "商户支付订单号")
    @Column(name = "pay_record")
    private String payRecord;

    /**
     * 第三方支付流水号 在回调成功之后生成
     */
    @ApiModelProperty("第三方支付流水号 在回调成功之后生成")
    @PropertyCheck(name = "第三方支付流水号 在回调成功之后生成")
    @Column(name = "transaction_id")
    private String transactionId;

    /**
     * 订单金额
     */
    @ApiModelProperty("订单金额")
    @PropertyCheck(name = "订单金额")
    @Column(name = "order_amount_total")
    private Double orderAmountTotal;

    /**
     * 支付时间
     */
    @ApiModelProperty("支付时间")
    @PropertyCheck(name = "支付时间")
    @Column(name = "pay_time")
    private Date payTime;

    /**
     * 组订单id
     */
    @ApiModelProperty("组订单id")
    @PropertyCheck(name = "组订单id")
    @Column(name = "group_id")
    private String groupId;

    /**
     * 应付金额
     */
    @ApiModelProperty("应付金额")
    @PropertyCheck(name = "应付金额")
    @Column(name = "payable")
    private Double payable;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 备注
     * @see com.logicalthining.endeshop.common.pojo.order.OrderRemarks
     */
    @ApiModelProperty("备注")
    @PropertyCheck(name = "备注")
    @Column(name = "remarks")
    private String remarks;

    /**
     * 订单快照
     * @see com.logicalthining.endeshop.common.responseVo.product.AdminProductVo
     */
    @ApiModelProperty("订单快照")
    @PropertyCheck(name = "订单快照")
    @Column(name = "details_json")
    private String detailsJson;

    /**
     * 使用的优惠券json数组
     * @see com.logicalthining.endeshop.common.pojo.voucher.DiscountCoupon
     */
    @ApiModelProperty("使用的优惠券json数组")
    @PropertyCheck(name = "使用的优惠券json数组")
    @Column(name = "order_coupon")
    private String orderCoupon;
}

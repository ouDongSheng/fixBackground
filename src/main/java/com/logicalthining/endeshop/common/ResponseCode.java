package com.logicalthining.endeshop.common;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


/**
 * @author User
 */

public enum ResponseCode implements BaseEnum<Integer> {
    /**
     * 响应状态数据
     */
    SUCCESS(200),
    ERROR(500),
	图片格式不对(501),
	视频格式不对(502),
	不允许为空(503),
	重新登录(504),
	库存为空(505),
	超出库存(506),
	待审核(507),
	审核未通过(508),
    支付宝下单支付异常(509),
    支付宝查询订单异常(510),
    支付宝支付成功(511),
    微信下单生成预订单失败(512),
    微信回调显示支付异常(512),
    微信支付失败(513),
    退款成功(514),
    退款失败(514),
    超出起售数量(515),
    低于起售数量(516),
    商品没有上架(517),
    支付宝申请退款异常(518),
    支付宝退款异常(519),
    支付宝退款成功(520),
    支付宝查询退款异常(521),
    支付宝转账异常(522);


    private int code;
    private String msg;

    private ResponseCode(int code) {
        this.code = code;
        this.msg = this.name();
    }

    private ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public Integer code() {
        return this.code;
    }


    public int getCode() {
        return this.code;
    }
}

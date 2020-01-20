package com.logicalthining.endeshop.common.enums;

/**
 * 系统配置的key
 * 存在数据库中可动态修改
 *
 * @author chenlijia
 * @version 1.0
 * @see com.logicalthining.endeshop.entity.SystemConfig  系统配置
 * @see com.logicalthining.endeshop.service.SystemConfigServiceI#selectByConfigKey(String) 查询系统配置
 * @see com.logicalthining.endeshop.common.pojo.Constants#SYSTEM_CONFIG_INIT_MAP 系统配置初始值
 * @since 2019/11/7 0007 上午 10:00
 **/
public enum SystemConfigKey {

    //分享奖为用户首次下单升级为会员，并且这个订单是分享来的,就可以得到分享奖
    SHARE_AWARD_RATIO("share_award_ratio", "分享奖比率"),
    //会员或者合伙人重复购买享折扣
    VIP_DISCOUNT("vip_discount", "重复购买折扣"),
    //合伙人的下属会员复购产品,合伙人获得复购奖
    REPEAT_AWARD_RATIO("repeat_award_ratio", "复购奖比率"),
    //普通会员升级为会员,其上级合伙人获得间接奖
    INDIRECT_AWARD_RATIO("indirect_award_ratio", "间接奖比率"),
    //成为会员需要购买的最小商品金额
    VIP_LIMIT_MONEY("vip_limit_money", "成为会员需要购买的最小商品金额"),
    //成为合伙人需要购买的最小商品金额
    PARTNER_LIMIT_MONEY("partner_limit_money", "成为合伙人需要购买的最小商品金额"),
    //奖金池设置  一个范围集合
    AWARD_POOL_CONFIG("award_pool_config", "奖金池设置"),
    ;

    SystemConfigKey(String key, String remarks) {
        this.key = key;
        this.remarks = remarks;
    }

    private String key;

    private String remarks;

    public String getKey() {
        return key;
    }
}

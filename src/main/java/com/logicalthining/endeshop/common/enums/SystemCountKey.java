package com.logicalthining.endeshop.common.enums;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/12 0012 下午 2:50
 **/
public enum SystemCountKey {

    NOWADAYS_SALES("nowadays_sales", "今日销售"),
    SYSTEM_TOTAL_SALES("system_total_sales", "平台总销售"),
    SHARE_AWARD("share_award", "分享奖"),
    INDIRECT_AWARD("indirect_award", "间接奖"),
    REPEAT_AWARD("repeat_award", "复购奖"),
    AWARD_POOL("award_pool", "奖金池"),
    ;

    SystemCountKey(String name, String remarks) {
        this.name = name;
        this.remarks = remarks;
    }

    private String name;

    private String remarks;

    public String getName() {
        return name;
    }
}

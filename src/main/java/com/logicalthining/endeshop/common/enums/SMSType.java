package com.logicalthining.endeshop.common.enums;

/**
 * 短信验证码类型
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/14 0014 上午 11:26
 **/
public enum SMSType {

    LOGIN("【恩德生态】正在进行登录操作，您的验证码是#code#"),
    WITHDRAW("【恩德生态】正在进行提现操作，您的验证码是#code#"),
    ;

    SMSType(String template) {
        this.template = template;
    }

    private String template;

    public String getTemplate() {
        return template;
    }
}

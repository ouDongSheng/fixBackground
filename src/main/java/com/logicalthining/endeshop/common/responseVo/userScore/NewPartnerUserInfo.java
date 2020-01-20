package com.logicalthining.endeshop.common.responseVo.userScore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 新晋合伙人信息
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/11 0011 下午 5:42
 **/
@ApiModel
@Setter
@Getter
public class NewPartnerUserInfo {

    /**
     * 用户id
     * @since 下午 5:42 2019/11/11 0011
     **/
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 用户账号
     * @since 下午 5:43 2019/11/11 0011
     **/
    @ApiModelProperty(value = "用户账号")
    private String account;

    /**
     * 晋升时间
     * @since 下午 5:43 2019/11/11 0011
     **/
    @ApiModelProperty(value = "晋升时间")
    private Date promotionTime;

}

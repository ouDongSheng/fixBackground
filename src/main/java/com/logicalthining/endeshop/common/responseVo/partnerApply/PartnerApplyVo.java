package com.logicalthining.endeshop.common.responseVo.partnerApply;

import com.logicalthining.endeshop.entity.PartnerApplyRecode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 合伙人申请返回对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/6 0006 上午 9:44
 **/
@ApiModel
@Setter
@Getter
public class PartnerApplyVo extends PartnerApplyRecode {

    /**
     * 账号
     * @since 上午 9:55 2019/11/6 0006
     **/
    @ApiModelProperty(value = "账号")
    private String account;

    /**
     * 注册时间
     *
     * @since 上午 9:45 2019/11/6 0006
     **/
    @ApiModelProperty(value = "注册时间")
    private Date registerTime;

}

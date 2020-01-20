package com.logicalthining.endeshop.common.requestVo.partnerApply;

import com.github.chenlijia1111.utils.common.constant.RegConstant;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.github.chenlijia1111.utils.core.enums.PropertyCheckType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 合伙人申请参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 下午 8:02
 **/
@ApiModel
@Setter
@Getter
public class AddParams {

    /**
     * 合伙人姓名
     */
    @ApiModelProperty("合伙人姓名")
    @PropertyCheck(name = "合伙人姓名")
    private String partnerName;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @PropertyCheck(name = "手机号", checkType = PropertyCheckType.MOBILE_PHONE)
    private String telephone;

    /**
     * 银行卡号
     */
    @ApiModelProperty("银行卡号")
    @PropertyCheck(name = "银行卡号", regMatcher = RegConstant.INT)
    private String partnerBankCard;

    /**
     * 身份证正面图片
     */
    @ApiModelProperty("身份证正面图片")
    @PropertyCheck(name = "身份证正面图片")
    private String idCardImageFront;

    /**
     * 身份证反面图片
     */
    @ApiModelProperty("身份证反面图片")
    @PropertyCheck(name = "身份证反面图片")
    private String idCardImageBack;


    /**
     * 分享用户的id
     * @since 上午 11:06 2019/11/6 0006
     **/
    @ApiModelProperty(value = "分享用户的id")
    private Integer shareUserId;

}

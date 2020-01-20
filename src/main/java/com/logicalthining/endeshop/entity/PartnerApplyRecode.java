package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * 合伙人申请记录
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-05 19:58:46
 **/
@ApiModel("合伙人申请记录")
@Table(name = "s_partner_apply_recode")
@Setter
@Getter
@Accessors(chain = true)
public class PartnerApplyRecode {
    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    @PropertyCheck(name = "主键id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 申请的用户id
     */
    @ApiModelProperty("申请的用户id")
    @PropertyCheck(name = "申请的用户id")
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 合伙人姓名
     */
    @ApiModelProperty("合伙人姓名")
    @PropertyCheck(name = "合伙人姓名")
    @Column(name = "partner_name")
    private String partnerName;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @PropertyCheck(name = "手机号")
    @Column(name = "telephone")
    private String telephone;

    /**
     * 银行卡号
     */
    @ApiModelProperty("银行卡号")
    @PropertyCheck(name = "银行卡号")
    @Column(name = "partner_bank_card")
    private String partnerBankCard;

    /**
     * 身份证正面图片
     */
    @ApiModelProperty("身份证正面图片")
    @PropertyCheck(name = "身份证正面图片")
    @Column(name = "id_card_image_front")
    private String idCardImageFront;

    /**
     * 身份证反面图片
     */
    @ApiModelProperty("身份证反面图片")
    @PropertyCheck(name = "身份证反面图片")
    @Column(name = "id_card_image_back")
    private String idCardImageBack;

    /**
     * 申请时间
     */
    @ApiModelProperty("申请时间")
    @PropertyCheck(name = "申请时间")
    @Column(name = "apply_time")
    private Date applyTime;

    /**
     * 审核状态 0初始状态 1审核通过 2审核不通过
     */
    @ApiModelProperty("审核状态 0初始状态 1审核通过 2审核不通过")
    @PropertyCheck(name = "审核状态 0初始状态 1审核通过 2审核不通过")
    @Column(name = "review_status")
    private Integer reviewStatus;

    /**
     * 审核人
     */
    @ApiModelProperty("审核人")
    @PropertyCheck(name = "审核人")
    @Column(name = "review_admin")
    private Integer reviewAdmin;

    /**
     * 审核时间
     */
    @ApiModelProperty("审核时间")
    @PropertyCheck(name = "审核时间")
    @Column(name = "review_time")
    private Date reviewTime;


}

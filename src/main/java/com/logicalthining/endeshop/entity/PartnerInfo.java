package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 合伙人信息
 * 在合伙人申请审核通过之后
 * 在合伙人信息中创建一份数据以保存合伙人信息
 * <p>
 * 会员推荐的用户升级为合伙人后，不再与原会员有上下级关系。原合伙人不再享受奖金。但销售业绩归原合伙人名下业绩
 * 意思就是说如果这个用户是会员或者vip，那么他的下级升级成合伙人了。这个用户和他的下级就没有关系了
 * 但是如果这个会员本身就是合伙人，那他的下级也升级为合伙人了，这个父用户就不在享受奖金了，但是销售业绩还是归这个父用户名下
 * <p>
 * 也就是说,在以后处理奖金的时候,需要过滤掉那些下级是合伙人的用户
 * 然后再就是在用户升级为合伙人的时候要进行判断，判断他是否有上级，
 * 如果有上级，看他的上级是什么身份，如果上级不是合伙人，就解除上下级之间的关系
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-06 10:17:24
 **/
@ApiModel("合伙人信息")
@Table(name = "s_partner_info")
@Setter
@Getter
@Accessors(chain = true)
public class PartnerInfo {
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @PropertyCheck(name = "用户id")
    @Id
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
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;


}

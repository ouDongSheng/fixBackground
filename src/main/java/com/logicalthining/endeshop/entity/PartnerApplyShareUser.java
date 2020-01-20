package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * 合伙人申请 分享人
 * 表示用户在申请合伙人的时候，是不是通过其他的合伙人的链接进行申请的
 *
 * 只有是合伙人推荐出去的链接才有用的,如果是会员推荐出去的,不能行成绑定
 * @author chenLiJia
 * @since 2019-11-06 11:44:16
 * @version 1.0
 **/
@ApiModel("合伙人申请 分享人")
@Table(name = "s_partner_apply_share_user")
@Setter
@Getter
@Accessors(chain = true)
public class PartnerApplyShareUser {
    /**
     * 合伙人申请id
     */
    @ApiModelProperty("合伙人申请id")
    @PropertyCheck(name = "合伙人申请id")
    @Id
    @Column(name = "partner_apply_id")
    private Integer partnerApplyId;

    /**
     * 分享人id
     */
    @ApiModelProperty("分享人id")
    @PropertyCheck(name = "分享人id")
    @Column(name = "share_user_id")
    private Integer shareUserId;


}

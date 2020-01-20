package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户下级数量
 * 在用户绑定以及解绑的时候就计算好用户的下级数量,不然，下级层级太多，查询不过来
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-09 15:23:12
 **/
@ApiModel("用户下级数量")
@Table(name = "s_user_child_count")
@Setter
@Getter
public class UserChildCount {
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @PropertyCheck(name = "用户id")
    @Id
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 下级总数量
     */
    @ApiModelProperty("下级总数量")
    @PropertyCheck(name = "下级总数量")
    @Column(name = "child_count")
    private Integer childCount;

    /**
     * 下级合伙人数量
     */
    @ApiModelProperty("下级合伙人数量")
    @PropertyCheck(name = "下级合伙人数量")
    @Column(name = "partner_child_count")
    private Integer partnerChildCount;

    /**
     * 下级普通用户数量
     */
    @ApiModelProperty("下级普通用户数量")
    @PropertyCheck(name = "下级普通用户数量")
    @Column(name = "common_user_count")
    private Integer commonUserCount;

    /**
     * 下级会员数量
     */
    @ApiModelProperty("下级会员数量")
    @PropertyCheck(name = "下级会员数量")
    @Column(name = "vip_user_count")
    private Integer vipUserCount;
}

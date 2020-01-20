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
 * 用户表
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-10-31 19:10:44
 **/
@ApiModel("用户表")
@Table(name = "s_user")
@Setter
@Getter
@Accessors(chain = true)
public class User {
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
     * 账号
     */
    @ApiModelProperty("账号")
    @PropertyCheck(name = "账号")
    @Column(name = "account")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    @PropertyCheck(name = "密码")
    @Column(name = "password")
    private String password;

    /**
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    @PropertyCheck(name = "用户名称")
    @Column(name = "name")
    private String name;

    /**
     * 1 普通用户 2会员 3合伙人
     * {@link com.logicalthining.endeshop.common.enums.UserRole}
     */
    @ApiModelProperty("1 普通用户 2会员 3合伙人")
    @PropertyCheck(name = "1 普通用户 2会员 3合伙人")
    @Column(name = "role")
    private Integer role;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    @PropertyCheck(name = "头像")
    @Column(name = "head_image")
    private String headImage;

    /**
     * 有效时间
     */
    @ApiModelProperty("有效时间")
    @PropertyCheck(name = "有效时间")
    @Column(name = "limit_time")
    private Date limitTime;

    /**
     * 是否启用 0未启用 1启用
     */
    @ApiModelProperty("是否启用 0未启用 1启用")
    @PropertyCheck(name = "是否启用 0未启用 1启用")
    @Column(name = "open_status")
    private Integer openStatus;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @PropertyCheck(name = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人,自己注册的没有
     */
    @ApiModelProperty("创建人,自己注册的没有")
    @PropertyCheck(name = "创建人,自己注册的没有")
    @Column(name = "create_admin")
    private Integer createAdmin;

    /**
     * 是否删除 0未删除 1删除
     */
    @ApiModelProperty("是否删除 0未删除 1删除")
    @PropertyCheck(name = "是否删除 0未删除 1删除")
    @Column(name = "delete_status")
    private Integer deleteStatus;


}

package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import javax.persistence.*;

/**
 * 后台用户表
 * @author chenLiJia
 * @since 2019-10-30 09:53:30
 * @version 1.0
 **/
@ApiModel("后台用户表")
@Table(name = "s_admin")
@Setter
@Getter
@Accessors(chain = true)
public class Admin {
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
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    @PropertyCheck(name = "用户名称")
    @Column(name = "name")
    private String name;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    @PropertyCheck(name = "密码")
    @Column(name = "password")
    private String password;

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    @PropertyCheck(name = "角色id")
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 启用状态 0未启用 1启用
     */
    @ApiModelProperty("启用状态 0未启用 1启用")
    @PropertyCheck(name = "启用状态 0未启用 1启用")
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
     * 创建人
     */
    @ApiModelProperty("创建人")
    @PropertyCheck(name = "创建人")
    @Column(name = "create_user")
    private Integer createUser;

    /**
     * 删除状态 0未删除 1已删除
     */
    @ApiModelProperty("删除状态 0未删除 1已删除")
    @PropertyCheck(name = "删除状态 0未删除 1已删除")
    @Column(name = "delete_status")
    private Integer deleteStatus;


}

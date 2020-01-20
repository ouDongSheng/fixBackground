package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * 权限表
 * @author chenLiJia
 * @since 2019-10-30 09:53:30
 * @version 1.0
 **/
@ApiModel("权限表")
@Table(name = "s_auth")
@Setter
@Getter
@Accessors(chain = true)
public class Auth {
    /**
     * 主键Id
     */
    @ApiModelProperty("主键Id")
    @PropertyCheck(name = "主键Id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 权限名称
     */
    @ApiModelProperty("权限名称")
    @PropertyCheck(name = "权限名称")
    @Column(name = "name")
    private String name;

    /**
     * 权限类别 1模块 2页面 3按钮
     */
    @ApiModelProperty("权限类别 1模块 2页面 3按钮")
    @PropertyCheck(name = "权限类别")
    @Column(name = "auth_type")
    private Integer authType;

    /**
     * 页面路径
     */
    @ApiModelProperty("页面路径")
    @PropertyCheck(name = "页面路径")
    @Column(name = "page_url")
    private String pageUrl;

    /**
     * 父权限id  顶级权限为0
     */
    @ApiModelProperty("父权限id  顶级权限为0")
    @PropertyCheck(name = "父权限id  顶级权限为0")
    @Column(name = "parent_auth")
    private Integer parentAuth;


}

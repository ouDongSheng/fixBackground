package com.logicalthining.endeshop.entity;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * 角色权限关联表
 * @author chenLiJia
 * @since 2019-10-30 09:53:30
 * @version 1.0
 **/
@ApiModel("角色权限关联表")
@Table(name = "s_role_auth")
@Setter
@Getter
@Accessors(chain = true)
public class RoleAuth {
    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    @PropertyCheck(name = "主键id")
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    @PropertyCheck(name = "角色id")
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 权限id
     */
    @ApiModelProperty("权限id")
    @PropertyCheck(name = "权限id")
    @Column(name = "auth_id")
    private Integer authId;

}

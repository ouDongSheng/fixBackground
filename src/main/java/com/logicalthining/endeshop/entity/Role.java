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
 * 角色表
 * @author chenLiJia
 * @since 2019-10-30 09:53:30
 * @version 1.0
 **/
@ApiModel("角色表")
@Table(name = "s_role")
@Setter
@Getter
@Accessors(chain = true)
public class Role {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @PropertyCheck(name = "主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    @PropertyCheck(name = "角色名称")
    @Column(name = "role_name")
    private String roleName;

    /**
     * 是否启用 0未启用 1启用
     */
    @ApiModelProperty("是否启用 0未启用 1启用")
    @PropertyCheck(name = "是否启用 0未启用 1启用")
    @Column(name = "open_status")
    private Integer openStatus;

    /**
     * 操作人
     */
    @ApiModelProperty("操作人")
    @PropertyCheck(name = "操作人")
    @Column(name = "update_user")
    private Integer updateUser;

    /**
     * 操作时间
     */
    @ApiModelProperty("操作时间")
    @PropertyCheck(name = "操作时间")
    @Column(name = "update_time")
    private Date updateTime;

}

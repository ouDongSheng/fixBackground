package com.logicalthining.endeshop.common.requestVo.admin;

import com.github.chenlijia1111.utils.common.constant.RegConstant;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.logicalthining.endeshop.common.checkFunction.StateCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

/**
 * 添加后台用户请求参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/30 0030 上午 10:16
 **/
@ApiModel
@Setter
@Getter
public class AddParams {

    /**
     * 账号
     */
    @ApiModelProperty("账号")
    @PropertyCheck(name = "账号", regMatcher = RegConstant.MOBILE_PHONE)
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
    private String password;

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    @PropertyCheck(name = "角色id")
    private Integer roleId;

    /**
     * 启用状态 0未启用 1启用
     */
    @ApiModelProperty("启用状态 0未启用 1启用")
    @PropertyCheck(name = "启用状态", checkFunction = StateCheck.class)
    private Integer openStatus;

}

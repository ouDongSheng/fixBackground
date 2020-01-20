package com.logicalthining.endeshop.common.requestVo.user;

import com.github.chenlijia1111.utils.common.constant.RegConstant;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.logicalthining.endeshop.common.checkFunction.StateCheck;
import com.logicalthining.endeshop.common.checkFunction.UserRoleCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用户添加参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/31 0031 下午 7:15
 **/
@ApiModel
@Setter
@Getter
public class AddParams {


    /**
     * 账号
     */
    @ApiModelProperty("账号")
    @PropertyCheck(name = "手机号码", regMatcher = RegConstant.MOBILE_PHONE)
    private String account;


    /**
     * 1 普通用户 2会员 3合伙人
     */
    @ApiModelProperty("角色 1 普通用户 2会员 3合伙人")
    @PropertyCheck(name = "角色", checkFunction = UserRoleCheck.class)
    private Integer role;


    /**
     * 有效时间
     */
    @ApiModelProperty("有效时间")
    @PropertyCheck(name = "有效时间")
    private Date limitTime;

    /**
     * 是否启用 0未启用 1启用
     */
    @ApiModelProperty("是否启用 0未启用 1启用")
    @PropertyCheck(name = "状态", checkFunction = StateCheck.class)
    private Integer openStatus;

}

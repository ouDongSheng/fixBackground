package com.logicalthining.endeshop.common.requestVo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 修改密码参数
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/7 0007 下午 2:18
 **/
@ApiModel
@Setter
@Getter
public class UpdatePasswordParams {

    /**
     * 原密码
     * @since 下午 2:18 2019/11/7 0007
     **/
    @ApiModelProperty(value = "原密码")
    private String oldPassword;

    /**
     * 新密码
     * @since 下午 2:19 2019/11/7 0007
     **/
    @ApiModelProperty(value = "新密码")
    private String newPassword;

}

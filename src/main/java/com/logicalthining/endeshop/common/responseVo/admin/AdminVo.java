package com.logicalthining.endeshop.common.responseVo.admin;

import com.logicalthining.endeshop.entity.Admin;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 后台用户返回对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/30 0030 上午 10:27
 **/
@ApiModel
@Setter
@Getter
public class AdminVo extends Admin {

    /**
     * 角色名称
     *
     * @since 上午 10:28 2019/10/30 0030
     **/
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 操作人姓名
     * @since 上午 11:46 2019/10/30 0030
     **/
    @ApiModelProperty(value = "操作人姓名")
    private String createUserName;

}

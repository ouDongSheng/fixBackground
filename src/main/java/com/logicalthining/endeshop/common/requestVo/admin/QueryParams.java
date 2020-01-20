package com.logicalthining.endeshop.common.requestVo.admin;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.logicalthining.endeshop.common.requestVo.PageTimeLimitQueryVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 后台用户查询条件
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/30 0030 上午 10:23
 **/
@ApiModel
@Setter
@Getter
public class QueryParams extends PageTimeLimitQueryVo {

    /**
     * 账号名称
     */
    @ApiModelProperty("账号名称")
    @PropertyCheck(name = "账号名称")
    private String account;


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
    @PropertyCheck(name = "启用状态 0未启用 1启用")
    private Integer openStatus;


}

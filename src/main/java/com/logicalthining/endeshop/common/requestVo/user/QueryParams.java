package com.logicalthining.endeshop.common.requestVo.user;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.logicalthining.endeshop.common.requestVo.PageTimeLimitQueryVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户管理列表查询参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/31 0031 下午 7:21
 **/
@ApiModel
@Setter
@Getter
public class QueryParams extends PageTimeLimitQueryVo {

    /**
     * 账号
     */
    @ApiModelProperty("账号")
    @PropertyCheck(name = "账号")
    private String account;


    /**
     * 1 普通用户 2会员 3合伙人
     */
    @ApiModelProperty("角色 1 普通用户 2会员 3合伙人")
    @PropertyCheck(name = "角色")
    private Integer role;

}

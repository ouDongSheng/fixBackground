package com.logicalthining.endeshop.common.requestVo.role;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.logicalthining.endeshop.common.requestVo.PageAbleVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/30 0030 下午 2:43
 **/
@ApiModel
@Setter
@Getter
public class QueryParams extends PageAbleVo {

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    @PropertyCheck(name = "角色名称")
    private String roleName;

    /**
     * 是否启用 0未启用 1启用
     */
    @ApiModelProperty("是否启用 0未启用 1启用")
    @PropertyCheck(name = "是否启用 0未启用 1启用")
    private Integer openStatus;

    /**
     * 创建时间筛选  多少天以内
     *
     * @since 下午 2:44 2019/10/30 0030
     **/
    @ApiModelProperty(value = "创建时间筛选  多少天以内")
    private Integer limitDays;

}

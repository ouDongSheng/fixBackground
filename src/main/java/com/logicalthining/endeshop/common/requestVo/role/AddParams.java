package com.logicalthining.endeshop.common.requestVo.role;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 角色添加参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/30 0030 下午 2:40
 **/
@ApiModel
@Setter
@Getter
public class AddParams {


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
     * 权限集合
     *
     * @since 下午 2:41 2019/10/30 0030
     **/
    @ApiModelProperty(value = "权限集合")
    private List<Integer> authIdList;

}

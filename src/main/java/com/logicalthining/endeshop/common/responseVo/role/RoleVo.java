package com.logicalthining.endeshop.common.responseVo.role;

import com.logicalthining.endeshop.common.responseVo.auth.AuthVo;
import com.logicalthining.endeshop.entity.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 角色返回对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/30 0030 下午 3:44
 **/
@ApiModel
@Setter
@Getter
public class RoleVo extends Role {

    /**
     * 该角色的权限集合
     *
     * @since 下午 3:45 2019/10/30 0030
     **/
    @ApiModelProperty(value = "该角色的权限集合")
    private List<AuthVo> authVoList;

}

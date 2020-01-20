package com.logicalthining.endeshop.common.responseVo.user;

import com.logicalthining.endeshop.common.pojo.Constants;
import com.logicalthining.endeshop.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * 用户返回对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/31 0031 下午 7:43
 **/
@ApiModel
@Setter
@Getter
public class UserVo extends User {

    /**
     * 角色名称
     *
     * @since 下午 7:44 2019/10/31 0031
     **/
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @Override
    public User setRole(Integer role) {
        super.setRole(role);

        if (Objects.nonNull(role)) {
            String s = Constants.USER_ROLE_MAP.get(role);
            this.roleName = s;
        }

        return this;
    }
}

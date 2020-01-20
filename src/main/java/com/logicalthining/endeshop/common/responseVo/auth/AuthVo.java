package com.logicalthining.endeshop.common.responseVo.auth;

import com.logicalthining.endeshop.entity.Auth;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 权限对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/30 0030 下午 1:47
 **/
@ApiModel
@Setter
@Getter
public class AuthVo extends Auth {

    /**
     * 子权限
     *
     * @since 下午 1:47 2019/10/30 0030
     **/
    @ApiModelProperty(value = "子权限")
    private List<AuthVo> children;

    /**
     * 权限名称
     *
     * @since 下午 3:44 2019/11/7 0007
     **/
    @ApiModelProperty(value = "权限名称")
    private String title;

    /**
     * 页面路径
     */
    @ApiModelProperty("页面路径")
    private String href;

    @Override
    public Auth setName(String name) {
        super.setName(name);
        this.title = name;
        return this;
    }

    @Override
    public Auth setPageUrl(String pageUrl) {
        super.setPageUrl(pageUrl);
        this.href = pageUrl;
        return this;
    }
}

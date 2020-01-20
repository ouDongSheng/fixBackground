package com.logicalthining.endeshop.common.responseVo.user;

import com.logicalthining.endeshop.entity.ClientAddress;
import com.logicalthining.endeshop.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 后台用户详情返回对象
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/9 0009 下午 1:38
 **/
@ApiModel
@Setter
@Getter
public class UserInfoVo extends User {

    /**
     * 用户收货地址集合
     * @since 下午 1:40 2019/11/9 0009
     **/
    @ApiModelProperty(value = "用户收货地址集合")
    private List<ClientAddress> addressList;

}

package com.logicalthining.endeshop.common.responseVo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 用户二维码对象
 *
 * 现在只有id一个字段,后期如果有需求可扩展
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/13 0013 下午 2:22
 **/
@ApiModel
@Setter
@Getter
@Accessors(chain = true)
public class UserQrCodeVo {

    /**
     * 用户id
     * @since 下午 2:24 2019/11/13 0013
     **/
    @ApiModelProperty(value = "用户id")
    private Integer userId;


}

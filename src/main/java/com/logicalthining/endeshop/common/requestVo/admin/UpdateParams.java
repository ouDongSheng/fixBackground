package com.logicalthining.endeshop.common.requestVo.admin;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编辑后台用户请求参数
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/30 0030 上午 10:16
 **/
@ApiModel
@Setter
@Getter
public class UpdateParams extends AddParams{

    /**
     * 账号名称
     */
    @ApiModelProperty("id")
    @PropertyCheck(name = "id")
    private Integer id;



}

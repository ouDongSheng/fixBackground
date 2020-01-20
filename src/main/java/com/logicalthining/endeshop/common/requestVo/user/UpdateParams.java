package com.logicalthining.endeshop.common.requestVo.user;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户编辑参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/31 0031 下午 7:15
 **/
@ApiModel
@Setter
@Getter
public class UpdateParams extends AddParams {


    /**
     * id
     */
    @ApiModelProperty("id")
    @PropertyCheck(name = "id")
    private Integer id;

}

package com.logicalthining.endeshop.common.requestVo.role;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.List;

/**
 * 角色编辑参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/30 0030 下午 2:40
 **/
@ApiModel
@Setter
@Getter
public class UpdateParams extends AddParams{


    /**
     * 角色名称
     */
    @ApiModelProperty("id")
    @PropertyCheck(name = "id")
    private Integer id;



}

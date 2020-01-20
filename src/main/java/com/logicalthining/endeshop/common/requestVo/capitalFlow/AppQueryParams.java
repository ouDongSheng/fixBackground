package com.logicalthining.endeshop.common.requestVo.capitalFlow;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.logicalthining.endeshop.common.requestVo.PageAbleVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 客户端用户流水查询请求参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/14 0014 上午 9:27
 **/
@ApiModel
@Setter
@Getter
public class AppQueryParams extends PageAbleVo {

    /**
     * 流水类别  1 收入 2支出
     *
     * @since 上午 9:28 2019/11/14 0014
     **/
    @ApiModelProperty(value = "流水类别  1 收入 2支出")
    @PropertyCheck(name = "流水类别")
    private Integer type;


    /**
     * 用户Id
     *
     * @since 上午 9:29 2019/11/14 0014
     **/
    @ApiModelProperty(value = "用户id,此参数前端不需要传")
    private Integer userId;

}

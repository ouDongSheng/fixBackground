package com.logicalthining.endeshop.common.requestVo.partnerApply;

import com.logicalthining.endeshop.common.requestVo.PageTimeLimitQueryVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 合伙人申请查询参数
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/6 0006 上午 9:38
 **/
@ApiModel
@Setter
@Getter
public class QueryParams extends PageTimeLimitQueryVo {

    /**
     * 账号
     * @since 上午 9:40 2019/11/6 0006
     **/
    @ApiModelProperty(value = "账号")
    private String account;

    /**
     * 状态 0初始状态 1审核通过 2审核不通过
     * @since 上午 9:40 2019/11/6 0006
     **/
    @ApiModelProperty(value = "状态 0初始状态 1审核通过 2审核不通过")
    private Integer state;


}

package com.logicalthining.endeshop.common.requestVo.userScore;

import com.logicalthining.endeshop.common.requestVo.PageAbleVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 积分池详细查询参数
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/11 0011 下午 5:19
 **/
@ApiModel
@Setter
@Getter
public class UserPoolInfoQueryParams extends PageAbleVo {
    
    /**
     * 父用户id
     * @since 下午 5:20 2019/11/11 0011
     **/
    @ApiModelProperty(value = "上级用户id")
    private Integer parentUserId;
    
    /**
     * 搜索条件
     * 合伙人手机号
     * @since 下午 5:20 2019/11/11 0011
     **/
    @ApiModelProperty(value = "搜索条件")
    private String searchKey;
    
    /**
     * 季度
     * @since 下午 5:20 2019/11/11 0011
     **/
    @ApiModelProperty(value = "季度")
    private String quarter;
    
}

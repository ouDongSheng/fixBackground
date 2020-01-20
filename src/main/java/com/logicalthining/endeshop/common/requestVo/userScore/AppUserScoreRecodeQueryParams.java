package com.logicalthining.endeshop.common.requestVo.userScore;

import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.logicalthining.endeshop.common.requestVo.PageAbleVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 客户端用户积分记录查询参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/12 0012 上午 11:53
 **/
@ApiModel
@Setter
@Getter
public class AppUserScoreRecodeQueryParams extends PageAbleVo {

    /**
     * 积分获取类型
     *
     * @since 上午 10:32 2019/11/8 0008
     **/
    @ApiModelProperty(value = "积分来源类型 1间接奖获取积分 2复购奖获取积分 3使用积分 4积分池结算")
    @PropertyCheck(name = "积分来源类型")
    private Integer scoreType;

    /**
     * 当前用户id
     * @since 下午 2:06 2019/11/20 0020
     **/
    private Integer currentUserId;
}

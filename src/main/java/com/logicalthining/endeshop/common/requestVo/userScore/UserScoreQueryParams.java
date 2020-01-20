package com.logicalthining.endeshop.common.requestVo.userScore;

import com.logicalthining.endeshop.common.requestVo.PageTimeLimitQueryVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 后台用户积分获取记录查询参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/8 0008 上午 10:31
 **/
@ApiModel
@Setter
@Getter
public class UserScoreQueryParams extends PageTimeLimitQueryVo {

    /**
     * 搜索条件 手机号、订单编号
     *
     * @since 上午 10:32 2019/11/8 0008
     **/
    @ApiModelProperty(value = "searchKey")
    private String searchKey;

    /**
     * 积分获取类型
     *
     * @since 上午 10:32 2019/11/8 0008
     **/
    @ApiModelProperty(value = "积分来源类型 1间接奖获取积分 2复购奖获取积分 3使用积分 4积分池结算")
    private Integer scoreType;

}

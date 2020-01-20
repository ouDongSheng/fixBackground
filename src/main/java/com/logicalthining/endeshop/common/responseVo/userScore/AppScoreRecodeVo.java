package com.logicalthining.endeshop.common.responseVo.userScore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 客户端积分记录返回对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/20 0020 下午 1:57
 **/
@Setter
@Getter
@ApiModel
public class AppScoreRecodeVo {

    /**
     * 合伙人账号
     *
     * @since 上午 10:53 2019/11/7 0007
     **/
    @ApiModelProperty(value = "合伙人账号")
    private String parentUserAccount;

    /**
     * 合伙人姓名
     *
     * @since 上午 10:53 2019/11/7 0007
     **/
    @ApiModelProperty(value = "合伙人姓名")
    private String parentUserAccountName;

    /**
     * 积分 表示合伙人在这个订单中的获得到的积分
     *
     * @since 上午 10:53 2019/11/7 0007
     **/
    @ApiModelProperty(value = "获得积分")
    private Double score;

    /**
     * 时间
     *
     * @since 上午 10:53 2019/11/7 0007
     **/
    @ApiModelProperty(value = "时间")
    private String createTime;

}

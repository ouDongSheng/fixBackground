package com.logicalthining.endeshop.common.requestVo.userScore;

import com.logicalthining.endeshop.common.requestVo.PageAbleVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 积分池查询参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/9 0009 下午 4:28
 **/
@ApiModel
@Setter
@Getter
public class ScorePoolQueryParams extends PageAbleVo {

    /**
     * 搜索条件 合伙人手机号
     *
     * @since 下午 4:30 2019/11/9 0009
     **/
    @ApiModelProperty(value = "搜索条件 合伙人手机号")
    private String searchKey;

    /**
     * 季度
     * 格式 2019-1
     *
     * @since 下午 4:30 2019/11/9 0009
     **/
    @ApiModelProperty(value = "季度 格式 2019-1表示2019年第一季度")
    private String quarter;

    /**
     * 可获积分比例
     * 如果传了这个参数,就从系统设置里面查询出来关于积分比例的设置,
     * 获取到这个积分比例的两个边界,然后去数据库里查询即可
     *
     * @since 下午 4:30 2019/11/9 0009
     **/
    @ApiModelProperty(value = "可获积分比例")
    private Double ratio;

}

package com.logicalthining.endeshop.common.requestVo.userScore;

import com.github.chenlijia1111.utils.core.StringUtils;
import com.logicalthining.endeshop.common.requestVo.PageAbleVo;
import com.logicalthining.endeshop.util.QuarterTimeTransferUtil;
import com.logicalthining.endeshop.util.QuarterTimeTransferUtil.QuarterTimeInterval;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

/**
 * 用户积分记录查询参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/12 0012 上午 11:53
 **/
@ApiModel
@Setter
@Getter
public class UserScoreRecodeQueryParams extends PageAbleVo {

    /**
     * 用户id
     *
     * @since 上午 11:54 2019/11/12 0012
     **/
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 季度 2019-1
     *
     * @since 上午 11:54 2019/11/12 0012
     **/
    @ApiModelProperty(value = "季度 如2019-1")
    private String quarter;

    /**
     * 开始时间
     *
     * @since 下午 12:07 2019/11/12 0012
     **/
    private Date startTime;

    /**
     * 结束时间
     *
     * @since 下午 12:07 2019/11/12 0012
     **/
    private Date endTime;


    public void setQuarter(String quarter) {
        this.quarter = quarter;
        if (StringUtils.isNotEmpty(quarter)) {
            QuarterTimeInterval quarterTimeInterval = QuarterTimeTransferUtil.transferQuarterToQuarterTimeInterval(quarter);
            if (Objects.nonNull(quarterTimeInterval)) {
                this.startTime = quarterTimeInterval.getStartTime();
                this.endTime = quarterTimeInterval.getEndTime();
            }
        }
    }
}

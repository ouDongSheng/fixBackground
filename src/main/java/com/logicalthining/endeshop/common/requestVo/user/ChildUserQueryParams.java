package com.logicalthining.endeshop.common.requestVo.user;

import com.logicalthining.endeshop.common.requestVo.PageTimeLimitQueryVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import java.util.Objects;

/**
 * 客户端下级用户查询列表
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/14 0014 下午 2:38
 **/
@ApiModel
@Setter
@Getter
public class ChildUserQueryParams extends PageTimeLimitQueryVo {

    /**
     * 当前用户id
     *
     * @since 下午 3:07 2019/11/14 0014
     **/
    @ApiModelProperty(value = "当前用户id")
    private Integer parentUserId;

    /**
     * 下级用户角色类型
     *
     * @since 下午 2:39 2019/11/14 0014
     **/
    @ApiModelProperty(value = "下级用户角色类型 1 普通用户 2会员 3合伙人")
    private Integer childRole;

    /**
     * 是否今日新增 0否 1是
     *
     * @since 下午 2:39 2019/11/14 0014
     **/
    @ApiModelProperty(value = "是否今日新增 0否 1是")
    private Integer nowadaysAdd;


    public void setNowadaysAdd(Integer nowadaysAdd) {
        this.nowadaysAdd = nowadaysAdd;
        if (Objects.nonNull(nowadaysAdd) && Objects.equals(1, nowadaysAdd)) {
            DateTime now = DateTime.now();
            this.setStartTime(now.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDate());
            this.setEndTime(now.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate());
        }
    }
}

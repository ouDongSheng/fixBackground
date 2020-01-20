package com.logicalthining.endeshop.common.responseVo.userRelation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * 用户下级数量对象
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/13 0013 下午 5:14
 **/
@ApiModel
@Setter
@Getter
public class UserChildCountVo {

    /**
     * 用户id
     *
     * @since 下午 5:15 2019/11/13 0013
     **/
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 下级普通用户数量
     * 这里只需要显示用户的直接下级即可
     *
     * @since 下午 3:13 2019/11/13 0013
     **/
    @ApiModelProperty(value = "下级普通用户数量")
    private Integer teamCommonUserCount = 0;

    /**
     * 下级会员用户数量
     * 这里只需要显示用户的直接下级即可
     *
     * @since 下午 3:13 2019/11/13 0013
     **/
    @ApiModelProperty(value = "下级会员用户数量")
    private Integer teamVIPUserCount = 0;

    /**
     * 下级合伙人用户数量
     * 这里只需要显示用户的直接下级即可
     *
     * @since 下午 3:14 2019/11/13 0013
     **/
    @ApiModelProperty(value = "下级合伙人用户数量")
    private Integer teamPartnerUserCount = 0;

    public void setTeamCommonUserCount(Integer teamCommonUserCount) {
        if (Objects.isNull(teamCommonUserCount) || teamCommonUserCount < 0) {
            teamCommonUserCount = 0;
        }
        this.teamCommonUserCount = teamCommonUserCount;
    }

    public void setTeamVIPUserCount(Integer teamVIPUserCount) {
        if (Objects.isNull(teamVIPUserCount) || teamVIPUserCount < 0) {
            teamVIPUserCount = 0;
        }
        this.teamVIPUserCount = teamVIPUserCount;
    }

    public void setTeamPartnerUserCount(Integer teamPartnerUserCount) {
        if (Objects.isNull(teamPartnerUserCount) || teamPartnerUserCount < 0) {
            teamPartnerUserCount = 0;
        }
        this.teamPartnerUserCount = teamPartnerUserCount;
    }

    public Integer getTeamCommonUserCount() {
        return teamCommonUserCount == null ? 0 : teamCommonUserCount;
    }

    public Integer getTeamVIPUserCount() {
        return teamVIPUserCount == null ? 0 : teamVIPUserCount;
    }

    public Integer getTeamPartnerUserCount() {
        return teamPartnerUserCount == null ? 0 : teamPartnerUserCount;
    }
}

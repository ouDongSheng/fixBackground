package com.logicalthining.endeshop.common.responseVo.user;

import com.logicalthining.endeshop.common.enums.UserRole;
import com.logicalthining.endeshop.common.responseVo.userScore.AwardPoolTeamCount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

/**
 * 客户端账户详情
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/13 0013 下午 3:08
 **/
@ApiModel
@Setter
@Getter
public class AppAccountInfoVo {

    /**
     * 用户id
     *
     * @since 下午 3:12 2019/11/13 0013
     **/
    @ApiModelProperty(value = "用户id")
    private Integer id;

    /**
     * 用户账户
     *
     * @since 下午 3:12 2019/11/13 0013
     **/
    @ApiModelProperty(value = "用户账户")
    private String account;

    /**
     * 用户名称
     *
     * @since 下午 3:12 2019/11/13 0013
     **/
    @ApiModelProperty(value = "用户名称")
    private String name;

    /**
     * 头像
     *
     * @since 下午 3:36 2019/11/13 0013
     **/
    @ApiModelProperty(value = "头像")
    private String headImage;

    /**
     * 用户角色
     *
     * @since 下午 3:12 2019/11/13 0013
     **/
    @ApiModelProperty(value = "用户角色")
    private Integer role;

    /**
     * 用户角色名称
     *
     * @since 下午 3:12 2019/11/13 0013
     **/
    @ApiModelProperty(value = "用户角色名称")
    private String roleName;

    /**
     * 累计收益
     *
     * @since 下午 3:12 2019/11/13 0013
     **/
    @ApiModelProperty(value = "累计收益")
    private Double grandTotalMoney;

    /**
     * 可提现金额
     *
     * @since 下午 3:12 2019/11/13 0013
     **/
    @ApiModelProperty(value = "可提现金额")
    private Double canWithdrawMoney;

    /**
     * 我的积分
     *
     * @since 下午 3:13 2019/11/13 0013
     **/
    @ApiModelProperty(value = "我的积分")
    private Double score;

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

    /**
     * 当前季度业绩
     *
     * @since 下午 3:33 2019/11/14 0014
     **/
    @ApiModelProperty(value = "当前季度业绩")
    private Double currentQuarterPerformance;

    /**
     * 业绩提示语  如：恭喜您可参与一等积分池
     * 根据当前业绩来进行计算得出
     *
     * @since 下午 3:35 2019/11/14 0014
     **/
    @ApiModelProperty(value = "业绩提示语")
    private String performanceRemarks;

    /**
     * 奖金池层级对应的团队数量
     * @since 下午 7:58 2019/11/18 0018
     **/
    @ApiModelProperty(value = "奖金池层级对应的团队数量")
    private List<AwardPoolTeamCount> awardPoolTeamCountList;

    public void setRole(Integer role) {
        this.role = role;
        if (Objects.nonNull(role)) {
            String roleName = UserRole.transferRoleName(role);
            this.roleName = roleName;
        }
    }
}

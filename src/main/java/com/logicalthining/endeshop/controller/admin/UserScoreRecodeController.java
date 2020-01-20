package com.logicalthining.endeshop.controller.admin;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.UserScoreRecodeBiz;
import com.logicalthining.endeshop.common.pojo.systemConfig.AwardPoolConfig;
import com.logicalthining.endeshop.common.requestVo.userScore.*;
import com.logicalthining.endeshop.common.responseVo.userScore.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户积分记录
 * 合伙人下属要有≥2个合伙人才能资格参加积分池
 *
 * @author chenLiJia
 * @since 2019-11-07 13:40:17
 **/
@RestController
@RequestMapping(value = "admin/user/score")
@Api(tags = "用户积分记录")
public class UserScoreRecodeController {


    @Autowired
    private UserScoreRecodeBiz biz;//用户积分记录

    /**
     * 用户积分获取记录列表查询
     * 查询间接奖与复购奖接口
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:34 2019/11/8 0008
     **/
    @GetMapping(value = "listPage")
    @ApiOperation(value = "查询用户积分获取记录(查询间接奖与复购奖接口)", response = AdminUserScoreListVo.class)
    public Result listPage(UserScoreQueryParams params) {
        return biz.adminListPage(params);
    }

    /**
     * 设置间接奖和复购奖获得积分百分比
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:17 2019/11/8 0008
     **/
    @PostMapping(value = "indirectAndRepeatAward/config")
    @ApiOperation(value = "设置间接奖和复购奖获得积分百分比")
    public Result configIndirectAndRepeatAward(IndirectAndRepeatAwardConfigParams params) {
        return biz.configIndirectAndRepeatAward(params);
    }


    /**
     * 重置间接奖和复购奖得积分百分比
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:17 2019/11/8 0008
     **/
    @PostMapping(value = "indirectAndRepeatAward/reset")
    @ApiOperation(value = "重置间接奖和复购奖得积分百分比")
    public Result resetIndirectAndRepeatAward() {
        return biz.resetIndirectAndRepeatAward();
    }


    /**
     * 设置奖金池
     *
     * @param list 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:18 2019/11/8 0008
     **/
    @PostMapping(value = "awardPool/config")
    @ApiOperation(value = "设置奖金池")
    public Result configAwardPool(@RequestBody List<AwardPoolConfig> list) {
        return biz.configAwardPool(list);
    }

    /**
     * 重置奖金池
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:18 2019/11/8 0008
     **/
    @PostMapping(value = "awardPool/config/reset")
    @ApiOperation(value = "重置奖金池")
    public Result resetAwardPool() {
        return biz.resetAwardPool();
    }


    /**
     * 查询积分比例设置
     * 奖金池设置
     * 间接奖以及分享奖设置
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:19 2019/11/8 0008
     **/
    @GetMapping(value = "findAwardConfig")
    @ApiOperation(value = "查询积分比例设置", response = ScoreRatioConfigVo.class)
    public Result findAwardConfig() {
        return biz.findAwardConfig();
    }


    /**
     * 列表查询积分池
     * 积分池需要满足有至少两个直接下级是合伙人
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 4:32 2019/11/9 0009
     **/
    @GetMapping(value = "listPageScorePool")
    @ApiOperation(value = "列表查询积分池", response = ScorePoolVo.class)
    public Result listPageScorePool(ScorePoolQueryParams params) {
        return biz.listPageScorePool(params);
    }


    /**
     * 查询新晋级合伙人
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:28 2019/11/11 0011
     **/
    @GetMapping(value = "listNewPartnerUser")
    @ApiOperation(value = "查询新晋级合伙人", response = NewPartnerUserInfo.class)
    public Result listNewPartnerUser(UserPoolInfoQueryParams params) {
        return biz.listNewPartnerUser(params);
    }

    /**
     * 列出团队积分详情
     * 因为这里需要展示积分比例，所以也需要直接下级有两个以上的合伙人
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:32 2019/11/11 0011
     **/
    @GetMapping(value = "listTeamScoreInfo")
    @ApiOperation(value = "列出团队积分详情", response = ScorePoolInfoVo.class)
    public Result listTeamScoreInfo(UserPoolInfoQueryParams params) {
        return biz.listTeamScoreInfo(params);
    }

    /**
     * 查询用户积分记录
     * 积分池-详情-团队积分详情-历史收益记录
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:55 2019/11/12 0012
     **/
    @GetMapping(value = "listUserScoreRecodeVo")
    @ApiOperation(value = "查询用户积分记录", notes = "积分池-详情-团队积分详情-历史收益记录", response = UserScoreRecodeVo.class)
    public Result listUserScoreRecodeVo(UserScoreRecodeQueryParams params) {
        return biz.listUserScoreRecodeVo(params);
    }

}

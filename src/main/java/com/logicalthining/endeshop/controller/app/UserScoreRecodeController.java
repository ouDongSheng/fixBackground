package com.logicalthining.endeshop.controller.app;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.UserScoreRecodeBiz;
import com.logicalthining.endeshop.common.requestVo.userScore.AppUserScoreRecodeQueryParams;
import com.logicalthining.endeshop.common.responseVo.userScore.AppScoreRecodeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户积分记录
 * 合伙人下属要有≥2个合伙人才能资格参加积分池
 *
 * @author chenLiJia
 * @since 2019-11-07 13:40:17
 **/
@RestController("appUserScoreRecodeController")
@RequestMapping(value = "app/user/score")
@Api(tags = "用户积分记录")
public class UserScoreRecodeController {


    @Autowired
    private UserScoreRecodeBiz biz;//用户积分记录

    /**
     * 客户端查询用户积分获取记录列表
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:34 2019/11/8 0008
     **/
    @GetMapping(value = "listPage")
    @ApiOperation(value = "客户端查询用户积分获取记录列表", response = AppScoreRecodeVo.class)
    public Result listPage(AppUserScoreRecodeQueryParams params) {
        return biz.appListPage(params);
    }


}

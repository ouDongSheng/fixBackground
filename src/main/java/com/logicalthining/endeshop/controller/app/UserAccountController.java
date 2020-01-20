package com.logicalthining.endeshop.controller.app;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.UserAccountBiz;
import com.logicalthining.endeshop.common.requestVo.capitalFlow.AppQueryParams;
import com.logicalthining.endeshop.common.requestVo.user.AppWithdrawParams;
import com.logicalthining.endeshop.common.requestVo.user.EditWithdrawPasswordParams;
import com.logicalthining.endeshop.common.responseVo.capitalFlow.AppUserCapitalVo;
import com.logicalthining.endeshop.common.responseVo.user.AppAccountInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户账户控制器
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/13 0013 下午 3:29
 **/
@RestController
@RequestMapping(value = "app/user/account")
@Api(tags = "用户账户")
public class UserAccountController {

    @Autowired
    private UserAccountBiz biz;//用户账户


    /**
     * 查询当前用户信息以及账户信息
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 3:06 2019/11/13 0013
     **/
    @GetMapping(value = "findCurrentUserAccountInfo")
    @ApiOperation(value = "查询当前用户信息以及账户信息",response = AppAccountInfoVo.class)
    public Result findCurrentUserAccountInfo() {
        return biz.findCurrentUserAccountInfo();
    }


    /**
     * 查询当前用户流水
     *
     * @param params
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 6:09 2019/11/13 0013
     **/
    @GetMapping(value = "listCapitalFlow")
    @ApiOperation(value = "查询当前用户流水",response = AppUserCapitalVo.class)
    public Result listCapitalFlow(AppQueryParams params) {
        return biz.listCapitalFlow(params);
    }


    /**
     * 设置提现密码
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:38 2019/11/14 0014
     **/
    @PostMapping(value = "withdraw/password/edit")
    @ApiOperation(value = "设置提现密码")
    public Result editWithdrawPassword(EditWithdrawPasswordParams params) {
        return biz.editWithdrawPassword(params);
    }

    /**
     * 校验用户是否设置了提现密码
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:00 2019/11/14 0014
     **/
    @PostMapping(value = "withdraw/password/check")
    @ApiOperation(value = "校验用户是否设置了提现密码")
    public Result checkWithdrawPassword() {
        return biz.checkWithdrawPassword();
    }

    /**
     * 用户提现申请
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:02 2019/11/14 0014
     **/
    @PostMapping(value = "withdraw")
    @ApiOperation(value = "用户提现申请")
    public Result withdraw(AppWithdrawParams params) {
        return biz.withdraw(params);
    }


}

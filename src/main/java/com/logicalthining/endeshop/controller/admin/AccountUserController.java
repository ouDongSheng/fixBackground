package com.logicalthining.endeshop.controller.admin;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.UserAccountBiz;
import com.logicalthining.endeshop.common.requestVo.user.AdminWithdrawRecodeQueryParams;
import com.logicalthining.endeshop.common.responseVo.withdraw.AdminWithdrawListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户账户
 *
 * @author chenLiJia
 * @since 2019-11-07 09:38:10
 **/
@RestController
@RequestMapping(value = "admin/user/account")
@Api(tags = "用户账户")
public class AccountUserController {


    @Autowired
    private UserAccountBiz biz;//用户账户


    /**
     * 后台查询用户提现记录
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:32 2019/11/14 0014
     **/
    @GetMapping(value = "listWithdrawRecode")
    @ApiOperation(value = "后台查询用户提现记录",response = AdminWithdrawListVo.class)
    public Result listWithdrawRecode(AdminWithdrawRecodeQueryParams params) {
        return biz.adminListWithdrawRecode(params);
    }


    /**
     * 提现成功
     * @since 上午 10:36 2019/11/14 0014
     * @param id 提现记录id
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    @PostMapping(value = "withdraw/success")
    @ApiOperation(value = "提现成功")
    public Result successWithdraw(Integer id){
        return biz.successWithdraw(id);
    }

    /**
     * 提现失败
     * @since 上午 10:36 2019/11/14 0014
     * @param id 提现记录id
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    @PostMapping(value = "withdraw/failure")
    @ApiOperation(value = "提现失败")
    public Result failureWithdraw(Integer id){
        return biz.failureWithdraw(id);
    }

}

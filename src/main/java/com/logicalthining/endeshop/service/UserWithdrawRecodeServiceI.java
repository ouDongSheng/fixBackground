package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.common.requestVo.user.AdminWithdrawRecodeQueryParams;
import com.logicalthining.endeshop.common.responseVo.withdraw.AdminWithdrawListVo;
import com.logicalthining.endeshop.entity.UserWithdrawRecode;

import java.util.List;

/**
 * 用户提现记录
 *
 * @author chenLiJia
 * @since 2019-11-13 20:12:48
 **/
public interface UserWithdrawRecodeServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-13 20:12:48
     **/
    Result add(UserWithdrawRecode params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-13 20:12:48
     **/
    Result update(UserWithdrawRecode params);

    /**
     * 后台查询用户提现记录
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.withdraw.AdminWithdrawListVo>
     * @since 上午 10:41 2019/11/14 0014
     **/
    List<AdminWithdrawListVo> listAdminWithdrawListVo(AdminWithdrawRecodeQueryParams params);


    /**
     * 主键查询
     * @since 上午 10:41 2019/11/14 0014
     * @param id 1
     * @return com.logicalthining.endeshop.entity.UserWithdrawRecode
     **/
    UserWithdrawRecode findById(Integer id);
}

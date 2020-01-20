package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.entity.UserWithdrawPassword;

/**
 * 用户提现密码
 *
 * @author chenLiJia
 * @since 2019-11-13 20:12:48
 **/
public interface UserWithdrawPasswordServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-13 20:12:48
     **/
    Result add(UserWithdrawPassword params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-13 20:12:48
     **/
    Result update(UserWithdrawPassword params);

    /**
     * 查找用户提现密码
     *
     * @param userId 用户id
     * @return com.logicalthining.endeshop.entity.UserWithdrawPassword
     * @since 上午 9:42 2019/11/14 0014
     **/
    UserWithdrawPassword findByUserId(Integer userId);
}

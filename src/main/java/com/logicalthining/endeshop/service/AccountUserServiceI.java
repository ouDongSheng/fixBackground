package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.entity.AccountUser;

/**
 * 用户账户
 * @author chenLiJia
 * @since 2019-11-07 09:38:11
 **/
public interface AccountUserServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-07 09:38:11
     **/
    Result add(AccountUser params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-07 09:38:11
     **/
    Result update(AccountUser params);

    /**
     * 通过用户id查询用户账户信息
     * @since 上午 10:12 2019/11/7 0007
     * @param userId 1
     * @return com.logicalthining.endeshop.entity.AccountUser
     **/
    AccountUser findByUserId(Integer userId);
}

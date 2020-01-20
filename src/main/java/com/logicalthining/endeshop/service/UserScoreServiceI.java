package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.entity.UserScore;

/**
 * 用户积分
 * @author chenLiJia
 * @since 2019-11-12 09:30:20
 **/
public interface UserScoreServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-12 09:30:20
     **/
    Result add(UserScore params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-12 09:30:20
     **/
    Result update(UserScore params);

    /**
     * 查找用户积分
     * @since 上午 9:34 2019/11/12 0012
     * @param userId 用户id
     * @return com.logicalthining.endeshop.entity.UserScore
     **/
    UserScore findByUserId(Integer userId);


}

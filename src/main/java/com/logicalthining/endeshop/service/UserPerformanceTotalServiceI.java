package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.entity.UserPerformanceTotal;

/**
 * 用户累计业绩
 * @author chenLiJia
 * @since 2019-11-12 10:20:06
 **/
public interface UserPerformanceTotalServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-12 10:20:06
     **/
    Result add(UserPerformanceTotal params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-12 10:20:06
     **/
    Result update(UserPerformanceTotal params);

    /**
     * 根据用户id查询用户累计业绩
     * @since 上午 10:21 2019/11/12 0012
     * @param userId 1
     * @return com.logicalthining.endeshop.entity.UserPerformanceTotal
     **/
    UserPerformanceTotal findByUserId(Integer userId);


}

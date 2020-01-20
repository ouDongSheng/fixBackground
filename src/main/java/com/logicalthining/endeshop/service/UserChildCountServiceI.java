package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.entity.UserChildCount;

/**
 * 用户下级数量
 * @author chenLiJia
 * @since 2019-11-09 15:23:29
 **/
public interface UserChildCountServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-09 15:23:29
     **/
    Result add(UserChildCount params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-09 15:23:29
     **/
    Result update(UserChildCount params);

    /**
     * 查询用户的下级数量
     * @since 下午 4:52 2019/11/13 0013
     * @param userId 1
     * @return com.logicalthining.endeshop.entity.UserChildCountVo
     **/
    UserChildCount findByUserId(Integer userId);


}

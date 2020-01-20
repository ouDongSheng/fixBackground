package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.entity.UserPerformanceHistory;

import java.util.List;

/**
 * 用户业绩记录
 * @author chenLiJia
 * @since 2019-12-12 14:40:38
 **/
public interface UserPerformanceHistoryServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-12-12 14:40:38
     **/
    Result add(UserPerformanceHistory params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-12-12 14:40:38
     **/
    Result update(UserPerformanceHistory params);

    /**
     * 条件查询
     * @since 下午 2:49 2019/12/12 0012
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.UserPerformanceHistory>
     **/
    List<UserPerformanceHistory> listByCondition(UserPerformanceHistory condition);

    /**
     * 根据主键删除
     * @since 下午 2:49 2019/12/12 0012
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    Result deleteById(Integer id);


}

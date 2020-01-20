package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.entity.UserPerformanceTotal;
import tk.mybatis.mapper.common.Mapper;

/**
 * 用户累计业绩
 * @author chenLiJia
 * @since 2019-11-12 10:19:53
 * @version 1.0
 **/
public interface UserPerformanceTotalMapper extends Mapper<UserPerformanceTotal> {
    UserPerformanceTotal selectByPrimaryKey(Integer userId);
}
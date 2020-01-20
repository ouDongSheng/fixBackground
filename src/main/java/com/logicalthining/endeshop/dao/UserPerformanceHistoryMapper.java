package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.entity.UserPerformanceHistory;
import tk.mybatis.mapper.common.Mapper;

/**
 * 用户业绩记录
 * @author chenLiJia
 * @since 2019-12-12 14:40:24
 * @version 1.0
 **/
public interface UserPerformanceHistoryMapper extends Mapper<UserPerformanceHistory> {
    UserPerformanceHistory selectByPrimaryKey(Integer id);
}
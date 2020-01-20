package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.entity.SystemCount;
import tk.mybatis.mapper.common.Mapper;

/**
 * 系统数据统计
 * @author chenLiJia
 * @since 2019-11-12 14:49:19
 * @version 1.0
 **/
public interface SystemCountMapper extends Mapper<SystemCount> {
    SystemCount selectByPrimaryKey(String countKey);
}
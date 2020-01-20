package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.entity.SystemConfig;
import tk.mybatis.mapper.common.Mapper;

/**
 * 系统配置数据
 * @author chenLiJia
 * @since 2019-11-06 10:30:41
 * @version 1.0
 **/
public interface SystemConfigMapper extends Mapper<SystemConfig> {
    SystemConfig selectByPrimaryKey(String configKey);
}
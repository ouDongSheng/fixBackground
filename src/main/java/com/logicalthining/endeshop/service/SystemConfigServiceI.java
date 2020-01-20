package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.entity.SystemConfig;

/**
 * 系统配置数据
 * @author chenLiJia
 * @since 2019-11-06 10:30:53
 **/
public interface SystemConfigServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 10:30:53
     **/
    Result add(SystemConfig params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 10:30:53
     **/
    Result update(SystemConfig params);

    /**
     * 查找系统配置信息
     * @since 上午 10:33 2019/11/6 0006
     * @param configKey 1
     * @return com.logicalthining.endeshop.entity.SystemConfig
     **/
    SystemConfig selectByConfigKey(String configKey);


}

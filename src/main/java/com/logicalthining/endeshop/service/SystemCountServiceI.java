package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.entity.SystemCount;

import java.util.List;

/**
 * 系统数据统计
 * @author chenLiJia
 * @since 2019-11-12 14:49:35
 **/
public interface SystemCountServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-12 14:49:35
     **/
    Result add(SystemCount params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-12 14:49:35
     **/
    Result update(SystemCount params);

    /**
     * 通过key查询统计数据
     * @since 下午 2:55 2019/11/12 0012
     * @param key 1
     * @return com.logicalthining.endeshop.entity.SystemCount
     **/
    SystemCount findByCountKey(String key);

    /**
     * 查询所有统计数据
     * @since 下午 3:26 2019/11/12 0012
     * @return java.util.List<com.logicalthining.endeshop.entity.SystemCount>
     **/
    List<SystemCount> listAll();
}

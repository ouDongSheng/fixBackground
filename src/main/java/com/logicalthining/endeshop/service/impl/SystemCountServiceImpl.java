package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.logicalthining.endeshop.dao.SystemCountMapper;
import com.logicalthining.endeshop.entity.SystemCount;
import com.logicalthining.endeshop.service.SystemCountServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 系统数据统计
 *
 * @author chenLiJia
 * @since 2019-11-12 14:49:35
 **/
@Service
public class SystemCountServiceImpl implements SystemCountServiceI {


    @Resource
    private SystemCountMapper systemCountMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-12 14:49:35
     **/
    public Result add(SystemCount params) {

        int i = systemCountMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-12 14:49:35
     **/
    public Result update(SystemCount params) {

        int i = systemCountMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 通过key查询统计数据
     *
     * @param key 1
     * @return com.logicalthining.endeshop.entity.SystemCount
     * @since 下午 2:55 2019/11/12 0012
     **/
    @Override
    public SystemCount findByCountKey(String key) {
        if (StringUtils.isNotEmpty(key)) {
            SystemCount systemCount = systemCountMapper.selectByPrimaryKey(key);
            if (Objects.isNull(systemCount)) {
                systemCount = new SystemCount();
                systemCount.setCountKey(key);
                systemCount.setCountValue(0.0);
                systemCount.setUpdateTime(new Date());
                systemCountMapper.insertSelective(systemCount);
            }
            return systemCount;
        }
        return null;
    }


    /**
     * 查询所有统计数据
     *
     * @return java.util.List<com.logicalthining.endeshop.entity.SystemCount>
     * @since 下午 3:26 2019/11/12 0012
     **/
    @Override
    public List<SystemCount> listAll() {
        return systemCountMapper.selectAll();
    }


}

package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.dao.UserPerformanceTotalMapper;
import com.logicalthining.endeshop.entity.UserPerformanceTotal;
import com.logicalthining.endeshop.service.UserPerformanceTotalServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * 用户累计业绩
 *
 * @author chenLiJia
 * @since 2019-11-12 10:20:06
 **/
@Service
public class UserPerformanceTotalServiceImpl implements UserPerformanceTotalServiceI {


    @Resource
    private UserPerformanceTotalMapper userPerformanceTotalMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-12 10:20:06
     **/
    public Result add(UserPerformanceTotal params) {

        int i = userPerformanceTotalMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-12 10:20:06
     **/
    public Result update(UserPerformanceTotal params) {

        int i = userPerformanceTotalMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 根据用户id查询用户累计业绩
     *
     * @param userId 1
     * @return com.logicalthining.endeshop.entity.UserPerformanceTotal
     * @since 上午 10:21 2019/11/12 0012
     **/
    @Override
    public UserPerformanceTotal findByUserId(Integer userId) {
        if (Objects.nonNull(userId)) {
            UserPerformanceTotal userPerformanceTotal = userPerformanceTotalMapper.selectByPrimaryKey(userId);
            if (Objects.isNull(userPerformanceTotal)) {
                userPerformanceTotal = new UserPerformanceTotal();
                userPerformanceTotal.setUserId(userId);
                userPerformanceTotal.setGrandTotalPerformance(0.0);
                userPerformanceTotal.setUpdateTime(new Date());
                userPerformanceTotalMapper.insertSelective(userPerformanceTotal);
            }
            return userPerformanceTotal;
        }
        return null;
    }


}

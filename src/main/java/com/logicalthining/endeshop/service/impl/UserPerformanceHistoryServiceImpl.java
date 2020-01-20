package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.logicalthining.endeshop.dao.UserPerformanceHistoryMapper;
import com.logicalthining.endeshop.entity.UserPerformanceHistory;
import com.logicalthining.endeshop.service.UserPerformanceHistoryServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 用户业绩记录
 *
 * @author chenLiJia
 * @since 2019-12-12 14:40:38
 **/
@Service
public class UserPerformanceHistoryServiceImpl implements UserPerformanceHistoryServiceI {


    @Resource
    private UserPerformanceHistoryMapper userPerformanceHistoryMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-12-12 14:40:38
     **/
    public Result add(UserPerformanceHistory params) {

        int i = userPerformanceHistoryMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-12-12 14:40:38
     **/
    public Result update(UserPerformanceHistory params) {

        int i = userPerformanceHistoryMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 条件查询
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.UserPerformanceHistory>
     * @since 下午 2:49 2019/12/12 0012
     **/
    @Override
    public List<UserPerformanceHistory> listByCondition(UserPerformanceHistory condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition);
        return userPerformanceHistoryMapper.select(condition);
    }

    /**
     * 根据主键删除
     *
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:49 2019/12/12 0012
     **/
    @Override
    public Result deleteById(Integer id) {
        if (Objects.nonNull(id)) {
            userPerformanceHistoryMapper.deleteByPrimaryKey(id);
        }
        return Result.success("操作成功");
    }


}

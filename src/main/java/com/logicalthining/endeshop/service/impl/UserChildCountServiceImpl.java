package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.dao.UserChildCountMapper;
import com.logicalthining.endeshop.entity.UserChildCount;
import com.logicalthining.endeshop.service.UserChildCountServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 用户下级数量
 *
 * @author chenLiJia
 * @since 2019-11-09 15:23:29
 **/
@Service
public class UserChildCountServiceImpl implements UserChildCountServiceI {


    @Resource
    private UserChildCountMapper userChildCountMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-09 15:23:29
     **/
    public Result add(UserChildCount params) {

        int i = userChildCountMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-09 15:23:29
     **/
    public Result update(UserChildCount params) {

        int i = userChildCountMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询用户的下级数量
     *
     * @param userId 1
     * @return com.logicalthining.endeshop.entity.UserChildCountVo
     * @since 下午 4:52 2019/11/13 0013
     **/
    @Override
    public UserChildCount findByUserId(Integer userId) {
        UserChildCount userChildCount = userChildCountMapper.selectByPrimaryKey(userId);
        if (Objects.isNull(userChildCount)) {
            userChildCount = new UserChildCount();
            userChildCount.setUserId(userId);
            userChildCount.setChildCount(0);
            userChildCount.setPartnerChildCount(0);
            userChildCount.setCommonUserCount(0);
            userChildCount.setVipUserCount(0);
            userChildCountMapper.insertSelective(userChildCount);
        }
        return userChildCount;
    }


}

package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.dao.UserWithdrawPasswordMapper;
import com.logicalthining.endeshop.entity.UserWithdrawPassword;
import com.logicalthining.endeshop.service.UserWithdrawPasswordServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 用户提现密码
 *
 * @author chenLiJia
 * @since 2019-11-13 20:12:48
 **/
@Service
public class UserWithdrawPasswordServiceImpl implements UserWithdrawPasswordServiceI {


    @Resource
    private UserWithdrawPasswordMapper userWithdrawPasswordMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-13 20:12:48
     **/
    public Result add(UserWithdrawPassword params) {

        int i = userWithdrawPasswordMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-13 20:12:48
     **/
    public Result update(UserWithdrawPassword params) {

        int i = userWithdrawPasswordMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查找用户提现密码
     *
     * @param userId 用户id
     * @return com.logicalthining.endeshop.entity.UserWithdrawPassword
     * @since 上午 9:42 2019/11/14 0014
     **/
    @Override
    public UserWithdrawPassword findByUserId(Integer userId) {
        if (Objects.nonNull(userId)) {
            return userWithdrawPasswordMapper.selectByPrimaryKey(userId);
        }
        return null;
    }


}

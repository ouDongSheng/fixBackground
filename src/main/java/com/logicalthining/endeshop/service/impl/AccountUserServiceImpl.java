package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.dao.AccountUserMapper;
import com.logicalthining.endeshop.dao.UserMapper;
import com.logicalthining.endeshop.entity.AccountUser;
import com.logicalthining.endeshop.entity.User;
import com.logicalthining.endeshop.service.AccountUserServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * 用户账户
 *
 * @author chenLiJia
 * @since 2019-11-07 09:38:11
 **/
@Service
public class AccountUserServiceImpl implements AccountUserServiceI {


    @Resource
    private AccountUserMapper accountUserMapper;//用户账户
    @Resource
    private UserMapper userMapper;//用户


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-07 09:38:11
     **/
    public Result add(AccountUser params) {

        int i = accountUserMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-07 09:38:11
     **/
    public Result update(AccountUser params) {

        int i = accountUserMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }


    /**
     * 通过用户id查询用户账户信息
     *
     * @param userId 1
     * @return com.logicalthining.endeshop.entity.AccountUser
     * @since 上午 10:12 2019/11/7 0007
     **/
    @Override
    public AccountUser findByUserId(Integer userId) {

        if (Objects.nonNull(userId)) {
            //先判断一下这个用户是否存在
            User user = userMapper.selectByPrimaryKey(userId);
            if (Objects.nonNull(user)) {
                //查询账户信息是否存在
                AccountUser accountUser = accountUserMapper.selectByPrimaryKey(userId);
                if (Objects.isNull(accountUser)) {
                    //初始化用户账户信息
                    accountUser = new AccountUser().setUserId(userId).
                            setUserBalance(0.0).
                            setGrandTotalBalance(0.0).
                            setUpdateTime(new Date());

                    accountUserMapper.insertSelective(accountUser);
                }
                return accountUser;
            }
        }
        return null;
    }


}

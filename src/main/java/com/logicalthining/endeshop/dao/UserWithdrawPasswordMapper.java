package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.entity.UserWithdrawPassword;
import tk.mybatis.mapper.common.Mapper;

/**
 * 用户提现密码
 * @author chenLiJia
 * @since 2019-11-13 20:12:10
 * @version 1.0
 **/
public interface UserWithdrawPasswordMapper extends Mapper<UserWithdrawPassword> {
    UserWithdrawPassword selectByPrimaryKey(Integer userId);
}
package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.entity.AccountUser;
import tk.mybatis.mapper.common.Mapper;

/**
 * 用户账户
 * @author chenLiJia
 * @since 2019-11-07 09:37:20
 * @version 1.0
 **/
public interface AccountUserMapper extends Mapper<AccountUser> {
    AccountUser selectByPrimaryKey(Integer userId);
}
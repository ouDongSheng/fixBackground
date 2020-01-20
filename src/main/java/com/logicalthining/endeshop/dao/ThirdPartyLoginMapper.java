package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.entity.ThirdPartyLogin;
import tk.mybatis.mapper.common.Mapper;

/**
 * 用户第三方登录信息
 * @author chenLiJia
 * @since 2019-11-12 17:00:02
 * @version 1.0
 **/
public interface ThirdPartyLoginMapper extends Mapper<ThirdPartyLogin> {
    ThirdPartyLogin selectByPrimaryKey(Integer id);
}
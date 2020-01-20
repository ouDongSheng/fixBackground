package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.entity.UserScore;
import tk.mybatis.mapper.common.Mapper;

/**
 * 用户积分
 * @author chenLiJia
 * @since 2019-11-12 09:30:05
 * @version 1.0
 **/
public interface UserScoreMapper extends Mapper<UserScore> {
    UserScore selectByPrimaryKey(Integer userId);
}
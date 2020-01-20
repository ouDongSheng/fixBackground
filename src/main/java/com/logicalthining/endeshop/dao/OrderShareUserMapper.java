package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.entity.OrderShareUser;
import tk.mybatis.mapper.common.Mapper;

/**
 * 用户分享订单记录，记录了订单下单时是由谁分享过来的
 * @author chenLiJia
 * @since 2019-11-06 11:05:01
 * @version 1.0
 **/
public interface OrderShareUserMapper extends Mapper<OrderShareUser> {
    OrderShareUser selectByPrimaryKey(String groupId);
}
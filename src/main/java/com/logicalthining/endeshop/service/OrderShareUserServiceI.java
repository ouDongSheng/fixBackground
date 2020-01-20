package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.entity.OrderShareUser;

/**
 * 用户分享订单记录，记录了订单下单时是由谁分享过来的
 *
 * @author chenLiJia
 * @since 2019-11-06 11:05:15
 **/
public interface OrderShareUserServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 11:05:15
     **/
    Result add(OrderShareUser params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 11:05:15
     **/
    Result update(OrderShareUser params);

    /**
     * 通过组订单id查询订单下单分享信息
     *
     * @param groupId 1
     * @return com.logicalthining.endeshop.entity.OrderShareUser
     * @since 上午 11:11 2019/11/6 0006
     **/
    OrderShareUser findByGroupId(String groupId);

    /**
     * 根据groupId进行删除分享记录
     * @param groupId
     * @return
     */
    Result deleteByGroupId(String groupId);


}

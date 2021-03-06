package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.entity.ClientAddress;

import java.util.List;

/**
 * 用户地址信息表
 * @author chenLiJia
 * @since 2019-11-05 13:58:15
 **/
public interface ClientAddressServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 13:58:15
     **/
    Result add(ClientAddress params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 13:58:15
     **/
    Result update(ClientAddress params);


    /**
     * 将某个用户的收货地址全部改为非默认地址
     *
     * @param clientId 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenlijia
     * @since 下午 12:59 2019/8/15 0015
     **/
    Result updateClientCommonAddressByClient(Integer clientId);

    /**
     * 条件查询用户收货地址
     * @since 下午 2:14 2019/11/5 0005
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.ClientAddress>
     **/
    List<ClientAddress> listByCondition(ClientAddress condition);

    /**
     * 主键查询
     * @since 上午 11:10 2019/11/19 0019
     * @param id 1
     * @return com.logicalthining.endeshop.entity.ClientAddress
     **/
    ClientAddress findById(Integer id);
}

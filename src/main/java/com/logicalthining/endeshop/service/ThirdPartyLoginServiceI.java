package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.entity.ThirdPartyLogin;

/**
 * 用户第三方登录信息
 * @author chenLiJia
 * @since 2019-11-12 17:00:13
 **/
public interface ThirdPartyLoginServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-12 17:00:13
     **/
    Result add(ThirdPartyLogin params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-12 17:00:13
     **/
    Result update(ThirdPartyLogin params);

    /**
     * 通过openId查询用户第三方信息
     * @since 下午 5:20 2019/11/12 0012
     * @param openId 1
     * @return com.logicalthining.endeshop.entity.ThirdPartyLogin
     **/
    ThirdPartyLogin findByOpenId(String openId);

    /**
     * 根据用户id和第三方类型查询
     * @since 下午 7:25 2019/11/12 0012
     * @param userId 用户id
     * @param type 第三方类型
     * @return com.logicalthining.endeshop.entity.ThirdPartyLogin
     **/
    ThirdPartyLogin findByUserIdAndType(Integer userId,Integer type);

}

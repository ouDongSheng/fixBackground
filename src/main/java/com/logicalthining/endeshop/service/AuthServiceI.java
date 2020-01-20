package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.common.responseVo.auth.AuthVo;
import com.logicalthining.endeshop.entity.Auth;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 权限表
 *
 * @author chenLiJia
 * @since 2019-10-30 10:01:08
 **/
public interface AuthServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-10-30 10:01:08
     **/
    Result add(Auth params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-10-30 10:01:08
     **/
    Result update(Auth params);


    /**
     * 列出所有权限
     *
     * @param roleId 角色id
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.auth.AuthVo>
     * @since 下午 1:51 2019/10/30 0030
     **/
    List<AuthVo> listAuthVo(Integer roleId);


    /**
     * 根据角色id集合列出对应的权限
     *
     * @param roleIdSet 1
     * @return java.util.Map<java.lang.Integer, java.util.List < com.logicalthining.endeshop.common.responseVo.auth.AuthVo>>
     * @since 下午 4:05 2019/10/30 0030
     **/
    Map<Integer, List<AuthVo>> listAuthVo(Set<Integer> roleIdSet);
}

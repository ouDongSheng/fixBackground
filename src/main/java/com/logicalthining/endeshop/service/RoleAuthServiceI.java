package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.entity.RoleAuth;

import java.util.List;

/**
 * 角色权限关联表
 * @author chenLiJia
 * @since 2019-10-30 10:01:08
 **/
public interface RoleAuthServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-10-30 10:01:08
     **/
    Result add(RoleAuth params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-10-30 10:01:08
     **/
    Result update(RoleAuth params);


    /**
     * 批量添加
     * @since 下午 3:16 2019/10/30 0030
     * @param list 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    Result batchAdd(List<RoleAuth> list);

    /**
     * 删除角色的所有权限关联
     * @since 下午 3:17 2019/10/30 0030
     * @param roleId 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    Result delete(Integer roleId);

}

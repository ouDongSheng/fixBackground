package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.common.responseVo.auth.AuthVo;
import com.logicalthining.endeshop.entity.Auth;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

public interface AuthMapper extends Mapper<Auth> {

    /**
     * 通过角色id查询权限
     *
     * @param roleId 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.auth.AuthVo>
     * @since 下午 2:27 2019/10/30 0030
     **/
    List<AuthVo> listByRoleId(@Param("roleId") Integer roleId);

    /**
     * 通过id集合查询数据
     * @since 下午 4:10 2019/10/30 0030
     * @param idSet 1
     * @return java.util.List<com.logicalthining.endeshop.entity.Auth>
     **/
    List<Auth> listByIdSet(@Param("idSet") Set<Integer> idSet);
}

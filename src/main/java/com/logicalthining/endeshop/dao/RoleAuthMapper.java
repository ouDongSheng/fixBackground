package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.entity.RoleAuth;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

public interface RoleAuthMapper extends Mapper<RoleAuth> {

    /**
     * 批量添加
     *
     * @param roleAuthList 1
     * @return java.lang.Integer
     * @since 下午 3:20 2019/10/30 0030
     **/
    Integer batchInsert(@Param("roleAuthList") List<RoleAuth> roleAuthList);

    /**
     * 根据角色id集合查询数据
     *
     * @param roleIdSet 1
     * @return java.util.List<com.logicalthining.endeshop.entity.RoleAuth>
     * @since 下午 4:08 2019/10/30 0030
     **/
    List<RoleAuth> listByRoleIdSet(@Param("roleIdSet") Set<Integer> roleIdSet);

}

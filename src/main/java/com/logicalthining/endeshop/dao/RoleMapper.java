package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.common.requestVo.BatchUpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.role.QueryParams;
import com.logicalthining.endeshop.common.responseVo.role.RoleVo;
import com.logicalthining.endeshop.entity.Role;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleMapper extends Mapper<Role> {

    /**
     * 列表查询角色
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.role.RoleVo>
     * @since 下午 3:46 2019/10/30 0030
     **/
    List<RoleVo> listPage(QueryParams params);

    /**
     * 批量修改角色状态
     * @since 下午 12:43 2019/11/9 0009
     * @param params 1
     * @return java.lang.Integer
     **/
    Integer batchUpdateState(BatchUpdateStateParams params);

}

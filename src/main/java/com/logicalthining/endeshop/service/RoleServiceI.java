package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.common.requestVo.BatchUpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.role.QueryParams;
import com.logicalthining.endeshop.common.responseVo.role.RoleVo;
import com.logicalthining.endeshop.entity.Role;

import java.util.List;

/**
 * 角色表
 *
 * @author chenLiJia
 * @since 2019-10-30 10:01:08
 **/
public interface RoleServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-10-30 10:01:08
     **/
    Result add(Role params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-10-30 10:01:08
     **/
    Result update(Role params);

    /**
     * 条件查询
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.Role>
     * @since 下午 3:10 2019/10/30 0030
     **/
    List<Role> listByCondition(Role condition);

    /**
     * 删除
     *
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 3:40 2019/10/30 0030
     **/
    Result delete(Integer id);

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
     * @since 下午 12:42 2019/11/9 0009
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    Result batchUpdateState(BatchUpdateStateParams params);

    /**
     * 根据Id查询角色
     * @since 下午 4:19 2019/11/29 0029
     * @param id 1
     * @return com.logicalthining.endeshop.entity.Role
     **/
    Role findById(Integer id);
}

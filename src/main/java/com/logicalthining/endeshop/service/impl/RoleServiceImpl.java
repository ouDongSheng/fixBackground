package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.logicalthining.endeshop.common.requestVo.BatchUpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.role.QueryParams;
import com.logicalthining.endeshop.common.responseVo.role.RoleVo;
import com.logicalthining.endeshop.dao.RoleMapper;
import com.logicalthining.endeshop.entity.Role;
import com.logicalthining.endeshop.service.RoleServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 角色表
 *
 * @author chenLiJia
 * @since 2019-10-30 10:01:08
 **/
@Service
public class RoleServiceImpl implements RoleServiceI {


    @Resource
    private RoleMapper roleMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-10-30 10:01:08
     **/
    public Result add(Role params) {

        int i = roleMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-10-30 10:01:08
     **/
    public Result update(Role params) {

        int i = roleMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 条件查询
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.Role>
     * @since 下午 3:10 2019/10/30 0030
     **/
    @Override
    public List<Role> listByCondition(Role condition) {

        condition = PropertyCheckUtil.transferObjectNotNull(condition, true);
        return roleMapper.select(condition);
    }

    /**
     * 删除
     *
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 3:40 2019/10/30 0030
     **/
    @Override
    public Result delete(Integer id) {
        if (Objects.nonNull(id)) {
            roleMapper.deleteByPrimaryKey(id);
        }
        return Result.success("操作成功");
    }

    /**
     * 列表查询角色
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.role.RoleVo>
     * @since 下午 3:46 2019/10/30 0030
     **/
    @Override
    public List<RoleVo> listPage(QueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        return roleMapper.listPage(params);
    }

    /**
     * 批量修改角色状态
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 12:42 2019/11/9 0009
     **/
    @Override
    public Result batchUpdateState(BatchUpdateStateParams params) {

        Integer i = roleMapper.batchUpdateState(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 根据Id查询角色
     *
     * @param id 1
     * @return com.logicalthining.endeshop.entity.Role
     * @since 下午 4:19 2019/11/29 0029
     **/
    @Override
    public Role findById(Integer id) {
        if (Objects.nonNull(id)) {
            return roleMapper.selectByPrimaryKey(id);
        }
        return null;
    }


}

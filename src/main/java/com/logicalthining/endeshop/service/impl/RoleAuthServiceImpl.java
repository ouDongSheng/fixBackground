package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.list.Lists;
import com.logicalthining.endeshop.dao.RoleAuthMapper;
import com.logicalthining.endeshop.entity.RoleAuth;
import com.logicalthining.endeshop.service.RoleAuthServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色权限关联表
 *
 * @author chenLiJia
 * @since 2019-10-30 10:01:08
 **/
@Service
public class RoleAuthServiceImpl implements RoleAuthServiceI {


    @Resource
    private RoleAuthMapper roleAuthMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-10-30 10:01:08
     **/
    public Result add(RoleAuth params) {

        int i = roleAuthMapper.insertSelective(params);
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
    public Result update(RoleAuth params) {

        int i = roleAuthMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 批量添加
     *
     * @param list 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 3:16 2019/10/30 0030
     **/
    @Override
    public Result batchAdd(List<RoleAuth> list) {
        if (Lists.isNotEmpty(list)) {
            roleAuthMapper.batchInsert(list);
        }
        return Result.success("操作成功");
    }

    /**
     * 删除角色的所有权限关联
     *
     * @param roleId 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 3:17 2019/10/30 0030
     **/
    @Override
    public Result delete(Integer roleId) {
        roleAuthMapper.delete(new RoleAuth().setRoleId(roleId));
        return Result.success("操作成功");
    }


}

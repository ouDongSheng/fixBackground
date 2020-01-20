package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import com.logicalthining.endeshop.common.responseVo.auth.AuthVo;
import com.logicalthining.endeshop.dao.AuthMapper;
import com.logicalthining.endeshop.dao.RoleAuthMapper;
import com.logicalthining.endeshop.entity.Auth;
import com.logicalthining.endeshop.entity.RoleAuth;
import com.logicalthining.endeshop.service.AuthServiceI;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 权限表
 *
 * @author chenLiJia
 * @since 2019-10-30 10:01:08
 **/
@Service
public class AuthServiceImpl implements AuthServiceI {


    @Resource
    private AuthMapper authMapper;
    @Resource
    private RoleAuthMapper roleAuthMapper;//角色权限关联


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-10-30 10:01:08
     **/
    public Result add(Auth params) {

        int i = authMapper.insertSelective(params);
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
    public Result update(Auth params) {

        int i = authMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }


    /**
     * 列出所有权限
     * 如果角色id为空 直接返回所有权限
     * 调用者需要在调用之前做好判断
     *
     * @param roleId 角色id
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.auth.AuthVo>
     * @since 下午 1:51 2019/10/30 0030
     **/
    @Override
    public List<AuthVo> listAuthVo(Integer roleId) {

        if (Objects.isNull(roleId)) {
            List<Auth> list = authMapper.selectAll();
            return transferToAuthVo(list);
        }

        List<AuthVo> authVoList = authMapper.listByRoleId(roleId);
        return recursiveFindChild(authVoList, null);
    }

    /**
     * 根据角色id集合列出对应的权限
     *
     * @param roleIdSet 1
     * @return java.util.Map<java.lang.Integer, java.util.List < com.logicalthining.endeshop.common.responseVo.auth.AuthVo>>
     * @since 下午 4:05 2019/10/30 0030
     **/
    @Override
    public Map<Integer, List<AuthVo>> listAuthVo(Set<Integer> roleIdSet) {
        if (Sets.isEmpty(roleIdSet)) {
            return new HashMap<>();
        }

        List<RoleAuth> roleAuths = roleAuthMapper.listByRoleIdSet(roleIdSet);
        //权限id集合
        Set<Integer> authIdSet = roleAuths.stream().map(e -> e.getAuthId()).collect(Collectors.toSet());
        List<Auth> auths = authMapper.listByIdSet(authIdSet);

        //开始处理数据
        HashMap<Integer, List<AuthVo>> map = new HashMap<>();
        for (Integer roleId : roleIdSet) {
            //查询这个角色的权限集合
            Set<Integer> currentRoleAuthSet = roleAuths.stream().filter(e -> Objects.equals(e.getRoleId(), roleId)).map(e -> e.getAuthId()).collect(Collectors.toSet());
            List<Auth> currentRoleAuth = auths.stream().filter(e -> currentRoleAuthSet.contains(e.getId())).collect(Collectors.toList());
            List<AuthVo> authVoList = transferToAuthVo(currentRoleAuth);
            map.put(roleId, authVoList);
        }
        return map;
    }


    /**
     * Auth 转为有父子关系的对象 AuthVo
     *
     * @param list 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.auth.AuthVo>
     * @since 下午 2:34 2019/10/30 0030
     **/
    private List<AuthVo> transferToAuthVo(List<Auth> list) {

        if (Lists.isNotEmpty(list)) {
            List<AuthVo> authVoList = list.stream().map(e -> {
                AuthVo authVo = new AuthVo();
                BeanUtils.copyProperties(e, authVo);
                return authVo;
            }).collect(Collectors.toList());

            //开始处理父子关系
            authVoList = recursiveFindChild(authVoList, null);
            return authVoList;
        }

        return null;
    }


    /**
     * 递归填充子权限
     *
     * @param list     1
     * @param parentId 2
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.auth.AuthVo>
     * @since 下午 2:03 2019/10/30 0030
     **/
    private List<AuthVo> recursiveFindChild(List<AuthVo> list, Integer parentId) {

        List<AuthVo> resultList = new ArrayList<>();
        for (AuthVo authVo : list) {
            if (Objects.equals(authVo.getParentAuth(), parentId)) {
                List<AuthVo> authVoList = recursiveFindChild(list, authVo.getId());
                authVo.setChildren(authVoList);
                resultList.add(authVo);
            }
        }
        return resultList;
    }

}

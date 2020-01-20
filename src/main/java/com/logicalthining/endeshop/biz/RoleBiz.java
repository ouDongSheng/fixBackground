package com.logicalthining.endeshop.biz;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.logicalthining.endeshop.common.requestVo.BatchUpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.UpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.role.AddParams;
import com.logicalthining.endeshop.common.requestVo.role.QueryParams;
import com.logicalthining.endeshop.common.requestVo.role.UpdateParams;
import com.logicalthining.endeshop.common.responseVo.auth.AuthVo;
import com.logicalthining.endeshop.common.responseVo.role.RoleVo;
import com.logicalthining.endeshop.entity.Admin;
import com.logicalthining.endeshop.entity.Role;
import com.logicalthining.endeshop.entity.RoleAuth;
import com.logicalthining.endeshop.service.AdminServiceI;
import com.logicalthining.endeshop.service.AuthServiceI;
import com.logicalthining.endeshop.service.RoleAuthServiceI;
import com.logicalthining.endeshop.service.RoleServiceI;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色表
 *
 * @author chenLiJia
 * @since 2019-10-30 10:01:08
 **/
@Service
public class RoleBiz {


    @Autowired
    private RoleServiceI roleService;//角色
    @Autowired
    private AdminServiceI adminService;//后台用户
    @Autowired
    private RoleAuthServiceI roleAuthService;//角色权限关联
    @Autowired
    private AuthServiceI authService;//权限

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:45 2019/10/30 0030
     **/
    public Result add(AddParams params) {

        //是否登录
        Optional<Admin> optional = adminService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断这个角色名称是否已经存在
        Role condition = new Role().setRoleName(params.getRoleName());
        List<Role> roles = roleService.listByCondition(condition);
        if (Lists.isNotEmpty(roles)) {
            return Result.failure("角色名称已存在");
        }

        Role role = new Role();
        BeanUtils.copyProperties(params, role);
        role.setUpdateUser(optional.get().getId()).setUpdateTime(new Date());

        roleService.add(role);

        //批量添加角色对应的权限
        if (Lists.isNotEmpty(params.getAuthIdList())) {
            List<Integer> authIdList = params.getAuthIdList();
            List<RoleAuth> roleAuthList = authIdList.stream().map(e -> new RoleAuth().setRoleId(role.getId()).setAuthId(e)).
                    collect(Collectors.toList());
            roleAuthService.batchAdd(roleAuthList);
        }
        return Result.success("操作成功");
    }

    /**
     * 编辑
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:45 2019/10/30 0030
     **/
    public Result update(UpdateParams params) {

        //是否登录
        Optional<Admin> optional = adminService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断这个角色名称是否已经存在
        Role condition = new Role().setRoleName(params.getRoleName());
        List<Role> roles = roleService.listByCondition(condition);
        if (Lists.isNotEmpty(roles) && roles.stream().filter(e -> !Objects.equals(e.getId(), params.getId())).findAny().isPresent()) {
            return Result.failure("角色名称已存在");
        }

        //判断数据是否存在
        condition = new Role().setId(params.getId());
        List<Role> roles1 = roleService.listByCondition(condition);
        if (Lists.isEmpty(roles1)) {
            return Result.failure("数据不存在");
        }

        Role role = roles1.get(0);
        BeanUtils.copyProperties(params, role);
        roleService.update(role);

        //修改对应的权限
        roleAuthService.delete(role.getId());
        if (Lists.isNotEmpty(params.getAuthIdList())) {
            List<Integer> authIdList = params.getAuthIdList();
            List<RoleAuth> roleAuthList = authIdList.stream().map(e -> new RoleAuth().setRoleId(role.getId()).setAuthId(e)).
                    collect(Collectors.toList());
            roleAuthService.batchAdd(roleAuthList);
        }

        return Result.success("操作成功");
    }

    /**
     * 删除
     *
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:45 2019/10/30 0030
     **/
    public Result delete(Integer id) {

        if (Objects.isNull(id)) {
            return Result.failure("id为空");
        }

        //判断有没有用户正在使用这个角色,如果有,无法删除
        Admin adminCondition = new Admin().setRoleId(id).setDeleteStatus(BooleanConstant.NO_INTEGER);
        List<Admin> adminList = adminService.findByCondition(adminCondition);
        if (Lists.isNotEmpty(adminList)) {
            return Result.failure("有用户正在使用该角色,无法删除");
        }

        return roleService.delete(id);
    }

    /**
     * 列出所有角色
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:46 2019/10/30 0030
     **/
    public Result listAll() {
        Role emptyCondition = new Role();
        List<Role> roles = roleService.listByCondition(emptyCondition);
        List<RoleVo> voList = roles.stream().map(e -> {
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(e, roleVo);
            return roleVo;
        }).collect(Collectors.toList());
        //关联权限
        getFullInfo(voList);
        return Result.success("查询成功", voList);
    }

    /**
     * 列表查询
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:46 2019/10/30 0030
     **/
    public Result listPage(QueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<RoleVo> list = roleService.listPage(params);
        PageInfo<RoleVo> pageInfo = new PageInfo<>(list);
        //关联权限
        getFullInfo(list);
        return Result.success("查询成功", pageInfo);
    }

    /**
     * 关联角色权限
     *
     * @param list 1
     * @return void
     * @since 下午 5:34 2019/11/7 0007
     **/
    private void getFullInfo(List<RoleVo> list) {
        //关联权限
        if (Lists.isNotEmpty(list)) {
            Set<Integer> roleIdSet = list.stream().map(e -> e.getId()).collect(Collectors.toSet());
            Map<Integer, List<AuthVo>> map = authService.listAuthVo(roleIdSet);
            for (RoleVo vo : list) {
                List<AuthVo> authVoList = map.get(vo.getId());
                vo.setAuthVoList(authVoList);
            }
        }
    }


    /**
     * 修改状态
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:28 2019/11/7 0007
     **/
    public Result updateState(UpdateStateParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //查询数据是否存在
        Role roleCondition = new Role().setId(params.getId());
        List<Role> roles = roleService.listByCondition(roleCondition);
        if (Lists.isEmpty(roles)) {
            return Result.failure("数据不存在");
        }

        Role role = roles.get(0);
        role.setOpenStatus(params.getState());

        return roleService.update(role);
    }

    /**
     * 主键查询角色信息
     *
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:14 2019/11/8 0008
     **/
    public Result findById(Integer id) {

        if (Objects.isNull(id)) {
            return Result.failure("id为空");
        }

        //查询数据是否存在
        Role roleCondition = new Role().setId(id);
        List<Role> roles = roleService.listByCondition(roleCondition);
        if (Lists.isEmpty(roles)) {
            return Result.failure("数据不存在");
        }

        Role role = roles.get(0);
        //查询角色的权限
        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(role, roleVo);

        getFullInfo(Lists.asList(roleVo));

        return Result.success("查询成功", roleVo);
    }

    /**
     * 批量修改状态
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:28 2019/11/7 0007
     **/
    public Result batchUpdateState(BatchUpdateStateParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        result = roleService.batchUpdateState(params);
        return result;
    }

}

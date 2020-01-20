package com.logicalthining.endeshop.biz;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.encrypt.MD5EncryptUtil;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Maps;
import com.github.chenlijia1111.utils.list.annos.MapType;
import com.github.chenlijia1111.utils.oauth.jwt.JWTUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.logicalthining.endeshop.common.pojo.Constants;
import com.logicalthining.endeshop.common.requestVo.UpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.admin.*;
import com.logicalthining.endeshop.common.responseVo.admin.AdminVo;
import com.logicalthining.endeshop.common.responseVo.auth.AuthVo;
import com.logicalthining.endeshop.entity.Admin;
import com.logicalthining.endeshop.entity.Role;
import com.logicalthining.endeshop.service.AdminServiceI;
import com.logicalthining.endeshop.service.AuthServiceI;
import com.logicalthining.endeshop.service.MsgSendRecodeServiceI;
import com.logicalthining.endeshop.service.RoleServiceI;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 后台用户表
 *
 * @author chenLiJia
 * @since 2019-10-30 10:01:08
 **/
@Service
public class AdminBiz {


    @Autowired
    private AdminServiceI adminService;//后台用户
    @Autowired
    private AuthServiceI authService;//权限
    @Autowired
    private MsgSendRecodeServiceI msgSendRecodeService;//发放消息记录(验证码等)
    @Autowired
    private RoleServiceI roleService;//角色

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:28 2019/10/30 0030
     **/

    public Result add(AddParams params) {

        //获取当前用户
        Optional<Admin> optional = adminService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        //验证参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //校验账号是否存在
        Admin condition = new Admin().setAccount(params.getAccount()).setDeleteStatus(BooleanConstant.NO_INTEGER);
        List<Admin> byCondition = adminService.findByCondition(condition);
        if (Lists.isNotEmpty(byCondition)) {
            return Result.failure("账号已存在");
        }

        Admin admin = new Admin();
        BeanUtils.copyProperties(params, admin);

        //密码加密
        String s = MD5EncryptUtil.MD5StringToHexString(params.getPassword() + Constants.SALT);
        admin.setPassword(s);
        admin.setCreateTime(new Date());
        admin.setCreateUser(optional.get().getId());
        admin.setDeleteStatus(BooleanConstant.NO_INTEGER);

        return adminService.add(admin);
    }

    /**
     * 编辑
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:29 2019/10/30 0030
     **/
    public Result update(UpdateParams params) {

        //获取当前用户
        Optional<Admin> optional = adminService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        //验证参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //先判断当前这个用户是否存在
        List<Admin> byCondition1 = adminService.findByCondition(new Admin().setId(params.getId()));
        if (Lists.isEmpty(byCondition1)) {
            return Result.failure("数据不存在");
        }

        //校验账号是否存在
        List<Admin> byCondition = adminService.findByCondition(new Admin().setAccount(params.getAccount()).setDeleteStatus(BooleanConstant.NO_INTEGER));
        if (Lists.isNotEmpty(byCondition) && byCondition.stream().filter(e -> !Objects.equals(e.getId(), params.getId())).findAny().isPresent()) {
            return Result.failure("账号已存在");
        }

        Admin admin = new Admin();
        BeanUtils.copyProperties(params, admin);

        //加密密码
        if (StringUtils.isNotEmpty(admin.getPassword())) {
            admin.setPassword(MD5EncryptUtil.MD5StringToHexString(admin.getPassword()));
        }

        return adminService.update(admin);
    }

    /**
     * 删除
     *
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:29 2019/10/30 0030
     **/
    public Result delete(Integer id) {

        if (Objects.isNull(id)) {
            return Result.failure("id为空");
        }

        //查询数据是否存在
        List<Admin> byCondition1 = adminService.findByCondition(new Admin().setId(id));
        if (Lists.isEmpty(byCondition1)) {
            return Result.failure("数据不存在");
        }

        Admin admin = byCondition1.get(0);
        admin.setDeleteStatus(BooleanConstant.YES_INTEGER);
        return adminService.update(admin);
    }

    /**
     * 重置密码
     *
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:29 2019/10/30 0030
     **/
    public Result resetPassword(Integer id) {

        if (Objects.isNull(id)) {
            return Result.failure("id为空");
        }

        //查询数据是否存在
        List<Admin> byCondition1 = adminService.findByCondition(new Admin().setId(id));
        if (Lists.isEmpty(byCondition1)) {
            return Result.failure("数据不存在");
        }

        Admin admin = byCondition1.get(0);
        admin.setPassword(MD5EncryptUtil.MD5StringToHexString(Constants.INIT_PASSWORD + Constants.SALT));
        return adminService.update(admin);
    }

    /**
     * 列表查询
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:29 2019/10/30 0030
     **/
    public Result listPage(QueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<AdminVo> list = adminService.listPage(params);
        PageInfo<AdminVo> pageInfo = new PageInfo<>(list);
        return Result.success("查询成功", pageInfo);
    }

    /**
     * 登录
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:41 2019/10/30 0030
     **/
    public Result login(LoginParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断验证码是否正确
        result = msgSendRecodeService.checkMsgCode(params.getVerifyCodeKey(), 1, params.getVerifyCodeValue());
        if (!result.getSuccess()) {
            return result;
        }


        //判断用户是否存在
        String account = params.getAccount();

        Admin condition = new Admin().setAccount(account).setDeleteStatus(BooleanConstant.NO_INTEGER);
        List<Admin> byCondition = adminService.findByCondition(condition);
        if (Lists.isEmpty(byCondition)) {
            return Result.failure("用户不存在");
        }

        Admin admin = byCondition.get(0);
        if (Objects.equals(BooleanConstant.NO_INTEGER, admin.getOpenStatus())) {
            return Result.failure("该用户未启用");
        }

        //校验密码
        if (!Objects.equals(admin.getPassword(), MD5EncryptUtil.MD5StringToHexString(params.getPassword() + Constants.SALT))) {
            return Result.failure("密码错误");
        }

        //判断角色
        Integer roleId = admin.getRoleId();
        if (Objects.nonNull(roleId)) {
            Role role = roleService.findById(roleId);
            if (Objects.isNull(role)) {
                return Result.failure("用户角色不存在或者已被删除");
            }
            if (Objects.equals(BooleanConstant.NO_INTEGER, role.getOpenStatus())) {
                return Result.failure("用户角色已被禁用");
            }
        }

        //登陆成功,返回token
        String jwt = JWTUtil.createJWT(admin.getId() + "", Constants.ADMIN, Constants.ADMIN_TIMEOUT, Constants.SALT);

        //查询这个用户拥有的权限
        List<AuthVo> authVoList = null;
        if (Objects.nonNull(admin.getRoleId())) {
            authVoList = authService.listAuthVo(admin.getRoleId());
        }
        Map map = Maps.mapBuilder(MapType.LINKED_HASH_MAP).put("token", jwt).put("admin", admin).put("authList", authVoList).build();
        return Result.success("登陆成功", map);
    }


    /**
     * 刷新token
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 12:52 2019/10/30 0030
     **/
    public Result refreshToken() {

        //获取当前用户
        Optional<Admin> optional = adminService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        //返回一个新的token给前端,进行续期
        String jwt = JWTUtil.createJWT(optional.get().getId() + "", Constants.ADMIN, Constants.ADMIN_TIMEOUT, Constants.SALT);

        return Result.success("操作成功", jwt);
    }


    /**
     * 修改用户状态
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:08 2019/11/1 0001
     **/
    public Result updateState(UpdateStateParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断数据是否存在
        Admin condition = new Admin().setId(params.getId());
        List<Admin> byCondition = adminService.findByCondition(condition);
        if (Lists.isEmpty(byCondition)) {
            return Result.failure("数据不存在");
        }

        Admin admin = new Admin().setId(params.getId()).setOpenStatus(params.getState());
        return adminService.update(admin);
    }


    /**
     * 修改密码
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:19 2019/11/7 0007
     **/
    public Result updatePassword(UpdatePasswordParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //获取当前用户
        Optional<Admin> optional = adminService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        Admin admin = optional.get();

        //判断原密码是否正确
        if (!Objects.equals(MD5EncryptUtil.MD5StringToHexString(params.getOldPassword()), admin.getPassword())) {
            return Result.failure("原密码错误");
        }

        //修改密码
        admin.setPassword(MD5EncryptUtil.MD5StringToHexString(params.getNewPassword()));

        return adminService.update(admin);
    }


    /**
     * 主键查询信息
     *
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:04 2019/11/8 0008
     **/
    public Result findById(Integer id) {

        if (Objects.isNull(id)) {
            return Result.failure("id为空");
        }

        Admin adminCondition = new Admin().setId(id).setDeleteStatus(BooleanConstant.NO_INTEGER);
        List<Admin> adminList = adminService.findByCondition(adminCondition);
        if (Lists.isEmpty(adminList)) {
            return Result.failure("用户不存在");
        }

        Admin admin = adminList.get(0);
        return Result.success("查询成功", admin);
    }

    /**
     * 获取当前用户
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:45 2019/11/28 0028
     **/
    public Result currentUser() {
        Optional<Admin> optional = adminService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }
        return Result.success("操作成功", optional.get());
    }

}

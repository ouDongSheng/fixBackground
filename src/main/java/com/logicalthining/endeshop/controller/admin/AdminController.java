package com.logicalthining.endeshop.controller.admin;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.AdminBiz;
import com.logicalthining.endeshop.common.requestVo.UpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.admin.*;
import com.logicalthining.endeshop.common.responseVo.admin.AdminVo;
import com.logicalthining.endeshop.entity.Admin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台用户表
 *
 * @author chenLiJia
 * @since 2019-10-30 10:01:08
 **/
@RestController
@RequestMapping(value = "admin/admin")
@Api(tags = "后台用户")
public class AdminController {

    @Autowired
    private AdminBiz biz;//后台用户

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:28 2019/10/30 0030
     **/
    @PostMapping(value = "add")
    @ApiOperation(value = "添加")
    public Result add(AddParams params) {
        return biz.add(params);
    }

    /**
     * 编辑
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:29 2019/10/30 0030
     **/
    @PostMapping(value = "update")
    @ApiOperation(value = "编辑")
    public Result update(UpdateParams params) {
        return biz.update(params);
    }

    /**
     * 删除
     *
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:29 2019/10/30 0030
     **/
    @PostMapping(value = "delete")
    @ApiOperation(value = "删除")
    public Result delete(Integer id) {
        return biz.delete(id);
    }

    /**
     * 重置密码
     *
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:29 2019/10/30 0030
     **/
    @PostMapping(value = "resetPassword")
    @ApiOperation(value = "重置密码")
    public Result resetPassword(Integer id) {
        return biz.resetPassword(id);
    }

    /**
     * 列表查询
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:29 2019/10/30 0030
     **/
    @GetMapping(value = "listPage")
    @ApiOperation(value = "列表查询", response = AdminVo.class)
    public Result listPage(QueryParams params) {
        return biz.listPage(params);
    }

    /**
     * 登录
     * token有效期为30分钟,前端需要在每个接口请求前进行判断token是否将要过期,如果将要过期,调取刷新token的接口获取新的token续期token
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:41 2019/10/30 0030
     **/
    @PostMapping(value = "login")
    @ApiOperation(value = "登录", notes = "token有效期为30分钟,前端需要在每个接口请求前进行判断token是否将要过期,如果将要过期,调取刷新token的接口获取新的token续期token")
    public Result login(LoginParams params) {
        return biz.login(params);
    }

    /**
     * 刷新token
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 12:52 2019/10/30 0030
     **/
    @PostMapping(value = "token/refresh")
    @ApiOperation(value = "刷新token")
    public Result refreshToken() {
        return biz.refreshToken();
    }


    /**
     * 修改用户状态
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:08 2019/11/1 0001
     **/
    @PostMapping(value = "state/update")
    @ApiOperation(value = "修改用户状态")
    public Result updateState(UpdateStateParams params) {
        return biz.updateState(params);
    }


    /**
     * 修改密码
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:19 2019/11/7 0007
     **/
    @PostMapping(value = "password/update")
    @ApiOperation(value = "修改密码")
    public Result updatePassword(UpdatePasswordParams params) {
        return biz.updatePassword(params);
    }

    /**
     * 主键查询信息
     *
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:04 2019/11/8 0008
     **/
    @GetMapping(value = "findById")
    @ApiOperation(value = "主键查询信息", response = Admin.class)
    public Result findById(Integer id) {
        return biz.findById(id);
    }

    /**
     * 获取当前用户
     * @since 上午 9:45 2019/11/28 0028
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    @GetMapping(value = "currentUser")
    @ApiOperation(value = "获取当前用户")
    public Result currentUser(){
        return biz.currentUser();
    }

}

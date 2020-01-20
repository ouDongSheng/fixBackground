package com.logicalthining.endeshop.controller.app;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.UserBiz;
import com.logicalthining.endeshop.common.requestVo.user.BindTelephoneParams;
import com.logicalthining.endeshop.common.requestVo.user.ChildUserQueryParams;
import com.logicalthining.endeshop.common.requestVo.user.LoginParams;
import com.logicalthining.endeshop.common.requestVo.user.WxLoginParams;
import com.logicalthining.endeshop.common.responseVo.user.AppUserChildUserListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


/**
 * 用户
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 上午 10:28
 **/
@RestController("appUserController")
@RequestMapping(value = "app/user")
@Api(tags = "用户接口")
public class UserController {


    @Autowired
    private UserBiz biz;

    /**
     * 用户登录
     *
     * @param params 1
     * @return javax.xml.transform.Result
     * @since 上午 11:08 2019/11/5 0005
     **/
    @PostMapping(value = "login")
    @ApiOperation(value = "用户登录", notes = "token有效期为1天,前端需要在每个接口请求前进行判断token是否将要过期,如果将要过期,调取刷新token的接口获取新的token续期token")
    public Result login(LoginParams params) {
        return biz.login(params);
    }

    /**
     * 绑定微信
     *
     * @param code 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 4:48 2019/11/12 0012
     **/
    @PostMapping(value = "wxLogin")
    @ApiOperation(value = "微信登录")
    public Result wxLogin(String code) {
        return biz.wxLogin(code);
    }

    /**
     * 绑定微信
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 4:48 2019/11/12 0012
     **/
    @PostMapping(value = "wxLogin/v2")
    @ApiOperation(value = "微信登录")
    public Result wxLoginV2(WxLoginParams params) {
        return biz.wxLoginV2(params);
    }

    /**
     * 绑定手机号
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 6:12 2019/11/12 0012
     **/
    @PostMapping(value = "bindTelephone")
    @ApiOperation(value = "绑定手机号")
    public Result bindTelephone(BindTelephoneParams params) {
        return biz.bindTelephone(params);
    }


    /**
     * 刷新token
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:11 2019/11/5 0005
     **/
    @PostMapping(value = "token/refresh")
    @ApiOperation(value = "刷新token")
    public Result refreshToken() {
        return biz.refreshToken();
    }


    /**
     * 用户二维码
     * 跳转到公众号首页
     *
     * @param id 1
     * @return void
     * @since 下午 2:27 2019/11/13 0013
     **/
    @GetMapping(value = "userQrCode")
    @ApiOperation(value = "用户二维码")
    public void userQrCode(Integer id, HttpServletResponse response) {
        biz.userQrCode(id, response);
    }


    /**
     * 客户端查询直接下级列表
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:40 2019/11/14 0014
     **/
    @GetMapping(value = "listChildUser")
    @ApiOperation(value = "客户端查询直接下级列表", response = AppUserChildUserListVo.class)
    public Result listChildUser(ChildUserQueryParams params) {
        return biz.listChildUser(params);
    }

    /**
     * 绑定上级关系
     * 如果当前用户没有消费，可以直接覆盖掉原来的上级
     * 如果当前用户消费了，不可以覆盖原来的上级
     *
     * @param parentUserId 上级用户id
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:50 2019/11/21 0021
     **/
    @PostMapping(value = "bindParent")
    @ApiOperation(value = "绑定上级关系")
    public Result bindParent(Integer parentUserId) {
        return biz.bindParent(parentUserId);
    }

    /**
     * 绑定微信
     * @param code
     * @return
     */
    @PostMapping(value = "bindWechat")
    @ApiOperation(value = "绑定微信")
    public Result bindWechat(String code){
        return biz.bindWechat(code);
    }

    /**
     * 获取openId
     * @param code
     * @return
     */
    @PostMapping(value = "openId")
    @ApiOperation(value = "获取openId")
    public Result openId(String code){
        return biz.openId(code);
    }

}

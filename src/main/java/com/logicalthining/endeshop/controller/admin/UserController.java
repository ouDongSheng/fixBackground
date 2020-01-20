package com.logicalthining.endeshop.controller.admin;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.UserBiz;
import com.logicalthining.endeshop.common.requestVo.BatchUpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.UpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.user.AddParams;
import com.logicalthining.endeshop.common.requestVo.user.QueryParams;
import com.logicalthining.endeshop.common.requestVo.user.UpdateParams;
import com.logicalthining.endeshop.common.responseVo.user.UserVo;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户表
 *
 * @author chenLiJia
 * @since 2019-10-31 19:11:15
 **/
@RestController
@RequestMapping(value = "admin/user")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private UserBiz biz;//用户

    /**
     * 添加用户
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 7:23 2019/10/31 0031
     **/
    @PostMapping(value = "add")
    @ApiOperation(value = "添加用户")
    public Result add(AddParams params) {
        return biz.add(params);
    }

    /**
     * 编辑用户
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 7:23 2019/10/31 0031
     **/
    @PostMapping(value = "update")
    @ApiOperation(value = "编辑用户")
    public Result update(UpdateParams params) {
        return biz.update(params);
    }

    /**
     * 删除用户
     *
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 7:23 2019/10/31 0031
     **/
    @PostMapping(value = "delete")
    @ApiOperation(value = "删除用户")
    public Result delete(Integer id) {
        return biz.delete(id);
    }


    /**
     * 列表查询用户
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 7:23 2019/10/31 0031
     **/
    @GetMapping(value = "listPage")
    @ApiOperation(value = "列表查询用户", response = UserVo.class)
    public Result listPage(QueryParams params) {
        return biz.listPage(params);
    }

    /**
     * 修改用户状态
     * @since 上午 9:14 2019/11/1 0001
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    @PostMapping(value = "state/update")
    @ApiOperation(value = "修改用户状态")
    public Result updateState(UpdateStateParams params){
        return biz.updateState(params);
    }

    /**
     * 批量修改用户状态
     * @since 上午 11:39 2019/11/9 0009
     * @param list 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    @PostMapping(value = "state/update/batch")
    @ApiOperation(value = "批量修改用户状态")
    public Result batchUpdateState(@RequestBody BatchUpdateStateParams params){
        return biz.batchUpdateState(params);
    }

    /**
     * 根据id查询用户信息
     * @since 下午 1:35 2019/11/9 0009
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    @GetMapping(value = "findById")
    @ApiOperation(value = "根据id查询用户信息")
    public Result findById(Integer id){
        return biz.findById(id);
    }


    /**
     * 用户二维码
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

}

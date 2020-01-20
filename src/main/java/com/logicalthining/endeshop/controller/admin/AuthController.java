package com.logicalthining.endeshop.controller.admin;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.AuthBiz;
import com.logicalthining.endeshop.common.responseVo.auth.AuthVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限表
 *
 * @author chenLiJia
 * @since 2019-10-30 10:01:08
 **/
@RestController
@RequestMapping(value = "admin/auth")
@Api(tags = "权限")
public class AuthController {

    @Autowired
    private AuthBiz biz;//权限


    /**
     * 列出所有权限
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 1:49 2019/10/30 0030
     **/
    @GetMapping(value = "listAll")
    @ApiOperation(value = "列出所有权限", response = AuthVo.class)
    public Result listAll() {
        return biz.listAll();
    }

}

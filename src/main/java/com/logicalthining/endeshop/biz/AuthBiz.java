package com.logicalthining.endeshop.biz;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.common.responseVo.auth.AuthVo;
import com.logicalthining.endeshop.service.AuthServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限表
 *
 * @author chenLiJia
 * @since 2019-10-30 10:01:08
 **/
@Service
public class AuthBiz {


    @Autowired
    private AuthServiceI authService;//权限

    /**
     * 列出所有权限
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 1:49 2019/10/30 0030
     **/
    public Result listAll() {
        Integer roleId = null;
        List<AuthVo> list = authService.listAuthVo(roleId);
        return Result.success("查询成功", list);
    }

}

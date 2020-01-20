package com.logicalthining.endeshop.common.intercepters;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.http.ResponseUtil;
import com.logicalthining.endeshop.entity.Admin;
import com.logicalthining.endeshop.service.AdminServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 后台用户拦截器
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/30 0030 下午 12:11
 **/
@Component
public class AdminInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    private AdminServiceI adminService;//后台用户


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Optional<Admin> optional = adminService.currentUser();
        if (!optional.isPresent()) {
            ResponseUtil.printRest(Result.notLogin(), response);
            return false;
        }

        return true;
    }
}

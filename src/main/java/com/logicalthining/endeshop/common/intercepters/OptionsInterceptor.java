package com.logicalthining.endeshop.common.intercepters;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.http.ResponseUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截options请求
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/20 0020 上午 11:05
 **/
@Component
public class OptionsInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if ("options".equals(method.toLowerCase())) {
            //直接返回
            ResponseUtil.printRest(Result.success("操作成功"), response);
            return false;
        }
        return true;
    }
}

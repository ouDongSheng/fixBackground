package com.logicalthining.endeshop.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/29 0029 下午 8:08
 **/
public class SpringUtil {

    /**
     * 获取当前线程 request
     * 线程安全的
     *
     * @return javax.servlet.http.HttpServletRequest
     * @author chenlijia
     * @since 上午 10:09 2019/6/14 0014
     **/
    public static HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
    }


    /**
     * 返回项目根目录
     *
     * @param request
     * @return
     */
    public static String getServerPath(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
//        String url = scheme + "://" + serverName + ":" + serverPort + contextPath;
        String url = "http://2503p699o7.wicp.vip/ende";
        return url;
    }

}

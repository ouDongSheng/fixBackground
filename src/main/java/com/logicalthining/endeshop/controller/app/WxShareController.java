package com.logicalthining.endeshop.controller.app;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.WxShareBiz;
import com.logicalthining.endeshop.common.requestVo.wx.WxJsApiSignParams;
import com.logicalthining.endeshop.common.responseVo.wx.WxJsApiSignVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 微信分享
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/20 0020 下午 3:19
 **/
@RestController
@RequestMapping(value = "app/wx/share")
@Api(tags = "微信分享")
public class WxShareController {

    @Autowired
    private WxShareBiz biz;

    /**
     * 微信公众号 接口凭证加密
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 4:10 2019/11/20 0020
     **/
    @PostMapping(value = "jsApiTicketSign")
    @ApiOperation(value = "微信公众号 接口凭证加密", response = WxJsApiSignVo.class)
    public Result jsApiTicketSign(WxJsApiSignParams params) {
        return biz.jsApiTicketSign(params);
    }

    /**
     * 获取分享图片
     * @param currentUserId 当前用户id
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:21 2019/11/25 0025
     **/
    @GetMapping(value = "shareImage")
    @ApiOperation(value = "获取分享图片")
    public void shareImage(Integer currentUserId, HttpServletResponse response) {
        biz.shareImageV2(currentUserId,response);
    }

}

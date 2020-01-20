package com.logicalthining.endeshop.controller.app;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.ShoppingOrderBiz;
import com.logicalthining.endeshop.common.requestVo.order.APPOrderQueryParams;
import com.logicalthining.endeshop.common.requestVo.order.CalculateOrderPriceVo;
import com.logicalthining.endeshop.common.requestVo.order.OrderAddParams;
import com.logicalthining.endeshop.common.requestVo.order.PayParams;
import com.logicalthining.endeshop.common.responseVo.order.AppOrderListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单
 *
 * @author chenLiJia
 * @since 2019-11-05 16:39:24
 **/
@RestController(value = "appShoppingOrderController")
@RequestMapping(value = "app/order")
@Api(tags = "订单")
public class ShoppingOrderController {

    @Autowired
    private ShoppingOrderBiz biz;//订单


    /**
     * 添加订单
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 4:53 2019/11/5 0005
     **/
    @PostMapping(value = "add")
    @ApiOperation(value = "添加订单")
    public Result add(@RequestBody OrderAddParams params) {
        return biz.add(params);
    }

    /**
     * 试算订单价格
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 7:28 2019/11/5 0005
     **/
    @PostMapping(value = "calculatePrice")
    @ApiOperation(value = "试算订单价格", response = CalculateOrderPriceVo.class)
    public Result calculatePrice(@RequestBody OrderAddParams params) {
        return biz.calculatePrice(params);
    }


    /**
     * 假支付接口
     *
     * @param payParams 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:50 2019/11/6 0006
     **/
    @PostMapping(value = "orderPay")
    @ApiOperation(value = "假支付接口")
    public Result orderPay(PayParams payParams) {
        return biz.orderPay(payParams);
    }


    /**
     * app 订单列表查询
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:11 2019/11/15 0015
     **/
    @GetMapping(value = "listPage")
    @ApiOperation(value = "订单列表查询", response = AppOrderListVo.class)
    public Result listPage(APPOrderQueryParams params) {
        return biz.appListPage(params);
    }

    /**
     * 根据groupId查询订单详情
     *
     * @param groupId 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:12 2019/11/15 0015
     **/
    @GetMapping(value = "findByGroupId")
    @ApiOperation(value = "根据groupId查询订单详情", response = AppOrderListVo.class)
    public Result findByGroupId(String groupId) {
        return biz.appFindByGroupId(groupId);
    }

    /**
     * 查找物流时间
     *
     * @param orderNo 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:13 2019/11/15 0015
     **/
    @GetMapping(value = "findExpressInfo")
    @ApiOperation(value = "查询物流信息")
    public Result findExpressInfo(String orderNo) {
        return biz.findExpressInfo(orderNo);
    }

    /**
     * 微信支付订单
     *
     * @param groupId 组订单编号
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:51 2019/11/19 0019
     **/
    @PostMapping(value = "wxPay")
    @ApiOperation(value = "微信支付订单")
    public Result wxPay(String groupId, String openId, HttpServletRequest request) {
        return biz.wxPay(groupId, openId, request);
    }

    /**
     * 微信支付回调地址
     *
     * @param request 1
     * @return java.lang.String
     * @since 上午 9:28 2019/11/20 0020
     **/
    @RequestMapping(value = "wxNotify")
    public String wxNotify(HttpServletRequest request) {
        return biz.wxNotify(request);
    }

}

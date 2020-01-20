package com.logicalthining.endeshop.controller.admin;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.ShoppingOrderBiz;
import com.logicalthining.endeshop.common.requestVo.order.AddRemarksParams;
import com.logicalthining.endeshop.common.requestVo.order.AdminOrderQueryParams;
import com.logicalthining.endeshop.common.requestVo.order.ShipParams;
import com.logicalthining.endeshop.common.responseVo.order.AdminOrderInfoVo;
import com.logicalthining.endeshop.common.responseVo.order.AdminOrderListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单
 * 订单的那些奖励都在订单完成之后才会进行发放--记得修改
 *
 * @author chenLiJia
 * @since 2019-11-05 16:39:24
 **/
@RestController
@RequestMapping(value = "admin/order")
@Api(tags = "订单")
public class ShoppingOrderController {


    @Autowired
    private ShoppingOrderBiz biz;

    /**
     * 后台订单列表查询
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:44 2019/11/7 0007
     **/
    @GetMapping(value = "listPage")
    @ApiOperation(value = "后台订单列表查询", response = AdminOrderListVo.class)
    public Result listPage(AdminOrderQueryParams params) {
        return biz.adminListPage(params);
    }

    /**
     * 根据groupId查询订单详情
     *
     * @param groupId 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 4:37 2019/11/7 0007
     **/
    @GetMapping(value = "findByGroupId")
    @ApiOperation(value = "根据groupId查询订单详情", response = AdminOrderInfoVo.class)
    public Result findByGroupId(String groupId) {
        return biz.adminFindByGroupId(groupId);
    }

    /**
     * 发货
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:24 2019/11/8 0008
     **/
    @PostMapping(value = "ship")
    @ApiOperation(value = "发货")
    public Result ship(ShipParams params) {
        return biz.ship(params);
    }

    /**
     * 自提发货
     *
     * @param orderNo 订单编号
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:24 2019/11/8 0008
     **/
    @PostMapping(value = "ship/foundSelf")
    @ApiOperation(value = "自提发货")
    public Result shipByFoundSelf(String orderNo) {
        return biz.shipByFoundSelf(orderNo);
    }

    /**
     * 取消订单
     * 取消订单之后,如果付了款的话,金额退回
     *
     * @param orderNo 订单编号
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:24 2019/11/8 0008
     **/
    @PostMapping(value = "cancelOrder")
    @ApiOperation(value = "取消订单")
    public Result cancelOrder(String orderNo) {
        return biz.adminCancelOrder(orderNo);
    }

    /**
     * 商家添加备注
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:28 2019/11/8 0008
     **/
    @PostMapping(value = "addRemarks")
    @ApiOperation(value = "商家添加备注")
    public Result addRemarks(AddRemarksParams params) {
        return biz.addRemarks(params);
    }

    /**
     * 完成订单
     *
     * @param orderNo 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:28 2019/11/8 0008
     **/
    @PostMapping(value = "completeOrder")
    @ApiOperation(value = "完成订单")
    public Result completeOrder(String orderNo) {
        return biz.completeOrder(orderNo);
    }

    /**
     * 查询物流信息
     *
     * @param orderNo 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:29 2019/11/8 0008
     **/
    @GetMapping(value = "findExpressInfo")
    @ApiOperation(value = "查询物流信息")
    public Result findExpressInfo(String orderNo) {
        return biz.findExpressInfo(orderNo);
    }


}

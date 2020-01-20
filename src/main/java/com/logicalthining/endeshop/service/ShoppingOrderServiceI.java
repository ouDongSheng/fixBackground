package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.common.requestVo.count.CountSaleQueryParams;
import com.logicalthining.endeshop.common.requestVo.order.APPOrderQueryParams;
import com.logicalthining.endeshop.common.requestVo.order.AdminOrderQueryParams;
import com.logicalthining.endeshop.common.requestVo.shareManage.ShareManageQueryParams;
import com.logicalthining.endeshop.common.responseVo.count.SaleCountProvinceVo;
import com.logicalthining.endeshop.common.responseVo.order.AdminOrderInfoVo;
import com.logicalthining.endeshop.common.responseVo.order.AdminOrderListVo;
import com.logicalthining.endeshop.common.responseVo.order.AppOrderListVo;
import com.logicalthining.endeshop.common.responseVo.shareManage.ShareManageVo;
import com.logicalthining.endeshop.entity.ShoppingOrder;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author chenLiJia
 * @since 2019-11-05 16:39:24
 **/
public interface ShoppingOrderServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    Result add(ShoppingOrder params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    Result update(ShoppingOrder params);

    /**
     * 条件查询订单
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.ShoppingOrder>
     * @since 上午 10:54 2019/11/6 0006
     **/
    List<ShoppingOrder> listByCondition(ShoppingOrder condition);

    /**
     * 条件统计数量
     *
     * @param condition 1
     * @return java.lang.Integer
     * @since 上午 11:58 2019/11/21 0021
     **/
    Integer countByCondition(ShoppingOrder condition);

    /**
     * 列表查询分享记录
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.shareManage.ShareManageVo>
     * @since 上午 11:02 2019/11/7 0007
     **/
    List<ShareManageVo> listShareManageVo(ShareManageQueryParams params);

    /**
     * 后台查询订单列表
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.order.AdminOrderListVo>
     * @since 下午 2:55 2019/11/7 0007
     **/
    List<AdminOrderListVo> adminListPage(AdminOrderQueryParams params);

    /**
     * 通过订单编号集合查询订单状态
     * 订单状态定义 1初始状态 2已付款 3已发货 4已收货 5已取消
     *
     * @param orderNoSet 1
     * @return java.util.Map<java.lang.String, java.lang.Integer>
     * @since 下午 3:38 2019/11/7 0007
     **/
    Map<String, Integer> findOrderStateByOrderNoSet(Set<String> orderNoSet);

    /**
     * 通过组订单id集合查询组订单状态
     * 订单状态定义 1初始状态 2已付款 3已发货 4已收货 5已取消
     *
     * @param groupIdSet 1
     * @return java.util.Map<java.lang.String, java.lang.Integer>
     * @since 下午 3:39 2019/11/7 0007
     **/
    Map<String, Integer> findGroupStateByGroupIdSet(Set<String> groupIdSet);

    /**
     * 后台查询订单详情
     *
     * @param groupId 组订单id
     * @return com.logicalthining.endeshop.common.responseVo.order.AdminOrderInfoVo
     * @since 下午 6:25 2019/11/7 0007
     **/
    AdminOrderInfoVo findAdminOrderInfoVo(String groupId);

    /**
     * 通过订单编号查询订单信息
     *
     * @param orderNo 1
     * @return com.logicalthining.endeshop.entity.ShoppingOrder
     * @since 上午 9:37 2019/11/8 0008
     **/
    ShoppingOrder findByOrderNo(String orderNo);

    /**
     * 统计每个省的销售值
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.count.SaleCountProvinceVo>
     * @since 下午 3:55 2019/11/12 0012
     **/
    List<SaleCountProvinceVo> listSaleCountProvinceVo(CountSaleQueryParams params);


    /**
     * 客户端订单列表查询
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.order.AppOrderListVo>
     * @since 上午 10:34 2019/11/15 0015
     **/
    List<AppOrderListVo> listAppOrderListVo(APPOrderQueryParams params);

    /**
     * 统计系统业绩
     *
     * @param startTime 1
     * @param endTime   2
     * @return java.lang.Double
     * @since 下午 4:24 2019/12/2 0002
     **/
    Double countPerformance(Date startTime, Date endTime);

    /**
     * 统计用户在本系统中付款金额大于等于界限金额的订单数量
     *
     * @param userId     1
     * @param limitMoney 界限金额 大于等于
     * @return java.lang.Double
     * @since 下午 2:22 2019/12/11 0011
     **/
    Integer countUserPayedMoney(String userId, Double limitMoney);

}

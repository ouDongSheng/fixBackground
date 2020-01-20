package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.common.requestVo.count.CountSaleQueryParams;
import com.logicalthining.endeshop.common.requestVo.order.APPOrderQueryParams;
import com.logicalthining.endeshop.common.requestVo.order.AdminOrderQueryParams;
import com.logicalthining.endeshop.common.requestVo.shareManage.ShareManageQueryParams;
import com.logicalthining.endeshop.common.responseVo.count.ProductSaleVo;
import com.logicalthining.endeshop.common.responseVo.count.SaleCountProvinceVo;
import com.logicalthining.endeshop.common.responseVo.order.AdminOrderListVo;
import com.logicalthining.endeshop.common.responseVo.order.AppOrderListVo;
import com.logicalthining.endeshop.common.responseVo.shareManage.ShareManageVo;
import com.logicalthining.endeshop.entity.ShoppingOrder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author chenLiJia
 * @since 2019-11-05 16:39:11
 * @version 1.0
 **/
public interface ShoppingOrderMapper extends Mapper<ShoppingOrder> {


    /**
     * 列表查询分享记录
     * @since 上午 11:02 2019/11/7 0007
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.shareManage.ShareManageVo>
     **/
    List<ShareManageVo> listShareManageVo(ShareManageQueryParams params);


    /**
     * 后台查询订单列表
     * @since 下午 2:55 2019/11/7 0007
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.order.AdminOrderListVo>
     **/
    List<AdminOrderListVo> adminListPage(AdminOrderQueryParams params);

    /**
     * 通过组订单Id集合查询订单集合
     * @since 下午 3:29 2019/11/7 0007
     * @param groupIdSet 1
     * @return java.util.List<com.logicalthining.endeshop.entity.ShoppingOrder>
     **/
    List<ShoppingOrder> listByGroupIdSet(@Param("groupIdSet") Set<String> groupIdSet);

    /**
     * 根据订单
     * @since 下午 3:42 2019/11/7 0007
     * @param orderNoSet 1
     * @return java.util.List<com.logicalthining.endeshop.entity.ShoppingOrder>
     **/
    List<ShoppingOrder> listByOrderNoSet(@Param("orderNoSet") Set<String> orderNoSet);

    /**
     * 统计每个省的销售值
     * @since 下午 4:09 2019/11/12 0012
     * @param startTime 1
     * @param endTime 2
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.count.SaleCountProvinceVo>
     **/
    List<SaleCountProvinceVo> listSaleCountProvinceVo(@Param("startTime") Date startTime,@Param("endTime") Date endTime);


    /**
     * 客户端订单列表查询
     * 这个系统前端不需要出现待付款的订单
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.order.AppOrderListVo>
     * @since 上午 10:34 2019/11/15 0015
     **/
    List<AppOrderListVo> listAppOrderListVo(APPOrderQueryParams params);

    /**
     * 查询产品销量对象
     * @since 下午 1:43 2019/11/18 0018
     * @param productIdSet 产品id集合
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.count.ProductSaleVo>
     **/
    List<ProductSaleVo> listProductSaleVo(@Param("productIdSet") Set<String> productIdSet);


    /**
     * 统计系统业绩
     *
     * @param startTime 1
     * @param endTime   这里的结束时间是可以=的,注意一下
     * @return java.lang.Double
     * @since 下午 4:24 2019/12/2 0002
     **/
    Double countPerformance(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 统计用户在本系统中付款金额大于等于界限金额的订单数量
     *
     * @param userId     1
     * @param limitMoney 界限金额 大于等于
     * @return java.lang.Double
     * @since 下午 2:22 2019/12/11 0011
     **/
    Integer countUserPayedMoney(@Param("userId") String userId, @Param("limitMoney") Double limitMoney);

}

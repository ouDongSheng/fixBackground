package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.common.requestVo.product.AppProductQueryParams;
import com.logicalthining.endeshop.common.requestVo.product.AdminProductQueryParams;
import com.logicalthining.endeshop.common.responseVo.count.ProductSaleVo;
import com.logicalthining.endeshop.common.responseVo.product.AdminProductListVo;
import com.logicalthining.endeshop.common.responseVo.product.AdminProductVo;
import com.logicalthining.endeshop.common.responseVo.product.AppProductListVo;
import com.logicalthining.endeshop.common.responseVo.product.ProductAwardCountInfo;
import com.logicalthining.endeshop.entity.Product;

import java.util.List;
import java.util.Set;

/**
 * 产品表
 * @author chenLiJia
 * @since 2019-11-01 13:46:54
 **/
public interface ProductServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 13:46:54
     **/
    Result add(Product params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 13:46:54
     **/
    Result update(Product params);

    /**
     * 条件查询
     * @param condition
     * @return
     */
    List<Product> listByCondition(Product condition);

    /**
     * 后台产品列表查询
     * @since 上午 9:32 2019/11/4 0004
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.product.AdminProductListVo>
     **/
    List<AdminProductListVo> adminListPage(AdminProductQueryParams params);

    /**
     * 客户端查询产品列表
     * @since 上午 11:49 2019/11/5 0005
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.product.AppProductListVo>
     **/
    List<AppProductListVo> appListPage(AppProductQueryParams params);

    /**
     * 通过产品id查询产品信息
     * @since 下午 5:17 2019/11/5 0005
     * @param productId 1
     * @return com.logicalthining.endeshop.entity.Product
     **/
    Product findByProductId(String productId);

    /**
     * 查询产品详细信息
     * @since 下午 7:36 2019/11/7 0007
     * @param productId 1
     * @return com.logicalthining.endeshop.common.responseVo.product.AdminProductVo
     **/
    AdminProductVo findAdminProductVoByProductId(String productId);

    /**
     * 查询产品销量
     * @since 下午 3:18 2019/11/18 0018
     * @param productIdSet 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.count.ProductSaleVo>
     **/
    List<ProductSaleVo> listProductSaleVo(Set<String> productIdSet);

    /**
     * 查询产品累计发放奖励
     * @since 下午 4:38 2019/11/25 0025
     * @param productId 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.product.ProductAwardCountInfo>
     **/
    List<ProductAwardCountInfo> listProductAwardCountInfo(Set<String> productId);
}

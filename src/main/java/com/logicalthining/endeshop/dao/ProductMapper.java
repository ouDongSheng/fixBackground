package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.common.requestVo.product.AdminProductQueryParams;
import com.logicalthining.endeshop.common.requestVo.product.AppProductQueryParams;
import com.logicalthining.endeshop.common.responseVo.product.AdminProductListVo;
import com.logicalthining.endeshop.common.responseVo.product.AppProductListVo;
import com.logicalthining.endeshop.common.responseVo.product.AppProductVo;
import com.logicalthining.endeshop.common.responseVo.product.ProductAwardCountInfo;
import com.logicalthining.endeshop.entity.Product;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 产品表
 * @author chenLiJia
 * @since 2019-11-01 13:46:43
 * @version 1.0
 **/
public interface ProductMapper extends Mapper<Product> {


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

    List<AppProductVo> appListByType(AppProductQueryParams params);


    /**
     * 查询产品累计发放奖励
     * @since 下午 4:38 2019/11/25 0025
     * @param productIdSet 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.product.ProductAwardCountInfo>
     **/
    List<ProductAwardCountInfo> listProductAwardCountInfo(@Param("productIdSet") Set<String> productIdSet);
}

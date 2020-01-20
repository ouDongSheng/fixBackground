package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.common.responseVo.product.GoodVo;
import com.logicalthining.endeshop.entity.Goods;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 商品表
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-01 13:46:43
 **/
public interface GoodsMapper extends Mapper<Goods> {

    /**
     * 批量删除商品
     *
     * @param goodIdSet 商品id集合
     * @return
     */
    Integer batchDelete(@Param("goodIdSet") Set<String> goodIdSet);


    /**
     * 通过产品id集合查询商品列表
     *
     * @param productIdSet 1
     * @return java.util.List<com.logicalthining.endeshop.entity.Goods>
     * @since 上午 10:01 2019/11/5 0005
     **/
    List<GoodVo> listByProductIdSet(@Param("productIdSet") Set<String> productIdSet);

}

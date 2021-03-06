package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.entity.ReturnGoodsOrder;
import tk.mybatis.mapper.common.Mapper;

/**
 * 退货单
 * @author chenLiJia
 * @since 2019-11-05 16:39:11
 * @version 1.0
 **/
public interface ReturnGoodsOrderMapper extends Mapper<ReturnGoodsOrder> {
    ReturnGoodsOrder selectByPrimaryKey(String reOrderNo);
}
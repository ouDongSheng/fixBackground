package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.entity.ReturnGoodsOrder;
import com.logicalthining.endeshop.dao.ReturnGoodsOrderMapper;
import com.logicalthining.endeshop.service.ReturnGoodsOrderServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 退货单
 * @author chenLiJia
 * @since 2019-11-05 16:39:24
 **/
@Service
public class ReturnGoodsOrderServiceImpl implements ReturnGoodsOrderServiceI {


    @Resource
    private ReturnGoodsOrderMapper returnGoodsOrderMapper;


    /**
     * 添加
     *
     * @param params      添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    public Result add(ReturnGoodsOrder params){
    
        int i = returnGoodsOrderMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params      编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    public Result update(ReturnGoodsOrder params){
    
        int i = returnGoodsOrderMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }


}

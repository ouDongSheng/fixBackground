package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.common.requestVo.capitalFlow.AppQueryParams;
import com.logicalthining.endeshop.common.responseVo.capitalFlow.AppUserCapitalVo;
import com.logicalthining.endeshop.entity.CapitalFlowUser;

import java.util.List;

/**
 * 用户资金流水
 *
 * @author chenLiJia
 * @since 2019-11-07 09:38:10
 **/
public interface CapitalFlowUserServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-07 09:38:10
     **/
    Result add(CapitalFlowUser params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-07 09:38:10
     **/
    Result update(CapitalFlowUser params);

    /**
     * 列出用户的流水记录
     *
     * @param params
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.capitalFlow.AppUserCapitalVo>
     * @since 上午 9:25 2019/11/14 0014
     **/
    List<AppUserCapitalVo> listAppUserCapitalVo(AppQueryParams params);

    /**
     * 流水记录id
     * @since 上午 11:15 2019/11/14 0014
     * @param id 流水id
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    Result deleteById(Integer id);

    /**
     * 条件查询
     * @since 上午 11:19 2019/11/14 0014
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.CapitalFlowUser>
     **/
    List<CapitalFlowUser> listByCondition(CapitalFlowUser condition);

}

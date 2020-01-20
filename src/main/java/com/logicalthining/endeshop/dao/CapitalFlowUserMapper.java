package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.common.requestVo.capitalFlow.AppQueryParams;
import com.logicalthining.endeshop.common.responseVo.capitalFlow.AppUserCapitalVo;
import com.logicalthining.endeshop.entity.CapitalFlowUser;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 用户资金流水
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-07 09:37:20
 **/
public interface CapitalFlowUserMapper extends Mapper<CapitalFlowUser> {

    /**
     * 列出用户的流水记录
     *
     * @param params
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.capitalFlow.AppUserCapitalVo>
     * @since 上午 9:25 2019/11/14 0014
     **/
    List<AppUserCapitalVo> listAppUserCapitalVo(AppQueryParams params);

}

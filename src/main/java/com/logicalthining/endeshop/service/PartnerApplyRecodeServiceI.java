package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.common.requestVo.partnerApply.QueryParams;
import com.logicalthining.endeshop.common.responseVo.partnerApply.PartnerApplyVo;
import com.logicalthining.endeshop.entity.PartnerApplyRecode;

import java.util.List;

/**
 * 合伙人申请记录
 *
 * @author chenLiJia
 * @since 2019-11-05 19:59:13
 **/
public interface PartnerApplyRecodeServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 19:59:13
     **/
    Result add(PartnerApplyRecode params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 19:59:13
     **/
    Result update(PartnerApplyRecode params);

    /**
     * 列表查询合伙人申请记录
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.partnerApply.PartnerApplyVo>
     * @since 上午 9:49 2019/11/6 0006
     **/
    List<PartnerApplyVo> listPartnerApplyVo(QueryParams params);

    /**
     * 条件查询列表
     * @since 上午 10:04 2019/11/6 0006
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.PartnerApplyRecode>
     **/
    List<PartnerApplyRecode> listByCondition(PartnerApplyRecode condition);
}

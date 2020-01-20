package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.entity.PartnerApplyShareUser;

/**
 * 合伙人申请 分享人
 * @author chenLiJia
 * @since 2019-11-06 11:44:42
 **/
public interface PartnerApplyShareUserServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 11:44:42
     **/
    Result add(PartnerApplyShareUser params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 11:44:42
     **/
    Result update(PartnerApplyShareUser params);


    /**
     * 通过合伙人申请id查询 分享人
     * @since 上午 11:57 2019/11/6 0006
     * @param applyId 合伙人申请id
     * @return com.logicalthining.endeshop.entity.PartnerApplyShareUser
     **/
    PartnerApplyShareUser findByApplyId(Integer applyId);


}

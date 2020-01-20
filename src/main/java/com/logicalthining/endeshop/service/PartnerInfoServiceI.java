package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.common.requestVo.userScore.NewPartnerQueryParams;
import com.logicalthining.endeshop.common.responseVo.userScore.NewPartnerUserInfo;
import com.logicalthining.endeshop.entity.PartnerInfo;

import java.util.List;

/**
 * 合伙人信息
 *
 * @author chenLiJia
 * @since 2019-11-06 10:17:39
 **/
public interface PartnerInfoServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 10:17:39
     **/
    Result add(PartnerInfo params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 10:17:39
     **/
    Result update(PartnerInfo params);

    /**
     * 条件查询
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.PartnerInfo>
     * @since 上午 10:22 2019/11/6 0006
     **/
    List<PartnerInfo> listByCondition(PartnerInfo condition);


    /**
     * 查询新晋合伙人列表
     *
     * @param params
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.userScore.NewPartnerUserInfo>
     * @since 下午 5:48 2019/11/11 0011
     **/
    List<NewPartnerUserInfo> listNewPartnerUserInfo(NewPartnerQueryParams params);


}

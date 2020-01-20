package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.common.requestVo.userScore.NewPartnerQueryParams;
import com.logicalthining.endeshop.common.responseVo.userScore.NewPartnerUserInfo;
import com.logicalthining.endeshop.entity.PartnerInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 合伙人信息
 * @author chenLiJia
 * @since 2019-11-06 10:17:24
 * @version 1.0
 **/
public interface PartnerInfoMapper extends Mapper<PartnerInfo> {


    /**
     * 查询新晋合伙人列表
     *
     * @param params
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.userScore.NewPartnerUserInfo>
     * @since 下午 5:48 2019/11/11 0011
     **/
    List<NewPartnerUserInfo> listNewPartnerUserInfo(NewPartnerQueryParams params);

}

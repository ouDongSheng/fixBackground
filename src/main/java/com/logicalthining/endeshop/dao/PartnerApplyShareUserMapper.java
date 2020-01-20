package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.entity.PartnerApplyShareUser;
import tk.mybatis.mapper.common.Mapper;

/**
 * 合伙人申请 分享人
 * @author chenLiJia
 * @since 2019-11-06 11:44:16
 * @version 1.0
 **/
public interface PartnerApplyShareUserMapper extends Mapper<PartnerApplyShareUser> {
    PartnerApplyShareUser selectByPrimaryKey(Integer partnerApplyId);
}
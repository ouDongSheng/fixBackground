package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.common.requestVo.partnerApply.QueryParams;
import com.logicalthining.endeshop.common.responseVo.partnerApply.PartnerApplyVo;
import com.logicalthining.endeshop.entity.PartnerApplyRecode;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 合伙人申请记录
 * @author chenLiJia
 * @since 2019-11-05 19:58:46
 * @version 1.0
 **/
public interface PartnerApplyRecodeMapper extends Mapper<PartnerApplyRecode> {

    /**
     * 列表查询合伙人申请记录
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.partnerApply.PartnerApplyVo>
     * @since 上午 9:49 2019/11/6 0006
     **/
    List<PartnerApplyVo> listPartnerApplyVo(QueryParams params);

}

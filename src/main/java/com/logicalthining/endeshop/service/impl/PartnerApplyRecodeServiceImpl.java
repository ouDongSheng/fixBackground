package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.logicalthining.endeshop.common.requestVo.partnerApply.QueryParams;
import com.logicalthining.endeshop.common.responseVo.partnerApply.PartnerApplyVo;
import com.logicalthining.endeshop.dao.PartnerApplyRecodeMapper;
import com.logicalthining.endeshop.entity.PartnerApplyRecode;
import com.logicalthining.endeshop.service.PartnerApplyRecodeServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 合伙人申请记录
 *
 * @author chenLiJia
 * @since 2019-11-05 19:59:13
 **/
@Service
public class PartnerApplyRecodeServiceImpl implements PartnerApplyRecodeServiceI {


    @Resource
    private PartnerApplyRecodeMapper partnerApplyRecodeMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 19:59:13
     **/
    public Result add(PartnerApplyRecode params) {

        int i = partnerApplyRecodeMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 19:59:13
     **/
    public Result update(PartnerApplyRecode params) {

        int i = partnerApplyRecodeMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 列表查询合伙人申请记录
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.partnerApply.PartnerApplyVo>
     * @since 上午 9:49 2019/11/6 0006
     **/
    @Override
    public List<PartnerApplyVo> listPartnerApplyVo(QueryParams params) {
        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        List<PartnerApplyVo> list = partnerApplyRecodeMapper.listPartnerApplyVo(params);
        return list;
    }

    /**
     * 条件查询列表
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.PartnerApplyRecode>
     * @since 上午 10:04 2019/11/6 0006
     **/
    @Override
    public List<PartnerApplyRecode> listByCondition(PartnerApplyRecode condition) {

        condition = PropertyCheckUtil.transferObjectNotNull(condition, true);
        return partnerApplyRecodeMapper.select(condition);
    }


}

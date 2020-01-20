package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.logicalthining.endeshop.common.requestVo.userScore.NewPartnerQueryParams;
import com.logicalthining.endeshop.common.responseVo.userScore.NewPartnerUserInfo;
import com.logicalthining.endeshop.entity.PartnerInfo;
import com.logicalthining.endeshop.dao.PartnerInfoMapper;
import com.logicalthining.endeshop.service.PartnerInfoServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 合伙人信息
 * @author chenLiJia
 * @since 2019-11-06 10:17:39
 **/
@Service
public class PartnerInfoServiceImpl implements PartnerInfoServiceI {


    @Resource
    private PartnerInfoMapper partnerInfoMapper;//合伙人信息


    /**
     * 添加
     *
     * @param params      添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 10:17:39
     **/
    public Result add(PartnerInfo params){
    
        int i = partnerInfoMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params      编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 10:17:39
     **/
    public Result update(PartnerInfo params){
    
        int i = partnerInfoMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 条件查询
     * @since 上午 10:22 2019/11/6 0006
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.PartnerInfo>
     **/
    @Override
    public List<PartnerInfo> listByCondition(PartnerInfo condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition,true);
        return partnerInfoMapper.select(condition);
    }

    /**
     * 查询新晋合伙人列表
     *
     * @param params
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.userScore.NewPartnerUserInfo>
     * @since 下午 5:48 2019/11/11 0011
     **/
    @Override
    public List<NewPartnerUserInfo> listNewPartnerUserInfo(NewPartnerQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params,true);
        List<NewPartnerUserInfo> list = partnerInfoMapper.listNewPartnerUserInfo(params);
        return list;
    }


}

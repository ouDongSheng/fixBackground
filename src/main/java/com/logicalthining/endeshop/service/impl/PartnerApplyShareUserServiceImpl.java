package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.dao.PartnerApplyShareUserMapper;
import com.logicalthining.endeshop.entity.PartnerApplyShareUser;
import com.logicalthining.endeshop.service.PartnerApplyShareUserServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 合伙人申请 分享人
 *
 * @author chenLiJia
 * @since 2019-11-06 11:44:42
 **/
@Service
public class PartnerApplyShareUserServiceImpl implements PartnerApplyShareUserServiceI {


    @Resource
    private PartnerApplyShareUserMapper partnerApplyShareUserMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 11:44:42
     **/
    public Result add(PartnerApplyShareUser params) {

        int i = partnerApplyShareUserMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 11:44:42
     **/
    public Result update(PartnerApplyShareUser params) {

        int i = partnerApplyShareUserMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 通过合伙人申请id查询 分享人
     *
     * @param applyId 合伙人申请id
     * @return com.logicalthining.endeshop.entity.PartnerApplyShareUser
     * @since 上午 11:57 2019/11/6 0006
     **/
    @Override
    public PartnerApplyShareUser findByApplyId(Integer applyId) {
        if (Objects.nonNull(applyId)) {
            return partnerApplyShareUserMapper.selectByPrimaryKey(applyId);
        }
        return null;
    }


}

package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.logicalthining.endeshop.common.requestVo.capitalFlow.AppQueryParams;
import com.logicalthining.endeshop.common.responseVo.capitalFlow.AppUserCapitalVo;
import com.logicalthining.endeshop.dao.CapitalFlowUserMapper;
import com.logicalthining.endeshop.entity.CapitalFlowUser;
import com.logicalthining.endeshop.service.CapitalFlowUserServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 用户资金流水
 *
 * @author chenLiJia
 * @since 2019-11-07 09:38:11
 **/
@Service
public class CapitalFlowUserServiceImpl implements CapitalFlowUserServiceI {


    @Resource
    private CapitalFlowUserMapper capitalFlowUserMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-07 09:38:11
     **/
    public Result add(CapitalFlowUser params) {

        int i = capitalFlowUserMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-07 09:38:11
     **/
    public Result update(CapitalFlowUser params) {

        int i = capitalFlowUserMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 列出用户的流水记录
     *
     * @param params
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.capitalFlow.AppUserCapitalVo>
     * @since 上午 9:25 2019/11/14 0014
     **/
    @Override
    public List<AppUserCapitalVo> listAppUserCapitalVo(AppQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        List<AppUserCapitalVo> list = capitalFlowUserMapper.listAppUserCapitalVo(params);
        return list;
    }

    /**
     * 流水记录id
     *
     * @param id 流水id
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:15 2019/11/14 0014
     **/
    @Override
    public Result deleteById(Integer id) {
        if (Objects.nonNull(id)) {
            capitalFlowUserMapper.deleteByPrimaryKey(id);
        }
        return Result.success("操作成功");
    }

    /**
     * 条件查询
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.CapitalFlowUser>
     * @since 上午 11:19 2019/11/14 0014
     **/
    @Override
    public List<CapitalFlowUser> listByCondition(CapitalFlowUser condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition, true);
        return capitalFlowUserMapper.select(condition);
    }


}

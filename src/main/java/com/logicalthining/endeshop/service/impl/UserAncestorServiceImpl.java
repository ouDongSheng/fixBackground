package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.entity.UserAncestor;
import com.logicalthining.endeshop.dao.UserAncestorMapper;
import com.logicalthining.endeshop.service.UserAncestorServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户的上级,上上级,祖宗，全部关联起来,方便直接判断是
 * @author chenLiJia
 * @since 2019-11-11 17:02:51
 **/
@Service
public class UserAncestorServiceImpl implements UserAncestorServiceI {


    @Resource
    private UserAncestorMapper userAncestorMapper;


    /**
     * 添加
     *
     * @param params      添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-11 17:02:51
     **/
    public Result add(UserAncestor params){
    
        int i = userAncestorMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params      编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-11 17:02:51
     **/
    public Result update(UserAncestor params){
    
        int i = userAncestorMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }


}

package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.entity.UserAncestor;

/**
 * 用户的上级,上上级,祖宗，全部关联起来,方便直接判断是
 * @author chenLiJia
 * @since 2019-11-11 17:02:51
 **/
public interface UserAncestorServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-11 17:02:51
     **/
    Result add(UserAncestor params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-11 17:02:51
     **/
    Result update(UserAncestor params);


}

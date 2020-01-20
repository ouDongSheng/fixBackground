package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.common.requestVo.admin.QueryParams;
import com.logicalthining.endeshop.common.responseVo.admin.AdminVo;
import com.logicalthining.endeshop.entity.Admin;

import java.util.List;
import java.util.Optional;

/**
 * 后台用户表
 * @author chenLiJia
 * @since 2019-10-30 10:01:08
 **/
public interface AdminServiceI {

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-10-30 10:01:08
     **/
    Result add(Admin params);

    /**
     * 添加
     *
     * @param params      1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-10-30 10:01:08
     **/
    Result update(Admin params);

    /**
     * 查询符合条件的数据
     * @since 上午 11:04 2019/10/30 0030
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.Admin>
     **/
    List<Admin> findByCondition(Admin condition);

    /**
     * 获取当前用户
     * @since 上午 11:20 2019/10/30 0030
     * @return java.util.Optional<com.logicalthining.endeshop.entity.Admin>
     **/
    Optional<Admin> currentUser();

    /**
     * 列表查询
     * @since 上午 11:51 2019/10/30 0030
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.admin.AdminVo>
     **/
    List<AdminVo> listPage(QueryParams params);


}

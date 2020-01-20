package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.common.requestVo.BatchUpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.UpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.user.QueryParams;
import com.logicalthining.endeshop.common.responseVo.user.UserVo;
import com.logicalthining.endeshop.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * 用户表
 *
 * @author chenLiJia
 * @since 2019-10-31 19:11:15
 **/
public interface UserServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-10-31 19:11:15
     **/
    Result add(User params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-10-31 19:11:15
     **/
    Result update(User params);

    /**
     * 条件查询
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.User>
     * @since 下午 7:32 2019/10/31 0031
     **/
    List<User> listByCondition(User condition);

    /**
     * 列表查询用户
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.user.UserVo>
     * @since 下午 7:45 2019/10/31 0031
     **/
    List<UserVo> listPage(QueryParams params);

    /**
     * 获取当前用户
     *
     * @return java.util.Optional<com.logicalthining.endeshop.entity.User>
     * @since 上午 11:13 2019/11/5 0005
     **/
    Optional<User> currentUser();

    /**
     * 主键查询
     * @since 下午 1:48 2019/11/7 0007
     * @param id 1
     * @return com.logicalthining.endeshop.entity.User
     **/
    User findById(Integer id);

    /**
     * 批量修改用户状态
     * @since 上午 11:48 2019/11/9 0009
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    Result batchUpdateState(BatchUpdateStateParams params);

}

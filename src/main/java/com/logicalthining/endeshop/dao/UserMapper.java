package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.common.requestVo.BatchUpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.UpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.user.QueryParams;
import com.logicalthining.endeshop.common.responseVo.user.UserVo;
import com.logicalthining.endeshop.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 用户表
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-10-31 19:10:44
 **/
public interface UserMapper extends Mapper<User> {

    /**
     * 列表查询用户
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.user.UserVo>
     * @since 下午 7:45 2019/10/31 0031
     **/
    List<UserVo> listPage(QueryParams params);

    /**
     * 根据用户Id集合查询用户
     *
     * @param idSet 1
     * @return java.util.List<com.logicalthining.endeshop.entity.User>
     * @since 下午 4:22 2019/11/7 0007
     **/
    List<User> listByIdSet(@Param("idSet") Set<Integer> idSet);

    /**
     * 批量修改用户状态
     *
     * @param params 1
     * @return java.lang.Integer
     * @since 上午 11:49 2019/11/9 0009
     **/
    Integer batchUpdateState(BatchUpdateStateParams params);

}

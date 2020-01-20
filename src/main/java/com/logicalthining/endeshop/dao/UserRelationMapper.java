package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.common.requestVo.user.ChildUserQueryParams;
import com.logicalthining.endeshop.common.responseVo.user.AppUserChildUserListVo;
import com.logicalthining.endeshop.common.responseVo.userRelation.UserChildCountVo;
import com.logicalthining.endeshop.entity.UserRelation;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 用户上下级关系表
 * @author chenLiJia
 * @since 2019-11-06 10:43:06
 * @version 1.0
 **/
public interface UserRelationMapper extends Mapper<UserRelation> {


    /**
     * 查找用户的直接下级数量
     * @since 下午 5:16 2019/11/13 0013
     * @param parentId 用户id
     * @return com.logicalthining.endeshop.common.responseVo.userRelation.UserChildCountVo
     **/
    UserChildCountVo findUserChildCountVo(@Param("parentId") Integer parentId);


    /**
     * 客户端查询直接下级列表
     * @since 下午 3:05 2019/11/14 0014
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.user.AppUserChildUserListVo>
     **/
    List<AppUserChildUserListVo> listAppUserChildUserListVo(ChildUserQueryParams params);

}

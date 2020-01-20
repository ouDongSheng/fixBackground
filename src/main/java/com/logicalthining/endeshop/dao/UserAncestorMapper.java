package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.common.responseVo.userRelation.UserChildCountVo;
import com.logicalthining.endeshop.entity.UserAncestor;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * 用户的上级,上上级,祖宗，全部关联起来,方便直接判断是
 * @author chenLiJia
 * @since 2019-11-11 17:02:21
 * @version 1.0
 **/
public interface UserAncestorMapper extends Mapper<UserAncestor> {

    /**
     * 查找用户的所有下级数量
     * @since 下午 5:16 2019/11/13 0013
     * @param parentId 用户id
     * @return com.logicalthining.endeshop.common.responseVo.userRelation.UserChildCountVo
     **/
    UserChildCountVo findUserChildCountVo(@Param("parentId") Integer parentId);

}

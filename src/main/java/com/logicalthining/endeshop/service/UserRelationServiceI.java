package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.common.requestVo.user.ChildUserQueryParams;
import com.logicalthining.endeshop.common.responseVo.user.AppUserChildUserListVo;
import com.logicalthining.endeshop.common.responseVo.userRelation.UserChildCountVo;
import com.logicalthining.endeshop.entity.User;
import com.logicalthining.endeshop.entity.UserRelation;

import java.util.List;

/**
 * 用户上下级关系表
 * 当用户绑定上下级关系的时候，
 * 需要在 {@link com.logicalthining.endeshop.entity.UserChildCount} 里面修改上级用户的下级数量
 * 注意，需要递归修改，知道找不到上级位置
 * 当用户删除上下级关系的时候，
 * 同样需要在 {@link com.logicalthining.endeshop.entity.UserChildCount} 里面修改上级用户的下级数量
 *
 * @author chenLiJia
 * @since 2019-11-06 10:43:18
 **/
public interface UserRelationServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 10:43:18
     **/
    Result add(UserRelation params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 10:43:18
     **/
    Result update(UserRelation params);

    /**
     * 查询用户的上下级关系
     *
     * @param userId 1
     * @return com.logicalthining.endeshop.entity.UserRelation
     * @since 上午 11:31 2019/11/6 0006
     **/
    UserRelation findByUserId(Integer userId);

    /**
     * 删除这个用户的上下级关系
     *
     * @param userId 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 7:22 2019/11/6 0006
     **/
    Result deleteByUserId(Integer userId);

    /**
     * 删除这个用户的上下级关系
     *
     * @param parentId 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 7:22 2019/11/6 0006
     **/
    Result deleteByUParentId(Integer parentId);


    /**
     * 递归修改上级的下级用户数量
     *
     * @param parentId  上级用户id
     * @param userCount 要增加或者减少的用户数量 如 1 表示增加一个用户 -1 表示减少一个用户
     * @param userId    用户id
     * @param userRole  用户角色  修改用户对应的角色的下级用户的数量,如果是用户升级的话,比如普通会员升级到会员,
     *                  会员升级到合伙人,这种情况需要另外处理,增加相应的角色数量,减少这个用户之前的据说的数量
     * @return void
     * @since 下午 3:37 2019/11/9 0009
     **/
    void recursiveUpdateParentChildCount(Integer parentId, Integer userCount, Integer userId, Integer userRole);

    /**
     * 递归修改上级的下级合伙人用户数量
     * 当下级升级为合伙人的时候进行调用 增加1
     * 判断他们之间之前的关系,如果之前就已经跟他绑定了关系,
     * 那么这个用户的普通用户数量或者会员数量要相应的减少
     *
     * @param parentId    上级用户id
     * @param userCount   要增加或者减少的用户数量 如 1 表示增加一个用户 -1 表示减少一个用户
     * @param userId      用户id
     * @param userPreRole 用户之前的角色
     * @return void
     * @since 下午 3:37 2019/11/9 0009
     **/
    void recursiveUpdateParentPartnerChildCount(Integer parentId, Integer userCount, Integer userId, Integer userPreRole);


    /**
     * 递归修改上级的下级会员用户数量
     * 当下级升级为会员的时候进行调用 增加1
     * 判断他们之间之前的关系,如果之前就已经跟他绑定了关系,
     * 那么这个用户的普通用户数量要相应的减少
     *
     * 注意,调用这个方法的前提的用户升级了会员，且用户与上级用户本来就绑定了关系
     * @param parentId  上级用户id
     * @param userCount 要增加或者减少的用户数量 如 1 表示增加一个用户 -1 表示减少一个用户
     * @param userId    用户id
     * @return void
     * @since 下午 3:37 2019/11/9 0009
     **/
    void recursiveUpdateParentVipChildCount(Integer parentId, Integer userCount, Integer userId);

    /**
     * 查找用户的直接下级数量
     * @since 下午 5:16 2019/11/13 0013
     * @param parentId 1
     * @return com.logicalthining.endeshop.common.responseVo.userRelation.UserChildCountVo
     **/
    UserChildCountVo findUserChildCountVo(Integer parentId);

    /**
     * 查找用户的所有下级数量
     * @since 下午 5:16 2019/11/13 0013
     * @param parentId 1
     * @return com.logicalthining.endeshop.common.responseVo.userRelation.UserChildCountVo
     **/
    UserChildCountVo findUserAllChildCountVo(Integer parentId);

    /**
     * 递归查找第一个合伙人祖宗
     * @since 下午 7:32 2019/11/13 0013
     * @param userId 1
     * @return com.logicalthining.endeshop.entity.User
     **/
    User findFirstPartnerAncestor(Integer userId);

    /**
     * 客户端查询直接下级列表
     * @since 下午 3:05 2019/11/14 0014
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.user.AppUserChildUserListVo>
     **/
    List<AppUserChildUserListVo> listAppUserChildUserListVo(ChildUserQueryParams params);

    /**
     * 校验并更新下级数量
     *
     * @param parentId
     */
    void checkAndUpdateChildCount(Integer parentId);

}

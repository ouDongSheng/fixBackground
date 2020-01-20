package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.list.Lists;
import com.logicalthining.endeshop.common.requestVo.user.ChildUserQueryParams;
import com.logicalthining.endeshop.common.responseVo.user.AppUserChildUserListVo;
import com.logicalthining.endeshop.common.responseVo.userRelation.UserChildCountVo;
import com.logicalthining.endeshop.dao.UserAncestorMapper;
import com.logicalthining.endeshop.dao.UserChildCountMapper;
import com.logicalthining.endeshop.dao.UserMapper;
import com.logicalthining.endeshop.dao.UserRelationMapper;
import com.logicalthining.endeshop.entity.User;
import com.logicalthining.endeshop.entity.UserAncestor;
import com.logicalthining.endeshop.entity.UserChildCount;
import com.logicalthining.endeshop.entity.UserRelation;
import com.logicalthining.endeshop.service.UserRelationServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 用户上下级关系表
 * <p>
 * 当用户绑定上下级关系的时候，
 * 需要在 {@link com.logicalthining.endeshop.entity.UserChildCount} 里面修改上级用户的下级数量
 * 注意，需要递归修改，知道找不到上级位置
 * 当用户删除上下级关系的时候，
 * 同样需要在 {@link com.logicalthining.endeshop.entity.UserChildCount} 里面修改上级用户的下级数量
 *
 * @author chenLiJia
 * @since 2019-11-06 10:43:18
 **/
@Service
public class UserRelationServiceImpl implements UserRelationServiceI {


    @Resource
    private UserRelationMapper userRelationMapper;//用户关系
    @Resource
    private UserChildCountMapper userChildCountMapper;//用户下级数量
    @Resource
    private UserAncestorMapper userAncestorMapper;//用户与祖宗上级的关系
    @Resource
    private UserMapper userMapper;//用户


    /**
     * 添加
     * 前置条件：用户还没有绑定过关系
     * 关系绑定的两个场景 1用户根据分享的链接下单并支付 2用户根据分享的链接申请合伙人并且通过
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 10:43:18
     **/
    public Result add(UserRelation params) {

        //自己不可以绑定自己
        if (Objects.equals(params.getParentUserId(), params.getUserId())) {
            return Result.failure("不允许自己绑定自己");
        }
        int i = userRelationMapper.insertSelective(params);
        if (i > 0) {
            User user = userMapper.selectByPrimaryKey(params.getUserId());
            //递归修改上级的下级数量 +1  并且给上级加上一个除了父子关系之外的跟下级的关联
            recursiveUpdateParentChildCount(params.getParentUserId(), 1, params.getUserId(), user.getRole());
        }
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }


    /**
     * 递归修改上级的下级用户数量
     * <p>
     * 递归维护用户与祖宗用户的关系 {@link com.logicalthining.endeshop.entity.UserAncestor}
     *
     * @param parentId  上级用户id
     * @param userCount 要增加或者减少的用户数量 如 1 表示增加一个用户 -1 表示减少一个用户
     * @param userId    用户id
     * @param userRole  用户角色  修改用户对应的角色的下级用户的数量,如果是用户升级的话,比如普通会员升级到会员,
     *                  会员升级到合伙人,这种情况需要另外处理,增加相应的角色数量,减少这个用户之前的据说的数量
     * @return void
     * @since 下午 3:37 2019/11/9 0009
     **/
    @Override
    public void recursiveUpdateParentChildCount(Integer parentId, Integer userCount, Integer userId, Integer userRole) {

        //维护用户与祖宗用户的关系
        if (userCount > 0) {
            //先判断他与祖宗是否已有关系
            List<UserAncestor> select = userAncestorMapper.select(new UserAncestor().setUserId(userId).setAncesterId(parentId));
            if (Lists.isEmpty(select)) {
                //增加关系
                UserAncestor userAncestor = new UserAncestor();
                userAncestor.setUserId(userId);
                userAncestor.setAncesterId(parentId);
                userAncestor.setCreateTime(new Date());
                userAncestorMapper.insertSelective(userAncestor);
            }
        } else if (userCount < 0) {
            //删除关系
            UserAncestor userAncestorCondition = new UserAncestor();
            userAncestorCondition.setUserId(userId);
            userAncestorCondition.setAncesterId(parentId);
            userAncestorMapper.delete(userAncestorCondition);
        }

        //校验并更新下级数量
        checkAndUpdateChildCount(parentId);

        //判断上级有没有上级
        UserRelation userRelation = userRelationMapper.selectByPrimaryKey(parentId);
        if (Objects.nonNull(userRelation)) {
            recursiveUpdateParentChildCount(userRelation.getParentUserId(), userCount, userId, userRole);
        }
    }

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
    @Override
    public void recursiveUpdateParentPartnerChildCount(Integer parentId, Integer userCount, Integer userId, Integer userPreRole) {
        //校验并更新下级数量
        checkAndUpdateChildCount(parentId);

        //判断上级有没有上级
        UserRelation userRelation = userRelationMapper.selectByPrimaryKey(parentId);
        if (Objects.nonNull(userRelation)) {
            recursiveUpdateParentChildCount(userRelation.getParentUserId(), userCount, userId, userPreRole);
        }
    }

    /**
     * 递归修改上级的下级会员用户数量
     * 当下级升级为会员的时候进行调用 增加1
     * 判断他们之间之前的关系,如果之前就已经跟他绑定了关系,
     * 那么这个用户的普通用户数量要相应的减少
     * <p>
     * 注意,调用这个方法的前提的用户升级了会员，且用户与上级用户本来就绑定了关系
     *
     * @param parentId  上级用户id
     * @param userCount 要增加或者减少的用户数量 如 1 表示增加一个用户 -1 表示减少一个用户
     * @param userId    用户id
     * @return void
     * @since 下午 3:37 2019/11/9 0009
     **/
    @Override
    public void recursiveUpdateParentVipChildCount(Integer parentId, Integer userCount, Integer userId) {
        //校验并更新下级数量
        checkAndUpdateChildCount(parentId);

        //判断上级有没有上级
        UserRelation userRelation = userRelationMapper.selectByPrimaryKey(parentId);
        if (Objects.nonNull(userRelation)) {
            recursiveUpdateParentVipChildCount(userRelation.getParentUserId(), userCount, userId);
        }
    }

    /**
     * 查找用户的直接下级数量
     *
     * @param parentId 1
     * @return com.logicalthining.endeshop.common.responseVo.userRelation.UserChildCountVo
     * @since 下午 5:16 2019/11/13 0013
     **/
    @Override
    public UserChildCountVo findUserChildCountVo(Integer parentId) {
        if (Objects.nonNull(parentId)) {
            return userRelationMapper.findUserChildCountVo(parentId);
        }
        return null;
    }

    /**
     * 查找用户的所有下级数量
     * 顺便更新一下下级的数量
     *
     * @param parentId 1
     * @return com.logicalthining.endeshop.common.responseVo.userRelation.UserChildCountVo
     * @since 下午 5:16 2019/11/13 0013
     **/
    @Override
    public UserChildCountVo findUserAllChildCountVo(Integer parentId) {
        if (Objects.nonNull(parentId)) {
            return userAncestorMapper.findUserChildCountVo(parentId);
        }
        return null;
    }

    /**
     * 校验并更新下级数量
     *
     * @param parentId
     */
    public void checkAndUpdateChildCount(Integer parentId) {
        UserChildCountVo userChildCountVo = userAncestorMapper.findUserChildCountVo(parentId);
        if (null == userChildCountVo) {
            userChildCountVo = new UserChildCountVo();
        }
        //防止没有查询出信息
        userChildCountVo.setUserId(parentId);

        UserChildCount userChildCount = findUserChildCountByUserId(parentId);
        userChildCount.setCommonUserCount(userChildCountVo.getTeamCommonUserCount());
        userChildCount.setVipUserCount(userChildCountVo.getTeamVIPUserCount());
        userChildCount.setPartnerChildCount(userChildCountVo.getTeamPartnerUserCount());
        userChildCount.setChildCount(userChildCountVo.getTeamCommonUserCount() +
                userChildCountVo.getTeamVIPUserCount() + userChildCountVo.getTeamPartnerUserCount());
        userChildCountMapper.updateByPrimaryKeySelective(userChildCount);
    }


    /**
     * 递归查找第一个合伙人祖宗
     *
     * @param userId 1
     * @return com.logicalthining.endeshop.entity.User
     * @since 下午 7:32 2019/11/13 0013
     **/
    @Override
    public User findFirstPartnerAncestor(Integer userId) {

        if (Objects.nonNull(userId)) {
            UserRelation userRelation = userRelationMapper.selectByPrimaryKey(userId);
            if (Objects.nonNull(userRelation)) {
                //判断上级是不是合伙人
                User user = userMapper.selectByPrimaryKey(userRelation.getParentUserId());
                if (Objects.nonNull(user)) {
                    if (Objects.equals(user.getRole(), 3)) {
                        return user;
                    }

                    //上级不是合伙人,递归往上找
                    return findFirstPartnerAncestor(userRelation.getParentUserId());
                }
            }
        }
        return null;
    }

    /**
     * 客户端查询直接下级列表
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.user.AppUserChildUserListVo>
     * @since 下午 3:05 2019/11/14 0014
     **/
    @Override
    public List<AppUserChildUserListVo> listAppUserChildUserListVo(ChildUserQueryParams params) {
        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        List<AppUserChildUserListVo> list = userRelationMapper.listAppUserChildUserListVo(params);
        return list;
    }


    /**
     * 查找用户当前下级数量
     *
     * @param userId 1
     * @return com.logicalthining.endeshop.entity.UserChildCountVo
     * @since 下午 3:35 2019/11/9 0009
     **/
    private UserChildCount findUserChildCountByUserId(Integer userId) {
        UserChildCount userChildCount = userChildCountMapper.selectByPrimaryKey(userId);
        if (Objects.isNull(userChildCount)) {
            userChildCount = new UserChildCount();
            userChildCount.setUserId(userId);
            userChildCount.setChildCount(0);
            userChildCount.setPartnerChildCount(0);
            userChildCount.setCommonUserCount(0);
            userChildCount.setVipUserCount(0);
            userChildCountMapper.insertSelective(userChildCount);
        }
        return userChildCount;
    }


    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 10:43:18
     **/
    public Result update(UserRelation params) {

        int i = userRelationMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询用户的上下级关系
     *
     * @param userId 1
     * @return com.logicalthining.endeshop.entity.UserRelation
     * @since 上午 11:31 2019/11/6 0006
     **/
    @Override
    public UserRelation findByUserId(Integer userId) {
        if (Objects.nonNull(userId)) {
            return userRelationMapper.selectByPrimaryKey(userId);
        }
        return null;
    }

    /**
     * 删除这个用户的上下级关系
     *
     * @param userId 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 7:22 2019/11/6 0006
     **/
    @Override
    public Result deleteByUserId(Integer userId) {
        if (Objects.nonNull(userId)) {
            UserRelation userRelation = userRelationMapper.selectByPrimaryKey(userId);
            if (Objects.nonNull(userRelation)) {
                userRelationMapper.deleteByPrimaryKey(userId);
                //递归减少该用户的上级用户的下级数量
                User user = userMapper.selectByPrimaryKey(userId);
                recursiveUpdateParentChildCount(userRelation.getParentUserId(), -1, userId, user.getRole());
            }
        }
        return Result.success("操作成功");
    }

    /**
     * 删除这个用户的上下级关系
     * 删除这个用户的下级关系
     * 这个用户都已经删除了,所以就不用维护他的下级数量了
     * 只需要{@link UserRelation} 和{@link UserAncestor} 中跟他有关的都删了即可
     *
     * @param parentId 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 7:22 2019/11/6 0006
     **/
    @Override
    public Result deleteByUParentId(Integer parentId) {
        //先查询有没有关系存在
        if (Objects.nonNull(parentId)) {
            UserRelation userRelationCondition = new UserRelation().setParentUserId(parentId);
            userRelationMapper.delete(userRelationCondition);
            UserAncestor userAncestorCondition = new UserAncestor().setAncesterId(parentId);
            userAncestorMapper.delete(userAncestorCondition);
        }
        return Result.success("操作成功");
    }


}

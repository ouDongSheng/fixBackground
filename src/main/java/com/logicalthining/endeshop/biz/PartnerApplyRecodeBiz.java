package com.logicalthining.endeshop.biz;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.logicalthining.endeshop.common.enums.UserRole;
import com.logicalthining.endeshop.common.requestVo.BatchUpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.partnerApply.AddParams;
import com.logicalthining.endeshop.common.requestVo.partnerApply.QueryParams;
import com.logicalthining.endeshop.common.responseVo.partnerApply.PartnerApplyVo;
import com.logicalthining.endeshop.entity.*;
import com.logicalthining.endeshop.service.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 合伙人申请记录
 *
 * @author chenLiJia
 * @since 2019-11-05 19:59:13
 **/
@Service
public class PartnerApplyRecodeBiz {

    @Autowired
    private PartnerApplyRecodeServiceI partnerApplyRecodeService;//合伙人
    @Autowired
    private UserServiceI userService;//用户
    @Autowired
    private AdminServiceI adminService;//后台用户
    @Autowired
    private PartnerInfoServiceI partnerInfoService;//合伙人信息
    @Autowired
    private PartnerApplyShareUserServiceI partnerApplyShareUserService;//合伙人申请 分享人
    @Autowired
    private UserRelationServiceI userRelationService;//用户上下级关系
    @Autowired
    private UserChildCountServiceI userChildCountService;//用户下级用户数量


    /**
     * 合伙人申请参数
     * 只有周二才可以提现
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 8:58 2019/11/6 0006
     **/
    public Result add(AddParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //当前用户
        Optional<User> optional = userService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        User currentUser = optional.get();

        if (Objects.equals(UserRole.PARTNER_USER.getRole(), currentUser.getRole())) {
            return Result.failure("当前用户已经是合伙人,无需申请");
        }

        PartnerApplyRecode partnerApplyRecode = new PartnerApplyRecode().setUserId(currentUser.getId()).
                setPartnerName(params.getPartnerName()).
                setTelephone(params.getTelephone()).
                setPartnerBankCard(params.getPartnerBankCard()).
                setIdCardImageFront(params.getIdCardImageFront()).
                setIdCardImageBack(params.getIdCardImageBack()).
                setApplyTime(new Date()).setReviewStatus(0);

        Result add = partnerApplyRecodeService.add(partnerApplyRecode);
        if (add.getSuccess()) {
            //如果是分享过来的话,需要记录分享人
            if (Objects.nonNull(params.getShareUserId())) {
                PartnerApplyShareUser partnerApplyShareUser = new PartnerApplyShareUser().
                        setPartnerApplyId(partnerApplyRecode.getId()).
                        setShareUserId(params.getShareUserId());

                partnerApplyShareUserService.add(partnerApplyShareUser);
            }
        }


        return add;
    }


    /**
     * 查询合伙人申请列表
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:41 2019/11/6 0006
     **/
    public Result listPage(QueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<PartnerApplyVo> list = partnerApplyRecodeService.listPartnerApplyVo(params);
        PageInfo<PartnerApplyVo> pageInfo = new PageInfo<>(list);
        return Result.success("查询成功", pageInfo);
    }

    /**
     * 同意
     *
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:41 2019/11/6 0006
     **/
    public Result agree(Integer id) {

        //校验参数
        if (Objects.isNull(id)) {
            return Result.failure("id为空");
        }

        //当前用户
        Optional<Admin> optional = adminService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        Admin currentAdmin = optional.get();

        //判断数据是否存在
        PartnerApplyRecode condition = new PartnerApplyRecode().setId(id);
        List<PartnerApplyRecode> partnerApplyRecodes = partnerApplyRecodeService.listByCondition(condition);
        if (Lists.isEmpty(partnerApplyRecodes)) {
            return Result.failure("数据不存在");
        }

        PartnerApplyRecode partnerApplyRecode = partnerApplyRecodes.get(0);

        if (!Objects.equals(0, partnerApplyRecode.getReviewStatus())) {
            return Result.failure("操作失败,该申请已审核");
        }

        partnerApplyRecode.setReviewStatus(1);
        partnerApplyRecode.setReviewAdmin(currentAdmin.getId());
        partnerApplyRecode.setReviewTime(new Date());

        Result update = partnerApplyRecodeService.update(partnerApplyRecode);

        if (update.getSuccess()) {
            //审核通过
            //添加合伙人信息到数据库中
            PartnerInfo partnerInfo = new PartnerInfo().setUserId(partnerApplyRecode.getUserId()).
                    setPartnerName(partnerApplyRecode.getPartnerName()).
                    setTelephone(partnerApplyRecode.getTelephone()).
                    setPartnerBankCard(partnerApplyRecode.getPartnerBankCard()).
                    setIdCardImageFront(partnerApplyRecode.getIdCardImageFront()).
                    setIdCardImageBack(partnerApplyRecode.getIdCardImageBack()).setCreateTime(new Date());

            //判断是不是已经有合伙人信息了(即之前已经审核通过了一次,现在又通过,如果有,就做变更操作)
            PartnerInfo partnerInfoCondition = new PartnerInfo().setUserId(partnerApplyRecode.getUserId());
            List<PartnerInfo> partnerInfos = partnerInfoService.listByCondition(partnerInfoCondition);
            if (Lists.isEmpty(partnerInfos)) {
                partnerInfoService.add(partnerInfo);
            } else {
                partnerInfoService.update(partnerInfo);
            }

            //申请人信息
            //修改申请人角色
            User userCondition = new User().setId(partnerApplyRecode.getUserId());
            List<User> users = userService.listByCondition(userCondition);
            if (Lists.isNotEmpty(users)) {
                User user = users.get(0);
                //用户之前的角色
                Integer userPreRole = user.getRole();
                user.setRole(3);
                userService.update(user);
                //判断是否分享过来的申请,如果是分享过来的申请,需要绑定上下级关系
                PartnerApplyShareUser partnerApplyShareUser = partnerApplyShareUserService.findByApplyId(id);
                //用户上下级关系
                UserRelation userRelation = userRelationService.findByUserId(user.getId());
                //如果是分享过来的申请 且该用户没有绑定过上下级关系 > 进行绑定上下级关系
                if (Objects.nonNull(partnerApplyShareUser) && Objects.isNull(userRelation)) {
                    userRelation = new UserRelation().
                            setUserId(user.getId()).
                            setParentUserId(partnerApplyShareUser.getShareUserId()).
                            setCreateTime(new Date());

                    userRelationService.add(userRelation);
                }


                //判断这个用户有没有上级,用户升级为合伙人之后,上级只能是合伙人,如果不是合伙人,解除上下级关系
                userRelation = userRelationService.findByUserId(user.getId());
                if (Objects.nonNull(userRelation)) {
                    Integer parentUserId = userRelation.getParentUserId();
                    List<User> parentUserList = userService.listByCondition(new User().setId(parentUserId));
                    if (Lists.isNotEmpty(parentUserList)) {
                        User parentUser = parentUserList.get(0);
                        if (!Objects.equals(UserRole.PARTNER_USER.getRole(), parentUser.getRole())) {
                            //解除关系
                            userRelationService.deleteByUserId(user.getId());
                        } else {
                            //说明这个用户的上级是合伙人
                            //增加这个用户的下级合伙人数量
                            userRelationService.recursiveUpdateParentPartnerChildCount(parentUserId, 1, user.getId(), userPreRole);
                        }
                    }
                }

            }


        }

        return update;
    }


    /**
     * 拒绝
     *
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:41 2019/11/6 0006
     **/
    public Result deny(Integer id) {
        //校验参数
        if (Objects.isNull(id)) {
            return Result.failure("id为空");
        }

        //当前用户
        Optional<Admin> optional = adminService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        Admin currentAdmin = optional.get();

        //判断数据是否存在
        PartnerApplyRecode condition = new PartnerApplyRecode().setId(id);
        List<PartnerApplyRecode> partnerApplyRecodes = partnerApplyRecodeService.listByCondition(condition);
        if (Lists.isEmpty(partnerApplyRecodes)) {
            return Result.failure("数据不存在");
        }

        PartnerApplyRecode partnerApplyRecode = partnerApplyRecodes.get(0);

        if (!Objects.equals(0, partnerApplyRecode.getReviewStatus())) {
            return Result.failure("操作失败,该申请已审核");
        }

        partnerApplyRecode.setReviewStatus(2);
        partnerApplyRecode.setReviewAdmin(currentAdmin.getId());
        partnerApplyRecode.setReviewTime(new Date());

        return partnerApplyRecodeService.update(partnerApplyRecode);
    }

    /**
     * 批量审核
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 1:48 2019/11/9 0009
     **/
    @Transactional
    public Result batchReview(BatchUpdateStateParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        List<Integer> idList = params.getIdList();

        if (Objects.equals(BooleanConstant.YES_INTEGER, params.getState())) {
            //批量同意
            for (Integer id : idList) {
                Result agree = agree(id);
                if (!agree.getSuccess()) {
                    //回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return agree;
                }
            }
        } else {
            //批量拒绝
            for (Integer id : idList) {
                Result deny = deny(id);
                if (!deny.getSuccess()) {
                    //回滚
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return deny;
                }
            }
        }

        return Result.success("操作成功");
    }

}

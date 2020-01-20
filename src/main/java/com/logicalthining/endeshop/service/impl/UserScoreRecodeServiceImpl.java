package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.logicalthining.endeshop.common.requestVo.userScore.AppUserScoreRecodeQueryParams;
import com.logicalthining.endeshop.common.requestVo.userScore.UserScoreQueryParams;
import com.logicalthining.endeshop.common.requestVo.userScore.UserScoreRecodeQueryParams;
import com.logicalthining.endeshop.common.responseVo.userScore.AdminUserScoreListVo;
import com.logicalthining.endeshop.common.responseVo.userScore.AppScoreRecodeVo;
import com.logicalthining.endeshop.common.responseVo.userScore.UserScoreRecodeVo;
import com.logicalthining.endeshop.dao.UserScoreRecodeMapper;
import com.logicalthining.endeshop.entity.UserScoreRecode;
import com.logicalthining.endeshop.service.UserScoreRecodeServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 用户积分记录
 *
 * @author chenLiJia
 * @since 2019-11-07 13:40:17
 **/
@Service
public class UserScoreRecodeServiceImpl implements UserScoreRecodeServiceI {


    @Resource
    private UserScoreRecodeMapper userScoreRecodeMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-07 13:40:17
     **/
    public Result add(UserScoreRecode params) {

        int i = userScoreRecodeMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-07 13:40:17
     **/
    public Result update(UserScoreRecode params) {

        int i = userScoreRecodeMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 获取用户当前积分
     *
     * @param userId 用户id
     * @return java.lang.Double
     * @since 下午 1:43 2019/11/7 0007
     **/
    @Override
    public Double findUserScore(Integer userId) {
        if (Objects.nonNull(userId)) {
            Double userScore = userScoreRecodeMapper.findUserScore(userId);
            return Objects.isNull(userScore) ? 0.0 : userScore;
        }
        return null;
    }

    /**
     * 后台查询用户积分记录
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.userScore.AdminUserScoreListVo>
     * @since 上午 10:40 2019/11/8 0008
     **/
    @Override
    public List<AdminUserScoreListVo> listAdminUserScoreListVo(UserScoreQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        List<AdminUserScoreListVo> list = userScoreRecodeMapper.listAdminUserScoreListVo(params);
        return list;
    }

    /**
     * 积分池-详情-团队积分详情-历史收益记录
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.userScore.UserScoreRecodeVo>
     * @since 下午 12:00 2019/11/12 0012
     **/
    @Override
    public List<UserScoreRecodeVo> listUserScoreRecodeVo(UserScoreRecodeQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        List<UserScoreRecodeVo> list = userScoreRecodeMapper.listUserScoreRecodeVo(params);
        return list;
    }

    /**
     * 客户端查询积分列表
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.userScore.AppScoreRecodeVo>
     * @since 下午 2:05 2019/11/20 0020
     **/
    @Override
    public List<AppScoreRecodeVo> listAppScoreRecodeVo(AppUserScoreRecodeQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        List<AppScoreRecodeVo> list = userScoreRecodeMapper.listAppScoreRecodeVo(params);
        return list;
    }

    /**
     * 条件查询
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.UserScoreRecode>
     * @since 下午 2:21 2019/12/12 0012
     **/
    @Override
    public List<UserScoreRecode> listByCondition(UserScoreRecode condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition);
        return userScoreRecodeMapper.select(condition);
    }

    /**
     * 主键删除
     *
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:30 2019/12/12 0012
     **/
    @Override
    public Result deleteById(Integer id) {
        if (Objects.nonNull(id)) {
            userScoreRecodeMapper.deleteByPrimaryKey(id);
        }
        return Result.success("操作成功");
    }


}

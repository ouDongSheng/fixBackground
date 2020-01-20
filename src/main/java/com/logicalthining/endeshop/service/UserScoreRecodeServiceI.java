package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.common.requestVo.userScore.AppUserScoreRecodeQueryParams;
import com.logicalthining.endeshop.common.requestVo.userScore.UserScoreQueryParams;
import com.logicalthining.endeshop.common.requestVo.userScore.UserScoreRecodeQueryParams;
import com.logicalthining.endeshop.common.responseVo.userScore.AdminUserScoreListVo;
import com.logicalthining.endeshop.common.responseVo.userScore.AppScoreRecodeVo;
import com.logicalthining.endeshop.common.responseVo.userScore.UserScoreRecodeVo;
import com.logicalthining.endeshop.entity.UserScoreRecode;

import java.util.List;

/**
 * 用户积分记录
 *
 * @author chenLiJia
 * @since 2019-11-07 13:40:17
 **/
public interface UserScoreRecodeServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-07 13:40:17
     **/
    Result add(UserScoreRecode params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-07 13:40:17
     **/
    Result update(UserScoreRecode params);

    /**
     * 获取用户当前积分
     *
     * @param userId 用户id
     * @return java.lang.Double
     * @since 下午 1:43 2019/11/7 0007
     **/
    Double findUserScore(Integer userId);

    /**
     * 后台查询用户积分记录
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.userScore.AdminUserScoreListVo>
     * @since 上午 10:40 2019/11/8 0008
     **/
    List<AdminUserScoreListVo> listAdminUserScoreListVo(UserScoreQueryParams params);

    /**
     * 积分池-详情-团队积分详情-历史收益记录
     * @since 下午 12:00 2019/11/12 0012
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.userScore.UserScoreRecodeVo>
     **/
    List<UserScoreRecodeVo> listUserScoreRecodeVo(UserScoreRecodeQueryParams params);

    /**
     * 客户端查询积分列表
     * @since 下午 2:05 2019/11/20 0020
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.userScore.AppScoreRecodeVo>
     **/
    List<AppScoreRecodeVo> listAppScoreRecodeVo(AppUserScoreRecodeQueryParams params);

    /**
     * 条件查询
     * @since 下午 2:21 2019/12/12 0012
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.UserScoreRecode>
     **/
    List<UserScoreRecode> listByCondition(UserScoreRecode condition);

    /**
     * 主键删除
     * @since 下午 2:30 2019/12/12 0012
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    Result deleteById(Integer id);

}

package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.common.requestVo.userScore.AppUserScoreRecodeQueryParams;
import com.logicalthining.endeshop.common.requestVo.userScore.UserScoreQueryParams;
import com.logicalthining.endeshop.common.requestVo.userScore.UserScoreRecodeQueryParams;
import com.logicalthining.endeshop.common.responseVo.userScore.AdminUserScoreListVo;
import com.logicalthining.endeshop.common.responseVo.userScore.AppScoreRecodeVo;
import com.logicalthining.endeshop.common.responseVo.userScore.UserScoreRecodeVo;
import com.logicalthining.endeshop.entity.UserScoreRecode;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 用户积分记录
 * @author chenLiJia
 * @since 2019-11-07 13:41:29
 * @version 1.0
 **/
public interface UserScoreRecodeMapper extends Mapper<UserScoreRecode> {

    /**
     * 获取用户当前积分
     * @since 下午 1:43 2019/11/7 0007
     * @param userId 用户id
     * @return java.lang.Double
     **/
    Double findUserScore(@Param("userId") Integer userId);

    /**
     * 后台查询积分记录
     * @since 上午 10:44 2019/11/8 0008
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.userScore.AdminUserScoreListVo>
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

}

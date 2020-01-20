package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.common.pojo.systemConfig.AwardPoolConfig;
import com.logicalthining.endeshop.common.responseVo.userScore.AwardPoolTeamCount;
import com.logicalthining.endeshop.common.responseVo.userScore.ScorePoolInfoVo;
import com.logicalthining.endeshop.common.responseVo.userScore.ScorePoolVo;
import com.logicalthining.endeshop.entity.UserPerformanceCount;
import com.logicalthining.endeshop.entity.UserPerformanceTotal;

import java.util.Date;
import java.util.List;

/**
 * 用户业绩季度统计信息
 *
 * @author chenLiJia
 * @since 2019-11-09 15:23:29
 **/
public interface UserPerformanceCountServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-09 15:23:29
     **/
    Result add(UserPerformanceCount params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-09 15:23:29
     **/
    Result update(UserPerformanceCount params);


    /**
     * 递归增加用户以及用户的上级的业绩统计信息
     *
     * @param userId          用户id
     * @param payAble         金额
     * @param orderCreateTime 订单创建时间
     * @param groupId         组订单id
     * @return void
     * @since 下午 3:52 2019/11/9 0009
     **/
    void recursiveAddUserPerformance(Integer userId, Double payAble, Date orderCreateTime, String groupId);

    /**
     * 查询积分池列表
     * 积分池需要满足有至少两个直接下级是合伙人
     *
     * @param searchKey        搜索条件 合伙人手机号
     * @param quarter          季度 2019-1
     * @param ratio            积分比例
     * @param awardPoolConfigs 系统积分池设置信息
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.userScore.ScorePoolVo>
     * @since 下午 4:52 2019/11/9 0009
     **/
    List<ScorePoolVo> listScorePoolVo(String searchKey, String quarter, Double ratio, List<AwardPoolConfig> awardPoolConfigs);

    /**
     * 搜索积分池-详情-团队积分列表
     * 因为这里需要展示积分比例，所以也需要直接下级有两个以上的合伙人
     * 包括自己
     *
     * @param ancestorId 祖宗id
     * @param searchKey  搜索条件 手机号
     * @param quarter    季度 2019-1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.userScore.ScorePoolInfoVo>
     * @since 上午 9:48 2019/11/12 0012
     **/
    List<ScorePoolInfoVo> listScorePoolInfoVo(Integer ancestorId, String searchKey, String quarter);

    /**
     * 列出还没有结算的合伙人的业绩
     *
     * @param quarter 季度
     * @return java.util.List<com.logicalthining.endeshop.entity.UserPerformanceCount>
     * @since 下午 1:44 2019/11/12 0012
     **/
    List<UserPerformanceCount> listNotArchivePerformance(String quarter);


    /**
     * 根据用户id以及季度查询用户业绩信息
     *
     * @param userId  用户id
     * @param quarter 季度
     * @return com.logicalthining.endeshop.entity.UserPerformanceCount
     * @since 下午 3:55 2019/11/9 0009
     **/
    UserPerformanceCount findUserPerformanceWithQuarter(Integer userId, String quarter);

    /**
     * 查询奖金池每个层级对应的团队数量
     *
     * @param list 表示查询的层级
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.userScore.AwardPoolTeamCount>
     * @since 下午 7:44 2019/11/18 0018
     **/
    List<AwardPoolTeamCount> listAwardPoolTeamCount(List<AwardPoolConfig> list);

    /**
     * 根据用户id查询用户累计业绩
     *
     * @param userId 1
     * @return com.logicalthining.endeshop.entity.UserPerformanceTotal
     * @since 上午 10:21 2019/11/12 0012
     **/
    UserPerformanceTotal findUserPerformanceTotalByUserId(Integer userId);

}

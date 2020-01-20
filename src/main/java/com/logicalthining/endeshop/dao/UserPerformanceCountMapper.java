package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.common.responseVo.userScore.ScorePoolInfoVo;
import com.logicalthining.endeshop.common.responseVo.userScore.ScorePoolVo;
import com.logicalthining.endeshop.entity.UserPerformanceCount;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 用户业绩季度统计信息
 *
 * @author chenLiJia
 * @version 1.0
 * @since 2019-11-09 15:23:12
 **/
public interface UserPerformanceCountMapper extends Mapper<UserPerformanceCount> {


    /**
     * 查询用户的总业绩
     *
     * @param userId 1
     * @return java.lang.Double
     * @since 下午 5:19 2019/11/9 0009
     **/
    Double historyMoney(@Param("userId") Integer userId);


    /**
     * 查询积分池列表
     * 积分池需要满足有至少两个直接下级是合伙人
     *
     * @param searchKey       搜索条件 合伙人手机号
     * @param quarter         季度 2019-1
     * @param startLimitMoney 季度业绩边界金额
     * @param endLimitMoney   季度业绩边界金额
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.userScore.ScorePoolVo>
     * @since 下午 4:52 2019/11/9 0009
     **/
    List<ScorePoolVo> listScorePoolVo(@Param("searchKey") String searchKey, @Param("quarter") String quarter,
                                      @Param("startLimitMoney") Double startLimitMoney, @Param("endLimitMoney") Double endLimitMoney);


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
    List<ScorePoolInfoVo> listScorePoolInfoVo(@Param("ancestorId") Integer ancestorId, @Param("searchKey") String searchKey,
                                              @Param("quarter") String quarter);

    /**
     * 列出还没有结算的合伙人的业绩
     *
     * @param quarter 季度
     * @return java.util.List<com.logicalthining.endeshop.entity.UserPerformanceCount>
     * @since 下午 1:44 2019/11/12 0012
     **/
    List<UserPerformanceCount> listNotArchivePerformance(@Param("quarter") String quarter);


    /**
     * 根据业绩区间查询符合条件的团队数量
     * 前提，需要是由两个直接下级合伙人的合伙人才可以
     *
     * @param limitStart 业绩区间
     * @param limitEnd   业绩区间
     * @param quarter    季度
     * @return java.lang.Integer
     * @since 下午 7:46 2019/11/18 0018
     **/
    Integer findTeamCountByPerformanceRange(@Param("limitStart") Double limitStart, @Param("limitEnd") Double limitEnd,
                                            @Param("quarter") String quarter);

}

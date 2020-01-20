package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.list.Lists;
import com.logicalthining.endeshop.common.pojo.systemConfig.AwardPoolConfig;
import com.logicalthining.endeshop.common.responseVo.userScore.AwardPoolTeamCount;
import com.logicalthining.endeshop.common.responseVo.userScore.ScorePoolInfoVo;
import com.logicalthining.endeshop.common.responseVo.userScore.ScorePoolVo;
import com.logicalthining.endeshop.dao.UserPerformanceCountMapper;
import com.logicalthining.endeshop.dao.UserPerformanceHistoryMapper;
import com.logicalthining.endeshop.dao.UserPerformanceTotalMapper;
import com.logicalthining.endeshop.dao.UserRelationMapper;
import com.logicalthining.endeshop.entity.UserPerformanceCount;
import com.logicalthining.endeshop.entity.UserPerformanceHistory;
import com.logicalthining.endeshop.entity.UserPerformanceTotal;
import com.logicalthining.endeshop.entity.UserRelation;
import com.logicalthining.endeshop.service.UserPerformanceCountServiceI;
import com.logicalthining.endeshop.util.QuarterTimeTransferUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户业绩季度统计信息
 *
 * @author chenLiJia
 * @since 2019-11-09 15:23:29
 **/
@Service
public class UserPerformanceCountServiceImpl implements UserPerformanceCountServiceI {


    @Resource
    private UserPerformanceCountMapper userPerformanceCountMapper;//用户业绩统计
    @Resource
    private UserRelationMapper userRelationMapper;//用户关系
    @Resource
    private UserPerformanceTotalMapper userPerformanceTotalMapper;//用户累计业绩
    @Resource
    private UserPerformanceHistoryMapper userPerformanceHistoryMapper;//用户业绩记录


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-09 15:23:29
     **/
    public Result add(UserPerformanceCount params) {

        int i = userPerformanceCountMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-09 15:23:29
     **/
    public Result update(UserPerformanceCount params) {

        int i = userPerformanceCountMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }


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
    @Override
    public void recursiveAddUserPerformance(Integer userId, Double payAble, Date orderCreateTime, String groupId) {
        //将时间转化为业绩字符串
        //季度
        String quarter = QuarterTimeTransferUtil.transferDateToQuarter(orderCreateTime);
        UserPerformanceCount userPerformanceWithQuarter = findUserPerformanceWithQuarter(userId, quarter);

        //查询当前用户的总业绩
        Double historyMoney = userPerformanceCountMapper.historyMoney(userId);
        historyMoney = Objects.isNull(historyMoney) ? 0 : historyMoney;
        //历史业绩
        historyMoney = historyMoney + payAble;

        //增加业绩
        userPerformanceWithQuarter.setCurrentQuarterMoney(userPerformanceWithQuarter.getCurrentQuarterMoney() + payAble);
        userPerformanceWithQuarter.setHistoryMoney(historyMoney);
        userPerformanceWithQuarter.setUpdateTime(new Date());
        userPerformanceCountMapper.updateByPrimaryKeySelective(userPerformanceWithQuarter);

        //修改用户的累计业绩
        UserPerformanceTotal userPerformanceTotal = findUserPerformanceTotalByUserId(userId);
        userPerformanceTotal.setGrandTotalPerformance(userPerformanceTotal.getGrandTotalPerformance() + payAble);
        userPerformanceTotal.setUpdateTime(new Date());
        userPerformanceTotalMapper.updateByPrimaryKeySelective(userPerformanceTotal);

        //用户业绩记录
        UserPerformanceHistory userPerformanceHistory = new UserPerformanceHistory();
        userPerformanceHistory.setUserId(userId);
        userPerformanceHistory.setRelationItems(groupId);
        userPerformanceHistory.setTransactionMoney(payAble);
        userPerformanceHistory.setCurrentQuarter(quarter);
        userPerformanceHistory.setCurrentQuarterMoney(userPerformanceWithQuarter.getCurrentQuarterMoney());
        userPerformanceHistory.setCurrentMoney(userPerformanceTotal.getGrandTotalPerformance());
        userPerformanceHistory.setCreateTime(orderCreateTime);
        userPerformanceHistoryMapper.insertSelective(userPerformanceHistory);


        //判断他有没有上级,递归给上级也增加业绩
        UserRelation userRelation = userRelationMapper.selectByPrimaryKey(userId);
        if (Objects.nonNull(userRelation)) {
            recursiveAddUserPerformance(userRelation.getParentUserId(), payAble, orderCreateTime, groupId);
        }
    }


    /**
     * 根据用户id查询用户累计业绩
     *
     * @param userId 1
     * @return com.logicalthining.endeshop.entity.UserPerformanceTotal
     * @since 上午 10:21 2019/11/12 0012
     **/
    @Override
    public UserPerformanceTotal findUserPerformanceTotalByUserId(Integer userId) {
        if (Objects.nonNull(userId)) {
            UserPerformanceTotal userPerformanceTotal = userPerformanceTotalMapper.selectByPrimaryKey(userId);
            if (Objects.isNull(userPerformanceTotal)) {
                userPerformanceTotal = new UserPerformanceTotal();
                userPerformanceTotal.setUserId(userId);
                userPerformanceTotal.setGrandTotalPerformance(0.0);
                userPerformanceTotal.setUpdateTime(new Date());
                userPerformanceTotalMapper.insertSelective(userPerformanceTotal);
            }
            return userPerformanceTotal;
        }
        return null;
    }

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
    @Override
    public List<ScorePoolVo> listScorePoolVo(String searchKey, String quarter, Double ratio, List<AwardPoolConfig> awardPoolConfigs) {

        //季度业绩边界金额
        Double startLimitMoney = null;
        //季度业绩边界金额
        Double endLimitMoney = null;
        //查询奖金池配置信息
        if (Objects.nonNull(ratio)) {
            //查询这个积分比例所对应的的金额区间
            Optional<AwardPoolConfig> any = awardPoolConfigs.stream().filter(e -> Objects.equals(e.getRatio(), ratio)).findAny();
            if (any.isPresent()) {
                AwardPoolConfig awardPoolConfig = any.get();
                startLimitMoney = awardPoolConfig.getStartRange();
                endLimitMoney = awardPoolConfig.getEndRange();
            }
        }

        List<ScorePoolVo> list = userPerformanceCountMapper.listScorePoolVo(searchKey, quarter, startLimitMoney, endLimitMoney);
        //关联查询当前季度业绩信息,计算可获得积分
        if (Lists.isNotEmpty(list)) {
            //查询积分池配置信息
            for (ScorePoolVo vo : list) {
                //查询当前业绩可以获得的积分比例
                Double currentQuarterMoney = vo.getCurrentQuarterMoney();
                Double moneyRatio = getPerformanceMoneyRatio(currentQuarterMoney, awardPoolConfigs);
                if (Objects.nonNull(moneyRatio)) {
                    //计算可获得积分
                    double score = currentQuarterMoney * moneyRatio;
                    vo.setRatio(moneyRatio);
                    vo.setScore(score);
                }
            }

        }
        return list;
    }


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
    @Override
    public List<ScorePoolInfoVo> listScorePoolInfoVo(Integer ancestorId, String searchKey, String quarter) {
        return userPerformanceCountMapper.listScorePoolInfoVo(ancestorId, searchKey, quarter);
    }

    /**
     * 列出还没有结算的合伙人的业绩
     *
     * @param quarter 季度
     * @return java.util.List<com.logicalthining.endeshop.entity.UserPerformanceCount>
     * @since 下午 1:44 2019/11/12 0012
     **/
    @Override
    public List<UserPerformanceCount> listNotArchivePerformance(String quarter) {
        return userPerformanceCountMapper.listNotArchivePerformance(quarter);
    }


    /**
     * 查找业绩的积分比例
     *
     * @param money            业绩金额
     * @param awardPoolConfigs 积分池配置信息
     * @return java.lang.Double
     * @since 下午 4:39 2019/11/11 0011
     **/
    private Double getPerformanceMoneyRatio(Double money, List<AwardPoolConfig> awardPoolConfigs) {
        List<AwardPoolConfig> collect = awardPoolConfigs.stream().sorted(Comparator.comparing(AwardPoolConfig::getStartRange).reversed()).collect(Collectors.toList());
        for (AwardPoolConfig config : collect) {
            if (money >= config.getStartRange() && (Objects.isNull(config.getEndRange()) || money < config.getEndRange())) {
                return config.getRatio();
            }
        }
        //未找到合适的配置信息
        return null;
    }


    /**
     * 根据用户id以及季度查询用户业绩信息
     *
     * @param userId  用户id
     * @param quarter 季度
     * @return com.logicalthining.endeshop.entity.UserPerformanceCount
     * @since 下午 3:55 2019/11/9 0009
     **/
    @Override
    public UserPerformanceCount findUserPerformanceWithQuarter(Integer userId, String quarter) {
        //获取这个季度用户的业绩信息
        UserPerformanceCount condition = new UserPerformanceCount();
        condition.setUserId(userId);
        condition.setQuarter(quarter);

        UserPerformanceCount userPerformanceCount = userPerformanceCountMapper.selectOne(condition);

        if (Objects.isNull(userPerformanceCount)) {
            userPerformanceCount = new UserPerformanceCount();
            userPerformanceCount.setUserId(userId);
            userPerformanceCount.setQuarter(quarter);
            userPerformanceCount.setCurrentQuarterMoney(0.0);
            //未结算状态
            userPerformanceCount.setArchiveStatus(BooleanConstant.NO_INTEGER);
            userPerformanceCount.setUpdateTime(new Date());

            //历史业绩
            UserPerformanceTotal totalByUserId = findUserPerformanceTotalByUserId(userId);
            userPerformanceCount.setHistoryMoney(totalByUserId.getGrandTotalPerformance());

            userPerformanceCountMapper.insertSelective(userPerformanceCount);
        }

        return userPerformanceCount;
    }


    /**
     * 查询奖金池每个层级对应的团队数量
     *
     * @param list 表示查询的层级
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.userScore.AwardPoolTeamCount>
     * @since 下午 7:44 2019/11/18 0018
     **/
    @Override
    public List<AwardPoolTeamCount> listAwardPoolTeamCount(List<AwardPoolConfig> list) {

        List<AwardPoolTeamCount> awardPoolTeamCountList = Lists.newArrayList();
        //当前季度
        String quarter = QuarterTimeTransferUtil.transferDateToQuarter(new Date());
        if (Lists.isNotEmpty(list)) {
            for (AwardPoolConfig config : list) {
                //查询这个积分等级的团队数量
                Integer teamCount = userPerformanceCountMapper.findTeamCountByPerformanceRange(config.getStartRange(), config.getEndRange(), quarter);
                teamCount = null == teamCount ? 0 : teamCount;
                AwardPoolTeamCount count = new AwardPoolTeamCount();
                count.setRangeScoreStart(config.getStartRange());
                count.setTeamCount(teamCount);
                awardPoolTeamCountList.add(count);
            }
        }
        return awardPoolTeamCountList;
    }


}

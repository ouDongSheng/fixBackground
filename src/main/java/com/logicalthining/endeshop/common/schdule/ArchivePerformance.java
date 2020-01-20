package com.logicalthining.endeshop.common.schdule;

import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.JSONUtil;
import com.github.chenlijia1111.utils.core.NumberUtil;
import com.github.chenlijia1111.utils.list.Lists;
import com.logicalthining.endeshop.common.enums.SystemConfigKey;
import com.logicalthining.endeshop.common.enums.SystemCountKey;
import com.logicalthining.endeshop.common.pojo.systemConfig.AwardPoolConfig;
import com.logicalthining.endeshop.entity.*;
import com.logicalthining.endeshop.service.*;
import com.logicalthining.endeshop.service.impl.*;
import com.logicalthining.endeshop.util.QuarterTimeTransferUtil;
import com.logicalthining.endeshop.util.SpringContextHolder;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 定时器定时结算季度业绩
 * 结算的时机,在每个季度后的最后一天的晚上二点开始
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/12 0012 下午 1:32
 **/
public class ArchivePerformance implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        //查询所有可以进积分池用户的业绩,条件为自己是合伙人,且下面必须要有两个以上的合伙人
        Date currentTime = new Date();
        //用户业绩
        UserPerformanceCountServiceI userPerformanceCountService = SpringContextHolder.getBean(UserPerformanceCountServiceImpl.class);
        //系统设置
        SystemConfigServiceI configService = SpringContextHolder.getBean(SystemConfigServiceImpl.class);
        //用户积分记录
        UserScoreRecodeServiceI userScoreRecodeService = SpringContextHolder.getBean(UserScoreRecodeServiceImpl.class);
        //用户积分
        UserScoreServiceI userScoreService = SpringContextHolder.getBean(UserScoreServiceImpl.class);
        //系统数据统计
        SystemCountServiceI systemCountService = SpringContextHolder.getBean(SystemCountServiceImpl.class);
        //订单
        ShoppingOrderServiceI shoppingOrderService = SpringContextHolder.getBean(ShoppingOrderServiceImpl.class);


        Date lastQuarterTime = new DateTime(currentTime.getTime()).plusMonths(-3).toDate();
        //计算上个季度是哪个季度
        String lastQuarter = QuarterTimeTransferUtil.transferDateToQuarter(lastQuarterTime);

        //查询上个季度没有结算的数据
        List<UserPerformanceCount> userPerformanceCounts = userPerformanceCountService.listNotArchivePerformance(lastQuarter);
        if (Lists.isNotEmpty(userPerformanceCounts)) {
            //上个业绩的总业绩
            //通过订单表进行查询
            //上个季度的开始时间和结束时间
            QuarterTimeTransferUtil.QuarterTimeInterval quarterTimeInterval = QuarterTimeTransferUtil.transferQuarterToQuarterTimeInterval(lastQuarter);
            //上个季度的总业绩
            Double lastQuarterTotalPerformance = shoppingOrderService.countPerformance(quarterTimeInterval.getStartTime(), quarterTimeInterval.getEndTime());


            //查询系统积分池的配置
            SystemConfig systemConfig = configService.selectByConfigKey(SystemConfigKey.AWARD_POOL_CONFIG.getKey());
            String configValue = systemConfig.getConfigValue();
            List<AwardPoolConfig> awardPoolConfigs = JSONUtil.strToList(configValue, ArrayList.class, AwardPoolConfig.class);


            //每个级别进行平分总业绩
            if (Lists.isNotEmpty(awardPoolConfigs)) {
                //倒序排序
                awardPoolConfigs.sort(Comparator.comparing(AwardPoolConfig::getStartRange).reversed());

                //总共结算的金额
                double totalArchivePerformanceMoney = 0.0;
                for (AwardPoolConfig awardPoolConfig : awardPoolConfigs) {
                    Double startRange = awardPoolConfig.getStartRange();
                    Double endRange = awardPoolConfig.getEndRange();
                    Double ratio = awardPoolConfig.getRatio();

                    //计算符合这个区间的合伙人
                    List<UserPerformanceCount> hitData = userPerformanceCounts.stream().filter(e -> e.getCurrentQuarterMoney() >= startRange
                            && (Objects.nonNull(endRange) && e.getCurrentQuarterMoney() < endRange)).collect(Collectors.toList());

                    //将总业绩进行平分
                    if (Lists.isNotEmpty(hitData)) {
                        //这个级别系统分的钱
                        double currentLevelAward = lastQuarterTotalPerformance * ratio;
                        currentLevelAward = NumberUtil.doubleToFixLengthDouble(currentLevelAward, 2);

                        //每个人可以得到的钱
                        double perUserCanGet = currentLevelAward / hitData.size();
                        perUserCanGet = NumberUtil.doubleToFixLengthDouble(perUserCanGet, 2);

                        //总共结算的金额
                        totalArchivePerformanceMoney += currentLevelAward;

                        for (UserPerformanceCount hitDatum : hitData) {
                            //修改结算状态为已结算
                            hitDatum.setArchiveStatus(BooleanConstant.YES_INTEGER);
                            hitDatum.setScoreRatio(ratio);
                            hitDatum.setScore(perUserCanGet);
                            userPerformanceCountService.update(hitDatum);

                            //添加用户积分记录
                            if (perUserCanGet > 0.0) {

                                //查询这个用户的积分
                                UserScore userScore = userScoreService.findByUserId(hitDatum.getUserId());

                                //添加积分获取记录
                                UserScoreRecode userScoreRecode = new UserScoreRecode().
                                        setUserId(hitDatum.getUserId()).
                                        setType(4).setRemarks("积分池季度结算").
                                        setRatio(ratio).setTransactionScore(perUserCanGet).
                                        setTotalScore(userScore.getScore() + perUserCanGet).
                                        setCreateTime(new Date());

                                userScoreRecodeService.add(userScoreRecode);

                                //修改用户积分
                                userScore.setScore(userScore.getScore() + perUserCanGet);
                                userScore.setGrandTotalScore(userScore.getGrandTotalScore() + perUserCanGet);
                                userScore.setUpdateTime(new Date());
                                userScoreService.update(userScore);
                            }
                        }
                    }
                }

                //给系统统计数据添加奖金池数据
                SystemCount systemCount = systemCountService.findByCountKey(SystemCountKey.AWARD_POOL.getName());
                systemCount.setCountValue(systemCount.getCountValue() + totalArchivePerformanceMoney);
                systemCount.setUpdateTime(new Date());
                systemCountService.update(systemCount);
            }

        }

    }
}

package com.logicalthining.endeshop.biz;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.JSONUtil;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.logicalthining.endeshop.common.checkFunction.PriceCheck;
import com.logicalthining.endeshop.common.checkFunction.RatioCheck;
import com.logicalthining.endeshop.common.enums.SystemConfigKey;
import com.logicalthining.endeshop.common.pojo.Constants;
import com.logicalthining.endeshop.common.pojo.systemConfig.AwardPoolConfig;
import com.logicalthining.endeshop.common.requestVo.userScore.*;
import com.logicalthining.endeshop.common.responseVo.userScore.*;
import com.logicalthining.endeshop.entity.SystemConfig;
import com.logicalthining.endeshop.entity.User;
import com.logicalthining.endeshop.service.*;
import com.logicalthining.endeshop.util.QuarterTimeTransferUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 用户积分记录
 *
 * @author chenLiJia
 * @since 2019-11-07 13:40:17
 **/
@Service
public class UserScoreRecodeBiz {

    @Autowired
    private UserScoreRecodeServiceI userScoreRecodeService;//用户积分记录
    @Autowired
    private SystemConfigServiceI systemConfigService;//系统配置
    @Autowired
    private UserPerformanceCountServiceI userPerformanceCountService;//用户业绩
    @Autowired
    private PartnerInfoServiceI partnerInfoService;//合伙人信息
    @Autowired
    private UserServiceI userService;//用户

    /**
     * 用户积分获取记录列表查询
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:34 2019/11/8 0008
     **/
    public Result adminListPage(UserScoreQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<AdminUserScoreListVo> list = userScoreRecodeService.listAdminUserScoreListVo(params);
        PageInfo<AdminUserScoreListVo> pageInfo = new PageInfo<>(list);
        return Result.success("查询成功", pageInfo);
    }

    /**
     * 客户端查询用户积分获取记录列表
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:34 2019/11/8 0008
     **/
    public Result appListPage(AppUserScoreRecodeQueryParams params) {

        //校验参数
        params = PropertyCheckUtil.transferObjectNotNull(params, true);

        //当前用户
        Optional<User> optional = userService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        params.setCurrentUserId(optional.get().getId());

        PageHelper.startPage(params.getPage(), params.getLimit());
        List<AppScoreRecodeVo> list = userScoreRecodeService.listAppScoreRecodeVo(params);
        PageInfo<AppScoreRecodeVo> pageInfo = new PageInfo<>(list);
        return Result.success("查询成功", pageInfo);
    }


    /**
     * 设置间接奖获得积分百分比
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:17 2019/11/8 0008
     **/
    public Result configIndirectAndRepeatAward(IndirectAndRepeatAwardConfigParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //查询间接奖配置
        SystemConfig indirectSystemConfig = systemConfigService.selectByConfigKey(SystemConfigKey.INDIRECT_AWARD_RATIO.getKey());
        indirectSystemConfig.setConfigValue(String.valueOf(params.getIndirectRatio()));
        systemConfigService.update(indirectSystemConfig);

        //查询复购奖配置
        SystemConfig repeatSystemConfig = systemConfigService.selectByConfigKey(SystemConfigKey.REPEAT_AWARD_RATIO.getKey());
        repeatSystemConfig.setConfigValue(String.valueOf(params.getRepeatRatio()));
        systemConfigService.update(repeatSystemConfig);

        return Result.success("操作成功");
    }


    /**
     * 重置间接奖获得积分百分比
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:17 2019/11/8 0008
     **/
    public Result resetIndirectAndRepeatAward() {


        //查询间接奖配置
        SystemConfig indirectSystemConfig = systemConfigService.selectByConfigKey(SystemConfigKey.INDIRECT_AWARD_RATIO.getKey());
        indirectSystemConfig.setConfigValue(Constants.SYSTEM_CONFIG_INIT_MAP.get(SystemConfigKey.INDIRECT_AWARD_RATIO.getKey()));
        systemConfigService.update(indirectSystemConfig);

        //查询复购奖配置
        SystemConfig repeatSystemConfig = systemConfigService.selectByConfigKey(SystemConfigKey.REPEAT_AWARD_RATIO.getKey());
        repeatSystemConfig.setConfigValue(Constants.SYSTEM_CONFIG_INIT_MAP.get(SystemConfigKey.REPEAT_AWARD_RATIO.getKey()));
        systemConfigService.update(repeatSystemConfig);

        return Result.success("操作成功");
    }


    /**
     * 设置奖金池
     *
     * @param list 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:18 2019/11/8 0008
     **/
    public Result configAwardPool(List<AwardPoolConfig> list) {

        //校验参数
        if (Lists.isEmpty(list)) {
            return Result.failure("参数为空");
        }
        for (int i = 0; i < list.size(); i++) {
            AwardPoolConfig awardPoolConfig = list.get(i);
            Double startRange = awardPoolConfig.getStartRange();
            Double endRange = awardPoolConfig.getEndRange();
            Double ratio = awardPoolConfig.getRatio();

            PriceCheck priceCheck = new PriceCheck();
            RatioCheck ratioCheck = new RatioCheck();

            if (!priceCheck.test(startRange)) {
                return Result.failure("区间起始金额不合法");
            }
            if (!priceCheck.test(endRange) && (Objects.nonNull(endRange) || (i != list.size() - 1))) {
                //只有最后一个区间金额才可以为空
                return Result.failure("区间结束金额不合法");
            }
            if (!ratioCheck.test(ratio)) {
                return Result.failure("积分百分比不合法");

            }

            //判断金额有没有覆盖问题
            if (Objects.nonNull(startRange) && Objects.nonNull(endRange) && startRange >= endRange) {
                return Result.success("金额区间起始金额不能大于等于结束金额");
            }

            //判断跟别的金额区间有没有覆盖问题
            if (i != list.size() - 1) {
                //判断会不会跟后面的区间有冲突
                AwardPoolConfig nextConfig = list.get(i + 1);
                if (endRange >= nextConfig.getStartRange()) {
                    return Result.failure("金额区间重叠");
                }
            }
            if (i != 0) {
                //判断会不会跟前面的区间有冲突
                AwardPoolConfig preConfig = list.get(i - 1);
                if (startRange <= preConfig.getEndRange()) {
                    return Result.failure("金额区间重叠");
                }
            }
        }


        //查询奖金池配置
        SystemConfig systemConfig = systemConfigService.selectByConfigKey(SystemConfigKey.AWARD_POOL_CONFIG.getKey());
        String s = JSONUtil.objToStr(list);
        systemConfig.setConfigValue(s);

        return systemConfigService.update(systemConfig);
    }

    /**
     * 重置奖金池
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:18 2019/11/8 0008
     **/
    public Result resetAwardPool() {

        //查询奖金池配置
        SystemConfig systemConfig = systemConfigService.selectByConfigKey(SystemConfigKey.AWARD_POOL_CONFIG.getKey());

        //初始值
        String initValue = Constants.SYSTEM_CONFIG_INIT_MAP.get(SystemConfigKey.AWARD_POOL_CONFIG.getKey());
        systemConfig.setConfigValue(initValue);

        return systemConfigService.update(systemConfig);
    }


    /**
     * 查询积分比例设置
     * 奖金池设置
     * 间接奖以及分享奖设置
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:19 2019/11/8 0008
     **/
    public Result findAwardConfig() {

        //查询奖金池配置
        SystemConfig awardPoolConfig = systemConfigService.selectByConfigKey(SystemConfigKey.AWARD_POOL_CONFIG.getKey());
        String awardPoolConfigValue = awardPoolConfig.getConfigValue();
        List<AwardPoolConfig> poolConfigs = JSONUtil.strToList(awardPoolConfigValue, ArrayList.class, AwardPoolConfig.class);

        //查询间接奖配置
        SystemConfig indirectAwardConfig = systemConfigService.selectByConfigKey(SystemConfigKey.INDIRECT_AWARD_RATIO.getKey());
        String indirectAwardConfigValue = indirectAwardConfig.getConfigValue();
        //查询复购奖配置
        SystemConfig repeatAwardConfig = systemConfigService.selectByConfigKey(SystemConfigKey.REPEAT_AWARD_RATIO.getKey());
        String repeatAwardConfigValue = repeatAwardConfig.getConfigValue();

        ScoreRatioConfigVo vo = new ScoreRatioConfigVo();
        vo.setAwardPoolConfigList(poolConfigs);
        vo.setIndirectAwardConfig(Double.parseDouble(indirectAwardConfigValue));
        vo.setRepeatAwardConfig(Double.parseDouble(repeatAwardConfigValue));

        return Result.success("查询成功", vo);
    }


    /**
     * 列表查询积分池
     * 积分池需要满足有至少两个直接下级是合伙人
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 4:32 2019/11/9 0009
     **/
    public Result listPageScorePool(ScorePoolQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);

        SystemConfig systemConfig = systemConfigService.selectByConfigKey(SystemConfigKey.AWARD_POOL_CONFIG.getKey());
        String configValue = systemConfig.getConfigValue();
        //转换为奖金池设置信息对象
        List<AwardPoolConfig> awardPoolConfigs = JSONUtil.strToList(configValue, ArrayList.class, AwardPoolConfig.class);

        PageHelper.startPage(params.getPage(), params.getLimit());
        List<ScorePoolVo> list = userPerformanceCountService.listScorePoolVo(params.getSearchKey(),
                params.getQuarter(), params.getRatio(), awardPoolConfigs);
        PageInfo<ScorePoolVo> pageInfo = new PageInfo<>(list);
        return Result.success("查询成功", pageInfo);
    }


    /**
     * 查询新晋级合伙人
     * 新晋合伙人表示当前季度晋升为合伙人的合伙人
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:28 2019/11/11 0011
     **/
    public Result listNewPartnerUser(UserPoolInfoQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);

        NewPartnerQueryParams condition = new NewPartnerQueryParams().setAncestorId(params.getParentUserId()).
                setUserAccount(params.getSearchKey());
        //将查询条件中的季度转换为时间区间
        if (StringUtils.isNotEmpty(params.getQuarter())) {
            QuarterTimeTransferUtil.QuarterTimeInterval quarterTimeInterval = QuarterTimeTransferUtil.transferQuarterToQuarterTimeInterval((params.getQuarter()));
            condition.setPromotionTimeStartTime(quarterTimeInterval.getStartTime());
            condition.setPromotionTimeEndTime(quarterTimeInterval.getEndTime());
        }

        List<NewPartnerUserInfo> list = partnerInfoService.listNewPartnerUserInfo(condition);
        return Result.success("查询成功", list);
    }

    /**
     * 列出团队积分详情
     * 因为这里需要展示积分比例，所以也需要直接下级有两个以上的合伙人
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:32 2019/11/11 0011
     **/
    public Result listTeamScoreInfo(UserPoolInfoQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        if (Objects.isNull(params.getParentUserId())) {
            return Result.failure("父用户不能为空");
        }

        PageHelper.startPage(params.getPage(), params.getLimit());
        List<ScorePoolInfoVo> list = userPerformanceCountService.listScorePoolInfoVo(params.getParentUserId(), params.getSearchKey(), params.getQuarter());
        PageInfo<ScorePoolInfoVo> pageInfo = new PageInfo<>(list);

        if (Lists.isNotEmpty(list)) {
            //关联计算得出这个季度可以获得的积分比例以及积分
            //如果这个季度已经结算了,那就不需要计算了,如果还没有结算,
            // 那就通过当前积分池的配置来进行计算一下可以获得多少积分以及积分比例
            SystemConfig systemConfig = systemConfigService.selectByConfigKey(SystemConfigKey.AWARD_POOL_CONFIG.getKey());
            String configValue = systemConfig.getConfigValue();
            List<AwardPoolConfig> awardPoolConfigs = JSONUtil.strToList(configValue, ArrayList.class, AwardPoolConfig.class);

            for (ScorePoolInfoVo vo : list) {
                if (Objects.equals(vo.getArchiveStatus(), BooleanConstant.NO_INTEGER)) {
                    //未结算的
                    //查询积分比例
                    Double ratio = AwardPoolConfig.findRatio(vo.getCurrentQuarterMoney(), awardPoolConfigs);
                    if (Objects.nonNull(ratio)) {
                        //可获得积分
                        double currentScore = vo.getCurrentQuarterMoney() * ratio;
                        vo.setRatio(ratio);
                        vo.setCurrentScore(currentScore);
                    }
                }
            }
        }

        return Result.success("查询成功", pageInfo);
    }


    /**
     * 查询用户积分记录
     * 积分池-详情-团队积分详情-历史收益记录
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:55 2019/11/12 0012
     **/
    public Result listUserScoreRecodeVo(UserScoreRecodeQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<UserScoreRecodeVo> list = userScoreRecodeService.listUserScoreRecodeVo(params);
        PageInfo<UserScoreRecodeVo> pageInfo = new PageInfo<>(list);
        return Result.success("查询成功", pageInfo);
    }

}

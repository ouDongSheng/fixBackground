package com.logicalthining.endeshop.biz;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.JSONUtil;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.encrypt.MD5EncryptUtil;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.logicalthining.endeshop.common.enums.SystemConfigKey;
import com.logicalthining.endeshop.common.pojo.systemConfig.AwardPoolConfig;
import com.logicalthining.endeshop.common.requestVo.capitalFlow.AppQueryParams;
import com.logicalthining.endeshop.common.requestVo.user.AdminWithdrawRecodeQueryParams;
import com.logicalthining.endeshop.common.requestVo.user.AppWithdrawParams;
import com.logicalthining.endeshop.common.requestVo.user.EditWithdrawPasswordParams;
import com.logicalthining.endeshop.common.responseVo.capitalFlow.AppUserCapitalVo;
import com.logicalthining.endeshop.common.responseVo.user.AppAccountInfoVo;
import com.logicalthining.endeshop.common.responseVo.userRelation.UserChildCountVo;
import com.logicalthining.endeshop.common.responseVo.userScore.AwardPoolTeamCount;
import com.logicalthining.endeshop.common.responseVo.withdraw.AdminWithdrawListVo;
import com.logicalthining.endeshop.entity.*;
import com.logicalthining.endeshop.service.*;
import com.logicalthining.endeshop.util.QuarterTimeTransferUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户账户
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/13 0013 下午 3:31
 **/
@Service
public class UserAccountBiz {

    @Autowired
    private UserServiceI userService;//用户
    @Autowired
    private AccountUserServiceI accountUserService;//用户账户信息
    @Autowired
    private UserScoreServiceI userScoreService;//用户积分信息
    @Autowired
    private UserChildCountServiceI userChildCountService;//用户下级数量
    @Autowired
    private UserRelationServiceI userRelationService;//用户关系
    @Autowired
    private CapitalFlowUserServiceI capitalFlowUserService;//用户流水
    @Autowired
    private UserWithdrawPasswordServiceI userWithdrawPasswordService;//用户提现密码
    @Autowired
    private UserWithdrawRecodeServiceI userWithdrawRecodeService;//用户提现记录
    @Autowired
    private AdminServiceI adminService;//管理员
    @Autowired
    private MsgSendRecodeServiceI msgSendRecodeService;//验证码发送记录
    @Autowired
    private UserPerformanceCountServiceI userPerformanceCountService;//用户业绩
    @Autowired
    private SystemConfigServiceI systemConfigService;//系统设置

    /**
     * 查询当前用户信息以及账户信息
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 3:06 2019/11/13 0013
     **/
    public Result findCurrentUserAccountInfo() {

        //判断当前用户是否登陆
        Optional<User> optional = userService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        User user = optional.get();

        //开始组织返回数据
        AppAccountInfoVo vo = new AppAccountInfoVo();
        vo.setId(user.getId());
        vo.setAccount(user.getAccount());
        vo.setHeadImage(user.getHeadImage());
        vo.setRole(user.getRole());
        vo.setName(user.getName());

        //查询用户账户信息
        AccountUser accountUser = accountUserService.findByUserId(user.getId());
        vo.setGrandTotalMoney(accountUser.getGrandTotalBalance());
        vo.setCanWithdrawMoney(accountUser.getUserBalance());

        //查询积分信息
        UserScore userScore = userScoreService.findByUserId(user.getId());
        vo.setScore(userScore.getScore());

        //获取直接下级用户数量
        UserChildCountVo userChildCountVo = userRelationService.findUserAllChildCountVo(user.getId());
        if (Objects.nonNull(userChildCountVo)) {
            vo.setTeamCommonUserCount(userChildCountVo.getTeamCommonUserCount());
            vo.setTeamVIPUserCount(userChildCountVo.getTeamVIPUserCount());
            vo.setTeamPartnerUserCount(userChildCountVo.getTeamPartnerUserCount());
        }

        //查询当前季度业绩信息
        //将当前时间转为业绩字符串
        Date currentTime = new Date();
        String currentQuarter = QuarterTimeTransferUtil.transferDateToQuarter(currentTime);
        UserPerformanceCount userPerformanceCount = userPerformanceCountService.findUserPerformanceWithQuarter(user.getId(), currentQuarter);
        //当前季度业绩
        vo.setCurrentQuarterPerformance(userPerformanceCount.getCurrentQuarterMoney());

        //查询积分池积分比例设置
        SystemConfig systemConfig = systemConfigService.selectByConfigKey(SystemConfigKey.AWARD_POOL_CONFIG.getKey());
        String configValue = systemConfig.getConfigValue();
        List<AwardPoolConfig> awardPoolConfigs = JSONUtil.strToList(configValue, ArrayList.class, AwardPoolConfig.class);

        //查询当前季度业绩可获得的积分
        Double ratio = AwardPoolConfig.findRatio(userPerformanceCount.getCurrentQuarterMoney(), awardPoolConfigs);
        //判断属于第几个,生成标语 如 恭喜您可参与一等积分池
        if (Objects.nonNull(ratio)) {
            awardPoolConfigs = awardPoolConfigs.stream().sorted(Comparator.comparing(AwardPoolConfig::getRatio).reversed()).collect(Collectors.toList());
            for (int i = 0; i < awardPoolConfigs.size(); i++) {
                AwardPoolConfig awardPoolConfig = awardPoolConfigs.get(i);
                if (Objects.equals(awardPoolConfig.getRatio(), ratio)) {
                    vo.setPerformanceRemarks("恭喜您可参与" + (i + 1) + "等积分池");
                    break;
                }
            }
        }

        //奖金池级别对应的团队数量
        //按区间开始业绩进行倒序排序,取前三个查询对应的团队数量
        List<AwardPoolConfig> collect = awardPoolConfigs.stream().sorted(Comparator.comparing(AwardPoolConfig::getStartRange).reversed()).limit(3).collect(Collectors.toList());
        List<AwardPoolTeamCount> awardPoolTeamCountList = userPerformanceCountService.listAwardPoolTeamCount(collect);
        vo.setAwardPoolTeamCountList(awardPoolTeamCountList);

        return Result.success("查询成功", vo);
    }

    /**
     * 查询当前用户流水
     *
     * @param params
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 6:09 2019/11/13 0013
     **/
    public Result listCapitalFlow(AppQueryParams params) {

        //校验参数
        params = PropertyCheckUtil.transferObjectNotNull(params, true);

        //当前用户
        Optional<User> optional = userService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        params.setUserId(optional.get().getId());

        PageHelper.startPage(params.getPage(), params.getLimit());
        List<AppUserCapitalVo> list = capitalFlowUserService.listAppUserCapitalVo(params);
        PageInfo<AppUserCapitalVo> pageInfo = new PageInfo<>(list);
        return Result.success("查询成功", pageInfo);
    }


    /**
     * 设置提现密码
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:38 2019/11/14 0014
     **/
    public Result editWithdrawPassword(EditWithdrawPasswordParams params) {

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
        User user = optional.get();

        //判断验证码是否正确
        result = msgSendRecodeService.checkMsgCode(user.getAccount(), 3, params.getMsgCode());
        if (!result.getSuccess()) {
            return result;
        }

        //如果设置了密码，就覆盖旧密码,没设置,就添加
        UserWithdrawPassword userWithdrawPassword = userWithdrawPasswordService.findByUserId(user.getId());
        if (Objects.isNull(userWithdrawPassword)) {
            userWithdrawPassword = new UserWithdrawPassword();
            userWithdrawPassword.setUserId(user.getId());
            userWithdrawPassword.setPassword(MD5EncryptUtil.MD5StringToHexString(params.getPassword()));
            userWithdrawPassword.setUpdateTime(new Date());
            userWithdrawPasswordService.add(userWithdrawPassword);
        } else {
            userWithdrawPassword.setPassword(MD5EncryptUtil.MD5StringToHexString(params.getPassword()));
            userWithdrawPassword.setUpdateTime(new Date());
            userWithdrawPasswordService.update(userWithdrawPassword);
        }
        return Result.success("操作成功");
    }


    /**
     * 校验用户是否设置了提现密码
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:00 2019/11/14 0014
     **/
    public Result checkWithdrawPassword() {
        //当前用户
        Optional<User> optional = userService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }
        User user = optional.get();

        //如果设置了密码，就覆盖旧密码,没设置,就添加
        UserWithdrawPassword userWithdrawPassword = userWithdrawPasswordService.findByUserId(user.getId());
        return Objects.isNull(userWithdrawPassword) ? Result.success("用户未设置提现密码", false) :
                Result.success("用户已设置提现密码", true);
    }


    /**
     * 用户提现申请
     * 提现申请之后，用户的账户余额会先被减去，然后再流水记录表里加一条记录
     * 相当于锁定了这部分钱
     * 如果提现失败，就把这部分钱返回回来
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:02 2019/11/14 0014
     **/
    public Result withdraw(AppWithdrawParams params) {

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
        User user = optional.get();

        //只有周二才可以提现
        int dayOfWeek = DateTime.now().getDayOfWeek();
        if (!Objects.equals(2, dayOfWeek)) {
            return Result.failure("只有周二方可提现");
        }

        //判断用户提现密码
        UserWithdrawPassword userWithdrawPassword = userWithdrawPasswordService.findByUserId(user.getId());
        if (Objects.isNull(userWithdrawPassword)) {
            return Result.failure("当前用户未设置提现密码,请先设置提现密码");
        }

        if (!Objects.equals(MD5EncryptUtil.MD5StringToHexString(params.getPassword()), userWithdrawPassword.getPassword())) {
            return Result.failure("提现密码错误");
        }

        //判断余额是否充足
        AccountUser accountUser = accountUserService.findByUserId(user.getId());
        if (accountUser.getUserBalance() < params.getMoney()) {
            return Result.failure("余额不足");
        }

        //提交提现申请记录
        UserWithdrawRecode withdrawRecode = new UserWithdrawRecode().
                setUserId(user.getId()).
                setWithdrawMoney(params.getMoney()).
                setWithdrawAccount(params.getWithdrawAccount()).
                setStatus(0).
                setCreateTime(new Date());

        userWithdrawRecodeService.add(withdrawRecode);

        //减去账户余额
        accountUser.setUserBalance(accountUser.getUserBalance() - params.getMoney());
        accountUserService.update(accountUser);

        //添加流水记录
        CapitalFlowUser flowUser = new CapitalFlowUser().setUserId(user.getId()).
                setType(2).
                setRelationItem(withdrawRecode.getId() + "").
                setTransactionMoney(params.getMoney()).
                setRemarks("提现").
                setCreateTime(new Date()).setUserBalance(accountUser.getUserBalance());
        capitalFlowUserService.add(flowUser);

        return Result.success("操作成功");
    }


    /**
     * 后台查询用户提现记录
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:32 2019/11/14 0014
     **/
    public Result adminListWithdrawRecode(AdminWithdrawRecodeQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<AdminWithdrawListVo> list = userWithdrawRecodeService.listAdminWithdrawListVo(params);
        PageInfo<AdminWithdrawListVo> pageInfo = new PageInfo<>(list);
        return Result.success("查询成功", pageInfo);
    }


    /**
     * 提现成功
     *
     * @param id 提现记录id
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:36 2019/11/14 0014
     **/
    public Result successWithdraw(Integer id) {

        //验证参数
        if (Objects.isNull(id)) {
            return Result.failure("id为空");
        }

        //当前后台用户
        Optional<Admin> optional = adminService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }
        Admin admin = optional.get();

        //判断数据是否存在
        UserWithdrawRecode userWithdrawRecode = userWithdrawRecodeService.findById(id);
        if (Objects.isNull(userWithdrawRecode)) {
            return Result.failure("数据不存在");
        }

        //不是初始状态
        if (!Objects.equals(0, userWithdrawRecode.getStatus())) {
            return Result.failure("该申请记录已处理,无法重复处理");
        }

        //开始处理
        userWithdrawRecode.setStatus(1);
        userWithdrawRecode.setProcessAdmin(admin.getId());
        userWithdrawRecode.setProcessTime(new Date());

        return userWithdrawRecodeService.update(userWithdrawRecode);
    }

    /**
     * 提现失败
     *
     * @param id 提现记录id
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:36 2019/11/14 0014
     **/
    public Result failureWithdraw(Integer id) {
        //验证参数
        if (Objects.isNull(id)) {
            return Result.failure("id为空");
        }

        //当前后台用户
        Optional<Admin> optional = adminService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }
        Admin admin = optional.get();

        //判断数据是否存在
        UserWithdrawRecode userWithdrawRecode = userWithdrawRecodeService.findById(id);
        if (Objects.isNull(userWithdrawRecode)) {
            return Result.failure("数据不存在");
        }

        //不是初始状态
        if (!Objects.equals(0, userWithdrawRecode.getStatus())) {
            return Result.failure("该申请记录已处理,无法重复处理");
        }

        //开始处理
        userWithdrawRecode.setStatus(2);
        userWithdrawRecode.setProcessAdmin(admin.getId());
        userWithdrawRecode.setProcessTime(new Date());
        Result update = userWithdrawRecodeService.update(userWithdrawRecode);
        if (update.getSuccess()) {
            //提现失败,需要把用户的余额加回去
            AccountUser accountUser = accountUserService.findByUserId(userWithdrawRecode.getUserId());
            accountUser.setUserBalance(accountUser.getUserBalance() + userWithdrawRecode.getWithdrawMoney());
            accountUserService.update(accountUser);
            //删除流水记录
            CapitalFlowUser flowUserCondition = new CapitalFlowUser();
            flowUserCondition.setUserId(userWithdrawRecode.getUserId()).
                    setRelationItem(userWithdrawRecode.getId() + "").setType(2);
            List<CapitalFlowUser> capitalFlowUsers = capitalFlowUserService.listByCondition(flowUserCondition);
            if (Lists.isNotEmpty(capitalFlowUsers)) {
                capitalFlowUserService.deleteById(capitalFlowUsers.get(0).getId());
            }
        }

        return update;
    }


}

package com.logicalthining.endeshop.biz;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.common.constant.RegConstant;
import com.github.chenlijia1111.utils.common.constant.TimeConstant;
import com.github.chenlijia1111.utils.core.*;
import com.github.chenlijia1111.utils.core.enums.CharSetType;
import com.github.chenlijia1111.utils.encrypt.MD5EncryptUtil;
import com.github.chenlijia1111.utils.http.HttpClientUtils;
import com.github.chenlijia1111.utils.http.HttpUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Maps;
import com.github.chenlijia1111.utils.list.Sets;
import com.github.chenlijia1111.utils.list.annos.MapType;
import com.github.chenlijia1111.utils.pay.wx.PayType;
import com.github.chenlijia1111.utils.pay.wx.WXPayUtil;
import com.github.chenlijia1111.utils.xml.XmlUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.logicalthining.endeshop.common.enums.SystemConfigKey;
import com.logicalthining.endeshop.common.enums.SystemCountKey;
import com.logicalthining.endeshop.common.enums.UserRole;
import com.logicalthining.endeshop.common.pojo.Constants;
import com.logicalthining.endeshop.common.pojo.IDGenerateFactory;
import com.logicalthining.endeshop.common.pojo.order.OrderRemarks;
import com.logicalthining.endeshop.common.pojo.voucher.DiscountCoupon;
import com.logicalthining.endeshop.common.requestVo.order.*;
import com.logicalthining.endeshop.common.responseVo.order.AdminOrderInfoVo;
import com.logicalthining.endeshop.common.responseVo.order.AdminOrderListVo;
import com.logicalthining.endeshop.common.responseVo.order.AppOrderListVo;
import com.logicalthining.endeshop.common.responseVo.product.AdminProductVo;
import com.logicalthining.endeshop.common.responseVo.product.GoodVo;
import com.logicalthining.endeshop.entity.*;
import com.logicalthining.endeshop.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 订单
 *
 * @author chenLiJia
 * @since 2019-11-05 16:39:24
 **/
@Service
@Slf4j
public class ShoppingOrderBiz {

    @Autowired
    private ShoppingOrderServiceI shoppingOrderService;//购物单
    @Autowired
    private ImmediatePaymentOrderServiceI immediatePaymentOrderService;//发货单
    @Autowired
    private ReceivingGoodsOrderServiceI receivingGoodsOrderService;//收货单
    @Autowired
    private UserServiceI userService;//客户端用户
    @Autowired
    private GoodsServiceI goodsService;//商品
    @Autowired
    private ProductServiceI productService;//产品
    @Autowired
    private OrderShareUserServiceI orderShareUserService;//订单分享者
    @Autowired
    private UserRelationServiceI userRelationService;//用户上下级关系
    @Autowired
    private SystemConfigServiceI systemConfigService;//系统配置
    @Autowired
    private AccountUserServiceI accountUserService;//用户账户信息
    @Autowired
    private CapitalFlowUserServiceI capitalFlowUserService;//用户资金流水
    @Autowired
    private UserScoreRecodeServiceI userScoreRecodeService;//用户积分
    @Autowired
    private UserPerformanceCountServiceI userPerformanceCountService;//用户业绩信息统计
    @Autowired
    private UserScoreServiceI userScoreService;//用户积分
    @Autowired
    private SystemCountServiceI systemCountService;//系统统计数据
    @Autowired
    private ThirdPartyLoginServiceI thirdPartyLoginService;//第三方登录信息
    @Autowired
    private UserPerformanceHistoryServiceI userPerformanceHistoryService;//用户业绩记录
    @Autowired
    private UserPerformanceTotalServiceI userPerformanceTotalService;//用户总业绩

    /**
     * 添加订单
     * 重复购折扣：会员、合伙人，全平台商品复购一率6.5折优惠
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 4:53 2019/11/5 0005
     **/
    @Transactional
    public Result add(OrderAddParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }
        //校验订单中的商品参数
        List<SingleOrderAddParams> singleOrderList = params.getSingleOrderList();
        for (SingleOrderAddParams addParams : singleOrderList) {
            result = PropertyCheckUtil.checkProperty(addParams);
            if (!result.getSuccess()) {
                return result;
            }
        }

        //当前用户
        Optional<User> optional = userService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        User user = optional.get();
        //用户角色 1 普通用户 2会员 3合伙人
        Integer userRole = user.getRole();


        //组订单Id
        String groupId = IDGenerateFactory.createOrderId();
        //当前时间
        Date currentTime = new Date();

        //开始处理订单
        for (SingleOrderAddParams addParams : singleOrderList) {

            //总应付金额
            Double payAble = 0.0;
            //商品id
            String goodId = addParams.getGoodId();
            //商品数量
            Integer count = addParams.getCount();

            //查询商品信息
            GoodVo goodVo = goodsService.findByGoodId(goodId);
            if (Objects.isNull(goodVo)) {
                //回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure("商品不存在");
            }
            //判断产品是否存在且上架
            Product product = productService.findByProductId(goodVo.getProductId());
            if (Objects.isNull(product) || Objects.equals(BooleanConstant.YES_INTEGER, product.getDeleteStatus())) {
                //回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure("产品不存在");
            }
            if (Objects.equals(BooleanConstant.NO_INTEGER, product.getShelfStatus())) {
                //回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure("产品未上架");
            }

            //订单编号
            String orderNo = IDGenerateFactory.createOrderId();

            ShoppingOrder shoppingOrder = new ShoppingOrder().setOrderNo(orderNo).
                    setCustom(user.getId() + "").
                    setGoodsId(goodId).
                    setCount(count).
                    setState(Constants.ORDER_INIT).
                    setProductAmountTotal(goodVo.getPrice() * count).
                    setGroupId(groupId).
                    setCreateTime(currentTime);

            //订单快照
            AdminProductVo adminProductVo = productService.findAdminProductVoByProductId(goodVo.getProductId());
            String productSnapshot = JSONUtil.objToStr(adminProductVo);
            shoppingOrder.setDetailsJson(productSnapshot);

            //订单备注
            OrderRemarks orderRemarks = new OrderRemarks().setCustomRemarks(params.getRemarks());
            String remarks = JSONUtil.objToStr(orderRemarks);
            shoppingOrder.setRemarks(remarks);

            //根据用户身份判断商品价格
            if (Objects.equals(1, userRole)) {
                //普通用户
                //商品支付时单价
                Double goodPrice = goodVo.getPrice();
                //订单金额
                Double orderAmountTotal = goodPrice * count;
                //加上总应付金额
                payAble += orderAmountTotal;
                shoppingOrder.setGoodPrice(goodPrice).setOrderAmountTotal(orderAmountTotal);
            } else {
                //会员或者合伙人
                //商品支付时单价
                //会员或者合伙人折扣
                SystemConfig systemConfig = systemConfigService.selectByConfigKey(SystemConfigKey.VIP_DISCOUNT.getKey());
                String configValue = systemConfig.getConfigValue();
                Double vipDiscount = Double.parseDouble(configValue);
                Double goodPrice = goodVo.getPrice() * vipDiscount;
                //保留两位小数
                goodPrice = NumberUtil.doubleToFixLengthDouble(goodPrice, 2);
                //订单金额
                Double orderAmountTotal = goodPrice * count;
                //加上总应付金额
                payAble += orderAmountTotal;
                //这个商品单价以及订单金额不取折扣后的,总价取折扣后的
                shoppingOrder.setGoodPrice(goodVo.getPrice()).setOrderAmountTotal(goodVo.getPrice() * count);

                //添加使用的折扣券,这样到时候可以回显
                DiscountCoupon discountCoupon = new DiscountCoupon().setConditionCount(0).
                        setConditionMoney(0.0).
                        setDiscount(vipDiscount).
                        setEffective(BooleanConstant.YES_INTEGER);
                List<Object> voucherList = Lists.newArrayList();
                voucherList.add(discountCoupon);
                String orderCoupon = JSONUtil.objToStr(voucherList);
                shoppingOrder.setOrderCoupon(orderCoupon);
            }

            //保留两位小数
            payAble = NumberUtil.doubleToFixLengthDouble(payAble, 2);
            //总应付金额
            shoppingOrder.setPayable(payAble);

            //添加订单
            shoppingOrderService.add(shoppingOrder);

            //添加发货单
            //发货单单号
            String sendOrderNo = IDGenerateFactory.createOrderId();
            ImmediatePaymentOrder immediatePaymentOrder = new ImmediatePaymentOrder().
                    setOrderNo(sendOrderNo).
                    setCustom(user.getId() + "").
                    setShops(product.getShops() + "").
                    setState(Constants.ORDER_INIT).
                    setRecUser(params.getReceiverName()).
                    setRecTel(params.getReceiverTelephone()).
                    setRecAddr(params.getReceiverAddress()).
                    setRecProvince(params.getReceiveProvince()).
                    setFrontOrder(orderNo).
                    setShoppingOrder(orderNo).
                    setCreateTime(currentTime);
            immediatePaymentOrderService.add(immediatePaymentOrder);

            //添加收货单
            //收货单单号
            String receiveOrderNo = IDGenerateFactory.createOrderId();
            ReceivingGoodsOrder receivingGoodsOrder = new ReceivingGoodsOrder().
                    setOrderNo(receiveOrderNo).
                    setCustom(user.getId() + "").
                    setShops(product.getShops() + "").
                    setState(Constants.ORDER_INIT).
                    setShoppingOrder(orderNo).
                    setImmediatePaymentOrder(sendOrderNo).
                    setFrontOrder(sendOrderNo).
                    setRecUser(params.getReceiverName()).
                    setCreateTime(currentTime);
            receivingGoodsOrderService.add(receivingGoodsOrder);
        }


        //如果这个订单是通过分享下的订单,需要记录分享记录,支付后会确定上下级关系
        if (Objects.nonNull(params.getShareUserId())) {
            OrderShareUser orderShareUser = new OrderShareUser().setGroupId(groupId).setShareUserId(params.getShareUserId());
            orderShareUserService.add(orderShareUser);
        }

        //返回groupId
        return Result.success("操作成功", groupId);
    }


    /**
     * 试算订单价格
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 7:28 2019/11/5 0005
     **/
    public Result calculatePrice(OrderAddParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params, Lists.asList("singleOrderList"));
        if (!result.getSuccess()) {
            return result;
        }
        //校验订单中的商品参数
        List<SingleOrderAddParams> singleOrderList = params.getSingleOrderList();
        for (SingleOrderAddParams addParams : singleOrderList) {
            result = PropertyCheckUtil.checkProperty(addParams);
            if (!result.getSuccess()) {
                return result;
            }
        }

        //当前用户
        Optional<User> optional = userService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        User user = optional.get();
        //用户角色 1 普通用户 2会员 3合伙人
        Integer userRole = user.getRole();

        //订单总金额
        Double orderAmountTotal = 0.0;
        //订单优惠金额
        Double discountPrice = 0.0;
        //总应付金额
        Double payAble = 0.0;

        //开始处理订单
        for (SingleOrderAddParams addParams : singleOrderList) {
            //商品id
            String goodId = addParams.getGoodId();
            //商品数量
            Integer count = addParams.getCount();

            //查询商品信息
            GoodVo goodVo = goodsService.findByGoodId(goodId);
            if (Objects.isNull(goodVo)) {
                return Result.failure("商品不存在");
            }
            //判断产品是否存在且上架
            Product product = productService.findByProductId(goodVo.getProductId());
            if (Objects.isNull(product)) {
                return Result.failure("产品不存在");
            }
            if (Objects.equals(BooleanConstant.NO_INTEGER, product.getShelfStatus())) {
                return Result.failure("产品未上架");
            }


            //根据用户身份判断商品价格
            if (Objects.equals(1, userRole)) {
                //普通用户
                //商品支付时单价
                Double goodPrice = goodVo.getPrice();
                //加上订单总金额
                orderAmountTotal += goodPrice * count;
                //加上总应付金额
                payAble += goodPrice * count;
            } else {
                //会员或者合伙人
                //商品支付时单价
                //会员或者合伙人折扣
                SystemConfig systemConfig = systemConfigService.selectByConfigKey(SystemConfigKey.VIP_DISCOUNT.getKey());
                String configValue = systemConfig.getConfigValue();
                Double vipDiscount = Double.parseDouble(configValue);
                Double goodPrice = goodVo.getPrice() * vipDiscount;
                //订单金额
                orderAmountTotal += goodVo.getPrice() * count;
                //加上总应付金额
                payAble += goodPrice * count;
            }

        }

        //订单优惠金额
        discountPrice = orderAmountTotal - payAble;

        //保留两位小数
        orderAmountTotal = NumberUtil.doubleToFixLengthDouble(orderAmountTotal, 2);
        payAble = NumberUtil.doubleToFixLengthDouble(payAble, 2);
        discountPrice = NumberUtil.doubleToFixLengthDouble(discountPrice, 2);

        CalculateOrderPriceVo calculateOrderPriceVo = new CalculateOrderPriceVo().
                setOrderAmountTotal(orderAmountTotal).
                setDiscountPrice(discountPrice).
                setPayAble(payAble);

        return Result.success("操作成功", calculateOrderPriceVo);
    }


    /**
     * 假支付接口
     *
     * @param payParams 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:50 2019/11/6 0006
     **/
    public Result orderPay(PayParams payParams) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(payParams);
        if (!result.getSuccess()) {
            return result;
        }

        //查询这个组订单的所有订单
        ShoppingOrder condition = new ShoppingOrder().setGroupId(payParams.getGroupId());

        List<ShoppingOrder> shoppingOrders = shoppingOrderService.listByCondition(condition);
        if (Lists.isEmpty(shoppingOrders)) {
            return Result.failure("订单不存在");
        }

        //处理订单
        checkPayed(shoppingOrders, payParams.getPayChannel(), payParams.getPayRecode(), payParams.getTransactionId());

        return Result.success("操作成功");
    }

    /**
     * 订单支付,修改订单状态
     * 处理上下级关系，以及判断是否可以升级会员
     *
     * @param shoppingOrders 1
     * @param payChannel     支付渠道
     * @param payRecode      商家内部交易单号
     * @param transactionId  第三方交易流水号
     * @return void
     * @since 下午 2:12 2019/12/11 0011
     **/
    private void checkPayed(List<ShoppingOrder> shoppingOrders, String payChannel, String payRecode, String transactionId) {
        if (Lists.isNotEmpty(shoppingOrders)) {
            //当前时间
            Date currentTime = new Date();

            for (ShoppingOrder shoppingOrder : shoppingOrders) {
                shoppingOrder.setPayChannel(payChannel);
                shoppingOrder.setPayRecord(payRecode);
                shoppingOrder.setTransactionId(transactionId);
                shoppingOrder.setPayTime(currentTime);

                //修改订单状态
                shoppingOrder.setState(Constants.ORDER_COMPLETE);

                shoppingOrderService.update(shoppingOrder);
            }


            ShoppingOrder shoppingOrder = shoppingOrders.get(0);

            String custom = shoppingOrder.getCustom();
            Double payable = shoppingOrder.getPayable();
            String groupId = shoppingOrder.getGroupId();

            //首先判断订单是不是分享下的单,如果是,需要绑定上下级关系
            checkShareOrder(Integer.valueOf(custom), groupId);

//        //然后判断支付金额有没有到达会员支付金额,如果达到了就升级为会员
//        //如果原来的角色是普通用户,第一次升级为会员,那么他的直接上级可以获得分享奖
            checkUpgradeVIP(Integer.valueOf(custom), payable, groupId);
//
//          //校验是否满足复购奖
//            checkRepeatAward(Integer.valueOf(custom), payable, groupId);
            //转移到完成的时候计算各种奖

            //添加统计系统总销售额和今日销售额
            checkSystemCount(payable);

            //递归给这个用户以及他的上级用户添加业绩统计信息
//            userPerformanceCountService.recursiveAddUserPerformance(Integer.valueOf(custom), payable, currentTime, groupId);
        }
    }

    /**
     * 统计系统中的总销售额和今日销售
     *
     * @param payable 1
     * @return void
     * @since 下午 3:05 2019/11/12 0012
     **/
    private void checkSystemCount(Double payable) {

        //当前时间
        Date currentTime = new Date();

        //系统总销售额
        SystemCount systemCount = systemCountService.findByCountKey(SystemCountKey.SYSTEM_TOTAL_SALES.getName());
        Double countValue = systemCount.getCountValue();
        systemCount.setCountValue(countValue + payable);
        systemCount.setUpdateTime(currentTime);
        systemCountService.update(systemCount);

        //系统今日销售额
        SystemCount nowadaysSystemCount = systemCountService.findByCountKey(SystemCountKey.NOWADAYS_SALES.getName());
        Double nowadaysCountValue = nowadaysSystemCount.getCountValue();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TimeConstant.DATE);
        simpleDateFormat.format(currentTime);
        //判断是不是第二天了
        if (!Objects.equals(simpleDateFormat.format(currentTime), simpleDateFormat.format(nowadaysSystemCount.getUpdateTime()))) {
            nowadaysSystemCount.setCountValue(payable);
        } else {
            nowadaysSystemCount.setCountValue(nowadaysCountValue + payable);
        }
        nowadaysSystemCount.setUpdateTime(currentTime);
        systemCountService.update(nowadaysSystemCount);
    }


    /**
     * 校验是否可以升级成会员了
     * 如果第一次升级会员，且是分享订单，上级有分享奖
     * 如果上级是合伙人，有间接奖
     * 这里呢,先不要结算用户的账户金额,等到订单完成的时候在进行结算
     *
     * @param userId  用户id
     * @param payAble 订单支付金额
     * @return void
     * @since 上午 11:23 2019/11/6 0006
     **/
    private void checkUpgradeVIP(Integer userId, Double payAble, String groupId) {

        if (Objects.nonNull(userId) && Objects.nonNull(payAble)) {
            User userCondition = new User().setId(userId);
            List<User> users = userService.listByCondition(userCondition);
            if (Lists.isNotEmpty(users)) {
                //订单用户信息
                User user = users.get(0);
                //判断是否满足升级条件  满足单次支付金额 且是普通用户 如果已经是会员或者合伙人就没必要在升级了,还会降低等级
                //查询成为会员需要满足的最小金额
                SystemConfig vipLimitMoneyConfig = systemConfigService.selectByConfigKey(SystemConfigKey.VIP_LIMIT_MONEY.getKey());
                String vipLimitMoneyConfigValue = vipLimitMoneyConfig.getConfigValue();
                double vipLimitMoney = Double.parseDouble(vipLimitMoneyConfigValue);
                if (payAble >= vipLimitMoney && Objects.equals(1, user.getRole())) {
                    //以满足
                    user.setRole(UserRole.VIP_USER.getRole());
                    //升级会员
                    userService.update(user);

                    //第一次升级为会员,那么他的直接上级可以获得分享奖
                    //先查询这个用户的上级
                    UserRelation userRelation = userRelationService.findByUserId(userId);
                    if (Objects.nonNull(userRelation)) {
                        //上级的id
                        Integer parentUserId = userRelation.getParentUserId();
                        //查询上级用户
                        User parentUser = userService.findById(parentUserId);
                        if (Objects.isNull(parentUser)) {
                            //上级不存在,数据存在问题了,系统异常
                            return;
                        }

                        //升级会员之后需要判断一下他有没有上级,如果有上级,需要把他的上级的普通下级用户数量-1,会员数量+1
                        userRelationService.recursiveUpdateParentVipChildCount(parentUserId, 1, userId);

                        //获取系统设置的分享奖比率
                        SystemConfig systemConfig = systemConfigService.selectByConfigKey(SystemConfigKey.SHARE_AWARD_RATIO.getKey());
                        String configValue = systemConfig.getConfigValue();
                        //防止转换小数错误
                        if (StringUtils.isDouble(configValue)) {
                            //分享奖比率
                            double v = Double.parseDouble(configValue);
                            //分享奖金额
                            double shareAward = payAble * v;
                            //保留两位小数
                            shareAward = NumberUtil.doubleToFixLengthDouble(shareAward, 2);
                            //加到上级账户以及流水
                            //查询上级用户的账户信息
                            AccountUser parentAccount = accountUserService.findByUserId(parentUserId);
                            if (Objects.nonNull(parentAccount)) {
                                //添加账户资金---完成的时候在变更账户余额
//                                parentAccount.setUserBalance(parentAccount.getUserBalance() + shareAward).
//                                        setGrandTotalBalance(parentAccount.getGrandTotalBalance() + shareAward).
//                                        setUpdateTime(new Date());
//
//                                accountUserService.update(parentAccount);

                                //添加用户资金流水
                                CapitalFlowUser capitalFlowUser = new CapitalFlowUser().
                                        setUserId(parentUserId).
                                        setType(1).
                                        setRelationItem(groupId).
                                        setTransactionMoney(shareAward).
                                        setRemarks("下级首次升级会员获得分享奖").
                                        setCreateTime(new Date()).
                                        setUserBalance(parentAccount.getUserBalance()).
                                        setArriveAccountStatus(BooleanConstant.NO_INTEGER);

                                capitalFlowUserService.add(capitalFlowUser);

                                //给系统统计数据添加分享奖数据--等到完成的时候在加
//                                SystemCount systemCount = systemCountService.findByCountKey(SystemCountKey.SHARE_AWARD.getName());
//                                systemCount.setCountValue(systemCount.getCountValue() + shareAward);
//                                systemCount.setUpdateTime(new Date());
//                                systemCountService.update(systemCount);
                            }
                        }


                    }


                    //查找第一个上级合伙人,普通用户升级为会员，其上级合伙人获得间接奖。此奖励为一次性奖励。此为积分显示
                    // --间接奖也放到完成的时候发放,这里只做记录
                    User firstPartnerAncestor = userRelationService.findFirstPartnerAncestor(userId);
                    if (Objects.nonNull(firstPartnerAncestor)) {
                        //查询上级现在的积分数量
                        UserScore userScore = userScoreService.findByUserId(firstPartnerAncestor.getId());

                        //获取系统设置的间接奖比率
                        SystemConfig indirectSystemConfig = systemConfigService.selectByConfigKey(SystemConfigKey.INDIRECT_AWARD_RATIO.getKey());
                        String indirectConfigValue = indirectSystemConfig.getConfigValue();
                        if (StringUtils.isDouble(indirectConfigValue)) {
                            //间接奖比率
                            double v = Double.parseDouble(indirectConfigValue);
                            v = NumberUtil.doubleToFixLengthDouble(v, 2);
                            //可获得的积分为
                            double transactionScore = payAble * v;
                            transactionScore = NumberUtil.doubleToFixLengthDouble(transactionScore, 2);
                            UserScoreRecode scoreRecode = new UserScoreRecode().
                                    setUserId(firstPartnerAncestor.getId()).
                                    setType(1).
                                    setRelationItem(groupId).
                                    setRemarks("间接奖获得积分").
                                    setRatio(v).
                                    setTransactionScore(transactionScore).
                                    setTotalScore(userScore.getScore() + transactionScore).
                                    setCreateTime(new Date()).
                                    setArriveAccountStatus(BooleanConstant.NO_INTEGER);

                            //添加用户积分记录
                            userScoreRecodeService.add(scoreRecode);
                            //修改用户积分--完成时再改
//                            userScore.setScore(userScore.getScore() + transactionScore);
//                            userScore.setGrandTotalScore(userScore.getGrandTotalScore() + transactionScore);
//                            userScore.setUpdateTime(new Date());
//                            userScoreService.update(userScore);

                            //给系统统计数据添加间接奖数据--完成是再改
//                            SystemCount systemCount = systemCountService.findByCountKey(SystemCountKey.INDIRECT_AWARD.getName());
//                            systemCount.setCountValue(systemCount.getCountValue() + transactionScore);
//                            systemCount.setUpdateTime(new Date());
//                            systemCountService.update(systemCount);
                        }

                    }

                }
            }
        }


    }

    /**
     * 校验上下级关系绑定
     * 如果该用户已经有用户绑定了的话,绑定失败
     *
     * @param userId  用户id
     * @param groupId 组订单id
     * @return void
     * @since 上午 11:24 2019/11/6 0006
     **/
    private void checkShareOrder(Integer userId, String groupId) {
        if (Objects.nonNull(userId) && Objects.nonNull(groupId)) {
            //查询这个订单是否有分享人
            OrderShareUser orderShareUser = orderShareUserService.findByGroupId(groupId);
            if (Objects.nonNull(orderShareUser)) {
                //有分享人
                Integer shareUserId = orderShareUser.getShareUserId();
                //不可以自己绑定自己
                //先查询这个用户有没有上级,如果有上级了,不可以绑定
                UserRelation userRelation = userRelationService.findByUserId(userId);
                if (Objects.isNull(userRelation) && !Objects.equals(userId, shareUserId)) {
                    //没有绑定上下级,且绑定的不是自己,可以进行绑定
                    userRelation = new UserRelation().
                            setUserId(userId).
                            setParentUserId(shareUserId).
                            setCreateTime(new Date());

                    userRelationService.add(userRelation);
                }
            }
        }
    }

    /**
     * 检验是否符合复购奖
     * 即当前购买人的上级是个合伙人
     * 且当前不是第一次购买了
     * 那么第一个上级合伙人可以获得复购奖
     *
     * @param userId  用户id
     * @param payAble 应付金额
     * @param groupId 组订单Id
     * @return void
     * @since 下午 2:00 2019/11/7 0007
     **/
    private void checkRepeatAward(Integer userId, Double payAble, String groupId) {

        //查询第一个上级合伙人
        User firstPartnerAncestor = userRelationService.findFirstPartnerAncestor(userId);
        if (Objects.nonNull(firstPartnerAncestor)) {
            //判断是否首次购买,如果不是首次购买,上级合伙人可以获得复购奖
            ShoppingOrder orderCondition = new ShoppingOrder().setCustom(String.valueOf(userId)).setState(Constants.ORDER_COMPLETE);
            List<ShoppingOrder> shoppingOrders = shoppingOrderService.listByCondition(orderCondition);
            //有效订单次数
            long effectOrderCount = shoppingOrders.stream().map(e -> e.getGroupId()).distinct().count();
            if (effectOrderCount >= 2) {
                //表示当前已经至少是第二次购买了
                //上级合伙人可以获得复购奖
                //查询上级现在的积分数量
                UserScore userScore = userScoreService.findByUserId(firstPartnerAncestor.getId());

                //本次可获得的积分为
                //查询复购奖的积分比率
                SystemConfig systemConfig = systemConfigService.selectByConfigKey(SystemConfigKey.REPEAT_AWARD_RATIO.getKey());
                String configValue = systemConfig.getConfigValue();
                if (StringUtils.isDouble(configValue)) {
                    //复购奖的积分比率
                    double v = Double.parseDouble(configValue);
                    v = NumberUtil.doubleToFixLengthDouble(v, 2);

                    double transactionScore = payAble * v;
                    transactionScore = NumberUtil.doubleToFixLengthDouble(transactionScore, 2);
                    UserScoreRecode scoreRecode = new UserScoreRecode().
                            setUserId(firstPartnerAncestor.getId()).
                            setType(2).
                            setRelationItem(groupId).
                            setRemarks("复购奖获得积分").
                            setRatio(v).
                            setTransactionScore(transactionScore).
                            setTotalScore(userScore.getScore() + transactionScore).
                            setCreateTime(new Date());

                    //添加用户积分记录
                    userScoreRecodeService.add(scoreRecode);
                    //修改用户积分
                    userScore.setScore(userScore.getScore() + transactionScore);
                    userScore.setGrandTotalScore(userScore.getGrandTotalScore() + transactionScore);
                    userScore.setUpdateTime(new Date());
                    userScoreService.update(userScore);

                    //给系统统计数据添加复购奖数据
                    SystemCount systemCount = systemCountService.findByCountKey(SystemCountKey.REPEAT_AWARD.getName());
                    systemCount.setCountValue(systemCount.getCountValue() + transactionScore);
                    systemCount.setUpdateTime(new Date());
                    systemCountService.update(systemCount);
                }
            }

        }
    }


    /**
     * 后台订单列表查询
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:44 2019/11/7 0007
     **/
    public Result adminListPage(AdminOrderQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);

        PageHelper.startPage(params.getPage(), params.getLimit());
        List<AdminOrderListVo> list = shoppingOrderService.adminListPage(params);
        PageInfo<AdminOrderListVo> pageInfo = new PageInfo<>(list);
        return Result.success("操作成功", pageInfo);
    }


    /**
     * 后台根据groupId查询订单详情
     *
     * @param groupId 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 4:37 2019/11/7 0007
     **/
    public Result adminFindByGroupId(String groupId) {

        //校验参数
        if (Objects.isNull(groupId)) {
            return Result.failure("groupId为空");
        }

        AdminOrderInfoVo vo = shoppingOrderService.findAdminOrderInfoVo(groupId);
        return Objects.isNull(vo) ? Result.failure("数据不存在") : Result.success("查询成功", vo);
    }


    /**
     * 发货
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:24 2019/11/8 0008
     **/
    public Result ship(ShipParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断订单是否存在
        ShoppingOrder shoppingOrder = shoppingOrderService.findByOrderNo(params.getOrderNo());
        if (Objects.isNull(shoppingOrder)) {
            return Result.failure("订单不存在");
        }

        //查询该订单的发货单
        List<ImmediatePaymentOrder> immediatePaymentOrders = immediatePaymentOrderService.listByFrontNoSet(Sets.asSets(params.getOrderNo()));
        if (Lists.isEmpty(immediatePaymentOrders)) {
            //如果发货单不存在,说明这个订单有问题，可能是下单的时候重启或者其他状况导致发货单不存在
            return Result.failure("发货单不存在");
        }

        ImmediatePaymentOrder immediatePaymentOrder = immediatePaymentOrders.get(0);

        //判断是不是已经发过货了
        if (Objects.equals(Constants.ORDER_COMPLETE, immediatePaymentOrder.getState())) {
            return Result.failure("该订单已经发过货了");
        }

        //发货
        immediatePaymentOrder.setState(Constants.ORDER_COMPLETE);
        immediatePaymentOrder.setExpressName(params.getExpressName());
        immediatePaymentOrder.setExpressCom(params.getExpressCom());
        immediatePaymentOrder.setExpressNo(params.getExpressNo());
        immediatePaymentOrder.setSendTime(new Date());

        Result update = immediatePaymentOrderService.update(immediatePaymentOrder);

        if (update.getSuccess() && Objects.equals(Constants.FOUND_SELF_EXPRESS_NAME, params.getExpressName())) {
            //如果是自提,直接完成
            completeOrder(params.getOrderNo());
        }
        return update;
    }

    /**
     * 自提发货
     * <p>
     * 如果是自提,直接就是完成状态
     *
     * @param orderNo 订单编号
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:24 2019/11/8 0008
     **/
    public Result shipByFoundSelf(String orderNo) {
        if (StringUtils.isEmpty(orderNo)) {
            return Result.failure("订单编号为空");
        }

        ShipParams shipParams = new ShipParams().setOrderNo(orderNo).
                setExpressName(Constants.FOUND_SELF_EXPRESS_NAME).
                setExpressCom(Constants.FOUND_SELF_EXPRESS_COM).
                setExpressNo(Constants.FOUND_SELF_EXPRESS_NO);
        return ship(shipParams);
    }

    /**
     * 取消订单
     * 取消之后需要将用户的钱退回去
     *
     * @param orderNo 订单编号
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:24 2019/11/8 0008
     **/
    @Transactional
    public Result adminCancelOrder(String orderNo) {

        if (StringUtils.isEmpty(orderNo)) {
            return Result.failure("订单编号为空");
        }

        //查询订单判断是否存在
        ShoppingOrder shoppingOrder = shoppingOrderService.findByOrderNo(orderNo);
        if (Objects.isNull(shoppingOrder)) {
            return Result.failure("订单不存在");
        }

        if (Objects.equals(Constants.ORDER_CANCEL, shoppingOrder.getState())) {
            return Result.failure("该订单已取消,无法重复取消");
        }

        //只有未发货的订单才可以取消
        //查询发货单
        List<ImmediatePaymentOrder> immediatePaymentOrders = immediatePaymentOrderService.listByFrontNoSet(Sets.asSets(orderNo));
        if (Lists.isEmpty(immediatePaymentOrders)) {
            //如果发货单不存在,说明这个订单有问题，可能是下单的时候重启或者其他状况导致发货单不存在
            return Result.failure("发货单不存在");
        }

        ImmediatePaymentOrder immediatePaymentOrder = immediatePaymentOrders.get(0);
        //判断是不是已经发过货了
        if (Objects.equals(Constants.ORDER_COMPLETE, immediatePaymentOrder.getState())) {
            return Result.failure("该订单已发货,无法取消");
        }

        //进行取消
        if (Objects.equals(Constants.ORDER_COMPLETE, shoppingOrder.getState())) {
            //这是付过款的,需要进行退款
            //微信敏感操作证书
            InputStream inputStream = IOUtil.inputStreamToBaseProject("apiclient_cert.p12");
            //支付金额,转化为分
            Double v = shoppingOrder.getPayable() * 100;
            WXPayUtil.refund(Constants.WX_APP_ID, Constants.WX_MCHID, Constants.WX_PAY_SIGN_KEY, inputStream,
                    Constants.WX_MCHID, shoppingOrder.getTransactionId(), shoppingOrder.getPayRecord(),
                    v.intValue(), v.intValue());
        }

        shoppingOrder.setState(Constants.ORDER_CANCEL);
        Result update = shoppingOrderService.update(shoppingOrder);

        if (update.getSuccess()) {
            //查找这个用户
            User user = userService.findById(Integer.valueOf(shoppingOrder.getCustom()));
            //用户不存在,说明有人手动删了
            if (Objects.nonNull(user) && Objects.equals(UserRole.VIP_USER.getRole(), user.getRole())) {
                //取消订单之后,判断当前用户是否是会员,如果是会员,
                //判断他是否符合会员门槛,如果不符合了,把他修改为普通用户
                String vipLimitMoneyStr = Constants.SYSTEM_CONFIG_INIT_MAP.get(SystemConfigKey.VIP_LIMIT_MONEY.getKey());
                Double vipLimitMoney = Double.valueOf(vipLimitMoneyStr);
                //保留两位小数
                vipLimitMoney = NumberUtil.doubleToFixLengthDouble(vipLimitMoney, 2);
                Integer count = shoppingOrderService.countUserPayedMoney(shoppingOrder.getCustom(), vipLimitMoney);
                if (Objects.isNull(count) || count == 0) {
                    //没有资格成为会员,打回普通用户
                    user.setRole(UserRole.COMMON_USER.getRole());
                    userService.update(user);
                }
            }

            //把这个订单的分享奖,间接奖,复购奖,业绩统计都需要相应的减去
            //现在是每个订单都是单个商品,没有影响,可以直接通过groupId查询相关联的奖项做取消操作即可,
            // 但是后面如果有购物车了需要做修改
            //分享奖取消
            CapitalFlowUser capitalFlowUserCondition = new CapitalFlowUser().setType(1).setRelationItem(shoppingOrder.getGroupId());
            List<CapitalFlowUser> capitalFlowUsers = capitalFlowUserService.listByCondition(capitalFlowUserCondition);
            if (Lists.isNotEmpty(capitalFlowUsers)) {
                //删除流水
                //减少受益人的余额
                for (CapitalFlowUser capitalFlowUser : capitalFlowUsers) {
                    Integer userId = capitalFlowUser.getUserId();
                    AccountUser accountUser = accountUserService.findByUserId(userId);
                    accountUser.setUserBalance(accountUser.getUserBalance() - capitalFlowUser.getTransactionMoney());
                    accountUser.setGrandTotalBalance(accountUser.getGrandTotalBalance() - capitalFlowUser.getTransactionMoney());
                    accountUser.setUpdateTime(new Date());
                    accountUserService.update(accountUser);
                    //删除分享奖流水信息
                    capitalFlowUserService.deleteById(capitalFlowUser.getId());
                }
            }
            //间接奖复购奖取消
            UserScoreRecode userScoreRecodeCondition = new UserScoreRecode().setRelationItem(shoppingOrder.getGroupId());
            List<UserScoreRecode> userScoreRecodes = userScoreRecodeService.listByCondition(userScoreRecodeCondition);
            if (Lists.isNotEmpty(userScoreRecodes)) {
                //只处理复购奖和间接奖
                List<Integer> containsTypes = Lists.asList(1, 2);
                for (UserScoreRecode userScoreRecode : userScoreRecodes) {
                    if (containsTypes.contains(userScoreRecode.getType())) {
                        Integer userId = userScoreRecode.getUserId();
                        UserScore userScore = userScoreService.findByUserId(userId);
                        userScore.setScore(userScore.getScore() - userScoreRecode.getTransactionScore());
                        userScore.setGrandTotalScore(userScore.getGrandTotalScore() - userScoreRecode.getTransactionScore());
                        userScore.setUpdateTime(new Date());
                        userScoreService.update(userScore);
                        //删除复购奖或者间接奖
                        userScoreRecodeService.deleteById(userScoreRecode.getId());
                    }
                }
            }

            //减去业绩统计,用户累计业绩
            UserPerformanceHistory userPerformanceHistoryCondition = new UserPerformanceHistory().setRelationItems(shoppingOrder.getGroupId());
            List<UserPerformanceHistory> userPerformanceHistories = userPerformanceHistoryService.listByCondition(userPerformanceHistoryCondition);
            if (Lists.isNotEmpty(userPerformanceHistories)) {
                for (UserPerformanceHistory userPerformanceHistory : userPerformanceHistories) {
                    //删除业绩记录,减去用户业绩
                    //查询用户该季度的业绩
                    UserPerformanceCount userPerformanceWithQuarter = userPerformanceCountService.
                            findUserPerformanceWithQuarter(userPerformanceHistory.getUserId(), userPerformanceHistory.getCurrentQuarter());
                    userPerformanceWithQuarter.setCurrentQuarterMoney(userPerformanceWithQuarter.getCurrentQuarterMoney() - userPerformanceHistory.getTransactionMoney());
                    userPerformanceWithQuarter.setUpdateTime(new Date());
                    userPerformanceCountService.update(userPerformanceWithQuarter);
                    //查询该用户总业绩
                    UserPerformanceTotal userPerformanceTotal = userPerformanceCountService.findUserPerformanceTotalByUserId(userPerformanceHistory.getUserId());
                    userPerformanceTotal.setGrandTotalPerformance(userPerformanceTotal.getGrandTotalPerformance() - userPerformanceHistory.getTransactionMoney());
                    userPerformanceTotal.setUpdateTime(new Date());
                    userPerformanceTotalService.update(userPerformanceTotal);

                    //删除业绩记录
                    userPerformanceHistoryService.deleteById(userPerformanceHistory.getId());
                }
            }
            //删除分享记录
            orderShareUserService.deleteByGroupId(shoppingOrder.getGroupId());


        }
        return update;
    }

    /**
     * 商家添加备注
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:28 2019/11/8 0008
     **/
    public Result addRemarks(AddRemarksParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //查询订单判断是否存在
        ShoppingOrder shoppingOrder = shoppingOrderService.findByOrderNo(params.getOrderNo());
        if (Objects.isNull(shoppingOrder)) {
            return Result.failure("订单不存在");
        }

        String remarks = shoppingOrder.getRemarks();
        OrderRemarks orderRemarks = JSONUtil.strToObj(remarks, OrderRemarks.class);

        orderRemarks.setShopRemarks(params.getRemarks());
        remarks = JSONUtil.objToStr(orderRemarks);

        shoppingOrder.setRemarks(remarks);

        return shoppingOrderService.update(shoppingOrder);
    }

    /**
     * 完成订单
     *
     * @param orderNo 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:28 2019/11/8 0008
     **/
    @Transactional
    public Result completeOrder(String orderNo) {

        if (Objects.isNull(orderNo)) {
            return Result.failure("订单编号为空");
        }

        //查询订单判断是否存在
        ShoppingOrder shoppingOrder = shoppingOrderService.findByOrderNo(orderNo);
        if (Objects.isNull(shoppingOrder)) {
            return Result.failure("订单不存在");
        }

        //查询发货单与收货单,判断收货单是否已收货,在本系统中收货就是完成的意思
        //查询发货单
        List<ImmediatePaymentOrder> immediatePaymentOrders = immediatePaymentOrderService.listByFrontNoSet(Sets.asSets(orderNo));
        if (Lists.isEmpty(immediatePaymentOrders)) {
            //如果发货单不存在,说明这个订单有问题，可能是下单的时候重启或者其他状况导致发货单不存在
            return Result.failure("发货单不存在");
        }

        ImmediatePaymentOrder immediatePaymentOrder = immediatePaymentOrders.get(0);

        //查询收货单
        List<ReceivingGoodsOrder> receivingGoodsOrders = receivingGoodsOrderService.listByFrontNoSet(Sets.asSets(immediatePaymentOrder.getOrderNo()));
        if (Lists.isEmpty(receivingGoodsOrders)) {
            //如果收货单不存在,说明这个订单有问题，可能是下单的时候重启或者其他状况导致发货单不存在
            return Result.failure("收货单不存在");
        }

        ReceivingGoodsOrder receivingGoodsOrder = receivingGoodsOrders.get(0);

        //判断是否已经收过货了
        if (Objects.equals(Constants.ORDER_COMPLETE, receivingGoodsOrder.getState())) {
            return Result.failure("该订单已完成,请勿重复请求");
        }

        receivingGoodsOrder.setState(Constants.ORDER_COMPLETE);

        Result update = receivingGoodsOrderService.update(receivingGoodsOrder);

        //完成之后,计算各种奖
        if (update.getSuccess()) {

            String custom = shoppingOrder.getCustom();
            Double payable = shoppingOrder.getPayable();
            String groupId = shoppingOrder.getGroupId();
            Date orderCreateTime = shoppingOrder.getCreateTime();

            //然后判断支付金额有没有到达会员支付金额,如果达到了就升级为会员
            //如果原来的角色是普通用户,第一次升级为会员,那么他的直接上级可以获得分享奖
            //普通用户升级为会员，其上级合伙人获得间接奖。此奖励为一次性奖励。此为积分显示
            //转移到付款那里就进行判断升级
//            checkUpgradeVIP(Integer.valueOf(custom), payable, groupId);

            //发放分享奖
            checkShareAward(groupId);
            //发放间接奖
            checkIndirectAward(groupId);

            //校验是否满足复购奖---转移到付款时进行调用
            checkRepeatAward(Integer.valueOf(custom), payable, groupId);

            //递归给这个用户以及他的上级用户添加业绩统计信息---转移到付款时进行调用
            userPerformanceCountService.recursiveAddUserPerformance(Integer.valueOf(custom), payable, orderCreateTime, groupId);
        }

        return update;
    }

    /**
     * 奖订单的分享奖发放
     *
     * @param groupId
     */
    public void checkShareAward(String groupId) {
        if (StringUtils.isNotEmpty(groupId)) {
            CapitalFlowUser capitalFlowUserCondition = new CapitalFlowUser().
                    setRelationItem(groupId).setType(1).
                    setArriveAccountStatus(BooleanConstant.NO_INTEGER);
            List<CapitalFlowUser> capitalFlowUsers = capitalFlowUserService.listByCondition(capitalFlowUserCondition);
            if (Lists.isNotEmpty(capitalFlowUsers)) {
                //更新复购奖为发放,并更新发放的用户的账户信息(正常逻辑只会有一个分享奖不可能会有多个)
                CapitalFlowUser capitalFlowUser = capitalFlowUsers.get(0);
                //查询账户
                AccountUser accountUser = accountUserService.findByUserId(capitalFlowUser.getUserId());
                if (Objects.nonNull(accountUser)) {
                    accountUser.setUserBalance(accountUser.getUserBalance() + capitalFlowUser.getTransactionMoney()).
                            setGrandTotalBalance(accountUser.getGrandTotalBalance() + capitalFlowUser.getTransactionMoney()).
                            setUpdateTime(new Date());
                    //更新账户
                    accountUserService.update(accountUser);

                    //给系统统计数据添加分享奖数据
                    SystemCount systemCount = systemCountService.findByCountKey(SystemCountKey.SHARE_AWARD.getName());
                    systemCount.setCountValue(systemCount.getCountValue() + capitalFlowUser.getTransactionMoney());
                    systemCount.setUpdateTime(new Date());
                    systemCountService.update(systemCount);

                    capitalFlowUser.setUserBalance(accountUser.getUserBalance() + capitalFlowUser.getTransactionMoney()).
                            setArriveAccountStatus(BooleanConstant.YES_INTEGER).setCreateTime(new Date());
                    //将分享奖发放
                    capitalFlowUserService.update(capitalFlowUser);
                }
            }
        }
    }

    /**
     * 奖订单的间接奖发放
     *
     * @param groupId
     */
    public void checkIndirectAward(String groupId) {
        if (StringUtils.isNotEmpty(groupId)) {
            UserScoreRecode userScoreRecodeCondition = new UserScoreRecode().
                    setRelationItem(groupId).setType(1).
                    setArriveAccountStatus(BooleanConstant.NO_INTEGER);
            List<UserScoreRecode> userScoreRecodes = userScoreRecodeService.listByCondition(userScoreRecodeCondition);
            if (Lists.isNotEmpty(userScoreRecodes)) {
                //理论上只会有一个
                UserScoreRecode userScoreRecode = userScoreRecodes.get(0);
                //查询用户积分
                UserScore userScore = userScoreService.findByUserId(userScoreRecode.getUserId());

                //修改用户积分
                userScore.setScore(userScore.getScore() + userScoreRecode.getTransactionScore());
                userScore.setGrandTotalScore(userScore.getGrandTotalScore() + userScoreRecode.getTransactionScore());
                userScore.setUpdateTime(new Date());
                userScoreService.update(userScore);

                //给系统统计数据添加间接奖数据
                SystemCount systemCount = systemCountService.findByCountKey(SystemCountKey.INDIRECT_AWARD.getName());
                systemCount.setCountValue(systemCount.getCountValue() + userScoreRecode.getTransactionScore());
                systemCount.setUpdateTime(new Date());
                systemCountService.update(systemCount);

                //修改积分状态为发放
                userScoreRecode.setArriveAccountStatus(BooleanConstant.YES_INTEGER).
                        setCreateTime(new Date());
                userScoreRecodeService.update(userScoreRecode);
            }
        }
    }

    /**
     * 查询物流信息
     * 没有物流接口,留着后面写
     *
     * @param orderNo 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:29 2019/11/8 0008
     **/
    public Result findExpressInfo(String orderNo) {

        //获取订单信息
        ShoppingOrder shoppingOrder = shoppingOrderService.findByOrderNo(orderNo);
        if (Objects.isNull(shoppingOrder)) {
            return Result.failure("订单不存在");
        }

        //查询发货单
        List<ImmediatePaymentOrder> immediatePaymentOrders = immediatePaymentOrderService.listByFrontNoSet(Sets.asSets(orderNo));
        if (Lists.isEmpty(immediatePaymentOrders)) {
            return Result.failure("发货单不存在");
        }

        ImmediatePaymentOrder immediatePaymentOrder = immediatePaymentOrders.get(0);
        if (!Objects.equals(Constants.ORDER_COMPLETE, immediatePaymentOrder.getState())) {
            return Result.failure("订单未发货");
        }

        String expressCom = immediatePaymentOrder.getExpressCom();
        String expressNo = immediatePaymentOrder.getExpressNo();

        Map requestDataMap = Maps.mapBuilder(MapType.HASH_MAP).put("ShipperCode", expressCom).put("LogisticCode", expressNo).build();
        String requestData = JSONUtil.objToStr(requestDataMap);
        try {
            HttpClientUtils params = HttpClientUtils.getInstance();

            params.putParams("RequestData", URLEncoder.encode(requestData, CharSetType.UTF8.name()));
            params.putParams("EBusinessID", Constants.EXPRESS_USERID);
            params.putParams("RequestType", "1002");

            //数据内容签名：把(请求内容(未编码)+AppKey)进行MD5加密，然后Base64编码，最后 进行URL(utf-8)编码
            String dataSign = Base64.getEncoder().encodeToString(MD5EncryptUtil.MD5StringToHexString((requestData + Constants.EXPRESS_APIKEY)).toLowerCase().getBytes(CharSetType.UTF8.getType()));
            params.putParams("DataSign", URLEncoder.encode(dataSign, CharSetType.UTF8.name()));
            params.putParams("DataType", "2");

            Map map = params.doPost("http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx").toMap();
            Object success = map.get("Success");
            if (Objects.equals("true", success.toString())) {
                //请求成功
                Object traces = map.get("Traces");
                if (Objects.nonNull(traces)) {
                    List<Map> tracesList = (List<Map>) traces;
                    Collections.reverse(tracesList);
                    String telephone = subStrTracesPhone(tracesList);

                    Map build = Maps.mapBuilder(MapType.HASH_MAP).
                            put("tracesList", tracesList).
                            put("telephone", telephone).build();
                    return Result.success("查询成功", build);
                }
            }

            Object reason = map.get("Reason");
            return Result.failure("查询失败", reason.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return Result.failure("查询失败");
    }

    /**
     * 截取物流信息中的号码
     *
     * @param tracesList 1
     * @return java.lang.String
     * @since 下午 5:16 2019/11/21 0021
     **/
    private String subStrTracesPhone(List<Map> tracesList) {
        if (Lists.isNotEmpty(tracesList)) {
            for (Map traceMap : tracesList) {
                String acceptStation = (String) traceMap.get("AcceptStation");
                if (Pattern.matches("^[\\s\\S]+1[3-9]\\d{9}[\\s\\S]+$", acceptStation)) {
                    int index = acceptStation.indexOf("1");
                    while (index != -1 && acceptStation.length() > index + 11) {
                        String substring = acceptStation.substring(index, index + 11);
                        if (Pattern.matches(RegConstant.MOBILE_PHONE, substring)) {
                            //命中
                            return substring;
                        }
                        index = acceptStation.indexOf("1", index + 1);
                    }
                }
            }
        }
        return null;
    }


    /**
     * app 订单列表查询
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:11 2019/11/15 0015
     **/
    public Result appListPage(APPOrderQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);

        //当前用户
        Optional<User> optional = userService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        params.setCurrentUserId(optional.get().getId());

        //开始查询
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<AppOrderListVo> list = shoppingOrderService.listAppOrderListVo(params);
        PageInfo<AppOrderListVo> pageInfo = new PageInfo<>(list);
        return Result.success("查询成功", pageInfo);
    }

    /**
     * 根据groupId查询订单详情
     *
     * @param groupId 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:12 2019/11/15 0015
     **/
    public Result appFindByGroupId(String groupId) {

        if (StringUtils.isEmpty(groupId)) {
            return Result.failure("组订单编号为空");
        }

        //当前用户
        Optional<User> optional = userService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        APPOrderQueryParams params = new APPOrderQueryParams();
        params.setGroupId(groupId);
        //开始查询
        List<AppOrderListVo> list = shoppingOrderService.listAppOrderListVo(params);

        return Lists.isEmpty(list) ? Result.failure("数据不存在") : Result.success("查询成功", list.get(0));
    }


    /**
     * 微信支付订单
     *
     * @param groupId 组订单编号
     * @param openId  前端直接传openId过来
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 5:51 2019/11/19 0019
     **/
    public Result wxPay(String groupId, String openId, HttpServletRequest request) {

        //校验参数
        if (StringUtils.isEmpty(groupId)) {
            return Result.failure("groupId为空");
        }

        //当前用户
        Optional<User> optional = userService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        //判断当前用户是否绑定了微信
        ThirdPartyLogin thirdPartyLogin = thirdPartyLoginService.findByUserIdAndType(optional.get().getId(), 1);
        if (Objects.isNull(thirdPartyLogin) && StringUtils.isEmpty(openId)) {
            return Result.failure("该用户未绑定微信,请先绑定微信");
        }

        //查询订单金额
        ShoppingOrder shoppingOrderCondition = new ShoppingOrder().setGroupId(groupId);
        List<ShoppingOrder> shoppingOrders = shoppingOrderService.listByCondition(shoppingOrderCondition);
        if (Lists.isEmpty(shoppingOrders)) {
            return Result.failure("订单不存在");
        }

        Double payable = shoppingOrders.get(0).getPayable();
        payable = NumberUtil.doubleToFixLengthDouble(payable, 2);
        Double v = payable * 100;

        String notifyUrl = HttpUtils.currentRequestUrlPrefix(request) + "/app/order/wxNotify";
        //支付订单号
        String payOrderNo = RandomUtil.createUUID();
        for (ShoppingOrder order : shoppingOrders) {
            order.setPayRecord(payOrderNo);
            shoppingOrderService.update(order);
        }

        Map map = WXPayUtil.createPreOrder(Constants.WX_APP_ID, Constants.WX_MCHID, "恩德生态",
                Constants.WX_PAY_SIGN_KEY, v.intValue(),
                notifyUrl, PayType.JSAPI, null == thirdPartyLogin ? openId : thirdPartyLogin.getOpenId(), payOrderNo, request);

        return Result.success("操作成功", map);
    }


    /**
     * 微信支付回调地址
     *
     * @param request 1
     * @return java.lang.String
     * @since 上午 9:28 2019/11/20 0020
     **/
    public String wxNotify(HttpServletRequest request) {

        Map map = WXPayUtil.notify(request);
        log.info("微信支付回调" + map);
        String result_code = map.get("result_code").toString();
        String sign = map.get("sign").toString();
        //构建签名,判断信息正确性
        map.remove("sign");
        String paramsStr = HttpClientUtils.getInstance().putParams(map).paramsToString(true);
        String localSign = MD5EncryptUtil.MD5StringToHexString(paramsStr + "&key=" + Constants.WX_PAY_SIGN_KEY);
        if (localSign.equals(sign)) {
            if (StringUtils.isNotEmpty(result_code) && Objects.equals("SUCCESS", result_code)) {
                // 更新订单信息
                log.info("微信支付回调,更新订单信息:" + map);
                //订单支付成功，接收到回调信息 获取支付流水号 存入数据库
                //商户订单号
                String outTradeNo = map.get("out_trade_no").toString();
                //支付流水号
                String transactionId = map.get("transaction_id").toString();
                //通过商户订单号查询对应的订单信息，添加第三方支付的流水号
                ShoppingOrder shoppingOrderCondition = new ShoppingOrder().setPayRecord(outTradeNo).setState(Constants.ORDER_INIT);
                List<ShoppingOrder> shoppingOrders = shoppingOrderService.listByCondition(shoppingOrderCondition);
                //修改为已支付
                if (Lists.isNotEmpty(shoppingOrders)) {
                    //处理订单
                    checkPayed(shoppingOrders, "微信", outTradeNo, transactionId);
                }

                // 发送通知等
                Map<String, Object> xml = new HashMap<>();
                xml.put("return_code", "SUCCESS");
                xml.put("return_msg", "OK");
                return XmlUtil.parseMapToXml(xml);
            }
        }
        return null;
    }


}

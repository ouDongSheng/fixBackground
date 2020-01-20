package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.JSONUtil;
import com.github.chenlijia1111.utils.core.NumberUtil;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Sets;
import com.logicalthining.endeshop.common.enums.UserRole;
import com.logicalthining.endeshop.common.pojo.Constants;
import com.logicalthining.endeshop.common.pojo.order.OrderRemarks;
import com.logicalthining.endeshop.common.pojo.voucher.DiscountCoupon;
import com.logicalthining.endeshop.common.requestVo.count.CountSaleQueryParams;
import com.logicalthining.endeshop.common.requestVo.order.APPOrderQueryParams;
import com.logicalthining.endeshop.common.requestVo.order.AdminOrderQueryParams;
import com.logicalthining.endeshop.common.requestVo.shareManage.ShareManageQueryParams;
import com.logicalthining.endeshop.common.responseVo.count.SaleCountProvinceVo;
import com.logicalthining.endeshop.common.responseVo.order.*;
import com.logicalthining.endeshop.common.responseVo.product.AdminProductVo;
import com.logicalthining.endeshop.common.responseVo.product.GoodSpecVo;
import com.logicalthining.endeshop.common.responseVo.product.GoodVo;
import com.logicalthining.endeshop.common.responseVo.shareManage.ShareManageVo;
import com.logicalthining.endeshop.dao.*;
import com.logicalthining.endeshop.entity.ImmediatePaymentOrder;
import com.logicalthining.endeshop.entity.ReceivingGoodsOrder;
import com.logicalthining.endeshop.entity.ShoppingOrder;
import com.logicalthining.endeshop.entity.User;
import com.logicalthining.endeshop.service.ShoppingOrderServiceI;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单
 *
 * @author chenLiJia
 * @since 2019-11-05 16:39:24
 **/
@Service
public class ShoppingOrderServiceImpl implements ShoppingOrderServiceI {


    @Resource
    private ShoppingOrderMapper shoppingOrderMapper;//购物单
    @Resource
    private ImmediatePaymentOrderMapper immediatePaymentOrderMapper;//发货单
    @Resource
    private ReceivingGoodsOrderMapper receivingGoodsOrderMapper;//收货单
    @Resource
    private UserMapper userMapper;//用户
    @Resource
    private GoodsMapper goodsMapper;//商品
    @Resource
    private ProductMapper productMapper;///产品
    @Resource
    private GoodSpecMapper goodSpecMapper;//商品规格


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    public Result add(ShoppingOrder params) {

        int i = shoppingOrderMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 16:39:24
     **/
    public Result update(ShoppingOrder params) {

        int i = shoppingOrderMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 条件查询订单
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.ShoppingOrder>
     * @since 上午 10:54 2019/11/6 0006
     **/
    @Override
    public List<ShoppingOrder> listByCondition(ShoppingOrder condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition, true);
        return shoppingOrderMapper.select(condition);
    }

    /**
     * 条件统计数量
     *
     * @param condition 1
     * @return java.lang.Integer
     * @since 上午 11:59 2019/11/21 0021
     **/
    @Override
    public Integer countByCondition(ShoppingOrder condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition, true);
        return shoppingOrderMapper.selectCount(condition);
    }


    /**
     * 列表查询分享记录
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.shareManage.ShareManageVo>
     * @since 上午 11:02 2019/11/7 0007
     **/
    @Override
    public List<ShareManageVo> listShareManageVo(ShareManageQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        return shoppingOrderMapper.listShareManageVo(params);
    }


    /**
     * 后台查询订单列表
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.order.AdminOrderListVo>
     * @since 下午 2:55 2019/11/7 0007
     **/
    @Override
    public List<AdminOrderListVo> adminListPage(AdminOrderQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);

        //查询出来的数据之后 groupId 一个字段 所以还需要进行关联数据
        List<AdminOrderListVo> list = shoppingOrderMapper.adminListPage(params);

        //进行关联数据
        if (Lists.isNotEmpty(list)) {
            //组订单集合
            Set<String> groupIdSet = list.stream().map(e -> e.getGroupId()).collect(Collectors.toSet());
            //查询订单
            List<ShoppingOrder> shoppingOrders = shoppingOrderMapper.listByGroupIdSet(groupIdSet);
            //购物单编号集合
            Set<String> orderNoSet = shoppingOrders.stream().map(e -> e.getOrderNo()).collect(Collectors.toSet());
            //用户id集合
            Set<Integer> customIdSet = shoppingOrders.stream().map(e -> Integer.valueOf(e.getCustom())).collect(Collectors.toSet());
            //查询发货单
            List<ImmediatePaymentOrder> immediatePaymentOrders = immediatePaymentOrderMapper.listByFrontOrderNoSet(orderNoSet);
            //查询组订单状态
            Map<String, Integer> groupStateMap = findGroupStateByGroupIdSet(groupIdSet);
            //查询所有用户信息
            List<User> users = userMapper.listByIdSet(customIdSet);
            //开始处理数据
            for (AdminOrderListVo vo : list) {
                String groupId = vo.getGroupId();

                //订单信息
                Optional<ShoppingOrder> any = shoppingOrders.stream().filter(e -> Objects.equals(e.getGroupId(), groupId)).findAny();
                if (any.isPresent()) {
                    ShoppingOrder shoppingOrder = any.get();
                    vo.setOrderCreateTime(shoppingOrder.getCreateTime());

                    //查找用户账户
                    Integer userId = Integer.valueOf(shoppingOrder.getCustom());
                    Optional<User> any1 = users.stream().filter(e -> Objects.equals(e.getId(), userId)).findAny();
                    if (any1.isPresent()) {
                        User user = any1.get();
                        vo.setUserAccount(user.getAccount());
                        Integer role = user.getRole();
                        vo.setUserRoleName(UserRole.transferRoleName(role));
                    }

                    vo.setPayAble(shoppingOrder.getPayable());

                    //查询发货单信息
                    Optional<ImmediatePaymentOrder> any2 = immediatePaymentOrders.stream().filter(e -> Objects.equals(e.getFrontOrder(), shoppingOrder.getOrderNo())).findAny();
                    if (any2.isPresent()) {
                        ImmediatePaymentOrder immediatePaymentOrder = any2.get();
                        vo.setRecUserName(immediatePaymentOrder.getRecUser());
                        vo.setRecAddress(immediatePaymentOrder.getRecAddr());
                        vo.setRecTelephone(immediatePaymentOrder.getRecTel());
                    }
                }

                //组订单状态
                Integer state = groupStateMap.get(groupId);
                vo.setOrderState(state);
            }
        }
        return list;
    }

    /**
     * 通过订单编号集合查询订单状态
     * 订单状态定义 1初始状态 2已付款 3已发货 4已收货 5已取消
     *
     * @param orderNoSet 1
     * @return java.util.Map<java.lang.String, java.lang.Integer>
     * @since 下午 3:38 2019/11/7 0007
     **/
    @Override
    public Map<String, Integer> findOrderStateByOrderNoSet(Set<String> orderNoSet) {

        Map<String, Integer> map = new HashMap<>();

        if (Sets.isNotEmpty(orderNoSet)) {
            //查询这些订单的订单信息、发货单、收货单
            //所有购物单
            List<ShoppingOrder> shoppingOrders = shoppingOrderMapper.listByOrderNoSet(orderNoSet);
            //所有发货单
            List<ImmediatePaymentOrder> immediatePaymentOrders = immediatePaymentOrderMapper.listByFrontOrderNoSet(orderNoSet);
            //所有发货单单号集合
            Set<String> sendOrderNoSet = immediatePaymentOrders.stream().map(e -> e.getOrderNo()).collect(Collectors.toSet());
            //所有收货单
            List<ReceivingGoodsOrder> receivingGoodsOrders = receivingGoodsOrderMapper.listByFrontOrderNoSet(sendOrderNoSet);

            for (String orderNo : orderNoSet) {
                //查询这个订单的信息
                Optional<ShoppingOrder> any = shoppingOrders.stream().filter(e -> Objects.equals(orderNo, e.getOrderNo())).findAny();
                if (any.isPresent()) {
                    ShoppingOrder shoppingOrder = any.get();
                    Integer state = shoppingOrder.getState();
                    if (Objects.equals(state, Constants.ORDER_INIT)) {
                        map.put(orderNo, 1);
                    } else if (Objects.equals(state, Constants.ORDER_CANCEL)) {
                        map.put(orderNo, 5);
                    } else if (Objects.equals(state, Constants.ORDER_COMPLETE)) {
                        //已支付
                        //判断有没有发货
                        Optional<ImmediatePaymentOrder> any1 = immediatePaymentOrders.stream().filter(e -> Objects.equals(orderNo, e.getFrontOrder())).findAny();
                        if (any1.isPresent()) {
                            ImmediatePaymentOrder immediatePaymentOrder = any1.get();
                            Integer sendState = immediatePaymentOrder.getState();
                            if (Objects.equals(Constants.ORDER_INIT, sendState)) {
                                //未发货
                                map.put(orderNo, 2);
                            } else if (Objects.equals(Constants.ORDER_COMPLETE, sendState)) {
                                //已发货
                                map.put(orderNo, 3);
                            }

                            //判断是否已收货
                            Optional<ReceivingGoodsOrder> any2 = receivingGoodsOrders.stream().filter(e -> Objects.equals(immediatePaymentOrder.getOrderNo(), e.getFrontOrder())).findAny();
                            if (any2.isPresent()) {
                                ReceivingGoodsOrder receivingGoodsOrder = any2.get();
                                Integer receiveState = receivingGoodsOrder.getState();
                                if (Objects.equals(Constants.ORDER_COMPLETE, receiveState)) {
                                    //已收货
                                    map.put(orderNo, 4);
                                }
                            }
                        }
                    }
                }
                //没有找到,订单不存在
            }
        }

        return map;
    }

    /**
     * 通过组订单id集合查询组订单状态
     *
     * @param groupIdSet 1
     * @return java.util.Map<java.lang.String, java.lang.Integer>
     * @since 下午 3:39 2019/11/7 0007
     **/
    @Override
    public Map<String, Integer> findGroupStateByGroupIdSet(Set<String> groupIdSet) {

        Map<String, Integer> map = new HashMap<>();

        if (Sets.isNotEmpty(groupIdSet)) {
            List<ShoppingOrder> shoppingOrders = shoppingOrderMapper.listByGroupIdSet(groupIdSet);
            //订单编号集合
            Set<String> orderNoSet = shoppingOrders.stream().map(e -> e.getOrderNo()).collect(Collectors.toSet());
            //查询这些订单的状态
            Map<String, Integer> orderStatusMap = findOrderStateByOrderNoSet(orderNoSet);

            for (String groupId : groupIdSet) {
                //查找这个组的订单
                List<ShoppingOrder> groupOrderList = shoppingOrders.stream().filter(e -> Objects.equals(e.getGroupId(), groupId)).collect(Collectors.toList());

                //查找这些订单的订单状态 取最小状态来表示组订单的状态
                Optional<Integer> minStatus = groupOrderList.stream().map(e -> orderStatusMap.get(e.getOrderNo())).
                        collect(Collectors.toList()).stream().filter(e -> Objects.nonNull(e)).min(Comparator.comparing(e -> e));

                if (minStatus.isPresent()) {
                    map.put(groupId, minStatus.get());
                }
            }
        }

        return map;
    }


    /**
     * 后台查询订单详情
     *
     * @param groupId 组订单id
     * @return com.logicalthining.endeshop.common.responseVo.order.AdminOrderInfoVo
     * @since 下午 6:25 2019/11/7 0007
     **/
    @Override
    public AdminOrderInfoVo findAdminOrderInfoVo(String groupId) {

        if (StringUtils.isNotEmpty(groupId)) {

            Map<String, Integer> groupStateMap = findGroupStateByGroupIdSet(Sets.asSets(groupId));

            //购物单
            List<ShoppingOrder> shoppingOrders = shoppingOrderMapper.listByGroupIdSet(Sets.asSets(groupId));
            if (Lists.isNotEmpty(shoppingOrders)) {

                //子订单编号集合
                Set<String> orderNoSet = shoppingOrders.stream().map(e -> e.getOrderNo()).collect(Collectors.toSet());
                //子订单状态
                Map<String, Integer> orderStateMap = findOrderStateByOrderNoSet(orderNoSet);

                ShoppingOrder shoppingOrder = shoppingOrders.get(0);
                //查询用户下单信息
                AdminOrderInfoVo adminOrderInfoVo = new AdminOrderInfoVo();
                adminOrderInfoVo.setGroupId(groupId);
                //组订单状态
                adminOrderInfoVo.setGroupOrderStatus(groupStateMap.get(groupId));
                //用户信息
                User user = userMapper.selectByPrimaryKey(Integer.valueOf(shoppingOrder.getCustom()));
                if (Objects.nonNull(user)) {
                    adminOrderInfoVo.setCustomUserAccount(user.getAccount());
                }
                adminOrderInfoVo.setOrderCreateTime(shoppingOrder.getCreateTime());
                //查询收货信息
                List<ImmediatePaymentOrder> immediatePaymentOrders = immediatePaymentOrderMapper.listByFrontOrderNoSet(Sets.asSets(shoppingOrder.getOrderNo()));
                if (Lists.isNotEmpty(immediatePaymentOrders)) {
                    ImmediatePaymentOrder immediatePaymentOrder = immediatePaymentOrders.get(0);
                    adminOrderInfoVo.setRecUserName(immediatePaymentOrder.getRecUser());
                    adminOrderInfoVo.setRecTelephone(immediatePaymentOrder.getRecTel());
                    adminOrderInfoVo.setRecAddress(immediatePaymentOrder.getRecAddr());

                }
                adminOrderInfoVo.setPayTime(shoppingOrder.getPayTime());
                //订单总金额
                Double orderAmountMoney = shoppingOrders.stream().collect(Collectors.summingDouble(ShoppingOrder::getPayable));
                adminOrderInfoVo.setOrderAmountMoney(orderAmountMoney);
                //实付金额
                adminOrderInfoVo.setPayAble(shoppingOrder.getPayable());
                //备注
                String remarks = shoppingOrder.getRemarks();
                OrderRemarks orderRemarks = JSONUtil.strToObj(remarks, OrderRemarks.class);
                adminOrderInfoVo.setRemarks(orderRemarks.getCustomRemarks());

                //购物单信息
                List<AdminSingleOrderInfoVo> singleOrderList = Lists.newArrayList();
                for (ShoppingOrder order : shoppingOrders) {
                    AdminSingleOrderInfoVo singleOrderInfoVo = new AdminSingleOrderInfoVo();
                    singleOrderInfoVo.setShoppingOrderNo(order.getOrderNo());

                    //子订单状态
                    singleOrderInfoVo.setOrderStatus(orderStateMap.get(order.getOrderNo()));

                    String detailsJson = order.getDetailsJson();
                    //订单产品快照
                    AdminProductVo orderProductSnapshot = JSONUtil.strToObj(detailsJson, AdminProductVo.class);

                    singleOrderInfoVo.setProductId(orderProductSnapshot.getId());
                    singleOrderInfoVo.setGoodId(order.getGoodsId());
                    singleOrderInfoVo.setProductImage(orderProductSnapshot.getSmallPic());
                    singleOrderInfoVo.setProductName(orderProductSnapshot.getName());

                    //商品规格名称
                    List<GoodVo> goodVoList = orderProductSnapshot.getGoodVoList();
                    Optional<GoodVo> any = goodVoList.stream().filter(e -> Objects.equals(e.getId(), order.getGoodsId())).findAny();
                    if (any.isPresent()) {
                        GoodVo goodVo = any.get();
                        List<GoodSpecVo> goodSpecVoList = goodVo.getGoodSpecVoList();
                        String sku = goodSpecVoList.stream().map(e -> e.getSpecValue()).collect(Collectors.joining(" "));
                        //规格名称
                        singleOrderInfoVo.setSku(sku);
                        //商品编号
                        singleOrderInfoVo.setGoodNo(goodVo.getGoodNo());
                    }
                    singleOrderInfoVo.setUnitPrice(order.getGoodPrice());
                    singleOrderInfoVo.setCount(order.getCount());
                    //商家备注
                    singleOrderInfoVo.setRemarks(orderRemarks.getShopRemarks());
                    //子订单发货信息
                    Optional<ImmediatePaymentOrder> any1 = immediatePaymentOrders.stream().filter(e -> Objects.equals(e.getFrontOrder(), singleOrderInfoVo.getShoppingOrderNo())).findAny();
                    if (any1.isPresent()) {
                        ImmediatePaymentOrder immediatePaymentOrder = any1.get();
                        singleOrderInfoVo.setExpressName(immediatePaymentOrder.getExpressName());
                        singleOrderInfoVo.setExpressNo(immediatePaymentOrder.getExpressNo());
                    }

                    singleOrderList.add(singleOrderInfoVo);
                }

                adminOrderInfoVo.setSingleOrderList(singleOrderList);

                return adminOrderInfoVo;
            }
        }
        return null;
    }

    /**
     * 通过订单编号查询订单信息
     *
     * @param orderNo 1
     * @return com.logicalthining.endeshop.entity.ShoppingOrder
     * @since 上午 9:37 2019/11/8 0008
     **/
    @Override
    public ShoppingOrder findByOrderNo(String orderNo) {
        if (StringUtils.isNotEmpty(orderNo)) {
            return shoppingOrderMapper.selectByPrimaryKey(orderNo);
        }
        return null;
    }

    /**
     * 统计每个省的销售值
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.count.SaleCountProvinceVo>
     * @since 下午 3:55 2019/11/12 0012
     **/
    @Override
    public List<SaleCountProvinceVo> listSaleCountProvinceVo(CountSaleQueryParams params) {

        Date startTime = null;
        Date endTime = null;

        if (Objects.nonNull(params.getYear())) {
            DateTime startDateTime = new DateTime().withYear(params.getYear());
            DateTime endDateTime = new DateTime().withYear(params.getYear());
            //判断是否有具体查询某个月
            if (Objects.nonNull(params.getMonth())) {
                startDateTime = startDateTime.withMonthOfYear(params.getMonth());
                endDateTime = endDateTime.withMonthOfYear(params.getMonth());
                int maximumValue = startDateTime.dayOfMonth().getMaximumValue();
                startTime = startDateTime.withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDate();
                endTime = endDateTime.withDayOfMonth(maximumValue).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate();
            } else {
                startTime = startDateTime.withMonthOfYear(1).withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDate();
                endTime = endDateTime.withMonthOfYear(12).withDayOfMonth(31).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate();
            }
        }

        return shoppingOrderMapper.listSaleCountProvinceVo(startTime, endTime);
    }

    /**
     * 客户端订单列表查询
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.order.AppOrderListVo>
     * @since 上午 10:34 2019/11/15 0015
     **/
    @Override
    public List<AppOrderListVo> listAppOrderListVo(APPOrderQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        //查询出来的数据之后 groupId 一个字段 所以还需要进行关联数据
        List<AppOrderListVo> list = shoppingOrderMapper.listAppOrderListVo(params);

        //进行关联数据
        if (Lists.isNotEmpty(list)) {
            //组订单集合
            Set<String> groupIdSet = list.stream().map(e -> e.getGroupId()).collect(Collectors.toSet());
            //查询订单
            List<ShoppingOrder> shoppingOrders = shoppingOrderMapper.listByGroupIdSet(groupIdSet);
            //购物单编号集合
            Set<String> orderNoSet = shoppingOrders.stream().map(e -> e.getOrderNo()).collect(Collectors.toSet());
            //查询发货单
            List<ImmediatePaymentOrder> immediatePaymentOrders = immediatePaymentOrderMapper.listByFrontOrderNoSet(orderNoSet);
            //发货单编号集合
            Set<String> sendOrderNo = immediatePaymentOrders.stream().map(e -> e.getOrderNo()).collect(Collectors.toSet());
            List<ReceivingGoodsOrder> receivingGoodsOrders = receivingGoodsOrderMapper.listByFrontOrderNoSet(sendOrderNo);
            //查询组订单状态
            Map<String, Integer> groupStateMap = findGroupStateByGroupIdSet(groupIdSet);
            //查询所有订单状态
            Map<String, Integer> orderStateMap = findOrderStateByOrderNoSet(orderNoSet);
            //开始处理数据
            for (AppOrderListVo vo : list) {
                String groupId = vo.getGroupId();

                //订单信息
                List<ShoppingOrder> collect = shoppingOrders.stream().filter(e -> Objects.equals(e.getGroupId(), groupId)).collect(Collectors.toList());
                if (Lists.isNotEmpty(collect)) {
                    ShoppingOrder shoppingOrder = collect.get(0);
                    //订单总金额
                    Double orderAmountTotal = collect.stream().collect(Collectors.summingDouble(ShoppingOrder::getOrderAmountTotal));
                    vo.setGroupOrderTotalMoney(orderAmountTotal);

                    //重复购买抵扣金额,查询订单中使用的折扣券
                    String orderCoupon = shoppingOrder.getOrderCoupon();
                    if (StringUtils.isNotEmpty(orderCoupon)) {
                        List<HashMap> couponList = JSONUtil.strToList(orderCoupon, ArrayList.class, HashMap.class);
                        if (Lists.isNotEmpty(couponList)) {
                            Optional<HashMap> any = couponList.stream().filter(e -> Objects.equals(e.get("voucherKey").toString(), DiscountCoupon.class.getSimpleName())
                                    && Objects.equals(e.get("effective"), BooleanConstant.YES_INTEGER)).findAny();
                            if (any.isPresent()) {
                                HashMap hashMap = any.get();
                                Double discount = (Double) hashMap.get("discount");
                                double repeatBuyDiscountMoney = orderAmountTotal * (1 - discount);
                                //保留两位小数
                                repeatBuyDiscountMoney = NumberUtil.doubleToFixLengthDouble(repeatBuyDiscountMoney, 2);
                                vo.setRepeatBuyDiscountMoney(repeatBuyDiscountMoney);
                            }
                        }
                    }
                    //支付金额
                    vo.setPayable(shoppingOrder.getPayable());
                    //下单时间
                    vo.setCreateTime(shoppingOrder.getCreateTime());

                    //查询发货单信息
                    Optional<ImmediatePaymentOrder> any2 = immediatePaymentOrders.stream().filter(e -> Objects.equals(e.getFrontOrder(), shoppingOrder.getOrderNo())).findAny();
                    if (any2.isPresent()) {
                        ImmediatePaymentOrder immediatePaymentOrder = any2.get();
                        vo.setRecUserName(immediatePaymentOrder.getRecUser());
                        vo.setRecAddress(immediatePaymentOrder.getRecAddr());
                        vo.setRecTelephone(immediatePaymentOrder.getRecTel());
                    }

                    List<AppSingleOrderListVo> singleOrderListVoList = Lists.newArrayList();
                    //子订单信息
                    for (ShoppingOrder order : collect) {

                        AppSingleOrderListVo singleOrderListVo = new AppSingleOrderListVo();
                        singleOrderListVo.setShoppingOrderNo(order.getOrderNo());
                        //订单备注
                        String remarks = order.getRemarks();
                        OrderRemarks orderRemarks = JSONUtil.strToObj(remarks, OrderRemarks.class);
                        singleOrderListVo.setRemarks(orderRemarks.getCustomRemarks());

                        //订单产品快照
                        String detailsJson = order.getDetailsJson();
                        AdminProductVo adminProductVo = JSONUtil.strToObj(detailsJson, AdminProductVo.class);
                        //产品图片
                        singleOrderListVo.setProductImage(adminProductVo.getSmallPic());
                        //产品名称
                        singleOrderListVo.setProductName(adminProductVo.getName());
                        //规格名称
                        List<GoodVo> goodVoList = adminProductVo.getGoodVoList();
                        Optional<GoodVo> any = goodVoList.stream().filter(e -> Objects.equals(e.getId(), order.getGoodsId())).findAny();
                        if (any.isPresent()) {
                            GoodVo goodVo = any.get();
                            List<GoodSpecVo> goodSpecVoList = goodVo.getGoodSpecVoList();
                            String skuName = goodSpecVoList.stream().map(e -> e.getSpecValue()).collect(Collectors.joining(" "));
                            singleOrderListVo.setSkuName(skuName);
                        }
                        //商品价格
                        singleOrderListVo.setPrice(order.getGoodPrice());
                        //购买数量
                        singleOrderListVo.setCount(order.getCount());
                        //订单状态
                        Integer orderState = orderStateMap.get(order.getOrderNo());
                        singleOrderListVo.setOrderStatus(orderState);
                        //子订单发货信息
                        Optional<ImmediatePaymentOrder> any1 = immediatePaymentOrders.stream().filter(e -> Objects.equals(e.getFrontOrder(), order.getOrderNo())).findAny();
                        if (any1.isPresent()) {
                            ImmediatePaymentOrder immediatePaymentOrder = any1.get();
                            singleOrderListVo.setExpressName(immediatePaymentOrder.getExpressName());
                            singleOrderListVo.setExpressNo(immediatePaymentOrder.getExpressNo());
                            singleOrderListVo.setSendTime(immediatePaymentOrder.getSendTime());
                            //查询发货单的收货单信息
                            Optional<ReceivingGoodsOrder> any3 = receivingGoodsOrders.stream().filter(e -> Objects.equals(e.getFrontOrder(), immediatePaymentOrder.getOrderNo())).findAny();
                            if (any3.isPresent()) {
                                ReceivingGoodsOrder receivingGoodsOrder = any3.get();
                                singleOrderListVo.setReceiveTime(receivingGoodsOrder.getReceiveTime());
                            }
                        }

                        singleOrderListVoList.add(singleOrderListVo);
                    }

                    vo.setList(singleOrderListVoList);
                }

                //组订单状态
                Integer state = groupStateMap.get(groupId);
                vo.setGroupOrderStatus(state);
            }
        }
        return list;
    }

    /**
     * 统计系统业绩
     *
     * @param startTime 1
     * @param endTime   2
     * @return java.lang.Double
     * @since 下午 4:24 2019/12/2 0002
     **/
    @Override
    public Double countPerformance(Date startTime, Date endTime) {
        return shoppingOrderMapper.countPerformance(startTime, endTime);
    }

    /**
     * 统计用户在本系统中付款金额大于等于界限金额的订单数量
     *
     * @param userId     1
     * @param limitMoney 界限金额 大于等于
     * @return java.lang.Double
     * @since 下午 2:22 2019/12/11 0011
     **/
    @Override
    public Integer countUserPayedMoney(String userId, Double limitMoney) {
        return shoppingOrderMapper.countUserPayedMoney(userId, limitMoney);
    }


}

package com.logicalthining.endeshop.biz;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.TimeConstant;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.list.Lists;
import com.logicalthining.endeshop.common.enums.SystemCountKey;
import com.logicalthining.endeshop.common.requestVo.count.CountSaleQueryParams;
import com.logicalthining.endeshop.common.responseVo.count.ProfitCountVo;
import com.logicalthining.endeshop.common.responseVo.count.SaleCountProvinceVo;
import com.logicalthining.endeshop.common.responseVo.count.SaleCountVo;
import com.logicalthining.endeshop.entity.SystemCount;
import com.logicalthining.endeshop.service.ShoppingOrderServiceI;
import com.logicalthining.endeshop.service.SystemCountServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 统计信息
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/12 0012 下午 2:31
 **/
@Service
public class CountBiz {

    @Autowired
    private SystemCountServiceI systemCountService;//系统统计数据
    @Autowired
    private ShoppingOrderServiceI shoppingOrderService;//订单

    /**
     * 统计盈利数据
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:32 2019/11/12 0012
     **/
    public Result countProfit() {

        List<SystemCount> systemCounts = systemCountService.listAll();

        //开始处理数据
        ProfitCountVo vo = new ProfitCountVo();

        //平台总销售额
        SystemCount systemCountValue = findSystemCountValue(SystemCountKey.SYSTEM_TOTAL_SALES.getName(), systemCounts);
        vo.setTotalSales(systemCountValue.getCountValue());

        //今日销售额
        SystemCount systemCountValue1 = findSystemCountValue(SystemCountKey.NOWADAYS_SALES.getName(), systemCounts);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TimeConstant.DATE);
        if (!Objects.equals(simpleDateFormat.format(new Date()), simpleDateFormat.format(systemCountValue1.getUpdateTime()))) {
            //上次统计当日数据不是今天
            vo.setNowadaysSales(0.0);
        } else {
            vo.setNowadaysSales(systemCountValue1.getCountValue());
        }

        //分享奖
        SystemCount systemCountValue2 = findSystemCountValue(SystemCountKey.SHARE_AWARD.getName(), systemCounts);
        vo.setShareAward(systemCountValue2.getCountValue());

        //间接奖
        SystemCount systemCountValue3 = findSystemCountValue(SystemCountKey.INDIRECT_AWARD.getName(), systemCounts);
        vo.setIndirectAward(systemCountValue3.getCountValue());

        //复购奖
        SystemCount systemCountValue4 = findSystemCountValue(SystemCountKey.REPEAT_AWARD.getName(), systemCounts);
        vo.setRepeatAward(systemCountValue4.getCountValue());

        //奖金池
        SystemCount systemCountValue5 = findSystemCountValue(SystemCountKey.AWARD_POOL.getName(), systemCounts);
        vo.setAwardPool(systemCountValue5.getCountValue());

        //盈利
        //总销售额 - 其他的奖
        double profit = vo.getTotalSales() - vo.getShareAward() - vo.getIndirectAward() - vo.getRepeatAward() - vo.getAwardPool();
        vo.setProfit(profit);

        return Result.success("查询成功", vo);
    }

    /**
     * 查找系统统计值
     *
     * @param key          1
     * @param systemCounts 2
     * @return com.logicalthining.endeshop.entity.SystemCount
     * @since 下午 3:36 2019/11/12 0012
     **/
    private SystemCount findSystemCountValue(String key, List<SystemCount> systemCounts) {
        if (StringUtils.isNotEmpty(key) && Lists.isNotEmpty(systemCounts)) {
            Optional<SystemCount> any = systemCounts.stream().filter(e -> Objects.equals(e.getCountKey(), key)).findAny();
            if (any.isPresent()) {
                return any.get();
            }
        }
        SystemCount systemCount = new SystemCount();
        systemCount.setCountKey(key);
        systemCount.setCountValue(0.0);
        systemCount.setUpdateTime(new Date());
        return systemCount;
    }


    /**
     * 统计信息-销售
     * 返回每个省相对的销售值
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 3:48 2019/11/12 0012
     **/
    public Result countSale(CountSaleQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);

        List<SaleCountProvinceVo> list = shoppingOrderService.listSaleCountProvinceVo(params);
        //获取总销售金额
        Double totalSales = 0.0;
        if (Lists.isNotEmpty(list)) {
            totalSales = list.stream().collect(Collectors.summingDouble(e -> e.getSales()));
        }

        //返回对象
        SaleCountVo vo = new SaleCountVo();
        vo.setList(list);
        vo.setTotalSales(totalSales);

        return Result.success("查询成功", vo);
    }

}

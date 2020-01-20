package com.logicalthining.endeshop.biz;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.logicalthining.endeshop.common.enums.SystemConfigKey;
import com.logicalthining.endeshop.common.pojo.Constants;
import com.logicalthining.endeshop.common.requestVo.shareManage.ShareManageQueryParams;
import com.logicalthining.endeshop.common.responseVo.shareManage.ShareManageVo;
import com.logicalthining.endeshop.entity.SystemConfig;
import com.logicalthining.endeshop.service.ShoppingOrderServiceI;
import com.logicalthining.endeshop.service.SystemConfigServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 分享管理
 * <p>
 * 就是通过 {@link com.logicalthining.endeshop.entity.CapitalFlowUser}
 * 查询用户的流水 是分享奖的流水  然后把这些查出来即可
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/7 0007 上午 10:23
 **/
@Service
public class ShareManageBiz {


    @Autowired
    private SystemConfigServiceI systemConfigService;//系统设置信息
    @Autowired
    private ShoppingOrderServiceI shoppingOrderService;//订单


    /**
     * 分享奖比例设置
     *
     * @param awardRatio 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:30 2019/11/7 0007
     **/
    public Result configShareAward(Double awardRatio) {

        if (Objects.isNull(awardRatio)) {
            return Result.failure("分享奖比例为空");
        }
        if (awardRatio < 0 || awardRatio > 1) {
            return Result.failure("分享奖比例不合法");
        }

        SystemConfig systemConfig = systemConfigService.selectByConfigKey(SystemConfigKey.SHARE_AWARD_RATIO.getKey());
        systemConfig.setConfigValue(String.valueOf(awardRatio));

        return systemConfigService.update(systemConfig);
    }


    /**
     * 查询分享奖比例设置
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:30 2019/11/7 0007
     **/
    public Result findShareAwardConfig() {

        SystemConfig systemConfig = systemConfigService.selectByConfigKey(SystemConfigKey.SHARE_AWARD_RATIO.getKey());
        return Result.success("查询成功", systemConfig);
    }


    /**
     * 重置分享奖比例
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:38 2019/11/7 0007
     **/
    public Result resetShardAward() {

        SystemConfig systemConfig = systemConfigService.selectByConfigKey(SystemConfigKey.SHARE_AWARD_RATIO.getKey());

        //重置为初始值
        String initValue = Constants.SYSTEM_CONFIG_INIT_MAP.get(SystemConfigKey.SHARE_AWARD_RATIO.getKey());
        systemConfig.setConfigValue(initValue);

        return systemConfigService.update(systemConfig);
    }


    /**
     * 列表查询分享记录
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:43 2019/11/7 0007
     **/
    public Result listPageShareRecode(ShareManageQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<ShareManageVo> list = shoppingOrderService.listShareManageVo(params);
        PageInfo<ShareManageVo> pageInfo = new PageInfo<>(list);
        return Result.success("查询成功", pageInfo);
    }

}

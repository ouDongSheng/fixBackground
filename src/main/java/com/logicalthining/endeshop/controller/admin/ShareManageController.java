package com.logicalthining.endeshop.controller.admin;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.ShareManageBiz;
import com.logicalthining.endeshop.common.requestVo.shareManage.ShareManageQueryParams;
import com.logicalthining.endeshop.common.responseVo.shareManage.ShareManageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分享管理控制器
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/7 0007 上午 10:23
 **/
@RestController
@RequestMapping(value = "admin/share/manage")
@Api(tags = "分享管理")
public class ShareManageController {


    @Autowired
    private ShareManageBiz biz;//分享管理


    /**
     * 分享奖比例设置
     *
     * @param awardRatio 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:30 2019/11/7 0007
     **/
    @PostMapping(value = "shareAward/config")
    @ApiOperation(value = "分享奖比例设置")
    public Result configShareAward(Double awardRatio) {
        return biz.configShareAward(awardRatio);
    }

    /**
     * 查询分享奖比例设置
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:30 2019/11/7 0007
     **/
    @GetMapping(value = "shareAward/config")
    @ApiOperation(value = "查询分享奖比例设置")
    public Result findShareAwardConfig() {
        return biz.findShareAwardConfig();
    }

    /**
     * 重置分享奖比例
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:38 2019/11/7 0007
     **/
    @PostMapping(value = "shareAward/config/reset")
    @ApiOperation(value = "重置分享奖比例")
    public Result resetShardAward() {
        return biz.resetShardAward();
    }

    /**
     * 列表查询分享记录
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 10:43 2019/11/7 0007
     **/
    @GetMapping(value = "shareRecode/listPage")
    @ApiOperation(value = "列表查询分享记录", response = ShareManageVo.class)
    public Result listPageShareRecode(ShareManageQueryParams params) {
        return biz.listPageShareRecode(params);
    }

}

package com.logicalthining.endeshop.controller.app;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.PartnerApplyRecodeBiz;
import com.logicalthining.endeshop.common.requestVo.partnerApply.AddParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 合伙人申请记录
 *
 * @author chenLiJia
 * @since 2019-11-05 19:59:13
 **/
@RestController(value = "appPartnerApplyRecodeController")
@RequestMapping(value = "app/partner/apply/recode")
@Api(tags = "合伙人申请记录")
public class PartnerApplyRecodeController {


    @Autowired
    private PartnerApplyRecodeBiz biz;//合伙人申请记录


    /**
     * 合伙人申请
     * 只有周二才可以提现
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 8:58 2019/11/6 0006
     **/
    @PostMapping(value = "add")
    @ApiOperation(value = "合伙人申请")
    public Result add(AddParams params) {
        return biz.add(params);
    }
}

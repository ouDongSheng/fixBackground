package com.logicalthining.endeshop.controller.admin;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.PartnerApplyRecodeBiz;
import com.logicalthining.endeshop.common.requestVo.BatchUpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.partnerApply.QueryParams;
import com.logicalthining.endeshop.common.responseVo.partnerApply.PartnerApplyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 合伙人申请记录
 *
 * @author chenLiJia
 * @since 2019-11-05 19:59:13
 **/
@RestController
@RequestMapping(value = "admin/partner/apply/recode")
@Api(tags = "合伙人申请记录")
public class PartnerApplyRecodeController {


    @Autowired
    private PartnerApplyRecodeBiz biz;//合伙人申请记录


    /**
     * 查询合伙人申请列表
     * @since 上午 9:41 2019/11/6 0006
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    @GetMapping(value = "listPage")
    @ApiOperation(value = "查询合伙人申请列表",response = PartnerApplyVo.class)
    public Result listPage(QueryParams params) {
        return biz.listPage(params);
    }

    /**
     * 同意
     * @since 上午 9:41 2019/11/6 0006
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    @PostMapping(value = "agree")
    @ApiOperation(value = "同意")
    public Result agree(Integer id) {
        return biz.agree(id);
    }

    /**
     * 拒绝
     * @since 上午 9:41 2019/11/6 0006
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    @PostMapping(value = "deny")
    @ApiOperation(value = "拒绝")
    public Result deny(Integer id) {
        return biz.deny(id);
    }

    /**
     * 批量审核
     * @since 下午 1:48 2019/11/9 0009
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    @PostMapping(value = "batchReview")
    @ApiOperation(value = "批量审核")
    public Result batchReview(@RequestBody BatchUpdateStateParams params){
        return biz.batchReview(params);
    }

}

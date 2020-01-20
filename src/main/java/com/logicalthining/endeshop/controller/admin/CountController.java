package com.logicalthining.endeshop.controller.admin;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.CountBiz;
import com.logicalthining.endeshop.common.requestVo.count.CountSaleQueryParams;
import com.logicalthining.endeshop.common.responseVo.count.ProfitCountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统计信息
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/12 0012 下午 2:30
 **/
@RestController
@RequestMapping(value = "admin/count")
@Api(tags = "统计信息")
public class CountController {

    @Autowired
    private CountBiz biz;


    /**
     * 统计盈利数据
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:32 2019/11/12 0012
     **/
    @GetMapping(value = "countProfit")
    @ApiOperation(value = "统计盈利数据", response = ProfitCountVo.class)
    public Result countProfit() {
        return biz.countProfit();
    }


    /**
     * 统计信息-销售
     * 返回每个省相对的销售值
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 3:48 2019/11/12 0012
     **/
    @GetMapping(value = "countSale")
    @ApiOperation(value = "统计信息-销售")
    public Result countSale(CountSaleQueryParams params) {
        return biz.countSale(params);
    }

}

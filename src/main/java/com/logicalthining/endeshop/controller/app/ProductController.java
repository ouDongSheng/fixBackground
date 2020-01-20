package com.logicalthining.endeshop.controller.app;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.ProductBiz;
import com.logicalthining.endeshop.common.requestVo.product.AppProductQueryParams;
import com.logicalthining.endeshop.common.responseVo.product.AppProductListVo;
import com.logicalthining.endeshop.common.responseVo.product.AppProductVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 产品接口
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 上午 11:25
 **/
@RestController(value = "appProductController")
@RequestMapping(value = "app/product")
@Api(tags = "产品接口")
public class ProductController {

    @Autowired
    private ProductBiz biz;//产品


    /**
     * 查询产品列表
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:27 2019/11/5 0005
     **/
    @GetMapping(value = "listPage")
    @ApiOperation(value = "查询产品列表", response = AppProductListVo.class)
    public Result listPage(AppProductQueryParams params) {
        return biz.appListPage(params);
    }

    /**
     * 根据产品Id查询产品详情
     *
     * @param productId 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:28 2019/11/5 0005
     **/
    @GetMapping(value = "findByProductId")
    @ApiOperation(value = "根据产品Id查询产品详情", response = AppProductVo.class)
    public Result findByProductId(String productId) {
        return biz.appFindByProductId(productId);
    }

}

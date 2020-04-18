package com.logicalthining.endeshop.controller.admin;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.ProductBiz;
import com.logicalthining.endeshop.common.requestVo.product.*;
import com.logicalthining.endeshop.common.responseVo.product.AdminProductListVo;
import com.logicalthining.endeshop.common.responseVo.product.AdminProductVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品表
 *
 * @since 2019-11-01 13:46:54
 **/
@RestController
@RequestMapping(value = "admin/product")
@Api(tags = "产品接口")
public class ProductController {


    @Autowired
    private ProductBiz biz;//产品

    /**
     * 添加商品
     *
     * @param params 1
     * @since 下午 2:18 2019/11/1 0001
     **/
    @PostMapping(value = "add")
    @ApiOperation(value = "添加商品")
    public Result add(@RequestBody ProductAddParams params) {
        return biz.add(params);
    }

    /**
     * 修改商品
     *
     * @param params 1
     * @since 下午 2:18 2019/11/1 0001
     **/
    @PostMapping(value = "update")
    @ApiOperation(value = "修改商品")
    public Result update(@RequestBody ProductUpdateParams params) {
        return biz.update(params);
    }

    /**
     * 删除商品
     * 逻辑删除
     *
     * @param productId 1
     * @since 下午 2:19 2019/11/1 0001
     **/
    @PostMapping(value = "delete")
    @ApiOperation(value = "删除商品")
    public Result delete(String productId) {
        return biz.delete(productId);
    }

    /**
     * 列表查询产品
     *
     * @param params 1
     * @since 下午 2:19 2019/11/1 0001
     **/
    @GetMapping(value = "listPage")
    @ApiOperation(value = "列表查询产品", response = AdminProductListVo.class)
    public Result listPage(AdminProductQueryParams params) {
        return biz.listPage(params);
    }

    /**
     * 修改上架状态
     *
     * @param params 1
     * @since 下午 2:20 2019/11/1 0001
     **/
    @PostMapping(value = "shelfStatus/update")
    @ApiOperation(value = "修改上架状态")
    public Result updateShelfStatus(UpdateShelfStatusParams params) {
        return biz.updateShelfStatus(params);
    }


    /**
     * 编辑排序值
     *
     * @param params 1
     * @since 上午 9:27 2019/11/4 0004
     **/
    @PostMapping(value = "sortNumber/update")
    @ApiOperation(value = "编辑排序值")
    public Result updateSortNumber(UpdateSortNumberParams params) {
        return biz.updateSortNumber(params);
    }

    /**
     * 通过产品Id查询商品详情
     *
     * @param productId 1
     * @since 上午 9:21 2019/11/5 0005
     **/
    @GetMapping(value = "findByProductId")
    @ApiOperation(value = "通过产品Id查询商品详情", response = AdminProductVo.class)
    public Result findByProductId(String productId) {
        return biz.findByProductId(productId);
    }


    /**
     * 批量修改产品上下架状态
     *
     * @param params 1
     * @since 下午 2:50 2019/11/13 0013
     **/
    @PostMapping(value = "shelfStatus/update/batch")
    @ApiOperation(value = "批量修改产品上下架状态")
    public Result batchUpdateShelfStatus(@RequestBody BatchUpdateProductShelfStatusParams params) {
        return biz.batchUpdateShelfStatus(params);
    }

    /**
     * 批量删除产品
     *
     * @param productIdList 1
     * @since 下午 2:50 2019/11/13 0013
     **/
    @PostMapping(value = "delete/batch")
    @ApiOperation(value = "批量删除产品")
    public Result batchDelete(@RequestBody List<String> productIdList) {
        return biz.batchDelete(productIdList);
    }
}

package com.logicalthining.endeshop.controller.app;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.ClientAddressBiz;
import com.logicalthining.endeshop.common.requestVo.UpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.clientAddress.AddParams;
import com.logicalthining.endeshop.common.requestVo.clientAddress.UpdateParams;
import com.logicalthining.endeshop.entity.ClientAddress;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户地址信息表
 *
 * @author chenLiJia
 * @since 2019-11-05 13:58:15
 **/
@RestController
@RequestMapping(value = "app/user/address")
@Api(tags = "用户收货地址")
public class ClientAddressController {


    @Autowired
    private ClientAddressBiz biz;//收货地址

    /**
     * 添加收货地址
     *
     * @param params 1
     * @return com.logicalthinking.jiuyou.common.pojo.Result
     * @author chenlijia
     * @since 下午 12:47 2019/8/15 0015
     **/
    @PostMapping(value = "add")
    @ApiOperation(value = "添加收货地址")
    public Result add(AddParams params) {
        return biz.add(params);
    }


    /**
     * 编辑收货地址
     *
     * @param params 1
     * @return com.logicalthinking.jiuyou.common.pojo.Result
     * @author chenlijia
     * @since 下午 12:47 2019/8/15 0015
     **/
    @PostMapping(value = "update")
    @ApiOperation(value = "编辑收货地址")
    public Result update(UpdateParams params) {
        return biz.update(params);
    }

    /**
     * 删除收货地址
     *
     * @param id 1
     * @return com.logicalthinking.jiuyou.common.pojo.Result
     * @author chenlijia
     * @since 下午 12:47 2019/8/15 0015
     **/
    @PostMapping(value = "delete")
    @ApiOperation(value = "删除收货地址")
    public Result delete(Integer id) {
        return biz.delete(id);
    }


    /**
     * 查询当前用户收货地址
     *
     * @return com.logicalthinking.jiuyou.common.pojo.Result
     * @author chenlijia
     * @since 下午 12:48 2019/8/15 0015
     **/
    @GetMapping(value = "listByClient")
    @ApiOperation(value = "查询当前用户收货地址列表", response = ClientAddress.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "commonAddress", value = "是否默认地址 0否 1是", paramType = "query")})
    public Result listByClient(Integer commonAddress) {
        return biz.listByClient(commonAddress);
    }

    /**
     * 主键查询地址
     *
     * @param id 1
     * @return com.logicalthinking.jiuyou.common.pojo.Result
     * @since 下午 3:45 2019/10/9 0009
     **/
    @GetMapping(value = "findById")
    @ApiOperation(value = "主键查询收货地址", response = ClientAddress.class)
    public Result findById(Integer id) {
        return biz.findById(id);
    }

    /**
     * 修改收货地址是否默认地址
     * @since 上午 11:07 2019/11/19 0019
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    @PostMapping(value = "commonStatus/update")
    @ApiOperation(value = "修改收货地址是否默认地址")
    public Result editAddressCommonStatus(UpdateStateParams params){
        return biz.editAddressCommonStatus(params);
    }


}

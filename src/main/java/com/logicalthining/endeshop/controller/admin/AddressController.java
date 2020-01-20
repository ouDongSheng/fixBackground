package com.logicalthining.endeshop.controller.admin;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.AddressBiz;
import com.logicalthining.endeshop.common.responseVo.address.ProvinceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 地址控制器
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/1 0001 上午 9:34
 **/
@RestController
@RequestMapping(value = "admin/address")
@Api(tags = "地址控制器")
public class AddressController {

    @Autowired
    private AddressBiz biz;//地址


    /**
     * 列出所有省市区
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:50 2019/11/1 0001
     **/
    @GetMapping(value = "listAll")
    @ApiOperation(value = "列出所有省市区", response = ProvinceVo.class)
    public Result listAllAddress() {
        return biz.listAllAddress();
    }

}

package com.logicalthining.endeshop.controller.admin;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.biz.RoleBiz;
import com.logicalthining.endeshop.common.requestVo.BatchUpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.UpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.role.AddParams;
import com.logicalthining.endeshop.common.requestVo.role.QueryParams;
import com.logicalthining.endeshop.common.requestVo.role.UpdateParams;
import com.logicalthining.endeshop.common.responseVo.role.RoleVo;
import com.logicalthining.endeshop.entity.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 角色表
 *
 * @author chenLiJia
 * @since 2019-10-30 10:01:08
 **/
@RestController
@RequestMapping(value = "admin/role")
@Api(tags = "角色")
public class RoleController {

    @Autowired
    private RoleBiz biz;//角色

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:45 2019/10/30 0030
     **/
    @PostMapping(value = "add")
    @ApiOperation(value = "添加")
    public Result add(@RequestBody AddParams params) {
        return biz.add(params);
    }

    /**
     * 编辑
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:45 2019/10/30 0030
     **/
    @PostMapping(value = "update")
    @ApiOperation(value = "编辑")
    public Result update(@RequestBody UpdateParams params) {
        return biz.update(params);
    }

    /**
     * 删除
     *
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:45 2019/10/30 0030
     **/
    @PostMapping(value = "delete")
    @ApiOperation(value = "删除")
    public Result delete(Integer id) {
        return biz.delete(id);
    }

    /**
     * 列出所有角色
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:46 2019/10/30 0030
     **/
    @GetMapping(value = "listAll")
    @ApiOperation(value = "列出所有角色",response = Role.class)
    public Result listAll() {
        return biz.listAll();
    }

    /**
     * 列表查询
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:46 2019/10/30 0030
     **/
    @GetMapping(value = "listPage")
    @ApiOperation(value = "列表查询",response = RoleVo.class)
    public Result listPage(QueryParams params) {
        return biz.listPage(params);
    }

    /**
     * 修改状态
     * @since 下午 5:28 2019/11/7 0007
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    @PostMapping(value = "state/update")
    @ApiOperation(value = "修改角色状态")
    public Result updateState(UpdateStateParams params){
        return biz.updateState(params);
    }

    /**
     * 主键查询角色信息
     * @since 上午 11:14 2019/11/8 0008
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    @GetMapping(value = "findById")
    @ApiOperation(value = "主键查询角色信息",response = RoleVo.class)
    public Result findById(Integer id){
        return biz.findById(id);
    }


    /**
     * 批量修改状态
     * @since 下午 5:28 2019/11/7 0007
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    @PostMapping(value = "state/update/batch")
    @ApiOperation(value = "批量修改状态")
    public Result batchUpdateState(@RequestBody BatchUpdateStateParams params){
        return biz.batchUpdateState(params);
    }


}

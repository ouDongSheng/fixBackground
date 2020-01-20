package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.common.requestVo.admin.QueryParams;
import com.logicalthining.endeshop.common.responseVo.admin.AdminVo;
import com.logicalthining.endeshop.entity.Admin;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AdminMapper extends Mapper<Admin> {

    /**
     * 列表查询后台用户
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.admin.AdminVo>
     * @since 上午 11:41 2019/10/30 0030
     **/
    List<AdminVo> listPage(QueryParams params);

}

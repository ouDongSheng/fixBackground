package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.common.requestVo.user.AdminWithdrawRecodeQueryParams;
import com.logicalthining.endeshop.common.responseVo.withdraw.AdminWithdrawListVo;
import com.logicalthining.endeshop.entity.UserWithdrawRecode;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 用户提现记录
 * @author chenLiJia
 * @since 2019-11-13 20:12:10
 * @version 1.0
 **/
public interface UserWithdrawRecodeMapper extends Mapper<UserWithdrawRecode> {

    /**
     * 后台查询用户提现记录
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.withdraw.AdminWithdrawListVo>
     * @since 上午 10:41 2019/11/14 0014
     **/
    List<AdminWithdrawListVo> listAdminWithdrawListVo(AdminWithdrawRecodeQueryParams params);

}

package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.logicalthining.endeshop.common.requestVo.user.AdminWithdrawRecodeQueryParams;
import com.logicalthining.endeshop.common.responseVo.withdraw.AdminWithdrawListVo;
import com.logicalthining.endeshop.entity.UserWithdrawRecode;
import com.logicalthining.endeshop.dao.UserWithdrawRecodeMapper;
import com.logicalthining.endeshop.service.UserWithdrawRecodeServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 用户提现记录
 * @author chenLiJia
 * @since 2019-11-13 20:12:48
 **/
@Service
public class UserWithdrawRecodeServiceImpl implements UserWithdrawRecodeServiceI {


    @Resource
    private UserWithdrawRecodeMapper userWithdrawRecodeMapper;


    /**
     * 添加
     *
     * @param params      添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-13 20:12:48
     **/
    public Result add(UserWithdrawRecode params){
    
        int i = userWithdrawRecodeMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params      编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-13 20:12:48
     **/
    public Result update(UserWithdrawRecode params){
    
        int i = userWithdrawRecodeMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 后台查询用户提现记录
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.withdraw.AdminWithdrawListVo>
     * @since 上午 10:41 2019/11/14 0014
     **/
    @Override
    public List<AdminWithdrawListVo> listAdminWithdrawListVo(AdminWithdrawRecodeQueryParams params) {
        params = PropertyCheckUtil.transferObjectNotNull(params,true);
        List<AdminWithdrawListVo> list = userWithdrawRecodeMapper.listAdminWithdrawListVo(params);
        return list;
    }

    /**
     * 主键查询
     * @since 上午 10:41 2019/11/14 0014
     * @param id 1
     * @return com.logicalthining.endeshop.entity.UserWithdrawRecode
     **/
    @Override
    public UserWithdrawRecode findById(Integer id) {
        if(Objects.nonNull(id)){
            return userWithdrawRecodeMapper.selectByPrimaryKey(id);
        }
        return null;
    }


}

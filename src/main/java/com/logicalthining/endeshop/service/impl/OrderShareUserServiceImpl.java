package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.logicalthining.endeshop.dao.OrderShareUserMapper;
import com.logicalthining.endeshop.entity.OrderShareUser;
import com.logicalthining.endeshop.service.OrderShareUserServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户分享订单记录，记录了订单下单时是由谁分享过来的
 *
 * @author chenLiJia
 * @since 2019-11-06 11:05:15
 **/
@Service
public class OrderShareUserServiceImpl implements OrderShareUserServiceI {


    @Resource
    private OrderShareUserMapper orderShareUserMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 11:05:15
     **/
    public Result add(OrderShareUser params) {

        int i = orderShareUserMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-06 11:05:15
     **/
    public Result update(OrderShareUser params) {

        int i = orderShareUserMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 通过组订单id查询订单下单分享信息
     *
     * @param groupId 1
     * @return com.logicalthining.endeshop.entity.OrderShareUser
     * @since 上午 11:11 2019/11/6 0006
     **/
    @Override
    public OrderShareUser findByGroupId(String groupId) {
        if (StringUtils.isNotEmpty(groupId)) {
            return orderShareUserMapper.selectByPrimaryKey(groupId);
        }
        return null;
    }

    /**
     * 根据groupId进行删除分享记录
     *
     * @param groupId
     * @return
     */
    @Override
    public Result deleteByGroupId(String groupId) {
        if (StringUtils.isNotEmpty(groupId)) {
            orderShareUserMapper.deleteByPrimaryKey(groupId);
        }
        return Result.success("操作成功");
    }


}

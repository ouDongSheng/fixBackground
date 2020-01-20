package com.logicalthining.endeshop.service;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.entity.MsgSendRecode;

import java.util.List;

/**
 * 信息发放记录
 *
 * @author chenLiJia
 * @since 2019-11-05 10:40:21
 **/
public interface MsgSendRecodeServiceI {

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 10:40:21
     **/
    Result add(MsgSendRecode params);

    /**
     * 添加
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 10:40:21
     **/
    Result update(MsgSendRecode params);

    /**
     * 条件查询消息发放记录
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.MsgSendRecode>
     * @since 上午 10:50 2019/11/5 0005
     **/
    List<MsgSendRecode> listByCondition(MsgSendRecode condition);

    /**
     * 删除过期的验证码
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 6:22 2019/11/12 0012
     **/
    Result deleteLimitedMsg();

    /**
     * 根据key和类型查询发送的验证码记录
     *
     * @param key  1
     * @param type 消息类型 1后台发放验证码 2前端发放验证码
     * @return com.logicalthining.endeshop.entity.MsgSendRecode
     * @since 下午 6:31 2019/11/12 0012
     **/
    MsgSendRecode findByKeyAndType(String key, Integer type);

    /**
     * 校验验证码
     *
     * @param key   1
     * @param type  消息类型 1后台发放验证码 2前端登录验证码 3前端提现验证码
     * @param value 3
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:52 2019/11/14 0014
     **/
    Result checkMsgCode(String key, Integer type, String value);
}

package com.logicalthining.endeshop.dao;

import com.logicalthining.endeshop.entity.MsgSendRecode;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * 信息发放记录
 * @author chenLiJia
 * @since 2019-11-05 10:40:06
 * @version 1.0
 **/
public interface MsgSendRecodeMapper extends Mapper<MsgSendRecode> {

    /**
     * 删除过期的验证码
     * @since 下午 6:22 2019/11/12 0012
     * @return java.lang.Integer
     **/
    Integer deleteLimitedMsg();

    /**
     * 根据key和类型查询发送的验证码记录
     * @since 下午 6:31 2019/11/12 0012
     * @param key 1
     * @param type 消息类型 1后台发放验证码 2前端发放验证码
     * @return com.logicalthining.endeshop.entity.MsgSendRecode
     **/
    MsgSendRecode findByKeyAndType(@Param("key") String key, @Param("type") Integer type);

}

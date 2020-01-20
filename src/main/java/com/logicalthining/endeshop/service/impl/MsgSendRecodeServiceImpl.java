package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.logicalthining.endeshop.dao.MsgSendRecodeMapper;
import com.logicalthining.endeshop.entity.MsgSendRecode;
import com.logicalthining.endeshop.service.MsgSendRecodeServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 信息发放记录
 *
 * @author chenLiJia
 * @since 2019-11-05 10:40:21
 **/
@Service
public class MsgSendRecodeServiceImpl implements MsgSendRecodeServiceI {


    @Resource
    private MsgSendRecodeMapper msgSendRecodeMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 10:40:21
     **/
    public Result add(MsgSendRecode params) {

        int i = msgSendRecodeMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-05 10:40:21
     **/
    public Result update(MsgSendRecode params) {

        int i = msgSendRecodeMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 条件查询消息发放记录
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.MsgSendRecode>
     * @since 上午 10:50 2019/11/5 0005
     **/
    @Override
    public List<MsgSendRecode> listByCondition(MsgSendRecode condition) {
        condition = PropertyCheckUtil.transferObjectNotNull(condition, true);
        return msgSendRecodeMapper.select(condition);
    }

    /**
     * 删除过期的验证码
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 6:22 2019/11/12 0012
     **/
    @Override
    public Result deleteLimitedMsg() {
        msgSendRecodeMapper.deleteLimitedMsg();
        return Result.success("操作成功");
    }

    /**
     * 根据key和类型查询发送的验证码记录
     *
     * @param key  1
     * @param type 消息类型 1后台发放验证码 2前端发放验证码
     * @return com.logicalthining.endeshop.entity.MsgSendRecode
     * @since 下午 6:31 2019/11/12 0012
     **/
    @Override
    public MsgSendRecode findByKeyAndType(String key, Integer type) {
        return msgSendRecodeMapper.findByKeyAndType(key, type);
    }


    /**
     * 校验验证码
     *
     * @param key   1
     * @param type  消息类型 1后台发放验证码 2前端登录验证码 3前端提现验证码
     * @param value 3
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:52 2019/11/14 0014
     **/
    @Override
    public Result checkMsgCode(String key, Integer type, String value) {

        //默认123456通过
//        if (Objects.equals(value, "123456")) {
//            return Result.success("验证通过");
//        }

        MsgSendRecode msgSendRecode = msgSendRecodeMapper.findByKeyAndType(key, type);
        if (Objects.isNull(msgSendRecode)) {
            return Result.failure("验证码不存在");
        }

        if (System.currentTimeMillis() > msgSendRecode.getLimitTime().getTime()) {
            return Result.failure("验证码已过期");
        }

        if (!Objects.equals(msgSendRecode.getMskValue().toLowerCase(), value.toLowerCase())) {
            return Result.failure("验证码错误");
        }

        return Result.success("验证通过");
    }


}

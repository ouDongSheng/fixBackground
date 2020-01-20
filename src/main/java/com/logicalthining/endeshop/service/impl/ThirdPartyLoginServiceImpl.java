package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.list.Lists;
import com.logicalthining.endeshop.dao.ThirdPartyLoginMapper;
import com.logicalthining.endeshop.entity.ThirdPartyLogin;
import com.logicalthining.endeshop.service.ThirdPartyLoginServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 用户第三方登录信息
 *
 * @author chenLiJia
 * @since 2019-11-12 17:00:13
 **/
@Service
public class ThirdPartyLoginServiceImpl implements ThirdPartyLoginServiceI {


    @Resource
    private ThirdPartyLoginMapper thirdPartyLoginMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-12 17:00:13
     **/
    public Result add(ThirdPartyLogin params) {

        int i = thirdPartyLoginMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-12 17:00:13
     **/
    public Result update(ThirdPartyLogin params) {

        int i = thirdPartyLoginMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 通过openId查询用户第三方信息
     *
     * @param openId 1
     * @return com.logicalthining.endeshop.entity.ThirdPartyLogin
     * @since 下午 5:20 2019/11/12 0012
     **/
    @Override
    public ThirdPartyLogin findByOpenId(String openId) {
        if (Objects.nonNull(openId)) {
            ThirdPartyLogin condition = new ThirdPartyLogin();
            condition.setOpenId(openId);

            List<ThirdPartyLogin> select = thirdPartyLoginMapper.select(condition);
            if (Lists.isNotEmpty(select)) {
                return select.get(0);
            }
        }
        return null;
    }

    /**
     * 根据用户id和第三方类型查询
     *
     * @param userId 用户id
     * @param type   第三方类型
     * @return com.logicalthining.endeshop.entity.ThirdPartyLogin
     * @since 下午 7:25 2019/11/12 0012
     **/
    @Override
    public ThirdPartyLogin findByUserIdAndType(Integer userId, Integer type) {

        ThirdPartyLogin condition = new ThirdPartyLogin();
        condition.setUserId(userId);
        condition.setThirdType(type);
        return thirdPartyLoginMapper.selectOne(condition);
    }


}

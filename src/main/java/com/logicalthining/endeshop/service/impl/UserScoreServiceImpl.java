package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.logicalthining.endeshop.dao.UserScoreMapper;
import com.logicalthining.endeshop.entity.UserScore;
import com.logicalthining.endeshop.service.UserScoreServiceI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * 用户积分
 *
 * @author chenLiJia
 * @since 2019-11-12 09:30:20
 **/
@Service
public class UserScoreServiceImpl implements UserScoreServiceI {


    @Resource
    private UserScoreMapper userScoreMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-12 09:30:20
     **/
    public Result add(UserScore params) {

        int i = userScoreMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-12 09:30:20
     **/
    public Result update(UserScore params) {

        int i = userScoreMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查找用户积分
     *
     * @param userId 用户id
     * @return com.logicalthining.endeshop.entity.UserScore
     * @since 上午 9:34 2019/11/12 0012
     **/
    @Override
    public UserScore findByUserId(Integer userId) {
        if (Objects.nonNull(userId)) {
            UserScore userScore = userScoreMapper.selectByPrimaryKey(userId);
            if (Objects.isNull(userScore)) {
                userScore = new UserScore();
                userScore.setUserId(userId);
                userScore.setScore(0.0);
                userScore.setGrandTotalScore(0.0);
                userScore.setUpdateTime(new Date());
                userScoreMapper.insertSelective(userScore);
            }
            return userScore;
        }
        return null;
    }


}

package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.oauth.jwt.JWTUtil;
import com.logicalthining.endeshop.common.pojo.Constants;
import com.logicalthining.endeshop.common.requestVo.BatchUpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.UpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.user.QueryParams;
import com.logicalthining.endeshop.common.responseVo.user.UserVo;
import com.logicalthining.endeshop.dao.UserMapper;
import com.logicalthining.endeshop.entity.User;
import com.logicalthining.endeshop.service.UserServiceI;
import com.logicalthining.endeshop.util.SpringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 用户表
 *
 * @author chenLiJia
 * @since 2019-10-31 19:11:15
 **/
@Service
public class UserServiceImpl implements UserServiceI {


    @Resource
    private UserMapper userMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-10-31 19:11:15
     **/
    public Result add(User params) {

        int i = userMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-10-31 19:11:15
     **/
    public Result update(User params) {

        int i = userMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 条件查询
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.User>
     * @since 下午 7:32 2019/10/31 0031
     **/
    @Override
    public List<User> listByCondition(User condition) {

        condition = PropertyCheckUtil.transferObjectNotNull(condition, true);
        return userMapper.select(condition);
    }

    /**
     * 列表查询用户
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.user.UserVo>
     * @since 下午 7:45 2019/10/31 0031
     **/
    @Override
    public List<UserVo> listPage(QueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        List<UserVo> userVos = userMapper.listPage(params);
        //关联查询收货地址
        return userVos;
    }

    /**
     * 获取当前用户
     *
     * @return java.util.Optional<com.logicalthining.endeshop.entity.User>
     * @since 上午 11:13 2019/11/5 0005
     **/
    @Override
    public Optional<User> currentUser() {

        HttpServletRequest request = SpringUtil.getCurrentRequest();
        String token = request.getHeader(Constants.TOKEN);

        if (StringUtils.isNotEmpty(token) && JWTUtil.checkFormat(token)
                && JWTUtil.checkSign(token, Constants.SALT)) {

            try {
                //解析token
                Claims claims = JWTUtil.parseJWT(token, Constants.SALT);
                String id = claims.getId();
                String subject = claims.getSubject();
                if (Objects.equals(Constants.APP, subject)) {
                    User user = userMapper.selectByPrimaryKey(id);
                    return Optional.ofNullable(user);
                }
            } catch (ExpiredJwtException e) {
                //token超时
            }
        }
        return Optional.empty();
    }

    /**
     * 主键查询
     * @since 下午 1:48 2019/11/7 0007
     * @param id 1
     * @return com.logicalthining.endeshop.entity.User
     **/
    @Override
    public User findById(Integer id) {
        if(Objects.nonNull(id)){
            return userMapper.selectByPrimaryKey(id);
        }
        return null;
    }


    /**
     * 批量修改用户状态
     * @since 上午 11:48 2019/11/9 0009
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     **/
    @Override
    public Result batchUpdateState(BatchUpdateStateParams params) {

        Integer i = userMapper.batchUpdateState(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }


}

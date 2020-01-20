package com.logicalthining.endeshop.service.impl;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.oauth.jwt.JWTUtil;
import com.logicalthining.endeshop.common.pojo.Constants;
import com.logicalthining.endeshop.common.requestVo.admin.QueryParams;
import com.logicalthining.endeshop.common.responseVo.admin.AdminVo;
import com.logicalthining.endeshop.dao.AdminMapper;
import com.logicalthining.endeshop.entity.Admin;
import com.logicalthining.endeshop.service.AdminServiceI;
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
 * 后台用户表
 *
 * @author chenLiJia
 * @since 2019-10-30 10:01:08
 **/
@Service
public class AdminServiceImpl implements AdminServiceI {


    @Resource
    private AdminMapper adminMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-10-30 10:01:08
     **/
    public Result add(Admin params) {

        int i = adminMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-10-30 10:01:08
     **/
    public Result update(Admin params) {

        int i = adminMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查询符合条件的数据
     *
     * @param condition 1
     * @return java.util.List<com.logicalthining.endeshop.entity.Admin>
     * @since 上午 11:04 2019/10/30 0030
     **/
    @Override
    public List<Admin> findByCondition(Admin condition) {

        PropertyCheckUtil.transferObjectNotNull(condition, true);
        return adminMapper.select(condition);
    }

    /**
     * 获取当前用户
     *
     * @return java.util.Optional<com.logicalthining.endeshop.entity.Admin>
     * @since 上午 11:20 2019/10/30 0030
     **/
    @Override
    public Optional<Admin> currentUser() {

        HttpServletRequest request = SpringUtil.getCurrentRequest();
        String token = request.getHeader(Constants.TOKEN);

        if (StringUtils.isNotEmpty(token) && JWTUtil.checkFormat(token)
                && JWTUtil.checkSign(token, Constants.SALT)) {

            try {
                //解析token
                Claims claims = JWTUtil.parseJWT(token, Constants.SALT);
                String id = claims.getId();
                String subject = claims.getSubject();
                if (Objects.equals(Constants.ADMIN, subject)) {
                    Admin admin = adminMapper.selectByPrimaryKey(id);
                    return Optional.ofNullable(admin);
                }
            } catch (ExpiredJwtException e) {
                //token超时
            }
        }
        return Optional.empty();
    }


    /**
     * 列表查询
     *
     * @param params 1
     * @return java.util.List<com.logicalthining.endeshop.common.responseVo.admin.AdminVo>
     * @since 上午 11:51 2019/10/30 0030
     **/
    @Override
    public List<AdminVo> listPage(QueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        return adminMapper.listPage(params);
    }


}

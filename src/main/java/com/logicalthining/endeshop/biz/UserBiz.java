package com.logicalthining.endeshop.biz;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.common.constant.BooleanConstant;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.http.HttpClientUtils;
import com.github.chenlijia1111.utils.http.ResponseUtil;
import com.github.chenlijia1111.utils.image.QRCodeUtil;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Maps;
import com.github.chenlijia1111.utils.list.annos.MapType;
import com.github.chenlijia1111.utils.oauth.jwt.JWTUtil;
import com.github.chenlijia1111.utils.oauth.wx.WXCommonLoginUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.logicalthining.endeshop.common.enums.UserRole;
import com.logicalthining.endeshop.common.pojo.Constants;
import com.logicalthining.endeshop.common.requestVo.BatchUpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.UpdateStateParams;
import com.logicalthining.endeshop.common.requestVo.user.*;
import com.logicalthining.endeshop.common.responseVo.user.AppUserChildUserListVo;
import com.logicalthining.endeshop.common.responseVo.user.UserInfoVo;
import com.logicalthining.endeshop.common.responseVo.user.UserVo;
import com.logicalthining.endeshop.entity.*;
import com.logicalthining.endeshop.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 用户表
 *
 * @author chenLiJia
 * @since 2019-10-31 19:11:15
 **/
@Service
public class UserBiz {


    @Autowired
    private UserServiceI userService;//用户
    @Autowired
    private AdminServiceI adminService;//后台用户
    @Autowired
    private ClientAddressServiceI clientAddressService;//用户收货地址
    @Autowired
    private ThirdPartyLoginServiceI thirdPartyLoginService;//第三方登录信息
    @Autowired
    private MsgSendRecodeServiceI msgSendRecodeService;//验证码发送记录
    @Autowired
    private UserRelationServiceI userRelationService;//用户关系
    @Autowired
    private ShoppingOrderServiceI orderService;//订单
    @Autowired
    private PartnerInfoServiceI partnerInfoService;//合伙人表

    //分享页面前端路径的前缀
    @Value("${share.urlSuffix}")
    private String shareUrlSuffix;

    /**
     * 添加用户
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 7:23 2019/10/31 0031
     **/
    public Result add(AddParams params) {

        //当前用户
        Optional<Admin> optional = adminService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断账号是否已经存在
        User condition = new User().setAccount(params.getAccount()).setDeleteStatus(BooleanConstant.NO_INTEGER);
        List<User> users = userService.listByCondition(condition);
        if (Lists.isNotEmpty(users)) {
            return Result.failure("账号已存在");
        }

        User user = new User();
        BeanUtils.copyProperties(params, user);

        user.setCreateAdmin(optional.get().getId()).setCreateTime(new Date()).setDeleteStatus(BooleanConstant.NO_INTEGER);
        Result add = userService.add(user);
        if (add.getSuccess() && Objects.equals(user.getRole(), UserRole.PARTNER_USER.getRole())) {
            //判断一下,如果是把他修改为了合伙人,需要在合伙人表里加个信息
            checkIsPartner(user);
        }
        return add;
    }

    /**
     * 编辑用户
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 7:23 2019/10/31 0031
     **/
    public Result update(UpdateParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断数据是否存在
        User condition = new User().setId(params.getId());
        List<User> users1 = userService.listByCondition(condition);
        if (Lists.isEmpty(users1)) {
            return Result.failure("数据不存在");
        }

        //判断账号是否已经存在
        condition = new User().setAccount(params.getAccount()).setDeleteStatus(BooleanConstant.NO_INTEGER);
        List<User> users = userService.listByCondition(condition);
        if (Lists.isNotEmpty(users) && users.stream().filter(e -> !Objects.equals(e.getId(), params.getId())).findAny().isPresent()) {
            return Result.failure("账号已存在");
        }

        User user = new User();
        BeanUtils.copyProperties(params, user);

        Result update = userService.update(user);
        if (update.getSuccess()) {
            if (Objects.equals(user.getRole(), UserRole.PARTNER_USER.getRole())) {
                //判断一下,如果是把他修改为了合伙人,需要在合伙人表里加个信息
                checkIsPartner(user);
            }
            //判断他有没有上级,如果有上级,就更新上级的下级数量
            UserRelation userRelation = userRelationService.findByUserId(user.getId());
            //更新用户的下级数量
            if (Objects.nonNull(userRelation)) {
                userRelationService.recursiveUpdateParentChildCount(userRelation.getParentUserId(),
                        1, user.getId(), user.getRole());
            }
        }
        return update;
    }

    /**
     * 判断这个用户的合伙人信息是否存在
     * 存在就变更,不存在就添加
     *
     * @param user
     */
    private void checkIsPartner(User user) {
        //判断是否是合伙人
        //是合伙人需要添加信息到合伙人表
        PartnerInfo partnerInfo = new PartnerInfo().setUserId(user.getId()).
                setPartnerName(user.getName()).
                setTelephone(user.getAccount()).
                setCreateTime(new Date());

        //判断是不是已经有合伙人信息了(即之前已经审核通过了一次,现在又通过,如果有,就做变更操作)
        PartnerInfo partnerInfoCondition = new PartnerInfo().setUserId(user.getId());
        List<PartnerInfo> partnerInfos = partnerInfoService.listByCondition(partnerInfoCondition);
        if (Lists.isEmpty(partnerInfos)) {
            partnerInfoService.add(partnerInfo);
        } else {
            partnerInfoService.update(partnerInfo);
        }
    }

    /**
     * 删除用户
     * 删除了用户之后需要把这个用户的上下级关系全部删除掉
     *
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 7:23 2019/10/31 0031
     **/
    public Result delete(Integer id) {

        if (Objects.isNull(id)) {
            return Result.failure("id为空");
        }

        //判断数据是否存在
        User condition = new User().setId(id);
        List<User> users = userService.listByCondition(condition);
        if (Lists.isEmpty(users)) {
            return Result.failure("数据不存在");
        }

        User user = users.get(0);
        user.setDeleteStatus(BooleanConstant.YES_INTEGER);

        Result update = userService.update(user);
        if (update.getSuccess()) {
            //删除关联关系
            userRelationService.deleteByUserId(id);
            //删除上级是这个用户的关联关系
            userRelationService.deleteByUParentId(id);
        }
        return update;
    }


    /**
     * 列表查询用户
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 7:23 2019/10/31 0031
     **/
    public Result listPage(QueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);

        PageHelper.startPage(params.getPage(), params.getLimit());
        List<UserVo> list = userService.listPage(params);
        PageInfo<UserVo> pageInfo = new PageInfo<>(list);
        return Result.success("查询成功", pageInfo);
    }


    /**
     * 修改用户状态
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:14 2019/11/1 0001
     **/
    public Result updateState(UpdateStateParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断数据是否存在
        User condition = new User().setId(params.getId());
        List<User> users = userService.listByCondition(condition);
        if (Lists.isEmpty(users)) {
            return Result.failure("数据不存在");
        }

        User user = new User().setId(params.getId()).setOpenStatus(params.getState());
        return userService.update(user);
    }


    /**
     * 用户登录
     *
     * @param params 1
     * @return javax.xml.transform.Result
     * @since 上午 11:08 2019/11/5 0005
     **/
    public Result login(LoginParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //判断验证码是否正确
        result = msgSendRecodeService.checkMsgCode(params.getTelephone(), 2, params.getMsgCode());
        if (!result.getSuccess()) {
            return result;
        }

        User currentUser = null;
        //判断是否有这个用户,没有这个用户直接创建一个新用户
        User condition = new User().setAccount(params.getTelephone()).setDeleteStatus(BooleanConstant.NO_INTEGER);
        List<User> users = userService.listByCondition(condition);
        if (Lists.isEmpty(users)) {
            //用户不存在,创建一个用户
            currentUser = new User().setAccount(params.getTelephone()).
                    setRole(UserRole.COMMON_USER.getRole()).
                    setOpenStatus(BooleanConstant.YES_INTEGER).
                    setCreateTime(new Date()).
                    setDeleteStatus(BooleanConstant.NO_INTEGER);

            userService.add(currentUser);
        } else {
            currentUser = users.get(0);
            //判断是否被停用
            if (Objects.equals(BooleanConstant.NO_INTEGER, currentUser.getOpenStatus())) {
                return Result.failure("用户已被停用");
            }
        }

        //返回token
        String jwt = JWTUtil.createJWT(currentUser.getId() + "", Constants.APP, Constants.APP_TIMEOUT, Constants.SALT);

        Map map = Maps.mapBuilder(MapType.HASH_MAP).put("user", currentUser).put("token", jwt).build();
        return Result.success("操作成功", map);
    }

    /**
     * 微信登录
     *
     * @param code 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 4:47 2019/11/12 0012
     **/
    public Result wxLogin(String code) {

        if (StringUtils.isEmpty(code)) {
            return Result.failure("微信code为空");
        }

        WXCommonLoginUtil loginUtil = new WXCommonLoginUtil();
        Map map = loginUtil.accessToken(Constants.WX_APP_ID, Constants.WX_APP_SECRET, code);
        //判断是否成功
        Object errcode = map.get("errcode");
        if (Objects.isNull(errcode)) {
            //请求成功
            //判断数据库中有没有保存过这个用户的数据
            String openId = map.get("openid").toString();
            //第三方登录信息
            ThirdPartyLogin thirdPartyLogin = thirdPartyLoginService.findByOpenId(openId);
            if (Objects.isNull(thirdPartyLogin)) {
                //还没有登陆过本系统
                thirdPartyLogin = new ThirdPartyLogin();
                thirdPartyLogin.setOpenId(openId);

                //获取用户详细信息
                Map userInfo = userInfo(map.get("access_token").toString(), map.get("openid").toString());
                if (Objects.isNull(userInfo.get("errcode"))) {
                    //请求用户信息成功
                    if (Objects.nonNull(userInfo.get("nickname"))) {
                        thirdPartyLogin.setNickName(userInfo.get("nickname").toString());
                    }
                    if (Objects.nonNull(userInfo.get("sex"))) {
                        thirdPartyLogin.setSex(Integer.valueOf(userInfo.get("sex").toString()));
                    }
                    if (Objects.nonNull(userInfo.get("province"))) {
                        thirdPartyLogin.setProvince(userInfo.get("province").toString());
                    }
                    if (Objects.nonNull(userInfo.get("city"))) {
                        thirdPartyLogin.setCity(userInfo.get("city").toString());
                    }
                    if (Objects.nonNull(userInfo.get("headimgurl"))) {
                        thirdPartyLogin.setHeadImage(userInfo.get("headimgurl").toString());
                    }
                }

                thirdPartyLoginService.add(thirdPartyLogin);
                //还没有关联账户,先添加第三方登录信息,统一返回错误信息
            } else {
                //登陆成功
                //查询与之关联的用户信息
                Integer userId = thirdPartyLogin.getUserId();
                if (Objects.nonNull(userId)) {
                    //查询这个用户,判断该用户的状态
                    User user = userService.findById(userId);
                    //判断是否被停用
                    if (Objects.equals(BooleanConstant.NO_INTEGER, user.getOpenStatus())) {
                        return Result.failure("用户已被停用");
                    }
                    //返回token
                    String jwt = JWTUtil.createJWT(user.getId() + "", Constants.APP, Constants.APP_TIMEOUT, Constants.SALT);

                    Map resultMap = Maps.mapBuilder(MapType.HASH_MAP).put("user", user).put("token", jwt).build();
                    return Result.success("操作成功", resultMap);
                }
            }

            //到达这里,表示第三方登录的账户没有绑定手机号
            Result notExists = Result.notExists("用户未绑定手机号");
            notExists.setData(thirdPartyLogin);
            return notExists;

        }
        return Result.failure("登陆失败");
    }

    /**
     * 微信登录
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 4:47 2019/11/12 0012
     **/
    public Result wxLoginV2(WxLoginParams params) {

        Result result = PropertyCheckUtil.checkProperty(params, Lists.asList("openId"));
        if (!result.getSuccess()) {
            return result;
        }

        //请求成功
        //判断数据库中有没有保存过这个用户的数据
        String openId = params.getOpenId();
        //第三方登录信息
        ThirdPartyLogin thirdPartyLogin = thirdPartyLoginService.findByOpenId(openId);
        if (Objects.isNull(thirdPartyLogin)) {
            //还没有登陆过本系统
            thirdPartyLogin = new ThirdPartyLogin();
            thirdPartyLogin.setOpenId(openId);

            if (Objects.nonNull(params)) {
                //请求用户信息成功
                thirdPartyLogin.setNickName(params.getNickName());
                thirdPartyLogin.setSex(Integer.valueOf(params.getSex()));
                thirdPartyLogin.setProvince(params.getProvince());
                thirdPartyLogin.setCity(params.getCity());
                thirdPartyLogin.setHeadImage(params.getHeadImgUrl());
            }

            thirdPartyLoginService.add(thirdPartyLogin);
            //还没有关联账户,先添加第三方登录信息,统一返回错误信息
        } else {
            //登陆成功
            //查询与之关联的用户信息
            Integer userId = thirdPartyLogin.getUserId();
            if (Objects.nonNull(userId)) {
                //查询这个用户,判断该用户的状态
                User user = userService.findById(userId);
                //判断是否被停用
                if (Objects.equals(BooleanConstant.NO_INTEGER, user.getOpenStatus())) {
                    return Result.failure("用户已被停用");
                }
                //返回token
                String jwt = JWTUtil.createJWT(user.getId() + "", Constants.APP, Constants.APP_TIMEOUT, Constants.SALT);

                Map resultMap = Maps.mapBuilder(MapType.HASH_MAP).put("user", user).put("token", jwt).build();
                return Result.success("操作成功", resultMap);
            }
        }

        //到达这里,表示第三方登录的账户没有绑定手机号
        Result notExists = Result.notExists("用户未绑定手机号");
        notExists.setData(thirdPartyLogin);
        return notExists;
    }


    /**
     * 绑定手机号
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 6:12 2019/11/12 0012
     **/
    public Result bindTelephone(BindTelephoneParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //验证验证码
        result = msgSendRecodeService.checkMsgCode(params.getTelephone(), 2, params.getMsgCode());
        if (!result.getSuccess()) {
            return result;
        }

        //查找第三方信息是否存在
        ThirdPartyLogin byOpenId = thirdPartyLoginService.findByOpenId(params.getOpenId());
        if (Objects.isNull(byOpenId)) {
            return Result.failure("第三方信息不存在");
        }

        //查询手机号用户是否存在
        User condition = new User().setAccount(params.getTelephone()).setDeleteStatus(BooleanConstant.NO_INTEGER);
        List<User> users = userService.listByCondition(condition);

        User user = null;
        if (Lists.isNotEmpty(users)) {

            user = users.get(0);

            //判断这个号码是不是已经绑定了微信号了,如果已经绑定了微信号了,就不允许绑定了
            //一个手机号不可以绑定两个微信号
            ThirdPartyLogin thirdPartyLogin = thirdPartyLoginService.findByUserIdAndType(user.getId(), 1);
            if (Objects.nonNull(thirdPartyLogin)) {
                return Result.failure("该手机号已绑定其他微信");
            }
        } else {
            //说明这个手机号还不存在
            //直接创建一个用户
            user = new User().
                    setAccount(params.getTelephone()).setRole(1).
                    setOpenStatus(BooleanConstant.YES_INTEGER).
                    setCreateTime(new Date()).
                    setDeleteStatus(BooleanConstant.NO_INTEGER);

            userService.add(user);
        }

        //把第三方的一些信息复制到用户信息中
        if (StringUtils.isEmpty(user.getName())) {
            user.setName(byOpenId.getNickName());
        }
        if (StringUtils.isEmpty(user.getHeadImage())) {
            user.setHeadImage(byOpenId.getHeadImage());
        }

        userService.update(user);

        //将第三方用户信息与用户信息进行关联
        byOpenId.setUserId(user.getId());
        byOpenId.setThirdType(1);
        thirdPartyLoginService.update(byOpenId);

        //返回token
        String jwt = JWTUtil.createJWT(user.getId() + "", Constants.APP, Constants.APP_TIMEOUT, Constants.SALT);

        Map resultMap = Maps.mapBuilder(MapType.HASH_MAP).put("user", user).put("token", jwt).build();
        return Result.success("操作成功", resultMap);
    }


    /**
     * 获取用户信息
     * <p>
     * {
     * "openid":" OPENID",
     * " nickname": NICKNAME,
     * "sex":"1",
     * "province":"PROVINCE"
     * "city":"CITY",
     * "country":"COUNTRY",
     * "headimgurl":       "http://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",
     * "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],
     * "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
     * }
     *
     * @param accessToken 1
     * @param openId      2
     * @return java.util.Map
     * @since 下午 4:46 2019/11/12 0012
     **/
    private Map userInfo(String accessToken, String openId) {
        Map map = HttpClientUtils.getInstance().putParams("openid", openId).
                putParams("access_token", accessToken).
                putParams("lang", "zh_CN").
                doGet("https://api.weixin.qq.com/sns/userinfo").toMap();
        return map;
    }


    /**
     * 刷新token
     *
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:11 2019/11/5 0005
     **/
    public Result refreshToken() {

        //获取当前用户
        Optional<User> optional = userService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }

        //返回一个新的token给前端,进行续期
        String jwt = JWTUtil.createJWT(optional.get().getId() + "", Constants.APP, Constants.APP_TIMEOUT, Constants.SALT);

        return Result.success("操作成功", jwt);
    }


    /**
     * 批量修改用户状态
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:39 2019/11/9 0009
     **/
    public Result batchUpdateState(BatchUpdateStateParams params) {

        //校验参数
        Result result = PropertyCheckUtil.checkProperty(params);
        if (!result.getSuccess()) {
            return result;
        }

        //开始处理
        result = userService.batchUpdateState(params);

        return result;
    }

    /**
     * 根据id查询查询用户信息
     *
     * @param id 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 1:35 2019/11/9 0009
     **/
    public Result findById(Integer id) {

        //校验参数
        if (Objects.isNull(id)) {
            return Result.failure("id为空");
        }
        //判断是否存在
        User user = userService.findById(id);
        if (Objects.isNull(user) || Objects.equals(BooleanConstant.YES_INTEGER, user.getDeleteStatus())) {
            return Result.failure("用户不存在");
        }

        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(user, userInfoVo);

        //查询用户收获地址信息
        ClientAddress clientAddressCondition = new ClientAddress().setClientId(id).setIsDelete(BooleanConstant.NO_INTEGER);
        List<ClientAddress> clientAddresses = clientAddressService.listByCondition(clientAddressCondition);

        userInfoVo.setAddressList(clientAddresses);

        return Result.success("查询成功", userInfoVo);
    }


    /**
     * 用户二维码
     *
     * @param id 1
     * @return void
     * @since 下午 2:27 2019/11/13 0013
     **/
    public void userQrCode(Integer id, HttpServletResponse response) {

        if (Objects.isNull(id)) {
            ResponseUtil.printRest(Result.failure("用户id为空"), response);
            return;
        }
        //http://192.168.1.134:9093/?shareUserId=12#/home
        if (StringUtils.isEmpty(shareUrlSuffix)) {
            ResponseUtil.printRest(Result.failure("分享页面前缀未设置"), response);
            return;
        }
        String shareUrl = shareUrlSuffix + "/?shareUserId=" + id + "#/home";

        try {
            new QRCodeUtil().output(shareUrl, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 客户端查询直接下级列表
     *
     * @param params 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 下午 2:40 2019/11/14 0014
     **/
    public Result listChildUser(ChildUserQueryParams params) {

        params = PropertyCheckUtil.transferObjectNotNull(params, true);
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<AppUserChildUserListVo> list = userRelationService.listAppUserChildUserListVo(params);
        PageInfo<AppUserChildUserListVo> pageInfo = new PageInfo<>(list);
        return Result.success("查询成功", pageInfo);
    }


    /**
     * 绑定上级关系
     * 如果当前用户没有消费，可以直接覆盖掉原来的上级
     * 如果当前用户消费了，不可以覆盖原来的上级
     *
     * @param parentUserId 上级用户id
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 11:50 2019/11/21 0021
     **/
    public Result bindParent(Integer parentUserId) {

        //校验参数
        if (Objects.isNull(parentUserId)) {
            return Result.failure("要绑定的id为空");
        }

        //当前用户
        Optional<User> optional = userService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }
        User user = optional.get();

        Result result = null;
        //判断当前用户有没有上级
        UserRelation userRelation = userRelationService.findByUserId(user.getId());
        if (Objects.isNull(userRelation)) {
            //没有关系,直接绑定
            userRelation = new UserRelation();
            userRelation.setUserId(user.getId());
            userRelation.setParentUserId(parentUserId);
            userRelation.setCreateTime(new Date());
            result = userRelationService.add(userRelation);
        } else {
            //有上级,判断这次绑定的上级是否就是之前的上级
            if (Objects.equals(parentUserId, userRelation.getParentUserId())) {
                result = Result.failure("当前用于已与该用户是绑定关系,无法重复绑定");
            } else {
                //有上级,判断当前用户有没有消费
                ShoppingOrder shoppingOrderCondition = new ShoppingOrder().
                        setCustom(String.valueOf(user.getId())).
                        setState(Constants.ORDER_COMPLETE);
                //有效订单数量
                Integer effectOrderCount = orderService.countByCondition(shoppingOrderCondition);
                if (Objects.nonNull(effectOrderCount) && effectOrderCount > 0) {
                    //已经有上级了,且已经消费了,不可以绑定
                    result = Result.failure("该用户已有上级,且已产生消费,无法改绑上级");
                } else {
                    //覆盖原来的上级
                    //先删除之前的关系
                    userRelationService.deleteByUserId(user.getId());
                    //再添加新的关系
                    userRelation.setUserId(user.getId());
                    userRelation.setParentUserId(parentUserId);
                    userRelation.setCreateTime(new Date());
                    result = userRelationService.add(userRelation);
                }
            }

        }

        return result;
    }


    /**
     * 绑定微信
     *
     * @param code
     * @return
     */
    public Result bindWechat(String code) {
        if (StringUtils.isEmpty(code)) {
            return Result.failure("微信code为空");
        }

        //判断当前用户
        Optional<User> optional = userService.currentUser();
        if (!optional.isPresent()) {
            return Result.notLogin();
        }
        User user = optional.get();

        WXCommonLoginUtil loginUtil = new WXCommonLoginUtil();
        Map map = loginUtil.accessToken(Constants.WX_APP_ID, Constants.WX_APP_SECRET, code);
        //判断是否成功
        Object errcode = map.get("errcode");
        if (Objects.isNull(errcode)) {
            //请求成功
            //判断数据库中有没有保存过这个用户的数据
            String openId = map.get("openid").toString();
            //第三方登录信息
            ThirdPartyLogin thirdPartyLogin = thirdPartyLoginService.findByOpenId(openId);
            if (Objects.isNull(thirdPartyLogin)) {
                //还没有登陆过本系统
                thirdPartyLogin = new ThirdPartyLogin();
                thirdPartyLogin.setOpenId(openId);

                //获取用户详细信息
                Map userInfo = userInfo(map.get("access_token").toString(), map.get("openid").toString());
                if (Objects.isNull(userInfo.get("errcode"))) {
                    //请求用户信息成功
                    if (Objects.nonNull(userInfo.get("nickname"))) {
                        thirdPartyLogin.setNickName(userInfo.get("nickname").toString());
                    }
                    if (Objects.nonNull(userInfo.get("sex"))) {
                        thirdPartyLogin.setSex(Integer.valueOf(userInfo.get("sex").toString()));
                    }
                    if (Objects.nonNull(userInfo.get("province"))) {
                        thirdPartyLogin.setProvince(userInfo.get("province").toString());
                    }
                    if (Objects.nonNull(userInfo.get("city"))) {
                        thirdPartyLogin.setCity(userInfo.get("city").toString());
                    }
                    if (Objects.nonNull(userInfo.get("headimgurl"))) {
                        thirdPartyLogin.setHeadImage(userInfo.get("headimgurl").toString());
                    }

                }
                thirdPartyLogin.setUserId(user.getId());
                thirdPartyLoginService.add(thirdPartyLogin);
            } else if (Objects.isNull(thirdPartyLogin.getUserId())) {
                //已经微信登陆了,但是没有绑定手机号
                thirdPartyLogin.setUserId(user.getId());
                thirdPartyLoginService.update(thirdPartyLogin);
            } else {
                //已经绑定了手机号
                return Result.failure("该微信已绑定其他手机号");
            }


        }
        return Result.failure("绑定失败");
    }

    /**
     * 获取openId
     *
     * @param code
     * @return
     */
    public Result openId(String code) {

        if (StringUtils.isEmpty(code)) {
            return Result.failure("code为空");
        }

        WXCommonLoginUtil loginUtil = new WXCommonLoginUtil();
        Map map = loginUtil.accessToken(Constants.WX_APP_ID, Constants.WX_APP_SECRET, code);
        Object accessToken = map.get("access_token");
        Object openid = map.get("openid");
        if (Objects.nonNull(accessToken) && Objects.nonNull(openid)) {
            Map map1 = loginUtil.userInfo(accessToken.toString(), openid.toString());
            return Result.success("操作成功", map1);
        }
        return Result.failure("获取用户信息失败");
    }

}

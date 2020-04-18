package com.logicalthining.endeshop.common.pojo;

import com.github.chenlijia1111.utils.core.JSONUtil;
import com.github.chenlijia1111.utils.list.Lists;
import com.github.chenlijia1111.utils.list.Maps;
import com.github.chenlijia1111.utils.list.annos.MapType;
import com.logicalthining.endeshop.common.enums.SystemConfigKey;
import com.logicalthining.endeshop.common.pojo.systemConfig.AwardPoolConfig;

import java.util.Map;

/**
 * 系统常量
 *
 * @since 上午 10:51 2019/8/5 0005
 **/
public class Constants {

    //前端token key
    public static final String TOKEN = "authentication";

    //密码加密加盐
    public static final String SALT = "KYJCGL2U";

    //后台用户登录超时时间 一天
    public static final Long ADMIN_TIMEOUT = 24 * 60 * 60 * 1000L;

    //app用户登录超时时间 2天
    public static final Long APP_TIMEOUT = 2 * 24 * 60 * 60 * 1000L;

    //后台图片验证码过期时间 30分钟
    public static final Long ADMIN_VERIFY_IMAGE_TIMEOUT = 30 * 60 * 1000L;

    //客户端短信验证码过期时间 15分钟
    public static final Long APP_MSM_CODE_TIMEOUT = 15 * 60 * 1000L;

    //放在 token 中，表示后台用户
    public static final String ADMIN = "admin";

    //放在 app 中，表示app用户
    public static final String APP = "APP";

    //初始密码
    public static final String INIT_PASSWORD = "123456";

    //客户端用户角色
    public static final Map<Integer, String> USER_ROLE_MAP = Maps.mapBuilder(MapType.HASH_MAP).
            put(1, "普通用户").put(2, "会员").put(3, "合伙人").build();

    //订单状态 初始状态
    public static final Integer ORDER_INIT = 0;

    //订单状态 取消状态
    public static final Integer ORDER_CANCEL = 0XF0001;

    //订单状态 完成状态
    public static final Integer ORDER_COMPLETE = 0XF0002;

    //系统配置初始化信息
    public static final Map<String, String> SYSTEM_CONFIG_INIT_MAP = Maps.mapBuilder(MapType.HASH_MAP).
            put(SystemConfigKey.SHARE_AWARD_RATIO.getKey(), "0.24").
            put(SystemConfigKey.VIP_DISCOUNT.getKey(), "0.65").
            put(SystemConfigKey.REPEAT_AWARD_RATIO.getKey(), "0.14").
            put(SystemConfigKey.INDIRECT_AWARD_RATIO.getKey(), "0.14").
            put(SystemConfigKey.VIP_LIMIT_MONEY.getKey(), "298.0").
            put(SystemConfigKey.PARTNER_LIMIT_MONEY.getKey(), "2980.0").
            put(SystemConfigKey.AWARD_POOL_CONFIG.getKey(),
                    JSONUtil.objToStr(Lists.asList(new AwardPoolConfig(100000.0, 500000.0, 0.08),
                            new AwardPoolConfig(500001.0, 1000000.0, 0.05),
                            new AwardPoolConfig(1000001.0, null, 0.03)))).
            build();

    //微信公众号配置信息
    public static final String WX_APP_ID = "wxefb12dedbfe8fe53";

    //微信公众号配置信息
    public static final String WX_APP_SECRET = "42b97390876ce9ca4bb215d6d7164ef5";

    //微信支付商户id
    public static final String WX_MCHID = "1562837811";

    //微信支付 签名加盐的key
    public static final String WX_PAY_SIGN_KEY = "XiztVMpHncFmjTTMYiAf49rDSEeKcOO8";

    //云片短信平台 apiKey
    public static final String SMS_APIKEY = "b87c521b6afe213229556f28d7af83bd";

    //快递鸟 http://www.kdniao.com  用户id
    public static final String EXPRESS_USERID = "1594935";

    //快递鸟
    public static final String EXPRESS_APIKEY = "6bac5a15-53cd-4841-a0cf-2bc8161b26b0";

    //快递自取名称(用于区分是否自取)
    public static final String FOUND_SELF_EXPRESS_NAME = "自提";
    //快递自取公司编号(无实际意义)
    public static final String FOUND_SELF_EXPRESS_COM = "ziti";
    //快递自取物流币编号(无实际意义)
    public static final String FOUND_SELF_EXPRESS_NO = "123456";


}

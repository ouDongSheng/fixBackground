package com.logicalthining.endeshop;

import com.github.chenlijia1111.utils.list.Lists;
import com.logicalthining.endeshop.dao.AuthMapper;
import com.logicalthining.endeshop.entity.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据初始化
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/10/30 0030 下午 1:13
 **/
@SpringBootTest
public class initTest {

    @Resource
    private AuthMapper authMapper;//权限


    //后台用户管理
    @Test
    public void testAuth() {

        Auth auth = new Auth().setName("后台用户管理").setAuthType(1);
        authMapper.insertSelective(auth);

        Auth auth1 = new Auth().setName("账号管理").setAuthType(2).setParentAuth(auth.getId());
        authMapper.insertSelective(auth1);
        List<String> list = Lists.asList("查询", "添加", "修改", "删除", "重置密码", "切换状态");
        for (String s : list) {
            Auth auth2 = new Auth().setName(s).setAuthType(3).setParentAuth(auth1.getId());
            authMapper.insertSelective(auth2);
        }

        Auth auth2 = new Auth().setName("角色管理").setAuthType(2).setParentAuth(auth.getId());
        authMapper.insertSelective(auth2);
        List<String> list1 = Lists.asList("查询", "添加", "修改", "删除", "切换状态");
        for (String s : list1) {
            Auth auth3 = new Auth().setName(s).setAuthType(3).setParentAuth(auth2.getId());
            authMapper.insertSelective(auth3);
        }

    }

    //用户管理
    @Test
    public void testAuth2() {
        Auth auth = new Auth().setName("用户管理").setAuthType(1);
        authMapper.insertSelective(auth);

        Auth auth1 = new Auth().setName("用户管理").setAuthType(2).setParentAuth(auth.getId());
        authMapper.insertSelective(auth1);
        List<String> list = Lists.asList("查询", "添加", "修改", "删除", "切换状态");
        for (String s : list) {
            Auth auth2 = new Auth().setName(s).setAuthType(3).setParentAuth(auth1.getId());
            authMapper.insertSelective(auth2);
        }

        Auth auth2 = new Auth().setName("合伙人申请").setAuthType(2).setParentAuth(auth.getId());
        authMapper.insertSelective(auth2);
        List<String> list1 = Lists.asList("查询", "通过", "不通过");
        for (String s : list1) {
            Auth auth3 = new Auth().setName(s).setAuthType(3).setParentAuth(auth2.getId());
            authMapper.insertSelective(auth3);
        }
    }

    //商品管理
    @Test
    public void testAuth3() {
        Auth auth = new Auth().setName("商品管理").setAuthType(2);
        authMapper.insertSelective(auth);

        List<String> list = Lists.asList("查询", "查看", "添加", "修改", "删除", "上下架");
        for (String s : list) {
            Auth auth2 = new Auth().setName(s).setAuthType(3).setParentAuth(auth.getId());
            authMapper.insertSelective(auth2);
        }
    }

    //分享管理
    @Test
    public void testAuth4() {
        Auth auth = new Auth().setName("分享管理").setAuthType(1);
        authMapper.insertSelective(auth);

        Auth auth1 = new Auth().setName("分享记录").setAuthType(2).setParentAuth(auth.getId());
        authMapper.insertSelective(auth1);
        List<String> list = Lists.asList("查询");
        for (String s : list) {
            Auth auth2 = new Auth().setName(s).setAuthType(3).setParentAuth(auth1.getId());
            authMapper.insertSelective(auth2);
        }

        Auth auth2 = new Auth().setName("分享奖比例设置").setAuthType(2).setParentAuth(auth.getId());
        authMapper.insertSelective(auth2);
        List<String> list1 = Lists.asList("提交", "重置");
        for (String s : list1) {
            Auth auth3 = new Auth().setName(s).setAuthType(3).setParentAuth(auth2.getId());
            authMapper.insertSelective(auth3);
        }
    }

    //订单管理
    @Test
    public void testAuth5() {
        Auth auth = new Auth().setName("订单管理").setAuthType(2);
        authMapper.insertSelective(auth);

        List<String> list = Lists.asList("查询", "查看详情", "发货", "添加备注", "取消订单");
        for (String s : list) {
            Auth auth2 = new Auth().setName(s).setAuthType(3).setParentAuth(auth.getId());
            authMapper.insertSelective(auth2);
        }
    }

    //积分管理
    @Test
    public void testAuth6() {
        Auth auth = new Auth().setName("积分管理").setAuthType(1);
        authMapper.insertSelective(auth);

        Auth auth1 = new Auth().setName("间接奖").setAuthType(2).setParentAuth(auth.getId());
        authMapper.insertSelective(auth1);
        List<String> list = Lists.asList("查询");
        for (String s : list) {
            Auth auth2 = new Auth().setName(s).setAuthType(3).setParentAuth(auth1.getId());
            authMapper.insertSelective(auth2);
        }

        Auth auth2 = new Auth().setName("复购奖").setAuthType(2).setParentAuth(auth.getId());
        authMapper.insertSelective(auth2);
        List<String> list2 = Lists.asList("查询");
        for (String s : list2) {
            Auth auth3 = new Auth().setName(s).setAuthType(3).setParentAuth(auth1.getId());
            authMapper.insertSelective(auth3);
        }

        Auth auth3 = new Auth().setName("积分池").setAuthType(2).setParentAuth(auth.getId());
        authMapper.insertSelective(auth3);
        List<String> list1 = Lists.asList("查询", "导出", "查看团队详情");
        for (String s : list1) {
            Auth auth4 = new Auth().setName(s).setAuthType(3).setParentAuth(auth2.getId());
            authMapper.insertSelective(auth4);
        }


        Auth auth4 = new Auth().setName("积分池").setAuthType(2).setParentAuth(auth.getId());
        authMapper.insertSelective(auth4);
        List<String> list4 = Lists.asList("奖金池设置提交", "奖金池设置重置", "间接奖及分享奖设置提交", "间接奖及分享奖设置重置");
        for (String s : list4) {
            Auth auth5 = new Auth().setName(s).setAuthType(3).setParentAuth(auth2.getId());
            authMapper.insertSelective(auth5);
        }


    }

    @Test
    public void testAuth7() {
        Auth auth = new Auth().setName("统计信息").setAuthType(1);
        authMapper.insertSelective(auth);

        Auth auth1 = new Auth().setName("盈利").setAuthType(2).setParentAuth(auth.getId());
        authMapper.insertSelective(auth1);

        Auth auth2 = new Auth().setName("销售").setAuthType(2).setParentAuth(auth.getId());
        authMapper.insertSelective(auth2);
        List<String> list1 = Lists.asList("查询");
        for (String s : list1) {
            Auth auth3 = new Auth().setName(s).setAuthType(3).setParentAuth(auth2.getId());
            authMapper.insertSelective(auth3);
        }
    }


    //提现记录
    @Test
    public void testAuth8() {
        Auth auth = new Auth().setName("提现记录").setAuthType(2);
        authMapper.insertSelective(auth);

        List<String> list = Lists.asList("查询", "提现成功", "提现失败");
        for (String s : list) {
            Auth auth2 = new Auth().setName(s).setAuthType(3).setParentAuth(auth.getId());
            authMapper.insertSelective(auth2);
        }
    }

}

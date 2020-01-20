package com.logicalthining.endeshop;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.logicalthining.endeshop.dao.AdminMapper;
import com.logicalthining.endeshop.dao.UserMapper;
import com.logicalthining.endeshop.entity.Admin;
import com.logicalthining.endeshop.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/7 0007 下午 2:55
 **/
@SpringBootTest
@Rollback(value = false)
public class TestPageHelper {

    @Resource
    private AdminMapper adminMapper;
    @Resource
    private UserMapper userMapper;

    @Test
    public void test1(){

        PageHelper.startPage(1,10);

        List<Admin> admins = adminMapper.selectAll();
        List<User> users = userMapper.selectAll();


        PageInfo<Admin> pageInfo = new PageInfo<>(admins);

        List<Admin> list = pageInfo.getList();
        System.out.println(list);

    }

}

package com.logicalthining.endeshop;

import com.logicalthining.endeshop.biz.ShoppingOrderBiz;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Rollback(value = false)
public class TestOtder {

    @Autowired
    private ShoppingOrderBiz biz;

    @Test
    public void test(){
        biz.adminCancelOrder("20191213161820792");
    }
}

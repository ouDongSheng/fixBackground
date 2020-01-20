package com.logicalthining.endeshop;

import com.logicalthining.endeshop.service.UserRelationServiceI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Rollback(value = false)
public class TestUserRelation {


    @Autowired
    private UserRelationServiceI userRelationService;

    @Test
    public void test1(){
        userRelationService.recursiveUpdateParentChildCount(120,1,126,1);
    }
}

package com.logicalthining.endeshop;

import com.logicalthining.endeshop.common.enums.SystemConfigKey;
import com.logicalthining.endeshop.entity.SystemConfig;
import com.logicalthining.endeshop.service.SystemConfigServiceI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/15 0015 上午 10:50
 **/
@SpringBootTest
public class SystemConfigTest {

    @Autowired
    private SystemConfigServiceI systemConfigService;

    @Test
    public void test1(){
        SystemConfig systemConfig = systemConfigService.selectByConfigKey(SystemConfigKey.VIP_DISCOUNT.getKey());
        systemConfigService.add(systemConfig);
    }

}

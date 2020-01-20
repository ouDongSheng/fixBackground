package com.logicalthining.endeshop.common.init;

import com.github.chenlijia1111.utils.image.ValidateImageUtil;
import com.github.chenlijia1111.utils.timer.TimerTaskUtil;
import com.github.chenlijia1111.utils.timer.TriggerUtil;
import com.logicalthining.endeshop.common.schdule.ArchivePerformance;
import com.logicalthining.endeshop.common.schdule.BackUpDatabase;
import com.logicalthining.endeshop.common.schdule.DeleteLimitedMsg;
import org.quartz.Trigger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * 在项目启动之后需要做的一些初始化操作
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/9 0009 上午 11:45
 **/
@Component
public class InitFunction implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
        //修改验证码字体的可选颜色
        ValidateImageUtil.fontColor = new Color[]{Color.RED, Color.BLUE, Color.GREEN};


        //添加四个定时器,结算四个季度的业绩
        addArchivePerformanceTask();

        //添加删除过期验证码任务
        addDeleteLimitedMsgTask();

        //添加备份数据定时器
//        addBackUpMysqlTask();

    }


    //添加四个定时器,结算四个季度的业绩
    private void addArchivePerformanceTask() {

        String groupName = "archivePerformance";
        Trigger oneQuarterTrigger = TriggerUtil.createCronTrigger("0 0 2 1 4 ? *", "one", groupName);
        Trigger twoQuarterTrigger = TriggerUtil.createCronTrigger("0 0 2 1 7 ? *", "two", groupName);
        Trigger threeQuarterTrigger = TriggerUtil.createCronTrigger("0 0 2 1 10 ? *", "three", groupName);
        Trigger fourQuarterTrigger = TriggerUtil.createCronTrigger("0 0 2 1 1 ? *", "four", groupName);

        TimerTaskUtil.doTask(oneQuarterTrigger, ArchivePerformance.class, "one", groupName);
        TimerTaskUtil.doTask(twoQuarterTrigger, ArchivePerformance.class, "two", groupName);
        TimerTaskUtil.doTask(threeQuarterTrigger, ArchivePerformance.class, "three", groupName);
        TimerTaskUtil.doTask(fourQuarterTrigger, ArchivePerformance.class, "four", groupName);
    }

    //添加删除过期验证码任务
    private void addDeleteLimitedMsgTask() {
        String groupName = "msgGroup";
        String name = "deleteLimitMsg";

        Trigger trigger = TriggerUtil.createCronTrigger("0 0 0 * * ? *", name, groupName);

        TimerTaskUtil.doTask(trigger, DeleteLimitedMsg.class, name, groupName);
    }

    //添加备份数据库定时任务
    private void addBackUpMysqlTask() {
        String groupName = "backupGroup";
        String name = "backupSql";

        //每天的0点开始备份
        Trigger trigger = TriggerUtil.createCronTrigger("0 0 0 * * ? *", name, groupName);

        TimerTaskUtil.doTask(trigger, BackUpDatabase.class, name, groupName);
    }


}

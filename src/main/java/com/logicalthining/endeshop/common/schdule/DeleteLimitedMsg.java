package com.logicalthining.endeshop.common.schdule;

import com.logicalthining.endeshop.service.MsgSendRecodeServiceI;
import com.logicalthining.endeshop.service.impl.MsgSendRecodeServiceImpl;
import com.logicalthining.endeshop.util.SpringContextHolder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 删除存储在系统中过期的验证码
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/12 0012 下午 6:24
 **/
public class DeleteLimitedMsg implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        MsgSendRecodeServiceI msgSendRecodeService = SpringContextHolder.getBean(MsgSendRecodeServiceImpl.class);
        msgSendRecodeService.deleteLimitedMsg();
    }
}

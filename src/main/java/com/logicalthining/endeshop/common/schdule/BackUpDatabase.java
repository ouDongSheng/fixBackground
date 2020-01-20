package com.logicalthining.endeshop.common.schdule;

import com.github.chenlijia1111.utils.common.constant.TimeConstant;
import com.github.chenlijia1111.utils.core.FileUtils;
import com.github.chenlijia1111.utils.database.MysqlBackUtil;
import com.logicalthining.endeshop.conf.UploadFileConfig;
import com.logicalthining.endeshop.util.SpringContextHolder;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;

/**
 * 定时备份数据库
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/16 0016 上午 9:21
 **/
public class BackUpDatabase implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        //找到文件存储地址
        UploadFileConfig fileConfig = SpringContextHolder.getBean(UploadFileConfig.class);
        //备份的文件名称
        String fileName = "ende-" + DateTime.now().toString(TimeConstant.DATE) + ".sql";
        File file = new File(fileConfig.getFileSavePath() + "/backup/sql/" + fileName);
        //校验父文件夹是否存在
        FileUtils.checkDirectory(file.getParent());

        MysqlBackUtil.exportSql("C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin", "58.250.17.31",
                "3345", "root", "0822myljsw", "ende",
                null, file);
    }


}

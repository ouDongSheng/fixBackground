package com.logicalthining.endeshop.common.exceptionHandle;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.email.EmailHostType;
import com.github.chenlijia1111.utils.email.EmailUtil;
import com.github.chenlijia1111.utils.list.Lists;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author chenlijia
 * @version 1.0
 * @since 2019/4/19 0019 上午 10:08
 **/
@RestControllerAdvice
public class GlobalExceptionHandle {


    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result handle(ConstraintViolationException e) {
        e.printStackTrace();
        return Result.failure(e.getMessage());
    }

    @ExceptionHandler(value = BindException.class)
    public Result handle(BindException e) {
        e.printStackTrace();
        return Result.failure(e.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public Result handle(Exception exception) {
        Result failure = Result.failure("系统异常");
        failure.setData(exception.getMessage());
        exception.printStackTrace();

        //把错误信息发送到邮箱,提醒开发者进行修复
        EmailUtil emailUtil = new EmailUtil("17770039942@163.com",
                "asdasd123123", EmailHostType.WANGYI, "陈礼佳");

        emailUtil.sendMassage(Lists.asList("571740367@qq.com"), "恩德生态异常邮件",
                exceptionStackInfoToString(exception), null);
        return failure;
    }

    /**
     * 异常栈信息转字符串
     *
     * @param exception 1
     * @return java.lang.String
     * @since 下午 2:12 2019/10/16 0016
     **/
    private String exceptionStackInfoToString(Exception exception) {
        try (StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw)) {
            exception.printStackTrace(pw);
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

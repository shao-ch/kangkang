package com.kangkang.orderLogAOP;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.java.Log;
import net.logstash.logback.encoder.org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.MDC;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName: OrderApplicationAspect  oder前台的日志收集的AOP,这个适合没有日志侵入controller的方法里面，
 * 这里可以利用kafaka取收集然后发送给logstash，由于我目前方法里面含有日志了，所以这里就不需要去处理了
 * @Author: shaochunhai
 * @Date: 2021/12/6 5:18 下午
 * @Description: TODO
 */
@Aspect
@Order(1)
//@Component
public class OrderApplicationAspect {

    /**
     * 获取日志收集的类
     */
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /**
     * 这里需要创建一个threadlocal，因为不相同的日志可能是不同的线程打印的
     */
    private ThreadLocal<LogTemplate> logStash=new ThreadLocal<>();

    /**
     * 定义切点，也就是触发条件，controller的所有方法
     */
    @Pointcut("execution(public * com.kangkang.controller.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void sendRequestParmsInfo(JoinPoint joinPoint){
        //创建发送信息的内容
        LogTemplate logTemplate = new LogTemplate();
        //设置当前时间
        logTemplate.setStartTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        logTemplate.setResponseTime(System.currentTimeMillis());
        //获取HttpServletRequest对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 记录下请求内容
        logTemplate.setUrl(request.getRequestURL().toString());

        //获取方法的参数
        Object[] args = joinPoint.getArgs();
        //判断是不是无参的方法
        Stream<?> stream = ArrayUtils.isEmpty(args) ? Stream.empty() : Arrays.stream(args);
        //然后遍历，过滤掉HttpServletRequest、HttpServletResponse这两个参数，因为这两个参数不参与日志的获取
        List<Object> logArgs = stream
                .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                .collect(Collectors.toList());
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        //这个就是转译，将json转化为string，["你好","我去","阿斯顿发"]-->"["你好","我去","阿斯顿发"]"  后面是通过这个方法得到的值
        logTemplate.setRequestRawJson(StringEscapeUtils.unescapeJavaScript(JSONObject.toJSONString(JSON.toJSONString(logArgs))));
        //设置方法名
        logTemplate.setMethod(methodName);
        //设置方法的路径
        logTemplate.setPath(joinPoint.getSignature().getDeclaringTypeName());
        //将值交给logstash容器等待发送
        logStash.set(logTemplate);


    }

    /**
     * @Description:记录响应请求正常日志
     * @Param: [ret]
     * @Return: void
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(JoinPoint joinPoint,Object ret) throws Throwable {
        logStash.get().setResponseRawJson(StringEscapeUtils.unescapeJavaScript(JSONObject.toJSONString(ret)));
        //这里就是算响应时间
        logStash.get().setResponseTime(System.currentTimeMillis() - logStash.get().getResponseTime());
        logStash.get().setEndTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        addLogData();
        logger.info(logStash.get().toString());

    }

    //自定义日志需要输入的字段
    private void addLogData() {
        MDC.put("startTime", logStash.get().getStartTime());
        MDC.put("endTime", logStash.get().getEndTime());
        MDC.put("requestRawJson", logStash.get().getRequestRawJson());
        MDC.put("responseRawJson",logStash.get().getResponseRawJson());
        MDC.put("responseTime", logStash.get().getResponseTime());
        MDC.put("url", logStash.get().getUrl());
        MDC.put("method", logStash.get().getMethod());
        MDC.put("path", logStash.get().getPath());
    }


    /**
     * @Description:记录响应请求异常日志
     * @Param: [ex]
     * @Return: void
     */
    @AfterThrowing(throwing="ex",pointcut = "webLog()")
    public void afterThrowing(Exception  ex) {
        String errorCode = "";
        String msg = "";
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException be = (MethodArgumentNotValidException) ex;
            msg = be.getBindingResult().getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .reduce((m1, m2) -> m1 + ";" + m2)
                    .orElse("未获取到错误信息");
            errorCode = "500";
        } else {
            errorCode = "500";
            msg = ex.getMessage();
        }
//        logger.error("异常code:"+errorCode+",异常信息："+msg, e);
        JSONObject repJson=new JSONObject();
        repJson.put("errorCode",errorCode);
        repJson.put("errorMsg",msg);
        logStash.get().setResponseRawJson(StringEscapeUtils.unescapeJavaScript(JSONObject.toJSONString(repJson)));
        logStash.get().setResponseTime(System.currentTimeMillis() - logStash.get().getResponseTime());
        logStash.get().setEndTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        logStash.get().setMessage(msg);
        addLogData();
//        logger.error("异常code:"+errorCode+",异常信息："+msg, ex);
        logger.info(logStash.get().toString());
    }

//    public static void main(String[] args) {
//        String[] template={"你好","我去","阿斯顿发"};
//
//        java.lang.String s = StringEscapeUtils.unescapeJavaScript(JSONObject.toJSONString(JSON.toJSONString(template)));
//        System.out.println("s = " + s);
//        System.out.println("ss = " + JSONObject.toJSONString(template));
//    }

}

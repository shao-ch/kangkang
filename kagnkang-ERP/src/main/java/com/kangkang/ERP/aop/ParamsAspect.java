package com.kangkang.ERP.aop;

import com.alibaba.fastjson.JSONObject;
import com.kangkang.ERP.mynote.ParamsAOP;
import com.kangkang.tools.ReflexObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: ParamsAspect
 * @Author: shaochunhai
 * @Date: 2022/4/17 3:53 下午
 * @Description: TODO
 */
@Slf4j
@Aspect
@Component
public class ParamsAspect {

    //配置切点
    @Pointcut("@annotation(com.kangkang.ERP.mynote.ParamsAOP)")
    public void paramsPoint() {
    }

    @Around("paramsPoint()")
    public void checkParmas(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("=========进入参数校验==========");
        //获取request和response
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletResponse response = attributes.getResponse();
        HttpServletRequest request = attributes.getRequest();

        //获取标志
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("=========接口路径为:{" +request.getRequestURI() + "}==========");
        //获取注解
        ParamsAOP annotation = method.getAnnotation(ParamsAOP.class);

        //获取方法的参数名称
        Parameter[] parameters = method.getParameters();
        //获取参数值
        Object[] args = joinPoint.getArgs();
        log.info("======校验的总参数为：" + JSONObject.toJSONString(args));
        //获取注解属性的值,也就是需要对应的规则的值
        String s = annotation.parmsName();

        Class classz = annotation.classz();

        //获取正则规则
        String regex = annotation.rule();
        for (int i = 0; i < parameters.length; i++) {
            //如果对应的名称匹配上了的话，并且是string类型就进行规则的匹配

            if (parameters[i].getParameterizedType().getTypeName().equals(String.class.getName())) {
                if (s.equals(parameters[i].getName())) {
                    //规则内容的匹配校验
                    if (ruleMatchCheck(response, regex,
                            "【" + parameters[i].getName() + "】此属性不符个规则！", args[i])){
                        return;
                    }
                }
            }else {
                Object value = ReflexObjectUtil.getValueByKey(args[i], s);
                if (ruleMatchCheck(response, regex,
                        "[" + parameters[i].getName() + "]该参数对象中【" + parameters[i].getName() + "】此属性不符个规则！", value)){
                    return;
                };
            }
        }
        //执行目标方法
        Object proceed = joinPoint.proceed();

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(JSONObject.toJSONString(proceed));
    }

    /**
     * 规则匹配
     * @param response
     * @param regex         正则表达式
     * @param message       返回给前台的信息
     * @param arg           参数实际数据
     * @throws IOException
     */
    private boolean ruleMatchCheck(HttpServletResponse response, String regex,
                           String message, Object arg) throws IOException {

        //载入规则
        Pattern compile = Pattern.compile(regex);

        Matcher matcher = compile.matcher(arg.toString());
        //如果没有按照规则匹配就返回前端
        if (!matcher.matches()) {
            HashMap<String, Object> map = new HashMap<>();

            map.put("code", "201");
            map.put("message", message);

            //返回前台数据
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(JSONObject.toJSONString(map));

            log.info("值为：["+arg+"]========"+message);
            return true;
        }
        return false;
    }

}


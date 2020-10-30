package com.parent.aoptest.aop;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.parent.aoptest.annotation.AopAnnotation;
import com.parent.aoptest.pojo.RequestInfo;
import com.parent.aoptest.service.RequestInfoService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 记录所有被调用的请求接口信息
 */

@Aspect
@Component
public class RequestAspect {

    @Autowired
    private RequestInfoService service;

    private Logger logger = LoggerFactory.getLogger(RequestAspect.class);

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {
    }

    /**
     * controller包下的所有方法都是切点
     */
    @Pointcut("execution(* com.parent.aoptest.controller.*.*(..))")
    public void methodPointCut() {

    }

    /**
     * 环绕通知，获得运行接口信息
     * @param joinPoint
     * @param aopAnnotation
     */
    @Around("requestMapping() && methodPointCut() && @annotation(aopAnnotation)")
    public void doAround(ProceedingJoinPoint joinPoint, AopAnnotation aopAnnotation) {

        //获得执行类的类名
        String clazzName = joinPoint.getSignature().getDeclaringTypeName();
        logger.info("执行类名：" + clazzName);
        //获得签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取执行方法
        Method method = methodSignature.getMethod();
        //获得执行方法名
        String methodName = method.getName();
        logger.info("执行方法名：" + methodName);
        //获取请求参数名，以及请求参数值
        Map<String, String> keyValue = new HashMap<String, String>();
        DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = nameDiscoverer.getParameterNames(method);
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < parameterNames.length; i++) {
            logger.info("参数名："+parameterNames[i]+"--->参数值："+args[i]);
            keyValue.put(parameterNames[i], args[i].toString());
        }
        //获得方法返回值
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
            logger.info(proceed.toString());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setClassName(clazzName);
        requestInfo.setMethodName(methodName);
        String params = JSON.toJSONString(keyValue);
        requestInfo.setParams(params);
        String result = JSON.toJSONString(proceed);
        requestInfo.setResults(result);

        this.service.saveInfo(requestInfo);
    }

}

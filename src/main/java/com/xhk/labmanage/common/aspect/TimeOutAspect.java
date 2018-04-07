package com.xhk.labmanage.common.aspect;

import com.xhk.labmanage.utils.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * create by xhk on 18/3/4
 */
public class TimeOutAspect {
    private static Logger logger = LoggerFactory.getLogger(TimeOutAspect.class);
    public void beforeAdvice() {

    }

    public void afterAdvice() {
    }

    public void afterReturnAdvice(String result) {
    }

    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = "";
        long start = System.currentTimeMillis();
        result =  proceedingJoinPoint.proceed();

        try {
            long end = System.currentTimeMillis();
            logger.info("接口耗时："+(end - start)+"ms");
            if(end-start > 3000){
                Signature s = proceedingJoinPoint.getSignature();
                String className = proceedingJoinPoint.getTarget().getClass().getName();
                String methodName = s.getName();
                logger.warn("className={},methodName={},params={}",className,methodName, JsonUtil.getJsonFromObject(proceedingJoinPoint.getArgs()));
            }
        } catch (Throwable e) {
            logger.error("TimeOutAspect aroundAdvice error",e);
        }
        return result;
    }

    public void throwingAdvice(JoinPoint joinPoint, Exception e) {

    }
}

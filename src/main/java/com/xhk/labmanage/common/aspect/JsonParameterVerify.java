package com.xhk.labmanage.common.aspect;

import java.lang.annotation.*;

/**
 * create by xhk on 18/3/4
 */

//自定义注解相关设置
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonParameterVerify {
    public boolean isPage() default false;
}

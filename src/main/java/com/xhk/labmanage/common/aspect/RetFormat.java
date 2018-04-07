package com.xhk.labmanage.common.aspect;

import java.lang.annotation.*;

/**
 * create by xhk on 18/3/4
 */

//自定义注解相关设置
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RetFormat {
   public int type() default 0;
   public boolean isPage() default false;
   public boolean isBackAction() default false;//是否是后台管理员操作
   public int backActionType() default 0;//后台操作类型
}

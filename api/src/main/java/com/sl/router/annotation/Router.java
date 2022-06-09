package com.sl.router.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 暂时先使用Runtime，考虑到后期会接入动态加载插件的场景
 * 仅当method == Activity时 service有效。
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Router {
    String path();
    Class service() default Object.class;
    CreateMethod method() default CreateMethod.ACTIVITY;
}

package com.sl.router.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Router {
    String path();
    Class service() default Object.class;
    CreateMethod method() default CreateMethod.ACTIVITY;
}

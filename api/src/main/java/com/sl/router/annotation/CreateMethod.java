package com.sl.router.annotation;

public enum CreateMethod {
    NEW, // 通过new的方式创建对象
    GET, // 通过getInstance获取，单例对象
//    QUERY, // 通过queryInstance获取，单例对象
    ACTIVITY // 不返回实例，用于路由跳转Activity
}

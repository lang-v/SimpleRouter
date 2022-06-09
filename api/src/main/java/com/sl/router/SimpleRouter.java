package com.sl.router;

/**
 * 整个路由框架对外暴露的所有功能
 */
public class SimpleRouter {

    private SimpleRouter() {

    }

    public SimpleRouter getInstance() {
        return SingleTon.instance;
    }

    public static  <T> T getService(Class<T> tClass){
        return null;
    }

    public static void to(Object context, String path){

    }

    public static class SingleTon{
        private final static SimpleRouter instance = new SimpleRouter();
    }
}

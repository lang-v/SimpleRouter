package com.sl.router;

/**
 * 路由工具
 *
 * @date 2022-05-30
 * @author sanshui
 */
public class SimpleRouter {

    private RouterCollection collection;

    private SimpleRouter() {
        try {
            Class<?> clazz = Class.forName("com.sl.router.RouterCollectionImpl");
            collection = (RouterCollection) clazz.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("请检查项目编译环境,确定router-plugin是否开启。");
        }
    }

    public SimpleRouter getInstance() {
        return SingleTon.instance;
    }

    public static  <T> T getService(Class<T> tClass){
        return (T) SingleTon.instance.collection.findByInterface(tClass);
    }

    public static void to(Object context, String path){

    }

    public static class SingleTon{
        private final static SimpleRouter instance = new SimpleRouter();
    }
}

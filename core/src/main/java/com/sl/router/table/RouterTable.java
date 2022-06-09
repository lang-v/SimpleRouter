package com.sl.router.table;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import java.util.HashMap;
import java.util.List;

public class RouterTable extends HashMap<String, RouterInfo> {


    public void add(String clazz, String path, String service, String method) throws RouterInformationDuplicateException {
        RouterInfo info = new RouterInfo(clazz, path, service, method);
        if (containsKey(clazz)) {
            throw new RouterInformationDuplicateException("出现重复路由可能导致运行异常，Class:" + clazz + " path:" + path + " service:" + service);
        }
        put(clazz, info);
    }

    public void add(RouterInfo info) throws RouterInformationDuplicateException {
        if (containsKey(info.getService())) {
            throw new RouterInformationDuplicateException("出现重复路由可能导致运行异常，Class:" + info.getService() + " path:" + info.getPath() + " service:" + info.getService()+"");
        }
        put(info.getService(), info);
    }


}

package com.sl.router.table;

import com.sl.router.annotation.CreateMethod;

public class RouterInfo {

    private final String clazz;
    private final String path;
    private final String service;
    private final String createMethod;

    public RouterInfo(String clazz, String path, String service, String createMethod) {
        this.clazz = clazz;
        this.path = path;
        this.service = service;
        this.createMethod = createMethod;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("new RouterInfo(")
                .append(clazz+".class")
                .append(",")
                .append("\""+path+"\"")
                .append(",")
                .append(service+".class")
                .append(",")
                .append("\""+CreateMethod.valueOf(createMethod)+"\"")
                .append(");");
        return builder.toString();
    }


    public String getClazz() {
        return clazz;
    }

    public String getPath() {
        return path;
    }

    public String getService() {
        return service;
    }

    public String getCreateMethod() {
        return createMethod;
    }

}

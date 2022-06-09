package com.sl.router;

public interface RouterCollect {

    public Class findByPath(String path);
    public Class findByInterface(Class clazz);

}

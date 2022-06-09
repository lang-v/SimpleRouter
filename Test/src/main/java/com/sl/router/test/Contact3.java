package com.sl.router.test;

import com.sl.router.annotation.CreateMethod;
import com.sl.router.annotation.Router;

@Router(path = "/contact3",service = Base3.class,method = CreateMethod.NEW)
public class Contact3 implements Base3,Base {
    @Override
    public void hello() {

    }
}

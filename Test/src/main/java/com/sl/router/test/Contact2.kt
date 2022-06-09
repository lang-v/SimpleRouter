package com.sl.router.test

import com.sl.router.annotation.CreateMethod
import com.sl.router.annotation.Router

@Router(path = "/contact2",service = Base2::class,method = CreateMethod.NEW)
class Contact2 :Base2 {
    fun hello() {
        println("hello,contact2!")
    }
}
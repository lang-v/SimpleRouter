package com.sl.router.table;

// 路由信息重复时抛出此异常
public class RouterInformationDuplicateException extends Exception {
    RouterInformationDuplicateException(String message) {
        super(message);
    }
}
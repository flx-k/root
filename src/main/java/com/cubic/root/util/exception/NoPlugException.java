package com.cubic.root.util.exception;

public class NoPlugException extends Exception{
    public NoPlugException() {
        super();
    }
    public NoPlugException(String msg) {
        super(msg+" [插件不存在]");
    }
}

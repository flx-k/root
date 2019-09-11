package com.cubic.root.util.exception;

public class CantDoException extends Exception{
    public CantDoException() {
        super();
    }
    public CantDoException(String msg) {
        super(msg+"[不能执行此操作]");
    }
}

package com.georgeinfo.ginkgo.injection.exception;

/**
 * 配置文件路径异常
 *
 * @author George (GeorgeWorld@qq.com)
 */
public class WrongConfigPathException extends RuntimeException {
    public WrongConfigPathException() {
    }

    public WrongConfigPathException(String message) {
        super(message);
    }

    public WrongConfigPathException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongConfigPathException(Throwable cause) {
        super(cause);
    }
}

package com.georgeinfo.ginkgo.injection.exception;

/**
 * 通用依赖注入异常
 *
 * @author George (GeorgeWorld@qq.com)
 */
public class DIException extends Exception {
    public DIException() {
    }

    public DIException(String message) {
        super(message);
    }

    public DIException(String message, Throwable cause) {
        super(message, cause);
    }

    public DIException(Throwable cause) {
        super(cause);
    }
}

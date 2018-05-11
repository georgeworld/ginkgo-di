package com.georgeinfo.ginkgo.dynamic;

/**
 * 类扫描器异常
 *
 * @author George (GeorgeWorld@qq.com)
 */
public class ScannerException extends Exception {
    public ScannerException() {
    }

    public ScannerException(String message) {
        super(message);
    }

    public ScannerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScannerException(Throwable cause) {
        super(cause);
    }
}

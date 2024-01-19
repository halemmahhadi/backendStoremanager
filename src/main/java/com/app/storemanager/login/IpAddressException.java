package com.app.storemanager.login;

public class IpAddressException extends Exception {
    public IpAddressException() {
    }

    public IpAddressException(String message) {
        super(message);
    }

    public IpAddressException(String message, Throwable cause) {
        super(message, cause);
    }

    public IpAddressException(Throwable cause) {
        super(cause);
    }

    public IpAddressException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

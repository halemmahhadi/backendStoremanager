package com.app.storemanager.common;

import com.app.storemanager.login.IpAddressException;

import javax.servlet.http.HttpServletRequest;

public class Common {

    public static final int TOKEN_EXPIRATION_TIME = 1000 * 60 * 20;

    public String getIpAddress(HttpServletRequest request) throws IpAddressException {
        String ipAddress = "";
        if (request != null) {
            ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null || "".equals(ipAddress)) {
                return  request.getRemoteAddr();
            } else {
                throw new IpAddressException("The ipAddress is empty");

            }
        } else {
            throw new IpAddressException("The request object is empty");
        }
    }

    public static String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}

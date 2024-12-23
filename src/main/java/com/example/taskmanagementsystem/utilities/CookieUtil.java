package com.example.taskmanagementsystem.utilities;

import javax.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    public static Cookie createJwtCookie(String cookieValue,int maxAge) {
        Cookie jwtCookie = new Cookie("JWT", cookieValue);
        jwtCookie.setMaxAge(maxAge);
        jwtCookie.setDomain("localhost");
        jwtCookie.setPath("/");
        jwtCookie.setHttpOnly(true);
        return jwtCookie;
    }
    public static String cookieToString(Cookie cookie) {
        return String.format("%s=%s; HttpOnly; Domain=%s; Path=%s; Max-Age=%d",cookie.getName(),cookie.getValue(),cookie.getDomain(),cookie.getPath(),cookie.getMaxAge());
    }
}

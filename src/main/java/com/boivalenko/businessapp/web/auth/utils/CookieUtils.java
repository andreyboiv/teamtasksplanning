package com.boivalenko.businessapp.web.auth.utils;

import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class CookieUtils {

    @Value("${cookie.jwt.name}")
    private String cookieJwtName;

    @Value("${cookie.jwt.max-age}")
    private int cookieAccessTokenDuration;

    @Value("${cookie.server.domain}")
    private String cookieAccessTokenDomain;

    // Cookie wird NUR auf dem Server erzeugt (server-side cookie)
    // mit jwt als Value und auf dem Server wird er validiert
    public HttpCookie createJwtCookie(String jwt) { // jwt - Value für Cookie
        return ResponseCookie.
        from(cookieJwtName, jwt) //Name und Bedeutung von Cookie
                .maxAge(cookieAccessTokenDuration)
                .sameSite(SameSiteCookies.STRICT.getValue())
                .httpOnly(true)
                .secure(true) //Cookie wird von Client an Server übergeben nur im Fall von https
                .domain(cookieAccessTokenDomain) //für welche domain wird Cookie valid
                .path("/") //Cookie wird für jede URl vom Server verfügbar
                .build();

    }

    public String getCookieAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String retVal = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieJwtName.equals(cookie.getName())) {
                    retVal = cookie.getValue();
                    break;
                }
            }
        }
        return retVal;
    }


    public HttpCookie deleteJwtCookie() {
        return ResponseCookie.
                from(cookieJwtName, null).
                maxAge(0) //Cookie mit O Age wird vom Browser automatisch gelöscht
                .sameSite(SameSiteCookies.STRICT.getValue())
                .httpOnly(true)
                .secure(true) //Cookie wird von Client an Server übergeben nur im Fall von https
                .domain(cookieAccessTokenDomain) //für welche domain wird Cookie valid
                .path("/") //Cookie wird für jede URl vom Server verfügbar
                .build();


    }

}

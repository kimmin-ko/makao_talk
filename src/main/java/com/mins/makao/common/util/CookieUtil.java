package com.mins.makao.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class CookieUtil {

    /**
     * 쿠키 모두 출력
     */
    public static void printAlLCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        Arrays.stream(cookies).forEach(cookie -> log.info("Cookie Name: {}, Cookie Value: {}", cookie.getName(), cookie.getValue()));
    }

    /**
     * 쿠키 생성
     */
    public static void createCookie(HttpServletResponse response, String name, String value, int day) {
        Objects.requireNonNull(response, "response is required.");
        Objects.requireNonNull(name, "cookie name is required.");
        Objects.requireNonNull(value, "cookie value is required.");

        if (day <= 0)
            throw new IllegalArgumentException("쿠키 기간은 0일 이상이어야 합니다.");

        log.info("create cookie name: {}", name);

        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(day * 60 * 60 * 24);
//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    /**
     * 쿠키 얻기
     */
    public static Optional<String> getCookie(HttpServletRequest request, String name) {
        Objects.requireNonNull(request, "request is required.");
        Objects.requireNonNull(name, "cookie name is required.");

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    log.info("get cookie name: {}", name);
                    return Optional.of(cookie.getValue());
                }
            }
        }

        return Optional.empty();
    }

    /**
     * 쿠키 삭제
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Objects.requireNonNull(request, "request is required.");
        Objects.requireNonNull(response, "response is required.");
        Objects.requireNonNull(name, "cookie name is required.");

        Cookie[] cookies = request.getCookies();

        if (Objects.nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    log.info("delete cookie name: {}", name);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
    }

    /**
     * 모든 쿠키 삭제
     */
    public static void deleteAllCookies(HttpServletRequest request, HttpServletResponse response) {
        Objects.requireNonNull(request, "request is required.");
        Objects.requireNonNull(response, "response is required.");

        Cookie[] cookies = request.getCookies();

        if (Objects.nonNull(cookies)) {
            log.info("delete all cookies");
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
    }

    /**
     * 해당 domain의 쿠키 삭제
     */
    public static void deleteCookieByDomain(HttpServletRequest request, HttpServletResponse response, String cookieName, String domainName) {
        Objects.requireNonNull(request, "request is required.");
        Objects.requireNonNull(response, "response is required.");
        Objects.requireNonNull(cookieName, "cookieName is required.");
        Objects.requireNonNull(domainName, "domainName is required.");

        Cookie[] cookies = request.getCookies();

        if (Objects.nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    log.info("delete cookie name: {}", cookieName);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    cookie.setDomain(domainName);
                    response.addCookie(cookie);
                }
            }
        }
    }

}

package com.mins.makao.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info(String.format("START URL ==> %-35s TIME ==> %-25s IP ==> %s", request.getRequestURI(), now().format(ofPattern("yyyy-MM-dd HH:mm:ss")), request.getRemoteAddr()));
        request.setAttribute("startTime", System.nanoTime());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        long endTime = System.nanoTime();
        long startTime = (long) request.getAttribute("startTime");
        log.info(String.format("END URL   ==> %-35s {executed in %d msec}", request.getRequestURI(), (endTime - startTime) / 1000000));
    }

}
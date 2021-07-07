package com.roofstacks.walletservice.configurations;

import com.roofstacks.walletservice.utils.ClientRequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    ClientRequestInfo clientRequestInfo;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        clientRequestInfo.setClientIpAdress(request.getRemoteAddr());
        clientRequestInfo.setSessionActivityId(request.getSession().getId());
        clientRequestInfo.setClientUrl(request.getRequestURI());
        return true;
    }
}
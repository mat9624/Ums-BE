package com.example.ums.service.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpHeaders;
import com.example.ums.service.UserServiceDB;

@Component
public class UmsInterceptor implements HandlerInterceptor {

    @Autowired
    private UserServiceDB userServiceDB;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        Boolean requestValidation=false;
        try{
            String auth= request.getHeader("authorization");
            requestValidation=userServiceDB.checkAuth(auth);
            if(!requestValidation){
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }catch (Exception e){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        return requestValidation;
    }

}

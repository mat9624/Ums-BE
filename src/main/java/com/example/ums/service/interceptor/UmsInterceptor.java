package com.example.ums.service.interceptor;

import com.example.ums.UmsApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.ums.service.UserServiceDB;

@Component
public class UmsInterceptor implements HandlerInterceptor {

    @Autowired
    private UserServiceDB userServiceDB;
    @Autowired
    private UmsApplication test; // per misurare utilizzo memoria

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println(System.nanoTime() / 1000000); // test velocità
        Boolean requestValidation = false;
        try {
            String auth = request.getHeader("authorization");
            requestValidation = userServiceDB.checkAuth(auth);
            test.runGC();   // avvio test memoria
            System.out.println(System.nanoTime() / 1000000);  // test velocità
            if (!requestValidation) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        return requestValidation;
    }

}

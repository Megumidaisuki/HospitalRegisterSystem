package com.megumi.hospitalregistersystem.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;


import com.megumi.hospitalregistersystem.domain.Doctor;
import com.megumi.hospitalregistersystem.exception.serviceException;
import com.megumi.hospitalregistersystem.service.DoctorService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    private static final String ERROR_CODE_401 = "401";

    @Autowired
    private DoctorService doctorService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");
        }
        if("OPTIONS".equals(request.getMethod().toUpperCase())) {
            System.out.println("Method:OPTIONS");
            return true;
        }

        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new serviceException(ERROR_CODE_401, "无token，请重新登录");
        }
        // 获取 token 中的doctorId
        String doctorId;
        Doctor doctor;
        try {
            doctorId = JWT.decode(token).getAudience().get(0);
            // 根据token中的userid查询数据库
            doctor = doctorService.getById(Integer.parseInt(doctorId));
        } catch (Exception e) {
            String errMsg = "token验证失败，请重新登录";
            log.error(errMsg + ", token=" + token, e);
            throw new serviceException(ERROR_CODE_401, errMsg);
        }
        if (doctor == null) {
            throw new serviceException(ERROR_CODE_401, "用户不存在，请重新登录");
        }

        try {
            // 用户密码加签验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(doctor.getPassword())).build();
            jwtVerifier.verify(token); // 验证token
        } catch (JWTVerificationException e) {
            throw new serviceException(ERROR_CODE_401, "token验证失败，请重新登录");
        }
        return true;
    }
}
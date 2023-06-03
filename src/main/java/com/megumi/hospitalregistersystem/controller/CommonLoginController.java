package com.megumi.hospitalregistersystem.controller;

import com.megumi.hospitalregistersystem.common.Result;
import com.megumi.hospitalregistersystem.controller.dto.CommonLoginDTO;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
public class CommonLoginController {
    @Autowired
    LoginService loginService;
    //登录
    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        CommonLoginDTO commonLoginDTO = loginService.login(loginRequest);
        return Result.success(commonLoginDTO);
    }
}

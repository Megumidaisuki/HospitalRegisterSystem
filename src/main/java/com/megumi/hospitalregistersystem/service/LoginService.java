package com.megumi.hospitalregistersystem.service;

import com.megumi.hospitalregistersystem.controller.dto.CommonLoginDTO;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.domain.Doctor;
import com.megumi.hospitalregistersystem.domain.Patient;

public interface LoginService {
    CommonLoginDTO login(LoginRequest loginRequest);

    //通过用户名得到patient对象
    Patient getByUsernamePatient(String username);

    //通过用户名得到doctor对象
    Doctor getByUsernameDoctor(String username);
}

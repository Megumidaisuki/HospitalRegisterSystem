package com.megumi.hospitalregistersystem.dao;

import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.domain.Doctor;
import com.megumi.hospitalregistersystem.domain.Patient;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginDao {
    Patient getByUsernamePatient(String username);

    Doctor getByUsernameDoctor(String username);

    Doctor loginDoctor(LoginRequest loginRequest);

    Patient loginPatient(LoginRequest loginRequest);
}

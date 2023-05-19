package com.megumi.hospitalregistersystem.dao;

import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.domain.Patient;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PatientDao {
    Patient getByUsername(String username);

    Patient login(LoginRequest loginRequest);

    void save(Patient patient);
}

package com.megumi.hospitalregistersystem.service;

import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.domain.Patient;

public interface PatientService {
    LoginDTO login(LoginRequest loginRequest);

    void save(Patient patient);
}

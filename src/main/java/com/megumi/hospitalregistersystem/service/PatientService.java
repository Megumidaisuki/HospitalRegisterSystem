package com.megumi.hospitalregistersystem.service;

import com.github.pagehelper.PageInfo;
import com.megumi.hospitalregistersystem.controller.dto.DoctorPageDTO;
import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.controller.request.RegisterTypePageRequest;
import com.megumi.hospitalregistersystem.domain.Patient;
import com.megumi.hospitalregistersystem.domain.RegisterMessage;
import com.megumi.hospitalregistersystem.domain.RegisterType;

import java.util.List;

public interface PatientService {
    LoginDTO login(LoginRequest loginRequest);

    void save(Patient patient);

    PageInfo<DoctorPageDTO> pageDoctor(DoctorPageRequest pageRequest);

    RegisterType getByDepartment(String department);

    PageInfo<RegisterType> pageRegister(RegisterTypePageRequest pageRequest);

    List<RegisterMessage> getHistory(Integer id);

    void updateStatus(RegisterMessage registerMessage);
}

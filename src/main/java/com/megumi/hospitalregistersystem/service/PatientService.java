package com.megumi.hospitalregistersystem.service;

import com.github.pagehelper.PageInfo;
import com.megumi.hospitalregistersystem.controller.dto.DoctorPageDTO;
import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.controller.request.NewPassRequest;
import com.megumi.hospitalregistersystem.controller.request.RegisterTypePageRequest;
import com.megumi.hospitalregistersystem.domain.Patient;
import com.megumi.hospitalregistersystem.domain.RegisterMessage;
import com.megumi.hospitalregistersystem.domain.RegisterType;

import java.util.List;

public interface PatientService {
    LoginDTO login(LoginRequest loginRequest);

    void save(Patient patient);

    PageInfo<DoctorPageDTO> pageDoctor(DoctorPageRequest pageRequest);

    List<RegisterType> getByDepartment(String department);

    PageInfo<RegisterType> pageRegister(RegisterTypePageRequest pageRequest);

    List<RegisterMessage> getHistory(Patient patient);

    void updateStatus(RegisterMessage registerMessage);

    void newPass(NewPassRequest newPassRequest);

    void register(RegisterType registerType, Patient patient);





    //将患者信息的失约次数加一(唯一确定订单信息)
    void updatePatientMessage(String patientName, String doctorName, Integer cost);

    List<DoctorPageDTO> findAll();
}

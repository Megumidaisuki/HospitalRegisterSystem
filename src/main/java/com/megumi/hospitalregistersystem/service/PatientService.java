package com.megumi.hospitalregistersystem.service;

import com.github.pagehelper.PageInfo;
import com.megumi.hospitalregistersystem.controller.dto.DoctorPageDTO;
import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.controller.request.NewPassRequest;
import com.megumi.hospitalregistersystem.controller.request.RegisterTypePageRequest;
import com.megumi.hospitalregistersystem.domain.Doctor;
import com.megumi.hospitalregistersystem.domain.Patient;
import com.megumi.hospitalregistersystem.domain.RegisterMessage;
import com.megumi.hospitalregistersystem.domain.RegisterType;

import java.util.List;

public interface PatientService {
    LoginDTO login(LoginRequest loginRequest);

    void save(Patient patient);

    List<RegisterMessage> getAllRegisterMessage();

    void newPass(NewPassRequest newPassRequest);

    RegisterMessage register(Integer id);

    List<Doctor> findAll();

    List<RegisterMessage> getHistoryRegisterMessage();

    List<String> getDepartmentByDateAndTimeScope(String date, String timeScope);

    List<RegisterType> getAllRegisterType();

    List<String> getNameByDateAndTimeScopeAndDepartment(String department, String date, String timeScope);

    RegisterMessage getMessageByRegisterType(RegisterType RegisterType);

    RegisterType getTypeByDateAndTimeScopeAndDepartmentAndName(String name, String department, String date, String timeScope);

    List<RegisterType> getMessageByDate(String date);

    List<RegisterType> getMessageByDateAndTimeScope(String date, String timeScope);

    List<RegisterType> getMessageByDateAndTimeScopeAndDepartment(String department, String name, String date, String timeScope);

    void deleteHistoryRegisterMessage(Integer id);

    Patient getById(Integer id);

    void pay(Integer id);

    Doctor getDoctorById(Integer id);

    List<String> getDepartment();
    PageInfo<DoctorPageDTO> pageDoctor(DoctorPageRequest pageRequest);

    List<RegisterType> getByDepartment(String department);

    PageInfo<RegisterType> pageRegister(RegisterTypePageRequest pageRequest);

    void updateStatus(RegisterMessage registerMessage);
    //将患者信息的失约次数加一(唯一确定订单信息)
    void updatePatientMessage(String patientName, String doctorName, Integer cost);
    List<String> getDocotrNameByDepartment(String department);

    List<RegisterType> getByDepartmentAndName(String department, String doctorName);
    List<RegisterType> darkRegister(String doctorName, String department);

    void logout();
}


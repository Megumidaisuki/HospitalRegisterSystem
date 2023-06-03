package com.megumi.hospitalregistersystem.dao;

import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.controller.request.NewPassRequest;
import com.megumi.hospitalregistersystem.domain.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PatientDao {
    Patient getByUsername(String username);

    Patient login(LoginRequest loginRequest);

    void save(Patient patient);

    String getNameById(Integer id);

    List<RegisterMessage> getRegisterMessageByName(Patient patient);

    void updateStatus(RegisterMessage registerMessage);

    void updatePassword(NewPassRequest newPassRequest);

    void newPatientMessage(String doctorName,String name,Integer age,Integer gender,Long phone,Integer discreditTimes);

    void updateArrangementMessage(RegisterType registerType);

    void updateRegisterType(RegisterType registerType);

    TypeMessage getTypeMessageByRegisterType(RegisterType registerType);

    void register(Integer typeId, Integer cost, String patientName, String doctorName, Long orderId, String timeScope, String department);

    void updatePatientMessage(String patientName, String doctorName, Integer cost);

    RegisterMessage getAccurateRegisterMessage(RegisterType registerType, Patient patient);

    List<Doctor> findAll();

    List<RegisterMessage> getHistoryRegister(Patient patient);

    List<RegisterType> getAllRegisterType();

    List<String> getDepartmentByDateAndTimeScope(String date, String timeScope);

    List<String> getNameByDateAndTimeScopeAndDepartment(String department, String date, String timeScope);

    RegisterType getTypeByDateAndTimeScopeAndDepartmentAndName(String name, String department, String date, String timeScope);

    RegisterMessage getRegisterMessage(Integer typeId,Integer cost,String patientName,String doctorName,Long orderId);

    List<RegisterType> getMessageByDate(String date);

    List<RegisterType> getMessageByDateAndTimeScope(String date, String timeScope);

    List<RegisterType> getMessageByDateAndTimeScopeAndDepartmentAndName(String department, String name, String date, String timeScope);

    void deleteHistoryRegisterMessage(Integer id);

    Patient getById(Integer id);

    void pay(Integer id);

    Doctor getDoctorById(Integer id);

    RegisterType getRegisterTypeById(Integer id);
}

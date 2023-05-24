package com.megumi.hospitalregistersystem.dao;

import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.controller.request.NewPassRequest;
import com.megumi.hospitalregistersystem.controller.request.RegisterTypePageRequest;
import com.megumi.hospitalregistersystem.domain.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PatientDao {
    Patient getByUsername(String username);

    Patient login(LoginRequest loginRequest);

    void save(Patient patient);

    List<Doctor> queryByNameAndDepartment(DoctorPageRequest pageRequest);

    RegisterType getByDepartment(String department);

    List<RegisterType> getByNameAndDepartment(RegisterTypePageRequest pageRequest);

    String getNameById(Integer id);

    List<RegisterMessage> getRegisterMessageByName(Patient patient);

    void updateStatus(RegisterMessage registerMessage);

    void updatePassword(NewPassRequest newPassRequest);

    void newPatientMessage(RegisterType registerType, Patient patient);

    void updateArrangementMessage(RegisterType registerType);

    void updateRegisterType(RegisterType registerType);
    TypeMessage getTypeMessageByRegisterType(RegisterType registerType);

    void register(TypeMessage typeMessage, Patient patient, RegisterType registerType);
}

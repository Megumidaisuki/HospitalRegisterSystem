package com.megumi.hospitalregistersystem.dao;

import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.controller.request.RegisterTypePageRequest;
import com.megumi.hospitalregistersystem.domain.Doctor;
import com.megumi.hospitalregistersystem.domain.Patient;
import com.megumi.hospitalregistersystem.domain.RegisterMessage;
import com.megumi.hospitalregistersystem.domain.RegisterType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PatientDao {
    Patient getByUsername(String username);

    Patient login(LoginRequest loginRequest);

    void save(Patient patient);

    List<Doctor> queryByNameAndDepartment(DoctorPageRequest pageRequest);

    RegisterType getByDepartment(String department);

    RegisterType getByNameAndDepartment(RegisterTypePageRequest pageRequest);

    String getNameById(Integer id);

    List<RegisterMessage> getRegisterMessageByName(String name);

    void updateStatus(RegisterMessage registerMessage);
}

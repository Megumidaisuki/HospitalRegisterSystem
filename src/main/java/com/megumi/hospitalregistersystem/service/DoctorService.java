package com.megumi.hospitalregistersystem.service;

import com.github.pagehelper.PageInfo;
import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.domain.Doctor;
import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;

public interface DoctorService {
    Doctor getById(Integer id);

    LoginDTO login(LoginRequest loginRequest);
    Doctor getByUsername(String username);

    void save(Doctor doctor);

    void update(Doctor doctor);

    PageInfo<Doctor> page(DoctorPageRequest pageRequest);
}

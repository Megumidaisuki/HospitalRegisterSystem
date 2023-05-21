package com.megumi.hospitalregistersystem.service;

import com.github.pagehelper.PageInfo;
import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.domain.ArrangementMessage;
import com.megumi.hospitalregistersystem.domain.ArrangementTemplate;
import com.megumi.hospitalregistersystem.domain.Doctor;
import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;

import java.util.List;

public interface DoctorService {
    Doctor getById(Integer id);

    LoginDTO login(LoginRequest loginRequest);
    Doctor getByUsername(String username);

    void save(Doctor doctor);

    void update(Doctor doctor);

    PageInfo<Doctor> page(DoctorPageRequest pageRequest);

    List<ArrangementMessage> showSchedule(Doctor doctor);

    List<ArrangementTemplate> showTemplate();

    void newTemplate(ArrangementTemplate arrangementType);

    void updateSchedule(ArrangementMessage arrangementMessage);

    void updateTemplate(ArrangementTemplate arrangementType);

    void deleteSchedule(ArrangementMessage arrangementMessage);

    void deleteTemplate(ArrangementTemplate arrangementTemplate);
}

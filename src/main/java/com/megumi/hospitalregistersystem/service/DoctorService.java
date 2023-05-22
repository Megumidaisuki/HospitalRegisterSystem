package com.megumi.hospitalregistersystem.service;

import com.github.pagehelper.PageInfo;
import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.controller.request.NewPassRequest;
import com.megumi.hospitalregistersystem.domain.ArrangementMessage;
import com.megumi.hospitalregistersystem.domain.ArrangementTemplate;
import com.megumi.hospitalregistersystem.domain.Doctor;
import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.domain.PatientMessage;

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

    void setMessageByTemplate(ArrangementTemplate arrangementTemplate, Doctor doctor);

    void copyByDay(String date, String targetDate);
    List<ArrangementMessage> getMessageByDate(String date);

    void copyByWeek(int year, int week, int targetWeek);

    List<PatientMessage> getPatientMessage(Doctor doctor);

    void updatePatientMessage(Doctor doctor);

    void newPass(NewPassRequest newPassRequest);
}

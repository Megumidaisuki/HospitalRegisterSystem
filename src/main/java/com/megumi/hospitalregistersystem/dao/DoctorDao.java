package com.megumi.hospitalregistersystem.dao;

import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.controller.request.NewPassRequest;
import com.megumi.hospitalregistersystem.domain.ArrangementMessage;
import com.megumi.hospitalregistersystem.domain.ArrangementTemplate;
import com.megumi.hospitalregistersystem.domain.Doctor;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.domain.PatientMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DoctorDao {
    Doctor getById(Integer id);

    Doctor login(LoginRequest loginRequest);

    Doctor getByUsername(String username);

    void save(Doctor doctor);

    void update(Doctor doctor);

    List<Doctor> queryByNameAndDepartment(DoctorPageRequest pageRequest);

    List<ArrangementMessage> getByName(Doctor doctor);

    List<ArrangementTemplate> getAllTemplate();

    void newTemplate(ArrangementTemplate arrangementType);

    void updateSchedule(ArrangementMessage arrangementMessage);

    void updateTemplate(ArrangementTemplate arrangementType);

    void deleteSchedule(ArrangementMessage arrangementMessage);

    void deleteTemplate(ArrangementTemplate arrangementTemplate);

    void setMessage(ArrangementMessage arrangementMessage);

    List<ArrangementMessage> getMessageByDate(String date);

    List<PatientMessage> getPatientMessage(Doctor doctor);

    void updatePatientMessage(Doctor doctor);

    void updatePassword(NewPassRequest newPassRequest);
}

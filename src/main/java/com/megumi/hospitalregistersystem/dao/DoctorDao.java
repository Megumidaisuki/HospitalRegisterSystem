package com.megumi.hospitalregistersystem.dao;

import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.domain.ArrangementMessage;
import com.megumi.hospitalregistersystem.domain.ArrangementTemplate;
import com.megumi.hospitalregistersystem.domain.Doctor;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
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
}

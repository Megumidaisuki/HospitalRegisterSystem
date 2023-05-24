package com.megumi.hospitalregistersystem.dao;

import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.controller.request.NewPassRequest;
import com.megumi.hospitalregistersystem.domain.*;
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

    void setMessage(ArrangementMessage arrangementMessage);

    List<ArrangementMessage> getMessageByDate(String date);

    List<PatientMessage> getPatientMessage(Doctor doctor);

    void updatePatientMessage(Patient patient);

    void updatePassword(NewPassRequest newPassRequest);

    void setMessageFollowed(ArrangementMessage message);

    void updateRegisterTypeByArrangement(ArrangementMessage arrangementMessage);

    void deleteRegisterTypeByArrangement(ArrangementMessage arrangementMessage);

    void newRegisterTypeByArrangement(ArrangementMessage arrangementMessage);

    List<RegisterMessage> getRegisterMessageByName(Doctor doctor);

    void updateStatus(RegisterMessage registerMessage);
}

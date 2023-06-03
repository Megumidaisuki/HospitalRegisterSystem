package com.megumi.hospitalregistersystem.service;

import com.github.pagehelper.PageInfo;
import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.controller.request.NewPassRequest;
import com.megumi.hospitalregistersystem.domain.*;
import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;

import java.util.List;

public interface DoctorService {
    Doctor getById(Integer id);

    LoginDTO login(LoginRequest loginRequest);
    Doctor getByUsername(String username);

    void save(Doctor doctor);

    void update(Doctor doctor);

//    PageInfo<Doctor> page(DoctorPageRequest pageRequest);

    List<ArrangementMessage> showSchedule(Doctor doctor);

    List<ArrangementTemplate> showTemplate();

//    void newTemplate(ArrangementTemplate arrangementType);

    void updateSchedule(ArrangementMessage arrangementMessage);

    void updateTemplate(ArrangementTemplate arrangementType);

    void deleteSchedule(ArrangementMessage arrangementMessage);

    void deleteTemplate(ArrangementTemplate arrangementTemplate);

    void setMessageByTemplate(ArrangementTemplate arrangementTemplate, Doctor doctor);

    void copyByDay(String date, String targetDate);
    List<ArrangementMessage> getMessageByDate(String date);

    void copyByWeek(int year, int week, int targetWeek);

    List<PatientMessage> getPatientMessage();

    void updatePatientMessage(PatientMessage patient);
    List<RegisterMessage> getHistory();

    void newPass(NewPassRequest newPassRequest);
    void updateRegisterTypeByArrangement(ArrangementMessage arrangementMessage);

    void updateStatus(RegisterMessage registerMessage);

    List<Doctor> findAll();

    List<TypeMessage> getTypeMessage();

    void addTypeMessage(TypeMessage typeMessage);

    void deleteTypeMessage(Integer typeMessage);


    Doctor getDoctor();

    List<RegisterType> getAllRegisterType();

    void addRegisterType(String name,String date,String registerAmounts,String typeName,String timeScope);

    void deleteRegisterType(Integer id);

    List<RegisterType> getRegisterTypeByDate(String date);

    List<RegisterType> getFirstStatusRegisterType();

    List<RegisterType> getSecondStatusRegisterType();

    List<RegisterType> getThirdStatusRegisterType();

    List<RegisterMessage> getFullRegisterMessage();

    List<RegisterMessage> getHistoryRegisterMessage(String patientName);

    void logout();

    List<RegisterMessage> getRegisterMessageOne();

    List<RegisterMessage> getRegisterMessageTwo();

    void updateRegisterMessage(RegisterMessage registerMessage);

    void deleteRegisterMessage(Integer id);

    void newTemplate(String doctorName, String typeName, String registerAmounts, String timeScope);

    List<RegisterMessage> getSpecialRegisterMessage();
}

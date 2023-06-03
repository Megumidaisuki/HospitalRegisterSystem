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

//    List<Doctor> queryByNameAndDepartment(DoctorPageRequest pageRequest);

    List<ArrangementMessage> getByName(Doctor doctor);

    List<ArrangementTemplate> getAllTemplate();



    void updateSchedule(ArrangementMessage arrangementMessage);

    void updateTemplate(ArrangementTemplate arrangementType);

    void deleteSchedule(ArrangementMessage arrangementMessage);

    void deleteTemplate(ArrangementTemplate arrangementTemplate);



    List<ArrangementMessage> getMessageByDate(String date);

    List<PatientMessage> getPatientMessage(Doctor doctor);

    void updatePatientMessage(PatientMessage patientMessage);

    void updatePassword(NewPassRequest newPassRequest);

    void setMessageFollowed(ArrangementMessage message);

    void updateRegisterTypeByArrangement(ArrangementMessage arrangementMessage);

    void deleteRegisterTypeByArrangement(ArrangementMessage arrangementMessage);

    void newRegisterTypeByArrangement(ArrangementMessage arrangementMessage);

    List<RegisterMessage> getRegisterMessageByName(Doctor doctor);

    void updateStatus(RegisterMessage registerMessage);

    List<Doctor> findAll();

    List<TypeMessage> getTypeMessage(Doctor doctor);

    void addTypeMessage(TypeMessage typeMessage);

    void deleteTypeMessage(Integer id);

    Doctor getDoctor();

    List<RegisterType> getAllRegisterType(Doctor doctor);


    void deleteRegisterType(Integer id);

    Integer getTypeIdByTypeName(String typeName);

    List<RegisterType> getRegisterTypeByDate(String date);

    List<RegisterType> getFirstStatusRegisterType();

    List<RegisterType> getSecondStatusRegisterType();

    List<RegisterType> getThirdStatusRegisterType();

    List<RegisterMessage> getFullRegisterMessage(Doctor doctor);

    List<RegisterMessage> getHistoryRegisterMessage(String doctorName, String patientName);

    List<RegisterMessage> getRegisterMessageOne(Doctor doctor);

    List<RegisterMessage> getRegisterMessageTwo(Doctor doctor);

    void updateRegisterMessage(RegisterMessage registerMessage);

    void deleteRegisterMessage(Integer id);

    String getTypeIdByName(String doctorName, String typeName, String registerAmounts, String timeScope);

    void newTemplate(String doctorName, String typeId, String registerAmounts, String timeScope);

    void setMessage(ArrangementMessage message);


    void addRegisterType(String name, String date, String registerAmounts, Integer typeId, String timeScope);

    List<RegisterMessage> getSpecialRegisterMessage(String doctorName);
}

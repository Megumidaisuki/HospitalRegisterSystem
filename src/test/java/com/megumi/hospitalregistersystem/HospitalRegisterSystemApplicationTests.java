package com.megumi.hospitalregistersystem;

import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.dao.DoctorDao;
import com.megumi.hospitalregistersystem.dao.PatientDao;
import com.megumi.hospitalregistersystem.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class HospitalRegisterSystemApplicationTests {

    @Autowired
    PatientDao patientDao;
    @Autowired
    DoctorDao doctorDao;
    @Test
    void contextLoads() {
//        Patient patient = new Patient();
//        patient.setName("张三");
//        List<RegisterMessage> registerMessageByName = patientDao.getRegisterMessageByName(patient);
//        System.out.println(registerMessageByName);
    }

}

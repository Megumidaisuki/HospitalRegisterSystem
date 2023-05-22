package com.megumi.hospitalregistersystem.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.megumi.hospitalregistersystem.controller.dto.DoctorPageDTO;
import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.controller.request.NewPassRequest;
import com.megumi.hospitalregistersystem.controller.request.RegisterTypePageRequest;
import com.megumi.hospitalregistersystem.dao.PatientDao;
import com.megumi.hospitalregistersystem.domain.Doctor;
import com.megumi.hospitalregistersystem.domain.Patient;
import com.megumi.hospitalregistersystem.domain.RegisterMessage;
import com.megumi.hospitalregistersystem.domain.RegisterType;
import com.megumi.hospitalregistersystem.exception.serviceException;
import com.megumi.hospitalregistersystem.service.PatientService;
import com.megumi.hospitalregistersystem.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientDao patientDao;
    //定义盐
    private static final String PASS_SALT = "hospital";
    private String encrypt(String password){
        return SecureUtil.md5(password+PASS_SALT);
    }
    @Override
    public LoginDTO login(LoginRequest loginRequest) {
        //密码加密
        loginRequest.setPassword(encrypt(loginRequest.getPassword()));
        //初步判断
        Patient login = getByUsername(loginRequest.getUsername());
        if(login==null){
            throw new serviceException("401","用户名密码错误");
        } else if (!login.getPassword().equals(loginRequest.getPassword())) {
            throw new serviceException("401","用户名或密码不匹配");
        }
        Patient patient = patientDao.login(loginRequest);
        LoginDTO loginDTO = new LoginDTO();
        //类类型转换
        BeanUtils.copyProperties(patient,loginDTO);
        //生成token
        String token = TokenUtils.genToken(String.valueOf(login.getId()), login.getPassword());
        loginDTO.setToken(token);
        return loginDTO;

    }

    @Override
    public void save(Patient patient) {
        //加密
        patient.setPassword(encrypt(patient.getPassword()));

        patientDao.save(patient);
    }

    @Override
    public PageInfo<DoctorPageDTO> pageDoctor(DoctorPageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        //将Doctor对象转化为DoctorPageDTO返回
        List<Doctor> doctors = patientDao.queryByNameAndDepartment(pageRequest);
        List<DoctorPageDTO> pageDTOS = new ArrayList<>();
        for (Doctor doctor : doctors) {
            DoctorPageDTO pageDTO = new DoctorPageDTO();
            BeanUtils.copyProperties(doctors,pageDTO);
            pageDTOS.add(pageDTO);
        }
        PageInfo<DoctorPageDTO> pageInfo = new PageInfo<>(pageDTOS);
        return pageInfo;
    }

    @Override
    public RegisterType getByDepartment(String department) {
        RegisterType registerType = patientDao.getByDepartment(department);
        if(registerType==null){
            throw new serviceException("401","该科室不存在");
        }
        return registerType;
    }

    @Override
    public PageInfo<RegisterType> pageRegister(RegisterTypePageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        List<RegisterType> registerTypeS = (List<RegisterType>) patientDao.getByNameAndDepartment(pageRequest);
        PageInfo<RegisterType> pageInfo = new PageInfo<>(registerTypeS);
        return pageInfo;
    }

    @Override
    public List<RegisterMessage> getHistory(Integer id) {
        String name = patientDao.getNameById(id);
        List<RegisterMessage> messages = patientDao.getRegisterMessageByName(name);
        return messages;
    }

    @Override
    public void updateStatus(RegisterMessage registerMessage) {
        patientDao.updateStatus(registerMessage);
    }

    @Override
    public void newPass(NewPassRequest newPassRequest) {
        //先对新的密码加密
        newPassRequest.setNewPass(encrypt(newPassRequest.getNewPass()));
        patientDao.updatePassword(newPassRequest);
    }

    @Override
    public void register(RegisterType registerType, Patient patient) {
        //在register_message中添加信息
        patientDao.register(registerType,patient);
        //在patient_message中添加信息
        patientDao.newPatientMessage(registerType,patient);
    }

    public Patient getByUsername(String username) {
        return patientDao.getByUsername(username);
    }
}

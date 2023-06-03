package com.megumi.hospitalregistersystem.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.megumi.hospitalregistersystem.controller.dto.CommonLoginDTO;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.dao.LoginDao;
import com.megumi.hospitalregistersystem.domain.Doctor;
import com.megumi.hospitalregistersystem.domain.Patient;
import com.megumi.hospitalregistersystem.exception.serviceException;
import com.megumi.hospitalregistersystem.service.LoginService;
import com.megumi.hospitalregistersystem.utils.PTokenUtils;
import com.megumi.hospitalregistersystem.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    //定义盐
    private static final String PASS_SALT = "hospital";
    //提供一个根据密码加密的方法
    private String encrypt(String password){
        return SecureUtil.md5(password+PASS_SALT);
    }
    @Autowired
    private LoginDao loginDao;
    @Override
    public CommonLoginDTO login(LoginRequest loginRequest) {
        //密码加密
        loginRequest.setPassword(encrypt(loginRequest.getPassword()));
        //初步判断
        Patient patient = getByUsernamePatient(loginRequest.getUsername());
        if(patient==null){
            Doctor doctor = getByUsernameDoctor(loginRequest.getUsername());
            if(doctor==null){
                throw new serviceException("该用户不存在");
            }else if(!doctor.getPassword().equals(loginRequest.getPassword())){
                throw new serviceException("401","用户名或密码不匹配");
            }
            Doctor doctorr = loginDao.loginDoctor(loginRequest);
            CommonLoginDTO loginDTO = new CommonLoginDTO();
            //类类型转换
            BeanUtils.copyProperties(doctorr,loginDTO);
            loginDTO.setCategory("医生");
            //生成token
            String token = TokenUtils.genToken(String.valueOf(doctor.getId()), doctor.getPassword());
            loginDTO.setToken(token);
            return loginDTO;

        } else if (!patient.getPassword().equals(loginRequest.getPassword())) {
            throw new serviceException("401","用户名或密码不匹配");
        }
        Patient patientt = loginDao.loginPatient(loginRequest);
        CommonLoginDTO loginDTO = new CommonLoginDTO();
        //类类型转换
        BeanUtils.copyProperties(patientt,loginDTO);
        loginDTO.setCategory("患者");
        //生成token
        String token = PTokenUtils.genToken(String.valueOf(patient.getId()), patient.getPassword());
        loginDTO.setToken(token);
        return loginDTO;
    }
    //通过用户名得到patient对象
    @Override
    public Patient getByUsernamePatient(String username) {
        return loginDao.getByUsernamePatient(username);
    }

    //通过用户名得到doctor对象
    @Override
    public Doctor getByUsernameDoctor(String username) {
        return loginDao.getByUsernameDoctor(username);
    }
}

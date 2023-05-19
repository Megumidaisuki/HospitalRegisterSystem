package com.megumi.hospitalregistersystem.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.dao.DoctorDao;
import com.megumi.hospitalregistersystem.domain.Doctor;
import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.exception.serviceException;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.service.DoctorService;
import com.megumi.hospitalregistersystem.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorDao doctorDao;
    //定义盐
    private static final String PASS_SALT = "hospital";
    private String encrypt(String password){
        return SecureUtil.md5(password+PASS_SALT);
    }
    @Override
    public Doctor getById(Integer id) {
        return doctorDao.getById(id);
    }


    @Override
    public LoginDTO login(LoginRequest loginRequest) {
        //密码加密
        loginRequest.setPassword(encrypt(loginRequest.getPassword()));
        //初步判断
        Doctor login = getByUsername(loginRequest.getUsername());
        if(login==null){
            throw new serviceException("401","用户名密码错误");
        } else if (!login.getPassword().equals(loginRequest.getPassword())) {
            throw new serviceException("401","用户名或密码不匹配");
        }
        Doctor doctor = doctorDao.login(loginRequest);
        LoginDTO loginDTO = new LoginDTO();
        //类类型转换
        BeanUtils.copyProperties(doctor,loginDTO);
        //生成token
        String token = TokenUtils.genToken(String.valueOf(login.getId()), login.getPassword());
        loginDTO.setToken(token);
        return loginDTO;
    }




    @Override
    public Doctor getByUsername(String username) {
        return doctorDao.getByUsername(username);
    }

    @Override
    public void save(Doctor doctor) {
        //加密
        doctor.setPassword(encrypt(doctor.getPassword()));

        doctorDao.save(doctor);

    }

    @Override
    public void update(Doctor doctor) {

        doctorDao.update(doctor);
    }

    @Override
    public PageInfo<Doctor> page(DoctorPageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        List<Doctor> doctors = doctorDao.queryByNameAndDepartment(pageRequest);
        PageInfo<Doctor> pageInfo = new PageInfo<>(doctors);
        return pageInfo;
    }
}

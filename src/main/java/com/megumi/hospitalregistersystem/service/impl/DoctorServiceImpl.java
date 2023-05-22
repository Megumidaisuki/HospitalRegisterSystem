package com.megumi.hospitalregistersystem.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.controller.request.NewPassRequest;
import com.megumi.hospitalregistersystem.dao.DoctorDao;
import com.megumi.hospitalregistersystem.domain.ArrangementMessage;
import com.megumi.hospitalregistersystem.domain.ArrangementTemplate;
import com.megumi.hospitalregistersystem.domain.Doctor;
import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.domain.PatientMessage;
import com.megumi.hospitalregistersystem.exception.serviceException;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.service.DoctorService;
import com.megumi.hospitalregistersystem.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorDao doctorDao;
    //定义盐
    private static final String PASS_SALT = "hospital";
    //提供一个根据密码加密的方法
    private String encrypt(String password){
        return SecureUtil.md5(password+PASS_SALT);
    }
    //提供一个根据date转化为当年的第几周的方法
    private int WeekOfYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    //提供一个辅助方法将日期对象格式化为字符串（yyyy-MM-dd）
    private static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
    //提供一个方法通过年份和周数推出日期范围,返回值为起始日期
    private String rangeByWeek(int year,int weekOfYear){
        //创建一个Calendar实例，并设置为指定年份的第几周
        Calendar calendar = Calendar.getInstance();
        calendar.setWeekDate(year, weekOfYear, Calendar.MONDAY);

        //获取该周的起始日期
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String startDate = formatDate(calendar.getTime());
        return startDate;
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

    @Override
    public List<ArrangementMessage> showSchedule(Doctor doctor) {
        List<ArrangementMessage> messageList = doctorDao.getByName(doctor);
        return messageList;
    }

    @Override
    public List<ArrangementTemplate> showTemplate() {
        List<ArrangementTemplate> allTemplate = doctorDao.getAllTemplate();
        return allTemplate;
    }

    @Override
    public void newTemplate(ArrangementTemplate arrangementType) {
        doctorDao.newTemplate(arrangementType);
    }

    @Override
    public void updateSchedule(ArrangementMessage arrangementMessage) {
        doctorDao.updateSchedule(arrangementMessage);
    }

    @Override
    public void updateTemplate(ArrangementTemplate arrangementType) {
        doctorDao.updateTemplate(arrangementType);
    }

    @Override
    public void deleteSchedule(ArrangementMessage arrangementMessage) {
        doctorDao.deleteSchedule(arrangementMessage);
    }

    @Override
    public void deleteTemplate(ArrangementTemplate arrangementTemplate) {
        doctorDao.deleteTemplate(arrangementTemplate);
    }

    @Override
    public void setMessageByTemplate(ArrangementTemplate arrangementTemplate, Doctor doctor)  {
        ArrangementMessage arrangementMessage = new ArrangementMessage();
        BeanUtils.copyProperties(arrangementTemplate,arrangementMessage);
        //设置时间为当天(该写法可能有问题)
        arrangementMessage.setDate(formatDate(new Date()));
        //设置医生名
        arrangementMessage.setDoctorName(doctor.getName());
        //将该模板添加到排班信息
        doctorDao.setMessage(arrangementMessage);
    }
    //提供一个根据日期获得排班信息的方法

    @Override
    public List<ArrangementMessage> getMessageByDate(String date) {
        return doctorDao.getMessageByDate(date);
    }

    @Override
    public void copyByWeek(int year, int week, int targetWeek) {
        //创建一个Calendar实例，并设置为指定年份的第几周
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setWeekDate(year, week, Calendar.MONDAY);
        calendar2.setWeekDate(year, targetWeek, Calendar.MONDAY);
        //实现周复制(该实现方式繁琐，理论上有很大的优化空间)
        for(int i=0;i<7;i++) {
            calendar1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY+i);
            calendar2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY+i);
            String startDate = formatDate(calendar1.getTime());
            String targetDate = formatDate(calendar2.getTime());
            copyByDay(startDate, targetDate);
        }
    }

    @Override
    public List<PatientMessage> getPatientMessage(Doctor doctor) {
        List<PatientMessage> messageList = doctorDao.getPatientMessage(doctor);
        return messageList;
    }

    @Override
    public void updatePatientMessage(Doctor doctor) {
        doctorDao.updatePatientMessage(doctor);
    }

    @Override
    public void newPass(NewPassRequest newPassRequest) {
        //先对新的密码加密
        newPassRequest.setNewPass(encrypt(newPassRequest.getNewPass()));
        doctorDao.updatePassword(newPassRequest);
    }

    @Override
    public void copyByDay(String date, String targetDate) {
        List<ArrangementMessage> messageList = getMessageByDate(date);
        for(ArrangementMessage message:messageList) {
            doctorDao.setMessage(message);
        }
    }
}

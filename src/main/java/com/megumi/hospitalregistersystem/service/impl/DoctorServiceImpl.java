package com.megumi.hospitalregistersystem.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.controller.request.NewPassRequest;
import com.megumi.hospitalregistersystem.dao.DoctorDao;
import com.megumi.hospitalregistersystem.domain.*;
import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.exception.serviceException;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.service.DoctorService;
import com.megumi.hospitalregistersystem.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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

    //登录
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



    //通过用户名得到doctor对象
    @Override
    public Doctor getByUsername(String username) {
        return doctorDao.getByUsername(username);
    }

    //注册
    @Override
    public void save(Doctor doctor) {
        //加密
        doctor.setPassword(encrypt(doctor.getPassword()));
        doctorDao.save(doctor);
    }

    //更新医生信息
    @Override
    public void update(Doctor doctor) {
        doctorDao.update(doctor);
    }

//    //医生信息的分页模糊查询
//    @Override
//    public PageInfo<Doctor> page(DoctorPageRequest pageRequest) {
//        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
//        List<Doctor> doctors = doctorDao.queryByNameAndDepartment(pageRequest);
//        PageInfo<Doctor> pageInfo = new PageInfo<>(doctors);
//        return pageInfo;
//    }

    //展现医生的排班表
    @Override
    public List<ArrangementMessage> showSchedule(Doctor doctor) {
        List<ArrangementMessage> messageList = doctorDao.getByName(doctor);
        return messageList;
    }

    //展现所有的排班模板
    @Override
    public List<ArrangementTemplate> showTemplate() {
        List<ArrangementTemplate> allTemplate = doctorDao.getAllTemplate();
        return allTemplate;
    }

    //创建新的排班模板
    @Override
    public void newTemplate(String doctorName, String typeName, String registerAmounts, String timeScope) {
        String typeId = doctorDao.getTypeIdByName(doctorName, typeName, registerAmounts, timeScope);
        doctorDao.newTemplate(doctorName,typeId,registerAmounts,timeScope);
    }

    @Override
    public List<RegisterMessage> getSpecialRegisterMessage() {
        Doctor doctor = TokenUtils.getCurrentAdmin();
        String doctorName = doctor.getName();
        return doctorDao.getSpecialRegisterMessage(doctorName);
    }


    //更新排班信息表
    @Override
    public void updateSchedule(ArrangementMessage arrangementMessage) {
        doctorDao.updateSchedule(arrangementMessage);
        //在更新排班信息表的同时也要更新register_type中的信息
        doctorDao.updateRegisterTypeByArrangement(arrangementMessage);
    }

    //更新排班模板
    @Override
    public void updateTemplate(ArrangementTemplate arrangementType) {
        doctorDao.updateTemplate(arrangementType);
    }

    //删除排班信息
    @Override
    public void deleteSchedule(ArrangementMessage arrangementMessage) {
        doctorDao.deleteSchedule(arrangementMessage);
        //删除排班信息表的同时也要删除register_type中的信息
        doctorDao.deleteRegisterTypeByArrangement(arrangementMessage);

    }

    //删除排班模板
    @Override
    public void deleteTemplate(ArrangementTemplate arrangementTemplate) {
        doctorDao.deleteTemplate(arrangementTemplate);
    }

    //将排班模板应用到排班信息
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
        doctorDao.newRegisterTypeByArrangement(arrangementMessage);
    }

    //提供一个根据日期获得排班信息的方法
    @Override
    public List<ArrangementMessage> getMessageByDate(String date) {
        return doctorDao.getMessageByDate(date);
    }

    //根据周复制排班信息
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

    //得到已挂号患者的信息
    @Override
    public List<PatientMessage> getPatientMessage() {
        Doctor doctor = TokenUtils.getCurrentAdmin();
        List<PatientMessage> messageList = doctorDao.getPatientMessage(doctor);
        return messageList;
    }


    @Override
    public void updatePatientMessage(PatientMessage patientMessage) {
        doctorDao.updatePatientMessage(patientMessage);
    }

    //得到该医生负责的历史挂号信息
    @Override
    public List<RegisterMessage> getHistory() {
        Doctor doctor = TokenUtils.getCurrentAdmin();
        List<RegisterMessage> messages = doctorDao.getRegisterMessageByName(doctor);
        return messages;
    }

    //修改密码
    @Override
    public void newPass(NewPassRequest newPassRequest) {
        //先对新的密码加密
        newPassRequest.setNewPass(encrypt(newPassRequest.getNewPass()));
        doctorDao.updatePassword(newPassRequest);
    }


    //通过排班更新挂号类型
    @Override
    public void updateRegisterTypeByArrangement(ArrangementMessage arrangementMessage) {
        doctorDao.updateRegisterTypeByArrangement(arrangementMessage);
    }

    //将挂号信息表从“已预约”改为“已付款”
    @Override
    public void updateStatus(RegisterMessage registerMessage) {
        doctorDao.updateStatus(registerMessage);
    }

    //分页查询医生信息的初始化————查询所有医生信息
    @Override
    public List<Doctor> findAll() {
        return doctorDao.findAll();
    }

    @Override
    public List<TypeMessage> getTypeMessage() {
        Doctor doctor = TokenUtils.getCurrentAdmin();
        List<TypeMessage> typeMessage = doctorDao.getTypeMessage(doctor);
        return typeMessage;
    }

    @Override
    public void addTypeMessage(TypeMessage typeMessage) {
        doctorDao.addTypeMessage(typeMessage);
    }

    @Override
    public void deleteTypeMessage(Integer id) {
        doctorDao.deleteTypeMessage(id);
    }

    @Override
    public Doctor getDoctor() {
        Doctor doctor = TokenUtils.getCurrentAdmin();
        return doctor;
    }

    @Override
    public List<RegisterType> getAllRegisterType() {
        Doctor doctor = TokenUtils.getCurrentAdmin();
        return doctorDao.getAllRegisterType(doctor);
    }

    @Override
    public void addRegisterType(String name,String date,String registerAmounts,String typeName,String timeScope) {
        Doctor doctor = TokenUtils.getCurrentAdmin();
        //将registerType中的挂号匹配到type_id
        Integer typeId =  doctorDao.getTypeIdByTypeName(typeName);
        doctorDao.addRegisterType(name,date,registerAmounts,typeId,timeScope);
    }

    @Override
    public void deleteRegisterType(Integer id) {
        doctorDao.deleteRegisterType(id);
    }

    @Override
    public List<RegisterType> getRegisterTypeByDate(String date) {
        return doctorDao.getRegisterTypeByDate(date);
    }

    @Override
    public List<RegisterType> getFirstStatusRegisterType() {
        return doctorDao.getFirstStatusRegisterType();
    }

    @Override
    public List<RegisterType> getSecondStatusRegisterType() {
        return doctorDao.getSecondStatusRegisterType();
    }

    @Override
    public List<RegisterType> getThirdStatusRegisterType() {
        return doctorDao.getThirdStatusRegisterType();
    }

    @Override
    public List<RegisterMessage> getFullRegisterMessage() {
        Doctor doctor = TokenUtils.getCurrentAdmin();
        return doctorDao.getFullRegisterMessage(doctor);
    }

    @Override
    public List<RegisterMessage> getHistoryRegisterMessage(String patientName) {
        Doctor doctor = TokenUtils.getCurrentAdmin();
        String doctorName = doctor.getName();
        return doctorDao.getHistoryRegisterMessage(doctorName,patientName);
    }

    @Override
    public void logout() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        token=null;
    }

    @Override
    public List<RegisterMessage> getRegisterMessageOne() {
        Doctor doctor = TokenUtils.getCurrentAdmin();
        return doctorDao.getRegisterMessageOne(doctor);
    }

    @Override
    public List<RegisterMessage> getRegisterMessageTwo() {
        Doctor doctor = TokenUtils.getCurrentAdmin();
        return doctorDao.getRegisterMessageTwo(doctor);
    }

    @Override
    public void updateRegisterMessage(RegisterMessage registerMessage) {
        doctorDao.updateRegisterMessage(registerMessage);
    }

    @Override
    public void deleteRegisterMessage(Integer id) {
        doctorDao.deleteRegisterMessage(id);
    }




    //按天复制排班信息
    @Override
    public void copyByDay(String date, String targetDate) {
        List<ArrangementMessage> messageList = getMessageByDate(date);
        for(ArrangementMessage message:messageList) {
            doctorDao.setMessage(message);
            //添加到挂号类型表
            doctorDao.newRegisterTypeByArrangement(message);

        }
    }
}

package com.megumi.hospitalregistersystem.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageInfo;
import com.megumi.hospitalregistersystem.controller.dto.DoctorPageDTO;
import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.controller.request.NewPassRequest;
import com.megumi.hospitalregistersystem.controller.request.RegisterTypePageRequest;
import com.megumi.hospitalregistersystem.dao.PatientDao;
import com.megumi.hospitalregistersystem.domain.*;
import com.megumi.hospitalregistersystem.exception.serviceException;
import com.megumi.hospitalregistersystem.service.PatientService;
import com.megumi.hospitalregistersystem.utils.PTokenUtils;
import com.megumi.hospitalregistersystem.utils.SnowflakeIdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientDao patientDao;
    //定义盐
    private static final String PASS_SALT = "hospital";

    private String encrypt(String password) {
        return SecureUtil.md5(password + PASS_SALT);
    }

    //登录
    @Override
    public LoginDTO login(LoginRequest loginRequest) {
        //密码加密
        loginRequest.setPassword(encrypt(loginRequest.getPassword()));
        //初步判断
        Patient login = getByUsername(loginRequest.getUsername());
        if (login == null) {
            throw new serviceException("401", "用户名密码错误");
        } else if (!login.getPassword().equals(loginRequest.getPassword())) {
            throw new serviceException("401", "用户名或密码不匹配");
        }
        Patient patient = patientDao.login(loginRequest);
        LoginDTO loginDTO = new LoginDTO();
        //类类型转换
        BeanUtils.copyProperties(patient, loginDTO);
        //生成token
        String token = PTokenUtils.genToken(String.valueOf(login.getId()), login.getPassword());
        loginDTO.setToken(token);
        return loginDTO;

    }

    //通过用户名得到患者信息（此接口为login方法的辅助接口）
    public Patient getByUsername(String username) {
        return patientDao.getByUsername(username);
    }


    //注册
    @Override
    public void save(Patient patient) {
        //加密
        patient.setPassword(encrypt(patient.getPassword()));

        patientDao.save(patient);
    }

//    //医生信息的分页模糊查询
//    @Override
//    public PageInfo<DoctorPageDTO> pageDoctor(DoctorPageRequest pageRequest) {
//        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
//        //将Doctor对象转化为DoctorPageDTO返回
//        List<Doctor> doctors = patientDao.queryByNameAndDepartment(pageRequest);
//        List<DoctorPageDTO> pageDTOS = new ArrayList<>();
//        for (Doctor doctor : doctors) {
//            DoctorPageDTO pageDTO = new DoctorPageDTO();
//            BeanUtils.copyProperties(doctors,pageDTO);
//            pageDTOS.add(pageDTO);
//        }
//        PageInfo<DoctorPageDTO> pageInfo = new PageInfo<>(pageDTOS);
//        return pageInfo;
//    }

//    //通过部门获得挂号信息
//    @Override
//    public List<RegisterType> getByDepartment(String department) {
//        List<RegisterType> registerTypeS = patientDao.getByDepartment(department);
//        if(registerTypeS.isEmpty()){
//            throw new serviceException("401","该科室不存在");
//        }
//        return registerTypeS;
//    }

//    //通过医生和部门模糊分页查询挂号类型
//    @Override
//    public PageInfo<RegisterType> pageRegister(RegisterTypePageRequest pageRequest) {
//        PageHelper.startPage(pageRequest.getCurrentPage(), pageRequest.getPageSize());
//        List<RegisterType> registerTypeS = patientDao.getByNameAndDepartment(pageRequest);
//        PageInfo<RegisterType> pageInfo = new PageInfo<>(registerTypeS);
//        return pageInfo;
//    }

    //得到历史挂号信息
    @Override
    public List<RegisterMessage> getAllRegisterMessage() {
        Patient patient = PTokenUtils.getCurrentAdmin();
        List<RegisterMessage> messages = patientDao.getRegisterMessageByName(patient);
        return messages;
    }

//    //无效接口
//    @Override
//    public void updateStatus(RegisterMessage registerMessage) {
//        patientDao.updateStatus(registerMessage);
//    }

    //修改密码
    @Override
    public void newPass(NewPassRequest newPassRequest) {
        //先对新的密码加密
        newPassRequest.setNewPass(encrypt(newPassRequest.getNewPass()));
        patientDao.updatePassword(newPassRequest);
    }

    //挂号
    @Override
    public RegisterMessage register(Integer id) {
        RegisterType registerType = patientDao.getRegisterTypeById(id);
        Patient patient = PTokenUtils.getCurrentAdmin();
        //通过register_type得到type_message
        TypeMessage typeMessage = patientDao.getTypeMessageByRegisterType(registerType);
        //建立一个雪花算法
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(7);
        long orderId = snowflakeIdWorker.nextId();
        //得到小信息
        Integer typeId = registerType.getTypeId();
        Integer cost = typeMessage.getCost();
        String patientName = patient.getName();
        String doctorName = registerType.getDoctorName();
        String timeScope = registerType.getTimeScope();
        String department = typeMessage.getDepartment();

        //在register_message中添加信息
        patientDao.register(typeId,cost,patientName,doctorName,orderId,timeScope,department);
        //提供一个返回刚刚录入的信息的方法
        RegisterMessage returnMessage = patientDao.getRegisterMessage(typeId,cost,patientName,doctorName,orderId);
        //在patient_message中添加信息
        String name = patient.getName();
        Integer age = patient.getAge();
        Integer gender = patient.getGender();
        Long phone = patient.getPhone();
        Integer discreditTimes = patient.getDiscreditTimes();
        patientDao.newPatientMessage(doctorName,name,age,gender,phone,discreditTimes);
        //将arrangement_message的“可挂号数量”减一
        patientDao.updateArrangementMessage(registerType);
        //将register_type的“可挂号数量”减一
        patientDao.updateRegisterType(registerType);
        //开始倒计时，60min内订单状态若未从0变成1则使失约次数加一
        //创建一个Timer计时器类
        Patient finalPatient = patient;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                RegisterMessage message = patientDao.getAccurateRegisterMessage(registerType, finalPatient);
                if (message.getOrderStatus() == 0) {
                    patientDao.updatePatientMessage(finalPatient.getName(), registerType.getDoctorName(), typeMessage.getCost());
                }
                cancel();
            }
        }, 60 * 1000 * 60);

        return returnMessage;
    }

//    //将患者信息的失约次数加一(唯一确定订单信息)
//    @Override
//    public void updatePatientMessage(String patientName, String doctorName, Integer cost) {
//        patientDao.updatePatientMessage(patientName, doctorName, cost);
//    }

    //得到所有的医生信息
    @Override
    public List<Doctor> findAll() {

        List<Doctor> doctors = patientDao.findAll();
        //将Doctor对象转化为DoctorPageDTO返回
//        List<DoctorPageDTO> pageDTOS = new ArrayList<>();
//        for (Doctor doctor : doctors) {
//            DoctorPageDTO pageDTO = new DoctorPageDTO();
//            BeanUtils.copyProperties(doctor, pageDTO);
//            pageDTOS.add(pageDTO);
//        }
        return doctors;
    }
//    //通过科室查询所有存在属于该科室挂号类型的医生名称
//    @Override
//    public List<String> getDocotrNameByDepartment(String department) {
//        List<String> doctorNameS = patientDao.getDoctorByDepartment(department);
//        return doctorNameS;
//    }

//    @Override
//    public List<RegisterType> getByDepartmentAndName(String department, String doctorName) {
//        return patientDao.getByDepartmentAndName(department, doctorName);
//    }

    @Override
    public Patient getById(Integer id) {

        return patientDao.getById(id);
    }

    @Override
    public void pay(Integer id) {
        patientDao.pay(id);
    }

    @Override
    public Doctor getDoctorById(Integer id) {
        return patientDao.getDoctorById(id);
    }

    @Override
    public List<String> getDepartment() {
        return null;
    }

    @Override
    public PageInfo<DoctorPageDTO> pageDoctor(DoctorPageRequest pageRequest) {
        return null;
    }

    @Override
    public List<RegisterType> getByDepartment(String department) {
        return null;
    }

    @Override
    public PageInfo<RegisterType> pageRegister(RegisterTypePageRequest pageRequest) {
        return null;
    }

    @Override
    public void updateStatus(RegisterMessage registerMessage) {

    }

    @Override
    public void updatePatientMessage(String patientName, String doctorName, Integer cost) {

    }

    @Override
    public List<String> getDocotrNameByDepartment(String department) {
        return null;
    }

    @Override
    public List<RegisterType> getByDepartmentAndName(String department, String doctorName) {
        return null;
    }

    @Override
    public List<RegisterType> darkRegister(String doctorName, String department) {
        return null;
    }

    @Override
    public void logout() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        token=null;
    }

//    @Override
//    public List<String> getDepartment() {
//        List<String> department = patientDao.getDepartment();
//        return department;
//    }

//    @Override
//    public List<RegisterType> darkRegister(String doctorName, String department) {
//        return patientDao.darkRegister(doctorName, department);
//    }

    @Override
    public List<RegisterMessage> getHistoryRegisterMessage() {
        Patient patient = PTokenUtils.getCurrentAdmin();
        return patientDao.getHistoryRegister(patient);
    }

    @Override
    public List<String> getDepartmentByDateAndTimeScope(String date, String timeScope) {
        return patientDao.getDepartmentByDateAndTimeScope(date, timeScope);
    }

    @Override
    public List<RegisterType> getAllRegisterType() {
        return patientDao.getAllRegisterType();
    }

    @Override
    public List<String> getNameByDateAndTimeScopeAndDepartment(String department, String date, String timeScope) {
        return patientDao.getNameByDateAndTimeScopeAndDepartment(department, date,timeScope);
    }

    @Override
    public RegisterMessage getMessageByRegisterType(RegisterType registerType) {
        //挂号
        RegisterMessage message = register(registerType.getId());
        return message;

    }

    @Override
    public RegisterType getTypeByDateAndTimeScopeAndDepartmentAndName(String name, String department, String date, String timeScope) {
        return patientDao.getTypeByDateAndTimeScopeAndDepartmentAndName(name, department, date, timeScope);
    }

    @Override
    public List<RegisterType> getMessageByDate(String date) {
        return patientDao.getMessageByDate(date);
    }

    @Override
    public List<RegisterType> getMessageByDateAndTimeScope(String date, String timeScope) {
        return patientDao.getMessageByDateAndTimeScope(date, timeScope);
    }

    @Override
    public List<RegisterType> getMessageByDateAndTimeScopeAndDepartment(String department, String name, String date, String timeScope) {
        return patientDao.getMessageByDateAndTimeScopeAndDepartmentAndName(department,name,date,timeScope);
    }

    @Override
    public void deleteHistoryRegisterMessage(Integer id) {
        patientDao.deleteHistoryRegisterMessage(id);
    }
}


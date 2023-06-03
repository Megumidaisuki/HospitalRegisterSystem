package com.megumi.hospitalregistersystem.controller;

import com.megumi.hospitalregistersystem.common.Result;
import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.controller.request.NewPassRequest;
import com.megumi.hospitalregistersystem.domain.Doctor;
import com.megumi.hospitalregistersystem.domain.Patient;
import com.megumi.hospitalregistersystem.domain.RegisterType;
import com.megumi.hospitalregistersystem.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        LoginDTO loginDTO = patientService.login(loginRequest);
        return Result.success(loginDTO);
    }

    //注册
    @PutMapping("/save")
    public Result save(@RequestBody Patient patient) {
        patientService.save(patient);
        return Result.success();
    }

    //退出登录
    @PostMapping("/logout")
    public Result logout(){
        patientService.logout();
        return Result.success();
    }
    //查看所有医生信息
    @GetMapping("/findAll")
    public Result allDoctor() {
        List<Doctor> doctors = patientService.findAll();
        return Result.success(doctors);
    }

    //根据id查询医生信息
    @GetMapping("/getDoctorById")
    public Result getById(Integer id) {
        return Result.success(patientService.getDoctorById(id));
    }


    //得到所有的挂号类型
    @GetMapping("/getAllRegisterType")
    public Result getAllRegisterType() {
        List<RegisterType> registerType = patientService.getAllRegisterType();
        return Result.success(registerType);
    }

    //根据日期和时间得到所有部门
    @GetMapping("/getDepartmentByDateAndTimeScope")
    public Result getDepartmentByDateAndTimeScope(String date, String timeScope) {
        return Result.success(patientService.getDepartmentByDateAndTimeScope(date, timeScope));
    }

    //通过部门和日期和时间得到所有医生名
    @GetMapping("/getNameByDateAndTimeScopeAndDepartment")
    public Result getNameByDateAndTimeScopeAndDepartment(String department, String date, String timeScope) {
        return Result.success(patientService.getNameByDateAndTimeScopeAndDepartment(department, date, timeScope));
    }

    //通过部门和日期和时间和医生名得到所有的挂号信息(该接口还要挂号)
    @GetMapping("/getMessageByDateAndTimeScopeAndDepartmentAndNameDark")
    public Result getMessageByDateAndTimeScopeAndDepartmentAndNameDark(String name, String department, String date, String timeScope) {
        //根据上述四个参数确定唯一挂号类型
        RegisterType registerType = patientService.getTypeByDateAndTimeScopeAndDepartmentAndName(name, department, date, timeScope);
        return Result.success(patientService.getMessageByRegisterType(registerType));
    }

    //根据日期得到所有挂号信息
    @GetMapping("/getMesaageByDate")
    public Result getMessageByDate(String date) {
        return Result.success(patientService.getMessageByDate(date));
    }

    //根据日期和时间得到所有挂号信息
    @GetMapping("/getMessageByDateAndTimeScope")
    public Result getMessageByDateAndTimeScope(String date, String timeScope) {
        return Result.success(patientService.getMessageByDateAndTimeScope(date, timeScope));
    }

    //根据日期和时间和(部门和医生名模糊查询)  得到挂号信息
    @GetMapping("/getMessageByDateAndTimeScopeAndDepartmentAndName")
    public Result getMessageByDateAndTimeScopeAndDepartment(String department, String name, String date, String timeScope) {
        return Result.success(patientService.getMessageByDateAndTimeScopeAndDepartment(department, name, date, timeScope));
    }

    //删除历史挂号信息
    @DeleteMapping("/deleteHistoryRegisterMessage")
    public Result deleteHistoryRegisterMessage(Integer id) {
        patientService.deleteHistoryRegisterMessage(id);
        return Result.success();
    }

    //查看该用户所有所有信息
    @GetMapping("/getAllRegisterMessage")
    public Result getAllRegisterMessage() {
        return Result.success(patientService.getAllRegisterMessage());
    }

    //查看历史挂号信息
    @GetMapping("/getHistoryRegisterMessage")
    public Result getHistoryRegisterMessage() {
        return Result.success(patientService.getHistoryRegisterMessage());
    }

    //挂号接口，挂号后应该将患者信息增加到PatientMessage表中
    @PutMapping("/register")
    public Result register(Integer id) {

        return Result.success(patientService.register(id));
    }

    //修改密码
    @PutMapping("/newPass")
    public Result newPass(@RequestBody NewPassRequest newPassRequest) {
        patientService.newPass(newPassRequest);
        return Result.success();
    }

    //付款
    @PutMapping("/pay")
    public Result pay(Integer id) {
        patientService.pay(id);
        return Result.success();
    }


    //    //分页查看医生信息的接口
//    @GetMapping("/pageDoctor")
//    public Result pageDoctor(@RequestBody DoctorPageRequest pageRequest) {
//        PageInfo<DoctorPageDTO> doctorPageDTO = patientService.pageDoctor(pageRequest);
//        return Result.success(doctorPageDTO);
//    }

//    //模糊查看可以选择的挂号类型(1)
//    @GetMapping("/darkRegister")
//    public Result darkRegister(String doctorName,String department) {
//        return Result.success(patientService.darkRegister(doctorName,department));
//    }

//    //分页模糊查看可以选择的挂号类型
//    @GetMapping("/pageRegister")
//    public Result pageRegister(@RequestBody RegisterTypePageRequest pageRequest) {
//        PageInfo<RegisterType> registerType = patientService.pageRegister(pageRequest);
//        return Result.success(registerType);
//    }

//    //提供一个根据科室查看挂号类型的接口
//    @GetMapping("/department")
//    public Result getByDepartment(String department) {
//        return Result.success(patientService.getByDepartment(department));
//    }

//    //得到所有科室
//    @GetMapping("/getDepartment")
//    public Result getDepartment() {
//        return Result.success(patientService.getDepartment());
//    }

//    //通过科室查询所有存在属于该科室挂号类型的医生名称
//
//    @GetMapping("/getDocotrNameByDepartment")
//    public Result getDocotrNameByDepartment(String department) {
//        return Result.success(patientService.getDocotrNameByDepartment(department));
//    }

//    //根据科室和医生名查询所有挂号信息
//    @GetMapping("/getByDepartmentAndName")
//    public Result getByDepartmentAndName(String department,String doctorName) {
//        return Result.success(patientService.getByDepartmentAndName(department,doctorName));
//    }
//    //提供一个修改订单信息的接口(将status字段从“已预约0”改成“已付款1”)-----------------此接口无效，该操作应该由医生完成，相应的接口已在医生controller层补充
//    @PutMapping("/updateStatus")
//    public Result updateStatus(RegisterMessage registerMessage) {
//        patientService.updateStatus(registerMessage);
//        return Result.success();
//    }


}

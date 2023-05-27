package com.megumi.hospitalregistersystem.controller;

import com.github.pagehelper.PageInfo;
import com.megumi.hospitalregistersystem.common.Result;
import com.megumi.hospitalregistersystem.controller.dto.DoctorPageDTO;
import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.controller.request.NewPassRequest;
import com.megumi.hospitalregistersystem.controller.request.RegisterTypePageRequest;
import com.megumi.hospitalregistersystem.domain.Patient;
import com.megumi.hospitalregistersystem.domain.RegisterMessage;
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

    //分页查看医生信息的接口
    @GetMapping("/pageDoctor")
    public Result pageDoctor(@RequestBody DoctorPageRequest pageRequest) {
        PageInfo<DoctorPageDTO> doctorPageDTO = patientService.pageDoctor(pageRequest);
        return Result.success(doctorPageDTO);
    }

    //查看所有医生信息
    @GetMapping("/findAll")
    public Result allDoctor() {
        List<DoctorPageDTO> doctorPageDTO = patientService.findAll();
        return Result.success(doctorPageDTO);
    }
    //分页查看可以选择的挂号类型
    @GetMapping("/pageRegister")
    public Result pageRegister(@RequestBody RegisterTypePageRequest pageRequest) {
        PageInfo<RegisterType> registerType = patientService.pageRegister(pageRequest);
        return Result.success(registerType);
    }

    //提供一个根据科室查看挂号类型的接口
    @GetMapping("/department")
    public Result getByDepartment(@RequestBody String department) {
        return Result.success(patientService.getByDepartment(department));
    }

    //提供一个查看历史挂号信息和订单状态（已预约，已付款）的接口
    @GetMapping("/history")
    public Result getHistory(Patient patient) {
        return Result.success(patientService.getHistory(patient));
    }

    //提供一个修改订单信息的接口(将status字段从“已预约0”改成“已付款1”)-----------------此接口无效，该操作应该由医生完成，相应的接口已在医生controller层补充
    @PutMapping("/updateStatus")
    public Result updateStatus(RegisterMessage registerMessage) {
        patientService.updateStatus(registerMessage);
        return Result.success();
    }
    //挂号接口，挂号后应该将患者信息增加到PatientMessage表中
    @PutMapping("/register")
    public Result register(RegisterType registerType,Patient patient) {
        patientService.register(registerType,patient);
        return Result.success();
    }

    //修改密码
    @PutMapping("/newPass")
    public Result newPass(@RequestBody NewPassRequest newPassRequest) {
        patientService.newPass(newPassRequest);
        return Result.success();
    }
    //

}

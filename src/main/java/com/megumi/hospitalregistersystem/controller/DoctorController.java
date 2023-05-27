package com.megumi.hospitalregistersystem.controller;

import com.megumi.hospitalregistersystem.common.Result;
import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.controller.request.NewPassRequest;
import com.megumi.hospitalregistersystem.domain.*;
import com.megumi.hospitalregistersystem.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@CrossOrigin
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    //根据id查医生信息
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.success(doctorService.getById(id));
    }
    //医生信息的分页查询
    @GetMapping("/page")
    public Result page(DoctorPageRequest pageRequest){
        return Result.success(doctorService.page(pageRequest));
    }

    //分页查询页面初始化，查询所有医生信息
    @GetMapping("/findAll")
    public Result findAll(){
        return Result.success(doctorService.findAll());
    }

    //注册
    @PutMapping("/save")
    public Result save(@RequestBody Doctor doctor){
        doctorService.save(doctor);
        return Result.success();
    }
    //修改医生信息
    @PutMapping("/update")
    public Result update(@RequestBody Doctor doctor){
        doctorService.update(doctor);
        return Result.success();
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        LoginDTO loginDTO = doctorService.login(loginRequest);
        return Result.success(loginDTO);
    }

    //展现医生的排班信息(通过医生的名字)
    @GetMapping("/showSchedule")
    public Result showSchedule(Doctor doctor){
        return Result.success(doctorService.showSchedule(doctor));
    }

    //展现排班模板信息(通用，此信息并不属于特一医生)
    @GetMapping("/showTemplate")
    public Result showSchedule(){
        return Result.success(doctorService.showTemplate());
    }

    //医生新建排班模板
    @PutMapping("/newTemplate")
    public Result newTemplate(@RequestBody ArrangementTemplate arrangementType){
        doctorService.newTemplate(arrangementType);
        return Result.success();
    }
    //修改排班信息,同时更新挂号类型表
    @PutMapping("/updateSchedule")
        public Result updateSchedule(@RequestBody ArrangementMessage arrangementMessage){
        doctorService.updateSchedule(arrangementMessage);
        return Result.success();
    }
    //删除排班信息
    @DeleteMapping("/deleteSchedule")
    public Result deleteSchedule(ArrangementMessage arrangementMessage){
        doctorService.deleteSchedule(arrangementMessage);
        return Result.success();
    }
    //修改排班模板
    @PutMapping("/updateTemplate")
    public Result updateTemplate(@RequestBody ArrangementTemplate arrangementType){
        doctorService.updateTemplate(arrangementType);
        return Result.success();
    }
    //删除排班模板
    @DeleteMapping("/deleteTemplate")
    public Result deleteTemplate(ArrangementTemplate arrangementTemplate){
        doctorService.deleteTemplate(arrangementTemplate);
        return Result.success();
    }
    //选择排班模板，将当日排班设置为模板排班时间，同时插入新的挂号信息数据
    @PutMapping("/setMessageByTemplate")
    public Result setMessageByTemplate(ArrangementTemplate arrangementTemplate,Doctor doctor) {
        doctorService.setMessageByTemplate(arrangementTemplate,doctor);
        return Result.success();
    }
    //将排班信息按日复制
    @PutMapping("/copyByDay")
    public Result copyByDay(String date,String targetDate){
        doctorService.copyByDay(date,targetDate);
        return Result.success();
    }
    //将排班信息按周复制
    @PutMapping("/copyByWeek")
    public Result copyByWeek(int year,int week,int targetWeek){
        doctorService.copyByWeek(year,week,targetWeek);
        return Result.success();
    }

    //查看患者信息
    @GetMapping("/getPatientMessage")
    public Result getPatientMessage(Doctor doctor){
        return Result.success(doctorService.getPatientMessage(doctor));
    }

    //修改患者信息(失约次数加一)
    @PutMapping("/updatePatientMessage")
    public Result updatePatientMessage(@RequestBody Patient patient){
        doctorService.updatePatientMessage(patient);
        return Result.success();
    }

    //修改密码
    @PutMapping("/newPass")
    public Result newPass(@RequestBody NewPassRequest newPassRequest){
        doctorService.newPass(newPassRequest);
        return Result.success();
    }
    //提供一个查看历史挂号信息和订单状态（已预约，已付款）的接口
    @GetMapping("/history")
    public Result getHistory(Doctor doctor) {
        return Result.success(doctorService.getHistory(doctor));
    }
    //提供一个修改订单信息的接口(将status字段从“已预约0”改成“已付款1”)
    @PutMapping("/updateStatus")
    public Result updateStatus(RegisterMessage registerMessage) {
        doctorService.updateStatus(registerMessage);
        return Result.success();
    }
}

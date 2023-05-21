package com.megumi.hospitalregistersystem.controller;

import com.megumi.hospitalregistersystem.common.Result;
import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.domain.ArrangementMessage;
import com.megumi.hospitalregistersystem.domain.ArrangementTemplate;
import com.megumi.hospitalregistersystem.domain.Doctor;
import com.megumi.hospitalregistersystem.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
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
    public Result page(@RequestBody DoctorPageRequest pageRequest){
        return Result.success(doctorService.page(pageRequest));
    }
    //注册
    @PutMapping("/save")
    public Result save(@RequestBody Doctor doctor){
        doctorService.save(doctor);
        return Result.success();
    }
    //修改医生信息
    @PutMapping("/update")
    public Result update(Doctor doctor){
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
    public Result newTemplate(ArrangementTemplate arrangementType){
        doctorService.newTemplate(arrangementType);
        return Result.success();
    }
    //修改排班信息
    @PutMapping("/updateSchedule")
        public Result updateSchedule(ArrangementMessage arrangementMessage){
        doctorService.updateSchedule(arrangementMessage);
        return Result.success();
    }
    //删除排班信息(将doctorName,timeScope,registerType,registerAmounts作为删除依据)
    @DeleteMapping("/deleteSchedule")
    public Result deleteSchedule(ArrangementMessage arrangementMessage){
        doctorService.deleteSchedule(arrangementMessage);
        return Result.success();
    }
    //修改排班模板
    @PutMapping("/updateTemplate")
    public Result updateTemplate(ArrangementTemplate arrangementType){
        doctorService.updateTemplate(arrangementType);
        return Result.success();
    }
    //删除排班模板(将timeScope,registerType,registerAmounts作为删除依据)
    @DeleteMapping("/deleteTemplate")
    public Result deleteTemplate(ArrangementTemplate arrangementTemplate){
        doctorService.deleteTemplate(arrangementTemplate);
        return Result.success();
    }
    //选择排班模板，将当日排班设置为模板排班时间


}

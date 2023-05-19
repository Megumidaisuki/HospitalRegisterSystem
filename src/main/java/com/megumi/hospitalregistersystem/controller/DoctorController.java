package com.megumi.hospitalregistersystem.controller;

import com.megumi.hospitalregistersystem.common.Result;
import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.controller.request.DoctorPageRequest;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.domain.Doctor;
import com.megumi.hospitalregistersystem.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.success(doctorService.getById(id));
    }
    @GetMapping("/page")
    public Result page(@RequestBody DoctorPageRequest pageRequest){
        return Result.success(doctorService.page(pageRequest));
    }
    @PutMapping("/save")
    public Result save(@RequestBody Doctor doctor){
        doctorService.save(doctor);
        return Result.success();
    }
    @PutMapping("/update")
    public Result update(@RequestBody Doctor doctor){
        doctorService.update(doctor);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        LoginDTO loginDTO = doctorService.login(loginRequest);
        return Result.success(loginDTO);
    }
}

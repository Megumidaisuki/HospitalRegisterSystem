package com.megumi.hospitalregistersystem.controller;

import com.megumi.hospitalregistersystem.common.Result;
import com.megumi.hospitalregistersystem.controller.dto.LoginDTO;
import com.megumi.hospitalregistersystem.controller.request.LoginRequest;
import com.megumi.hospitalregistersystem.domain.Doctor;
import com.megumi.hospitalregistersystem.domain.Patient;
import com.megumi.hospitalregistersystem.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        LoginDTO loginDTO = patientService.login(loginRequest);
        return Result.success(loginDTO);
    }
    @PutMapping("/save")
    public Result save(@RequestBody Patient patient){
        patientService.save(patient);
        return Result.success();
    }
}

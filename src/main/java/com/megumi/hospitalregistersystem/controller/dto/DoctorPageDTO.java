package com.megumi.hospitalregistersystem.controller.dto;

import lombok.Data;

@Data
public class DoctorPageDTO {
    private String name;
    private String title;
    private String department;
    private String remark;
    private String registerType;


}

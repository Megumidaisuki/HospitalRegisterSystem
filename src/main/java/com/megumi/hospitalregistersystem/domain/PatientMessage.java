package com.megumi.hospitalregistersystem.domain;

import lombok.Data;

@Data
public class PatientMessage {
    private Integer id;
    private String name;
    private Integer age;
    private Integer gender;
    private Integer phone;
    private String email;
    private Integer discreditTimes;
    private String doctorName;
}

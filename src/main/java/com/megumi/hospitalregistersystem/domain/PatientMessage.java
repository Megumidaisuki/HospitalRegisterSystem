package com.megumi.hospitalregistersystem.domain;

import lombok.Data;

@Data
public class PatientMessage {
    private Integer id;
    private String name;
    private Integer age;
    private Integer gender;
    private Long phone;
    private Integer discreditTimes;
    private String doctorName;
}

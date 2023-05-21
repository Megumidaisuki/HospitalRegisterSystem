package com.megumi.hospitalregistersystem.domain;

import lombok.Data;

import java.util.List;

@Data
public class Doctor {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private Integer age;
    private Integer gender;
    private String email;
    private String title;
    private String department;
    private String remark;
    private String registerType;
}

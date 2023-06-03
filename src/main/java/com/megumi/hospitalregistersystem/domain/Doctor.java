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
    private String title;
    private String department;
    private String remark;
    private Long phone;
    private String specialty;
    private String nation;
    private String nationality;
    private String education;
}

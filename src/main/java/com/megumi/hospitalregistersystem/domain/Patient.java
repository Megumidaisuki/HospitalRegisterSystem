package com.megumi.hospitalregistersystem.domain;

import lombok.Data;

@Data
public class Patient {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private Integer gender;
    private Integer age;
    private Integer phone;
    private String email;
    private Integer discredit_times;
}

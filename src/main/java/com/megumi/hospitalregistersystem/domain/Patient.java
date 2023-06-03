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
    private Long phone;
    private Integer discreditTimes;
}

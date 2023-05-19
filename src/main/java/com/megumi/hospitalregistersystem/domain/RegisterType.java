package com.megumi.hospitalregistersystem.domain;

import lombok.Data;

@Data
public class RegisterType {
    private Integer id;
    private String name;
    private Integer cost;
    private String time;
    private String doctorName;
    private String department;
}

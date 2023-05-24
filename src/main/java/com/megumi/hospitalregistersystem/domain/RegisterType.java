package com.megumi.hospitalregistersystem.domain;

import lombok.Data;

import java.util.List;

@Data
public class RegisterType  {
    private Integer id;
    private String doctorName;
    private String date;
    private String registerAmounts;
    private String registeredAmounts;
    private Integer typeId;
}

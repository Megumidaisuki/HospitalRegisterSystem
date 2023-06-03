package com.megumi.hospitalregistersystem.controller.dto;

import lombok.Data;

@Data
public class CommonLoginDTO {
    private Integer id;
    private String username;
    private String name;
    private String email;
    private String phone;
    private String token;
    private String category;
}

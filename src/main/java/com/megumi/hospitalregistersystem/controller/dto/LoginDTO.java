package com.megumi.hospitalregistersystem.controller.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private Integer id;
    private String username;
    private String name;
    private String token;
}

package com.megumi.hospitalregistersystem.controller.request;

import lombok.Data;

@Data
public class NewPassRequest {
    private String username;
    private String password;
    private String newPass;
}

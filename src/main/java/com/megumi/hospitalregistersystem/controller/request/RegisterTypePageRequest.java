package com.megumi.hospitalregistersystem.controller.request;

import lombok.Data;

@Data
public class RegisterTypePageRequest extends BaseRequest{
    private String name;
    private String department;
}

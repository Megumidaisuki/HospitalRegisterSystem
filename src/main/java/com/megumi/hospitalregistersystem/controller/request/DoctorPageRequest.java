package com.megumi.hospitalregistersystem.controller.request;

import lombok.Data;

@Data
public class DoctorPageRequest extends BaseRequest{
    private String name;
    private String department;

}

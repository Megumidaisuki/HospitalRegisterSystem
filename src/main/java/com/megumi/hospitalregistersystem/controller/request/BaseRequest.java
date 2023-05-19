package com.megumi.hospitalregistersystem.controller.request;

import lombok.Data;

@Data
public class BaseRequest {
    private Integer pageSize = 10;
    private Integer currentPage = 1;
}
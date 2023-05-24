package com.megumi.hospitalregistersystem.domain;

import lombok.Data;

@Data
public class TypeMessage {
    private Integer id;
    private String typeName;
    private Integer cost;
    private String continueTime;
    private String department;

}

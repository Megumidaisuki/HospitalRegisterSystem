package com.megumi.hospitalregistersystem.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ArrangementTemplate {
    private Integer id;
    private String doctorName;
    private String typeId;
    private String registerAmounts;
    private String timeScope;
}

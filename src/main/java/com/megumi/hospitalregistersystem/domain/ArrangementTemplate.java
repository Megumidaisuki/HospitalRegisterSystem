package com.megumi.hospitalregistersystem.domain;

import lombok.Data;

@Data
public class ArrangementTemplate {
    private Integer id;
    private String timeScope;
    private String registerType;
    private String registerAmounts;
}
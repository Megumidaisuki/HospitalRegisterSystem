package com.megumi.hospitalregistersystem.domain;

import lombok.Data;

@Data
public class ArrangementMessage {
    private Integer id;
    private String doctorName;
    private String timeScope;
    private String registerType;
    private String registerAmounts;
    private String registeredAmounts;
}

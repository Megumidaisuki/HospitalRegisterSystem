package com.megumi.hospitalregistersystem.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ArrangementMessage {
    private Integer id;
    private String doctorName;
    private String timeScope;
    private String typeId;
    private String registerAmounts;
    private String registeredAmounts;
    private String date;
}

package com.megumi.hospitalregistersystem.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class RegisterMessage {
    private Integer id;
    private String typeId;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;
    private Integer cost;
    private Integer orderStatus;
    private String patientName;
    private String doctorName;
    private Long orderId;
    private String diagnosis;
    private String symptom;
    private PatientMessage patientMessage;
    private TypeMessage typeMessage;
    private RegisterType registerType;
    private String timeScope;
    private String department;
}

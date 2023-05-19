package com.megumi.hospitalregistersystem;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication(scanBasePackages = "com.megumi.hospitalregistersystem")
public class HospitalRegisterSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalRegisterSystemApplication.class, args);
    }

}
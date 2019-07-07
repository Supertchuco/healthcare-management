package com.healthcare.healthcaremanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamDto implements Serializable {

    private String institutionCNPJ;

    private String patientName;

    private String patientCPF;

    private int patientAge;

    private String patientGender;

    private String physicianCrm;

    private String physicianName;

    private String procedure;
}

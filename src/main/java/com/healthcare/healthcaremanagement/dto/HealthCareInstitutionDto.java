package com.healthcare.healthcaremanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class HealthCareInstitutionDto implements Serializable {

    private String cnpj;

    private String name;
}

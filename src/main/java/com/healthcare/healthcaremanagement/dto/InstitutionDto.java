package com.healthcare.healthcaremanagement.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class InstitutionDto implements Serializable {

    private String cnpj;

    private String name;
}

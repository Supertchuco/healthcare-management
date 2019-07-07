package com.healthcare.healthcaremanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionDto implements Serializable {

    private String cnpj;

    private String name;
}

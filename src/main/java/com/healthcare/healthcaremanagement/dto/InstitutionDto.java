package com.healthcare.healthcaremanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionDto implements Serializable {

    @NotNull
    private String cnpj;

    @NotNull
    private String name;
}

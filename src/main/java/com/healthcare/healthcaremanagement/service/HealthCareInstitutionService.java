package com.healthcare.healthcaremanagement.service;

import com.healthcare.healthcaremanagement.dto.HealthCareInstitutionDto;
import com.healthcare.healthcaremanagement.entity.HealthCareInstitution;
import com.healthcare.healthcaremanagement.exception.CreateHealthCareInstitutionException;
import com.healthcare.healthcaremanagement.exception.HealthCareInstitutionInsufficientPixeonBalanceException;
import com.healthcare.healthcaremanagement.exception.HealthCareInstitutionNotFoundException;
import com.healthcare.healthcaremanagement.exception.InvalidCNPJException;
import com.healthcare.healthcaremanagement.repository.HealthCareInstitutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.length;

@Service
@Slf4j
public class HealthCareInstitutionService {

    @Autowired
    private HealthCareInstitutionRepository healthCareInstitutionRepository;

    public HealthCareInstitution createHealthCareInstitution(final HealthCareInstitutionDto healthCareInstitutionDto) {
        log.info("Create new health care institution on database");
        validateCNPJHealthCareInstitution(healthCareInstitutionDto.getCnpj());
        try {
            return healthCareInstitutionRepository.save(new HealthCareInstitution(healthCareInstitutionDto.getCnpj(), healthCareInstitutionDto.getName()));
        } catch (Exception exception) {
            log.error("Error to create new health care institution");
            throw new CreateHealthCareInstitutionException();
        }
    }

    public HealthCareInstitution chargePixeonValue(final int pixeonChargedValue, final HealthCareInstitution healthcareInstitution) {
        healthcareInstitution.setPixeon(healthcareInstitution.getPixeon() - pixeonChargedValue);
        if (healthcareInstitution.getPixeon() < 0) {
            log.error("Health care institution with insufficient Pixeon balance to create exam");
            throw new HealthCareInstitutionInsufficientPixeonBalanceException();
        }
        return healthCareInstitutionRepository.save(healthcareInstitution);
    }

    public HealthCareInstitution findByCNPJ(final String cnpj) {
        HealthCareInstitution healthcareInstitution = healthCareInstitutionRepository.findByCnpj(cnpj);
        if (isNull(healthcareInstitution)) {
            log.error("Health care institution with CNPJ {} not found", cnpj);
            throw new HealthCareInstitutionNotFoundException();
        }
        return healthcareInstitution;
    }

    private void validateCNPJHealthCareInstitution(String cnpj) {
        cnpj = cnpj.replaceAll("[./-]", "");
        if (length(cnpj) != 14 || !isNumeric(cnpj)) {
            log.error("Invalid CNPJ {}", cnpj);
            throw new InvalidCNPJException();
        }
    }
}

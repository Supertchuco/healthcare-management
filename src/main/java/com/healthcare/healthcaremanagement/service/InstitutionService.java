package com.healthcare.healthcaremanagement.service;

import com.healthcare.healthcaremanagement.dto.InstitutionDto;
import com.healthcare.healthcaremanagement.entity.HealthcareInstitution;
import com.healthcare.healthcaremanagement.exception.CreateInstitutionException;
import com.healthcare.healthcaremanagement.exception.InstitutionInsufficientPixeonBalanceException;
import com.healthcare.healthcaremanagement.exception.InstitutionNotFoundException;
import com.healthcare.healthcaremanagement.exception.InvalidCnpjException;
import com.healthcare.healthcaremanagement.repository.InstitutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class InstitutionService {

    @Autowired
    private InstitutionRepository institutionRepository;

    public HealthcareInstitution createInstitution(final InstitutionDto institutionDto) {
        log.info("Create new healthcareInstitution on database");
        validateCnpjInstitution(institutionDto.getCnpj());
        try {
            return institutionRepository.save(new HealthcareInstitution(institutionDto.getCnpj(), institutionDto.getName()));
        } catch (Exception exception) {
            log.error("Error to create new healthcareInstitution");
            throw new CreateInstitutionException();
        }
    }

    public HealthcareInstitution chargePixeonValue(final int pixeonChargedValue, final HealthcareInstitution healthcareInstitution){
        healthcareInstitution.setPixeon(healthcareInstitution.getPixeon() - pixeonChargedValue);
        if (healthcareInstitution.getPixeon() < 0) {
            log.error("HealthcareInstitution with insufficient Pixeon balance to create exam");
            throw new InstitutionInsufficientPixeonBalanceException();
        }
        return institutionRepository.save(healthcareInstitution);
    }

    public HealthcareInstitution findByCnpj(final String cnpj){
        HealthcareInstitution healthcareInstitution = institutionRepository.findByCnpj(cnpj);
        if (isNull(healthcareInstitution)) {
            log.error("HealthcareInstitution with cnpj {} not found", cnpj);
            throw new InstitutionNotFoundException();
        }
        return healthcareInstitution;
    }

    private void validateCnpjInstitution(final String cnpj){
        if(StringUtils.length(cnpj) != 14 || !StringUtils.isAlphanumeric(cnpj)){
            log.error("Invalid CNPJ {}", cnpj);
            throw new InvalidCnpjException();
        }
    }
}

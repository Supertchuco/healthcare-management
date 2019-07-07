package com.healthcare.healthcaremanagement.service;

import com.healthcare.healthcaremanagement.dto.InstitutionDto;
import com.healthcare.healthcaremanagement.entity.Institution;
import com.healthcare.healthcaremanagement.exception.CreateHealthCareInstitutionException;
import com.healthcare.healthcaremanagement.exception.InstitutionInsufficientPixeonBalanceException;
import com.healthcare.healthcaremanagement.exception.InstitutionNotFoundException;
import com.healthcare.healthcaremanagement.exception.InvalidCNPJException;
import com.healthcare.healthcaremanagement.repository.InstitutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.length;

@Service
@Slf4j
public class InstitutionService {

    @Autowired
    private InstitutionRepository institutionRepository;

    public Institution createInstitution(final InstitutionDto institutionDto) {
        log.info("Create new institution on database");
        validateCNPJInstitution(institutionDto.getCnpj());
        try {
            return institutionRepository.save(new Institution(institutionDto.getCnpj(), institutionDto.getName()));
        } catch (Exception exception) {
            log.error("Error to create new health care institution");
            throw new CreateHealthCareInstitutionException();
        }
    }

    public Institution chargePixeonValue(final int pixeonChargedValue, final Institution healthcareInstitution) {
        healthcareInstitution.setPixeon(healthcareInstitution.getPixeon() - pixeonChargedValue);
        if (healthcareInstitution.getPixeon() < 0) {
            log.error("Institution with insufficient Pixeon balance to create exam");
            throw new InstitutionInsufficientPixeonBalanceException();
        }
        return institutionRepository.save(healthcareInstitution);
    }

    public Institution findByCNPJ(final String cnpj) {
        Institution institution = institutionRepository.findByCnpj(cnpj);
        if (isNull(institution)) {
            log.error("Institution with CNPJ {} not found", cnpj);
            throw new InstitutionNotFoundException();
        }
        return institution;
    }

    private void validateCNPJInstitution(String cnpj) {
        cnpj = cnpj.replaceAll("[./-]", "");
        if (length(cnpj) != 14 || !isNumeric(cnpj)) {
            log.error("Invalid CNPJ {}", cnpj);
            throw new InvalidCNPJException();
        }
    }
}

package com.healthcare.healthcaremanagement.service;

import com.healthcare.healthcaremanagement.dto.InstitutionDto;
import com.healthcare.healthcaremanagement.entity.Institution;
import com.healthcare.healthcaremanagement.exception.CNPJAlreadyExistOnDatabaseException;
import com.healthcare.healthcaremanagement.exception.CreateHealthCareInstitutionException;
import com.healthcare.healthcaremanagement.exception.InstitutionInsufficientPixeonBalanceException;
import com.healthcare.healthcaremanagement.exception.InstitutionNotFoundException;
import com.healthcare.healthcaremanagement.exception.InvalidCNPJException;
import com.healthcare.healthcaremanagement.repository.InstitutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.length;

@Service
@Slf4j
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class InstitutionService {

    @Autowired
    private InstitutionRepository institutionRepository;

    public Institution createInstitution(final InstitutionDto institutionDto) {
        log.info("Create new institution on database");
        institutionDto.setCnpj(formatCNPJInstitution(institutionDto.getCnpj()));
        if (nonNull(institutionRepository.findByCnpj(institutionDto.getCnpj()))) {
            log.error("Already exist an institution with this CNPJ {} ", institutionDto.getCnpj());
            throw new CNPJAlreadyExistOnDatabaseException();
        }
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

    private String formatCNPJInstitution(final String cnpj) {
        final String cnpjFormatted = cnpj.replaceAll("[./-]", "");
        if (length(cnpj) != 14 || !isNumeric(cnpj)) {
            log.error("Invalid CNPJ {}", cnpj);
            throw new InvalidCNPJException();
        }
        return cnpjFormatted;
    }
}

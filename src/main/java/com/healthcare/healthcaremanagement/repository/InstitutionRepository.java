package com.healthcare.healthcaremanagement.repository;

import com.healthcare.healthcaremanagement.entity.HealthcareInstitution;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionRepository extends CrudRepository<HealthcareInstitution, Integer> {

    HealthcareInstitution findByCnpj(final String cnpj);
}

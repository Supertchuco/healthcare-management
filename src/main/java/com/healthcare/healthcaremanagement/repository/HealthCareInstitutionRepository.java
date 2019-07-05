package com.healthcare.healthcaremanagement.repository;

import com.healthcare.healthcaremanagement.entity.HealthCareInstitution;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthCareInstitutionRepository extends CrudRepository<HealthCareInstitution, Integer> {

    HealthCareInstitution findByCnpj(final String cnpj);
}

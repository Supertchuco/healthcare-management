package com.healthcare.healthcaremanagement.repository;

import com.healthcare.healthcaremanagement.entity.Physician;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicianRepository extends CrudRepository<Physician, Integer> {

    Physician findByCrm(String crm);
}

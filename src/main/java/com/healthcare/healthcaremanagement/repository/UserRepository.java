package com.healthcare.healthcaremanagement.repository;

import com.healthcare.healthcaremanagement.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(final String email);

    List<User> findAll();
}

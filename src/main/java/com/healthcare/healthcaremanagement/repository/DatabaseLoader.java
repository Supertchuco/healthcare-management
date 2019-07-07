package com.healthcare.healthcaremanagement.repository;

import com.healthcare.healthcaremanagement.entity.Institution;
import com.healthcare.healthcaremanagement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    private final InstitutionRepository institutionRepository;

    @Autowired
    public DatabaseLoader(UserRepository userRepo, InstitutionRepository institutionRepo) {
        this.userRepository = userRepo;
        this.institutionRepository = institutionRepo;
    }

    @Override
    public void run(String... strings) {

        Institution institution = new Institution("25690845632", "Balada Institution");
        institutionRepository.save(institution);

        User user = new User("test", "test");
        institution.setUsers(Arrays.asList(user));
        user.setInstitution(institution);

        this.userRepository.save(user);
    }

}

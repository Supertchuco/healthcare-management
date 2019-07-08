package com.healthcare.healthcaremanagement.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum to define UserRole input.
 */
@AllArgsConstructor
@Getter
public enum UserRole {

    ADMIN("ADMIN"),
    CLIENT_ADMIN("CLIENT_ADMIN");

    private String role;
}

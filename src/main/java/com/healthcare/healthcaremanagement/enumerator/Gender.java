package com.healthcare.healthcaremanagement.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum to define gender input.
 */
@AllArgsConstructor
@Getter
public enum Gender {

    MALE("MALE"),
    FEMALE("FEMALE");

    private String gender;
}

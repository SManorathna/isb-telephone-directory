package com.isb.telephonedirectory.model;

public enum Gender {
    MALE(0), FEMALE(1);

    Integer type;

    Gender(Integer type) {
        this.type = type;
    }
}

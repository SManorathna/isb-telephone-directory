package com.isb.telephonedirectory.model;

public enum ServiceType {
    PRE_PAID(0), POST_PAID(1);

    Integer type;

    ServiceType(Integer type) {
        this.type = type;
    }
}

package com.jfb.account.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AccountType {
    CHECKING("CHECKING"), // conta corrente
    SAVINGS("SAVINGS"); // poupança
    private final String value;

    AccountType(String value) {
        this.value = value;
    }

    @JsonCreator
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static AccountType fromValue(String value) {
        for (AccountType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Type inválido: " + value);
    }
}

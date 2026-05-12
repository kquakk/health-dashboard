package com.vitalcore.health_dashboard.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum WeightUnit {
    POUNDS("lbs") {
        @Override
        public double toPounds(double value) {
            return value;
        }

        @Override
        public double toKilograms(double value) {
            return value / 2.20462;
        }
    },
    KILOGRAMS("kg") {
        @Override
        public double toPounds(double value) {
            return value * 2.20462;
        }

        @Override
        public double toKilograms(double value) {
            return value;
        }
    };

    private final String label;

    WeightUnit(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static WeightUnit fromValue(String value) {
        return Arrays.stream(values())
            .filter(unit -> unit.label.equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unsupported weight unit: " + value));
    }

    public abstract double toPounds(double value);

    public abstract double toKilograms(double value);
}

package com.vitalcore.health_dashboard.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum HeightUnit {
    IMPERIAL("ft/in") {
        @Override
        public double toCentimeters(double value) {
            return value * 2.54;
        }
    },
    CENTIMETERS("cm") {
        @Override
        public double toCentimeters(double value) {
            return value;
        }
    };

    private final String label;

    HeightUnit(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static HeightUnit fromValue(String value) {
        return Arrays.stream(values())
            .filter(unit -> unit.label.equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unsupported height unit: " + value));
    }

    public abstract double toCentimeters(double value);
}

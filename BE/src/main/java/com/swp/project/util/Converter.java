package com.swp.project.util;

public class Converter {
    public static Double convertToDouble(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Cannot convert value to Double: " + value);
            }
        } else {
            throw new IllegalArgumentException("Unexpected type for conversion to Double: " + value.getClass());
        }
    }
}

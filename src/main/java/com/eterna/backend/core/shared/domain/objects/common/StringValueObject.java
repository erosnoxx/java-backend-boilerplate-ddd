package com.eterna.backend.core.shared.domain.objects.common;

import com.eterna.backend.core.shared.domain.exceptions.DomainException;

public abstract class StringValueObject extends ValueObject<String> {

    protected StringValueObject(String value, int minLength, int maxLength) {
        super(value);
        if (value.length() < minLength || value.length() > maxLength) {
            throw new DomainException("must be between " + minLength + " and " + maxLength + " characters"
            );
        }
    }

    @Override
    protected String validate(String value) {
        if (value == null) {
            throw new DomainException(getClass().getSimpleName() + " cannot be null");
        }

        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new DomainException(getClass().getSimpleName() + " cannot be empty");
        }

        int length = value.length();

        return customValidate(trimmed);
    }

    protected abstract String customValidate(String value);

    public boolean isEmpty() {
        return value == null || value.trim().isEmpty();
    }

    public int length() {
        return value != null ? value.length() : 0;
    }
}

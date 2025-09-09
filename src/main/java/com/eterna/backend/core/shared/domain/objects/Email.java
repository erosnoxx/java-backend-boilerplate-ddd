package com.eterna.backend.core.shared.domain.objects;

import com.eterna.backend.core.shared.domain.exceptions.DomainException;
import com.eterna.backend.core.shared.domain.objects.common.StringValueObject;

import java.util.regex.Pattern;

public final class Email extends StringValueObject {
    private static final String EMAIL_PATTERN =
            "(?i)\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}\\b";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private Email(String value) {
        super(value, 5, 100);
    }

    public static Email of(String email) {
        return new Email(email);
    }

    @Override
    protected String customValidate(String value) {
        if (value.length() > 100) {
            throw new DomainException("Email cannot be longer than 100 characters");
        }

        if (!pattern.matcher(value).matches()) {
            throw new DomainException("Invalid email format: " + value);
        }

        return value.toLowerCase();
    }
}

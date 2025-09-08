package com.eterna.backend.core.shared.domain.objects.common;

import com.eterna.backend.core.shared.domain.exceptions.DomainException;

public abstract class Name extends StringValueObject {

    private final int minLength;
    private final int maxLength;

    protected Name(String value, int minLength, int maxLength) {
        super(value); // aqui só guarda o value
        this.minLength = minLength;
        this.maxLength = maxLength;

        validateLength(value); // validação agora acontece após os campos estarem setados
    }

    private void validateLength(String value) {
        int length = value.length();

        if (length < minLength || length > maxLength) {
            throw new DomainException(
                    getClass().getSimpleName() +
                            " must be between " + minLength + " and " + maxLength + " characters"
            );
        }
    }
}

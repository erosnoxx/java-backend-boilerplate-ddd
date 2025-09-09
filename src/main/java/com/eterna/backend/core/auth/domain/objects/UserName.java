package com.eterna.backend.core.auth.domain.objects;

import com.eterna.backend.core.shared.domain.objects.common.StringValueObject;

public final class UserName extends StringValueObject {

    public UserName(String value) {
        super(value, 3, 50);
    }

    public static UserName of(String name) {
        return new UserName(name);
    }

    @Override
    protected String customValidate(String value) {
        return value;
    }
}

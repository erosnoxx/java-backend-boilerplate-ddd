package com.eterna.backend.core.auth.domain.objects;

import com.eterna.backend.core.shared.domain.objects.common.Name;

public final class UserName extends Name {

    public UserName(String value) {
        super(value, 3, 50);
    }

    @Override
    protected String customValidate(String value) {
        return "";
    }


    public static UserName of(String name) {
        return new UserName(name);
    }
}

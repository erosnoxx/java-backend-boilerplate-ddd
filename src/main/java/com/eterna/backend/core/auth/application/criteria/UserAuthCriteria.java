package com.eterna.backend.core.auth.application.criteria;

import com.eterna.backend.core.auth.domain.enums.Role;
import com.eterna.backend.core.shared.application.Criteria;
import com.eterna.backend.core.shared.domain.enums.EntityStatus;

public record UserAuthCriteria(
        String email,
        Role role,
        EntityStatus status)
        implements Criteria {
}

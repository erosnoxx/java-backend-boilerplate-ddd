package com.eterna.backend.unit.objects;

import com.eterna.backend.core.shared.domain.exceptions.DomainException;
import com.eterna.backend.core.shared.domain.objects.Email;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @Test
    void shouldCreateValidEmail() {
        Email email = Email.of("Test.User@example.com");
        assertThat(email.toString()).isEqualTo("test.user@example.com"); // lowercase
    }

    @Test
    void shouldRejectInvalidEmailFormat() {
        assertThatThrownBy(() -> Email.of("invalid-email"))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("Invalid email format");
    }

    @Test
    void shouldRejectEmailTooLong() {
        String longEmail = "a".repeat(101) + "@example.com";
        assertThatThrownBy(() -> Email.of(longEmail))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("Email cannot be longer than 100 characters");
    }
}

package com.eterna.backend.unit.objects;

import com.eterna.backend.core.auth.domain.objects.Password;
import com.eterna.backend.core.shared.domain.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PasswordTest {

    @Test
    void shouldEncryptAndMatchPassword() {
        var encrypted = Password.fromPlainText("Abc123!@");
        assertThat(encrypted.isEncrypted()).isTrue();
    }

    @Test
    void shouldMatchEncryptedPasswordWithPlainText() {
        var plainText = "Abc123!@";
        var encrypted = Password.fromPlainText(plainText);
        var password = Password.fromEncrypted(encrypted.getValue());

        assertThat(encrypted.matches(plainText)).isTrue();
        assertThat(password.matches(plainText)).isTrue();
    }

    @Test
    void shouldRejectShortPassword() {
        assertThatThrownBy(() -> Password.fromPlainText("Ab1!"))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("at least 8 characters");
    }

    @Test
    void shouldRejectPasswordWithoutSpecialChar() {
        assertThatThrownBy(() -> Password.fromPlainText("Abc12345"))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("must contain at least one letter, one number and one special character");
    }

    @Test
    void shouldRejectNullOrEmptyPassword() {
        assertThatThrownBy(() -> Password.fromPlainText(null))
                .isInstanceOf(DomainException.class);
        assertThatThrownBy(() -> Password.fromPlainText("   "))
                .isInstanceOf(DomainException.class);
    }
}

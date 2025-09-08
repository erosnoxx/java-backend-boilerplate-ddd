package com.eterna.backend.unit.objects;

import com.eterna.backend.core.auth.domain.objects.UserName;
import com.eterna.backend.core.shared.domain.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNameTest {

    @Test
    void shouldCreateValidUserName() {
        UserName name = UserName.of("eROS");
        assertNotNull(name);
        assertEquals("eROS", name.getValue());
    }

    @Test
    void shouldTrimWhitespace() {
        UserName name = UserName.of("   John Doe   ");
        assertEquals("John Doe", name.getValue());
    }

    @Test
    void shouldFailWhenNameIsTooShort() {
        assertThrows(DomainException.class, () -> UserName.of("ab"));
    }

    @Test
    void shouldFailWhenNameIsTooLong() {
        String longName = "a".repeat(51);
        assertThrows(DomainException.class, () -> UserName.of(longName));
    }

    @Test
    void shouldFailWhenNameIsEmpty() {
        assertThrows(DomainException.class, () -> UserName.of("   "));
    }

    @Test
    void shouldFailWhenNameIsNull() {
        assertThrows(DomainException.class, () -> new UserName(null));
    }

    @Test
    void shouldReturnCorrectLength() {
        UserName name = UserName.of("Alice");
        assertEquals(5, name.length());
    }

    @Test
    void shouldDetectEmptyCorrectly() {
        UserName name = UserName.of("Bob");
        assertFalse(name.isEmpty());
    }
}

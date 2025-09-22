package com.sensedia.sample.password.utils;

import com.sensedia.sample.password.exception.PasswordInvalidException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PasswordValidateTest {

    private String user;
    private String validPassword;
    private String validConfirmPassword;

    @BeforeEach
    void setUp() {
        user = "joao";
        validPassword = "ABCDEFGH1u@Java";
        validConfirmPassword = "ABCDEFGH1u@Java";
    }

    @Test
    void shouldValidateSuccessfully() {
        assertDoesNotThrow(() -> PasswordValidate.validate(user, validPassword, validConfirmPassword));
    }

    @Test
    void shouldFailWhenPasswordsDoNotMatch() {
        assertThrows(PasswordInvalidException.class, () ->
                PasswordValidate.validate(user, validPassword, validPassword + "x")
        );
    }

    @Test
    void shouldFailWhenTooShort() {
        assertThrows(PasswordInvalidException.class, () ->
                PasswordValidate.validate(user, "A1a!xyz", "A1a!xyz")
        );
    }

    @Test
    void shouldFailWhenMissingUppercase() {
        String passwordWithoutUppercase = "abcdefg1!java";
        assertThrows(PasswordInvalidException.class, () -> PasswordValidate.validate(user, passwordWithoutUppercase, passwordWithoutUppercase));
    }

    @Test
    void shouldFailWhenMissingLowercase() {
        String passwordWithoutLowercase = "ABCDEFGH1!";
        assertThrows(PasswordInvalidException.class, () -> PasswordValidate.validate(user, passwordWithoutLowercase, passwordWithoutLowercase));
    }

    @Test
    void shouldFailWhenMissingDigit() {
        String passwordWithoutDigit = "Abcdefgh!";
        assertThrows(PasswordInvalidException.class, () -> PasswordValidate.validate(user, passwordWithoutDigit, passwordWithoutDigit));
    }

    @Test
    void shouldFailWhenMissingSpecialChar() {
        String passwordWithoutSpecialCharacter = "Abcdefgh1";
        assertThrows(PasswordInvalidException.class, () -> PasswordValidate.validate(user, passwordWithoutSpecialCharacter, passwordWithoutSpecialCharacter));
    }

    @Test
    void shouldFailWhenContainsUsername() {
        String passwordContainingUsername = "joaOJava1!";
        assertThrows(PasswordInvalidException.class, () -> PasswordValidate.validate(user, passwordContainingUsername, passwordContainingUsername));
    }

    @Test
    void shouldAppendAndValidatePasswordReuse() {
        List<String> history = new ArrayList<>();
        history.add("SenhaAntiga1!");
        List<String> updated = PasswordValidate.checkPasswordReuse(validPassword, history);
        assertEquals(2, updated.size());
        assertEquals(validPassword, updated.get(1));
    }

    @Test
    void shouldFailWhenPasswordReused() {
        List<String> history = new ArrayList<>();
        history.add(validPassword);
        assertThrows(PasswordInvalidException.class, () -> PasswordValidate.checkPasswordReuse(validPassword, history));
    }

    @Test
    void shouldKeepLastFivePasswords() {
        List<String> history = new ArrayList<>();
        history.add("A1a!x1");
        history.add("A1a!x2");
        history.add("A1a!x3");
        history.add("A1a!x4");
        history.add("A1a!x5");

        List<String> updated = PasswordValidate.checkPasswordReuse("A1a!x6", history);
        assertEquals(5, updated.size());
        assertEquals("A1a!x2", updated.get(0));
        assertEquals("A1a!x6", updated.get(4));
    }
}



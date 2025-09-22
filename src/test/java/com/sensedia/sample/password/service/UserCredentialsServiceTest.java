package com.sensedia.sample.password.service;

import com.sensedia.sample.password.domain.UserCredentials;
import com.sensedia.sample.password.domain.dto.UserCredentialsDTO;
import com.sensedia.sample.password.exception.PasswordInvalidException;
import com.sensedia.sample.password.repository.UserCredentialsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserCredentialsServiceTest {

    @Mock
    private UserCredentialsRepository repository;

    @InjectMocks
    private UserCredentialsService service;

    private UserCredentialsDTO dto;

    @BeforeEach
    void setUp() {
        dto = UserCredentialsDTO.builder()
                .user("joao")
                .password("ABCDEFGH1u@Java")
                .confirmPassword("ABCDEFGH1u@Java")
                .build();
    }

    @Test
    void shouldSaveNewUserWhenNotExists() {
        when(repository.findByUser("joao")).thenReturn(Optional.empty());
        when(repository.save(any(UserCredentials.class))).thenReturn(
                UserCredentials.builder()
                        .user("joao")
                        .passwords(List.of("ABCDEFGH1u@Java"))
                        .build()
        );

        UserCredentials saved = service.save(dto);

        assertNotNull(saved);
        assertEquals("joao", saved.getUser());
        assertEquals(List.of("ABCDEFGH1u@Java"), saved.getPasswords());
        verify(repository).save(any(UserCredentials.class));
    }

    @Test
    void shouldAppendPasswordWhenUserExists() {
        List<String> passwordHistory = new ArrayList<>();
        passwordHistory.add("SenhaAntiga1!");
        UserCredentials existingUser = UserCredentials.builder()
                .id("abc")
                .user("joao")
                .passwords(passwordHistory)
                .build();

        when(repository.findByUser("joao")).thenReturn(Optional.of(existingUser));
        when(repository.save(any(UserCredentials.class))).thenReturn(existingUser);

        UserCredentials saved = service.save(dto);

        assertNotNull(saved);
        assertEquals(2, saved.getPasswords().size());
        assertEquals("ABCDEFGH1u@Java", saved.getPasswords().get(1));
        verify(repository).save(any(UserCredentials.class));
    }

    @Test
    void shouldThrowWhenPasswordContainsUsername() {
        dto.setPassword("joaoJava1!");
        dto.setConfirmPassword("joaoJava1!");
        assertThrows(PasswordInvalidException.class, () -> service.save(dto));
    }

    @Test
    void shouldKeepOnlyLastFivePasswordsWhenListIsFull() {
        List<String> passwordHistory = new ArrayList<>();
        passwordHistory.add("A1a!x1");
        passwordHistory.add("A1a!x2");
        passwordHistory.add("A1a!x3");
        passwordHistory.add("A1a!x4");
        passwordHistory.add("A1a!x5");

        UserCredentials exist = UserCredentials.builder()
                .id("id-1")
                .user("joao")
                .passwords(passwordHistory)
                .build();

        when(repository.findByUser("joao")).thenReturn(Optional.of(exist));
        when(repository.save(any(UserCredentials.class))).thenReturn(exist);

        UserCredentials saved = service.save(dto);

        assertNotNull(saved);
        assertEquals(5, saved.getPasswords().size());
        assertEquals("A1a!x2", saved.getPasswords().get(0));
        assertEquals("ABCDEFGH1u@Java", saved.getPasswords().get(4));
        verify(repository).save(any(UserCredentials.class));
    }

    @Test
    void shouldThrowWhenReusingPassword() {
        List<String> passwordHistory = new ArrayList<>();
        passwordHistory.add("SenhaAntiga1!");
        passwordHistory.add("ABCDEFGH1u@Java");

        UserCredentials exist = UserCredentials.builder()
                .id("id-1")
                .user("joao")
                .passwords(passwordHistory)
                .build();

        when(repository.findByUser("joao")).thenReturn(Optional.of(exist));

        assertThrows(PasswordInvalidException.class, () -> service.save(dto));
    }
}



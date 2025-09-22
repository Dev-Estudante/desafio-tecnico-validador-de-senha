package com.sensedia.sample.password.rest;

import com.sensedia.sample.password.domain.UserCredentials;
import com.sensedia.sample.password.domain.dto.UserCredentialsDTO;
import com.sensedia.sample.password.service.UserCredentialsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordApiImplTest {

    @Mock
    private UserCredentialsService userCredentialsService;

    @InjectMocks
    private PasswordApi passwordApi;

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
    void shouldReturnBadRequestOnFindAll() {
        ResponseEntity<Object> response = passwordApi.findAll();
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Dummy", response.getBody());
    }

    @Test
    void shouldReturnNoContentOnSave() {
        when(userCredentialsService.save(any(UserCredentialsDTO.class)))
                .thenReturn(new UserCredentials());

        ResponseEntity<Void> response = passwordApi.save(dto);

        assertEquals(204, response.getStatusCode().value());
        verify(userCredentialsService).save(dto);
    }
}


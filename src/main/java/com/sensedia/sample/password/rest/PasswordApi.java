package com.sensedia.sample.password.rest;

import com.sensedia.sample.password.domain.UserCredentials;
import com.sensedia.sample.password.service.UserCredentialsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PasswordApi implements IPasswordApi {

    private final UserCredentialsService service;

	@Override
	public ResponseEntity<Object> findAll() {
		log.info("Requisição recebida!!!!!!");
		return ResponseEntity.badRequest().body("Dummy");
	}

    @Override
    public ResponseEntity<Void> save(@RequestBody UserCredentials credentials) {
        service.save(credentials);
        return ResponseEntity.noContent().build();
    }

}

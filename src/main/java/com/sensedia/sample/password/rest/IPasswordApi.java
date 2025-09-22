package com.sensedia.sample.password.rest;

import com.sensedia.sample.password.domain.dto.UserCredentialsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IPasswordApi {

	@GetMapping(value = "/password-validations", produces = { "application/json" })
	ResponseEntity<Object> findAll();

    @PostMapping(value = "/password-validations", produces = { "application/json" })
    ResponseEntity<Void> save(@RequestBody UserCredentialsDTO request);
}

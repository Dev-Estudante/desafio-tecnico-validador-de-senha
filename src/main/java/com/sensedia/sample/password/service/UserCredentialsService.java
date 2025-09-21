package com.sensedia.sample.password.service;


import com.sensedia.sample.password.domain.UserCredentials;
import com.sensedia.sample.password.repository.UserCredentialsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCredentialsService {

    private final UserCredentialsRepository repository;

    public void save(UserCredentials credentials){
        repository.save(credentials);
    }
}
